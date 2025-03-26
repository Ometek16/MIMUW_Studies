# wykład 5

# Monitory w bibliotece pthreads

- mutexy
- zmienne warunkowe

## Mutex

- mutex to taki zamek
- proces może go sobie zabrać

- na mutexie można wykonać operacje:
	- pthread_mutex_lock(&m)
	- pthread_mutex_unlock(&m)
- tylko proces, który ma mutex może wykonać unlock
- analogia z wchodzeniem do monitora

## Zmienne warunkowe

- na zmiennej warunkoewj c można wykonac operacje:
	- pthread_cond_wait(&c, &m)
	- pthread_cont_signal(&c) - wznawia conajmniej jeden wątek. Wątek wznawiany powinien być w posiadaniu mutexa

- wątek obudzony musi ponownie zdobyć mutex
- wątki wykonujace operacje na zmiennych warunkowych powinny być w posiadaniu mutexa, czyli być w monitorze
- specyfikacja nie gwaranguje że signal obudzi dokładnie jeden wątek - spontaniczne pobudki

## implementacja zgodna z pthreads

- z monitorem związujemy mutex m
- kazda funckje rozpczynamy od lock(m), kończymy unlock(m)
- operacje na zmiennych warunkowych realizujemy prawie bez zmian

# Model scentralizowany

- bez wsparcia ze strony sprzętu i systemu -> Algorytm Petersona
- Za pomoc¡ specjalnego, niepodzielnego rozkazu maszynowego
- Za pomoc¡ mechanizmów wspieranych przez system operacyjny i sprz¦t, np. semaforów lub monitorów


# Test_and_Set()

- niepodzielny rozkaz maszynowy
- w jednym ruchu odczytujemy i zapisujemy


# Linear Temporal Logic


