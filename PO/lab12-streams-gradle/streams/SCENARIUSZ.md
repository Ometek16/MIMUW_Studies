# Strumienie

(Mądra nazwa Stream API ;)

Strumień to abstrakcyjny (tym razem również w sensie, że bez konkretnej "fizycznej"
reprezentacji w pamięci) ciąg obiektów - raczej nie do przechowywania, tylko
do szybkiego wykorzystania (po obróbce).
(BTW: zauważcie, że w dok. biblioteki std.
[java.util.stream.Stream](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/stream/Stream.html)
nie ma wyszczególnionych Implementing Classes

Ogólna zasada pracy ze strumieniami:

* tworzenie strumienia
* przetwarzanie strumienia
* ...
* przetwarzanie strumienia
* konsumpcja strumienia


### Przykłady wstępne

* Wypisz marki wszystkich samochodów z listy, droższych niż 200_000.
* j.w., ale marki mają być posortowane, bez powtórzeń.

Przykład [Przyklady0Wstep](test/Przyklady0Wstep.java).

Strumienie są JEDNORAZOWE. Nie można skonsumować tego samego strumienia więcej niż jednokrotnie.

I leniwe, jak w "leniwe vs gorliwe wyliczanie wyrażeń logicznych" 
(ang. "lazy evaluation vs eager evaluation")
(dlatego sortowanie, to raczej w ostateczności, albo na małym strumieniu).

"Łatwo" się zrównoleglają.
(jeśli ktoś kiedyś natrafił, oglądając dokumentacje interfejsu List, na Spliterator i nie rozumiał 
o co w nim chodzi, no to właśnie jest on używany do tworzenia wielowątkowego stream-u
[StreamSupport::stream(Spliterator)](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/stream/StreamSupport.html#stream(java.util.Spliterator,boolean))
).


Operacje przetwarzające strumień (czyli te przed konsumpcją, i ewentualnie z wyjątkiem peek - do debugowania) 
powinny być "funkcyjne", czyli nie powinny zmieniać stanu (żadnych obiektów, wypisywać niczego, 
zapisywać do pliku itp.).
Oczywiście Java tego nie sprawdza (bo nie ma jak), ale właśnie nie mamy żadnej gwarancji kiedy, 
w jakiej kolejności i czy w ogóle one się wykonają, więc jeśli te operacje jednak mają efekty uboczne,
to nasz program staje się nieprzewidywalny.

Nie będziemy się na tym skupiać, ale jak to zwykle w Javie, są osobne typy strumieni (z leciutko różnymi operacjami) 
dla typów liczbowych bazowych: IntStream, LongStream, DoubleStream.

### Ciąg technologiczny strumieni (stream pipeline)

Tworzenie (source):
* Najprościej z jakiejś struktury danych, np. Collection.stream() albo Arrays.stream() 
* Ale też np. IntStream.range(...) generate(...) iterate(...)
* Z innych streamów Stream.concat(s1,s2), z niczego Stream.empty(), Stream.of("ala","ma","kota")
* Z plików - Files.walk(), Files.lines(), ze scannera, Scanner.tokens() Scanner.findAll() ... 

Przetwarzanie (intermediate operations):
* filter
* map (dwa najpopularniejsze)
* limit(ile), skip(ile), takeWhile(pred), dropWhile(pred) itp.
* parallel(), sequential(), unordered()
* i też sorted(...), distinct()
* ale także flatMap, mapMulti 

Konsumpcja (terminal operation)
* forEach(cons)
* toArray(), toList() **lista niezmienialna**
* allMatch(pred), anyMatch(pred), noneMatch(pred)
* count()
* min, max, (typ Optional)
* average(), summaryStatistics()
* reduce...  [odtąd zaawansowane...]
* collect..., Collectors.toList(), Collectors.toSet(), Collectors.joining() [napisy]
  Collectors.groupingBy(...) partitioningBy(...) toMap(...), teeing(...)
  (poza tym rzeczy bardzo podobne do terminal operations w stream'ach)


### Ćwiczenia proste

* Wypisz wszystkie kwadraty liczb naturalnych mniejsze niż 1000  
  IntStream.range(...).map(...).takeWhile(...)

* Wypisz sześciany liczb naturalnych większe niż 1000, ale mniejsze niż 10000  
  IntStream.range(...).map(...).dropWhile(...).takeWhile(...)

* Kolejne potęgi dwójki, 20 pierwszych   
  LongStream.iterate(...).limit(...)

* 10 liczb Fibonacciego większych od 100  
  LongStream.generate(...).dropWhile(...).limit(...)

* Statystyki sinusów liczb całkowitych od 0 do 1000  
  IntStream.range(...).mapToDouble(...).summaryStatistics()

* Tablica nazw (marka + model) samochodów 5-cio drzwiowych, posortowana malejąco.
  Tablica ma być typu String[]  
  listaSamochodów.stream().filter(...).map(...).sorted(...).toArray(...)

* Liczba słów w pliku "src/Samochod.java"  
  Scanner(...).tokens().count()

* Czy w tym pliku jest jakaś niepusta linia niezawierająca literki "a" (String.contains)  
  Files.lines(Path.of(...)).filter(...).anyMatch(...)

* Podać najdłuższą linię zawierającą "a" w pliku j.w.  
  Files.lines(...).filter(...).max(...).orElse("")

I tu dochodzimy do typu [Optional](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/Optional.html). To jest taka niewczesna próba zmuszenia programistów Javy do pamiętania, 
że czegoś może nie być.  
["Optional was created so code could after all these decades, finally start avoiding null."](https://stackoverflow.com/questions/38725445/optional-get-without-ispresent-check#59465726)  
Optional< E > jest albo isEmpty(), albo isPresent() i "wtedy" get() ma typ E.
I są różne wspomagacze, np. ifPresent(cons), orElse(E domyślne)
A także inne, które "udają", że Optional to taki mały stream-ik (filter, map, flatMap, stream)...

Tak jak ze strumieniami, są "wyspecjalizowane" typy OptionalInt, OptionalLong, OptionalDouble.

### Coś trudniejszego

Przykład [Przyklady1Trudniejsze](test/Przyklady1Trudniejsze.java).  
Budujemy strumień linii z pliku i za pomocą Scanner(String).tokens() oraz flatMap zamieniamy go na strumień słów z tego pliku;
wypisujemy powstałe słowa.

Ćwiczenie:
* A teraz z pomocą Stream.concat i Stream.of wstaw na początku zestawu słów dla danej linii napis "NOWA LINIA".
  wypisz słowa.

Powyższe rzeczy można by też zrobić za pomocą mapMulti
(wtedy zamiast pustawych Stream'ów wołamy - lub nie - odp. consumera).

Ćwiczenia:
* Zrób cennik samochodów z dodatkami: "Marka Model: cena", albo "Marka Model + Dodatek : cena": używając flatMap i Stream.concat
* j.w. używając mapMulti



### Reduce i collect: reduce

Takie operacje końcowe jak count, sum, max, min to instancje operacji reduce, która "redukuje" strumień za pomocą 
odpowiedniej funkcji binarnej na (dla uproszczenia) elementach strumienia, oznaczanej poniżej jako ⊙. Metoda reduce 
występuje w trzech wersjach:
* reduce(⊙)
* reduce(o, ⊙)
* reduce(o, ·, ⊙)

Żeby zrobić sumę strumienia, można użyć "reduce(+)", która np. strumień  
1 2 3 4 5 6 7 8 9 10  
"przerabia" na działanie:  
1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10

Ale w jakiej kolejności tak naprawdę są wykonywane te operacje? To zależy. Jak strumień jest sekwencyjny to takiej:  
(…( 1 + 2) + 3) + 4) + 5) + 6) + 7) + 8) + 9) + 10)  
Jak równoległy, to bardziej jakoś tak:  
(((1 + 2) + (3 + 4)) + ((5 + 6) + (((7 + 8) + 9) + 10)))  
Dlatego ⊙ powinien być operacją *łączną*, tj.  (a ⊙ b) ⊙ c = a ⊙ (b ⊙ c), ang. *associative*.

