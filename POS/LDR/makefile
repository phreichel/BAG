all: loader.bin

loader.bin: loader.ls ldrStart.o ldrBIOS.o ldrMain.o
	ld -m elf_i386 -T loader.ls -o loader.bin ldrStart.o ldrBIOS.o ldrMain.o

ldrStart.o: ldrStart.s
	as --32 ldrStart.s -o ldrStart.o

ldrBIOS.o: ldrBIOS.s
	as --32 ldrBIOS.s -o ldrBIOS.o

ldrMain.o: ldrMain.c
	gcc -m16 -ffreestanding -fno-builtin -fno-pic -fno-stack-protector -fno-asynchronous-unwind-tables -nostdlib -c ldrMain.c -o ldrMain.o

loader.ls:

clean:
	@-rm *.o 2> /dev/null
	@-rm *.bin 2> /dev/null
