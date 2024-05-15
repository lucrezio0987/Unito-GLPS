# SOFTWARE

>Caratteristiche di un prodotto software
> * MANTENIBILITA': dovrebbe essere scritto in modo da evolversi 
> in relazione alle nuove richieste dei clienti
> * FIDATEZZA: non dovrebbe causare danni fisici o economici nel caso 
> di malfunzionamento del sistema
> * EFFICIENZA: non dovrebbe fare un uso eccessivo delle risorse
> * ACCETTABILITA': deve essere comprensibile, usabile e compatibile

Più in generale un processo software descrive chi fa cosa, come e quando,
con l'intento di raggiungere un obiettivo. Descrive un approccio disciplinato
alla costruzione, al rilascio ed eventualmente alla manutenzione del software

### ATTIVITA' di PROCESSO
- #### 1. SPECIFICHE DEL SOFTWARE
Attività necessaria per capire e definire quali servizi sono richiesti dal 
sistema, e per identificare i vincoli allo sviluppo.

Le fasi principali sono
- Deduzione e analisi dei requisiti: discussione con gli utenti, analisi...
- Specifica dei requisiti: tradurre le informazioni raccolte in un documento
- Convalida dei requisiti: controlla che siano realistici, completi e coerenti

---
- #### 2. SVILUPPO DEL SOFTWARE
Attività di conversione delle specifiche in un sistema eseguibile, 
da consegnare al cliente. La progettazione e l'implementazione sono intrecciate 

Le attività principali sono:
- Progettazione dell'architettura: identifica la struttura complessiva del sistema,
dei componenti e delle loro distribuzioni
- Progettazione del database: progetta le struttura dati del sistema e come devono
essere rappresentate in un database
- Progettazione dell'interfaccia: definisce l'interfaccia dei componenti del 
sistema in modo che un componente possa essere usato da altri
- Progettazione e scelta dei componenti: si ricercano i componenti riutilizzabili 
o vengono progettati i nuovi componenti

Lo sviluppo di un programma segue la progettazione del sistema

---
- #### 3. CONVALIDA DEL SOFTWARE 
L'attività di verifica e convalida vuole mostrare che un sistema è conforme e soddisfa
le aspettative del cliente. Il test dei programmi è la tecnica principale di convalida.
Può richiedere anche attività di controllo, ispezione, revisione

Le attività principali sono:
- Test dei componenti: i componenti vengono testati dalle persone che sviluppano il sistema
- Test del sistema: si occupa di trovare gli errori causati da interazioni impreviste e 
problemi con le interfacce
- Test del cliente: il sistema viene testato dal cliente con i suoi dati

---
- #### 4. EVOLUZIONE DEL SOFTWARE
Il software può essere modificato in qualsiasi momento.
Il software viene modificato continuamente nel corso della sua vita per adeguarlo ai cambiamenti
dei requisiti e delle necessità dei clienti.

> I cambiamenti sono inevitabili, indipendentemente dal processo utilizzato
> Per ridurre i costi di rilavorazione si usano due approcci:
> * Anticipazione dei cambiamenti: quando il processo software include alcune attività
> che possono anticipare o predire possibili variazioni
> * Tolleranza ai cambiamenti: quando il processo e il software sono progettati in modo 
> che le modifiche possano essere facilmente apportate al sistema

---
### MODELLI DI PROCESSI SOFTWARE
Differenti tipi di sistemi richiedono differenti approcci di sviluppo.
Il software real-time deve essere specificato in maniera completa prima che inizi lo sviluppo, 
mentre nei sistemi di commercio elettronico le specifiche e il programma sono sviluppati assieme

Le 4 attività (specifica, sviluppo, convalida ed evoluzione) sono organizzate in modo diverso in 
processi di sviluppo diversi: in sequenza (a cascata) o intrecciate (sviluppo incrementale)