OK, a co jak strumień jest pusty? Wtedy mamy problem... Dlatego reduce(⊙) ma typ wyjściowy Optional.  
Ale np. w przypadku sumy dobrze wiemy, jaka powinna być suma pustego strumienia: 0. Jeśli dysponujemy taką wartością, 
jak 0, czyli taką, która nadaje się na wartość dla pustego strumienia, co również oznacza, że o ⊙ b = b, to wtedy nie tylko wiemy co z pustym strumieniem, ale także możemy operacje wykonywać 
tak:  
(…( 0 + 1) + 2) + 3) + 4) + 5) + 6) + 7) + 8) + 9) + 10)  
albo równolegle:  
((((0 + 1) + 2) + ((0 + 3) + 4)) + (((0 + 5) + 6) + ((((0 + 7) + 8) + 9) + 10)))  
Ale to jest lepiej??? Ano tak, bo pierwszy element (fragmentu) strumienia jest traktowany dokładnie tak samo jak każdy 
inny (też stoi po **prawej** stronie +).

No dobra, a jak policzyć samochody??? Nie umiemy przecież dodawać do siebie samochodów :(  
Najprościej każdy samochód zamienić na 1, a potem wszystkie te jedynki dodać.  
Tylko, że to tworzy niepotrzebną postać pośrednią (strumień samych jedynek), dlatego właśnie jest trzecia postać reduce:
w której ta operacja · w środku, liczbę i samochód zamienia na liczbę, w przypadku liczenia to oczywiście (5 · samochód = 6).
A zatem liczenie strumienia samochodów s₁ s₂ s₃ s₄ s₅ s₆ s₇ s₈ s₉, mogłoby się odbywać (równolegle) tak:

((((0 · s₁) · s₂) + ((0 · s₃) · s₄)) + (((0 · s₅) · s₆) + (((0 · s₇) · s₈) · s₉)))

BTW: operacje, które nadają sie do redukowania (czyli łączne :) to np. dodawanie, mnożenie, max, min, dodawanie wektorów,
iloczyn wektorowy, mnożenie macierzy, and, or, łączenie napisów, ignorowanie prawego... i różne ich kombinacje.

[Przyklady2Reduce](test/Przyklady2Reduce.java)


Ćwiczenie:
* Zrób klasę StatFun (jak statystyka, funkcyjnie), aby z pomocą metody reduce 
  jednocześnie policzyć sumy drzwi oraz sumy cen wszystkich samochodów (taka statystyka ;)
  Do tworzenia klasy najwygodniej użyć
  [record](https://docs.oracle.com/en/java/javase/20/language/records.html) - funkcyjność / niezmienialność, konstruktor, gettery, toString gratis :)  
  Dodaj do niej operacje:  
  StatFun dołącz(Samochod s)  
  StatFun połącz(StatFun stat)  
  (pamiętaj, że mają to być operacje funkcyjne, czyli ich implementacja to zasadniczo return new ...)
  oraz nie zapomnij o   
  static StatFun zero = ...  
  Której wersji reduce należy użyć?
  Zrób trzy wersje, używające w sumie wszystkich rodzajów metody reduce. Aby użyć pozostałych rodzajów reduce przyda Ci się nowa operacja w klasie StatFun:    
  static StatFun of(Samochod s)  
  We wszystkich trzech wersjach, spróbuj zmniejszyć w ramach eksperymentu długość strumienia do 1 lub 0
  (za pomocą limit(...)).

* Aby zachowywać zdrowy rozsądek i nie dawać się wciągać w niepotrzebne programowanie (poza wprawkami!), policz obie statystyki 
  osobno, nie używając żadnych dodatkowych klas :)


### Reduce i collect: collect

Collect to taki imperatywny reduce. Występuje w dwóch wersjach:
* collect(produkcja, dolewanie, zlewanie)
* collect(collector)

W tej drugiej wersji, w zasadzie te trzy elementy z wersji pierwszej połączono w obiekt klasy Collector, przy czym 
jest mnóstwo gotowych (niektórych bardzo skomplikowanych - o tym za chwilę) collectorów w klasie 
[java.util.Collectors](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/stream/Collectors.html)
(i są osobne tutoriale o Collectors na youtube :)

Załóżmy, że jesteśmy w zakładach farmaceutycznych i mamy ciąg technologiczny produkujący gotowe do zamknięcia fiolki lekarstwa (strumień fiolek) 
i musimy pobrać i zgromadzić (w jednym końcowym zbiorniku - zlewce) po kropelce z każdej fiolki. Na przykład po to, 
żeby potem zbadać całość (pod kątem zanieczyszczeń) i jak wszystko będzie dobrze, to znaczy, 
że każda fiolka jest dobra.

Chcemy zrównoleglić proces gromadzenia kropelek. Dlatego dzielimy strumień fiolek na fragmenty, dla każdego fragmentu strumienia 
fiolek działamy tak:
* bierzemy od producenta nową zlewkę (produkcja)
* dolewamy z każdej fiolki z tego fragmentu strumienia po kropelce do zlewki (dolewanie)  

Zawartość zlewek dla poszczególnych fragmentów łączymy (zlewanie), przelewając zawartość jednej zlewki do drugiej. 
Pustą zlewkę utylizujemy (trudno, o ekologii pomyślimy innym razem). 
Na koniec dostajemy jedną zlewkę zawierającą po kropelce z każdej fiolki.

[Przyklady3Collect](test%2FStrumienieTest3Collect.java)

Uwaga! IntStream, LongStream, DoubleStream mają tylko pierwszą postać metody collect. Aby użyć na nich
czegoś z klasy Collectors należy wykonać operację boxed(), która zastąpi np. IntStream przez 
Stream< Integer >.

Ćwiczenie na collect:
* Zrób klasę StatImp (jak statystyka, imperatywnie)  
  Zaimplementuj w niej operacje:  
  void dodaj(Samochod s)  
  void wchłoń(StatImp stat)  
  I użyj jej za pomocą metody collect do jednoczesnego policzenia sumy drzwi oraz sumy cen wszystkich samochodów...

Ćwiczenia na proste Collectors:
* Wygeneruj z List.of(1,3,5,8,14,19) za pomocą Collectors.joining z odp. argumentami napis:  
  "W ostatnim losowaniu Lotto padły następujące liczby: 1, 3, 5, 8, 14, 19. Mamy nadzieję, że Wasze są inne."
* Zrób LinkedHashSet zawierający wszystkie marki samochodów z listy, używając
  Collectors.toCollection.


### Zaawansowane collect'owanie: wiele wyników, mapy i zestawienia 

[Przyklady4CollectTrudne](test%2FPrzyklady4CollectTrudne.java)

##### teeing
Jeśli w wyniku przetwarzania strumienia potrzebujemy nie jednej danej końcowej (np. sumy jakiegoś elementu), ale 
np. dwóch, to oprócz robienia dwóch przetwarzań i wprowadzania specjalnej klasy, możemy użyć "rozgałęzionego" 
collectora:
* Collectors.teeing(col1, col2, zbieracz)

Metoda ta wprowadza strumień do dwóch kolektorów równocześnie, a następnie woła funkcję zbierającą ich wyniki.

  
##### groupingBy
Jeśli chcemy pogrupować dane ze strumienia, możemy użyć jednej z wersji metody
* Collectors.groupingBy(klasyfikator)

Strumień danych jest "rozdzielany" na osobne "strumyczki" wg wartości funkcji klasyfikatora, tworzona jest
mapa, której kluczami są wartości tej funkcji. 
Jeśli nie chcemy w wyniku po prostu mapowania na listy z danymi, możemy użyć innej wersji tej metody:
* Collectors.groupingBy(klasyfikator, collector)

W tej wersji dane w strumyczkach zbierane są za pomocą kolejnego collectora: np. do innej struktury danych 
(np. Collectors.toSet), zbierane są statystyki (np. Collectors.summingInt, Collectors.maxBy itp), 
ale może też zostać użyty teeing albo kolejny groupingBy - możliwości są nieograniczone.

Do tego właśnie służą liczne metody w klasie Collectors przypominające operacje pośrednie na strumieniach:
mapping, filtering, flatMapping, a także na oko powtarzające po klasie Stream operacje terminalne:
averaging, counting, maxBy, reducing, summarizing, toList...

##### toMap
Jeśli chcesz zamienić strumień w mapę przyporządkowującą kluczowi jedną wartość liczoną na podstawie 
jednego obiektu (np. cenę napisowi marka + model, który się nie powtarza), możesz użyć metody
* Collectors.toMap(kluczowanie, wartościowanie)

Produkuje ona mapę, która dla każdego obiektu o ze strumienia robi mapowanie 
kluczowanie(o) ↦ wartościowanie(o). Jeśliby się jednak zdarzyły dwa obiekty o tym samym kluczu, 
metoda ta rzuci wyjątek. Jeśli nie podoba nam się to, możemy użyć wersji:
* Collectors.toMap(kluczowanie, wartościowanie, łącznik)

która w przypadku kolizji kluczy wywoła funkcję łącznik(stara, nowa), aby ustalić jaka wartość 
ma być przypisana kluczowi.

Wydaje się, że użycie tej wersji toMap można by zastąpić przez groupingBy z collectorem łączącym
mapping i reducing, ale najprawdopodobniej autorzy uznali, że ta krótsza wersja jest przydatna.

##### partitioningBy
Jeśli chcesz podzielić obiekty w strumieniu na "dobre i złe", możesz użyć metody
* Collectors.partitioningBy(pred)

Tworzy ona mapę z Boolean w listę obiektów. Ma też wersję przepuszczającą oba strumyki przez
dodatkowy collector:
* Collectors.partitioningBy(pred, collector)

Opisane w tym rozdziale metody klasyfikujące (poza partitioninigBy) posiadają wersje, 
w których można podać funkcję tworzącą mapę konkretnego rodzaju (Supplier< M > mapFactory), a także wersje przeznaczone 
do tworzenia mapy z dostępem równoległym (groupingByConcurrent, toConcurrentMap).



Ćwiczenia:
* Stwórz mapę słów z ulubionego pliku: litera ↦ liczba słów na daną literę (literą może być dowolny pierwszy znak ze słowa)
* Stwórz mapę litera  ↦ najdłuższe słowo na daną literę
* Posortuj wiersze wg liczby wystąpień litery "a" - i zrób zestawienie: 
  tyle wystąpień  ↦ tyle wierszy o danej liczbie wystąpień
  (do liczenia liczby wystąpień możesz użyć strumienia String::chars ;) 
* Posortuj samochody wg ceny wraz ze wszystkimi dodatkami.
  Postaraj się nie liczyć łącznej ceny więcej niż raz dla każdego samochodu.
* Znajdź w przedziale (0,20000) wszystkie liczby o największej liczbie dzielników.
  Do liczenia dzielników możesz użyć range(...).filter(...).count() :)
  Nie obliczaj tego więcej niż jednokrotnie dla każdej liczby.

