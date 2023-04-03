## PROBLEMI E ALGORITMI (intro e concetti base)

### Problemi Computazionali
  [def]: è una collezione di domande(*istanza*) per cui sia stabilito un *criterio* per riconoscere le *risposte* corrette.

  [esempio] (Massimo Comun Divisore)

  - <relazione_binaria: è un problema >
  $ R = \{(istanza, riposta) | istanza, riposta \ \ soddisfano ... \}$
  - <dominio di R>
  $ dom(R) = \{ i | \exist r. (i,r) \in R \}$
  - <univoca: Relazione R se ogni istanza ammette /una_sola_risposta>

  [esempi]:
    - moltiplicazione fra 2 interi:
        R = { ( (a,b) ,  c  )   |  a in Z, b in Z   , a * b = c }
                ^^^^^    ^         ^^^^^^^^^^^^^^     ^^^^^^^^^
              istanza  riposta    istanza,risposta    condizione

        R è univoca
    - fattoriale 
        R non è univoca
    - ordinamento
    - percorso ottimo in un grafo

### Algoritmo
  [def]: metodo meccanico per risolvere un problema computazionale

  <procedura: sequenza finita di operazioni meccanicamente eseguibili>
  <algoritmo: procedura che /termina per ogni ingresso ammissibile>
  <algoritmo_deterministico: due eseguzioni dello stesso algoritmo con gli stessi ingressi forniscono le stessse uscite>

### Algoritmi e programmi
  [programma]: 
   - può implementere uno o più algoritmi
   - occorre specificare ed implementere opportune strutture dati
   - scritto in uno specifico linguaggio di programmazione
  
   | Algoritmo + Struttura Dati = Programma |
  <funzione_input-output:> $ A(input) = output $


#### Algoritmo di Euclide
  [algoritmo] Euclid(a,b)         a > 0 v b > 0
  [pseudo-codice]:
  
    if b = 0 then
      retun a 
    else  b != 0
      ...

  [Corretetzza]:
    *{ se b > 0 allora MCD(A,b) = MCD(b,a mod b) }*
    se si riesce a dimostrare questo lemma allora l'algoritmo è verificato.
### Peak Finding
  [algoritmo]:
   - *Input*:  vettore $A[0...n-1]$ interi positivi
   - *Output*: intero $0 <= p < n$ tale che $ A[p - 1] <= A[p] >=A[p+1] $ 
               dove $ A[-1] =A[n] = - \infty $
  [Teorema]: 
    `Siano i ≤ j. Se A[i−1] < A[i] e A[j] > A[j+1] allora esiste i ≤ p ≤ j tale che A[p − 1] ≤ A[p] ≥ A[p + 1] ossia p è un picco in A[i..j].`

  [Num_confronti]:
   - *Peak-find-left* (caso peggiore) e *Peak-find-max* --> n-1 confronti
   - *Peak-DI* --> log(n) confronti  

----

## CORRETTEZZA e TERMINAZIONE

 <Corretezza_Parziale: uscita almeno una volta> vs <Corretezza_Totale: uscita sempre>
 
### Specifica di un Algoritmo
  [pre-condizione]  : ipotesi sull'ingresso
  [post-condizione] : proprietà dell'uscita

  > Ricorsione: `Un algoritmo è ricorsivo se nella sua definizione utilizza direttamente o indirettamente sé stesso.`
  > Induzione: `schema usato per verificare la correttezza di un algoritmo ricorsivo`
   1. Caso base:       P(1)
   2. Passo induttivo: P(M) --> P(M+1)
   3. 1 e 2 implicano che $ \all n >= 1   P(n) $
 
#### Problema: Torri di Hanoi
  + 3 pioli A,B,C
  + torre di n dischi
  * spostare la torre da A a C
  verifica algoritmo:

  [complessità]: complessità  O(2^n)

#### Problema: Divisione Intera Ricorsiva

