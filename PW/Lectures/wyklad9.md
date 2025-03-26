# Wykład 9

~ kolejny z cyklu nocnych wykładów z programowania współbieżengo

-----

## Producenci - konsumenci, I

```c
// początkowo przestrzeń krotek jest pusta

proces Producent(i) {
	int p;
	for (;;) {
		produkuj(&p);
		tsPut("%d", p);
	}
}

proces Konsument(i) {
	int p;
	for (;;) {
		tsFetch("?d", &p);
		konsumuj(p);
	}
}
```

## Producenci - konsumenci, II

```c
// początkowo przestrzeń krotek jest pusta

proces Producent(i) {
	int p;
	for (int ile=1;;ile++) {
		produkuj(&p);
		tsPut("%d %d", ile, p);
	}
}

proces Konsument(i) {
	int p;
	for (int ile=1;;ile++) {
		tsFetch("%d ?d", ile, &p);
		konsumuj(p);
	}
}

// Działa dla P=K=1 -> wtedy jest zachowana kolejność

// Dla P=K jest ok, ale nie ma zachowania kolejności

// Dla pozostałych -> źle. po prostu
```

## Producenci - konsumenci, III

```c
// ("lprod 1", "lkons 1")

proces Producent(i) {
	int p, ile;
	for (;;) {
		produkuj(&p);
		tsFetch("lprod ?d", &ile);
		tsPut("%d %d", ile p);
		tsPut("lprod %d", ile + 1)
	}
}

proces Konsument(i) {
	int p, ile;
	for () {
		tsFetch("lkons ?d", &ile);
		tsFetch("%d ?d", ile &p);
		tsPut("lkons ?d", ile + 1);
		konsumuj(p);
	}
}

// Działa 
```

## Producenci - konsumenci, IV

```c
// ("lprod 1", "lkons 1") oraz M krotek "miejsce"

proces Producent(i) {
	int p, ile;
	for (;;) {
		produkuj(&p);
		tsFetch("miejsce")
		tsFetch("lprod ?d", &ile);
		tsPut("%d %d", ile p);
		tsPut("lprod %d", ile + 1)
	}
}

proces Konsument(i) {
	int p, ile;
	for () {
		tsFetch("lkons ?d", &ile);
		tsFetch("%d ?d", ile &p);
		tsPut("lkons ?d", ile + 1);
		tsPut("miejsce")
		konsumuj(p);
	}
}

// Działa 
```

## Czytelnicy - pisarze, I

```c
// ("0")

proces Czytelnik(i) {
	int ile;
	for (;;) {
		tsFetch("?d", &ile);
		tsPut("%d", ile + 1);
		czytanie();
		tsFetch("?d", &ile);
		tsPut("%d" ile - 1);
	}
}

proces Pisarz(i) {
	for (;;) {
		tsFetch("%d", 0);
		pisanie();
		tsPut("%d", 0);
	}
}

// zagłodzenie pisarzy, ale bezpiecznie 
```

## Czytelnicy - pisarze, II

```c
// ("0 0")

proces Czytelnik(i) {
	int c, p;
	for (;;) {
		tsFetch("?d %d", &c, 0);
		tsPut("%d %d", c+1, 0);
		czytanie();
		tsFetch("?d ?d", &c, %p);
		tsPut("%d %d" c-1, p);
	}
}

proces Pisarz(i) {
	int c, p;
	for (;;) {
		tsFetch("?d ?d", &c, &p);
		if (c > 0) {
			tsPut("%d %d", c, p+1);
			tsFetch("%d ?d", 0, &p);
			tsPut("%d %d", -1, p);
		}
		else {
			tsPut("%d %d", -1, p+1)
		}
		pisanie();
		tsFetch("%d ?d", -1, &p);
		tsPut("%d %d", 0, p-1);
	}
}

// zagłodzenie czytelników + nie bezpieczne
```


## Czytelnicy - pisarze, III

```c
// ("0 0")

proces Czytelnik(i) {
	int c, p;
	for (;;) {
		tsFetch("?d %d", &c, 0);
		tsPut("%d %d", c+1, 0);
		czytanie();
		tsFetch("?d ?d", &c, %p);
		tsPut("%d %d" c-1, p);
	}
}

proces Pisarz(i) {
	int c, p;
	for (;;) {
		tsFetch("?d ?d", &c, &p);
		if (c > 0) {
			tsPut("%d %d", c, p+1);
			tsFetch("%d ?d", 0, &p);
			p = p - 1
		}

		pisanie();

		tsPut("%d %d", 0, p);
	}
}

// brak zagłodzenia, bezpieczne
// nowi pisarze mogą omijać kolejkę, ale w końcu każdy pisarz napisze i będzie (0, 0)
```

