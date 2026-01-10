.equ VBR_SEGMENT,   0x07C0
.equ LDR_SEGMENT,   0x7000
.equ KNL_SEGMENT,   0x1000
.equ KNL_OFFSET,    0x0000
.equ HEAP_SEGMENT,  0x8000
.equ STACK_SEGMENT, 0x9000

#===========================================================

.code16
.global _start

#===========================================================

.org 0x0000
_start:

	# set stack with interrupts disabled
	cli
	movw $STACK_SEGMENT, %ax
	movw %ax, %ss
	movw $0xfffe, %sp
	sti

	movb $0x0E, %ah
	movb $'L', %al
	int  $0x10

	pushw $VBR_SEGMENT
	popw  %ds
	pushw %cs
	popw  %es

	movb %ds:(BPB_SectorsPerCluster), %al
	movb %al, %es:(_BPB_SectorsPerCluster)
	
	movw %ds:(BPB_ReservedSectors), %ax
	movw %ax, %es:(_BPB_ReservedSectors)
	
	movb %ds:(BPB_NumFATs), %al
	movb %al, %es:(_BPB_NumFATs)
	
	movw %ds:(BPB_RootEntryCount), %ax
	movw %ax, %es:(_BPB_RootEntryCount)
	
	movw %ds:(BPB_SectorsPerFAT), %ax
	movw %ax, %es:(_BPB_SectorsPerFAT)
	
	movw %ds:BPB_HiddenSectors+0, %ax
	movw %ax, %es:_BPB_HiddenSectors+0
	movw %ds:BPB_HiddenSectors+2, %ax
	movw %ax, %es:_BPB_HiddenSectors+2

	pushw %cs
	popw  %ds
	xorw %si, %si

	pushw $HEAP_SEGMENT
	popw  %es
	xorw %di, %di

	# save boot drive
	movb %dl, boot_drive

	call calc_FAT_start_sector
	call calc_rootdir_start_sector
	call calc_data_start_sector
	jmp read_entries # never returns

#===========================================================

_BPB_SectorsPerCluster:	.byte 0
_BPB_ReservedSectors:	.word 0	# sectors from partition start to data start, inkl. VBR
_BPB_NumFATs:			.byte 0
_BPB_RootEntryCount:	.word 0
_BPB_SectorsPerFAT:		.word 0
_BPB_HiddenSectors:		.long 0	# sectors to partition start on disk

#===========================================================

#-----------------------------------------------------------

calc_FAT_start_sector:

	movw _BPB_HiddenSectors+0, %ax
	movw _BPB_HiddenSectors+2, %dx
	addw _BPB_ReservedSectors, %ax
	adcw $0, %dx
	movw %ax,   FirstFAT_LBA+0
	movw %dx,   FirstFAT_LBA+2

	ret

#===========================================================

calc_rootdir_start_sector:

	movw _BPB_SectorsPerFAT, %ax
	xorw %bx, %bx
	movb _BPB_NumFATs, %bl
	mulw %bx
	addw FirstFAT_LBA+0, %ax
	adcw FirstFAT_LBA+2, %dx
	movw %ax,   FirstRoot_LBA+0
	movw %dx,   FirstRoot_LBA+2

	ret

#===========================================================

calc_data_start_sector:

	movw _BPB_RootEntryCount, %ax
	shlw $5, %ax	# *32
	addw $511, %ax
	shrw $9, %ax	# /512

	xorw %dx, %dx
	addw FirstRoot_LBA+0, %ax
	adcw FirstRoot_LBA+2, %dx
	movw %ax,   FirstData_LBA+0
	movw %dx,   FirstData_LBA+2

	ret

#===========================================================

current_lba:		.long 0
remaining_entries:	.word 0

#-----------------------------------------------------------

read_entries:

	pushw FirstRoot_LBA+0
	popw current_lba+0
	pushw FirstRoot_LBA+2
	popw current_lba+2
	
	pushw _BPB_RootEntryCount
	popw remaining_entries

read_entries_1:

	movw $1, dap_count
	movw $0x0000, dap_offset
	movw %es, dap_segment
	pushw current_lba+0
	popw dap_lba+0
	pushw current_lba+2
	popw dap_lba+2
	movw $0x00, dap_lba+4
	movw $0x00, dap_lba+6

	call disk_read_dap
	jmp read_sector_entries

read_entries_2:

	addw $0x01, current_lba+0
	adcw $0x00, current_lba+2
	
	jmp read_entries_1

#-----------------------------------------------------------

read_sector_entries:

	cld
	movw $0x10, %cx
	xorw %di, %di

read_sector_entries_1:

	movb %es:(%di), %al
	cmpb $0x00, %al
	je halt

	cmpb $0xE5, %al
	je read_sector_entries_2

	pushw %cx
	pushw %di
	movw $KernelName, %si	# DS:SI = compare string
	movw $11, %cx			# compare 11 bytes
	repe cmpsb				# compare ES:DI with DS:SI
	popw %di
	popw %cx
	je read_sector_entries_3

