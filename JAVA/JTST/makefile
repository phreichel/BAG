run: build
	java -jar Main.jar

build: compile
	cd cls ; jar -c -f ../Main.jar -e Main * ; cd ..

compile: clean 
	javac -d cls --source-path src ./src/Main.java

clean:
	-rm Main.jar
	-rm -rf ./cls/*
