OUTPUT_FORMAT(binary)
ENTRY(_start)

SECTIONS
{

	. = 0x0000;

	/* VBR layout template. assumes VBBR to 07C0:0000 */
	.vbrdata 0x0000 : {
		*(.vbrdata)
	}

	/* VBR loads LOADER to 7000:0000 */
	.text 0x0000 :
	{
		*(.text)
		*(.rodata)
		*(.data)
	}

	/* optional: discard everything else */
	/DISCARD/ :
	{
		*(.eh_frame)
		*(.comment)
		*(.note*)
	}

}
