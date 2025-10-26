#!/bin/bash

# === Konfiguration ===
PORT="/dev/ttyUSB0"
FQBN="arduino:avr:nano:cpu=atmega328"
PROJECT_DIR="$(dirname "$0")"
OUTDIR="$PROJECT_DIR/build"
BAUDRATE=9600

# === Funktionen ===

compile() {
    echo "🔨 Kompiliere..."
    arduino-cli compile --fqbn "$FQBN" --output-dir "$OUTDIR"
}

upload() {
    echo "🚀 Lade hoch..."
    arduino-cli upload -p "$PORT" --fqbn "$FQBN"
}

monitor() {
    echo "🔍 Starte seriellen Monitor..."
    arduino-cli monitor -p "$PORT" -c baudrate=$BAUDRATE
}

build() {
	compile;
	upload;
	monitor;
}

# === Aufruf verarbeiten ===

case "$1" in
    c)
        compile
        ;;
    u)
        upload
        ;;
    m)
        monitor
        ;;
    b)
        build
        ;;
    *)
        echo "❗ Ungültiger Parameter: $1"
        echo "Verwendung: $0 [c|u|m|b]"
        echo "  c = compile"
        echo "  u = upload"
        echo "  m = monitor"
        echo "  b = build"
        exit 1
        ;;
esac
