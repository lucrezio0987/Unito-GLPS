# Laboratorio per il corso di Algoritmi e Strutture Dati: regole d'esame, indicazioni generali e suggerimenti, consegne per gli esercizi

# Regole d'esame

---

**Importante**: gli studenti che hanno nel piano di studi l'insegnamento di Algoritmi con un numero di CFU **differente da 9** sono pregati di contattare il docente al più presto, al fine di concordare un programma d'esame commisurato ai CFU.

---

Il progetto di laboratorio può essere svolto individualmente o in gruppo (al più 3 persone). **I membri di uno stesso gruppo devono appartenere tutti allo stesso turno di laboratorio**.

ll progetto di laboratorio va consegnato mediante Git (vedi sotto) entro e non oltre la data della prova scritta che si intende sostenere. E' vietato sostenere la prova scritta in caso di mancata consegna del progetto di laboratorio. In caso di superamento della prova scritta, la prova orale (discussione del laboratorio) va sostenuta, previa prenotazione mediante apposita procedura che sarà messa a disposizione sulla pagina i-learn del corso, **nella medesima sessione della prova scritta superata** (si ricorda che le sessioni sono giugno-luglio 2023, settembre 2023, dicembre 2023 e gennaio-febbraio 2024).

Si noti che, per la sola sessione di giugno-luglio saranno previsti due appelli e, pertanto, esisteranno due possibilità per la discussione del laboratorio (primo o secondo appello della sessione). Nelle altre sessioni, l'appello è unico. Ad esempio, se la studentessa/lo studente X supera la prova scritta a dicembre 2023, deve necessariamente sostenere la discussione di laboratorio con la prova orale di dicembre 2023 (non sarà possibile discutere a gennaio-febbraio 2024).

Esempio:

- la studentessa/lo studente X sostiene la prova scritta nel primo appello di giugno;
- la studentessa/lo studente X deve assicurarsi che il progetto su GitLab, alla data della prova scritta che intende sostenere (in questo esempio, quella del primo appello di giugno), sia aggiornato alla versione che vuole presentare al docente di laboratorio;
- se la studentessa/lo studente X supera la prova scritta nel primo appello di giugno, deve (pena la perdita del voto ottenuto nella prova scritta) iscriversi a uno degli appelli orali di giugno o luglio, prenotarsi su i-learn in uno degli slot messi a disposizione dal docente del turno di appartenenza e sostenere l'orale nello slot temporale prenotato.

Le regole riportate sopra si applicano alla singola studentessa/al singolo studente. Per poter accedere alla discussione di laboratorio è in ogni caso necessaria l'iscrizione alla prova orale corrispondente su myunito.

Studentesse/studenti diversi, appartenenti allo stesso gruppo, possono sostenere la prova **scritta** nello stesso appello o in appelli diversi. Se studentesse/studenti diversi, appartenenti allo stesso gruppo, superano la prova scritta nello stesso appello, **devono** sostenere l' **orale** nello stesso appello orale. Se studentesse/studenti diversi, appartenenti allo stesso gruppo, superano la prova scritta in appelli diversi, **possono** sostenere l'**orale** in appelli diversi.

Ad esempio, si consideri un gruppo di laboratorio costituito dalle studentesse/dagli studenti X, Y e Z, e si supponga che i soli X e Y sostengano la prova scritta nel primo appello di giugno, X con successo, mentre Y con esito insufficiente. Devono essere rispettate le seguenti condizioni:

- alla data della prova scritta del primo appello di giugno, il progetto di laboratorio del gruppo deve essere aggiornato alla versione che si intende presentare;
- il solo studente X deve sostenere la prova orale nella sessione giugno-luglio,  procedendo come indicato nell'esempio riportato sopra, mentre Y e Z sosterranno la discussione quando avranno superato la prova scritta.
- Supponiamo che Y e Z superino la prova scritta nell'appello di gennaio: essi dovranno sostenere la prova orale nella sessione di gennaio-febbraio.
- Gli studenti Y e Z dovranno, di norma, discutere la stessa versione del progetto di laboratorio che ha discusso lo studente X; i.e., eventuali modifiche al laboratorio successive alla discussione di X dovranno essere debitamente documentate (i.e., il log delle modifiche dovrà comparire su GitLab) e motivate.

