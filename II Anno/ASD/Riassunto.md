# APPUNTI ASD
## CAPITOLO 1

### Problema computazionale
> * domande (input) -> istanze
> * risposte (output) -> uscite
> * criterio (astratto)

La RELAZIONE si dice UNIVOCA se ogni istanza 
ammette una sola risposta

---------------------------------

Un algoritmo è un metodo meccanico per risolvere 
un problema computazionale

* PROCEDURA -> sequenza finita di operazioni meccanicamente 
eseguibili per produrre un'uscita 
* ALGORITMI -> procedura che termina per ogni ingresso ammissibile

Un algoritmo si dice DETERMINISTICO se eseguito sullo stesso input,
fornisce sempre lo stesso output

Un algoritmo risolve un problema se la sua funzione input-output 
associa ad ogni istanza di R una risposta.

------------------------------

### PEAK FINDING

> bisogna trovare la soluzione più ottimale
> 1. è possibile trovare un picco qualunque in minor tempo?
> 2. dato un segmento, cosa assicura che un picco esista al suo interno?

La soluzione più ottimale è il PEAK FINDING DIVIDE ET IMPERA 
in quanto viene ottimizzato il numero di tentativi attraverso un processo ricorsivo

**Peak-DI effettua all'incirca log2 n confronti**

-------------------------------

### PROBLEMI INSOLUBILI (o INDECIDIBILI)

Non c'è un algoritmo che possa restituire una risposta corretta o incorretta per ogni possibile input del problema.

-------------------------------

## CAPITOLO 2

BUGS sono tutti i problemi riscontrati, da cercare di eliminare o risolvere

> CORRETTEZZA TOTALE -> Si raggiunge sempre e l'uscita è corretta
> CORRETTEZZA PARZIALE -> Se si raggiunge, l'uscita è corretta

> PRE-CONDIZIONI -> Ipotesi sull'ingresso
> POST-CONDIZIONI -> Proprietà dell'uscita

Un algoritmo è ricorsivo se nella sua definizione usa direttamente 
o indirettamente se stesso

------------------------------

### INDUZIONE

Nell'esempio della torre di Hanoi, si risolve il problema con n dischi, richiamando 
l'algoritmo con n-1 dischi. Passo induttivo P(m) -> P(m + 1) -> INDUZIONE SEMPLICE

Per Div-rec la soluzione con input si trova richiamando l'algoritmo con input (a-b, b).
Parametro diminuito non di 1 ma di b -> INDUZIONE COMPLETA

-------------------------------

### L'INVARIANTE

E' una proposizione che esprime una proprieta delle variabili che resta sempre 
vero in vari punti dell'algoritmo

> INIZIALIZZAZIONE: la proposizione vale immediatamente prima di entrare nel ciclo
> MANTENIMENTO: se la proposizione vale prima di eseguire il corpo del ciclo, 
> allora vale anche dopo

L'invariante deve ESSERE UTILE: insieme alle condizioni che si hanno all'uscita dal
ciclo deve implicare qualcosa per la correttezza dell'algoritmo

-------------------------------

### TERMINAZIONE 

Non esiste alcun algoritmo che, data una procedura ed un ingresso, sia in grado di 
decidere se la procedura termini

Per costruire una funzione ricorsiva terminante
* assicurarsi che qualcosa nel valore dei parametri diminuisca
* garantire che il valore dei parametri e/o la loro dimensione non può crescere illimitatamente
* contemplare casi minimali come caso base

Esempio - fact(n) termina per ogni n
* caso base: fact(0) termina uguale a 1
* caso induttivo: se fact (n-1) termina, allora fact(n) termina

-------------------------------

## CAPITOLO 3

### ORDINAMENTO, ALGORITMI QUADRATICI

RICERCA IN VETTORE NON ORDINATO

Per cercare un elemento in un vettore non ordinato occorre esaminare 
l'intero vettore elemento per elemento

> Quanti confronti sono necessari? 
> * 1 nel caso migliore 
> * n nel caso peggiore

-------------------------------
RICERCA IN VETTORE ORDINATO (Ricerca dicotomica)  
Confrontiamo l'elemento centrale e quello ricercato

* Se sono uguali allora l'elemento è presente
* Se l'elemento cercato è più grande, cerco nella seconda metà del vettore
* Se l'elemento cercato è più piccolo, cerco nella prima metà del vettore

