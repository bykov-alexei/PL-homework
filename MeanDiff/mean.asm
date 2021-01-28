%macro pushd 0
    push edx
    push ecx
    push ebx
    push eax
%endmacro

%macro popd 0
    pop eax
    pop ebx
    pop ecx
    pop edx
%endmacro

%macro print 2
    pushd
    mov edx, %1
    mov ecx, %2
    mov ebx, 1
    mov eax, 4
    int 0x80
    popd
%endmacro

%macro dprint 0
    pushd
    
    mov ecx, 10
    mov bx, 0   
    
    %%_divide:
        mov edx, 0
        div ecx
        push dx
        inc bx
    test eax, eax
    jnz %%_divide 
        
    mov cx, bx
        
    %%_digit:
        pop ax
        add ax, '0'
        mov [count], ax
        print 1, count
        dec cx
        mov ax, cx
    cmp cx, 0
    jg %%_digit
    
    popd
%endmacro

section .text

global _start

_start:

    mov eax, 0
    mov bx, 0
    

    _loop:
        add eax, [x+ebx] 
        sub eax, [y+ebx]
        add bx, 4        
    cmp bx, n
    jne _loop
    
    cmp eax, 0
    jge _positive

    print len, minus
    xor eax, 0xFFFFFFFF
    add eax, 1
    
_positive:
    
    mov ebx, 4
    mul ebx
    
    mov ecx, n
    div ecx
    
    dprint
    print nlen, newline

    mov     eax, 1
    int     0x80

section .data
    x dd 5, 2, 2, 6, 1, 7, 4
    n equ $ - x
    y dd 0, 10, 1, 9, 2, 8, 5
    
    minus db "-"
    len equ $ - minus
    newline db 0xA, 0xD
    nlen equ $ - newline

section .bss

    count resb 1
    
