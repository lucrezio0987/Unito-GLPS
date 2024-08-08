# Catering

Catering è configurato come un progetto maven. 

# Requisiti

- Java SDK 19
- IDE Java 

## Raccomandato

- [Docker](https://docs.docker.com/engine/install/)
- [Visual Studio Code](https://code.visualstudio.com/) con ["Extension Pack for Java"](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

[Tutorial](https://code.visualstudio.com/docs/languages/java) di installazione e configurazione di VS Code con Java.

# Progetto Java

Aprire la sottodirectory `java` con l'IDE. Esempio con VS code: `File` -> `Open Folder` -> sottodirectory `java`

# Database
Il programma necessita di un DB MySQL. Esistono diverse opzioni per installare MySQL, di seguito ve ne proponiamo due.

## [Docker](https://www.docker.com/)
Se si ha Docker installato si può usare il `docker-compose.yml` nella sottodirectory `database` per avviare il database:
```bash
cd database
docker compose up -d
```

Il `docker-compose.yml` lancia due container: uno per MySQL, e uno per Adminer.
Adminer offre una semplice interfaccia web al DB. (di default sulla porta `8080` configurabile in `docker-compose.yml`).

Una volta avviato, usare l'interfaccia web di Adminer sulla porta locale (di default sulla porta `8080` ma configurabile in `docker-compose.yml`) per creare il data base e le tabelle.

## [XAMP](https://www.apachefriends.org/it/index.html), [WAMP](https://www.wampserver.com/en/), [MAMP](https://www.mamp.info/)

Se si ha uno dei software "AMP", una volta avviato il database tramite l'applicazione, utilizzare l'interfaccia web disponibile su localhost (porta `3306` per **XAMPP** e **WAMP**, mentre `8889` per **MAMP**).

## Configurazione accesso al DB

Fate attenzione alla porta, host, nome utente e password che dovete poi inserire nel file `java/catering/persistence/PersistenceManager.java` per configurare l'accesso a MySQL da parte del applicativo Cat&Ring. La configurazione di default permette ad accedere al DB come istanziato tramite Docker.

## Creazione database e tabelle iniziale

L'istanza MySQL all'inizio è vuota. Creare un database di nome `catering` collegandosi con browser a `http://localhost:8080`.
Le tabelle iniziali possono essere create eseguendo dall'interfaccia web la query salvata in `catering_init_03.sql`. 