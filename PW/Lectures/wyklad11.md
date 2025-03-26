# Wykład 11 

# Uzgadnianie - problem Bizantyjskich generałów
- Jest n węzłów, z czego co najwyżej m działa wadliwie
- Każdy węzeł obliczył pewną wartość...
- ... i ma ustalić co obliczyły pozostałe węzły
	- wiedza każdego dobrego węzła o każdym dobrym węźle musi być dokładna
	- wiedza dobrych o każdym węźle musi być taka sama
- Uzgadnianie odbywa się poprzez wymianę komunikatów

---

- Niech $v_i$ oznacza wartość obliczoną przez $i$-ty węzeł
- Każdy węzeł $i$ ma wyznaczyć wektor (funkcję) $V_i$
- Wartość $V_i(j)$ jest tym co węzeł $i$-ty ustali na temat wartości $v_j$ przez węzeł $j$-ty

---

Muszą być spełnione następujące warunki:
- jeśli węzły $i$-ty i $j$-ty działają dobrze, to: \
	$V_i(j) = v_j$
- jeśli węzły $i$-ty i $j$-ty działają dobrze, to: \
	$V_i(k) = V_j(k)$ dla każdego $k$

---

Komunikacja P i Q:
- jeśli P lub Q jest wadliwy, nie możemy nic założyć o uczciwości komunikacji
- jeśli P i Q są dobre, to komunikat będzie poprawny

---

$V_2(1) = f_2(v_{1_a}', v_{1,3}') = v_{1_a}'$ \
$V_3(1) = f_3(v_{1_b}', v_{2,3}') = v_{1_b}'$ \
Jeśli $1$ jest wadliwy, to $V_2(1) \neq V_3(1)$ -> sprzeczność.

---

## Warunek konieczny: n > 3 * m

---