> Quanti confronti sono necessari? 
> * 1 nel caso migliore 
> * log n nel caso peggiore

-------------------------------
ORDINAMENTO PER INSERIMENTO  
Si vuole ordinare il vettore A[1..n]  
Quando la parte [1..i-1] è già ordinata, si può inserire l'elemento
A[i] tramite scambi

* Se A[i] >= A[i-1] è ordinato e ci si ferma, altrimenti si scambia A[i] con A[i-1]

> ### INSERTION SORT 
> INIZIO -> l'elemento è confrontato con quello precedente
> * se è più piccolo di quello precedente effettua lo scambio altrimenti termina
> * ripete poi il test con il valore ancora precedente e cosi via
> 
> FINE -> l'algoritmo termina quando tutti gli elementi sono stati controllati e quindi 
> il vettore risulterà ordinato

#### COMPLESSITA' DI INSERTION SORT
Nel peggiore dei casi insert-sort ha COMPLESSITA' TEMPORALE QUADRATICA  
Nel migliore dei casi insert-sort ha COMPLESSITA' TEMPORALE LINEARE

-------------------------------
ORDINAMENTO PER SELEZIONE
L'idea dell'algoritmo è
* assumiamo che la parte sinistra del vettore sia ordinato e quella a destra contiene 
elementi maggiori-uguali
* cerco l'elemento minimo in A[1..n] e lo scambiamo con A[1]
* cosi la parte ordinata si allarga (la disordinata diminuisce)

> ### SELECTION SORT
> INIZIO -> il vettore fino alla posizione I è ordinato, e gli elementi 
> rimanenti tutti > i
> * si cerca l'elemento più piccolo dell'array
> * si scambia l'elemento trovato con quello in posizione i

#### COMPLESSITA' DI SELECT SORT
Nel peggiore dei casi k viene aggiornato dopo ogni confronto  
Nel migliore dei casi il minimo si trova sempre all'inizio della parte non ordinata

In ogni caso Select Sort ha COMPLESSITA' TEMPORALE QUADRATICA

-------------------------------
ALBERI DI DECISIONE
Un albero rappresenta le esecuzioni di un algoritmo
* i nodi interni rappresentano decisioni da prendere
* le foglie rappresentano possibili uscite
* i rami rappresentano particolari esecuzioni

NEL CASO DELL'ORDINAMENTO
* n! foglie
* i nodi interni rappresentano confronti

In un albero per avere k foglie ci servono almeno log2 k livelli

-------------------------------

## CAPITOLO 4

### TEMPO DI CALCOLO E COMPLESSITA ASINTOTICA

Quante risorse usa l'algoritmo?
* tempo = quanto tempo ci mette
* spazio = quanta memoria occorre per eseguire l'algoritmo
* hardware = numero di processori, numero dei componenti di un circuito

-------------------------------
### COMPLESSITA' TEMPORALE
Per capire quanto tempo ci vuole e per stimare la grandezza massima dell'ingresso
di un'esecuzione ragionevole

DEFINIZIONE DEL TEMPO
* il numero dei secondi
* il numero delle operazioni elementari
* il numero di volte che un'operazione viene eseguita

> Moltiplicando per una costante il tempo di calcolo, la massima dimensione cambia poco
> Il tipo di crescita di una funzione NON dipende dalla costante moltiplicativa

>### Definizione di O, Ω, Θ
>* O, limite (o confine) asintotico superiore:
>
>f(n) ∈ O(g(n)) <-> ∃c > 0, n0 ∀n > n0 t.c. f(n) <= cg(n)
>* Ω, limite (o confine) asintotico inferiore:
>
>f(n) ∈ Ω(g(n)) <-> ∃c > 0, n0 ∀n > n0 t.c. cg(n) <= f(n)
>* Θ, limite (o confine) asintotico sia inferiore sia superiore
>
>f(n) ∈ Θ(g(n)) <-> ∃ c1 > 0, c2 > 0, n0∀n > n0 t.c. c1g(n) <= f(n) <= c2g(n)

-------------------------------

## CAPITOLO 5

### QUICK SORT
L'idea è
* se n <= 1 non fare niente, il vettore è ordinato
* scegli un elemento del vettore chiamato perno
* riorganizza il vettore in modo da avere all'inizio elementi <= q, 
seguiti da q, e in fondo gli elementi > q
* questo implica che q è al posto giusto
* ripeti tutto su A[1..p-1] e A[p+1..n]

