Esercizio 1

Sviluppare la classe generica Queue, che gestisce una coda di elementi di tipo generico T secondo la politica FIFO. La classe deve offrire i metodi dequeue() ed enqueue() per estrarre e inserire elementi dalla coda, e il metodo empty() che restituisce true se la coda è vuota, false altrimenti.

Il codice della classe Queue deve essere scritto astraendo da una particolare implementazione di una lista: non si implementi pertanto la classe Queue usando un ArrayList, bensì una List di elementi generici, in modo da permettere all'applicazione che crea la coda di scegliere la struttura dati di base da utilizzare per memorizzare i dati (ArrayList, LinkedList, o altro) e il tipo di dato da inserire in coda.

Sviluppare anche una semplice applicazione di test che crea una coda di Integer e una di Double, e le manipola. Verificate i controlli di tipo inserendo anche elementi di tipo sbagliato nelle code.
Esercizio 2

Riprendere la classe Geometries, e modificarla in modo da imporre che gestisca liste omogenee di poligoni. Si può ad esempio istanziare il tipo generico di Geometries sul tipo Rectangle oppure Triangle (per gestire rispettivamente liste di triangoli OR di rettangoli), evitando che l'utente possa invece inserire in una stessa lista oggetti di tipo disomogeneo (es. Rectangle AND Triangle).

Per lo sviluppo:

    si riutilizzi la gerarchia di Polygon che era stata sviluppata nei precedenti esercizi (abstract class Polygon, class Rectangle, class Triangle e così via).
    si definisca il tipo generico di Geometries in modo tale da permettere di gestire solo figure geometriche (e non, per esempio, stringhe di caratteri).
    la classe Geometries deve offrire i seguenti metodi:
        public int getNumElements: restituisce il numero di elementi che sono stati inseriti nella lista dei poligoni di Geometries
        public boolean add: permette di inserire un nuovo elemento nella lista dei poligoni
        public void printAreas: stampa il valore delle aree di ciascun elemento della lista di poligoni

Esercizio 3

Sviluppare la classe Calculator, che offre i seguenti metodi (statici) per fare calcoli su liste generiche di elementi di tipo numerico:

    un metodo print() che, data una lista generica di elementi passata come parametro, visualizza a video i valori di tali elementi
    un metodo sum() che, data una lista generica di elementi passata come parametro, restituisce la somma dei valori degli elementi della lista
    un metodo max() che, data una lista generica di elementi passata come parametro, restituisce il valore massimo della lista.

Sviluppare una semplice applicazione di test che crea liste di elementi (per es., una lista di Integer, una di Double) e invoca i metodi di Calculator per operare su di esse. Verificate i controlli di tipo anche passando ai metodi liste di elementi di tipo sbagliato.

Opzionale: per sfruttare a pieno il concetto di generici, nello sviluppo dei metodi di Calculator e dell'applicazione di test si cerchi di evitare l'utilizzo di switch e/o del costrutto instanceof per effettuare controlli sul tipo degli elementi presenti nelle liste.

