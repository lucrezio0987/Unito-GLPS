## Basi di dati
  [def]: Insieme organizzato di dati utilizzati per il supporto allo svolgimento di attività
  [Motivi]: 
    - Memorizzazione dei dati
    - Operazioni sui dati
    - Dati enormi
    - Accessi Concorrenti
    - Duplicazione del codice

### Data Base vs File System
 [DataBase-APPROCCIO]:
   - Il programmatore ha un *carico di lavoro più contenuto*
   - *Ridondanza controllata*
   - *Integrazione dei dati*
   - *Flessibilità* 
   - *Grande carico di lavoro* per la manutenzione
   - Servizi di *sicurezza*, *backup*, gestione *condivisione* forniti automaticamente
 
 [DataBase-SVANTAGGI]:
   - Se un’applicazione è _semplice_, usata da un _solo utente_, _statica_ e gestisce _pochi dati_
   - I DBMS sono _"costosi"_, _complessi_, _potenti_

### Sviluppare un'applicazione che usa un DBMS
   1. **Progettazione**
    - Creare uno schema concettuale
    - Creare uno schema logico
    - Definire la struttura del DB e i tipi di dati
   2. **Implementazione**
    - Creare le strutture sul DBMS
    - Popolare il DB
   3. **Scrivere applicazini che usano il DBMS** 

  [Schema_Logico]:
    > Tabelle

  [Interrogazione_DataBase]:
    <SQL: Structured Query Language>

  [Ottimizzazione_Query]:
    <Piano: albero di operatori dell’algebra relazionale e scelta dell’algoritmo per eseguire ogni operatore>

### Sistema Informatico `Saltata sembrerebbe`

### DataBase Relazionali
  (In questo corso ci focalizziamo sui database relazionali)

### DBMS
  [def]: *Sistema che gestisce collezioni di dati* grandi, persisteni e condivisi, garantendo privatezza, affidablità, efficienza, efficacia.
    <DBMS: Data Base Management Systems>

### Proprietà:
 + I Database sono:
   - **Grandi**
   - **Persistenti**
   - **Condivise** ([Problemi]: _Ridondanza_, _Incoerenza_ e _Concorrenza_)

 + I DBMS Garantiscono:
   - **Privatezza** 
   - **Affidabilità**
   - **Efficenza**
   - **Efficacia**

 [Transazioni]:Sequenza di operazioni atomiche, corretta anche in presenza di concorrenza e con effetti definitivi
 + Le Transazioni Sono:
   - **Atomiche**
   - **Concorrenti**
   

### Modello dei Dati
 [def]: Insieme di costrutti utilizzati per organizzare i dati di interesse e descriverne la dinamica.
  <Schema:  sostanzialmente invariante nel tempo, che ne descrive la struttura > (aspetto intensionale)
  <Istanza: i valori attuali, che possono cambiare anche molto rapidamente     > (aspetto estensionale)

### Tipi di Modelli (Principali)
 [Concettuali]: Permettono di rappresentare i dati in modo indipendente da ogni sistema:
   - cercano di descrivere i concetti del mondo reale
   - sono utilizzati nelle fasi preliminari di progettazione
   - es: _Modello entotà-relazione_
 [Logici]: Adottati per l’organizzazione dei dati
   - utilizzati dai programmi
   - indipendenti dalle strutture fisiche

### Architettura di un DBMS
 
 + **Semplificata**
    [Schema_logico]:  descrizione della base di dati nel *modello logico*
    [Schema_interno]: rappresentazione dello schema logico per mezzo di *strutture di memorizzazione*
 
 + **ANSI/SPARC**
    [Schema_interno]: rappresentazione dello schema logico per mezzo di *strutture fisiche di memorizzazione*
    [Schema_logico]:  descrizione dell’*intera base* di dati nel modello logico “principale” del DBMS
    [Schema_esterno]: descrizione di *parte della base* di dati in un modello logico


### Indipendenza dei dati

  > L’accesso avviene solo tramite il livello esterno  (che può coincidere con il livello logico)

  + **Indipendenza_Fisica**
    [Livello_logico] e [Livello_esterno] sono indipendenti da [Livello_interno]

  + **Indipendenza_Logica** 
    [Livello_esterno] è indipendente da [Livello_logico]

### SQL
  <SQL: Structured Query Language>
  
  [Operaziono]:
    - <DDL: /Crezione e /Modifica schemi di database>
    - <DML: /Inserire, /Modificare, /Gestire dati memorizzati>
    - <DQL: /Interrogare dati memorizzati>
    - <DCL: Creare e Gestire /Strumenti_di_controllo e accesso ai dati>

