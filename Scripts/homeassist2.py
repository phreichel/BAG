import numpy as np
import sounddevice as sd
import webrtcvad
import whisper
import torch
import paho.mqtt.client as mqtt
import time
import logging

# Logging aktivieren
logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(message)s")

# --- MQTT Setup ---
BROKER = "192.168.178.20"
PORT = 1883
TOPIC_MAP = {
    "arbeiten": "cmnd/werkstatt/POWER",
    "schlafen": "cmnd/empore/POWER",
}

client = mqtt.Client()
client.connect(BROKER, PORT, 60)
client.loop_start()

# --- Audio / VAD Setup ---
samplerate = 16000
block_duration = 30  # ms
vad = webrtcvad.Vad(2)  # Sprachaktivitätserkennung (Aggressivität 0–3)

# --- Whisper Setup ---
device = "cuda" if torch.cuda.is_available() else "cpu"
logging.info(f"Whisper läuft auf: {device}")
model = whisper.load_model("tiny", device=device)

# --- Hotword ---
HOTWORD = "computer"
hotword_active = False


def to_whisper_audio(data, sr):
    import torchaudio

    if sr != 16000:
        data = torchaudio.functional.resample(
            torch.from_numpy(data), sr, 16000).numpy()

    if data.ndim > 1:
        data = np.mean(data, axis=1)

    if data.dtype != np.float32:
        data = data.astype(np.float32) / np.iinfo(data.dtype).max

    return data


def record_until_silence(max_duration=5.0, silence_timeout=1.0):
    """Nimmt Audio auf, bis Stille erkannt wird oder max_duration erreicht ist"""
    block_size = int(samplerate * block_duration / 1000)
    frames = []
    silence_blocks = 0
    max_blocks = int(max_duration * 1000 / block_duration)
    silence_blocks_needed = int(silence_timeout * 1000 / block_duration)

    try:
        with sd.InputStream(samplerate=samplerate, channels=1, dtype="int16") as stream:
            while True:
                block, _ = stream.read(block_size)
                block_bytes = block.tobytes()
                is_speech = vad.is_speech(block_bytes, samplerate)

                frames.append(block.copy())

                if is_speech:
                    silence_blocks = 0
                else:
                    silence_blocks += 1

                if silence_blocks > silence_blocks_needed or len(frames) > max_blocks:
                    break
    except Exception as e:
        logging.error(f"Fehler beim Audioinput: {e}")
        return None

    if not frames:
        return None

    audio = np.concatenate(frames).astype(np.float32) / 32768.0
    return audio


# --- Main Loop ---
logging.info("Starte Sprachsteuerung – sage erst 'Computer', dann einen Befehl (CTRL+C zum Abbrechen)")

try:
    while True:
        audio = record_until_silence()
        if audio is None:
            continue

        audio = to_whisper_audio(audio, samplerate)
        audio = whisper.pad_or_trim(audio)

        result = model.transcribe(audio, language="de")
        text = result["text"].strip().lower()

        if not text:
            continue

        logging.info(f"Erkannt: {text}")

        # Hotword erkennen
        if HOTWORD in text:
            hotword_active = True
            logging.info("Hotword erkannt – warte auf Befehl...")
            continue

        # Nur nach Hotword Befehle akzeptieren
        if hotword_active:
            for keyword, topic in TOPIC_MAP.items():
                if keyword in text:
                    client.publish(topic, "TOGGLE")
                    logging.info(f"→ MQTT: {topic} TOGGLE")
                    hotword_active = False   # zurücksetzen
                    break

        time.sleep(0.2)

except KeyboardInterrupt:
    logging.info("Beende Sprachsteuerung...")
finally:
    client.loop_stop()
    client.disconnect()
