/*
------------------------------------------------------------------------------------------------------------------------------------------
#include <unistd.h>   --> restituisce l'id del processo chiamante
pid_t getpid(void); 
------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------------------
#include <unistd.h>   --> ogni processo ha anche un processo genitore
pid_t getppid(void);      restituisce l'id del genitore
------------------------------------------------------------------------------------------------------------------------------------------
La memoria utilizzata dal codice è divisa in blocchi
1 - segmento di testo (codice)
    variabili non inizializzate vengono inizializzate automaticamente dal sistema a 0
2 - Stack dinamico: variabili locali, argomenti, return value
3 - Heap: malloc etc.. 
------------------------------------------------------------------------------------------------------------------------------------------
Funzioni fondamentali per creazioni, esecuzioni e terminazioni = fork(), exit(), wait(),...
-FORK()
    - permette a un processo padre di crearne un altro detto figlio
    - il figlio è (quasi) una copia identica del padre, ottiene copie degli stack, heap etc.. del padre
-EXIT()
    - termina un processo rendendo le risorse utilizzate dal processo di nuovo disponibili
    - l'argomento status è un intero che descrive lo stato di terminazione del processo
-WAIT(&status)
    - se un figlio non ha ancora concluso la propria esecuzione chiamando la exit(), la wait() sospende
      l'esecuzione del processo chiamante finchè uno dei figli non ha terminato la propria esecuzione
    - dopo la terminazione del figlio lo stato di terminazione del figlio è restituito nell'argomento status della wait
-EXECVE(pathname, argv, envp)
    - carica un nuovo programma nella memoria del processo
    - il testo del programma precedente è cancellato e stack, dati e heap sono creati per il nuovo programma
------------------------------------------------------------------------------------------------------------------------------------------
CREAZIONE DI PROCESSI
- fork()
    #include <unistd.h>   --> nel padre restituisce il PID del figlio
    pid_t fork(void);         nel figlio restituisce 0

    - dopo l'esecuzione della fork() esistorno 2 processi e in ciascuno l'esecuzione riprende dal punto in cui la fork() restituisce
    - nello spazio di indirizzamento del processo figlio viene creata una copia delle variabili del padre, il nuovo processo
      inizia l'esecuzione a partire dalla prima istruzione successiva alla fork che lo ha creato
    - i due processi eseguono lo stesso testo, ma mantenendo copie distinte di stack, data e heap
    - dopo la fork() ogni processo può modificare le variabili in tali segmenti senza influenzare l'altro processo
------------------------------------------------------------------------------------------------------------------------------------------
TERMINAZIONE DI PROCESSI
- _exit()
    #include <unistd.h> 
    void _exit(int status);
    
    - nei programmi generalmente viene chiamata la exit(int status), che contiene al suo interno la _exit()
    - lo 0 indica la terminazione corretta nei processi
------------------------------------------------------------------------------------------------------------------------------------------
MONITORAGGIO DI PROCESSI
- wait() 
    #include <sys/wait.h> 
    pid_t wait(int *status);  --> ritorna il PID del figlio terminato

    - se nessuno figlio del processo chiamante ha già terminato, la chiamata di blocca finchè uno dei figli termina
      se un figlio ha già terminato al momento della chiamata, restituisce immediatamente
    - se status non è NULL l'informazione è assegnata all'intero a cui punta status

- error
    - in caso di errore wait() restituisce -1. Un possibile errore è che il processo chiamante potrebbe non avere figli,
      il che è indicato dal valore ECHILD di errno

-waitpid()
    la wait permette semplicemente di attendere che uno dei figli del chiamante termini, in alcuni casi è preferibile eseguire 
    una nonblocking wait, in modo da ottenere immediatamente che nessun figlio ha terminato
    la waitpid permette invece di indicare il PID specifico da aspettare

#include <sys/wait.h> 
pid_t waitpid(pid_t pid, int *status, int options)

    - se pid > 0 attendi per il figlio con quel pid
    - se pid == 0 attendi per qualsiasi figlio nello stesso gruppo di processi del chiamante
    - se pid < -1 attendi per qualsiasi figlio il cui process group è uguale al valore assoluto di pid
    - se pid == -1 attendi per un figlio qualsiasi (equivalente a wait)

#include <sys/wait.h> 
pid_t waitpid(pid_t pid, int *status, int options);

    L'argomento options è una bit mask che può includere 0 o i seguenti flag
    - WUNTRACED: oltre a restituire info quando un figlio termina, restituisci informazioni quando il figlio viene bloccato da un segnale
    - WCONTINUED: restituisci informazioni anche nel caso il figlio sia stopped e venga risvegliato da un segnale SIGCONT
    - WHOHANG: se nessun figlio specificato da pid ha cambiato stato, restituisci immediatamente invece di bloccare il chiamante

    il wait status value restituito da wait() e waitpid() ci permette di distinguere i diversi casi:
    - il figlio ha terminato l'esecuzione chiamando _exit()(o exit()) specificando un codice intero di uscita
    - il figlio ha terminato l'esecuzione per la ricezione di un segnale non gestito
    - il figlio è stato bloccato da un segnale, e waitpid() è stata chiamata con il flag WUNTRACED
    - il figlio ha ripreso l'esecuzione per un segnale SIGCOUNT e waitpid() è stata chiamata con il flag WCONTINUED

    - WIFSIGNALED(status) restituisce true se il figlio è stato ucciso da un segnale
    - WIFSTOPPED(status) restituisce true se il figlio è stato bloccato da un segnale
    - WIFCONTINUED(status) restituisce true se il figlio è stato risvegliato da un segnale

    In generale o il padre sopravvive al figlio, o  viceversa
    -> il figlio orfano è adottato da init, il progenitore di tutti i processi, il cui PID è 1
    In altre parole, dopo che il genitore termina, una chiamata a getppid() restituirà il valore 1

    Cosa capita a un figlio che termina prima che il padre esegua una wait()?
    -> il padre dovrebbe avere la possibilità di eseguire una wait() per determinare com'è terminato il figlio
    Il kernel garantisce questa possibilità trasformando il figlio in uno zombie
    Un processo zombie non può essere ucciso da un segnale, neppure SIGKILL, in modo che il genitore possa sempre eseguire una wait()
    Se il genitore termina senza eseguire una wait, il processo init adotta il figlio, rimuovendo automaticamente il processo zombie
    L'unico modo per rimuovere gli zombie è uccidere il loro padre
------------------------------------------------------------------------------------------------------------------------------------------
ESECUZIONE DI PROGRAMMI
execve() 
    Carica un nuovo programma nella memoria di un processo 
    Lo stack, i dati e lo heap del processo sono sostituiti da quelli del nuovo programma

#include <unistd.h>
int execve(const char *pathname, char *const argv[], char *const envp[]);

    - Non ritorna nulla in caso di successo, -1 in caso di errore
    - L'argomento pathname contiene il pathname del programma
      L'argomento argv specifica gli argomenti della linea di comando da passare al nuovo programma
      L'argomento envp specifica la lista environment list per il nuovo programma

    Possibili errori che possono essere restituiti in errno:
    - EACCES: l'argomento pathname non si riferisce a un file normale, il file non è un eseguibile
    - ENOENT: il file riferito dal pathname non esiste
    - ENOEXEC: il file riferito dal pathname è marcato come un eseguibile ma non è riconosciuto come formato effettivamente eseguibile
    - ETXTBSY: il file riferito dal pathname è aperto in scrittura da un altro processo
    - E2BIG: lo spazio complessivo richiesto dalla lista degli argomenti e dalla lista supera la massima dimensione consentita
*/