## Modello Relazionale
 
  ["Realzione":Accazzioni]
   - <Relazione_matematica: sottoinsieme del prodotto cartesiano (insieme di ennumple distinte, ciascna ordinata al suo interno)>
      (è posizionale, a differenza della Relazione nei database)
   - <Relazione_dei_dati: ho uno schema di relazione e specifico un insieme di attributi>
      (una base di dati è un insieme di relazioni.)
   - <Relazione_classe_di_fatti: /associazione>
 
  [Definizioni]:
   - <tupla: insieme di attributi> (relazione = insieme di tuple)

      R ( A1 ... An )  
      |   \_______/    
    nome  attributi
  
  [Tabella] Una tabella rappresenta una relazione se:
    - righe                 diverse fra loro
    - intestazioni colonne  diverse fra loro
    - ordine tra le righe   irrilevante
    - ordine tra le colonne irrilevante
    - ogni colonna a valori omogenei


  > Il modello relazionale è basato su valori.
  (nei database ogni attributo deve avere solo valori semplici) --> i concetti vengono scoporati e separati in tabelle diverse.

  <NULL: valore nullo ( /Sconosciuto - /Inesistente - /Senza_informazione ) >

  [Vincolo_di_Integrità]: Esistono istanze di basi di dati in cui *non è sufficiente rispettare i domini degli attributi* per rappresentare informazioni ammissibili per l’applicazione di interesse. (é un __Predicato logico__)

    Perche?
    - Descrizione più accurata della realtà
    - Contributo alla “qualità dei dati”
    - Utili nella progettazione
    - Usati dai DBMS nell‘esecuzione delle interrogazioni

  [Vincoli_di_tupla]:
   -  Esprimono condizioni sui valori di ciascuna tupla, indipendentemente dalle altre tuple
   -  Caso particolare: Vincoli di dominio (coinvolgono un solo attributo)

  [Vincoli_di_chiave]:

    <Superchiave: insieme di attributi usato per `identificare /univocamente le tuple` di una relazione> 
     - (non possono esserci due tuple diverse con stessa Superchiave)
     - un insieme di attributi Superchiave non può contenere tuple uguali.
    
    <chaive_candidata: (è una superchiave minimale) non contiene una superchiave, cioè se si toglie un attributo da K, K non è più superchiave>
    
    <chiave_primaria: particolare chiave scelta dal progettista>
     - non può assumere valori nulli
     - una e una sola
  
    ```
     Studenti(__Matricola__,Cognome,Nome,DataNascita)
     Corsi(__Codice__,Titolo,Docente)
     Esami(__Studente,Corso__,Data,Voto,Lode)
    ```
  [Vincoli_di_integrità_referenziale]: (vincoli di chiave esterna)
   - Garantiscono la correttezza dei riferimenti tra tabelle
   - Ogni vincolo ha un *verso*, cioè percorrendolo nel senso opposto cambia significato
    
    > Un vincolo di integrità referenziale fra un insieme di attributi X di una relazione R1 e una relazione R2 è soddisfatto se e solo se `R1 non  contiee valori X che non esistono in R2 (eccetto Null)`

      ```
        Esami(__Studente__)   REFERENZIA   Studenti(__Matricola__)
        Esami(__Corso__)      REFERENZIA   Corsi(__Codice__)
      ```
     I valori dell’attributo Studente nella relazione Esami devono comparire come valori di Matricola nella relazione Studenti


## Algebra Relazionale
+ [Operatori_di_base]:
  - **Selezione**             σ
  - **Proiezione**            π
  - **Prodotto cartesiano**   x
  - **Unione**                U
  - **Differenza**            -
  - **Riferimento**           ρ(B<-A)
+ [Operatori_derivati]:
  - **Intersezione**          ∩          
  - **Join**                  $\Join$
  - **Quoziente**             ÷

### Selezione
  > σ_pred Tab
  Data una relazione r su uno schema A, dove p è un predicato e r(A) è l'argomento dell’operatore.

### Proiezione
  > π_col Tab
  Data una relazione r(A) e un insieme di attributi Ai, Aj, ... Ak,tutti appartenenti ad A, produce come risultato una relazione con:
  - schema: {Ai, Aj, ... Ak}
  -  istanza: tutte le tuple della relazione argomento, ma solo rispetto agli attributi Ai, Aj, ... Ak

### Unione
  >  A \/ B
### Differenza
  >  A - B
### Intersezione    
  >  A /\ B
### Ridenominazione  
  >  ρ_(new<-old)

