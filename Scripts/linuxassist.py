import os
import sys
import signal
import time
import json
import pyaudio
import whisper
import torch
import wave
import paramiko
import numpy as np
import paho.mqtt.client as mqtt
from vosk import Model, KaldiRecognizer
import simpleaudio as sa
from piper import PiperVoice

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
		wf.setsampwidth(2)		# 16-bit PCM
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

def save_wave(frames, filename="rec.wav"):
	wf = wave.open(filename, "wb")
	wf.setnchannels(1)
	wf.setsampwidth(pa.get_sample_size(pyaudio.paInt16))
	wf.setframerate(16000)
	wf.writeframes(b"".join(frames))
	wf.close()
	return filename

def transcribe_local(filename):
	result = model.transcribe(filename, fp16=False)
	return result["text"]

def remote_boot(mac):
	cmd = "wakeonlan " + mac
	os.system(cmd)

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

def stop_self():
	subprocess.run(["systemctl", "--user", "stop", "linuxassist"], check=False)

def decide(text):
	found = False
	print(text)
	if "LICHT" in text:
		found = True
		client.publish(TOPIC_MAP[0], "TOGGLE")
	if "NACHT" in text:
		found = True
		client.publish(TOPIC_MAP[1], "TOGGLE")
	if ("HOCHFAHREN" in text or "HOCH FAHREN" in text):
		found = True
		remote_boot("0c:9d:92:82:31:95")
	if ("SHUTDOWN" in text or "SHUT DOWN" in text):
		found = True
		try:
			remote_shutdown(
				host="192.168.178.20",
				user="philip",
				ssh_password="Sackkarre"
			)
		except:
			say("FEHLER");
	if "DIENST BEENDEN" in text:
		found = True
		stop_self()
	if found:
		say("OK")
	else:
		say("SORRY")



running = True

# --- Stop Signal Handling Setup ---
def handle_sigterm(signum, frame):
    global running
    print("SIGTERM empfangen, beende sauber …")
    running = False

signal.signal(signal.SIGTERM, handle_sigterm)
signal.signal(signal.SIGINT, handle_sigterm)

# --- Piper Voice Setup ---
voice = PiperVoice.load("/data/DAT/VOICES/piper-voices/de/de_DE/thorsten/medium/de_DE-thorsten-medium.onnx")

# --- MQTT Setup ---
BROKER = "127.0.0.1"
PORT = 1883
TOPIC_MAP = {
	0: "cmnd/werkstatt/POWER",
	1: "cmnd/empore/POWER",
}

client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
client.connect(BROKER, PORT, 60)
client.loop_start()

# --- Whisper Setup (lokal) ---
device = "cuda" if torch.cuda.is_available() else "cpu"
model = whisper.load_model("medium", device=device)

# --- Vosk setup ---
VOSK_MODEL_PATH = "/data/DAT/vosk-model-small-de-0.15"
vosk_model = Model(VOSK_MODEL_PATH)
keywords = ["maschine", "renn"]
grammar  = json.dumps(keywords)
vosk_rec = KaldiRecognizer(vosk_model, 16000, grammar)
vosk_rec.SetWords(False)


# --- Pyaudio Setup ---
pa = pyaudio.PyAudio()
stream = pa.open(
	rate=16000,
	channels=1,
	format=pyaudio.paInt16,
	input=True,
	frames_per_buffer=4000
)


# --- State Machine Setup ---
STATE_IDLE = 0
STATE_RECORDING = 1
state = STATE_IDLE
frames = []
recording_start = 0.0
MAX_RECORD_TIME = 60  # Sekunden

# --- Main Script ---
say("AKTIV")

try:
	while running:

		heard = ""
		pcm = stream.read(4000, exception_on_overflow=False)
		has_final = vosk_rec.AcceptWaveform(pcm)
		if has_final:
			res = json.loads(vosk_rec.Result())
			heard = res.get("text", "").lower()

		# Audio puffern NUR im Recording-Modus
		if state == STATE_RECORDING:

			frames.append(pcm)
			if time.time() - recording_start > MAX_RECORD_TIME:
				# Timeout → verwerfen

				state = STATE_IDLE

				stream.stop_stream()
				say("ABBRUCH")
				stream.start_stream()

				vosk_rec.Reset()
				frames.clear()

				continue

			elif keywords[1] in heard:

				state = STATE_IDLE

				filename = save_wave(frames)
				detail = transcribe_local(filename).upper()
				decide(detail)

				vosk_rec.Reset()
				frames.clear()

				continue

		elif state == STATE_IDLE:

			if keywords[0] in heard:

				state = STATE_RECORDING

				stream.stop_stream()
				say("JA")
				stream.start_stream()

				recording_start = time.time()

				vosk_rec.Reset()
				frames.clear()

				continue

finally:
	say("BEENDET")
	print("Cleanup …")
	stream.stop_stream()
	stream.close()
	pa.terminate()
	client.loop_stop()
	client.disconnect()
	print("Beendet.")	
	sys.exit(0)
