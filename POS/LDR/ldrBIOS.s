
.code16gcc

#===========================================================
# void bios_putc(char c);
#===========================================================

.global bios_putc
bios_putc:
		pushl %ebp
		movl %esp, %ebp
		movw 8(%ebp), %ax
		movb $0x0E, %ah
		movb $0x12, %bl
		xorb %bh, %bh
		int $0x10
		popl %ebp
		ret

#===========================================================
# void bios_puts(char* sz);
#===========================================================

.global bios_puts
bios_puts:
		pushl %ebp
		movl %esp, %ebp
		movw 8(%ebp), %si
.l01:
		lodsb # AL = [DS:SI++]
		testb %al, %al
		jz .l02
		movb $0x0E, %ah
		xorw %bx, %bx	# BH=0 (page 0), BL=0
		int $0x10
		jmp .l01
.l02:
		popl %ebp
		ret

#===========================================================
# void bios_cursor(byte col, byte row, byte page);
#===========================================================

.global bios_cursor
bios_cursor:
		pushl %ebp
		movl %esp, %ebp
		movw 8(%ebp), %ax
		movb %al, %dl # row
		movw 12(%ebp), %ax
		movb %al, %dh # col
		movw 16(%ebp), %ax
		xorw %bx, %bx
		movb %al, %bh # page
		movw $0x0200, %ax	# AH=02h
		int $0x10
		popl %ebp
		ret

#===========================================================
# void bios_clear(byte x1, byte y1, byte x2, byte y2, byte color);
#===========================================================

.global bios_clear
bios_clear:
		pushl %ebp
		movl %esp, %ebp
		movw 8(%ebp), %ax
		movb %al, %cl # row1
		movw 12(%ebp), %ax
		movb %al, %ch # col1
		movw 16(%ebp), %ax
		movb %al, %dl # row2
		movw 20(%ebp), %ax
		movb %al, %dh # col2
		movw 24(%ebp), %ax
		movb %al, %bh # color attributes
		movw $0x0600, %ax	# AH=06h, AL=00h
		int $0x10
		popl %ebp
		ret
