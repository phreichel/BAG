all: pos.img

run: all
	qemu-system-amd64 pos.img

pos.img: MBR/mbr.bin LDR/loader.bin
	cat MBR/mbr.bin LDR/loader.bin > pos.img

MBR/mbr.bin:
	make -C MBR

LDR/loader.bin:
	make -C LDR

clean:
	@-rm *.img 2> /dev/null
	make -C MBR clean
	make -C LDR clean
