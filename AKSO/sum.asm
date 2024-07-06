global sum

section .text

sum:
													; rdi -> poczatek tablicy x
													; rsi -> n

	mov r8, 1										; r8 -> i = 1

												;! Przechodzimy po kolejnych elementach tablicy x
.for_loop:											; for (i = 1; i < n; i++)
	cmp r8, rsi										;	if (i >= n)
	jge .end_for									;		break

	mov qword r9, [rdi + 8 * r8]					; r9 -> x[i]

													; x[i] = (x[i - 1] < 0 ? -1 : 0)
	mov rax, [rdi + 8 * r8 - 8]
	mov qword [rdi + 8 * r8], rax
	sar qword [rdi + 8 * r8], 63

												;! Obliczamy docelowe przesunięcie
													; rax -> łącza liczba bitów = (64 * i * i) / n
	mov rax, r8
	imul rax, r8
	imul rax, 64
	cqo
	idiv rsi

													; rdx -> move_word = liczba pełnych słów 64-bitowych
	mov rdx, rax
	shr rdx, 6

	and rax, 63										; rax -> move_bits = pozostała liczba bitów 

												;! Ustalamy część liczby x[i] dodawaną do dolnej części
													; rax -> partLo = x[i] << move_bits
	mov rcx, rax									; rcx -> move_bits  
	mov rax, r9
	shl rax, cl

												;! Ustalamy część liczby x[i] dodawaną do górnej części
													; r9 -> partHi = x[i]

												;! Obsluga move_bits = 0
	cmp rcx, 0										; if (move_bits == 0)
	jz .zero_move_bits								; 	* wyjątkowe obsługiwanie tego przypadku *

	neg rcx											; rcx -> 64 - move_bits
	add rcx, 64
												;! Obsluga normalnego przypadku
													; r9 -> partHi = x[i] >> (64 - move_bits)
	sar r9, cl

	jmp .no_zero_move_bits

												;! Obsługa wyjątku wspomnianego wcześniej
.zero_move_bits:
													; r9 -> partHi = (x[i] >= 0 ? 0 : -1)
	sar r9, 63

.no_zero_move_bits:
												;! Zauktualizowanie dolnej części
	add qword [rdi + 8 * rdx], rax					; x[move_word] += partLo

	lahf											; zapisanie flag (w rax)

	inc rdx

												;! Zakutalizowanie górnej części
	cmp rdx, r8										; if (move_word > i)
	jg .end_while									;	break

	sahf											; odczytanie flag

	adc qword [rdi + 8 * rdx], r9					; x[move_word + 1] += partHi + CARRY
	mov qword r9, [rdi + 8 * rdx]					; r9 -> partHi = x[move_word + 1]

	inc rdx											; move_word++

												;! Ustawienie znaku wyniku (do i-tej pozycji włącznie)
.loop_while:										; while (move_word <= i)
	cmp rdx, r8										;	if (move_word > i)
	jg .end_while									;		break

													; x[move_word] = (partHi < 0 ? -1 : 0)
	mov qword [rdi + 8 * rdx], r9
	sar qword [rdi + 8 * rdx], 63

	inc rdx											; rdx -> move_word++;
	jmp .loop_while

.end_while:

	inc r8											; r8 -> i++;
	jmp .for_loop

.end_for:
	ret