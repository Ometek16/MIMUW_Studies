# Wykład

## Klasyczne problemy 

### Wzajemne wykluczanie

```py
while True:
	SL()
	protokół_wstępny()
	SK()
	protokół_końcowy()
```

- **Bezpieczeństwo** 
	- w sekcji krytycznej nigdy nie dojdzie do sytuacji, w której oba procesy są jednocześnie
- **Żywotność** 
	- każdy proces, który chce wejść do sekcji krytycznej, to w końcu do niej wejdzie (konieczna jest implikacja)

### Producenci i konsumenci

- *Producenci* produkują dane
- *Konsumenci* konsumują dane
- może być bufor
	- jednoelementowy
	- wieloelementowy
	- ograniczony
	- rozproszony
- porcje wkładane do bufora mogą mieć różny rozmiar
	- trzeba zadbać o żywotność, aby dało się włożyć dużą porcję

- **Bezpieczeństwo**
	- żadna porcja nie zgninie
	- konsument nie pobiera śmieci

- **Żywotność**
	- jeśli ktoś odbiera dane, to każdy producent, który wyprodukował porcję, przekarze ją pewnemu konsumentowi (symetrycznie dla konsumentów)

### Czytelnicy i pisarze

- *Czytelnicy* odczytują dane
- *Pisarze* zapisują dane
- Warianty
	- priorytety dla czytelników lub pisarzy (rezygnacja z żywotności)
	- ograniczenie na liczbę czytających
- Założenia
	- każdy pisarz skończy pisanie w skończonym czasie
	- każdy czytelnik kończy czytanie w skończonym czasie
	- czytelnicy powinni czytać współbieżnie

- **Bezpieczeństwo**
	- gdy ktoś pise, niekt inny ani nie pisze ani nie czyta
- **Żywotność**
	- każdy czytelnik, który chce przeczytać dane, w końcu będzie mógł to zrobić (symetrycznie pisarz)

### Problem pięciu filozofów

- siedzą przy okrągłym stole
- na środku stołu lerzy micha a w niej (ryba / ryż / makaron)
- między każdymi dwoma filozofami leży (widelec / pałeczka / widelec)

- *filozofowie* myślą, myślenie może się nie skończyć
- jak skończy myśleć, to głodnieje
- może wziąć tylko widolce obok niego
- *jedzienie* jest bezpieczne - zawsze się skończy

- **Bezpieczeństwo**
	- jedzenie może się zacząć tylko jak mamy oba widelce
	- nie można zacząć jeść jak nie mamy dwóch widelców

- **Żywotność** 
	- każdy filozof nie umrze z głodu

## Żywotność - problemy

- Zakleszczenie łatwo wykryć -> wszystko przestaje działać
- Zagłodzenie ciężej -> tylko jeden proces może nie działać
- LiveLock -> procesy ustępują sobie nawzajem

## Modele współbieżności

### Model scentralizowany

- Procesy mają dostęp do tej samej pamięci

Możliwości:
- Bez wsparcia ze strony środowiska ani środowiska
	- Algorytm Petersona

- Za pomocą specjalnego, niepodzielnego rozkazu maszynowego
	- Może istnieje fajniejszy algorytm - o tym później

- Za pomocą mechanizmów wspieranych przez system operacyjny i sprzęt -> semafory, monitory, etc.

### Model rozproszony

- Procesy nie mają wspólnej pamięci
- Wymieniają informacje przez komunikaty

# Semafory i Monitory

## Semafor Ogólny

- abstrakcyjny typ danych
- można go zainicjować (raz)
- *P* - próba przejścia przez Semafor	(WAIT)
- *V* - podniesienie (SIGNAL)
- Operacje *P* i *V* są niepodzielne i wykluczają się nawzajem
- Nie są dopuszczalne żadne inne operacje na semaforze
- Semafor to liczba całkowita nieujemna
- Semafor o wartości 0 jest zamknięty
- Semafor o wartości >0 jest otwarty
- Operacja P na zamkniętym semaforze powoduje wstrzymanie procesu na tym semaforze
- Operacja P na otwartym semaforze powoduje zmniejszenie semafora o 1


## Definicja klasyczna

```C++
semaphore S = ... /* >= 0*/

void P(S) {
	czejaj aż S > 0
	S--;
}

void V(S) {
	S++;
}

```

## Semafor słaby

```C++
void P(S) {
	if (S > 0)
		S--;
	else
		wstrzymaj wykonujący proces na semaforze S
}

void V(S) {
	if (jakiś proces czeka na semaforze)
		wznów ten proces
	else:
		S++;
}
```

## Semafor silny

```C++
// To samo co słaby, ale procesy czekają w kolejce FIFO
```

## **Semafor silnie uczciwy** -> będziemy przyjmowali tę definicję!

```C++
// Semafor słaby 
// 		+
// Jeśli operacja V będzie wykonana nieskończenie wiele razy, to w końcu każdy proces oczekujący na S zostanie wznowiony
```

## Semafor binarny

```C++
birnary semaphore S = ... /* 0 lub 1 */

void P(S) {
	if (S > 0) {
		S--;
	} else {
		zawieś się ()
	}
}

void V(S) {
	if (ktoś czeka) {
		obudź go;
	} else if (S == 0) {
		S = 1;
	} else {
		błąd;
	}
}
```

Semafor ogólny da się zastąpić trzema semaforami binarnymi
- SB mutex - realizuje wzajemne wykluczanie
- SB delay - służy do wstrzymania procesów
- int N - przechowuje wartość semafora / liczbę procesów
	- N > 0 -> wartość semafora
	- N <= 0 -> liczba czekających procesów
- początkowo
	- mutex = 1
	- delay = 0
	- N = ?

```C++

void P(S) {
	P(mutex);
	N--;
	if (N <= -1) {
		V(mutex);
		P(delay);		/* DSK */
	} 
	V(mutex);
}

void V(S) {
	P(mutex);
	N++;
	if (N <= 0) {
		V(delay);		/* DSK */
	} else {
		V(mutex);
	}
}
```

# Klasyczne problemy

### Wzajemne wykluczanie

```py
semaphore S = 1;

def Process():
	while True:
		SL()
		P(S)
		SK()
		V(S)
```

Bez żadnych założeń dotyczących kolejności wykonywania procesów to rozwiązanie nie jest żywotne!!!

Dla semafora słabego - dla dwóch procesów działa

Jeśli S jest semaforem silnym -> OK!

# Dziedziczenei sekcji krytycznej

- proces, który kogoś budzi nie podnosi semafora
- obudzony proces zastaje zamknięty semafor - dziedziczy sekcję krytyczną
- musi go podnieść w imieniu procesu, który go obudził
- między obudzeniem a faktycznym wznowieniem procesu nic nie może się wykonać

```py

def prot_wstepny():
	P(mutex)
	if (trzeba poczekac):
		ilu_czeka += 1
		V(mutex)
		P(delay)
		ilu_czeka -= 1
	V(mutex)

def prot_koncowy():
	P(mutex)
	if (mozna kogos wznowic and ilu_czeka > 0):
		V(delay)
	else:
		V(mutex)

```
