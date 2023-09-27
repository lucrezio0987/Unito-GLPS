# Tempo di calcolo e complessità asintotica

- **Obiettivi**:
  * `introdurre le nozioni di tempo di calcolo e di confronto asintotico delle funzioni`

- **Argomenti**:
  * `caso peggiore e caso migliore`
  * `notazione asintotica`
  * `inclusioni tra classi asintoticamente limitate`
  * `complessità di un problema`

## Complessità temporale
 -  tempo di esecuzione
 -  stimare la grandezza massima dell’ingresso di un’esecuzione ragionevole
 -  confrontare l’efficienza di più algoritmi
 -  sapere quale parte del codice sarà eseguita più volte

 ## Definizione del tempo
 -  n. secondi
 -  n. operazioni elementari (ciascuna con un proprio coefficiente)
 -  n. volte che una specifica operazione viene eseguita

## Come confrontare funzioni
 -  per confrontare il tempo di calcolo di due algoritmi si confrontano le loro funzioni temporali
 -  Le costanti contano poco
 -  Ordini di grandezza: O-grande

### Ordini di grandezza di polinomi
 [Teorema]: Se p(n) è un polinomio di grado k allora p(n) appartiene a O(n^k)
 ( I termini di grado inferiore si possono ignorare. )

 [Teorema]: f(n) appartiene a O(g(n)) se e solo se lim_(n->inf) f(n)/g(n) esiste ed è in [0, +inf)

 [Teorema]: Se p(n) è un polinomio di grado h > k e a_h > 0, allora p(n) non appartiene a O(n^k)
 ( Tutto ciò che conta in un polinomio è il grado. )

### Base dei logaritmi
  O(log_a n) = O(log_b n), a, b > 1
  ( Quindi scriviamo semplicemente O(log n) )

### Inclusioni
  O(1)      ⊂ O(log n)
  O(log n)  ⊂ O(n)
  O(n)      ⊂ O(n log n)
  O(n^p)    ⊂ O(2^n)
  O(2^n)     ≠ O(3^n)

 ( f. esponenziali > f. polinomiali )

## Definizione di O, Ω, Θ
[O]: limite asintotico SUPERIORE
[Ω]: limite asintotico INFERIORE
[Θ]: limite asintotico STRETTO (sia inferiore che superiore).


## Confini banali
 -  Dimensione del input
 -  Dimensione del output
 -  Eventi contabili

## Complessità di un problema
 > Un algoritmo con tempo di calcolo T(n) ∈ O(g(n)) è ottimo per un certo problema
   se g(n) è un confine inferiore alla complessità del problema in termini di Ω.




