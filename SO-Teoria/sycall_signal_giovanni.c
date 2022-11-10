/*
SEGNALI
    -Un segnale è una notifica  un processo che è occorso un certo evento.

:-Fra i tipi di eventi che causano il fatto che il kernel generi un segnale per un processo
  ci sono i seguenti: 
        -1 è occorsa una eccezione hardaware
        -2 L'utenete ha digitato sul terminale dei caratteri speciali che generano  i segnali
        -3 è occorso un evento software. (es. l'imput è divenuto disponibile su un descrittore di file, un timer è arrivato a 0)
:-I segnali sono definiti con interi numerici, la ccui sequenza inizia da I. (signal.h o in <sys/sygnal.h>)

*--------------------------------------------------------------*
signal lifecycle

:-Si dice che un segnale è generato da qìualche evento. 
:-Dopo essere stato generato, un segnale è inviato ad un processo che esegue una qualche azione in risposta al segnale
:-Nel momento ub cui è generato ed il momento in cui è inviato al processo il segnale è -pendente- (lasso di tempo vario, può durare tanto)
    -Un segnale -pendente- è inviato a un processo appena il processo è scelto per l'esecuzione, oppure immediatamente se il processo 
     è già in esecuzione.

:-A volte è necessario assicurare che un segmento si codice non sia interrotto dalla consegna di un segnale
    -in questo caso, possiamo aggiungere un segnale alla machera dei segnali del processo, cioè un insieme
    di segnali la cui ricezione è attualmente bloccata.

delivery and process actions

:-Al momento della ricezione di un segnale, un processo continua con una delle srguenti azioni di default
    -Il segnale è ignorato (viene scartartato dal kernel e non ha effetti sul processo)
    -Il processo viene terminato (killed)

delivery and process actions (2)

:-è generato un file contenente un -core dump file- e il processo viene terminato.
    -Un file con -core dump- contiene un'immagine della memoria virtuale del processo.
    -Il processo viene bloccato (stopped)
    -L'esecuzione del processo è ripresa (resumed)

Il sistema di segnali in Unix: _le trap_

:-Una classe di segnali sono le trap
    -segnali generati da eventi prodotti da un processo e inviati al processo stesso
    -Alcune trap sono causate da comportamenti errati (es. divisione per zero, indirizzamento errato degli array, istr. errate)

Il sistema di segnali in Unix: _gli interrupt_

:-Gli _interrupt_ sono segnali inviati ad un processo da un agente esterno:
    -Utente(da terminale):
        -CTRL-C (invia SIGINT)
        -CTRL-Z (invia SIGSTOP)
        -Comando kill: kill –s signame PID
        -Comando kill: kill –signumber PID
    -Altro processo
        - System call kill: kill(PID, SIGNAL)

Setting the disposition

:-Invece di accettare l'azione di default per un particolare segnale, un programma può modificare l'azione da
  intraprendere al momento della consegna (delivery) del segnale.
    -L'azione del default dovrebbe essere instrapresa (cancella precendenti modifiche della disposizione)
    -Il segnale è ignorato (un segnale la cui disposizione sarebbe quella di terminare il processo)
    -Viene eseguito un _signal handler_

Signal handlers

:-Un signal handler o gestore di segnali è una funzione che esegue azioni appropriate
  in risposta alla ricezione di un segnale.
  -(es. la shell ha un gestore per il segnale SIGINT
     che causa il suo blocco (stop) e la restituzione del controllo
     al ciclo di input principale: in questo caso all'utente viene
     presentato il prompt della shell).
  -Installare o stabilire un signal handler significa notificare al kernel
     che deve essere invocata una funzione handler.
  -Quando un handler è invocato in risposta alla ricezione di un
     segnale, si dice che il segnale è stato gestito (handled) o
     intercettato (caught).

Signal Types and Default Actions

:- SIGNCHLD
    -Segnale inviato dal kernel a un processo genitore quando uno dei figli termina
       (chiamando exit(), o ucciso da un qualche segnale).
    -Può essere inviato ad un processo quando uno dei suoi figli è bloccato o risvegliato da un segnale

:- SIGCONT
    -Quando viene inviato a un processo bloccato (stopped), questo segnale causa il risveglio del
       processo (resume), cioè che il processo venga schedulato per successivamente essere eseguito.
    -Quando è ricevuto da un processo che non è bloccato, questo segnale è ignorato di default. 
    -Un processo può intercettare questo segnale, in modo da eseguire qualche azione particolare
       al momento della ripresa dell'esecuzione.

:- SIGINT
    -Quanto l'utente digita il carattere di interrupt(Control-C), il terminale invia questo segnale
       al gruppo del processo in foreground. 
    -L'azione di default per questo segnale è terminare il processo.

:- SIGKILL
    -È il segnale sicuro di kill. Non può essere bloccato, ignorato, o intercettato da un handler 
       quindi termina sempre un processo.

:- SIGPIPE
    -Segnale generato quando un processo tenta di scrivere su un pipe o un FIFO per il quale non c'è un
       corrispondente processo lettore.
    -Qeusto normalmente occorre perchè il processo lettore ha chiuso il proprio file descriptor
       per il canale PC.

:-SIGSEV
    -Segnale generato quando un programma tenta un riferimento in memoria non valido. 
    -Il riferimento può non essere valido perché la pagina riferita non esiste
       oppure il processo ha tentato di modificare una locazione in read-only memory 
       o il processo ha tentato di accedere a una parte della memoria del kernel durante l'esecuzione in user mode. 

:-In C, questi eventi spesso derivano dalla dereferenziazione di un puntatore che contiene
   un 'bad address' (come un puntatore non inizializzato) o dal passaggio di un argomento non valido in una
   chiamata a funzione

:-SIGSTOP
    -Segnale per il blocco sicuro.
    -Non può essere ignorato o intercettato da un Handler (quindi blocca sempre un processo)

:-SIGTERM
    -Segnale standard utilizzato per terminare un processo
    -Inviato di default per dai comandi (kill e killall)

:-SIGTRAP
    -Segnale utilizzato per impementare i breakpoint in fase di debugging e per la tracciatura delle system call

:-SIGTSTP
    -Segnale per lo stop, inviato per bloccare il gruppo di processi in foreground quando l'utente digita (CTRL-Z)

:-SIGUSRI
    -Questo segnale e SIGUSR2 sono disponibili per fini specificati dal programmatore. 
    -Il kernel non genera mai questi segnali per un processo.
    -I processi possono utilizzare questi segnali per notificarsi a vicenda eventi, o per sincronizzarsi.

Changing Signal Dispositions: signal()

Kill() system call
:-L'argomento pid identifica uno o più processi a cui inviare il segnale (int da 1 a 31)
    -pid > 0: il segnale è inviato al processo identificativo da pid.
    -pid == 0: il segnale è inviato a ogni processo nello stesso gruppo del chiamante, chiamante incluso.
    -pid < -1: il segnale è inviato a tutti i processi nel gruppo del processo il cui ID è uguale al valore
       assoluto di pid. Inviare un segnale a tutti i processi di un gruppo di un processo è utile nel controllo
       dei job effettuato con la shell.
    -pid == -1: (broadcast signal) Il segnale è inviato a tutti i processi per i quali il processo ha i permessi
        di inviare un segnale, eccetto init.
:-Se nessun processo corrisponde al pid predefinito, kill() fallisce e setta errno a ESRCH ("No such process")
:-Verifica dell'esistenza di un processo, se l'argomento sig è settato a 0 (detto null signal), non è inviato alcun segnale
    -kill() esegue inicamente in controllo degli errori per vedere se è possbile inviare segnali al processo.
:-Se la chiamata fallisce con errore EPERM, il processo esiste, ma non abbiamo i permessi per inviargli un segnale
:-Se la chiamata va a buon fine, sappiamo che il processo esite

sigaction system call

:-sycall utilizzata per impostare un gestore di segnali
    -signum: numero del segnale da gestire
    -act, puntatore al nuovo gestore del segnale: se NULL il gestore resta invariato
    -oldact, puntatore al vecchio gestore; se impostato a NULL non viene restituito alcun handler
    -sigaction è sia una _system call_ sia una _struct_

:-the sigaction structure
    -sa_handler, puntatore alla funzione per la gestione del segnale
    -sa_mask, maschera dei segnali bloccati durante l’esecuzione dell’handler
    -sa_flags, insieme di flag messi in bitwise OR per modificare il comportamento del segnale

mask & fork()

:-La collezione di segnali che sono attualmente bloccati si chiama _signal mask_
:-Ogni processo ha la sua propria signal mask
:-Quando un nuovo processo è creato, eredita la mask del processo padre
:-Puoi bloccare e sbloccare le signal con flessibilità totale modificando la _signal mask_
:-Quando un segnale è inviato a un processo durante l’esecuzione di un handler, 
     il segnale è automaticamente bloccato fino a quando l’handler restituisce
     a meno che non sia settato il flag SA_NODEFER

signal mask

:-La maschera è la collezione di segnali attualmente blocked
    -Ogni processo ha la propria maschera di segnali
:-Le maschere sono di tipo sigset_t. le funzioni per la manipolazione dei set sono:
    -int sigemptyset(sigset_t *set);
    -int sigfillset(sigset_t *set);
    -int sigaddset(sigset_t *set, int signum);
    -int sigdelset(sigset_t *set, int signum);
    -int sigismember(const sigset_t *set, int signum);

Signal sets

:-Il tipo di dato sigset_t è utilizzato per rappresentare un signal set.
    -Internamente, potrebbe essere implementato sia come tipo intero che come tipo struttura
:-Ci sono due modi per inzializzare un signal set
    -puoi inizializzarlo sia vuoto con la "sigemptyset", e poi aggiungendo signal specifici individualizzati
    -oppure puoi specificare che sia pieno con "sigfillset" e poi eliminare signal specifici individualmente

setting the signal mask for a process

:-per impostare la maschera di segnali bloccati durante l'esecuzione dei processi si usa la syall sigprocmask();
:-l'argomento how può assumere i seguenti valori:
    -SIG_BLOCK: i segnali nel set sono bloccati
    -SIG_UNBLOICK: i segnali nel set sono rimossi dalla maschera esistente
    -SIG_SETMASK: il set diventa la nuova maschera del segnale
:-oldset è la vecchia madchera

signal mask during a handler

:-l'handler può comunque essere interrotto da altri segnali(o dallo stesso, nel caso SA_NODEFER sia settato)
    -quando l’handler restituisce, l’insieme di segnali bloccati è reimpostato al suo valore precedente la
       sua esecuzione, indipendentemente dalle possibili manipolazioni dei segnali bloccati eventualmente
       presenti nell’handler

delivery di segnali a processi sospesi

:-all'arrivo si un segnale:
    1-Lo stato del processo è salvato(registers..)
    2-La funzione dell'handler è eseguita
    3-Lo stato del processo è restored
:-per processi in attesa su wait(), o sospesi con pause() o sleep() sono possibli 2 comportamenti:
    -il processo non è in esecuzione(sospeso su qualche system call)
    -la funzione dell'handler è eseguita normalmente
:-quando l'handler ritorna:
    A-la sycall restituisce un errore, con errnmno settato a EINTR oppure
    B-la sycall viene automaticamente ripresa

writing handlers

:-l'handler deve essere associato al corrispondente segnale prima che il segnale possa essere lanciato
       e ricevuto dal processo: gestendo un segnale SIGCHLD, è necessario associare il gestore al 
       segnale prima della fork()
:-spesso una funzione può servire a gestire vari tipi di segnale.
    
*/