BITS 16

%define KNL_SEGMENT   0x1000
%define STACK_SEGMENT 0x9000
%define STACK_TOP     0xFFFE

global start
global _print_string
extern _main


start:
    ; Optional: "long jump" / far jump zur Synchronisation
    jmp KNL_SEGMENT:entry

entry:
    cli
    mov ax, STACK_SEGMENT
    mov ss, ax
    mov sp, STACK_TOP
    sti

    mov ax, KNL_SEGMENT
    mov ds, ax
    mov es, ax
    
    call _main

hang:
    cli
    hlt
    jmp hang

; -----------------------------
; BIOS TTY print
; DS:SI -> 0-terminated string
_print_string:
	push si
	push ax
	push bx
	mov si, msg
.loop:
    lodsb
    test al, al
    jz .done
    mov ah, 0x0E
    mov bh, 0x00        ; page
    mov bl, 0x07        ; attribute (ignored in many BIOSes for 0Eh)
    int 0x10
    jmp .loop
.done:
	pop bx
	pop ax
	pop si
    ret

msg db "KERNEL OK", 13, 10, 0
