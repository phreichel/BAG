import os
import subprocess
import sys
import urllib.request
import zipfile

IVY_URL = "https://repo1.maven.org/maven2/org/apache/ivy/ivy/2.5.0/ivy-2.5.0.jar"
IVY_JAR_PATH = "ivy-2.5.0.jar"
IVY_BIN_DIR = "ivy"  # Verzeichnis, in dem wir die Ivy JAR ablegen wollen
CACHE_DIR = "ivy"  # Dein lokales Ivy-Cache-Verzeichnis
LIB_DIR = "lib"  # Ziel-Verzeichnis fuer Abhaengigkeiten

IVY_SETTINGS_FILE = "ivy/ivysettings.xml"  # Dein Ivy-Einstellungs-XML
IVY_FILE = "ivy/ivy.xml"  # Dein Ivy-Abhaengigkeits-XML

def check_and_create_dirs():
    """Stellt sicher, dass die benoetigten Verzeichnisse existieren"""
    if not os.path.exists(CACHE_DIR):
        os.makedirs(CACHE_DIR)
    if not os.path.exists(LIB_DIR):
        os.makedirs(LIB_DIR)
    if not os.path.exists(IVY_BIN_DIR):
        os.makedirs(IVY_BIN_DIR)

def download_ivy_jar():
    """Laedt die Ivy JAR-Datei herunter, wenn sie noch nicht vorhanden ist"""
    if not os.path.exists(IVY_BIN_DIR):
        os.makedirs(IVY_BIN_DIR)
    if not os.path.exists(os.path.join(IVY_BIN_DIR, IVY_JAR_PATH)):
        print("Ivy JAR nicht gefunden. Lade Ivy JAR herunter...")
        try:
            urllib.request.urlretrieve(IVY_URL, os.path.join(IVY_BIN_DIR, IVY_JAR_PATH))
            print(f"Ivy JAR erfolgreich heruntergeladen nach '{os.path.join(IVY_BIN_DIR, IVY_JAR_PATH)}'")
        except Exception as e:
            print(f"Fehler beim Herunterladen der Ivy JAR-Datei: {e}")
            sys.exit(1)
    else:
        print("Ivy JAR ist bereits vorhanden.")

def download_dependencies():
    """Laedt Abhaengigkeiten mithilfe von Ivy herunter und kopiert sie ins lib-Verzeichnis"""
    command = [
        "java", "-jar", os.path.join(IVY_BIN_DIR, IVY_JAR_PATH),
        "-settings", IVY_SETTINGS_FILE,
        "-ivy", IVY_FILE,
        "-cache", CACHE_DIR
    ]
    
    # Ivy ausfuehren, um die Abhaengigkeiten zu downloaden
    try:
        subprocess.run(command, check=True)
        print("Abhaengigkeiten wurden erfolgreich heruntergeladen.")
    except subprocess.CalledProcessError as e:
        print(f"Fehler beim Ausfuehren von Ivy: {e}")
        sys.exit(1)

def copy_to_lib():
    """Kopiert die heruntergeladenen Abhaengigkeiten in das lib-Verzeichnis"""
    # Angenommen, die Abhaengigkeiten befinden sich im Cache-Verzeichnis
    cache_path = CACHE_DIR
    
    if os.path.exists(cache_path):
        for root, dirs, files in os.walk(cache_path):
            for file in files:
                if file.endswith(".jar"):
                    # Datei ins lib-Verzeichnis kopieren
                    src = os.path.join(root, file)
                    dest = os.path.join(LIB_DIR, file)
                    if not os.path.exists(dest):  # Verhindert das erneute Kopieren
                        print(f"Copying {src} to {dest}")
                        os.rename(src, dest)
    else:
        print(f"Fehler: Das Cache-Verzeichnis '{cache_path}' wurde nicht gefunden.")
        sys.exit(1)

def main():
    check_and_create_dirs()
    download_ivy_jar()
    download_dependencies()
    copy_to_lib()

if __name__ == "__main__":
    main()
