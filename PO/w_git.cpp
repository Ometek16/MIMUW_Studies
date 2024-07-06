/*
!! Programowanie Obiektowe
!! Wykład GIT
!! 17_05_2023

!! Wazne informacje:
	! po co komu git
*/

/*

& Zalety uzywania gita:
	* Ulatwia prace nad projektem
	* Standardowy sposob wspolpracy miedzy deweloperami
		- czesto wymagany przy rekrutacji

& Zalety uzywania gita solo:
	* Łatwe eksperymenowanie
		- plynnosc racy na wielu galeziach
	* Na czym skończyłem?
	* Debuggowanie -> bisekcja
	* Backup i synchronizacja

& Zalety uzywania gita w zespole:
	* "Kto to napsial i kto mi to wyjasni"
	* Ludzie przychodza i odchodza, repozytorium zostaje
		- dlaczego to jest tak zrobione
		- projekty trwajace latami
	* Debggowanie
	* Rollbacki
	* Efektywne zarządzanie kodem
		- utrzymanie wielu wersji
		- swoboda w integrowaniu zmian

& Koncepcje

^ Repozytorium to baza danych przechowujaca historie projektu
	* jak sie zmieniala zawartosc plikow
	* kto i kiedy wprowadzil te zmiany
	* jaka jest kolejnosc tych zmian

* Kazdy stan projektu zapisany w tej bazie to //! Commit

* Repozytorium jest jak tablica haszujaca, ktora mapuje commit ID na stan projektu

& Git jest rozproszony

* Gdzie są przechowywane dane projektu

* Co sie stanie jesli serwer (GitHub)
	* bedzie niedostepny?
	* zniknie na dobre?

* bezpieczne :D

& Zdalne repozytoria

* Remote to adres (wskaznik) do innego repozytorium
	* URL do repo dostepnego w sieci
	* sciezka do folderu na dysku

* Remote repository (zdalne repozytorium) to koncepcyjnie taki sam byt jak nasze lokalne repo, tylko mieszkający gdzieś indzizej (w chmurze)

? git log --oneline --graph
	--oneline
	--graph
	--all
	--branches
	--not
	--patch
	Historia projektu

& Gałęzie
	* rozwoj projektu nie jest liniowy
		- kazdy deweloper jest na osobnej galezi
	* rownolegla praca nad osobnymi funkcjonalnosciami
	* rownolegle utrzymanie roznych werjcji produktu
	* galez, na ktorej wszystko dziala (master)

? git diff
	--ignor-space-change		Ignorowanie bialych znakow
	--color-words				Porownywanie slow, nie linii

! github.com/jan-warchol/git-tools

& Co chcemy osiagnac?
	* Wiadomo co gdzie jest
	* Stabilny kod
	* Mozliwosc wdrozenia w kazdym momencie
	* Precyzyjny, automatyzowalny debug
	* Łatwosc wyzofana problamatycznych zmian
	* Mniej konfliktow
	* Szybkie i latwe integrowanie zmian


& Jak to zrobic?
	* Oddzielenie 'work in progress' od stabilnego kodu za pomoca galezi
	* Niewielkie, dobrze opisane commity
	* Oddzielenie bugfixow, nowych ficzerow i refaktorow
	* Oddzielenie zmian tak, aby byly jak najbardziej niezalezne

* Im wiekszy porzadek w commitach i galeziach, tym potezniejszym narzedziem staje sie git

* Commity powinny być atomowe - niepodzielne. Najmnijeszy zestaw zmain stanowiacy odrebna logiczna calosc. Raczej kilkadziesiat linii niz kilkaset.

& Jak dobrze opisac commit?
	* Napierw napisz co robi
		* staraj sie zmiescic w 50 znakach. (maksymalnie 80)
		* potem koniecznie pusta linia odstepu

	* Potem wyjasnij dlaczego to robi
		* dlaczego jest potrzebny
		* dlazego to jest zrobione w taki sposob, a nie inny

& Nie badz smutnym panem z obrazka
	* Pamietaj, ktos bedzie czytal ten kod za rok i bez wskazowek sie nie polapie
		* mozesz to byc ty!
		* zapisanie wyjasnienia to minuta, a rozkminianie kody moze zajac godzine
	* czasem nie jestesmy pewni zmain ktore wprowadzamy
		* to nie wstyd ,a przyznawanie sie ulatwi zycie innym.

& Master i feature branches

* Domyslna galez w repozytorium nazywamy master
	* To jest konwencja
	* Stablilna galez, na ktorej wszystko dziala. Nie powinna miec 'work in progress'
	* "jest zrobione kiedy jest na masterze"

* Feature branches to konwencja rozwijania kazdej funkcjonalosci na osobnej galezi.
	* merge do mastera kiedy kod jest gotowy
	* odgaleziamy sie od mastera
	* funkcjinalnosci moga byc rozwijane i integrowane niezaleznie

^ Gdy jest jakis bug w masterze to trzeba utworzyc nowa galez bezposrednio od mastera!!!

& Dobre praktyki

* Code review:
	* Nietrywialne zmiany wymagaja approve od innego deva
	* Unikajcie duzych PRow
	* Opisuje commity tak, zeby PR sie latwo reviewowalo

* Unikacjie rownoleglej pracy nad zaleznymi zmianami


& Git dba o nasze dane

	* jezeli cos zostalo zacommitowane, to da sie odzyskac
	* o ile nie stracimy smaego katalogu .git
		* dla tego regularnie trzeba pushowac
		* zgubione commity mozna znalesc w //!reflogu

& Unikaj plikow binarnych w repo

	* Nie daja sie latwo porownywac
	* Na ogoll zajmoja mnostwo miejsca
	* kazda nowa wersja musi byc zapisana w calosci. nie da sei latwo zapisac samych roznic

	* To samo dotyczy plikow generowanych z kodu zrodlowego

& Czego unikac:

^ Na poczatku
	* Rebase
	* Force-pushowanie
	* Commitowanie na masterze
	* Submoduły

^ Zawsze
	* Zmieniania istniejących tagow
	* Mieszania bugfixow z ficzerami
	* Binarnych plikow w repo

! https://github.com/git-guides
! https://git-scm.com/book/pl/v2



*/
