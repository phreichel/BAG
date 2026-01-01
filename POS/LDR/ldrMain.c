#include "ldrBIOS.h"

void main(void) {

	bios_cursor(0, 0, 0);
	bios_clear(0, 0, 79, 24, 0x1e);
	bios_puts("HELLO!!!!!");
	bios_cursor(0, 1, 0);

	while (0) {};

}