## Czytelnicy - pisarze, IV

```c
// ("0 0")

proces Czytelnik(i) {
	int c, p;
	for (;;) {
		tsFetch("?d %d", &c, 0);
		tsPut("%d %d", c+1, 0);
		czytanie();
		tsFetch("?d ?d", &c, %p);
		tsPut("%d %d" c-1, p);
	}
}

proces Pisarz(i) {
	int c;
	for (;;) {
		tsFetch("?d ?d", &c, 0);
		tsPut("%d %d", c, 1);
		tsFetch("%d ?d", 0, 1);

		pisanie();

		tsPut("%d %d", 0, 0);
	}
}

// bezpieczne i żywotne
```

## Czytelnicy - pisarze, V

```c
// ("0", "nie_piszę", "można")

proces Czytelnik(i) {
	int c;
	for (;;) {
		tsFetch("można");
		tsFetch("?d", &c);
		tsPut("%d", c+1);
		tsPut("można");
		tsRead("nie_piszę");
		czytanie();
		tsFetch("?d", &c);
		tsPut("%d", c-1);
	}
}

proces Pisarz(i) {
	for (;;) {
		tsFetch("można");
		tsRead("%d", 0);
		tsFetch("nie_piszę");
		tsPut("można");
		pisanie();
		tsPut("nie_piszę");
	}
}

// jest okay :D
```

# Wydajność algorytmów współbieżnych - notacja

Używamy języka C rozszerzonego o operacje do wyrażania współbieżności:
- spawn P(...) - wywołuje funkcję P(...) być może w nowym wątku wykonywanym równolegle z dotychczasowym
- sync - wstrzymuje wątek do chwili zakończenia wszystkich jego dzieci
- parallel for - jak zwykły for , ale poszczególne obroty mogą wykonywać się równolegle


- słowo kluczowe spawn oznacza logiczną równoległość - wywołania funkcji moe być osobne w różnym wątku
- Serializacja - rozwiązanie tego samego problemu, co oryginał


## Przykład

```c
int Fib(int n) {
	if (n <= 1) return n;

	int x = spawn Fib(n - 1);
	int y = Fib(n - 2);
	sync;
	return x + y;
}
```

# Wydajność

## Klasyfikacja krawędzi
- krawędź kontynuacji - pozioma
- krawędź rozmnażania - w dół 
- krawędź wywołania - w dół 
- krawędź powrotu - w górę 

## Miary wydajności 

Praca 
- łączny czas potrzebny do wykonania obliczenia na jednym procesorze
- łączna liczba wierzchołków O(1)
- czas potrzebny na serializację
- łączny czas wszystkich nici

Rozpiętość 
- łączny czas wykonania wszystkich nici na najdłuższej ścieżce

Przyspieszenie 
- ile razy obliczenie na $P$ procesoracg jest szybsze od wykonania na jednym procesorze, czyli $T_P / T_1$
- przyspieszenie doskonałe -> 1
- przyspieszenie liniowe -> O(P)

Równoległość oblczania
- "podatność" zadania na zrównoleglenie
- $T_1 / T_{\infty}$

Rzeczywisty czas wykoania obliczenia zależy od pracy i rozpiętości i od liczby dostępnych procesorów. Czas wykania na $P$ procesorach to $T_P$.

## Zasada pracy

$ T_P >= T_1 / P $

## Zasada rozpiętości

$ T_P >= T_{\infty} $

## Jak liczyć pracę

Praca sekwencyjnie i równolegle jest taka sama

$T_1(A \cup B) = T_1(A) + T_1(B)$

## Jak liczyć rozpiętość

Sekwencyjnie: \
$T_{\infty}(A \cup B) = T_{\infty}(A) + T_{\infty}(B)$

Równolegle: \
$T_{\infty}(A \cup B) = max(T_{\infty}(A), T_{\infty}(B))$


## Pętle równoległe

- parallel for można zrealizować metodą dziel i zwyciężaj

- powstaje drzewo binarne w którego liściach znajdują się pojedyncze iteracje

- drzewo ma wysokość O(log(n))

- zatem rozpiętość pęlni równoległej to \
$T_{\infty}(n) = O(log(n)) + max(iter_{\infty}(i))$ \
gdzie $iter(i)$ to rozpiętość i-tej iteracji
