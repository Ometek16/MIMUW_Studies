# Wyklad 6

## Niezmienniki semaforów - formalizm Hammermana
Niech:
- #V(S) - liczba wykonanych instrukcji V(S)
- #P(S) - liczba *zakończonych* operacji P(S)
- S - aktualna wartosc semafora S
- k - początkowa wartość semafoa S

Wówczas:
- S >= 0
- S = k + #V(S) - #P(S)

## Wzajemne wykluczanie - dowód bezpieczeństwa
sk = #P(S) - #V(S)

- początkowo -> ok
- jak proces wchodzi do sk, to L = sk = 1 = 1 - 0 = R
- jak proces wychodzi z sk, to L = sk = 0 = 1 - 1 = R

sk = #P(S) - #V(S) = k - S = 1 - S <= 1, bo S >= 0
A zatem jest co najwyżej jeden proces w sekcji krytycznej


# Logika LTL - Linear Temporal Logic

$Prop$ - przeliczalby zbior zmiennych zdaniowych i $p \in Prop$

$ \phi ::= p$ zmienna zdaniowa\
$ ::= \perp$ fałsz\
$ ::= \phi -> \phi$ implikacja

$ ::= \square \phi$ zawsze\
$ :: \Diamond \phi$ kiedyś\
$ ::= \bigcirc \phi$ w następnym stanie\
$ ::= \phi \mathcal{U} \psi $ kiedyś w przyszłości zajdzie $\psi$ a do tego czasu zajdzie $\phi$


# Dowod bezpieczeństwa Algorytmu Petersona