### Prodotto Cartesiano
  > A x B               `(Commutativo)`
  Attributi: A unione B
  Istanze: composizione di tutte le tuole di A con tutte le tuple di B

## Join
 - **Inner Join**
   - Θ-Join
   - Equi-Join
   - Netural-Join
   - Semi-Join
 - **Outer Join**
   - Left-Join
   - Right-Join
   - Full-Join

### θ-Join 
  > A $\Join_θ$ B       ` == σ_prep (A x B) `
  legge le tuple di A e verifica quali tuple di B soddisfano la condizione di join (Se la condizione è soddisfatta le tuple sono combinate e aggiunte nella tabella di join)

  <self-join: join in cui prendiamo 2 volte la stessa realazione> (possiamo usarla sapere le occorrenze che ci sono più volte)

[Casi_particolari_di_Join]:
### Equi-Join
  > A $\Join_θ_e$ B
  È un caso particolare del Θ-join in cui *i confronti sono solo uguaglianze*.
  
### Natural-Join
  (necessita: nomi uguali agli attributi che rappresentano lo stesso concetto)
  > A $\Join$ B
  Si sa gia qual è il *predicato*: Vengono messi in *uguaglianza tutti gli attributi omonimi*

### Semi-Join
  > A $\ltimes_θ$ B
  Funziona come un *filtro* sulla relazione A sfruttando la relazione B

---

### Interrograzioni con negazione
  Si tratta di un’interrogazione in cui la negazione non è essenziale 
  ES: "Elencare i pazienti non residenti a Torino" = " Elencare i pazienti con residenza diversa da Torino "

### Operatore di divisione
  > A ÷ B   ` -->   r(A,B) ÷ s(B) :=  π_A ((π_A(r) x s) - r)`
   produce una relazione u(A) che contiene le tuple che in r(A,B) compaiono in combinazione con ogni tupla in s(B)

   (cerca in A tutte le tuple che sono composte da una tupla di B)

### Semantica di Codd del valore nullo 
  (sembra abbastanza scontato...)
  --> Logica a tre valori (True, False, Sconosciuto)
  IS NULL
  IS NOT NULL

---

### Left Join
  > A $\Ljoin_θ$ B
  Conserva tutte le tuple della relazione A
### Right Join
  > A $\Rjoin_θ$ B
  Conserva tutte le tuple della relazione B
### Full Join
  > A $\Fjoin_θ$ B
  Conserva tutte le tuple della relazione A e B

----

### Proprietà delgi opeartori algebrici
 + [Commutative]
  - Prodotto cartesiano
  - θ-join
 + [Associative]
  - Prodotto cartesiano
  - ristretta θ-join:
      *  
 + [...]

[......]

## Ottimizzazione Logica

  [Fasi]: 
   1. **Analisi** lessicale, sintattica semantica
   2. **Ottimizzazione Logica**:
     - indipendente dalle strutture di memorizzazione
      _____________________                                     _____________________
     |                     |                                   |                     |
     | Albero sintattico   | -->  Proprietà dell'algebra  -->  | Albero sintattico   |
     | dell'interrogazione | -->     Relazionale          -->  |    equivalente      |
     |_____________________|                                   |_____________________|
  
   3. **Ottimizzazione Fisica**

  [Principio]: Ridurre la massa di tuple concettualmente coinvolte dall’interrogazione

  [Algoritmo]:
    1. *Decomporre gli AND*
    2. *Trasferire le selezioni verso le foglie* finché è possibile con le proprietà distributive della selezione
    3. *Trasferire le proiezioni verso le foglie* finché è possibile con le proprietà distributive della proiezione
    4. Ricondurre a *un’unica selezione le selezioni multiple* nello stesso nodo dell’albero
    5. *Riconoscere le sequenze di join*
    6. Ricondurre a *un’unica proiezione le proiezioni multiple*
    7. *Esaminare le varianti* dell’albero sintattico dovute alle proprietà associative scegliendo la variante *di costo minimo*
      |
      | --> approccio alternativo che *stima quantitativamente i costi* delle varie alternative
    
