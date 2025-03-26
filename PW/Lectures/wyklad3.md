# Wykład 3

## Pięciu filozofów - werja 1

```c++
binSem w[4] = {1, 1, 1, 1};	// widelce

proces F(i: 0..4) {
	for (;;) {
		myśli();
		P(w[i]);
		P(w[(i + 1) % 5]);
		je();
		V(w[i]);
		V(w[(i + 1) % 5]);
	}
}
```
Nie działa -> widoczne zakleszczenie

## Pięciu filozofów - wersja 2

```c++
enum Stan {Myśli, Głodny, Je};
Stan stan[5] = {Myśli, Myśli, Myśli, Myśli, Myśli};
binSem f[5] = {0, 0, 0, 0, 0};

proces F(i: 0..4) {
	for (;;) {
		myśli();
		stan[i] = Głodny;
		sprawdz(i);
		P(f[i]);
		je(i);
		stan[i] = Myśli;
		sprawdz((i + 1) % 5);
		sprawdz((i - 1) % 5);
	}
}

void sprawdź(k: 0..4) {
	if ((stan[k] == Głodny) && stan[(k - 1) % 5] != Je && stan[(k + 1) % 5] != Je) {
		stan[k] = Je;
		V(f[k]);
	}
}
```
nie jest nawet bezpieczne!! Bo 'sprawdź' nie jest atomowe.

## Pięciu filozofów - wersja 3

```c++
enum Stan {Myśli, Głodny, Je};
Stan stan[5] = {Myśli, Myśli, Myśli, Myśli, Myśli};
binSem f[5] = {0, 0, 0, 0, 0};
binSem mutex = 1;

proces F(i: 0..4) {
	for (;;) {
		myśli();
		stan[i] = Głodny;
		P(mutex);
		sprawdz(i);
		V(mutex);
		P(f[i]);
		je(i);
		P(mutex);
		stan[i] = Myśli;
		sprawdz((i + 1) % 5);
		sprawdz((i - 1) % 5);
		V(mutex);
	}
}

void sprawdź(k: 0..4) {
	if ((stan[k] == Głodny) && stan[(k - 1) % 5] != Je && stan[(k + 1) % 5] != Je) {
		stan[k] = Je;
		V(f[k]);
	}
}
```
Bezpieczne, ale są zagłodzenia.

## Pięciu filozofów - wersja 4

```C++
binSem w[5] = {1, 1, 1, 1, 1};
sem lokaj = 4;

proces F(i : 0..4) {
	for (;;) {
		myśli();
		P(lokaj);
		P(w[i]);
		P(w[(i + 1) % 5]);
		je();
		V(w[(i + 1) % 5]);
		V(w[i]);
		V(lokaj);
	}
}
```
POPRAWNE ROZWIAZANIE

## Pięciu filozofów - wersja 5

4 filozofów jest prawo-ręcznych
1 filozof jest lewo-ręczny

POPRAWNE ROZWIAZANIE

# Rodzaje semaforów

- semafory uogólnione
- semafory dwustronnie ograniczone
- semafowy agerwali
- semafory uniksowe
- semafory OR, AND

## Semafor uogólniony

P(S, n) -> czekaj az S >= n -> S -= n
V(S, n)	-> S += n

Co ma się stać, gdy S=3 i ktoś wisi na P(S, 4) a mamy nagle P(S, 2) ?? -> mogłoby być zagłodzenie

## Semafory dwustronnie ograniczone

Semafor przyjmuje wartości z przedziału [0, M] gdzie M jest podane przy inicjacji.

Dzięki temu zarówno P i S mogą wstrzymywać i budzić.

## Semafory Agerwali

S1, ..., Sn - Semafory ogólne

A, B $\subseteq$ {S1, ... Sn}, A $\cap$ B = 0

PA(A, B):
	czekaj aż:
		dla kazdego S $\subseteq$ A będie S > 0
		dla kazdego S $\subseteq$ B bedzie S == 0
	for S $\subseteq$ A:
		S--;

VA(A):
	for S $\subseteq$ A:
		S++;

	
## Semafory uniksowe

Tworzymy segment pamieci dzielonej i podpinamy go do tablicy stron dwoch procesow

Tworzymy zestaw semaforów (semget)
W zestawie może być wiele semaforów

