OUTPUT_FORMAT(binary)
ENTRY(_start)

SECTIONS
{
    /* MBR lädt LOADER nach 0x9000:0x0000 */
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
