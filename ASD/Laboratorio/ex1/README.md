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