Jednym wywołaniem funkcji systemowej (semop) można wykonać w sposób niepodzielny wiele operacji na semafforach z tego samego zestawu

Operacje:
	P(n)
	V(n)
	wstrzymać proces w oczekiwaniu na zamknięcie semafora ozn. Z
	Z oczekuje aż semafor będzie zamknięty

Operaja P występuje w wersji blokującej i nieblokującej

Można podglądać wartość semafora

Operacja P może powodować zagłodzenie.

## Czytelnicy i pisarze - wersja 1

```c++

USem czytelnicy = 0;
USem pisarze = 0;

proces Czytelnik() {
	for (;;) {
		Z(pisarze);
		V(czytelnicy, 1);
		czytaj();
		P(czytelnicy, 1);
	}
}

proces Pisarz() {
	for (;;) {
		Z(pisarze);
		Z(czytelnicy);
		V(pisarze);
		pisz();
		P(pisarze);
	}
}
```
Źle! nie jest bezpiecznie

## Czytelnicy i pisarze - wersja 2

```c++

USem czytelnicy = 0;
USem pisarze = 0;

proces Czytelnik() {
	for (;;) {
		[Z(pisarze), V(czytelnicy, 1)];
		czytaj();
		P(czytelnicy, 1);
	}
}

proces Pisarz() {
	for (;;) {
		[Z(pisarze), Z(czytelnicy), V(pisarze)];
		pisz();
		P(pisarze);
	}
}
```
Źle -> pisarz może być zagłodzony

## Czytelnicy i pisarze - wersja 3

```c++

USem czytelnicy = 0;
USem drzwi = 1;

proces Czytelnik() {
	for (;;) {
		[P(drzwi, 1), V(drzwi, 1), V(czytelnicy, 1)];
		czytaj();
		P(czytelnicy, 1);
	}
}

proces Pisarz() {
	for (;;) {
		P(drzwi, 1);
		Z(czytelnicy);
		pisz();
		V(drzwi, 1);
	}
}
```
POPRAWNE ROZWIAZANIE


# Monitory

Moduł programistyczny
	- udostępniający na zewnątrz pewne procedury i funkcje
	- ukrywający wszystkie zmienne, stałe i typy

Mechanizm synchronizacyjny
	- w danej chwili co najwyzej jeden proces wykonuje procedure lub funkcje monitora (jest w monitorze)
	- analogia do tradycyjnego, jednowejsciowego jądra systemu operacyjnego w którym mógł wykonać się w trybie uprzywilejowanym co najwyżej jeden proces

```c++
monitor nazwa_monitora {
	definicje stałych i typów;
	deklaracje zmiennych lokalnych;
	deklaracje funkcji prywatnych;
	deklaracje funkcji publicznych;

	inicjacja monitora;
}
```

Uwagi:
	- inicjacja monitora przez pierwszym wystąpieniem
	- inicjacja jedynie ustala wartosci poczatkowe
	- zmienne nie sa dostepne z zewnatrz
	- monitor z definicji jest funkcją krytyczną
	- nie potrzeba ochrony zmiennych
	- wzajemne wykluczanie na poziomie całego monitora

Monitor gwarantuje ochrone zmiennych globalnych

```java

monitor sekcja {
	public krytyczna() {
		// tutaj treść sekcji krytycznej
	}
}

proces P() {
	while (true) {
		wlasne_sprawy();
		sekcja.krytyczna();
	}
}
```

Zmienne warunkowe:
	condition c;

wait(c) - bezwarunkowe wstrzymanie procesu wyknujacy te operacje. proces ten opuszcza monitor

signal(c) - obudzenie jednego procesu oczekuje na wykonanie zmiennej warynckowej c. Wzniowiony porces kontynuje na instrukcji po wait(c)

empty(c) - przekazuje w wyniku true wtedy i tylko wtedy, gdy zaden proces nie czeka na zmiennej warunkowej c

```java 
public ProtokóaWstepny (. . . ) { 
	...
	if (trzeba poczeka¢) wait (c);
	// dalszy ci¡g protokoau wstepnego 
	// najczesciej bez else
	...
}
public ProtokóaKoncowy (. . . ) { ...
	if (mozna obudzic proces) signal (c);
}
```

