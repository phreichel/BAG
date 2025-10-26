import paho.mqtt.client as mqtt
import pvporcupine
import pyaudio
import struct

# --- MQTT Setup ---
BROKER = "192.168.178.20"
PORT = 1883
TOPIC_MAP = {
    0: "cmnd/werkstatt/POWER",  # alexa
    1: "cmnd/empore/POWER",     # porcupine
}

client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
client.connect(BROKER, PORT, 60)
client.loop_start()

# --- Porcupine Setup ---
ACCESS_KEY = "Jp6FmuGjHbGzI6zjcujjbzgJJg0enavIkIVi+/AHOXS3Z3ZYjM86Ag=="
porcupine = pvporcupine.create(
	access_key=ACCESS_KEY,
	keywords=["alexa", "porcupine"]
)

pa = pyaudio.PyAudio()
stream = pa.open(
    rate=porcupine.sample_rate,
    channels=1,
    format=pyaudio.paInt16,
    input=True,
    frames_per_buffer=porcupine.frame_length
)

print("Sprachsteuerung aktiv – sag 'alexa' oder 'porcupine' ...")

try:
    while True:
        pcm = stream.read(porcupine.frame_length, exception_on_overflow=False)
        pcm = struct.unpack_from("h" * porcupine.frame_length, pcm)

        keyword_index = porcupine.process(pcm)
        if keyword_index >= 0:
            print(f"→ Erkannt: {keyword_index} -> {TOPIC_MAP[keyword_index]}")
            client.publish(TOPIC_MAP[keyword_index], "TOGGLE")

except KeyboardInterrupt:
    print("Beendet.")

finally:
    stream.stop_stream()
    stream.close()
    pa.terminate()
    porcupine.delete()
    client.loop_stop()
    client.disconnect()