**Validità del progetto di laboratorio** : le specifiche per il progetto di laboratorio descritte in questo documento resteranno valide fino all'ultimo appello della sessione gennaio-febbraio relativa al corrente anno accademico **(vale a dire, quella di gennaio-febbraio 2024)** e non oltre! Gli appelli delle sessioni successive a questa dovranno essere sostenuti sulla base delle specifiche che verranno descritte nella prossima edizione del laboratorio di algoritmi.


# Indicazioni generali e suggerimenti

## Uso di Git

Durante la scrittura del codice è richiesto di usare in modo appropriato il sistema di versioning Git. Questa richiesta implica quanto segue:

- il progetto di laboratorio va inizializzato "clonando" il repository del laboratorio come descritto nel file Git.md;
- come è prassi nei moderni ambienti di sviluppo, è richiesto di effettuare commit frequenti. L'ideale è un commit per ogni blocco di lavoro terminato (es. creazione e test di una nuova funzione, soluzione di un baco, creazione di una nuova interfaccia, ...);
- ogni membro del gruppo dovrebbe effettuare il commit delle modifiche che lo hanno visto come principale sviluppatore;
- al termine del lavoro si dovrà consegnare l'intero repository.

Il file Git.md contiene un esempio di come usare Git per lo sviluppo degli esercizi proposti per questo laboratorio.

---

**Nota importante**: Su git dovrà essere caricato solamente il codice sorgente, in particolare nessun file dati dovrà essere oggetto di commit!

---

Si rammenta che la valutazione del progetto di laboratorio considererà anche l'uso adeguato di git da parte di ciascun membro del gruppo.

## Linguaggio in cui sviluppare il laboratorio

Gli esercizi vanno implementati utilizzando il linguaggio C o Java come precisato di seguito:

- Esercizio 1: C
- Esercizio 2: C
- Esercizio 3: Java
- Esercizio 4: Java

Come indicato sotto, alcuni esercizi chiedono di implementare codice generico. Seguono alcuni suggerimenti sul modo di realizzare codice con questa caratteristica nei due linguaggi accettati.

**Nota** : Con "codice generico" si intende codice che deve poter essere eseguito con tipi di dato non noti a tempo di compilazione.

**Suggerimenti (C)**: Nel caso del C, è necessario capire come meglio approssimare l'idea di codice generico utilizzando quanto permesso dal linguaggio. Un approccio comune è far sì che le funzioni e le procedure presenti nel codice prendano in input puntatori a `void` e utilizzino qualche funzione fornita dall'utente per accedere alle componenti necessarie.

**Suggerimenti (Java)**: La realizzazione di codice generico in Java deve basarsi sull’uso delle classi parametriche e dei cosiddetti “tipi generici”. Inoltre, è possibile (e consigliato) usare gli ArrayList invece degli array nativi al fine di semplificare la realizzazione di codice generico.

## Uso di librerie esterne e/o native del linguaggio scelto

È vietato (sia nello sviluppo in Java che in quello in C) l'uso di strutture dati native del linguaggio scelto o offerte da librerie esterne, quando la loro realizzazione è richiesta da uno degli esercizi proposti.

È, invece, possibile l'uso di strutture dati native del linguaggio o offerte da librerie esterne, se la loro realizzazione non è richiesta da uno degli esercizi proposti.

**Esempio:** nello sviluppo in Java, l'uso di ArrayList è da ritenersi possibile, se nessun esercizio chiede la realizzazione in Java di un array dinamico.

## Qualità dell'implementazione

È parte del mandato degli esercizi la realizzazione di codice di buona qualità.

Per "buona qualità" intendiamo codice ben modularizzato, ben commentato e ben testato.

**Alcuni suggerimenti:**

