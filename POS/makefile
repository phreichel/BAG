all: pos.img

run:
	qemu-system-amd64 -drive file=pos.img,format=raw

pos.img: image VBR/vbr.bin KNL/KERNEL.BIN image-loop-mount image-patch-vbr image-patch-loader image-copy-kernel image-loop-unmount
	chown philip:philip pos.img

VBR/vbr.bin:
	make -C VBR

KNL/KERNEL.BIN:
	make -C KNL

clean:
	@-sudo rm *.img 2> /dev/null
	make -C VBR clean
	make -C KNL clean

image:
	set -e; \
	dd if=/dev/zero of=pos.img bs=1M count=64 status=none; \
	printf "label: dos\nunit: sectors\n\n1 : start=2048, size=, type=0x06, bootable\n" | sfdisk pos.img; \
	LOOP=$$(losetup --find --show --partscan pos.img); \
	mkfs.fat -F 16 -R 16 -n POSDISK $${LOOP}p1; \
	losetup -d $$LOOP
	dd if=/usr/lib/syslinux/mbr/mbr.bin of=pos.img bs=440 count=1 conv=notrunc

image-loop-mount:
	@sudo losetup --find --show --partscan pos.img > .loopdev
	@echo "Loop device:" $$(cat .loopdev)

image-patch-vbr:
	@sudo dd if=./VBR/vbr.bin of=$$(cat .loopdev)p1 bs=1 count=3 conv=notrunc
	@sudo dd if=./VBR/vbr.bin of=$$(cat .loopdev)p1 bs=1 skip=90 seek=90 conv=notrunc
	@echo "VBR patched on" $$(cat .loopdev)p1

image-patch-loader:
	@sudo dd if=./VBR/loader.bin of=$$(cat .loopdev)p1 bs=512 seek=1 conv=notrunc
	@echo "Loader written to partition sectors starting at +1"

image-copy-kernel:
	@sudo mkdir -p /mnt/posdisk
	@sudo mount $$(cat .loopdev)p1 /mnt/posdisk
	@sudo cp KNL/KERNEL.BIN /mnt/posdisk/KERNEL.BIN
	@sync
	@sudo umount /mnt/posdisk

image-loop-unmount:
	@sudo losetup -d $$(cat .loopdev)
	@rm -f .loopdev
	@echo "Loop device released"
