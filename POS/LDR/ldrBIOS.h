#ifndef __LDRBIOS_H__
#define __LDRBIOS_H__

typedef unsigned char byte;

void bios_putc(char c);
void bios_puts(char* sz);
void bios_cursor(byte col, byte row, byte page);
void bios_clear(byte x1, byte y1, byte x2, byte y2, byte color);

#endif