read_sector_entries_2:

	decw remaining_entries
	jz halt

	addw $0x20, %di
	decw %cx
	jnz read_sector_entries_1

	jmp read_entries_2

read_sector_entries_3:

	movw %es:26(%di), %ax
	movw %ax, KernelFirstCluster

	movw %es:28(%di), %ax
	movw %ax, KernelFileSize+0
	movw %es:30(%di), %ax
	movw %ax, KernelFileSize+2

	# leave main call structure and
	# jump to kernel read structure
	jmp read_kernel

#===========================================================

KernelCurrentCluster:	.word 0
KernelCurrentOffset:	.word 0
KernelCurrentSegment:	.word 0

#-----------------------------------------------------------

read_kernel:

	# reset stack - not necessary any more due to jumps instead calls?
	cli
	movw $0xFFFE, %sp
	sti

	pushw KernelFirstCluster
	popw KernelCurrentCluster
	movw $KNL_OFFSET,	KernelCurrentOffset
	movw $KNL_SEGMENT,	KernelCurrentSegment

read_kernel_1:

	# end of fat cluster chain
	cmpw $0xFFF8, KernelCurrentCluster
	jae launch_kernel

	call read_dat_block
	call read_fat_block

	jmp read_kernel_1

launch_kernel:
	ljmp $KNL_SEGMENT, $KNL_OFFSET

#-----------------------------------------------------------

# expects KernelCurrentCluster to be valid
# expects KernelCurrentOffset to be valid
# expects KernelCurrentSegment to be valid
# loads current cluster into memory
# recalculates KernelCurrentOffset and KernelCurrentSegment
read_dat_block:

	pushw KernelCurrentOffset
	popw  dap_offset
	pushw KernelCurrentSegment
	popw  dap_segment

	xor %ax, %ax
	movb _BPB_SectorsPerCluster, %al
	movw %ax, dap_count
	shlw $0x09, %ax # *512 (bytes)

	addw %ax, KernelCurrentOffset
	jnc read_dat_block_1
	addw $0x1000, KernelCurrentSegment

read_dat_block_1:

	# calculate cluster start sector 
	pushw FirstData_LBA+0 
	popw  dap_lba+0
	pushw FirstData_LBA+2
	popw  dap_lba+2
	movw  $0x00, dap_lba+4
	movw  $0x00, dap_lba+6

	movw KernelCurrentCluster, %ax
	cmp $0x2, %ax
	jb halt
	subw $0x2, %ax
	xorw %dx, %dx
	xorw %bx, %bx
	movb _BPB_SectorsPerCluster, %bl
	mulw %bx

	addw %ax,   dap_lba+0
	adcw %dx,   dap_lba+2
	adcw $0x00, dap_lba+4
	adcw $0x00, dap_lba+6

	call disk_read_dap

	ret

#-----------------------------------------------------------

# expects KernelCurrentCluster to be valid
# reads coresponding fat block to 8000:0000
# updates KernelCurrentCluster from fat entry
read_fat_block:

	# calculate fat segment

	pushw FirstFAT_LBA+0
	popw  dap_lba+0
	pushw FirstFAT_LBA+2
	popw  dap_lba+2
	movw  $0x00, dap_lba+4
	movw  $0x00, dap_lba+6

	movw KernelCurrentCluster, %ax
	shrw $0x08, %ax

	addw %ax,   dap_lba+0
	adcw $0x00, dap_lba+2
	adcw $0x00, dap_lba+4
	adcw $0x00, dap_lba+6

	movw $0x01, dap_count
	movw $0x0000, dap_offset
	movw %es, dap_segment

	call disk_read_dap

	# update KernelCurrentCluster	
	movw KernelCurrentCluster, %di
	andw $0x00FF, %di
	shlw $0x0001, %di
	pushw %es:(%di)
	popw KernelCurrentCluster

	ret

#===========================================================

dap:
			 .byte 0x10	# dap struct size (fixed)
			 .byte 0x00
dap_count:   .word 0	# number of sectors to read in sequence
dap_offset:  .word 0	# offset of memory destination
dap_segment: .word 0	# segment of memory destination
dap_lba:     .quad 0	# 64-bit LBA - i.e. logical 512 bytes data segment index counted from start of physical disk

#-----------------------------------------------------------

disk_read_dap:
	movb boot_drive, %dl
	movb $0x42, %ah
	movw $dap, %si	# DS:SI -> DAP
	int $0x13
	jc halt
	ret

#===========================================================

halt:
	cli
	hlt
	jmp .
	
	#never returns

#===========================================================

KernelName:         .ascii "KERNEL  BIN" # exactly 11 bytes
KernelFileSize:     .long 0
KernelFirstCluster: .word 0

boot_drive:    .byte 0
FirstFAT_LBA:  .long 0
FirstRoot_LBA: .long 0
FirstData_LBA: .long 0

#===========================================================

.section .vbrdata
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
BPB_HiddenSectors:		.long 0	# 0x1C  (sectors to partition start on disk)
BPB_TotalSectors32:		.long 0	# 0x20  (in case 0x13 == 0)
