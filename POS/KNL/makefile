all: KERNEL.BIN

KERNEL.BIN: start.o main.o ldrBIOS.o
	ld86 -d -o KERNEL.BIN start.o main.o ldrBIOS.o

start.o: start.asm
	nasm -f as86 start.asm -o start.o

main.o: main.c
	bcc -c main.c -o main.o

ldrBIOS.o: ldrBIOS.asm
	nasm -f as86 ldrBIOS.asm -o ldrBIOS.o

clean:
	@-rm *.o 2> /dev/null
	@-rm *.elf 2> /dev/null
	@-rm *.BIN 2> /dev/null


