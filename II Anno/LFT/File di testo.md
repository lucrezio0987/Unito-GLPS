- [AUTOMI A STATI FINITI](#automi-a-stati-finiti)
  - [Automi a Stati Finiti Deterministici \[ DFA \]](#automi-a-stati-finiti-deterministici--dfa-)
  - [Automi a Stati Finiti non-Deterministici \[ NFA \]](#automi-a-stati-finiti-non-deterministici--nfa-)
    - [NFA con ε-Transizioni \[ ε-NFA \]](#nfa-con-ε-transizioni--ε-nfa-)
- [Linguaggio Regolare  \[ RE \]](#linguaggio-regolare---re-)
- [Linguaggio non-Regolare \[ \]](#linguaggio-non-regolare--)
    - [chiusura di Kleene](#chiusura-di-kleene)
- [GRAMMATICHE LIBERE dal contesto \[ GFC \]](#grammatiche-libere-dal-contesto--gfc-)
  - [Alberi sintattici](#alberi-sintattici)
  - [Ambiguità](#ambiguità)
  - [Automi a Pila \[ PDA \]](#automi-a-pila--pda-)
  - [Autiìomi a Pila Deterministici \[ DPDA \]](#autiìomi-a-pila-deterministici--dpda-)
- [Pumping Lemma](#pumping-lemma)
  - [Forma normale di Chomsky \[ DNF \]](#forma-normale-di-chomsky--dnf-)
  - [Prorpietà di Chiusura dei lichiusura di Kleenenguaggi liberi](#prorpietà-di-chiusura-dei-lichiusura-di-kleenenguaggi-liberi)
- [PARSING](#parsing)
  - [Parsing top-down e grammatiche LL](#parsing-top-down-e-grammatiche-ll)
    - [stringe annullabili        \[ NULL(a)     \]](#stringe-annullabili---------nulla-----)
    - [inizi di una stringa       \[ FIRST(a)    \]](#inizi-di-una-stringa--------firsta----)
    - [seguiti da una variabile   \[ FOLLOW(A)   \]](#seguiti-da-una-variabile----followa---)
    - [iniemi guida               \[ GUIDA(A-\>a) \]](#iniemi-guida----------------guidaa-a-)
  - [Grammatiche LL(1)](#grammatiche-ll1)
  - [Parsing Ricorsivo Discendente](#parsing-ricorsivo-discendente)
- [TRADUZIONE](#traduzione)
  - [Definizione Diretta della Sintassi \[ SDD \]](#definizione-diretta-della-sintassi--sdd-)
- [ESERCIZI](#esercizi)



## AUTOMI A STATI FINITI
### Automi a Stati Finiti Deterministici [ DFA ]

### Automi a Stati Finiti non-Deterministici [ NFA ]
#### NFA con ε-Transizioni [ ε-NFA ]


## Linguaggio Regolare  [ RE ]

## Linguaggio non-Regolare [ ]


#### chiusura di Kleene

------------------------------------------------------------------------

## GRAMMATICHE LIBERE dal contesto [ GFC ]
[def] **[ G = (V,T,P,S) ]** :
        - *V* = insieme di variabili / simboli non-terminali
        - *T* = simboli terminali
        - *P* = insime finiti di produzioni *A -> a*:
            >  *A* = Testa
            >  *a* = Corpo
        - *S* = simbolo iniziale
    *L(G)* = Linguaggio generato da *G* ("libero" se generato da "grammatica libera")

### Alberi sintattici
[def] **Alberi sintattici di G**
        - [Nodo]    = etichettato da variabile *V*
            > se *A* e i suoi figli sono *X_1*,...,*X_n* -> *A -> X_1...X_n* è una produttoria 
        - [Fogia]   = etichettata da variabile *V*, *T* o *ε*
            > se *ε* -> unico figlio del suo genitore

### Ambiguità
[def] [una grammatica è ambigua se ammetti più alberi sintattici distinti con lo stesso prodotto.]

[strategia] - Si stratificano e sbilanciano le espresioni
            - [Espresione]  = somma di temrini
            - [Termine]     = prodotto di fattori
            - [Fattore]     = costante o espressioe tra parentesi
    
[nota]  **Linguaggio interamente abiguio** = non esiste essuna grammatica "non-ambigua" che lo generi.

### Automi a Pila [ PDA ]
       [approccio riconoscito per la descrizione di linguaggi liberi]
[def]   **[ A = (Q, Σ, Γ, δ, q_0, Z_0, F) ]**:
        - *Q*   = insieme finito di stati
        - *Σ*   = alfabeto di input 
        - *Γ*   = alfabeto della pila
        - *δ*   = funzione di transizione *[ δ: Qx(Σ U {ε})xΓ -> p(QxΓ\*) ]*
        - *q_0* = stato inizile
        - *Z_0* = simbolo iniziale
        - *F*   = insime di stati finali
[nota] **Descrizione Istantanea**: **[ (q, w, a) ]**
        - *q* = stato in cui si trova l'automa
        - *w* = ciò che rimane da riconoscere della stringa di input
        - *a* = contenuto della pila dalla cima al fondo

### Autiìomi a Pila Deterministici [ DPDA ]
[def]   **[ A = (Q, Σ, Γ, δ, q_0, Z_0, F) ]** in cui *δ(q,a,X) U δ(q,ε,X)* contiene al massimo un elemento.
        (a parità di stato, un DPDA può fare al massimo una mossa)

[nota]  *DFA = NFA = ε-NFA = RE*
        *DFA ⊊ DPDA ⊊ CFG non ambigue ⊊ DFG = PDA*

## Pumping Lemma
[ proprietà P soddisfatta da tutti i linguaggi liberi]
[Teo]  **[ ∀L ∃n∈N : ∀x∈L con |z|>=n  ∃u,v,w,x,y :  z = wvwxy ]** e : 
        - *vx ≠ ε*
        - *|vwx| <= n*
        - *u(v^k)w(x^k)y ∈ L* per *∀ k>=0*

### Forma normale di Chomsky [ DNF ]
[def]   Grammatica di cui ogni sua produzione è della forma (aut-aut):
        - **[ A -> BC ]**  dove *A*, *B*, *C* sono variabili
        - **[ A-> a ]**    dove *A* è una variabile e *a* un terminale
        (nessuna variabile è annullabile)

[teo]   se G è DNF e w è il prodotto dell'albero sintattico di G,
        se la profondità è n>=1, allora |w| <= 2^(n-1)

### Prorpietà di Chiusura dei lichiusura di Kleenenguaggi liberi
[teo]   LL sono chiusi          per *Unione* e *concatenzaione*
[oss]   LL non sono chiusi      per *interszione*

[teo]   *L*, *R* sono LL        --> *L⋂R* è LL
[oss]   LL non sono chiusi      per *complemento* e *differenza*

[teo]   *L* è LL                --> *L^(R)* è LL

----------------------------------------------------------------------------

## PARSING

### Parsing top-down e grammatiche LL
[problema]   dati *G* e *w*, determinare se *[ S -> a_1 -> ... -> w ]*
             (o se esiste un albero di G con radice S e prodotto w)
        >> automa corrispondente a G = PDA non deterministico
        >> DPDA = non è possibile trovarlo per alcun G

#### stringe annullabili        [ NULL(a)     ]
#### inizi di una stringa       [ FIRST(a)    ]
#### seguiti da una variabile   [ FOLLOW(A)   ]
#### iniemi guida               [ GUIDA(A->a) ]

### Grammatiche LL(1)
[def]   G è LL(1) se per ogni coppia di produttorei distinte *A->a* e *A->b* in P: 
        **[ GUIDA(A->a) ⋂ GUIDA(A->b) = ∅ ]**
 
[nome]  - L = stringa letta da SX a DX
        - L = il parser cerca di costruire ua "derivazione canonica sinistra"
        - 1 = il parser usa "un solo simbolo terminale" della stringa per scegliere la produzione

### Parsing Ricorsivo Discendente
[problema]   realizzazione pratica di un parser top-down
[idea]       usare la pila per "ricordare" il suffisso della forma sentenziale sinistra da riconoscere.
[strutura]   - una procedura per ogni variabile della grammatica
             - la procedura A riconosce le strnghe generate dalla grammmatica A
             - la procedura A usa il simbolo corrente e gli insiemi guida per scegliere la produzione A -> a_1|...|a_n per scrivere A
             - per ogni simbolo X scelta: terminale, variabile

----------------------------------------------------------------------------

## TRADUZIONE

### Definizione Diretta della Sintassi [ SDD ]
[def] {
        **Attributo**: descrive un informazione come coppia (nome,valore) associata ad un nodo.
        **Albero sintattico annotato**: albero sintattico in cui ogni noto può avere 0,... attributi
        **definizione diretta della sintassi**: grammatica le cui produzioni sono associate a 0,... regole semantiche che specificano come calcolare il valore degli attributi associati ai nodi
      }

[def] { 
        **Attributi sintetizzati**: il suo valore dipende da quello di attributi dei figli e da eventuali altri attributi del nodo stesso,
        **Attributi erreditait**: 
      }


------------------------------------------------------------------------------

## ESERCIZI

E = ( (0+2) 1* 0* )*            su {0, 1, 2}