# Wykład 8

# Zdalne wywołanie procedury

```C
proces P {
	send Q.3+4;
}

proces Q {
	int y;
	receive Q.y;
}
```

```C
proces P {
	int x, y, z;
	send Q.ln(3+4, y, z);
	receive Q.Out(y, z);
}

proces Q {
	int x, y, z;

	receive P.ln(x, y, z)
	f(x, &y, &z);
	P.Out(y, z);
}
```

# Abstrakcja asynchroniczna

- buffer - nieskończona kolejka
- SendMessage(b, k) -> umieszcza komunikat na końcu kolejki
- GetMessage(b, k) -> pobiera pierwszy element kolejki, a jeśli jest pusta to wstrzymuje proces

- Getmessage jest niedeterministyczny i uczciwy

## Przykłady - wzajemne wykluczanie

```C
buffer b; // początkowo ma 1 wartość

proces P1, P2 {
	int k;
	while (true) {
		wlasne_sprawy();
		GetMessage(b, k);
		sekcja_krytyczna();
		SendMessage(b, k);
	}
}


```

## Przykłady - pięciu filozofów

```C

proces Fil(i: 0..4) {
	while (true) {
		myślę();
		GetMessage(b[i], m);
		GetMesssage(b[(i + 1) % 5], m);
		jem();
		SendMessage(b[i], m);
		SendMessage(b[(i + 1) % 5], m);		
	}
}

// zakleszczenie
```

```C

proces Fil(i: 0..4) {
	int k;
	while (true) {
		myślę();
		Sendmessage(w[i], i);
		SendMesssage(w[(i + 1) % 5], m);
		GetMessage(f[i], k);
		GetMessage(f[i], k);
		jem();
		SendMessage(b[i], m);
		SendMessage(b[(i + 1) % 5], m);		
	}
}

proces Wid(i: 0..4) {
	int k1, k2=0, ktoje=0;
	while (true) {
		GetMessage(w[i], k1);
		if (k2 == ktoje) {
			ktoje = k1;
		}
		else {
			ktoje = k2;
		}
		SendMessage(f[ktoje], i);
		GetMessage(w[i], k2);
	}
}

// też zakleszczenie XD
```

### Poprawne rozwiązania

-> jeden leworęczny
-> lokaj i 4 bilety -> 4 bilety, 5 widelców - ktoś musi jeść

# Linda

Linda - kolejna realizacja komunikacji asynchronicznej, procesy nie są związane ani w czasie ani w przestrzeni

## Krotka

Krotka (n-krotka) to ciąg danych o typach danych
Sygnatura krotki to ciąg typów poszczególnych składowych
	(5, 3.14, 'c', 20)	-> (int, double, char, int)

Przestrzeń krotek - to środowisko z którego proces pobiera dane i do którego wysyła wyniki


Procesy komunikują się tylko z przestrzenią krotek.
O lindzie myślimy jak o bibliotece

- tsPut			- wkłada do kolejki
- tsFetch		- próbuje wziąć, jak sie nie da, to czeka
- tsRead		- próbuje czytać, jak się nie da to czeka
- tsTryFetch	- próbuje wziąć, jak sie nie da to idzie dalej
- tsTryRead		- próbuje czytać, jak się nie da to idze dalej

```C
void tsPut(const char* fmt);

tsPut("%d %f %c %d", 5, 3.14, 'c', 20-3);
tsPut("KROTKA %d", 3*4);

// krotka dziurawa
tsPut("%d %f ?c %d", 3.14, r, 2);	

void tsFetch(const char* fmt);

int x, w; double y, char z;
tsFetch("?d ?f ?c ?d", &x, &y, &z, &w);
tsFetch("%d ?f %c ?d", 5*5, &y, 'a', &w);
```

Pierwszy argument krotki to PID adresata
Drugi argumnent krotki to PID nadawcy
Kolejne argumenty zależą od specyfiki zadania.

selektywny Fetch:
put do dowolnego procesu

```c
tsFetch("%d ?d ?d", 25, &nadawca, &liczba)
tsPut("?d %d %d", 18, liczba)

```
