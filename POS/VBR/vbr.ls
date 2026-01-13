OUTPUT_FORMAT(binary)
ENTRY(_start)

SECTIONS
{
    /* BIOS lädt MBR nach 0x7C00 */
    . = 0x0000;

    .text :
    {
        *(.text)
        *(.rodata)
        *(.data)
    }

    /* optional: alles andere verwerfen */
    /DISCARD/ :
    {
        *(.eh_frame)
        *(.comment)
        *(.note*)
    }

}
