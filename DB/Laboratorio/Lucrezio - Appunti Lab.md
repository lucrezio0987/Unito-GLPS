## Modello Entità-Relazione (**[ ER ]**)
  - Non modella il comportamento del sistema (come invece UML), ma *modella i dati*


    <Entità: Rappresentano aspetto del mondo reale come esistenza "autnoma" ai fini dell’applicazione di interesse> (oggetto, persona, evento, concetto, ...)
      [!] non sono entità: (cognome, data, età, mario rossi)
      - graficamente sono rettangoli
      - non hanno senso due entità con lo stesso nome
  
    <Occorrenze: un occorrenza di entità ha un esistenza indipendente>
    - un entità è un insieme delle sue occorrenze
  
    <Associazioni: rappresentano legami logici tra due o più entità>
     - stesse attività possono essere coinvolte da associazioni diverse
     - non possono essere collegate ad altre associazioni
     - non possono essere ripetute (tra le stesse entità)
     - possono coinvolgere più entità
     - non hanno un verso di lettura (non sono frecce)
     - possibile un associazione tra ensità e se stessa
    
    <Occorrenze_di_Associazioni:  coppie delle occorrenze delle entità che partecipano all’associazione>
     - un’associazione è come l’insieme delle sue occorrenze
     - in un insieme un elemento non può essere ripetuto
     - Un’associazione (a differenza delle entità) non ha un’esistenza “di per sé”, ma esprime un legame tra occorrenze di entità
     -  un’associazione n-aria richiede la partecipazione di un’occorrenza di almeno un’occorrenza per ognuna delle n entità coinvolte
  
    <Attributi: Proprietà di entità o associazioni>
    - Ogni attributo è caratterizzato dal suo dominio, l’insieme dei valori ammissibili per l’attributo
    - Un attributo assegna a un’occorrenza di entità o di associazione un valore appartenente al dominio dell’attributo
    
    <Cardinalità_delle_Associazioni: descrive il numero di occorrenze dell’associazione a cui l’occorrenza di entità può partecipare>
     - venono specificate Min e Max per ciascuna entità che partecipa a un’associazione
  
  ### Documentazione associata

  <Dizionario_dei_dati_per_le entità>
  <Dizionario_dei_dati_per_le associazioni>

  <Vincoli_di_integrità: /concetto deve o non deve /espressione_su_concetti>
  <Vincoli_di_derivazione: /concetto si ottiene /operazione_su_concetti>

## PROGETTAZIOME CONCATENATA
  > Raccolta e analisi dei requisiti e progettazione concenttuale
  + **Raccolta dei requisiti**
    - Utenti
    - Documentazione esistente
    - realizzazioni preesistenti
    
  + **analisi dei requisiti**
  + **costruzione del glossario**
  + **costruzione dello schema concettuale**

### Pattern di progettazione
  - [Reificazione_di_attributo_di_entità]: Trasformazione di un attributo in entità (un attributo sarebbe semplicemente una stringa, mentre un’entità ha una rappresentazione esplicita e più vincolata) 
  - [Part-of]: Due pattern per rappresentare un concetto parte di un intero:
      1.  la parte non esiste senza l’intero,
      2.  la parte può esistere senza l’intero.
  - [Instance-of]: Un pattern per rappresentare il concetto istanza-classe con due esempi analoghi
  - [Reificazione_di_attributo_di_un_associazione_binaria]: Trasformazione di un’associazione in entità se può essere ripetuta.
  - [Reificazione_di_attributo_di_un_associazione_ricorsiva]: Trasformazione di un’associazione ricorsiva in entità se può essere ripetuta.
  - [Reificazione_di_attributo_di_un_attributo_di_associazione]: Trasformazione di un attributo di un'associazione in entità se un concetto è importante: un attributo sarebbe semplicemente una stringa, mentre un’entità ha una rappresentazione esplicita e più vincolata.
  - [Storicizzazione_di_Entià]: Usiamo la generalizzazione per rappresentare le informazioni correnti di un’entità e tenere traccia dello “storico”.
  - [Storicizzazione_di_Associazione]: rappresentando separatamente associazione corrente e associazione «storica».
  - [Evoluzione_di_concetto]: Usiamo la generalizzazione per rappresentare l’evoluzione di un concetto mettendo nel genitore gli attributi e le associazioni comuni.
  - [Reificazione_di_associazione_ternaria]: nel caso in cui sia possibile avere ripetizioni di una stessa occorenza, dobbiamo  reificare l’associazione e aggiungere all’identificazione un attributo.

