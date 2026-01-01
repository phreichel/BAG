all: mbr.bin

mbr.bin: mbr.ls mbr.o
	ld -m elf_i386 -T mbr.ls -o mbr.bin mbr.o

mbr.o: mbr.s
	as --32 mbr.s -o mbr.o

mbr.ls:

clean:
	@-rm *.o 2> /dev/null
	@-rm *.bin 2> /dev/null
