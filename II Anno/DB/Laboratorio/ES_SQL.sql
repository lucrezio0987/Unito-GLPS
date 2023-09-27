---- Esercizio 3.1
-- Elencare tutti i fornitori con Status superiore a 20 e
-- la quantità delle parti eventualmente fornite
----
SELECT SName, 
       coalesce(Qty, 0)
FROM S LEFT JOIN SP ON S.SNum = SP.SNum
WHERE S.Status > 20;

---- Esercizio 3.2
-- Elencare i nomi di tutte le parti di colore verde e le
-- città dei loro eventuali fornitori
----
SELECT P.PName, 
       S.City
FROM P LEFT JOIN SP ON P.PNum = SP.PNum 
       LEFT JOIN S  ON SP.SNum = S.SNum
WHERE P.Color = 'Green';

---- Esercizio 3.3
-- Elencare tutti i fornitori che hanno forniture minori
-- di 200 parti (e quindi anche i fornitori che non
-- hanno fornito nulla). Il risultato deve comprendere
-- il nome del fornitore e la quantità delle parti
-- eventualmente fornite.
----

SELECT S.SName, 
       coalesce(Sum(QTY), 0)
FROM S LEFT JOIN SP ON S.SNum = SP.SNum
GROUP BY S.SName
HAVING SUM(QTY) <= 200 
    OR SUM(QTY) is Null;

---- Esercizio 3.4
-- Elencare tutte le coppie di parti disponibili nella
-- stessa città ma di colore diverso (mostrare codice
-- delle parti e nome della città)
----

SELECT P1.PNum Part1, 
       P2.PNum Part2, 
       P1.City Città
FROM P P1 JOIN P P2 ON P1.PNum < P2.PNum
WHERE P1.Color != P2.Color 
  AND P1.City   = P2.City
GROUP BY P1.City, 
         P1.PNum, 
         P2.PNum;

---- Esercizio 3.5
-- Elencare tutte le coppie di parti fornite dallo stesso
-- fornitore (mostrare nome del fornitore, codice e
-- nome delle parti) (suggerimento: scrivere prima la
-- query che mostra il codice del fornitore e i codici
-- delle coppie di parti dello stesso fornitore)
----

SELECT   S.SName  Fornitore, 
       SP1.PNum   Cod_1, 
        P1.PName  Nome_1, 
       SP2.PNum   Cod_2, 
        P2.PName  Nome_2
FROM S JOIN SP SP1 ON   S.SNum = SP1.SNum
       JOIN SP SP2 ON SP1.SNum = SP2.SNum
	   JOIN  P P1  ON SP1.PNum =  P1.PNum
	   JOIN  P P2  ON SP2.PNum =  P2.PNum
WHERE SP1.PNum < SP2.PNum;

---- Esercizio 4.1a
-- Estrarre la quantità totale di parti rosse fornite da
-- ciascun fornitore (mostrare nome del fornitore e
-- quantità totale di parti). Controllare che nel
-- risultato non siano presenti i fornitori che non
-- forniscono parti rosse come Adams, che non fornisce
-- nessuna parte, e Blake, che fornisce solo parti
-- verdi.
----

SELECT S.SName	SupplierName, 
       SUM(QTY)	TotQTY
FROM S JOIN SP ON  S.SNum = SP.SNum
	   JOIN  P ON SP.PNum =  P.PNum
WHERE P.Color = 'Red'
GROUP BY S.SName
ORDER BY TotQTY DESC;

---- Esercizio 4.1b
-- Estrarre la quantità totale di parti rosse fornite da
-- ciascun fornitore compresi i fornitori che non
-- forniscono nessuna parte , per i quali dovete
-- mostrare 0 (mostrare nome del fornitore e quantità
-- totale di parti). Nel risultato ci aspettiamo di avere
-- Adams, che non fornisce nessuna parte, ma non
-- Blake, che fornisce solo parti verdi.
----

SELECT S.SName	SupplierName, 
       coalesce(SUM(QTY),0)	TotQTY
FROM S LEFT JOIN SP ON  S.SNum = SP.SNum
	   LEFT JOIN  P ON SP.PNum =  P.PNum
WHERE P.Color = 'Red'
   OR SP.PNum is Null
GROUP BY S.SName
ORDER BY TotQTY DESC;

---- Esercizio 4.1c
-- Estrarre la quantità totale di parti rosse fornite da
-- ciascun fornitore compresi i fornitori che non
-- forniscono nessuna parte rossa (mostrare nome del
-- fornitore e quantità totale di parti) (suggerimento:
-- sfruttare la condizione del join). Nel risultato ci
-- aspettiamo di avere sia Adams, che non fornisce
-- nessuna parte, che Blake, che fornisce solo parti
-- verdi.
----

SELECT S.SName				SupplierName, 
       coalesce(SUM(QTY),0)	TotQTY
