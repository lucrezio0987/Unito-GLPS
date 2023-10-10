## Client Server Architectures

    (durato poco)

## HTTP Protocol

   [HyperText Transfert Protocol]:
    - Estreamente facile e leggero
    - Stateless: il server non ha uno stato, la richeista viene aperta,mandata,restituita e chiusa.
    - ad oggetto
    - generico: puoi "negoziare" la struttura dei dati

    1. prima si apre la Connessione HTTP
    2. viene effettuata la richeista (es: una certa pagina web)
    3. chiusura della Connessione

    [Operazioni]:
     + GET: richeista di dati
     + POST: invio di dati

     - Request Header (Metadati):
        * contiene se è una get o una post
        * contiene che tipo di dati può accetare

        > Accept
        > Accept-Charset
        > Accept-Encoding
        > Accept-Language
        > Authorization, Authorization info
        > Content-Length                        (OBBLIGATORIO nel caso del POST)
        > Cookie
        > From
        > Host
        > If-Modified-Since                     (Se gia in CACHE e non è cambiata non riscaricarla)
        > Referrer
        > User-Agent
        > ...

   !![Responses Codes]: è un intero contenuto nella risposta dal server

        << 200: OK
        << 204: No Content
        << 301: Spostato permanentemente

        << 401: non autorizzato
        << 403: Forbiden
        << 404: Pagina non trovata

        << 500: Server Error
        << 503: Servizio non attivo

### HTTPS
  [SSL Certificates]
    ...

  [HTTP/2]
    - permette di rendere il server "proattivo"
    ...

## NodeJS
  [Modello]:
    - Event-driven
    - Non-blocking I/O
    - Build on V8


### Express
  libreria per NodeJS che lo semplifica molto
    - organizza il server intorno a path
    - Framework Web minimale

  [Struttura]:
    - Metodo HTTP
    - Path
    - Callback Function

  [Route definition]
    > app.METHOD(PATH, CALLBACK)
