		/* Set 16bit format and ip origin */
		.code16gcc
		.global _start

		/* long jump to correct memory address */
_start:
		ljmp $0x9000, $.start

		/* real program start */
.start:

		/* set important registers with interrupts disabled */
		cli
		movw $0x9000, %ax
		movw %ax, %ss
		movw %ax, %ds
		movw %ax, %es
		movw $0xffff, %sp
		sti

		call main
    
		/* go into sleep mode */

		cli
		hlt
		jmp .
