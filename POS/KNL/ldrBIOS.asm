BITS 16

section .text

;===========================================================
; void bios_putc(char c);
;===========================================================

global _bios_putc
_bios_putc:
    push bp
    mov  bp, sp
    mov  ax, [bp+2]
    mov  ah, 0x0E
    mov  bl, 0x12
    xor  bh, bh
    int  0x10
    pop  bp
    ret

;===========================================================
; void bios_puts(char* sz);
;===========================================================

global _bios_puts
_bios_puts:
    push bp
    mov  bp, sp
    mov  si, [bp+4]

.l01:
    lodsb                  ; AL = [DS:SI++]
    test al, al
    jz   .l02
    mov  ah, 0x0E
    xor  bx, bx            ; BH=0 (page 0), BL=0
    int  0x10
    jmp  .l01

.l02:
    pop  bp
    ret

;===========================================================
; void bios_cursor(byte col, byte row, byte page);
;===========================================================

global _bios_cursor
_bios_cursor:
    push bp
    mov  bp, sp

    mov  ax, [bp+4]
    mov  dl, al            ; row

    mov  ax, [bp+6]
    mov  dh, al            ; col

    mov  ax, [bp+8]
    xor  bx, bx
    mov  bh, al            ; page

    mov  ax, 0x0200        ; AH=02h
    int  0x10

    pop  bp
    ret

;===========================================================
; void bios_clear(byte x1, byte y1, byte x2, byte y2, byte color);
;===========================================================

global _bios_clear
_bios_clear:
    push bp
    mov  bp, sp

    mov  ax, [bp+4]
    mov  cl, al            ; row1

    mov  ax, [bp+6]
    mov  ch, al            ; col1

    mov  ax, [bp+8]
    mov  dl, al            ; row2

    mov  ax, [bp+10]
    mov  dh, al            ; col2

    mov  ax, [bp+12]
    mov  bh, al            ; color

    mov  ax, 0x0600        ; AH=06h, AL=00h
    int  0x10

    pop  bp
    ret
