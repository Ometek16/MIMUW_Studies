# Asercje

1. **`assertEquals(expected, actual)`**: Sprawdza, czy dwie wartości są równe.
2. **`assertNotEquals(unexpected, actual)`**: Sprawdza, czy dwie wartości są różne.
3. **`assertTrue(condition)`**: Sprawdza, czy podany warunek jest prawdziwy.
4. **`assertFalse(condition)`**: Sprawdza, czy podany warunek jest fałszywy.
5. **`assertNull(value)`**: Sprawdza, czy podana wartość jest `null`.
6. **`assertNotNull(value)`**: Sprawdza, czy podana wartość nie jest `null`.
7. **`assertSame(expected, actual)`**: Sprawdza, czy dwie referencje wskazują na ten sam obiekt.
8. **`assertNotSame(unexpected, actual)`**: Sprawdza, czy dwie referencje wskazują na różne obiekty.
9. **`assertThrows(expectedExceptionType, executable)`**: Sprawdza, czy podany kod rzuca wyjątek oczekiwanego typu.
10. **`assertTimeout(duration, executable)`**: Sprawdza, czy podany kod kończy się w określonym czasie.
11. **`assertTimeoutPreemptively(duration, executable)`**: Podobnie jak `assertTimeout`, ale natychmiast przerywa wykonywanie kodu po przekroczeniu czasu.
12. **`assertIterableEquals(expected, actual)`**: Sprawdza, czy dwie kolekcje są równe (tzn. mają tę samą kolejność i zawartość elementów).
13. **`assertArrayEquals(expected, actual)`**: Sprawdza, czy dwie tablice są równe (tzn. mają tę samą długość i zawartość elementów).
14. **`assertLinesMatch(expected, actual)`**: Sprawdza, czy listy tekstów są równe, biorąc pod uwagę dopasowanie wzorca (np. wyrażeń regularnych).
15. **`assertAll(executables)`**: Pozwala na grupowanie wielu asercji w jednym bloku.
16. **`assertDoesNotThrow(executable)`**: Sprawdza, czy podany kod nie rzuca żadnego wyjątku.
17. **`assertAll(message, executables)`**: Pozwala na grupowanie wielu asercji w jednym bloku, jednocześnie dostarczając wiadomość, która zostanie wyświetlona w przypadku niepowodzenia którejkolwiek z asercji.
18. **`fail()`**: Powoduje nieudane zakończenie testu bez żadnych dodatkowych warunków.
19. **`fail(message)`**: Powoduje nieudane zakończenie testu z podaną wiadomością.
20. **`assumeTrue(condition)`**: Przerywa wykonywanie testu, jeśli podany warunek nie jest prawdziwy. Test nie zostanie uznany za nieudany, ale zostanie pominięty.
21. **`assumeFalse(condition)`**: Przerywa wykonywanie testu, jeśli podany warunek jest prawdziwy. Test nie zostanie uznany za nieudany, ale zostanie pominięty.
22. **`assumingThat(condition, executable)`**: Wykonuje blok kodu tylko wtedy, gdy podany warunek jest prawdziwy. Pozwala na warunkowe wykonanie części testu.

# Adnotacje

1. **@Test**: Oznacza metodę jako test jednostkowy. Ta metoda zostanie uruchomiona przez test runner JUnit, aby przetestować określoną część kodu.
2. **@BeforeEach**: Oznacza metodę, która zostanie uruchomiona przed każdym testem w klasie testowej. Jest to przydatne, gdy chcemy zresetować stan obiektów, przygotować dane wejściowe lub zasoby przed każdym testem.
3. **@AfterEach**: Oznacza metodę, która zostanie uruchomiona po każdym zakończonym teście w klasie testowej. Jest to przydatne, gdy chcemy posprzątać po teście, zwolnić zasoby lub zarejestrować wynik.
4. **@BeforeAll**: Oznacza metodę, która zostanie uruchomiona tylko raz przed wszystkimi testami w klasie testowej. Jest to przydatne, gdy chcemy zainicjować zasoby, które są wspólne dla wszystkich testów i mogą być kosztowne czasowo.
5. **@AfterAll**: Oznacza metodę, która zostanie uruchomiona tylko raz po zakończeniu wszystkich testów w klasie testowej. Jest to przydatne, gdy chcemy zwolnić wspólne zasoby dla wszystkich testów.
6. **@DisplayName**: Pozwala na nadanie czytelnej nazwy testowi, która będzie wyświetlana podczas uruchamiania testów. Jest to szczególnie przydatne, gdy chcemy dodać opisowe informacje o teście.
7. **@Disabled**: Oznacza test jako wyłączony. Test runner JUnit nie będzie uruchamiał testu oznaczonego tą anotacją. Jest to przydatne, gdy chcemy tymczasowo wyłączyć niektóre testy.
8. **@Nested**: Oznacza klasę wewnętrzną jako zagnieżdżoną klasę testową. Umożliwia grupowanie testów związanych z określoną funkcjonalnością lub kontekstem.
9. **@Tag**: Pozwala na przypisanie etykiet do testów, co umożliwia filtrowanie testów podczas uruchamiania na podstawie przypisanych etykiet.
10. **@Timeout**: Ustawia limit czasu dla danego testu. Jeśli test przekroczy określony limit czasu, zostanie uznany za nieudany.
11. **@ExtendWith**: Umożliwia rozszerzenie JUnit 5 za pomocą własnych rozszerzeń, takich jak dodatkowe operacje przed uruchomieniem testów, rejestratory wyników itp.
12. **@ParameterizedTest**: Oznacza metodę jako test parametryzowany, który pozwala na uruchomienie testu z róznymi parametrami
13. **@RepeatedTest**: Oznacza metodę jako test, który zostanie wykonany wielokrotnie z określoną liczbą powtórzeń. Pozwala to na łatwe sprawdzenie stabilności i niezawodności kodu podczas wykonywania tego samego testu wiele razy.
14. **@TestFactory**: Oznacza metodę jako dynamiczną metodę testową, która zwraca kolekcję obiektów `DynamicTest` lub `DynamicContainer`. Umożliwia to tworzenie testów w sposób programistyczny, zamiast deklaratywny, co pozwala na generowanie testów w czasie wykonania.
15. **@TestInstance**: Używana na poziomie klasy testowej, pozwala kontrolować cykl życia instancji testów. Domyślnie, JUnit tworzy nową instancję klasy testowej dla każdego testu, ale za pomocą tej anotacji można zmienić zachowanie na `Lifecycle.PER_CLASS`, co oznacza, że tylko jedna instancja klasy testowej zostanie utworzona dla wszystkich testów w klasie.
