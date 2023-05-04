//TODO: una libreria che realizza la struttura dati SkipList implementando la seguente dichiarazione
  struct SkipList;

// possibile definizione del tipo di dati SkipList 
  struct SkipList {
  struct Node *head;                            //* primo nodo della SkipList
    size_t max_level;                           //* max numero di puntatori che AL MOMENTO ci sono in un singolo nodo 
    size_t max_height;                          //* costante che definisce il massimo numero di puntatori che POSSONO ESSERCI in un singolo nodo
    int (*compare)(const void*, const void*);   //* criterio di ordinamento
  };

  struct Node {
    struct Node **next;                         //* array di puntatori
    size_t size;                                //* numero di puntatori
    void *item;                                 //* dato memorizzato
  };
//------------------------------------------------------

  void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*));
//TODO: alloca una nuova skiplist, data l'altezza massima e la funzione di confronto, salvando la locazione di memoria allocata in *list

  void clear_skiplist(struct SkipList **list);
//TODO: libera correttamente tutta la memoria allocata per la SkipList, inclusi tutti i nodi interni e i dati in essi contenuti

  void insert_skiplist(struct SkipList *list, void *item);
//TODO: inserire un certo elemento(fornito come puntatore) nella skiplist

  const void* search_skiplist(struct SkipList *list, void *item);
//TODO: restituendo il puntatore a elemento con valore uguale ad item nella skiplist (NULL altrimenti)

//! UNIT TESTNG: Implementare gli unit-test per tutte le operazioni della SkipList

 /*
  * Il dizionario contiene un elenco di parole. 
  * Le parole sono scritte di seguito, ciascuna su una riga.
  *
  * Il file `correctme.txt` contiene un testo da correggere. 
  * Alcune parole in questo testo non ci sono nel dizionario.

  * Usare la struttura dati `*SkipList*` per determinare in maniera efficiente 
  * la lista di parole nel testo da correggere non presenti nel dizionario.
  */

//? L'applicazione deve implementare la seguente dichiarazione:

  void find_errors(const char *dictfile, const char *textfile, size_t max_height);

 /*
  * Si sperimenti il funzionamento dell'applicazione considerando diversi valori per il parametro ``max_height``, 
  * riportando in una breve relazione (circa una pagina) i risultati degli esperimenti.
  */