### Indizione: Completa vs Semplice
 [Semplice]: nel passo induttivo il parametro è diminuito di  1
    il P.I. può essere    P(m)   ⟹ P(m+1)
    ma anche              P(m−1) ⟹ P(m)
 [Completa]: nel passo induttivo il parametro è diminuito di  b
    il P.I. può essere    P(0) ∧ P(1) ... ∧ P(m)   ⟹ P(m+1)
    ma anche              P(0) ∧ P(1) ... ∧ P(m−1) ⟹ P(m)

#### Esempio: x^2
  ~~~
    Rec-Calc(x)
      Pre: x ≥ 0
      Post: ...
      if x=0
        return 0
      else
        return Rec-Calc(x-1)+2x-1
  ~~~
[Caso_base]:è triviale
[Passo_induttivo]: 
  abbiamo:  $ Rec-Calc(n)   = Rec-Calc(n-1)+2n − 1        $
  dove:     $ Rec-Calc(n-1) =(n − 1)^2 = n^2 − 2n + 1     $
  quidi:    $ Rec-Calc(n)   = n^2 − 2n + 1 +2n − 1 = n^2  $

#### Esempio: Divisione intera
    ~~~ 
    
    ~~~

### Invariante di ciclo

  [def]: proposizione che esprime una proprietà delle variabili che resta sempre vero in vari punti dell'algoritmo:
   - **Inizializzazione** ( vale immediatamente _prima di entrare_ nel ciclo   )
   - **Mantenimento**     ( vale anche _dopo aver eseguito_ il corpo del ciclo )

## Terminazione

  <Congettura_di_Collatz>

  > *3n+1 vs fact*

  - nel fattoriale c'è una chiamata che decresce, in questo caso termina quando n=0.
  - il problema deriva dalla ricorsione
  
  <Ricorsione_di_coda: è in pratica l'iterazione (benché si utilizzi uno stack a differenza di questultima)>

  - si può dimostrare che il fattoriale abbia una struttura induttiva (*indizuone semplice / al successore*).

  [induzione_sulla_dimensione]: in algoritmi equivale all'*induzione completa*. 


## ORDINAMENTO
  
### Ricerca in un vettore ordinato
  - <Ricerca_lineare: il vettore viene esamniato elemento per elemento>
      [Cm] O(1)
      [Cp] O(n) 
  - <Ricarca_binaria: algoritmo di ricerca per cercare elementi in vettori ordinati>
      [Cm] O(1)
      [Cp] O(log n)

![Alt text](../Documenti/REPO_UNITO/ASD/complessit%C3%A0.png)
  
