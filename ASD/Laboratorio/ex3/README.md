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



