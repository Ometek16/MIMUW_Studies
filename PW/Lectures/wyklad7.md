# Wykład 7

# Realizacja wymiany komunikatów

- scentralizowany
	- ze wspólną pamięcią

- zdecentralizowany
	- bez wspólnej pamięci

- synchroniczna vs asynchroniczna - procesy mogą synchronizować się ze sobą na czas komunikacji (jak przy rozmowie telefonicznej) lub nie (email)

- symetryczna vs asymetryczna - procesy znaja nawzajem swoje identyfikatory lub tylko jeden proces zna identyfikator drugiego (klient-serwer)

- pośrednia vs bezpośrednia

# komunikaty

- procesyy porozumiewają się ze sobą za pomocą komunikatów

-> model synchroniczny, symetryczny, rozproszony

- wysyłając komunikat wskazujemy proces do ktorego komunikat jest skierowany

- nadawda jest wstrzymywany do chwili gdy odbiorca bedzie gotowy do jego odbioru

- argumentami komunikaty są wyrażenia

- próba komunikacji z procesem który się zakonczył jest błędem

send Bufor.5	
send Czytelnia.Chcę()	
send P.Dodaj(x, y + 3)	
send Q.(2, 3)	

- odbierając komunikat można wskazać nadawcę
- odbiorca jest wstrzymywany do chwili gdy nadawca będzie gotowy do jej wysłania
- parametrami komunikatu są lokacje (zmienne), w których zostaną umieszczone poszczególne argumenty komunikatu

recieve x
recieve Chcę()
recieve Q.Dodaj(x, y)
recieve P.z

- synchronizacja odbiorcy i nadawcy 

```C
process Semafor {
	while (1):
		recieve P()
		recieve v()
}
// binarny, dwustronnie ograniczony
```

---

```C
process A {
	for (int i = 1; i < 6; i++) {
		send Sumator.i;
	}
}

process B {
	for (int i = 1; i <10; i++) {
		send.Sumator.i*i;
	}
}

process Sumator {
	int suma = 0;
	for (;;) {
		select {
			on A.x
				suma += x;
			on B.x 
				suma += x;
		}
	}
}
```

```C
proces Zlicz {
	int ile = 0;
	for (;;) {
		select {
			on Dodaj()
				ile += 1;
			on Odejmij()
				ile -= 1;
		}
	}
}
```

---

- przed każą pułapką (on) może być dozór (if)
- wyrażenie nie może powołować efektów ubocznych
- pułapka jest aktywna wtedy i tylko wtedy gdy wyrażenie jest prawdziwe
- zakładamy uczciwość

```C
process Semafor {
	int w;
	recieve Inicjuj(w);
	for (;;) {
		select {
			on V() w++;
			if (w > 0) on P() w--;
		}
	}
}
// to już jest semafor uczciwy
```


--- 

# tablica procesów

```
define N 100 
define M 100

process Generuj[nr: 2..N] {
	for (int i = 1; i <= M; i++) {
		send Zbieraj.Daj(nr * i);
	}
}

process Zbieraj {
	for (;;) {
		select {
			for (i in 2..N) on Generuj[i].Daj(x) send Printer.x*x
		}
	}
}

process Zbieraj2 {
	for (;;) {
		select {
			on Daj(x) send Printer.x*x
		}
	}
}
```

---
# Problemy synchronizacyjne

W problemach synchronizacyjnych będzie jeden dodatkowy proces - serwer, który będzie obsługiwał chęci synchronizacji

```C

proces Czytelnia {
	int czytajacy = 0;
	for (;;) {
		select {
			on czytam() {
				czytający++;
			}
			on koniecCzytania() {
				czytający--;
			}
			if (czytający == 0) {
				on piszę() {
					recieve koniecPisania();
				}
			}
		}
	}
}

// bezpiecznie, ale nie żywotnie
```

```C

process Pisarz [i: 2..P] {
	for (;;) {
		send Czytelnia.chcęPisać();
		recieve możeszPisać();
		pisanie();
		send Czytelnia.koniecPisania();
	}
}

proces Czytelnia {
	int czytajacy = 0;
	for (;;) {
		select {
			on czytam() {
				czytający++;
			}
			on koniecCzytania() {
				czytający--;
			}
			for (i in 1..P) {
				on Pisarz[i].chcęPisać() {
					while (czytający > 0) {
						recieve koniecCzytania();
						czytający--;
					}
					send Pisarz[i].możeszPisać();
					recieve koniecPisania();
				}
			}
		}
	}
}

// bezpiecznie i żywotnie
```