#### PARTIZIONAMENTO
Il partizionamento può essere effettuato in vari modi, l'idea è
* come perno scegliamo A[1]
* due indici per il partizionamento i e j uguali rispettivamente a inizio e fine vettore

> * mando avanti i finchè A[i] >= perno A[1]
> * mando indietro j finchè A[j] <= perno A[1]
> * scambio A[i] e A[j]
> 
> Ripeto il ciclo finchè i <= j

### COMPLESSITA' TEMPORALE
* Partition scansiona una volta il vettore su cui opera
* l'ordine di grandezza del tempo di calcolo è lineare

-------------------------------

## CAPITOLO 6
### PROGRAMMAZIONE DINAMICA vs DIVIDE ET IMPERA

Per applicare programmazione dinamica serve che siano verificate le proprietà:
1. sotto struttura della soluzione: deve esserci una relazione fra le soluzioni dei sotto problemi
e la soluzione del problema
2. sotto problemi ripetuti

> Fasi
> 1. caratterizzazione della struttura di una soluzione
> 2. definizione ricorsiva della soluzione
> 3. eliminare le ripetizioni mediante annotazione dei risultati più semplici (memoization)
> 4. sviluppo di un approccio bottom-up, e dunque iterativo

#### FIBONACCI con MEMOIZATION
Lo spazio utilizzato da Fib-Memoization per array memo è Θ(𝑛)
-------------------------------

## CAPITOLO 7
### ARRAY, LISTE e TABELLE HASH

Strutture per rappresentare insiemi dinamici:
* numero finito di elementi
* gli elementi possono cambiare
* il numero di elementi può cambiare
* si assume che ogni elemento ha un attributo che serve da chiave
* le chiavi sono tutte diverse

Esistono due tipi di operazioni
* interrogazione (query)
* modifiche

Operazioni tipiche
* inserimento (insert)
* ricerca (search)
* cancellazione (delete)

Operazioni tipiche in caso di chiavi estratte da insiemi totalmente ordinati
* ricerca del minimo 
* ricerca del massimo
* ricerca del prossimo elemento più grande
* ricerca del prossimo elemento più piccolo

> La complessità
> * è misurata in funzione della dimensione dell'insieme
> * dipende da che tipo di struttura dati si utilizza per rappresentare l'insieme dinamico
> 
> Un operazione che è costosa con una certa struttura dati può costare poco con un'altra
> Quale operazioni sono necessarie dipende dall'applicazione

> Un array è una sequenza di caselle
> * ogni casella può contenere un elemento dell'insieme
> * le caselle sono grandi uguali e sono posizionati in una sequenza nella memoria
> * il calcolo dell'indirizzo di qualunque casella ha costo costante
> * con l'accesso diretto il tempo per leggere/scrivere in una cella è O(1)

----------------------------------------
#### ARRAY STATICO
E un array in cui il numero massimo di elementi è prefissato:
* M numero massimo di elementi
* N numero attuale di elementi
* Gli N elementi occupano sempre le prime N celle del vettore

Inserimento dell'elemento k nell'array A
* costo costante O(1), non dipende ne da N ne da M

Rimozione dell'elemento k dall'array A
* costo O(N)

Ricerca dell'elemento k nell'array A
* costo O(N)
* se array è ordinato, costo O(log N)

Inserire un elemento tenendo l array ordinato costa di più
Va inserito l'elemento in fondo, e poi si scorre ogni posizione (INSERTION-SORT)
questa operazione ha costo O(n)

#### ARRAY RIDIMENSIONABILE
Quanto costa un inserimento?
* se l array non è pieno il costo é O(1)
* se l array è pieno il costo è O(N) perché espandere ha un costo lineare
* quindi il costo dell'inserimento dipende dallo stato dell'array

Quindi a lungo andare, se l array si espande poche volte, il costo è circa O(1),
se l array si espande la maggior parte delle volte, il costo è circa O(N)

> Un'idea in concreto può essere
> * alloco spazio per M elementi
> * quando array è pieno raddoppiamo la dimensione potenziale dell array
> * per non sprecare, quando il vettore è occupato solo per 1/4 della dimensione, dimezziamo