> * MODELLO DI PROCESSO SOFTWARE
> rappresentazione semplificata di un processo software: per creare processi software specifici
> * MODELLO A CASCATA
> le attività di processo fondamentali sono rappresentate come fasi distinte
> * SVILUPPO INCREMENTALE
> intreccia le attività di specifica, sviluppo e convalida
> * INTEGRAZIONE E CONFIGURAZIONE:
> si basa sulla disponibilità di un gran numero di componenti o sistemi riutilizzabili

Non esiste un modello di processo universale che si possa applicare appropriatamente a tutti 
i tipi di sviluppo del software. Il processo appropriato dipende dai requisiti dei clienti.

#### MODELLO A CASCATA
Si basa su uno svolgimento sequenziale delle diverse attività di sviluppo
* all'inizio del progetto vengono definiti in dettaglio tutti i requisiti
* all'inizio del progetto viene definito un piano temporale dettagliato delle attività da svolgere
* si prosegue con la modellazione
* quindi viene creato un progetto completo del software
* a questo punto inizia la programmazione del sistema software
* seguono verifica e rilascio del prodotto

Il modello a cascata era piuttosto comune fino a pochi anni fa
E' appropriato
* nei sistemi integrati, dove il software deve interfacciarsi con i sistemi hardware
* in sistemi critici dove occorre un analisi approfondita
* grandi sistemi software che fanno parte di sistemi più complessi
Non è appropriato
* in quei casi in cui i requisiti del software cambiano rapidamente

#### SVILUPPO INCREMENTALE
Si basa sull'idea di sviluppare un'implementazione iniziale, esporla agli utenti e perfezionarla,
finché non si ottiene il sistema richiesto
Le attività di specifica, sviluppo e convalida sono intrecciate anziché separate

* Lo sviluppo incrementale è adesso l'approccio più comune per sviluppare sistemi di applicazioni
* questo approccio può essere plan-driven, agile o una combinazione di questi
* in un approccio plan-driven gli incrementi del sistema sono stabiliti in anticipo
* in un approccio agile, vengono identificati gli incrementi iniziali, ma lo sviluppo degli 
incrementi successivi dipende dall'avanzamento del lavoro

Lo sviluppo incrementale è migliore dell'approccio a cascata per quei sistemi in cui i requisiti
è probabile che cambino durante il processo di sviluppo
Riflette il modo in cui risolviamo i problemi, raramente troviamo in anticipo la soluzione finale 
di un problema, ma ci si arriva tramite una serie di passaggi

> #### Sviluppo incrementale VS 'a cascata'
> * il metodo a cascata è una pratica mediocre per la maggior pare dei progetti software
> * L'approccio a cascata è caratterizzato da una minore produttività e da maggiori difetti
> * le stime iniziali a cascata dei tempi e dei costi variano dai valori finali

#### INTEGRAZIONE E CONFIGURAZIONE
Le fasi principali sono
* Specifica dei requisiti
* Ricerca e valutazione del software 
* Perfezionamento dei requisiti
* Configurazione del sistema delle applicazioni
* Adattamento e integrazione dei componenti

- Ha il vantaggio di ridurre la quantità di software da sviluppare, riducendo costi e tempi
- Sono però inevitabili compromessi nei requisiti
- Si perde il controllo sull'evoluzione del sistema

#### SVILUPPO INCREMENTALE, ITERATIVO ED EVOLUTIVO
* Comporta fin dall'inizio la programmazione e il test di un sistema software
* Comporta che lo sviluppo inizi prima che tutti i requisiti siano stati definiti 
* Viene usato il feedback per chiarire e migliorare le specifiche in evoluzione

- Nell'approccio iterativo, lo sviluppo è organizzato in una serie di mini-progetti brevi, 
di lunghezza fissa, chiamati ITERAZIONI
- Il risultato di ciascuna iterazione è un sistema eseguibile, testato e integrato
- Ciascuna iterazione comprende le proprie attività di analisi, progettazione, implementazione e test

Sviluppo iterativo e 
* INCREMENTALE: il sistema cresce in modo incrementale nel tempo, iterazione dopo iterazione
* EVOLUTIVO: il feedback e l'adattamento fanno evolvere le specifiche e il progetto

