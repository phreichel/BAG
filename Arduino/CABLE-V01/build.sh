#!/bin/bash

set -euo pipefail

# === Konfiguration ===
PORT="/dev/ttyUSB0"
FQBN="arduino:avr:uno"
PROJECT_DIR="$(dirname "$0")"
LIBDIR="$PROJECT_DIR/lib"
OUTDIR="$PROJECT_DIR/build"
BAUDRATE=115200

# === Funktionen ===

compile() {
    echo "🔨 Kompiliere..."
    mkdir -p "$LIBDIR" "$OUTDIR"
	arduino-cli lib install AccelStepper
	arduino-cli lib install MobaTools
    arduino-cli compile --fqbn "$FQBN" --output-dir "$OUTDIR"
}

upload() {
    echo "🚀 Lade hoch..."
    arduino-cli upload -p "$PORT" --fqbn "$FQBN" --input-dir "$OUTDIR"
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
        echo "❗ Ungueltiger Parameter: $1"
        echo "Verwendung: $0 [c|u|m|b]"
        echo "  c = compile"
        echo "  u = upload"
        echo "  m = monitor"
        echo "  b = build"
        exit 1
        ;;
esac