----------------------------------------
### LISTE CONCATENATE
L'ordine è determinato dai puntatori che indicano l'elemento successivo. 
Data una lista L il primo elemento è indicato dal puntatore L.head.
* La lista può essere doppiamente concatenata, e può essere ordinata 
(secondo la chiave), o non ordinata. 
* La lista può essere circolare


#### Liste doppiamente concatenate e non ordinate
* La ricerca ha complessità O(n)
* L'inserimento in testa ha complessità O(1)
* Rimozione di un elemento puntato O(1)

Il LIST_DELETE è però macchinoso perché deve controllare le condizioni in testa e in coda
* aggiungiamo una sentinella che c'è sempre (un oggetto che non contiene dati)
* la complessità rimane O(1), ma il codice è molto più leggibile, non va controllato se il next è null o se il prev è l head
* anche la ricerca allo stesso modo non deve più controllare se x == Null ma solamente se x = L.sen
* la complessità della ricerca anche in questo caso è O(n)

----------------------------------------
### TAVOLE HASH
Le tabelle hash forniscono solo le operazioni di base (insert, search e delete), 
ma ognuna con tempo medio di O(1)

L'indirizzamento diretto non è praticabile se l'universo delle chiavi è grande e 
in ogni caso non è efficiente dal punto di vista della memoria utilizzata

**TAVOLE HASH**
L'indirizzamento non è più diretto
* L'elemento con chiave k si trova nella posizione h(k)

Conseguenze
* si riduce lo spazio utilizzato
* perdiamo la diretta corrispondenza fra chiavi e posizioni
* si possono creare però collisioni

HASH PERFETTO: una funzione che non crea mai collisione, cioè una funzione iniettiva

> Conclusione:
> In una tabella hash in cui le collisioni sono risolte mediante liste, nell'ipotesi di 
> uniformità semplice, una ricerca chiede in media un tempo di O(1)

Una buona funzione hash è uniforme semplice
* il metodo della divisione assegna alla chiave k la posizione h(k) = k mod m
* il metodo della divisione assegna alla chiave k la posizione h(k) = [m(Ak mod 1)]
* il valore di m non è critico, di solito si sceglie una potenza di 2

L'elemento con chiave k viene inserito nella posizione h(k) se essa è libera, 
se non lo è si cerca una posizione libera secondo uno schema di ispezione

Lo schema più semplice è l'ispezione LINEARE: l'elemento viene inserito
nella prima cella libera dopo h(k)

Per cancellare un elemento, non possiamo marcare semplicemente la posizione con nil,
si può marcare gli elementi cancellati con deleted

L'ispezione lineare crea file di celle occupate, fenomeno chiamato addensamento primario
L'ispezione quadratica invece dipende solo dal valore di hash, questo crea addensamento secondario

----------------------------------------

## CAPITOLO 8
### PILE e CODE

* I linguaggi di programmazione tipati forniscono tipi predefiniti
* Ogni tipo di dato è associato con un insieme di valori e operatori
* Ogni operatore funziona secondo certe regole

Un tipo di dato è ASTRATTO se è descritto prescindendo dalla sua concreta implementazione
Descrizione 
* la collezione dei dati: a partire da quali tipi di dati si costruisce un struttura del nuovo tipo
* le operazioni: che cosa devono fare le operazioni definite sul nuovo tipo
* complessità: eventualmente dei vincoli di complessità su tali operazioni

Un'implementazione concreta di un ADT è: 
* una struttura dati con cui memorizzare la collezione di dati
* una collezione di procedure con cui realizzare le operazioni

### PILE
In una pila i dati vengono estratti in ordine inverso rispetto a quello in cui sono stati inseriti

> Termini
> * push: inserire un elemento nella pila
> * pop: estrarre un elemento dalla pila (quello in cima)
> * top: restituisce l'elemento in cima

Assiomi
* size(pila), empty(pila), push(pila, elemento) sono sempre definiti
* pop(pila), top(pila) sono definiti se e solo se empty(pila) restituisce false
* empty(pila), size(pila), top(pila) non modificano la pila
* empty(pila) restituisce vero se e solo se size(pila) restituisce zero
* push(pila) incrementa size di 1, pop(pila) decrementa size di 1

