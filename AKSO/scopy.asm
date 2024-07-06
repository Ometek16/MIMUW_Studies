global _start

section .rodata
	SYS_READ equ 0
	SYS_WRITE equ 1
	SYS_OPEN equ 2
	SYS_CLOSE equ 3
	SYS_EXIT equ 60

	O_RDONLY equ 0
	O_WRONLY equ 1
	O_CREAT equ 64
	O_EXCL	equ 128

	VALID_PARAMS_NUMBER equ 3
	PERMISSION_CODE equ 644o

	BUFFER_SIZE equ 4096
	BUFFER_WARNING equ (BUFFER_SIZE - 2)
	FORCE_WRITE equ -12345

	LETTER_SMALL_S equ 115
	LETTER_BIG_S equ 83

section .bss
	READ_BUFFER: resb (BUFFER_SIZE + 2)
	WRITE_BUFFER: resb (BUFFER_SIZE + 2)

section .text

_start:
	mov r8, -1
	mov r9, -1
							; checks if (#params == 2)
	mov rax, [rsp]
	cmp rax, VALID_PARAMS_NUMBER
	jne .exit_error
							; open first file
	mov rax, SYS_OPEN
	mov rdi, [rsp + 16]
	mov rsi, O_RDONLY
	syscall
	
	mov r8, rax				; holds handle to the first file 

	cmp rax, 0
	jl .exit_error
							; create second file
	mov rax, SYS_OPEN
	mov rdi, [rsp + 24]
	mov rsi, O_WRONLY
	xor rsi, O_CREAT
	xor rsi, O_EXCL
	mov rdx, PERMISSION_CODE
	syscall
	
	mov r9, rax				; holds handle to the second file

	cmp rax, 0
	jl .exit_error

	xor r15, r15 			; holds #non-s-letters in a row
	xor r10, r10			; iterator through WRITE_BUFFER

.file_loop:					; while there are letters to be read
	mov rax, SYS_READ
	mov rdi, r8
	mov rsi, READ_BUFFER
	mov rdx, BUFFER_SIZE
	syscall

	cmp rax, 0				; checks if there is anything left to read
	jl .exit_error
	je .end_file_loop

	mov r13, rax			; holds the number of characters read 
	xor r12, r12			; iterator through READ_BUFFER

.buffer_loop:				; loops through all letters in the buffer 		
							; holds current letter
	mov r14, [READ_BUFFER + r12]
	and r14, 0FFh

							; checking if letter is s/S
	cmp r14, LETTER_SMALL_S
	je .letter_is_s
	cmp r14, LETTER_BIG_S
	je .letter_is_s
	jmp .letter_not_s

.letter_is_s:
	cmp r15, 0				; checking if should print the #non-s-letters
	je .print_letter_s

	mov word [WRITE_BUFFER + r10], r15w
	add r10, 2
	xor r15, r15
	
	call writeToFile
	
	cmp rax, 0
	jl .exit_error

.print_letter_s:
							; prints letter S/s
	mov byte [WRITE_BUFFER + r10], r14b
	inc r10
	
	call writeToFile

	cmp rax, 0
	jl .exit_error

	jmp .continue

.letter_not_s:
	inc r15

.continue:
	inc r12
	cmp r12, r13
	jl .buffer_loop

	jmp .file_loop

.end_file_loop:
	cmp r15, 0
	je .exit_good
							; prints #non-s characters (if any)
	mov word [WRITE_BUFFER + r10], r15w
	add r10, 2

.exit_good:
	mov rdx, FORCE_WRITE 
	call writeToFile
	
	cmp rax, 0
	jl .exit_error

	call closeFiles

	cmp rax, 0
	jl .files_closed_error_exit

	mov rax, SYS_EXIT
	xor rdi, rdi
	syscall

.exit_error:
	call closeFiles

.files_closed_error_exit:
	mov rax, SYS_EXIT
	mov rdi, 1
	syscall

;;;;;;;;;;;;;;;;;;;;;;;;
; Procedury pomocnicze ;
;;;;;;;;;;;;;;;;;;;;;;;;

closeFiles:
	xor r10, r10

	cmp r8, 0
	jl .closeOtherFile

	mov rax, SYS_CLOSE
	mov rdi, r8
	syscall
	
	cmp rax, 0
	jge .closeOtherFile

	mov r10, -1

.closeOtherFile:
	cmp r9, 0
	jl .finish

	mov rax, SYS_CLOSE
	mov rdi, r9
	syscall

	cmp rax, 0
	jge .finish

	mov r10, -1

.finish:
	mov rax, r10
	ret

writeToFile:
	push r12
	xor r12, r12
	
	cmp rdx, FORCE_WRITE
	je .force_write

	cmp r10, BUFFER_WARNING
	jl .finish
	
.force_write:
	mov rax, SYS_WRITE
	mov rdi, r9
	lea rsi, [WRITE_BUFFER + r12]
	mov rdx, r10
	syscall
	
	cmp rax, 0
	jl .finish
							; makes sure the entire buffor will be written
	add r12, rax
	sub r10, rax
	cmp r10, 0
	jg .force_write
	
	xor r10, r10

.finish:
	pop r12
	ret
