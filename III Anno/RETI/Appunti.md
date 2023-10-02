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
    (7) 5. Applicativi
        4. Trasporto
        3. Rete
        2. Collegamento
        1. Fisici


  - Modelli di servizio:
    + Client-server
    + P2P

## Sockets

  Interfaccia tra il livello Applicativo e i livelli più bassi

[Client]: chi ha aperto la comunicazione


> netstat
    consiglio di usarlo con "| less"
    contiene tutte le connessioni TCP aperte in questo momento dal pc

  [Aprire un socket]
> sudo nc -l 127.0.0.1 9000
    comando neltcat che apre in ascolto sulla porta indicata
> sudo nc 127.0.0.1 9000
    comando netcat che permette di aprire (non in ascolto) la connessione TCP


## HTTP
