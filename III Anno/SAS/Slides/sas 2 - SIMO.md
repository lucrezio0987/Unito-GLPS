# UNIFIED PROCESS
Lo sviluppo iterativo è alla base del modo in cui l'analisi dei requisiti orientata agli 
oggetti (OOA) e la progettazione orientata agli oggetti (OOD) viene applicata al meglio

Unified Process è un processo iterativo per lo sviluppo del software orientato agli oggetti

### OOD/A
Unified Model Language non è OOA/D

La progettazione orientata agli oggetti pone l'enfasi sulla definizione di oggetti software
e del modo in cui questi collaborano per soddisfare i requisiti

L'OOD è fortemente correlata all'attività dell'analisi dei requisiti
* Casi d'uso
* Storie utente

L'analisi orientata agli oggetti pone l'enfasi sull'identificazione e la descrizione degli 
oggetti, ovvero dei concetti nel dominio del problema

1. Definizione dei casi d'uso: storie scritte
2. Definizione di un modello di dominio: i concetti o gli oggetti significativi del dominio
3. Assegnare responsabilità agli oggetti e disegnare diagrammi di interazione
4. Definizione dei diagrammi delle classi di progetto

L'analisi dei requisiti e l'OOA/D vanno svolte nel contesto di qualche processo di sviluppo
* processo di sviluppo iterativo
* approccio agile
* unified process (UP)

--- 

### UNIFIED MODEL LANGUAGE - UML
È un linguaggio visuale per la specifica, la costruzione e la documentazione degli elaborati
di un sistema software

* Punto di vista concettuale
la notazione dei diagrammi di UML è utilizzata per visualizzare concetti del mondo reale
* Punto di vista software
la notazione dei diagrammi delle classi di UML è utilizzata per visualizzare elementi software 

--- 

### UNIFIED PROCESS - UP
È un processo iterativo ed evolutivo per lo sviluppo del software per la costruzione di sistemi
orientati agli oggetti. Le iterazioni iniziali sono guidate dal rischio e dal cliente.
* Iterazioni corte
* gruppi di lavoro auto-organizzati
* Programmazione a coppie
* Modellazione agile

> Un progetto UP organizza il lavoro e le iterazioni in quattro fasi
> 1. Ideazione: visione approssimativa
> 2. Elaborazione: visione raffinata, implementazione iterativa
> 3. Costruzione: implementazione iterativa degli elementi rimanenti
> 4. Transizione: beta test, rilascio

Le attività lavorative in UP si eseguono nell'ambito di discipline
* una disciplina è un insieme di attività e dei relativi elaborati in una determinata area
* un elaborato è il termine generico che indica un qualsiasi prodotto di lavoro

Discipline di UP
* modellazione del business
* requisiti
* progettazione
* implementazione
* test
* rilascio
* gestione delle configurazioni e del cambiamento
* gestione progetto
* infrastruttura

Le fasi sono sequenziali, le discipline non sono sequenziali e si eseguono 
nel progetto in ogni iterazione. Il numero di iterazioni dipende dalla decisione del 
manager di progetto e dai rischi di progetto

---

#### Uso di UML in UP

UP usa solo UML come linguaggio di modellazione
I diagrammi UML si usano con variabilità 
I diagrammi si usano in UP seguendo le caratteristiche di iterazione e incremento
In UP quasi tutto è opzionale
La scelta delle pratiche e artefatti UP per un progetto si riassume in un documento 
chiamato scenario di sviluppo

--- 

### REQUISITI EVOLUTIVI
Un requisito è una capacità o una condizione a cui il sistema deve essere conforme

I requisiti derivano da richieste degli utenti del sistema, 
per risolvere problemi e raggiungere obiettivi

* requisiti funzionali: i requisiti comportamentali descrivono il comportamento del sistema
* requisiti non funzionali: le proprietà del sistema nel suo complesso

UP promuove un insieme di best practice, una delle quali è gestire i requisiti
In UP si iniziano programmazione è test quando è stato specificato solo il 10% o il 20% dei
requisiti più significativi dal punto di vista del valore di business, del rischio e dell'architettura

#### Requisiti in UP vs Requisiti 'a cascata'
UP incoraggia un'acquisizione dei requisiti attraverso tecniche quali:
* scrivere i caso d'uso con i clienti
* workshop dei requisiti a cui partecipano si sviluppatori che clienti
* gruppi di lavoro con rappresentanti dei clienti
* dimostrazione ai clienti dei risultati di ciascuna iterazione

Tipi di requisiti (modello FURPS+)
* Funzionale (F): requisiti funzionali e sicurezza
* Usabilità (U): facilità d'uso del sistema, documentazione e aiuto per l'utente
* Affidabilità (R): la disponibilità del sistema, la capacità di tollerare guasti
* Prestazioni (P): tempi di risposta, capacità e uso delle risorse
* Sostenibilità (S): facilità di modifica per riparazioni e miglioramenti
* altre (+): vincoli di progetto, interoperabilità, operazioni, fisici, legali

UP ha diversi elaborati
- modello dei casi d'uso: scenari tipici dell'utilizzo di un sistema
- specifiche supplementari: ciò che non rientra nei casi d'uso
- glossario: termini significativi, dizionario dei dati
- visione: riassume i requisiti ad alto livello, un documento sintetico
- regole di business: regole di dominio, i requisiti 

Non si è capito lo sviluppo iterativo UP se 
* si cerca di definire tutti i requisiti del software prima di iniziare la progettazione
o l'implementazione
* si dedicano giorni o settimane a modellare con UML prima di iniziare a programmare
* si pensa: ideazione = requisiti, elaborazione = progettazione, costruzione = implementazione
* si pensa che l'obiettivo dell'elaborazione sia quello di definire in maniera completa e 
dettagliata i modelli, che verranno tradotti in codice
* si pensa che la durata adeguata per un'iterazione siano 3 mesi al posto di 3 settimane
* si cerca di pianificare il progetto nei dettagli dall'inizio alla fine
