# Wykład 4

# Semantyka signal

## Per Brinch Hansen

- signal musi być **ostatnią** operacją
- proces sygnalizujący wychodzi z monitora
- procesy budzone mają priorytet przed oczekującymi na wejście do monitora **(warunek natychmiastowego wznowienia)**

Wady:
- nie zawsze da się rozwiązać mając signal na końcu
- na przykład proces przed wstrzymaniem wykonania musi kogoś obudzić (siganl, wait) - bo czeka az inny proces coś dla niego zrobi

# Wzajemne wykluczanie

```c++

monitor SK {
	int iluwsekci = 0;
	condition wolna;

	public chceWejsc() {
		if (iluwsekcji > 0)
			wait(wolna)
		iluwsekcji = 1;
	}

	public wychodze() {
		iluwsekcji = 0;
		signal(wolna)
	}
}
```

## C.A.R. Hoare
- proces sygnalizujący wychodzi z monitora i ustawia się na początku kolejki oczekujących na wejście do monitora
- jego miejsce w monitorze zajmuje obudzony proces
- procesy budzone mają priorytet przed oczekującymi na wejście do monitora **(warunek natychmiastowego wznowienia)**


```c++

monitor SK {
	int iluwsekci = 0;
	condition wolna;

	public chceWejsc() {
		if (iluwsekcji > 0)
			wait(wolna)
		iluwsekcji = 1
	}

	public wychodze() {
		signal(wolna)
		iluwsekcji = 0
	}
}
// Źle!! Błędne rozwiązanie w obu semantykach
// zamiast 1 i 0 -> ++ i -- -> byłoby dobrze
```
