import sounddevice as sd, numpy as np, whisper, webrtcvad, paho.mqtt.client as mqtt, time, torch

BROKER = "192.168.178.20"
PORT   = 1883
COMMANDS = {
    "schlafen": ("cmnd/empore/POWER", "TOGGLE"),
    "arbeiten": ("cmnd/werkstatt/POWER", "TOGGLE"),
}

samplerate     = 16000
block_duration = 30
vad = webrtcvad.Vad(2)

device = "cuda" if torch.cuda.is_available() else "cpu"
model = whisper.load_model("tiny")

client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
client.connect(BROKER, PORT, 60)
client.loop_start()

def to_whisper_audio(data, sr):
    import torch, torchaudio
    if sr != 16000:
        data = torchaudio.functional.resample(torch.from_numpy(data), sr, 16000).numpy()
    if data.ndim > 1:
        data = np.mean(data, axis=1)
    if data.dtype != np.float32:
        data = data.astype(np.float32) / np.iinfo(data.dtype).max
    return data

def record_until_silence(max_duration=5.0, silence_timeout=1.0):
    block_size = int(samplerate * block_duration / 1000)
    frames, silence_blocks = [], 0
    max_blocks = int(max_duration * 1000 / block_duration)
    silence_blocks_needed = int(silence_timeout * 1000 / block_duration)

    with sd.InputStream(samplerate=samplerate, channels=1, dtype="int16") as stream:
        while True:
            block, _ = stream.read(block_size)
            block_bytes = block.tobytes()
            if vad.is_speech(block_bytes, samplerate):
                silence_blocks = 0
            else:
                silence_blocks += 1
            frames.append(block.copy())
            if silence_blocks > silence_blocks_needed or len(frames) > max_blocks:
                break
    return np.concatenate(frames).astype(np.float32) / 32768.0

while True:
    try:
        audio = record_until_silence()
        audio = to_whisper_audio(audio, samplerate)
        audio = whisper.pad_or_trim(audio)
        result = model.transcribe(audio, language="de")
        text = result["text"].strip().lower()
        if text:
            print("Erkannt:", text)
            for key, (topic, msg) in COMMANDS.items():
                if key in text:
                    client.publish(topic, msg)
                    print("→ MQTT:", topic, msg)
    except Exception as e:
        print("Fehler:", e)
        time.sleep(2)
