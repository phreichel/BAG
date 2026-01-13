import paho.mqtt.client as mqtt
import pvporcupine
import pyaudio
import struct
import whisper
import torch
import simpleaudio as sa
import wave
import numpy as np
from piper import PiperVoice
import paramiko

def to_bytes(data):
    """Konvertiert alles in echte Bytes, egal welches Format Piper liefert."""
    
    # NumPy-Array → perfekt
    if isinstance(data, np.ndarray):
        return data.tobytes()

    # Falls es schon Bytes sind
    if isinstance(data, (bytes, bytearray)):
        return data

    # Memoryview → Bytes
    if isinstance(data, memoryview):
        return data.tobytes()

    # Python-Liste von ints (sehr selten)
    if isinstance(data, list):
        arr = np.array(data, dtype=np.int16)
        return arr.tobytes()

    # Alles andere → versuchen
    try:
        return bytes(data)
    except Exception:
        raise TypeError(f"Kann Audio-Daten nicht in Bytes konvertieren: {type(data)}")

def speak(text, out_file="tts.wav"):
    audio_gen = voice.synthesize(text)

    with wave.open(out_file, "wb") as wf:
        wf.setnchannels(1)
        wf.setsampwidth(2)        # 16-bit PCM
        wf.setframerate(22050)

        for chunk in audio_gen:
            wf.writeframes(chunk.audio_int16_bytes)

def play_wav(filename):
    wf = wave.open(filename, "rb")

    out = pa.open(
        format=pa.get_format_from_width(wf.getsampwidth()),
        channels=wf.getnchannels(),
        rate=wf.getframerate(),
        output=True
    )

    data = wf.readframes(1024)
    while data:
        out.write(data)
        data = wf.readframes(1024)

    out.stop_stream()
    out.close()
    wf.close()

def say(text):
    speak(text, "now.wav")
    play_wav("now.wav")

def record_until_stop():
    frames = []
    while True:
        pcm = stream.read(porcupine.frame_length, exception_on_overflow=False)
        data = struct.unpack_from("h" * porcupine.frame_length, pcm)
        frames.append(pcm)
        # Ende-Wakeword prüfen
        kw = porcupine.process(data)
        if kw == STOP_KEYWORD:
            break
    return frames

def save_wave(frames, filename="rec.wav"):
    wf = wave.open(filename, "wb")
    wf.setnchannels(1)
    wf.setsampwidth(pa.get_sample_size(pyaudio.paInt16))
    wf.setframerate(porcupine.sample_rate)
    wf.writeframes(b"".join(frames))
    wf.close()
    return filename

def transcribe_local(filename):
    result = model.transcribe(filename, fp16=False)
    return result["text"]

def remote_shutdown(
    host: str,
    user: str,
    ssh_password: str,
    port: int = 22,
    timeout: int = 10
) -> None:
    client = paramiko.SSHClient()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())

    try:
        client.connect(
            hostname=host,
            port=port,
            username=user,
            password=ssh_password,
            timeout=timeout
        )

        stdin, stdout, stderr = client.exec_command(
            "sudo /sbin/shutdown -h now"
        )

        err = stderr.read().decode().strip()
        if err:
            raise RuntimeError(err)

    finally:
        client.close()

def decide(text):
	if "LICHT" in text:
		client.publish(TOPIC_MAP[0], "TOGGLE")
	if "NACHT" in text:
		client.publish(TOPIC_MAP[1], "TOGGLE")
	if ("LINUX" in text and ("SHUTDOWN" in text or "SHUT DOWN" in text)):
		try:
			remote_shutdown(
				host="192.168.178.20",
				user="philip",
				ssh_password="Sackkarre"
			)
		except:
			say("Fehler.");

# --- Piper Voice Setup ---
voice = PiperVoice.load("d:\\DAT\\VOICES\\piper-voices\\de\\de_DE\\thorsten\\medium\\de_DE-thorsten-medium.onnx")

# --- MQTT Setup ---
BROKER = "192.168.178.80"
PORT = 1883
TOPIC_MAP = {
    0: "cmnd/werkstatt/POWER",  # alexa
    1: "cmnd/empore/POWER",     # porcupine
}

client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
client.connect(BROKER, PORT, 60)
client.loop_start()

# --- Whisper Setup (lokal) ---
device = "cuda" if torch.cuda.is_available() else "cpu"

model = whisper.load_model("medium", device=device)

# --- Porcupine Setup ---
# ACCESS_KEY = "Jp6FmuGjHbGzI6zjcujjbzgJJg0enavIkIVi+/AHOXS3Z3ZYjM86Ag==" # OLD KEY noobder?
ACCESS_KEY = "QnbcxVBa+2ERgnuGTLVeDV1FWnxboT28T/tOluf6DN3DSkzNMDgoaw=="   # NEW KEY phreichel

porcupine = pvporcupine.create(
    access_key=ACCESS_KEY,
    keywords=["alexa", "terminator"]  # Start + Ende
)

START_KEYWORD = 0
STOP_KEYWORD  = 1

# --- Pyaudio Setup ---
pa = pyaudio.PyAudio()
stream = pa.open(
    rate=porcupine.sample_rate,
    channels=1,
    format=pyaudio.paInt16,
    input=True,
    frames_per_buffer=porcupine.frame_length
)

say("Aktiv.")

try:
    while True:
        pcm = stream.read(porcupine.frame_length, exception_on_overflow=False)
        data = struct.unpack_from("h" * porcupine.frame_length, pcm)
        keyword_index = porcupine.process(data)

        if keyword_index == START_KEYWORD:
            say("Ja?")
            frames = record_until_stop()
            wav = save_wave(frames)
            text = transcribe_local(wav)
            decide(text)
            say("OK.")

except KeyboardInterrupt:
    say("Beendet.")

finally:
    stream.stop_stream()
    stream.close()
    pa.terminate()
    porcupine.delete()
