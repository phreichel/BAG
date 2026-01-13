#include "ldrBIOS.h"

void main() {

	int i;

	bios_clear(0, 0, 79, 24, 0x12);

	for (i=0; i<10; i++) {
		bios_cursor(0, i, 0);
		bios_puts("HELLO, WORLD!!!!");
	}

}