FROM P        JOIN SP  ON  P.PNum  = SP.PNum 
                      AND  P.Color = 'Red'
        RIGHT JOIN  S  ON SP.SNum  = S.SNum
GROUP BY S.SName
ORDER BY TotQTY DESC;

---- Esercizio 4.2
-- Considerando solo forniture di oltre 100 parti,
-- estrarre le città in cui i fornitori, considerati
-- insieme, forniscono in totale almeno 1000 parti
-- (mostrare la città e la quantità totale di parti)
----

SELECT S.City   Città, 
       Sum(Qty) TotQTY
FROM S JOIN SP  ON  S.SNum = SP.SNum 
               AND SP.Qty  > 100
GROUP BY S.City
HAVING Sum(Qty) > 1000;

---- Esercizio 4.3
-- Estrarre le città in cui ci sono almeno due fornitori
-- che hanno fornito ognuno almeno due prodotti di
-- diverso colore (suggerimento: scrivere prima la
-- query che estrae le informazioni sulle coppie di
-- parti di diverso colore fornite dallo stesso
-- fornitore)
----

SELECT S.City
FROM S JOIN SP SP1 ON S.SNum = SP1.SNum
	   JOIN SP SP2 ON S.SNum = SP2.SNum AND SP1.PNum < SP2.PNum
       JOIN P P1 ON SP1.PNum = P1.PNum
       JOIN P P2 ON SP2.PNum = P2.PNum AND P1.Color < P2.Color
GROUP BY S.City;

--[ CONTROLLARE ]--

---- Esercizio 5.1
-- Elencare i fornitori che forniscono parti disponibili a
-- Londra (sia con costrutto in/not in che con costrutto 
-- any/all)
----

SELECT distinct S.SName
FROM S JOIN SP ON S.SNum = SP.SNum
WHERE SP.PNum IN ( 	SELECT P.PNum
					FROM P
					WHERE P.City = 'London'	);

SELECT distinct S.SName
FROM S JOIN SP ON S.SNum = SP.SNum
WHERE SP.PNum = ANY ( 	SELECT P.PNum
						FROM P
						WHERE P.City = 'London'	);

---- Esercizio 5.2	
-- Elencare le città in cui non vi sono fornitori con status
-- minore della media (sia con costrutto in/not in che
-- con costrutto any/all)
----

SELECT S.City
FROM S
WHERE S.Status >= (	SELECT avg(S.Status) 
					FROM S					);

---- Esercizio 6.1
-- Trovare i codici dei prodotti che hanno il peso
-- massimo (come esercizio sulle query correlate,
-- scrivere una versione determinando il peso massimo come il peso non inferiore ai pesi di tutti gli altri prodotti e un’altra versione con not exists)
----

SELECT P.PNum
FROM P
WHERE P.Weight = (	SELECT max(P.Weight) 
					FROM P					);

SELECT P.PNum
FROM P 
WHERE not exists (	SELECT * 
					FROM P P1 
					WHERE P1.Weight > P.Weight	);

---- Esercizio 6.2
-- Trovare i nomi dei fornitori che forniscono tutte le parti (senza utilizzare operatori aggregati)
-- (suggerimento: scrivere prima una query che trovi le
-- parti non fornite da S2 e poi generalizzare su ogni
-- fornitore)
----

SELECT S.SName
From S
WHERE not exists ( 	SELECT *
					FROM P
					WHERE not exists (	SELECT *
										FROM SP
										WHERE SP.SNum = S.Snum 
										  AND SP.PNum = P.Pnum	)	);

---- Esercizio 6.3
-- Trovare i nomi dei fornitori che forniscono almeno tutti i prodotti forniti da S2 (senza utilizzare
-- operatori aggregati) (suggerimento: scrivere prima
-- una query che trovi i prodotti forniti da S2 ma non
-- da S3 e poi generalizzare su ogni fornitore)
----

SELECT S.SName 
FROM S 
WHERE not exists (	SELECT * 
       				FROM P P1 
       				WHERE not exists (	SELECT * 
              							FROM SP SP1
              							WHERE  S.SNum = SP1.SNum 
										  AND P1.PNum = SP1.PNum							) 
             		  AND P1.PNum in (	SELECT P2.PNum 
              							FROM SP SP2 JOIN P P2 ON SP2.PNum = P2.PNum 
              							WHERE SP2.SNum = 'S2'							)	);

---- Esercizio 7.1
-- Create una copia delle tabelle S e P, nominandole S_x, P_x, dove x è il vostro numero di matricola
----
-- Sperimentate con aggiornamenti, modifiche,
-- cancellazioni, creazioni di viste, ecc...
----
-- Creando la tabella SP_x come copia di SP,
-- come vengono trattati i vincoli? Risolvete
-- utilizzando SQL
----