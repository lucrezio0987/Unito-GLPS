# Teoria DB

[Forme normali]: Una forma normale è una proprietà di una base di dati relazionale che ne garantisce la “qualità”, cioè l’assenza di determinati difetti
  + NON è in forma normale:
    - presenta **ridondanze**
    - causa **anomalie** ( agg. canc. ins. )

[Normalizzaione]: Procedura che permette di trasformare schemi non normali in schemi che soddisfano una forma normale.

[Dipendenza funzionale]: relazione tra due o più attributi, in cui il valore di un attributo determina il valore di un altro o altri attributi.

    Dati una relazione r(A) e due sottoinsiemi X e Y di attributi di A, il vincolo di dipendenza funzionale  X -> Y (X determina Y) è soddisfatto se e solo se
    Per ogni t1, t2 in r(t1[X]=t2[X] implica  t1[Y]=t2[Y])

[Equivalenza]: due insiemi di d.f. F' e F'' sono equivalente se evolvono esattamente nello stesso modo.

[Teoria di Armstrong]:
+ **Assiomi**:
    - <Riflessività>    Y è sottoinsieme di X   , allora X -> Y
    - <Unione>          X -> Y e X -> Z         , allora X -> YZ
    - <Transitività>    X -> Y e Y -> Z         , allora X -> Z

+ **Corretta**:
    dato F, se è possibile dedurre  X -> Y tramite t.A.
        allora è possibile ricavare X -> Y tramite def. di d.f.

+ **Completezza**:
    dato F, se è possibile ricavare X -> Y tramite def. di d.f.
        allora è possibile dedurre  X -> Y tramite t.A.

+ **Regole Aggiuntive**
    - <Espansione>              X -> Y e W insieme di attrributi    , allora WX -> WY
    - <Decomposizione>          X -> YZ                             , allora X -> Y e X -> Z
    - <Pseudo-Transitività>     X -> Y e WY -> Z                    , allora WX -> Z
    - <Prodotto>                X -> Y e W -> Z                     , allora XW -> YZ

[Chiusura]: insieme F+ di tutte le dipendenze funzionali derivabili da F

[Equivalenza]: F equivalente a G se e solo se F+ = G+
[Equivalenza]: F equivalente a G se e solo se è possibile derivare G da F e viceversa

[Chiusura di un insieme di attributi]:
























