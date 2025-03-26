# Będziemy sobie programować

**Wykonanie współbieżne** polega na tym, że kolejna czynność rozpoczyna się przed zakończeniem poprzedniej.

**Program współbieżny** - zbior procesów sekwencyjnych które moga być wykonywane równolegle.

## Dlaczego warto programować współbieżnie

- Niektore problemy sa z natury wspolbiezne, rozwiazania innych daja sie latwo i elegancko wyrazic w postaci niezaleznie wykonujacych sie procedur
- Systemy operacyjne optymalizyja czas obrotu zadainia wykonujac inny proces
- stale zwiekszajaca sie moc obliczeniowa procesorow umozliwia wykonanie wielu zadan na jednym procesorze, w sposob niezauwazalny dla uzytkownika
- uzywajac wielu procesow mozna wykonac program szybciej niz na jednym
- malejae ceny sprzetu sprzyjaja powstawanieu architektur wieloprocesorowych
- ...

## Niektóre problemy są z natury wspóbieżne

void sortuj(i, j):
	...
	concurent {
		sortuj(i, m)
		sortuj(m + 1, j)
	}
	scal(i, m, j)

## Wielozadaniowość, podział czasu

Ryzen 9: 32 rdzenie

4060 TI: 4352 rdzenie

OpenCL, CUDA - biblioteki pozwalające na wykonywanie obliczeń na GPU

## uzywajac wielu procesow mozna wykonac program szybciej niz na jednym

**Przyspieszenie** Stosunek czasu potrzebnego jednemu procesowi za wykonanie zadania do czasu potrzebnego n równoległym porcesorom na wykonanie tego samego zadania. **UWAGA** Przyspieszenie nie będzie n-krotne

**Proces** aktywne uruchomienie programu (textu)

**Proces sekwencyjny** wykoanie programu sekwencyjnego

**Proces współbieżny** zbiór procesów sekwencyjnych

Każde wykoanie równoległe jest wykoaniem współbieżnym. Ale jest czymś szerszym.

Proces ma przestrzeń adresową. Proces kompiluje się do kodu maszynowego. Rejestry, stan procesu.

Nowy -> Gotowy -> Aktywny -> Gotowy
						  -> Wstrzymany -> Gotowy
						  -> Zakończony

## Wyonania współbieżne

### Równoległe synchroniczne
- typowe dla architektur wieloprocesorowych
- wspólny zegar

### Równoległe asynchroniczne
- typowe dla arhcitektór rozproszonych
- brak wspólnego zegara

### Równoległe z przeplotem -> podział czasu


# Jak analizować programy współbieżne

Programowanie sekwencyjne:
- poprawność częściowa -> jesli sie zatrzyma to robi to co miał
- poprawność całkowita -> zatrzymuje sie i robi to co miał

Programowanie współbieżne:
- **Operacje atomowe** - wszystko można podzielić 
- **Obliczanie programu współbieżnego, przeplot**
- **Pojęcie poprawności** 

## Operacje atomowe
- Operacja atomowa wykonuje się od początku do końca
- jest niepodzielna
- my nie będziemy nic zakładać

System wieloprocesorowy nie gwarantuje niepodzielności nawet pojedynczego rozkazu maszynowego

Pisząc program często nie wiemy, na jakim sprzęcie pracujemy


## Obliczenie programu współbieżnego 

- przeplot, obraz

## Poprawność Programów Współbieżnych

- Bezpieczeństwo (zapewnianie)
	- nigdy nie dojdzie do sytuacji niepożądanej
	- własność zapewnianai pojawi się w specyfikacji problemu synchronizacjnego
	- odpowiednik częściowej poprawności programu sekwencyjnego

- Żywotność:
	- każdy proces, który chce wykonać pewną akcję w skończonym czasie będzie mógł to zrobić
	- własność żywotności jest związana z wykonaniem programu
	- odpowiednik całkowitej poprawności programu sekwencyjnego razem z własnością bezpieczeństwa


### Brak żywotności

Globalny - zakleszczenie (deadlock)

Lokalny - zagłodzenie - proces czeka w nieskończoność, gdyż zdarzenie na które czeka powoduje zawsze wznowienie innego procesu

### Brak aktywnego oczekiwania

Oczekujący proces ma zostać obudzony dopiero wtedy, gdy będzie mógł podjąć dalszą pracę.


### Brak ścisłego powiązania procesów

Metody synchronizacji nie powinny uzależniać działania jednego procesu od działania innych


### Zakładamy, że System Operacyjny jest uczciwy

Każdy proces gotowy do wykonania stanie się w końcu aktywny.
To nie musi być sprawiedliwe, ale każdy gotowy będzie kiedyś aktywny.


# Klasyczne problemy PW
- wzajemne wykluczanie
- producenci i konsumenci
- czytelnicy i pisarze
- pięciu filozofów


## Wzajemne wykluczanie

for (;;) {
	sekcja_lokalna();
	protokow_wstepny();
	sekcja_krytyczna();
	protokol_koncowy();
}

sekcja_lokalna() -> tu moze sie wydarzyc WSZYSTKO

sekcja_krytyczna() -> procesor w koncu z niej wyjdzie

protokol_wstepny() -> zapewnia, ze tylko jeden proces jest w sekcji krytycznej

Bezpieczeństwo -> w SK() jest co najwyzej jeden proces
Żywotność -> kazdy proces, ktore chca wejsc do SK() - w koncu tam weszly

# _Semafor ogólny_

- Abstrakcyjny typ danych z operacjami:
	- inicjacja (od razu przy deklaracji)
	- opuszczania P (Próba Prześcia Przez Semafor)
	- podniesienia V
- Operacje P i V są niepodzielne i wykluczają się nawzajem
- Semafor o wartości 0 jest zamknięty
- Semafor o wartości większej niż 0 jest otwarty
- Jeśli semafor jest otwarty, to P powoduje zmniejszenie jego wartości o 1
- Operacja P na zamkniętym semaforze powoduje wstrzymanie wykonującego się procesu na semaforze
- Operacja V powoduje zwiększenie wartości semafora o 1, jeśli nikt nie czeka na semaforze
- Jeśli ktoś czeka na semaforze, to operacja V budzi któryś z czekających procesów



