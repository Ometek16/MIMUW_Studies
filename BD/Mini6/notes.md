Stwórz pojęciowy model danych dla następującej sytuacji. Na konferencji naukowej będą prezentowane prace z różnych dziedzin informatyki (np. bazy danych, systemy operacyjne, algorytmy, logika, itp.). Każda praca będzie prezentowana podczas referatu przez jednego z autorów. Referaty są pogrupowane w sesje, każda sesja jest prowadzona przez jednego z uczestników konferencji. Nie każdy uczestnik będzie mówił referat i nie każdy jest autorem jakiejś pracy. Niektórzy są autorami wielu prac i mogą mówić wiele referatów (ale nie podczas tej samej sesji). Prowadzący sesję nie może być autorem żadnej pracy prezentowanej podczas tej sesji. Zatroszcz się o to, żeby wszystkie opisane założenia były weryfikowalne w oparciu o przechowywane dane. W miarę możliwości, założenia powinny być zawarte w modelu. Oddać należy jednostronicowy plik PDF; inne formaty nie są wyświetlane bezpośrednio w moodle'u i trzeba je ściągnąć na własny komputer, co wywołuje irytację sprawdzających i sprawia, że stają się oni nieświadomie acz nieuniknienie bardziej surowi w ocenie, zbliżając się nierzadko do granicy złośliwości, a czasami nawet ją przekraczając.


1) Konferencja naukowa


2) Praca naukowa
	-> dziedzina

5) Sesje:
	-> prowadzący -> uczestnik, nie moze byc autorem zadnej pracy prezentowanej podczs sejsi


3) Referat
	-> jest w 5) sesji
	-> jest wygłaszany przez jednego z autorów pracy

4) Autorzy
	-> autor
	-> jest autorem 2) Pracy naukowej


Praca -> Referat -> Sesja -> Konferencja
  ^			^		  ^
  |			|		  |
dziedzina  autor   prowadzacy