### Problema di oridinamento
  + **Forza_bruta**
    [Algoritmo]: confronta tutte le permutazioni possibili dell'array per trovare quella che risulta ordinata.

  + **Insertion-Sort**
    [Algoritmo]:
      INIZIO --> l'elemento è confrontato con gli elementi precedenti
          SE  trova un elemento più piccolo lo sposta indietro (con un while)
          SE  trova un elemento minore o uguale termina il ciclo più interno (while).
        FINE --> L'algoritmo cessa l'esecuzione quando termina il ciclo più esterno (for) 
    [Correttezza]:
      - Insertion-Sort è iterativo con due cicli
      - *invariante di ciclo* esterno
        > A[1...i−1] è ordinato.
      - *invariante di ciclo* interno
        > A[1...i−1] e A[j...i] sono ordinati
        > ciascun elemento in A[1...i−1] è <= di tutti gli elementi di A[j+1...i]

    [Complessità]:
      __Cm__: complessità temporale *lineare*       O(n)    (quando il vettore è già ordinato)
      __Cp__: complessità temporale *quadratica*    O(n²)   (quando l'arrey di partenza è ordinato in senso inverso)

+ **Selection-Sort**
    [Algoritmo]:
      INIZIO --> FOR [ i=0 ] -> [ n-1 ]
          sottociclo FOR [ j = i+1 ] --> [ n ]
          Si cerca il più piccolo elemento dell'array
          Si scambia l'elemento più piccolo con l'elemento alla posizione i
        FINE --> uscita
    [Correttezza]:
     - *invariante di ciclo* esterno
        > A[1...i−1] è ordinato.
     - *invariante di ciclo* interno
        > A[k] è Minimo in A[i..j-1]

    [Complessità]:  
      __Cm__: complessità temporale *quadratica*    O(n²)   (quando il vettore è già ordinato)
      __Cp__: complessità temporale *quadratica*    O(n²)   (quando l'arrey di partenza è ordinato in senso inverso)

### Alberi di decisione
  rappresenta graficamente le esecuzioni di un algoritmo
   <Nodi:   /Condizioni da valutare>
   <Rami:   particolari /Eseuzioni>
   <Foglie: possibili /Uscite>

  `In un albero binario per avere k foglie ci vogliono almeno log_2(k) livelli.`

[Ordinamento]:
  - n! foglie
  - nodi = confroni
  
  Nel caso dell'ordinamento il numero dei confronti deve essere dunque maggiore di: 
    $ log_2(n!) $  ovvero circa maggiore di $ n*log_2(n) $
  
  (quindi se un algortimo fa  n*log_2(n) confronti vuol dire che è un algoritmo ottimo e non può essere migliroato)


## TEMPO DI CALCOLO e COMPLESSITÀ ASINTITOICA

### Complessità di un algoritmo
  - **Tempo** 
  - **Splazio**
  - **Hardware**

### Complessità Temporale
  [Approcci]: 
   - num. di secondi (dipende dalla macchina)
   - num. delle operazioni elementari
   - num. delle volte che un operazione è eseguita

  [Dimensione_Ingresso]: La dimensione dell’ingresso è una misura della sua rappresentazione (a meno di una *costante moltiplicativa*)

    > T_migliore(n) = Min { T(x) : |x|=n }
    __Cm__ -->  T_migliore(|x|) = T(x)

    > T_peggiore(n) = Max { T(x) : |x|=n }
    __Cp__ -->  T_peggiore(|x|) = T(x)
  
  [Costati_Moltiplicative]: **contano poco**
   -  moltiplicando per una costante il tempo di calcolo, la massima *dimensione trattabile cambia poco*
   -  il *tipo di crescita* di una funzione *non dipende* dalla costante moltiplicativa
   -  la *stima esatta* delle costanti è *molto difficile* in pratica
  
  [Ordini_di_grandezza]:
  - trascura un numero finito dicasi
  - trascura le costanti moltiplicative

  >  f(n) `appartiene a` O(g(n))  `se e solo se`  ∃ c>0 ,  n_0  ∀ n>n_0  `.`  f(n) ≤ c*g(n)
    <O-grande: `g(n) è un limite superiore per la crescita a sintotica di f(n)`>
  [inclusioni] --> (Le funzioni esponenziali crescono più velocemente delle polinomiali).

    > O-grande: `limite asintitoco superiore`
    > Ω-grande: `limite asintotico inferiore`
    > Θ-grande: `limite asintotico superiore e inferiore`

  [Confini_Banali]:
  - **Dimensione dell'imput** (dati in ingresso)
  - **Dimensione dell'output** (elementi prodotti)
  - **Eventi contabili** (ripetizione di un evento il cui numero di volte sia necessaria alla soluzione del problema)

----

## Relazioni di ricorrenza
  La funzione T(algoritmo_ricorsivo) è ricorsiva e può essere descritta tramite una relazione di ricorrenza.

  [Metodi]:   ( Es: fattoriale )
  - **Iterazione**: ripetizione della ricorsione fino alla soluzione
    ```
      T(n) = T(n-1) + d           [ sappiamo che T(n−1) = T(n−2) + d ]
           = T(n−2) + d + d
           = T(n−2) + 2d
           = T(n-k) + kd          con k <= n
           
           = T(0) + nd = c + nd   --> Θ(n)
                        \______/
                        caso base
    ```
  - **Sostituzione**: ipotizzare una soluzione e provare a verificarla (tramite principio di induzione)
    ```
             / c            se n=0
      T(n) = \ T(n-1) + d   se n!=0 

      T(n) = c + nd           [soluzione ipotizzata da dimostrare con induzione]

      caso base:          c + 0d = c = T(0)
      passo induttivo:    T(n) = c + nd  --> T(n+1) = c + (n+1)d

      T(n+1) = T(n) + d       [secondo la relazione di ricorrenza]
             = c + nd + d     [utilizzando l'ipotesi induttiva]
             = c + (n+1)d     [dimostrato]
    ```
  [Relazioni_lineari_a_partizione_costante]:
    $  T(n) = T(n-1) + d  $
  
  ```  
    T(n) = c*T(n-1) + d
         = c * (c*T(n-2) + d) + d = c^2 * T(n-2) + cd + d =
         = c^" * (c*T(n-3) + d) + cd + d =  c^3 * T(n-3) + c^2 * d + cd + d
          ......
  ```

### Ordinamento Ricorsivo

 + **Partizionamento**
    [Algoritmo]:
    INIZIO --> Prendiamo pivot, i,j  :  A[ pivot, i, ..., j ]
          SE (i < pivot)    --> i++
            SE (pivot < j)  --> j--
            ALTRIMENTI      --> Scambio (i <-> j), i++, j--
    FINE -->  Incontro i e j --> Scambio (pitot <-> j)

    ``` PARTITION(A[1..n])
        i <- 2, j <- n
        while i<= j do
          if A[i] <= A[i] then
            i <- i+1
          else
            if A[j] > A[1] then
              j <- j-1
            else
              scambia A[i] con A[j]
              i <- i+1, j <- j-1
            end if
          end if
        end while
        scambia A[1] con A[j]
        return j
    ```
    [Correttezza]:
    - *invariante di ciclo*
      > A[2...i−1] ≤ A[1] ∧ A[1] < A[j+1...n]
    [Complessità]:
      __Cm__: complessità temporale *lineare*    O(n)   
      __Cp__:

 + **Quick-Sort**
    [Algoritmo]:
    ``` Quick-Sort(A[ 1..n ])
        if n > 1 then
          p <- PARTITION(A[1..n])
          if p > 2 then
            Quick-Sort(A[ 1..p-1 ])
          end if
          if p < n-1 then 
            Quick-Sort(A[ p+1..n ])
          end if
        end if
    ```
    [Correttezza]:
    - *induzione completa* (assumendo che il partizionamento funzioni correttamente)
      - Caso Base: n <= 1 il vettore è ordinato
      - Passo induttivo: corretto con dim < n --> corretto con dim = n
    [Complessità]: 
      __Cm__:                                       O(n*log(n))   
      __Cp__: complessità temporale *quadratica*    O(n²)         
    
### Minimo e Massimo
 + **Divide et Impera**
    [Algoritmo]: 
    ```DI-Min-Max (A, p, q)
          if p = q then return (A[p], A[p])
          if p = q - 1 then
            if A[p] < A[q] then return (A[p], A[q])
            else return (A[q], A[p])
          r <- (p + q)/2
          (min1, max1) <- DI-Min-Max (A, p, r)
          (min2, max2) <- DI-Min-Max (A, r +1, q)
        return (min (min1, min2), max(max1, max2))
    ```
    [Correttezza]:
    - 
    [Complessità]: 

### Relazioni lineari a partizione bilanciata
  + **Ricerca Binaria Ricorsiva**
  [Algoritmo]:
  ```BinSearch-Ric(x,A,i,j)
      if i>j then
        return false
      else 
        m <- (i+j)/2
        if x = A[m] then
          return true
        else
          if x<A[m] then
            return BinSearch-Ric(x,A,i,m-1)
          else
            return BinSearch-Ric(x,A,m+1,j)
          end if
        end if
      end if
    ```
   [Complessità]: 
    T(n) = Θ(log(n))

> metodo dell'iterazione
> metodo dell'induzione

------

## Programmazione Dinamica
 
  Programmazione dinamica (PD)    VS    Divide-et-Impera (DI)

  [Condizioni_di_applicabilità]:
  - Sottostruttira della soluzione
  - Sottoproblemi ripetuti

  [Fasi_di_sviluppo]:

### Successione di fibonacci