Usiamo un array statico di M celle per definire un'implementazione concreta del ADT pila.  
Grazie al meccanismo LIFO conviene fare cosi:
* gli elementi presenti nell'array occupano sempre le prime posizioni dell'array
* quando ci sono N elementi, il prossimo elemento da estrarre è nella posizione N
* Push(pila, elemento) è definito solo se size(pila) < M

#### Implementazione con lista
Utilizziamo una lista per realizzare la pila.  
Conviene una lista semplice ma con sentinella per non dover fare controlli

Complessità temporale delle operazioni? Sono tutte O(1)  
Con l'array bisogna stabilire a priori il numero massimo di elementi, con le liste no

### CODE
In una coda i dati vengono estratti nell'ordine in cui sono stati inseriti

> Termini
> * enqueue: inserire un elemento nella coda
> * dequeue: estrarre un elemento dalla coda
> * front: restituisce il primo elemento nella coda

Assiomi
* size (coda), empty(coda), enqueue(coda, elemento) sono sempre definiti
* dequeue(coda) e front(coda) sono definiti se e solo se empty(coda) restituisce false
* empty(coda), size(coda), front(coda) non modificano la coda
* empty(coda) restituisce vero se e solo se size(coda) restituisce 0
* enqueue(coda) incrementa size di 1, dequeue(coda) decrementa size di 1

Useremo l'array in maniera "circolare" tenendo conto di dove si trova l'inizio(head)
e la fine(tail) della coda.  
* coda.head indica la posizione da dove estrarre l'elemento successivo 
* coda.tail indica la posizione dove inserire l'elemento successivo
* se coda.head == coda.tail -> allora la coda è vuota
* di conseguenza possiamo gestire M - 1 elementi in un array di M celle

#### Implementazione con lista
Utilizziamo una lista per realizzare la coda.  
Gli inserimenti vengono fatti in testa, le estrazioni in coda.  
Usiamo una lista semplice ma aggiungiamo un puntatore all'ultimo elemento della coda

* coda.head indica l'elemento da estrarre
* coda.tail indica l'ultimo elemento inserito
* se coda.head == nil -> allora la coda è vuota

Complessità temporale delle operazioni? Sono tutte O(1).  
Con l'array bisogna stabilire a priori il numero massimo di elementi, con le liste no

----------------------------------------

## CAPITOLO 9
### ALBERI

Dato un insieme A di etichette, l'insieme degli alberi su A, denotato con T(A),
è definito induttivamente
> * a ∈ A ^ T1 ∈ T(A) ^ T2 ∈ T(A) ^ ... ^ Tk ∈ T(A) con k >= 0   => {a, T1, T2,..., TK} ∈ T(A)

* Un albero è un grafo connesso aciclico 
* Un insieme di alberi è una foresta

#### Alberi radicati
* la radice è un nodo privilegiato di un albero
* una foglia è un nodo da cui non esce alcun arco
* un nodo che non sia una foglia di dice interno

> Cammino: sequenza di archi ciascuno incidente sul vertice di quello successivo
> Un cammino dalla radice a una foglia si dice ramo

> Livello: insieme di vertici equidistanti dalla radice
> L'altezza è la massima distanza dalla radice di un livello non vuoto

> Grado: massimo numero di figli di qualche nodo (alberi k-ari)

> Un albero è ordinato quando lo sono i suoi livelli

Per rappresentare un albero k-ario in ogni nodo:
* etichetta (key)
* k puntatori
Bisogna sapere k a priori e i nil possono occupare tanta memoria  
Come alternativa in ogni nodo si può avere
* etichetta (key)
* una lista di puntatori

> La cardinalità di un albero è il numero dei suoi nodi
> L'altezza è il massimo dei livelli, ossia il massimo delle lunghezze dei rami

La visita di un albero consiste in un'ispezione dei nodi dell'albero in cui ciascun
nodo sia "visitato" esattamente una volta
* visita in profondità (DFS): lungo i rami, dalla radice alle foglie
* visita in ampiezza   (BFS): per livelli, da quello della radice in poi.

COMPLESSITA' DELLE VISITE
* la dimensione n di un albero è la sua cardinalità
* per limitare il tempo possiamo contare quante operazioni push/pop avvengono in una DFS o BFS
* ogni nodo dell'albero viene inserito ed estratto esattamente una volta
* dunque DFS e BFS hanno costo O(2n) = O(n)