- verificare che il codice sia suddiviso correttamente in package o moduli;
- aggiungere un commento, prima di una definizione, che spiega il funzionamento dell'oggetto definito. Evitare quando possibile di commentare direttamente il codice interno alle funzioni/metodi implementati (se il codice è ben scritto, i commenti in genere non servono);
- la lunghezza di un metodo/funzione è in genere un campanello di allarme: se essa cresce troppo, probabilmente è necessario rifattorizzare il codice spezzando la funzione in più parti. In linea di massima si può consigliare di intervenire quando la funzione cresce sopra le 30 righe (considerando anche commenti e spazi bianchi);
- sono accettabili commenti in italiano, sebbene siano preferibili in inglese;
- tutti i nomi (es., nomi di variabili, di metodi, di classi, ecc.) *devono* essere significativi e in inglese;
- il codice deve essere correttamente indentato; impostare l'indentazione a 2 caratteri (un'indentazione di 4 caratteri è ammessa ma scoraggiata) e impostare l'editor in modo che inserisca "soft tabs" (cioè, deve inserire il numero corretto di spazi invece che un carattere di tabulazione);
- per dare i nomi agli identificatori, seguire le convenzioni in uso per il linguaggio scelto:
  - Java: i nomi dei package sono tutti in minuscolo senza separazione fra le parole (es. thepackage); i nomi dei tipi (classi, interfacce, ecc.) iniziano con una lettera maiuscola e proseguono in camel case (es. TheClass), i nomi dei metodi e delle variabili iniziano con una lettera minuscola e proseguono in camel case (es. theMethod), i nomi delle costanti sono tutti in maiuscolo e in formato snake case (es. THE\_CONSTANT);
  - C:  macro e costanti sono tutti in maiuscolo e in formato snake case (es. THE\_MACRO, THE\_CONSTANT); i nomi di tipo (e.g.  struct, typedefs, enums, ...) iniziano con una lettera maiuscola e proseguono in camel case (e.g., TheType, TheStruct); i nomi di funzione iniziano con una lettera minuscola e proseguono in snake case (e.g., the\_function());
- i file vanno salvati in formato UTF-8.

# Consegne per gli esercizi

**Importante**: Gli esercizi richiedono (fra le altre cose) di sviluppare codice generico. Nello sviluppare questa parte, si deve assumere di stare sviluppando una libreria generica intesa come fondamento di futuri programmi. Non è pertanto lecito fare assunzioni semplificative; in generale, l'implementazione della libreria generica deve restare separata e non deve essere influenzata in alcun modo dagli usi di essa eventualmente richiesti negli esercizi (ad esempio, se un esercizio dovesse richiedere l'implementazione della struttura dati grafo e quello stesso o un altro esercizio dovesse richiedere l'implementazione, a partire da tale struttura dati, di un algoritmo per il calcolo delle componenti connesse di un grafo, l'implementazione della struttura dati dovrebbe essere separata dall'algoritmo per il calcolo delle componenti connesse e *non* dovrebbe contenere elementi – variabili, procedure, funzioni, metodi, ecc. – eventualmente utili a tale algoritmo, ma non essenziali alla struttura dati; analogamente, se un esercizio dovesse richiedere di operare su grafi con nodi di tipo stringa, l'implementazione della struttura dati grafo dovrebbe restare generica e non potrebbe quindi assumere per i nodi il solo tipo stringa).

In sede di discussione d'esame, sarà facoltà del docente chiedere di eseguire gli algoritmi implementati su dati forniti dal docente stesso. Nel caso questi dati siano memorizzati su file, questi saranno dei csv con la medesima struttura dei dataset forniti e descritti nel testo dell'esercizio. I codici sviluppati dovranno consentire un rapido e semplice adattamento agli input forniti: ad esempio, **una buona implementazione consentirà di inserire in input il nome del file su cui eseguire il test**, mentre una peggiore richiederà di modificare il codice sorgente e una successiva compilazione a fronte della sola modifica del nome del file contenente il dataset.

Per alcune parti degli esercizi sarà anche fornita durante il corso una piattaforma per la valutazione automatica della correttezza del codice prodotto.

## Unit Testing

Come indicato esplicitamente nei testi degli esercizi, il progetto di laboratorio comprende anche la definizione di opportune suite di unit test.

