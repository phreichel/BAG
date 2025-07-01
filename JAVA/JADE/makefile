# Hauptvariablen
SRC_DIR=src
CLS_DIR=cls
LIB_DIR=lib
JAR_NAME=jade.jar
MAIN_CLASS=jade.app.Main # Ändere dies auf deine Main-Klasse
JAVA_FILES=$(shell find $(SRC_DIR) -name "*.java")
CLASS_FILES=$(JAVA_FILES:$(SRC_DIR)/%.java=$(CLS_DIR)/%.class)

# Erstelle eine CLASSPATH-Variable, die alle JARs im lib-Verzeichnis enthält
LIB_CP=$(shell find $(LIB_DIR) -name "*.jar" | tr '\n' ':')

# Falls es Bibliotheken gibt, hänge sie an den CLASSPATH an
ifneq ($(LIB_CP),)
    CP_OPTION=-cp "$(LIB_CP):$(CLS_DIR)"
else
    CP_OPTION=-cp "$(CLS_DIR)"
endif

# Targets
.PHONY: all compile run jar fatjar clean

all: compile jar

# Kompilieren der Java-Dateien
compile:
	@mkdir -p $(CLS_DIR)
	@javac $(CP_OPTION) -d $(CLS_DIR) $(JAVA_FILES)

# Programm ausführen
run: compile
	@java $(CP_OPTION) $(MAIN_CLASS)

# JAR erstellen
jar: compile
	@echo "Main-Class: $(MAIN_CLASS)" > manifest.txt
	@jar cfm $(JAR_NAME) manifest.txt -C $(CLS_DIR) .
	@rm manifest.txt
	@echo "Erstellte JAR-Datei: $(JAR_NAME)"

# Fat JAR (inkl. aller Abhängigkeiten)
fatjar: compile
	@echo "Main-Class: $(MAIN_CLASS)" > manifest.txt
	@mkdir -p tmp_lib

	# Kopieren der JARs aus dem lib-Verzeichnis
	@cp $(shell find $(LIB_DIR) -name "*.jar") tmp_lib 2>/dev/null || true

	# Erstelle den Fat JAR, der nur Klassen aus dem CLS-Verzeichnis enthält
	@jar cfm $(JAR_NAME) manifest.txt -C $(CLS_DIR) . 

	# Entpacke jede JAR-Datei aus tmp_lib und füge sie dem Fat JAR hinzu
	@cd tmp_lib && for jar in *.jar; do \
		jar xf $$jar -C $$PWD; \
	done

# Aufräumen
clean:
	@rm -rf $(CLS_DIR) $(JAR_NAME) tmp_lib
	@echo "Aufräumen abgeschlossen."