### Aspetti quantitativi delle interrogazioni
    (I DBMS mantengono nel dizionario dei dati una serie di informazioni di tipo statistico su ogni tabella r)

  [informazioni_statistiche_su_tabella]:

  - **CARD(r)**     --> *cardinalità* della relazione
  - **SIZE(t)**     --> *ampiezza della tupla* in byte
  - **VAL(Ai,r)**   --> *numero di valori distinti* che appaiono nella colonna Ai all’interno della tabella r

  - **MIN(Ai,r)**   --> valore minimo di Ai contenuto in r
  - **MAX(Ai,r)**   --> valore massimo di Ai contenuto in r
  - **NPAGE(r)**    --> numero di pagine occupate da r

  [Analisi_dei_costi_delle_interrogazioni]:
    > L'analisi quantitativa dell'interrogazione permette di predire ex-ante il risultato della cardinalità della relazione risultato senza eseguirla
  
  [Fattore_di_selettività]: 
    > probabilità che una tupla in r soddisfi il predicato di selezione p 
     (ovvero la stima della percentuale di tuple che soddisfano il predicato di selezione)

  --->  **Assumiamo**:
        -  una *distribuzione uniforme dei valori* all’interno delle varie colonne
          (ignoriamo il fatto che alcuni valori possono essere più probabili di altri)
        -  *assenza di correlazione tra attributi* diversi
  
  + **Predicati Atomici**: 
    $        Ai =  v       $   ---Stima_di_fp--->    $               1 / VAL(Ai,r)                   $
    $        Ai <= v       $   ---Stima_di_fp--->    $ (v - MIN(Ai,r)) / (MAX(Ai,r) - MIN(Ai,r))     $
    $   v <= Ai            $   ---Stima_di_fp--->    $ (MAX(Ai,r) - v) / (MIN(Ai,r) - MAX(Ai,r))     $
    $  v1 <= Ai >=v2       $   ---Stima_di_fp--->    $       (v2 - v1) / (MAX(Ai,r) - MIN(Ai,r))     $
  
  + **Predicati Composti**:
    $  p1 && p2 && ... pn  $   ---Stima_di_fp--->    $ fp1 * fp2 * ... * fpn                         $
    $        !p            $   ---Stima_di_fp--->    $ 1 - fp                                        $
    $  p1 || p2 || ... pn  $   ---Stima_di_fp--->    $ 1 - ((1 - fp1) * (1 - fp2) * ... * (1 - fpn)) $

  [Stima_della_cardinalità_del_join]: (prendiamo l' equi-join come esempio)
  
    $ |r(A) ⋈_(Ai=Bj) s(B)| = min{1/VAL(A i, r), 1/VAL(Bj, s)} × CARD(r) × CARD(s)  $

  [Euristica_del_DBMS_per_la_stima]:
    Posso stimare VAL(COD, σp1(pazienti)) partendo dai dati che il DBMS conosce.
    Il numero da stimare non può essere maggiore di:
    - $ VAL(COD,pazienti) = 105                                            $    (numero di tuple in pazienti con valori distinti per COD)
    - $ CARD(σ_p1 (pazienti)) = f_p1 × CARD(pazienti) = 1/100 × 105 = 103  $    (numero di tuple nel risultato della selezione σp1 )


## Calcolo Relazionale  
  > L’`algebra relazionale` è un linguaggio di tipo *procedurale*

  [Calcolo_relazionale_su_tuple_con_dichiarazione_di_range]: (base teorica di SQL)
    <calcolo_su_tuple: le variabili denotano `tuple`>
    <con_dichiarazione_di_range: permette di specificare qual è il range di valori (cioè le relazioni) che le variabili possono assumere>
  
  [Composizione_interrogazione]:    $ { T | L | F } $     (es: { p.Nome, p.Cognome | p(Pazienti) | p.Residenza='TO' })
   1. **Target** (T): 
      > Specifica quali attributi compaiono nel risultato
      - Introduce variabili abbinate a relazioni di base con la seguente sintassi
   2. **Range list** (L):
      > Specifica il dominio delle variabili libere (cioè non quantificate) in F
      - È un predicato del primo ordine che vincola le variabili della range list.
   3. **Formula** (F):
      > Specifica una formula logica che il risultato deve soddisfare
      - La target list è l’elenco delle informazioni che voglio in uscita
  
  [Variante_con_quantificazione_esistenziale]:
    - Supponiamo che nell’interrogazione siano richieste solo informazioni sul paziente e nessuna informazione sul ricovero.
  
      $ {p.Cognome,p.Nome | p(Pazienti),r(Ricoveri) | p.COD=r.PAZ Ù r.Reparto='A' }  $
    
    - Posso cancellare dalla target list il riferimento a ricovero e riformulare l’interrogazione utilizzando la quantificazione esistenziale (e quindi eliminarla dalla range list):

      $ {p.Cognome,p.Nome | p(Pazienti) | Esiste r(Ricoveri)(p.COD=r.PAZ Ù r.Reparto='A')} $

  [Sintassi_della_quantificazione_universale_ed_esistenziale]:
    - Esiste variabile(Relazione)(formula)
    - Per Ogni variabile(Relazione)(formula)
  
  