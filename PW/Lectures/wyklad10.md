# Wykład 10

# System rozproszony

## Definicja
- System rozproszony składa się z N węzłów (fizycznie wyodrębniona jednostka, np. komputer)	
- W węźle może wykonywać się wiele procesów
- Komunikacja między węzłami (procesami) odbywa się poprzez asynchroniczną wymianę komunikatów

## Model komunikacji
- Sieć jest w pełni połączona - każdy węzeł może wysłać komunikat do każdego innego
- Komunikaty wysyłane od jednego węzła do innego dochodzą bez błędów w skończonym czasie (to zakładamy na chwilę obecną)
- Kolejność komunikatów wysyłanych między węzłami może ulec zmianie
- Węzły nie ulegają awarii

## Założenia
- Omawiane tutaj algorytmy działają w wyższych warstwach sieciowych
- Niższe warstwy mogą implementować techniki zapewniania niezawodnej komunikacji
- Niezawodność

# Wzajemne wykluczanie w systemie rozproszonym

- W węźle $W_i$ działa proces $P_i$ wymagający WW.
- Przed wejściem do SK proces $P_i$ wysyła do pewnych innych węzłów prośbę o zgodę na wejście do SK i czeka na ich zgodę.
- Po wyjściu z SK informuje oczekujących, że SK jest wolna

## Ogólny obraz
- Niech $S_i$ będzie zbiorem węzłów, które muszą się zgodzić na wejście $P_i$ do sekcji krytycznej
- Wymagane:
	- $S_i \cap S_j != \empty$ dla $1 <= i < j <= N$
- Przydatne: 
	- $|S_i| = |S_j|$
	- Każdy węzeł należy do tej samej liczby zbiorów $S_i$
	- $i \in S_i$

## Model pierwszy: klient-serwer

- koordynator-serwer, działający np. w węźle N-tym
- $S_i = \{N\}$ dla wszystkich $1 <= i < N$

Klient:
- wysyła żądanie
- czeka na zgodę
- wchodzi do SK
- informuje że skończył

Serwer:
- odbiera komunikat
- jeśli to jest żądanie i SK jest wolna to wysyła zgodę
- jeśli to jest żądanie i SK jest zajęta to dodaje na kolejkę
- jeśli SK jest wolna i coś jest na kolejce - obsługuje żądanie

## Model drugi: algorytm Ricarta-Agrawali

- nie ma wyróżnionego koordynatora - węzły same ustalają kto wejdzie do sekcji krytycznej
- $S_i = \{1, ..., N\} - \{i\}$
- Węzeł i-ty wysyła prośbę do pozostałych węzłów i czeka na komplet N-1 zgód
- Węzeł i po odebraniu żądania od węzła j
	- jeśli sam nie zgłosił żądania to od razu wysyła zgodę
	- jeśli zgłosił żądanie, ale żądanie od j jest "wcześniejsze" to też od razu wysyła zgodę
	- wpp wstawa numer j do kolejki
- gdy tylko wychodzi z SK węzeł wysyła zgody do wszystkich węzłów z kolejki

### Co to znaczy "wcześniejszy"??

- Do komunikatu Req dodawany jest aktualny czas
- Wcześniejszy czas leci pierwszy
- W przypadku remisu decyduje nr procesu


### No ale co to jest "aktualny czas"
- nie ma wspólnego zegara
- ale każdy proces może trzymać swój lokalny czas
- no ale czasy na różnych procesorach może lecieć z różną prędkością... Rozwiązanie?

## Algorytm Lamporta

### Relacja uprzedniości
- pewne zdarzenia w systemie rozproszonym są w naturalny sposób uporządkowane
- jeśli x i y są zdarzeniami w ramach tego samo procesu i x zaszło przed y, to $x \rarr y$
- jeśli x jest zdarzeniem odebrania komunikatu, a y jest zdarzeniem odebrania tego komunikatu, to $x \rarr y$
- częściowy porządek

### Uporządkowanie zgodnie z zegarami logicznymi
- z drugiej stront wygodne jest porządkowanie zdarzenia na podstawie wartości zegarów logicznych z chwili ich zajścia
- oznaczamy przez $T_i(x)$ wartość zegara logicznego w węźle i w chwili zajścia x
- definiujemy $x << y$, gdy
	$$ T_i(x) < T_j(y) $$ 
	$$ T_i(x) == T_j(y) \text{ oraz } i < j$$

- sensowne jest zakładać, że jeśli $x \rarr y \text{ to } x << y$
- implikacja narazie zachodzi w jedną storonę
- będziemy korygować czas logiczny w chwili odebrania komunikatu
- $T_i = max(T_i, T_j) + 1$

## Model trzeci: algorytm żetonowy - Nielsena-Misuno

- w każdym węźle jest zapamiętany numer rodzica
- na dzień dobry to jest drzewo, które będzie się zmieniać
- w korzeniu drzewa jest żeton
- węzły oczekujące na żeton są kolejkowane - każdy węzeł trzyma wskaźnik do następnego
- kolejka jest wirtualna i rozproszona na poszczególne węzły
- Węzeł z żetonem pamięta następnego chętnego itd
- Niezmiennik: w dowolnym momencie korzeniem drzewa jest węzeł z żetonem, gdy kolejka jest pusta lub ostatni element kolejki


- żądania mają postać Req(nadawca, twórca) i są przesyłane po krawędziach do korzenia
- Węzeł $i$, który chce wejść do SK
	- wchodzi do niej jeśli ma żeton 
	- wpp. Wysyła do rodzica żądanie Req(i, i) i zeruje wskaźnik rodzica

- Węzeł $i$, który otrzyma Req(nadawca, twórca)
	- Przesyła dalej Req(i, twórca)
	- zmienia swjego rodzica na nadawca.

- Korzeń, który otrzyma żądanie postaci Req (nadawca, twórca):
- zmienia swojego rodzica na węzeł nadawca
- jeśli ma żeton i nie jest w sekcji krytycznej, wysyała żeton do węzała twórca
- wpp zapamiętuje numer twórca jako następny element kolejki



