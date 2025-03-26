# Wykład 12

# Algorytm Uddinga

Zapewnienie braku zagłodzenia przy semaforach słabych.

**Założenie** ileprzy -> atomowe

```c
semafor bramka1 = 1, bramka2 = 0;
int ileprzy1 = 0, ileprzy2 = 0;

while (true) {
	wlasne_sprawy();

	P(bramka1);		// gwarantuje niepodzielnosc algorytmu
	ileprzy1++;
	V(bramka1);
	P(mutex);		// gwarantuje, że tylko jeden proces czeka na P(bramka1)

	P(bramka1);
	ileprzy2++;
	ileprzy1--;
	if (ileprzy1 > 0) 
		V(bramka1);
	else 
		V(bramka2);
	
	V(mutex);
	P(bramka2);
	ileprzy2--;

	sekcja_krytyczna();

	if (ileprzy2 > 0)
		V(bramka2)
	else
		V(bramka1);
}

```