# Startujemy

Na początek stwórzmy nasz pierwszy JavaFX projekt w Intellij.

Szczegółowa instrukcja jak to zrobić jest [tu](https://www.jetbrains.com/help/idea/javafx.html)
 - `File->Settings->Plugins->Installed` możemy sprawdzić że JavaFX jest włączone
 - `File->New->Project->JavaFX` możemy stworzyć aplikację HelloApplication

## 0. Wprowadzenie
Zanim omówimy sobie wygenerowaną aplikację, zacznijmy od czegoś jeszcze mniejszego.

### 0.1. Pusta aplikacja
Jeśli w naszym projekcie, tak jak tworzyliśmy zwykłe klasy, wybierzemy teraz `New->JavaFX Application` to otrzymamy 
automatycznie wygenerowany plik [Example0](src/main/java/com/example/javafxtutorial/Example0.java) 

1. Example0 dziedziczy po klasie Application (zob dokumentację [tu](https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html)) <br>
   a. cykl życia aplikacji to: <br>
   &nbsp; &nbsp;&nbsp; -- tworzenie instancji <br>
   &nbsp; &nbsp;&nbsp; -- `init()` <br>
   &nbsp; &nbsp;&nbsp; -- `start(javafx.stage.Stage)` <br>
   &nbsp; &nbsp;&nbsp; -- czekanie na koniec <br>
   &nbsp; &nbsp;&nbsp; -- `stop()` <br>
   b. metoda `start` jest abstrakcyjna, stąd trzeba podać jej implementację <br>
   c. statyczna metoda klasy Application `launch()` <br>
   &nbsp; &nbsp;&nbsp; -- uruchamia samodzielną aplikację <br>
   &nbsp; &nbsp;&nbsp; -- zazwyczaj wywołujemy z metody main (tylko raz) <br>
   &nbsp; &nbsp;&nbsp; -- kończy się po wyjściu z aplikacji (przez Platform.exit albo zamknięcie wszystkich okien) <br>

Jak uruchomimy ten przykład, nic się nie dzieje, możemy zakończyć go stop-em.

### 0.2 Klasa Stage

2. W metodzie `start` korzystamy z klasy `Stage`. W JavaFX jest to pojemnik najwyższego poziomu (zob też [tu](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html)).

Spróbujmy wyświetlić ten obiekt metodą `show()` w przykładzie [Example1](src/main/java/com/example/javafxtutorial/Example1.java).
Pojawiło nam się okno. Po jego zamknięciu aplikacja przestała się wykonywać.
Jednak przy zmienianiu rozmiaru okna widać dziwny obraz.

### 0.3 Klasa Scene i graf zawartości

3. W pojemniku `Stage` ustawiamy metodą `setScene()` obiekt klasy `Scene`.
4. Z kolei w obiekcie klasy `Scene` znajduje się cały graf zawartości. Schemat można obejrzeć [tu](https://i.stack.imgur.com/c7Zsv.jpg).
5. Aby stworzyć obiekt `Scene` pierwszy argument w konstruktorze to root - główny węzeł. W tym przykładzie tworzymy obiekt klasy Group. 

Teraz nasza aplikacja przy zmianie rozmiaru zachowuje się dobrze [Example2](src/main/java/com/example/javafxtutorial/Example2.java).

### 0.4 Proste modyfikacje

[Example3](src/main/java/com/example/javafxtutorial/Example3.java) przedstawia kilka opcji klasy `Stage` - modyfikujemy okno aplikacji.

[Example4](src/main/java/com/example/javafxtutorial/Example4.java) dodajemy niedziałający przycisk (elementy dodane do `Scene`). 

[Example5](src/main/java/com/example/javafxtutorial/Example5.java) dodajemy obsługę przyciku, aby po kliknięciu wypisał się tekst.

Zauważmy, że udało nam się stworzyć, z dokładnością do eleganckiego rozmieszczenia, taką samą aplikację, jaka wygenerowana została przez IntelliJ.


## 1. Aplikacja HelloWorld

Automatycznie wygenerowany kod przez IntelliJ znajduje się w [HelloApp](src/main/java/com/example/javafxtutorial/HelloApplication.java). 
Zauważmy, że generalnie jest to bardziej estetyczny wariant Example5.

HelloApplication.java nie zawiera definicji pojedynczych węzłów.
Za to w konstruktorze `Scene` w miejscu, gdzie podajemy pierwszy argument (root - główny węzeł), 
mamy `fxmlLoader.load()`.

W [pliku fxml](src/main/resources/com/example/javafxtutorial/hello-view.fxml) mamy 
importy VBox, Label i Button. Następnie zaczyna się definicja głównego węzła VBox.
Na początku definicji wskazany jest kontroler w linii 9, jest on w 
[klasie](src/main/java/com/example/javafxtutorial/HelloController.java).

W klasie tej znajdują się dwie annotacje `@FXML`: dla atrybutu `welcomeText` 
i dla metody `onHelloButtonClick`.

Jak wrócimy do [pliku fxml](src/main/resources/com/example/javafxtutorial/hello-view.fxml), to
w linii 14 mamy powiązanie poprzez `id` dla Label z atrybutem `welcomeText`.
Podobnie w linii 15 `onAction` łączone jest z metodą `onHelloButtonClick`. Zauważmy, że 
tekst wyświetlany na przyscisku ustalany jest bezpośrednio w pliku fxml.

Trochę to skomplikowane... czemu zatem FXML?

<b> FXML (oparty na XML-u) pozwala oddzielić 
warstwę do budowy interfejsu użytkownika
od wnętrza, w którym programujemy logikę naszej aplikacji.
</b>

Zobacz też [tu](https://docs.oracle.com/javafx/2/fxml_get_started/why_use_fxml.htm).
Będziemy generować go za pomocą narzędzia Scene Builder. Ale najpierw jeszcze przykłady na rozgrzewkę.

## 2 Proste przykłady

[Example6](src/main/java/com/example/javafxtutorial/Example6.java) pokazuje jak ustawić tło w naszej aplikacji.

[Example7](src/main/java/com/example/javafxtutorial/Example7.java) pokazuje jak dodać obrazek za pomocą ImageView.

### Zadanie 1

Skonstruuj applikację - przeglądarkę do memów. Ma ona umożliwiać przeglądanie obrazków, 
które są już w niej wgrane (w katalogu źródłowym ma ściągnięte pliki lub w kodzie na sztywno podane 
są adresy internetowe).

### Rozwiązanie

[Solution1](src/main/java/com/example/javafxtutorial/Solution1.java) przewinajnie za pomocą kliknięcia myszką w obrazek.


## 3 Scene Builder

Narzędzie można ściągnąć z tego [linku](https://gluonhq.com/products/scene-builder/#download). Po zainstalowaniu 
należy podpiąć Scene Builder-a do IntelliJ-a jak w tym [opisie](https://www.jetbrains.com/help/idea/opening-fxml-files-in-javafx-scene-builder.html).

Znajdź ścieżkę instalacji. Pod Windows wpisuje się ją w kreatorze. A instalator 
`.deb` instaluje domyślnie do `/opt/scenebuilder/bin/SceneBuilder`.

Tak jak w opisie klikamy `File->Settings->Languages&Frameworks->JavaFX` i wpisujemy powyższą ścieżkę.

Po kliknięciu na [hello-view](src/main/resources/com/example/javafxtutorial/hello-view.fxml) 
na dole ekranu mamy widoczne dwie opcje `Text` i `Scene Builder`, klikamy w tę drugą.
Nasz projekt nie miał Scene Buildera, ściągamy `Scene Builder Kit` jak 
w [opisie](https://www.jetbrains.com/help/idea/opening-fxml-files-in-javafx-scene-builder.html).

### Zadanie 2

Rozwiąż zadanie 1 za pomocą Scene Buildera. Stwórz dwa klawisze do przewijania w przód i w tył.

### Podpowiedź

Zacznij od stworzenia pustego pliku `.fxml` np klikając 
`r-click src/main/resources->New->FXML File`, a następnie 
ten plik otwórz w `Scene Builder`. Wygląda on [tak](src/main/resources/com/example/javafxtutorial/solution2a.fxml).

Z zakładki `Containers` wybierz np HBox i umieść na środkowym ekranie. 
Po lewej stronie na dole menu, pod AnchorPane, pojawił się VBox, można na niego 
kliknąć i wybrać opcję fit to parent. Teraz plik wygląda 
[tak](src/main/resources/com/example/javafxtutorial/solution2b.fxml).

Analogicznie z zakładki `Controls` wybierz ImageView i umieść na środkowym ekranie.
Następnie Button... Możesz zmieniać miejsca i rozmiary obiektów i 
przeglądać jak zmienia się plik `.fxml`. Zauważ, że po lewej stronie 
na dole możesz przełączać między dodanymi elementami. Struktura elementów 
przedstawiana jest jak struktura w systemie plików. Widać, że elementy ImageView i Button 
są dziećmi HBox. Teraz plik wygląda
[tak](src/main/resources/com/example/javafxtutorial/solution2c.fxml).

Gdyby najpierw dodać Button a potem HBox, to można przypisać Button do HBox 
tak jak się przenosi plik do katalogu. [Sprawdź](src/main/resources/com/example/javafxtutorial/solution2d.fxml)



Po prawej stronie możesz edytować wybrany element, np <br>
a. w zakładce Properties - zmienić nazwę z "Button" na "Do przodu" i "Do tyłu". <br>
b. w zakładce Code - ustawić id (np obrazek dla ImageView, przod, tyl dla Button) <br>
c. w zakładce Code - ustawić onAction (np idzDoPrzodu, idzDoTylu dla Button) <br>
Teraz plik wygląda [tak](src/main/resources/com/example/javafxtutorial/solution2e.fxml).

Generowanie kontrolera w IntelliJ uzyskujemy przez wyklikanie kolejnych brakujących klas i metod. 
W ten sposób otrzymujemy [taki wariant](src/main/java/com/example/javafxtutorial/Solution2f.java). 
Czemu tak się dzieje oraz jak zrobić by pojawiały się 
anotacje możemy doczytać [tu](https://stackoverflow.com/questions/47224993/intellij-is-it-possible-to-make-controller-as-in-netbeans/47226206#47226206).

Możemy jednak skorzystać bezpośrednio ze Scene Buildera.
```bash
/opt/scenebuilder/bin/SceneBuilder 
```
Otwieramy nasz [plik fxml](src/main/resources/com/example/javafxtutorial/solution2g.fxml).
Wybieramy `View->Show Sample Controller Skeleton->save as`. 
W ten sposób otrzymujemy taki [plik java](src/main/java/com/example/javafxtutorial/Solution2g.java).

### Rozwiązanie

Do kontrolera przekazujemy dane dopisując metodę `initData` i uruchaiając ją w wywoływaczu w metodzie `start`.
Zobacz [tu](src/main/java/com/example/javafxtutorial/Solution2Caller.java).

Kontroler wygląda [tak](src/main/java/com/example/javafxtutorial/Solution2Controller.java).


## 4. Zadanie

Dopisz interfejs do stworzonej wcześniej aplikacji (Mastermind, Szachy, BlackJack, ...)