all: vbr.bin loader.bin

vbr.bin: vbr.ls vbr.o
	ld -m elf_i386 -T vbr.ls -o vbr.bin vbr.o

loader.bin: loader.ls loader.o
	ld -m elf_i386 -T loader.ls -o loader.bin loader.o

vbr.o: vbr.s
	as --32 vbr.s -o vbr.o

loader.o: loader.s
	as --32 loader.s -o loader.o

vbr.ls:

loader.ls:

clean:
	@-rm *.o 2> /dev/null
	@-rm *.bin 2> /dev/null