### Strategia di progetto

- [top-down]: A partire dalle specifiche si individuano e specificano i concetti cardine creando la struttura dello schema e successivamente mediante trasformazioni lo si raffina descrivendo i vari concetti con maggiore dettaglio
- [bottom-up]:  Le specifiche sono suddivise in parti elementari, che vengono poi tradotte in semplici schemi concettuali e poi fusi fino a giungere a uno schema completo
- [inside-out]: (variante do bottom-up) Si individuano alcuni concetti importanti e poi da questi ci si muove a macchia d’olio. Si rappresentano prima i concetti legati a quelli già definiti 
- [mista]: (In pratica) 
  1. si inizia con uno shcema scheletro
  2. poi viene decomposta, espansa e integrata attreverso le altre strategie.

### Qualità di uno shcema concettuale
  - Correttezza
  - Completezza
  - Leggibilità
  - Minimalità
  - (Equivalenza)
  - (aderenza)

## Progettazione logica

  [Obiettivo]: “tradurre” lo schema concettuale in uno schema logico che rappresenti gli stessi dati in maniera corretta ed efficiente

  **Ingresso**:
   - schema concettuale
   - informazioni sul carico applicativo
   - modello logico che si intende usare

  **Uscita**:
   - schema logico
   - vincoli di integrità
   - documentazione associata

  [Fasi]: 
    1. **Ristrutturazione dello schema concettuale** (EER)
      - *Analisi delle ridondanze*: si decide se eliminare o aggiungere ridondanze presenti nello schema
      - *Eliminazione delle generalizzazioni*: tutte le generalizzazioni presenti vengono analizzate e sostituite da altri concetti
      - *Partizionamento/accorpamento di entità e associazioni*: si decide se è opportuno partizionare o accorpare concetti dello schema unico concetto
      - *Scelta degli identificatori principali*: si sceglie un identificatore per le entità che ne hanno più di uno
      - *Eliminazione degli attributi multivalore*
      - *Eliminazione degli attributi composti*
    2. **Traduzione verso il modello logico e ottimizzazioni**

### Analisi delle PRESTAZIONI:
  [Indicatori]:
   - **Tempo**  :  numero di occorrenze di entità e di associazioni visitate per eseguire un’operazione sul DB
   - **Spazio** :  spazio di memoria necessario per rappresentare i dati
  
  [Informazioni_necessarie]:
  - *volume dei dati* (numero di occorrenze, dimensione degli attributi)
  - *caratteristiche delle operazioni* (operazione interattiva/batch, frequenza, entità/associazioni coinvolte)

### Analisi delle RIDONDANZE
  [def]: informazione significativa ma derivabile da altre

  + **Attributi derivabili**
    - da altri attributi della stessa entità o associazione
    - da attributi di altre entità o associazioni
  + **Associazioni derivabili** 
    - dalla composizione di altre associazioni in presenza di cicli

  [Vantaggi]: Operazioni di interrogazione/lettura dei dati: *Più efficienti*, *Semplificate*
  [Svantaggi]:
    - Operazioni di inserimento e modifica dei dati *Meno efficienti*
    - Maggiore occupazione di spazio

[...]
### Associazione uno a uno
[...]

### Traduzione in realzione
  suggerimento: ristrutturiamo e poi partiamo da Sede


-------------------

## SQL
