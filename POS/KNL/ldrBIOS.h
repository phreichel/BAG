#ifndef LDRBIOS_H
#define LDRBIOS_H

typedef unsigned char byte;

// void bios_putc(char c);
void bios_putc();

// void bios_puts(char* sz);
void bios_puts();

// void bios_cursor(byte col, byte row, byte page);
void bios_cursor();

// void bios_clear(byte x1, byte y1, byte x2, byte y2, byte color);
void bios_clear();

#endif
