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
    
    finit 

    fild dword [x]
    fsqrt
    fistp dword [x]
    
    mov eax, [x]
    dprint
    
    mov     eax, 1
    int     0x80

section .data
    x dd 50
    

section .bss

    count resb 1
    
