all: vbr.bin

vbr.bin: vbr.ls vbr.o
	ld -m elf_i386 -T vbr.ls -o vbr.bin vbr.o

vbr.o: vbr.s
	as --32 vbr.s -o vbr.o

vbr.ls:

clean:
	@-rm *.o 2> /dev/null
	@-rm *.bin 2> /dev/null
