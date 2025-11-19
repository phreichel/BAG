import pvporcupine
import pyaudio
import struct
import whisper
import torch

import wave
import numpy as np
from piper import PiperVoice

# --- Piper Voice Setup ---
voice = PiperVoice.load("d:\\ORGA\\VOICES\\piper-voices\\de\\de_DE\\thorsten\\medium\\de_DE-thorsten-medium.onnx")

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
        wf.setsampwidth(2)   
        wf.setframerate(22050)

        for chunk in audio_gen:
            if hasattr(chunk, "audio"):
                data = to_bytes(chunk.audio)
            else:
                data = to_bytes(chunk)
            wf.writeframes(data)


# --- Whisper Setup (lokal) ---
device = "cuda" if torch.cuda.is_available() else "cpu"
print("Nutze Whisper auf:", device)

model = whisper.load_model("medium", device=device)

# --- Porcupine Setup ---
ACCESS_KEY = "Jp6FmuGjHbGzI6zjcujjbzgJJg0enavIkIVi+/AHOXS3Z3ZYjM86Ag=="

porcupine = pvporcupine.create(
    access_key=ACCESS_KEY,
    keywords=["alexa", "terminator"]  # Start + Ende
)

START_KEYWORD = 0
STOP_KEYWORD = 1

# --- Pyaudio Setup ---
pa = pyaudio.PyAudio()
stream = pa.open(
    rate=porcupine.sample_rate,
    channels=1,
    format=pyaudio.paInt16,
    input=True,
    frames_per_buffer=porcupine.frame_length
)

def record_until_stop():
    print("Aufnahme läuft... Sag 'terminator' zum Beenden.")
    frames = []

    while True:
        pcm = stream.read(porcupine.frame_length, exception_on_overflow=False)
        data = struct.unpack_from("h" * porcupine.frame_length, pcm)
        frames.append(pcm)

        # Ende-Wakeword prüfen
        kw = porcupine.process(data)
        if kw == STOP_KEYWORD:
            print("→ Aufnahme beendet.")
            break

    return frames

def save_wave(frames, filename="recording.wav"):
    wf = wave.open(filename, "wb")
    wf.setnchannels(1)
    wf.setsampwidth(pa.get_sample_size(pyaudio.paInt16))
    wf.setframerate(porcupine.sample_rate)
    wf.writeframes(b"".join(frames))
    wf.close()
    return filename

def transcribe_local(filename):
    print("→ Lokale Whisper-Transkription...")
    result = model.transcribe(filename, fp16=False)
    return result["text"]

print("Wakeword aktiv – sag 'alexa' ...")

try:
    while True:
        pcm = stream.read(porcupine.frame_length, exception_on_overflow=False)
        data = struct.unpack_from("h" * porcupine.frame_length, pcm)
        keyword_index = porcupine.process(data)

        if keyword_index == START_KEYWORD:
            print("→ Alexa erkannt – Aufnahme startet.")
            frames = record_until_stop()
            wav = save_wave(frames)

            text = transcribe_local(wav)
            print("\n--- ERKANNT ---")
            print(text)
            print("---------------\n")
            speak(text)

except KeyboardInterrupt:
    print("Beendet.")

finally:
    stream.stop_stream()
    stream.close()
    pa.terminate()
    porcupine.delete()
