DIR_LIB=./lib/
DIR_BLD=./build/

BOARD_ARCH=arduino:avr:uno

SERIAL_PORT=/dev/ttyUSB0
SERIAL_BAUDRATE=115200

deploy: build
	arduino-cli upload -p "$(SERIAL_PORT)" --fqbn "$(BOARD_ARCH)" --input-dir "$(DIR_BLD)"

build: clean
	mkdir -p "$(DIR_LIB)" "$(DIR_BLD)"
	arduino-cli lib install AccelStepper
	arduino-cli lib install MobaTools
	arduino-cli compile --fqbn "$(BOARD_ARCH)" --output-dir "$(DIR_BLD)"

clean:
	-rm -r lib
	-rm -r build

monitor:
	arduino-cli monitor -p "$(SERIAL_PORT)" -c baudrate=$(SERIAL_BAUDRATE)
