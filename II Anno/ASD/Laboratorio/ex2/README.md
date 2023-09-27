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
