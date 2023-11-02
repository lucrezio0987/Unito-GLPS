## Introduzione

### Internet
  - Network of networks
      (ISPs interconnessi)
  - Protocolli
      (invio e ricezione messaggi, HTTP, streaming, TCP, IP, WiFi, 4G, Ethernet)
  - Standards
      (RFC, IETF)

  [Infrastrutture]:

---

 > Lez. 29/09/23

    d_nodal = d_proc + d_queue + d_trans + d_prop
                       ^^^^^^^
  **Quantità di traffico**

        L * a       arrival rate bits
        -----   :   ------------------
          R         service rate bits

  (anche se in media il tasso di servizio è più alto del tasso di arrivo, c'è la probabilità di avere la coda, che aumenta più il tasso di arrivo aumenta rispetto al tasso di servizio)

  - Una causa di rallentamento della rete può essere dei cicli tra un rooter e l'altro (si possono notare atterverso lo strumento traceroute)


### Perdita dei pacchetti
  - per evitare che la coda cresca troppo, alcuni pacchetti vengono doppati.

  ...

### Sicurezza

  - Principali attacchi:
    + Sniffing
    + IP Spoofing
    + DoS

## Livelli di Protocolli e Modelli di riferimento

  - Livelli:
    (7) 5. **Applicativi**      HTTP, IMAP, SMTP, DNS, E-mail
        4. **Trasporto**        TCP, UDP
        3. **Rete**             IP, routing protocols
        2. **Collegamento**     Ethernet, 802.11 (WiFi), PPP
        1. **Fisici**

  Router: 1,2,3
  Switch: 1,2

  - Modelli di servizio:
    + Client-server
    + P2P

## Livello Applicativi

### Sockets

  Interfaccia tra il livello Applicativo e i livelli più bassi

[Client]: chi ha aperto la comunicazione


> netstat
    consiglio di usarlo con "| less"
    contiene tutte le connessioni TCP aperte in questo momento dal pc

  [Aprire un socket]
> sudo nc -l 127.0.0.1 9000
    comando neltcat che apre in ascolto sulla porta indicata
> sudo nc 127.0.0.1 9000
    comando netcat che permette di aprire una connessione TCP

 - Le richieste ai server devono essere formate correttamente
  esempio:

  > nc -v www.facebook.com 80     (per contattare il server )
    GET /index.html HTTP/1.1      (Richeista ben forata minimale)
    ```Return
      HTTP/1.1 301 Moved Permanently
      Location: https:///index.html
      Content-Type: text/plain
      Server: proxygen-bolt
      Date: Fri, 06 Oct 2023 09:34:47 GMT
      Connection: keep-alive
      Content-Length: 0
    ```
    GET / HTTP/1.1
    host: www.facebook.com (in questo caso specifichiamo perche su questo server c'è anche Instagram)
    ```Return
      HTTP/1.1 301 Moved Permanently
      Location: https://www.facebook.com/
      Content-Type: text/plain
      Server: proxygen-bolt
      Date: Fri, 06 Oct 2023 09:37:02 GMT
      Connection: keep-alive
      Content-Length: 0
    ```

### HTTP
  [Metodi]:
  - GET: mando dati
  - POST: mando dati, come GET ma devo mandare anche dei payload
  - HEAD
  - PUT

  [Errori]:
  - 200 OK
  - 301 Spostato Permanentemente
  - 400 Richeista non valida
  - 404 Non trovato
  - 505 versionene HTTP non supportata

### Cookies
  - è un database che salve delle informazioni.
  - l'utilizzo più importante  salvare le informazioni che identificano gli utenti.

### Cache
  - cache: Salvare il cotenuto per velocizzare il sito
  - Proxy: sta tra il client e il server e serve a trasferire le richieste.

  - il protocollo prevede un controllo degli header per controllare con il server se il contenuto è aggiornato.

  se le performance calano, allora si può risolvere in 2 modi:
   - aumentando la capacità di trasporto dei dati tra la cache e i server
   - aggiungere un server all'interno di una rete locale.

### HTTP/2

...


### Servizio di Posta

( >$ ps     mostra i processi)

 [SMTP]:

 > netstat -lptn (lissen, programs, tcp, numeric)

 > telnet

 > bat /etc/resolv.conf  (contiene bho...)


## Livello di Trasporto

  Permette la comuncazione tra due processi (uno nel browser e uno nel server)

  Floow-controll: regola la quantità di pacchetti che vengono mandati

  i paccehtti mandati sono numerati per poter essere riodinati quando arrivano.


~~  multipexing (in invio) - demultipexing (in ricezione)
~~    (Porta sorgente)        (porta destinazione)

  Protocolli di trasporto:
    [TCP]: (Transmission Control Protocol)
      - riceve in ordine i pacchetti (se un pacchetti manca viene richiesto)
      - prende la storia delle comnicazini con quella connessione e verifica se manca qualcosa.

    [UDP]: (User Datagram Protocol)
      - stateless, quando arriva qualcosa risponde (non importa da dove arriva.
      - inverte porta ssorgente con destinazione e manda la risposta
      - usato dai protocolli di gestione


## Principles of reliable data transfer

  - Problemi legati al'utilizzzo di **canali non affidabili** sono:
    + perdita di pacchetti
    + pacchetti danneggiati
    + pacchetti in ritardo

 - Serve uno scambio di messaggi tra chi manda e chi riceve per avere informazioni relativi a "cosa sta succedendo dall'altra parte".

 [Notazioni]:
   - **rdt_send()**: Reliable data transfer
    protocollo API per mandare i dati.
   - **rdt_rcv()**: interfaccia di ricevimento Reliable

