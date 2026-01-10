.code16
.global _start

#===========================================================

.equ VBR_SEGMENT, 0x07C0
.equ VBR_OFFSET,  0x0000

.equ LDR_SEGMENT, 0x7000
.equ LDR_OFFSET,  0x0000

.equ STACK_SEGMENT, 0x9000

#===========================================================

.org 0x0000
_start:
	jmp longjump
	nop

#===========================================================

.org 0x0B
BPB_BytesPerSector:		.word 0	# 0x0B  → always 512 (boot capable)
BPB_SectorsPerCluster:	.byte 0	# 0x0D
BPB_ReservedSectors:	.word 0	# 0x0E  (# sectors from partition start to data start, inkl. VBR)
BPB_NumFATs:			.byte 0	# 0x10
BPB_RootEntryCount:		.word 0	# 0x11
BPB_TotalSectors16:		.word 0	# 0x13
BPB_Media:				.byte 0	# 0x15
BPB_SectorsPerFAT:		.word 0	# 0x16
BPB_SectorsPerTrack:	.word 0	# 0x18  (for CHS, optional)
BPB_NumHeads:			.word 0	# 0x1A  (for CHS, optional)
BPB_HiddenSectors:		.long 0	# 0x1C  (# sectors to partition start on disk)
BPB_TotalSectors32:		.long 0	# 0x20  (in case 0x13 == 0)

#===========================================================

.org 0x005A

#===========================================================

longjump:
	ljmp $VBR_SEGMENT, $main

#===========================================================

boot_drive:	 .byte 0

dap:
			 .byte 0x10	# dap struct size (fixed)
			 .byte 0x00
dap_count:   .word 0	# number of sectors to read in sequence
dap_offset:  .word 0	# offset of memory destination
dap_segment: .word 0	# segment of memory destination
dap_lba:     .quad 0	# 64-bit LBA - i.e. logical 512 bytes data segment index counted from start of physical disk

#===========================================================

	# main vbr
main:

	# set important registers with interrupts disabled
	cli
	movw $STACK_SEGMENT, %ax
	movw %ax, %ss
	movw $0xfffe, %sp
	sti

	movw %cs, %ax
	movw %ax, %ds

	movb %dl, boot_drive

	movb $0x0E, %ah
	movb $'V', %al
	int  $0x10

	movw BPB_ReservedSectors, %ax
	decw %ax
	jz .error
	cmpw $128, %ax
	jae .error

	movw %ax, dap_count

	movw $LDR_OFFSET,  dap_offset
	movw $LDR_SEGMENT, dap_segment
	
	pushw BPB_HiddenSectors+0
	popw  dap_lba+0
	pushw BPB_HiddenSectors+2
	popw  dap_lba+2
	movw $0x00, dap_lba+4
	movw $0x00, dap_lba+6
	
	addw $1, dap_lba+0
	adcw $0, dap_lba+2
	adcw $0, dap_lba+4
	adcw $0, dap_lba+6

	xorw %ax,%ax
	movb $0x42, %ah  # BIOS function: read sectors (LBA)
	# DL = boot drive (set by BIOS)
	movw $dap, %si

	int  $0x13
	jc   .error  # Carry = error

	movb boot_drive, %dl

	# jump to loader
	ljmp $LDR_SEGMENT, $LDR_OFFSET

#===========================================================
    
	# go into sleep mode
.error:
	movb $0x0E, %ah
	movb $'E', %al
	int  $0x10
	cli
	hlt
	jmp .

#===========================================================

	# boot signature
	.fill 510 - (. - _start), 1, 0
	.word 0xAA55
