		/* Set 16bit format and ip origin */
		.code16
		.global _start

		/* long jump to correct memory address */
_start:
		ljmp $0x07C0, $.start

		/* real program start */
.start:

		/* set important registers with interrupts disabled */
		cli
		movw $0x07C0, %ax
		movw %ax, %ss
		movw %ax, %ds
		movw %ax, %es
		movb %dl, disk
		movw $0xffff, %sp
		sti


		/* clear screen */
		movw $0x0600, %ax	# AH=06h, AL=00h
		movb $0x07, %bh		# attributes: light gray on black
		xorw %cx, %cx		# CH=row=0, CL=col=0
		mov $0x184F, %dx	# DH=row=24, DL=79 (80x25)
		int $0x10

		/* text cursor to top left */
		movw $0x0200, %ax	# AH=02h
		xorw %bx, %bx		# BH=0 (page 0)
		xorw %dx, %dx		# DH=0, DL=0
		int $0x10

		/* teletype */
		movw $msg, %si
.print:
		lodsb			# AL = [DS:SI++]
		testb %al, %al
		jz .load
		movb $0x0E, %ah
		xorw %bx, %bx	# BH=0 (page 0), BL=0
		int $0x10
		jmp .print

		/* load stage 2 to memory address 0x9000:0000 */
.load:

		cli

		/* Zieladresse: ES:BX = 9000:0000 */
		movw $0x9000, %ax
		movw %ax, %es
		xorw %bx, %bx

		/* BIOS Disk Read */
		movb $0x02, %ah      /* Funktion: Read sectors */
		movb $0x01, %al      /* Anzahl Sektoren (z.B. 1) */
		movb $0x00, %ch      /* Cylinder 0 */
		movb $0x02, %cl      /* Sector 2 (MBR ist Sector 1!) */
		movb $0x00, %dh      /* Head 0 */
		/* DL = Boot Drive (vom BIOS gesetzt!) */
		movb disk, %dl

		int  $0x13
		jc   .exit     /* Carry = Fehler */

		sti

		/* JUMP!!! */
		ljmp $0x9000, $0x0000
    
		/* go into sleep mode */
.exit:

		/* teletype */
		movw $err, %si
.print2:
		lodsb			# AL = [DS:SI++]
		testb %al, %al
		jz .halt
		movb $0x0E, %ah
		xorw %bx, %bx	# BH=0 (page 0), BL=0
		int $0x10
		jmp .print2

.halt:
		cli
		hlt
		jmp .

disk:
		.byte 0

msg:
		.ascii "Loader Aktiv."
		.byte 0

err:
		.ascii "READ ERROR."
		.byte 0

		/* boot signature */
		.fill 510 - (. - _start), 1, 0
		.word 0xAA55