Si rammenta, però, che il focus del laboratorio è l'implementazione di strutture dati e algoritmi. Relativamente agli unit-test sarà quindi sufficiente che gli studenti dimostrino di averne colto il senso e di saper realizzare una suite di test sufficiente a coprire i casi più comuni e i casi limite.

## Esercizio 1 - Merge-BinaryInsertion Sort

### Linguaggio richiesto: C

### Testo

Implementare una libreria che offre un algoritmo di ordinamento  *Merge-BinaryInsertion Sort* su dati generici, implementando il seguente prototipo di funzione:

```
void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void *, const void*));
```

- `base` è un puntatore al primo elemento dell'array da ordinare;
- `nitems` è il numero di elementi nell'array da ordinare;
- `size` è la dimensione in bytes di ogni elemento dell'array;
- `k` è un parametro dell'algoritmo;
- `compar` è il criterio secondo cui ordinare i dati (dati due **puntatori a elementi** dell'array, restituisce un numero maggiore, uguale o minore di zero se il primo argomento è rispettivamente maggiore, uguale o minore del secondo). 

Con *BinaryInsertion Sort* ci riferiamo a una versione dell'algoritmo *Insertion Sort* in cui la posizione, all'interno della sezione ordinata del vettore, in cui inserire l'elemento corrente è determinata tramite ricerca binaria. Il *Merge-BinaryInsertion Sort* è un algoritmo ibrido che combina *Merge Sort* e *BinaryInsertion Sort*.  L'idea è di approfittare del fatto che il *BinaryInsertion Sort* può essere più veloce del *Merge Sort* quando la sottolista da ordinare è piccola. Ciò suggerisce di considerare una modifica del *Merge Sort* in cui le sottoliste di lunghezza `k` o inferiore sono ordinate usando il  *BinaryInsertion Sort* e sono poi combinate usando il meccanismo tradizionale di fusione del *Merge Sort*. Il valore del parametro `k` dovrà essere studiato e discusso nella relazione. Ad esempio, `k=0` implica che *Merge-BinaryInsertion Sort* si comporta esattamente come il *Merge Sort* classico, mentre `k>>0` aumenta l'utilizzo del *BinaryInsertion Sort*.

In alternativa, è anche ammissibile implementare il seguente prototipo meno generico, che ordina dati a patto che siano organizzati in un array di puntatori:

```
void merge_binary_insertion_sort(void **base, size_t nitems, size_t k, int (*compar)(const void *, const void*));
```

- `base` è un puntatore al primo elemento dell'array di puntatori da ordinare sulla base dei valori riferiti;
- `nitems` è il numero di elementi nell'array di puntatori da ordinare;
- `k` è un parametro dell'algoritmo;
- `compar` è il criterio secondo cui ordinare i dati (dati due **elementi** dell'array di puntatori).

Dato che la prima versione è anche in grado di ordinare array di puntatori (passando un comparatore opportuno, i cui argomenti saranno puntatori a puntatori ai dati), non serve implementare questa seconda versione se avete già implementato la prima.

### Unit Testing

Implementare gli unit-test per la libreria secondo le indicazioni suggerite nel documento Unit Testing.

### Uso della libreria di ordinamento implementata

Il file `records.csv` che potete trovare (compresso) all'indirizzo

```
https://datacloud.di.unito.it/index.php/s/X7qC8JSLNRtLxPC
```

contiene 20 milioni di record da ordinare.
Ogni record è descritto su una riga e contiene i seguenti campi:

- `id`: (tipo intero) identificatore univoco del record;
- `field1`: (tipo stringa) contiene parole estratte dalla divina commedia,
  potete assumere che i valori non contengano spazi o virgole;
- `field2`: (tipo intero);
- `field3`: (tipo floating point).

Il formato è un CSV standard: i campi sono separati da virgole; i record sono
separati da `\n`.

Usando l'algoritmo implementato precedentemente, si realizzi la seguente funzione per ordininare *record* contenuti nel file `records.csv` in ordine non decrescente secondo i valori contenuti nei tre campi "field".

```
void sort_records(const char *infile, const char *outfile, size_t k, size_t field);
```

- `infile` è il percorso del file CSV contenente i record da ordinare;
- `outfile` è un percorso nel quale salvare i record ordinati (che deve essere diverso da `infile`);
- `k` è un parametro dell'algoritmo;
- `field` può valere 1, 2 o 3 e indica quale dei tre campi deve essere usato per ordinare i record.

Si misurino i tempi di risposta variando il valore di `k`, per ciascuno dei tre field che si possono usare come chiave di ordinamento, e si produca una breve relazione in cui si riportano i risultati ottenuti insieme a un loro commento. Dimostrare nella relazione come il valore di `k` dovrebbe essere scelto nella pratica. Nel caso l'ordinamento si protragga per più di 10 minuti potete interrompere l'esecuzione e riportare un fallimento dell'operazione. I risultati sono quelli che vi sareste aspettati? Se sì, perché? Se no, fate delle ipotesi circa il motivo per cui l'algoritmo non funziona come vi aspettate, verificatele e riportate quanto scoperto nella relazione. I risultati dipendono dal campo usato come chiave di ordinamento?

**Si ricorda che il file `records.csv` NON DEVE ESSERE OGGETTO DI COMMIT SU GIT!**

### Condizioni per la consegna:

- Creare una sottocartella chiamata ``ex1`` all'interno del repository.
- La consegna deve obbligatoriamente contenere un `Makefile`. Il `Makefile` deve produrre all'interno di ``ex1/build`` un file eseguibile chiamato ``main_ex1``.
- ``main_ex1`` deve ricevere come parametri il percorso del file CSV contenente i record da ordinare e del file in cui salvare i record ordinati. Per esempio:

```
$ ./main_ex1 /tmp/data/records.csv /tmp/data/sorted.csv
```

## Esercizio 2 - SkipList

### Linguaggio richiesto: C

### Testo

Realizzare una struttura dati chiamata *SkipList*. La *SkipList* è un tipo di lista concatenata che memorizza una *lista ordinata* di elementi.

Al contrario delle liste concatenate classiche, la *SkipList* è una struttura dati probabilistica che permette di realizzare l'operazione di ricerca con complessità `O(log n)` in termini di tempo. Anche le operazioni di inserimento e cancellazione di elementi possono essere realizzate in tempo `O(log n)`. Per questa ragione, la *SkipList* è una delle strutture dati che vengono spesso utilizzate per indicizzare dati.

Ogni nodo di una lista concatenata contiene un puntatore all'elemento successivo nella lista. Dobbiamo quindi scorrere la lista sequenzialmente per trovare un elemento nella lista. La *SkipList* velocizza l'operazione di ricerca creando delle "vie espresse" che permettono di saltare parte della lista durante l'operazione di ricerca. Questo è possibile perché ogni nodo della *SkipList* contiene non solo un singolo puntatore al prossimo elemento della lista, ma un array di puntatori che ci permettono di saltare a diversi punti seguenti nella lista. Un esempio di questo schema è rappresentato nella seguente figura:

![Esempio di una *SkipList*. Dal nodo che contiene il numero 6 si può saltare direttamente ai nodi 9 e 25, senza visitare gli altri nodi.](skiplist.png)

Si implementi quindi una libreria che realizza la struttura dati *SkipList* implementando la seguente dichiarazione:

```
struct SkipList;
```

L'implementazione deve essere generica per quanto riguarda il tipo dei dati memorizzati nella struttura. Come suggerimento, una possibile definizione del tipo di dati *SkipList* è il seguente:

```
struct SkipList {
  struct Node *head;
  size_t max_level;
  size_t max_height;
  int (*compare)(const void*, const void*);
};

struct Node {
  struct Node **next;
  size_t size;
  void *item;
};
```

Dove:

- `head` è il primo nodo della *SkipList*;
- `max_level` è il massimo numero di puntatori che **al momento ci sono** in un singolo nodo della *SkipList* (come si vede nella figura, ogni nodo può avere un numero distinto di puntatori);
- `max_height` è una costante che definisce il massimo numero di puntatori che **possono esserci** in un singolo nodo della *SkipList*;
- `compar` è il criterio secondo cui ordinare i dati (dati due puntatori a elementi);
- `next` è l'array di puntatori in un dato nodo della *SkipList*;
- `size` è il numero di puntatori in un dato nodo della *SkipList*;
- `item` è il dato memorizzato in un dato nodo della *SkipList*.

La libreria deve implementare tutte le seguenti dichiarazioni.

```
void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*));

void clear_skiplist(struct SkipList **list);

void insert_skiplist(struct SkipList *list, void *item);

const void* search_skiplist(struct SkipList *list, void *item);
```

##### newSkipList: alloca una skiplist vuota

La funzione deve allocare una nuova skiplist, data l'altezza massima e la funzione di confronto, salvando la locazione di memoria allocata in `*list`.

##### clearSkipList: dealloca una skiplist

La funzione deve liberare correttamente tutta la memoria allocata per la *SkipList*, inclusi tutti i nodi interni e i dati in essi contenuti. L'utilizzo inteso di queste prime due funzioni è ad esempio:

```
struct Skiplist* list = NULL;
new_skiplist(&list, 10, compare);
// ora list != NULL, posso usarla
// per inserire elementi: insert_skiplist(list, ptr); 
// per cercare elementi: search_skiplist(list, ptr) != NULL; 
clear_skiplist(&list);
// ora list == NULL e tutta la memoria è libera
```

##### insertSkipList: inserisce un elemento nella skiplist

La funzione deve inserire un certo elemento `item` nella skiplist `list`. L'elemento da inserire viene fornito come puntatore ad un dato generico, la cui "responsabilità" viene passata alla skiplist (che quindi dovrà deallocarlo quando verrà deallocata la skiplist). Una possibile implementazione di questa funzione in pseudo-codice (da tradurre quindi in C) è la seguente:

```
insertSkipList(list, item)

    new = createNode(item, randomLevel())
    if new->size > list->max_level
        list->max_level = new->size

    x = list->head
    for k = list->max_level downto 1 do
        if (x->next[k] == NULL || item < x->next[k]->item)
            if k < new->size {
              new->next[k] = x->next[k]
              x->next[k] = new
            }
        else
            x = x->next[k]
            k++
```

La funzione ``randomLevel()`` nel codice precedente determina il numero di puntatori da includere nel nuovo nodo e deve essere realizzata conformemente al seguente algoritmo. Spiegare il vantaggio di questo algoritmo nella relazione da consegnare con l'esercizio:

```
randomLevel()
    lvl = 1

    // random() returns a random value in [0...1)
    while random() < 0.5 and lvl < MAX_HEIGHT do
        lvl = lvl + 1
    return lvl
```

#####  searchSkipList: verifica se un elemento è presente nella skiplist

La funzione deve verificare se un elemento con valore uguale ad  `item` è presente nella skiplist `list`; restituendo `NULL` se nessuna corrispondenza viene trovata, e restituendo il puntatore all'elemento `item` memorizzato nella skiplist altrimenti. Una possibile implementazione di questa funzione in pseudo-codice (da tradurre quindi in C) è la seguente:

```
searchSkipList(list, item)
    x = list->head

    // loop invariant: x->item < item
    for i = list->max_level downto 1 do
        while x->next[i]->item < item do
            x = x->next[i]

    // x->item < item <= x->next[1]->item
    x = x->next[1]
    if x->item == item then
        return x->item
    else
        return failure
```

### Unit Testing

Implementare gli unit-test per tutte le operazioni della *SkipList* secondo le indicazioni suggerite nel documento Unit Testing.

### Uso delle funzioni implementate

All'indirizzo

```
https://datacloud.di.unito.it/index.php/s/taii8aA8rNnXgCN
```
potete trovare un dizionario (`dictionary.txt`) e un file da correggere (`correctme.txt`).

Il dizionario contiene un elenco di parole. Le parole sono scritte di seguito, ciascuna su una riga.

Il file `correctme.txt` contiene un testo da correggere. Alcune parole in questo testo non ci sono nel dizionario.

Si implementi una applicazione che usa la struttura dati `*SkipList*` per determinare in maniera efficiente la lista di parole nel testo da correggere non presenti nel dizionario dato come input al programma. L'applicazione deve implementare la seguente dichiarazione:

```
void find_errors(const char *dictfile, const char *textfile, size_t max_height);
```

- `dictfile` è il percorso del file contenente le parole del dizionario;
- `textfile` è il percorso del file contenente il testo da correggere;
- `max_height` è il parametro della *SkipList*;
- la funzione deve stampare a schermo le parole del testo non presenti nel dizionario, nell'ordine con cui appaiono nel testo da correggere.

Si sperimenti il funzionamento dell'applicazione considerando diversi valori per il parametro ``max_height``, riportando in una breve relazione (circa una pagina) i risultati degli esperimenti.

**Si ricorda che i file `dictionary.txt` e `correctme.txt` NON DEVONO ESSERE OGGETTO DI COMMIT SU GIT!**

### Condizioni per la consegna:

- Creare una sottocartella chiamata ``ex2`` all'interno del repository.
- La consegna deve obbligatoriamente contenere un `Makefile`. Il `Makefile` deve produrre all'interno di ``ex2/build`` un file eseguibile chiamato ``main_ex2``.
- ``main_ex2`` deve ricevere come parametri il percorso del dizionario da usare come riferimento e il file da correggere, in quest'ordine. Il risultato va stampato a schermo, con le parole ordinate come nel file da correggere. Per esempio:

```
$ ./main_ex2 /tmp/data/dictionary.txt /tmp/data/correctme.txt
cinqve
perpeteva
squola
domandrono
vuolessi
scrissi
corpito
wita
```

## Esercizio 3 - PriorityQueue

### Linguaggio richiesto: Java

### Testo

Si implementi la struttura dati *coda con priorità (PriorityQueue)*.

La struttura dati deve gestire tipi generici e consentire un numero qualunque e non noto a priori di elementi, implementando la seguente interfaccia:

```
public interface AbstractQueue<E> {
  public boolean empty(); // controlla se la coda è vuota
  public boolean push(E e); // aggiunge un elemento alla coda
  public E top(); // accede all'elemento in cima alla coda
  public void pop(); // rimuove l'elemento in cima alla coda
};
```

La classe `PriorityQueue<E>` che implementa l'interfaccia dovrebbe avere almeno un costruttore che crea una coda vuota e prende come argomento un `Comparator<E>` da usare per confrontare gli elementi.

### Unit Testing

Implementare gli unit-test degli algoritmi secondo le indicazioni suggerite nel documento [Unit Testing](UnitTesting.md).

### Condizioni per la consegna:

- Creare una sottocartella chiamata ``ex3-4`` all'interno del repository, che conterrà tutte le classi relative a questo esercizio e al seguente, compresi i file di progetto relativi all'IDE Java che avete utilizzato.

## Esercizio 4 - Grafi sparsi e foreste ricoprenti minime

### Linguaggio richiesto: Java

### Testo

Si implementi una libreria che realizza la struttura dati Grafo in modo che sia ottimale per dati sparsi
(**attenzione**: le scelte implementative che farete dovranno essere giustificate in relazione alle nozioni presentate
durante le lezioni in aula).

La struttura deve consentire di rappresentare sia grafi diretti che grafi non diretti
(*suggerimento*:  un grafo non diretto può essere rappresentato usando un'implementazione per grafi diretti modificata
per garantire che, per ogni arco *(a,b)* etichettato *w*, presente nel grafo, sia presente nel grafo anche l'arco *(b,a)*
etichettato *w*. Ovviamente, il grafo dovrà mantenere l'informazione che specifica se esso è un grafo diretto o non diretto.).

L'implementazione deve essere generica sia per quanto riguarda il tipo dei nodi, sia per quanto riguarda le etichette
degli archi.
La struttura dati implementata dovrà offrire (almeno) le seguenti operazioni (accanto a ogni operazione è specificata la
complessità richiesta; n può indicare il numero di nodi o il numero di archi, a seconda del contesto):

- Creazione di un grafo vuoto – O(1)
- Aggiunta di un nodo – O(1)
- Aggiunta di un arco – O(1)
- Verifica se il grafo è diretto – O(1)
- Verifica se il grafo contiene un dato nodo – O(1)
- Verifica se il grafo contiene un dato arco – O(1)  _(*)_
- Cancellazione di un nodo – O(n)
- Cancellazione di un arco – O(1)  (*)
- Determinazione del numero di nodi – O(1)
- Determinazione del numero di archi – O(n)
- Recupero dei nodi del grafo – O(n)
- Recupero degli archi del grafo – O(n)
- Recupero nodi adiacenti di un dato nodo – O(1)  _(*)_
- Recupero etichetta associata a una coppia di nodi – O(1) _(*)_
- Determinazione del peso del grafo (se il grafo non è pesato, il metodo può terminare con un errore)– O(n)

_(*)_ quando il grafo è veramente sparso, assumendo che l'operazione venga effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).

### Unit Testing

Implementare gli unit-test degli algoritmi secondo le indicazioni suggerite nel documento [Unit Testing](UnitTesting.md).

### Uso della libreria che implementa la struttura dati Grafo

Si implementi l'algoritmo di Prim per la determinazione della minima foresta  ricoprente di un grafo, secondo il seguente template:

```
public class Prim {
  public static void main(String[] args) {
    // leggi i dati CSV del grafo dal percorso in args[1] 
    // calcola la minima foresta ricoprente con l'algoritmo di Prim
    // scrivi su standard output una descrizione della foresta calcolata come CSV  
  }
}
```

L'implementazione dell'algoritmo di Prim dovrà utilizzare la struttura dati *PriorityQueue* implementata nell'esercizio precedente e la struttura dati grafo appena implementata.
La struttura dati e l'algoritmo di Prim dovranno poi essere utilizzati con i dati contenuti nel file `italian_dist_graph.csv`, che potete recuperare all'indirizzo:

```
https://datacloud.di.unito.it/index.php/s/PirTJpq4JMnpH3G
```

Tale file contiene le distanze in metri tra varie località italiane e una frazione delle località a loro più vicine. Il formato è un CSV standard: i campi sono separati da virgole; i record sono separati dal carattere di fine riga (`\n`).

Ogni record contiene i seguenti dati:

- `place1`: (tipo stringa) nome della località "sorgente" (la stringa può contenere spazi ma non può contenere virgole);
- `place2`: (tipo stringa) nome della località "destinazione" (la stringa può contenere spazi ma non può contenere virgole);
- `distance`: (tipo float) distanza in metri tra le due località.

**Note:**

- Nel caso in cui il grafo sia costituito da una sola componente connessa, l'algoritmo restituirà un albero. Nel caso in cui, invece, vi siano più componenti connesse, l'algoritmo restituirà una foresta costituita dai minimi alberi ricoprenti di ciascuna componente connessa.
- Potete intrepretare le informazioni presenti nelle righe del file come   archi **non diretti** (per cui probabilmente vorrete inserire nel vostro grafo sia l'arco di andata che quello di ritorno a fronte di ogni riga letta).
- Il file è stato creato a partire da un dataset poco accurato. I dati riportati contengono inesattezze e imprecisioni.
- Un'implementazione corretta dell'algoritmo di Prim, eseguita sui dati contenuti nel file `italian_dist_graph.csv`, dovrebbe determinare una minima foresta ricoprente con 18.640 nodi, 18.637 archi (non orientati) e di peso complessivo di circa 89.939,913 Km.

**Si ricorda che il file `italian_dist_graph.csv` NON DEVE ESSERE OGGETTO DI COMMIT SU GIT!**

### Condizioni per la consegna:

- Creare una sottocartella chiamata ``ex3-4`` all'interno del repository, che conterrà tutte le classi relative a questo esercizio e al precedente, compresi i file di progetto relativi all'IDE Java che avete utilizzato.
