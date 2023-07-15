# Relazioni di ricorrenza
 > La funzione tempo di un algoritmo ricorsivo è ricorsiva e può essere
   descritta tramite una relazione di ricorrenza.

## Metodi di soluzione
 -  ITERAZIONE          (visto in 04)
 -  SOSTITUZIONE        (vediamo ora)

### Metodo della sosituzione
 - consideriamo:
    T(n) = { c   se n = 0  | T(n-1)+d   altiremnti
 - dimostriamo che la sostituzione sia:
    T(n)= c + nd
 - caso base:
    c + 0 d = c = T(0)
 - passo induttivo:
    dobbiamo dimostrare che     T(n) = c+nd     ⇒   T(n)+1 = c+(n+1)d
    secondo la rel. di ricorr.  T(n+1) = T(n)+d =
    utilizzando ip. induttiva          = c+nd+b = c+(n+1)d

## Quick-Sort
  [Corr]: induzione completa
    - Caso Base: n <= 1 il vettore è ordinato
    - Passo induttivo: corretto con dim < n --> corretto con dim = n
  [Comp]:
    - Caso Migliore:  O(n log n)
    - Caso Peggiore:  O(n^2)

## Partizionamento
  [Corr]: invariante di ciclo: A[2...i−1] ≤ A[1] ∧ A[1] < A[j+1...n]
  [Comp]:
    - Caso Migliore:  O(n)


# Divide et Impera

# ordinamento in O(n log n)