Nel processo iterativo non vi è fretta di iniziare la codifica, ne una fase di progettazione lunga
Il risultato di ciascuna iterazione è un sistema eseguibile ma incompleto
Il risultato di un'iterazione non è un prototipo ma un sottoinsieme del sistema finale

Esempi di processi di sviluppo iterativo ed evolutivo sono:
- UNIFIED PROCESS (UP)
- EXTREME PROGRAMMING (XP)
- SCRUM

Vantaggi
* riduzione precoce dei rischi maggiori
* progresso visibile fin dall'inizio
* feedback, coinvolgimento dell'utente
* gestione della complessità

Nei processi iterativi, in ogni iterazione viene stabilito il piano di lavoro per una sola 
iterazione, pianificazione iterativa o adattiva:
* UP: alla fine di ciascuna iterazione per l'iterazione successiva
* SCRUM: all'inizio di ciascuna iterazione per stabilire il piano dell'iterazione corrente

> La pianificazione è guidata dal rischio e guidata dal cliente:
> - Le iterazioni iniziali vengono scelte per identificare e attenuare i rischi maggiori
> - Per costruire e rendere visibili le caratteristiche a cui il cliente tiene di più
> - Stabilizzare il nucleo dell'architettura software

---
### SVILUPPO AGILE DEL SOFTWARE
I metodi per lo sviluppo agile di solito applicano lo sviluppo iterativo ed evolutivo
I metodi agili suggeriscono che il software deve essere sviluppato e consegnato in modo incrementale
I principi agili sono utili per due tipi di sviluppo di sistemi
- Lo sviluppo di prodotti di piccole e medie dimensioni
- Lo sviluppo personalizzato di sistemi all'interno di un'organizzazione dove c'è un chiaro
impegno da parte del cliente di essere coinvolto nel processo di sviluppo

Lo scopo della modellazione è quello di agevolare la comprensione e la comunicazione

> #### eXtreame Programming
> È uno dei metodi agili più noti
> Lo sviluppo è supportato attraverso piccole e frequenti release del sistema
> Le persone sono supportate dalla programmazione in coppia, dal processo collettivo e da un
> processo di sviluppo sostenibile

Tra le pratiche innovative dei metodi agili:
* STORIE UTENTE
sono scenari d'uso in cui potrebbe trovarsi un utente del sistema. 
* REFACTORING
le modifiche dovranno essere sempre apportate al codice che si sta sviluppando, 
* SVILUPPO CON TEST INIZIALI
lo sviluppo non può procedere finché tutti i test non sono stati superati
* PROGRAMMAZIONE A COPPIE
programmatori siedono realmente alla stessa postazione di lavoro di sviluppo del software

#### SCRUM
Offre un framework per organizzare progetti agili e fornire una visibilità esterna su ciò 
che sta accadendo, ossia si occupa dell'organizzazione del lavoro e della gestione dei progetti

Sono presenti 3 ruoli:
1. PRODUCT OWNER: definisce le caratteristiche del prodotto software da realizzare e 
specifica le priorità tra queste caratteristiche attraverso il product backlog
2. DEVELOPMENT TEAM: possiedono le competenze necessarie a sviluppare il software
3. SCRUM MASTER: aiuta l'intero gruppo ad apprendere e applicare scrum

Il risultato di ciascuno Sprint deve essere un prodotto software funzionante chiamato 
"incremento di prodotto potenzialmente rilasciabile". Deve essere integrato, verificato,
documentato per l'utente finale.

> PROBLEMI CON I METODI AGILI
> * l'informalità dello sviluppo agile è compatibile con l'approccio legale alla definizione 
> dei contratti che di solito si usano nelle grandi società
> * I metodi agili sono più indicati per lo sviluppo di nuovo software, non per la manutenzione 
> software
> * I metodi agili sono ideati per piccoli team fisicamente vicini

---