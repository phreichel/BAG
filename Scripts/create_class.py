#!/usr/bin/env python3
import os
import sys

HEADER_FOOTER = "//" + "*" * 78
CONSTRUCTOR_BORDER = "\t//" + "=" * 74

def find_src_dir(current_path):
    """ Sucht rekursiv nach dem src-Ordner, ausgehend vom aktuellen Verzeichnis """
    while current_path and current_path != os.path.dirname(current_path):
        if os.path.isdir(os.path.join(current_path, "src")):
            return os.path.join(current_path, "src")
        current_path = os.path.dirname(current_path)
    return None

def generate_class_file(class_name):
    # Aktuelles Arbeitsverzeichnis
    current_dir = os.getcwd()

    # `src`-Verzeichnis suchen
    src_dir = find_src_dir(current_dir)
    if not src_dir:
        print("Fehler: Konnte das 'src'-Verzeichnis nicht finden.")
        sys.exit(1)

    # Relativen Pfad vom aktuellen Verzeichnis zum `src`-Verzeichnis bestimmen
    relative_path = os.path.relpath(current_dir, src_dir)
    package_name = relative_path.replace(os.sep, ".") if relative_path != "." else ""

    # Dateiname bestimmen
    file_name = f"{class_name}.java"

    # Datei erstellen
    with open(file_name, "w") as f:
        # Package-Block
        if package_name:
            f.write(f"{HEADER_FOOTER}\n")
            f.write(f"package {package_name};\n")
            f.write(f"{HEADER_FOOTER}\n\n")

        # Klassen-Header
            f.write(f"{HEADER_FOOTER}\n")
        f.write(f"public class {class_name} {{\n\n")

        # Konstruktor
        f.write(f"{CONSTRUCTOR_BORDER}\n")
        f.write(f"\tpublic {class_name}() {{\n")
        f.write(f"\t}}\n")
        f.write(f"{CONSTRUCTOR_BORDER}\n\n")

        # Klassen-Footer
        f.write("}\n")
        f.write(f"{HEADER_FOOTER}\n")

    print(f"Java-Klasse '{file_name}' erfolgreich erstellt in '{current_dir}'.")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Verwendung: create_class.py Klassenname")
        sys.exit(1)

    generate_class_file(sys.argv[1])
