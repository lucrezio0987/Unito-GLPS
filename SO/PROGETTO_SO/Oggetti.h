#define SO_DAYS 10
#define SO_MERCI 10

#define C_TEMPESTA    0x1
#define C_MAREGGIATA  0x2
#define C_MAELSTROM   0x4

#define SIG_TEMPESTA    30	
#define	SIG_MAELSTROM   31	

enum mappa {X, Y};
enum bool {FALSE, TRUE};
enum stampa {INIZIALE, GIORNALIERA, FINALE};
enum meteo { TEMPESTA, MAREGGIATA, MAELSTROM };

typedef struct Merce {
    char t_merce[5];
    int size;
    int ton;
    int t_vita;
} Merce;

typedef struct Porto {
    double pos[2];
    int scadenza[SO_DAYS][SO_MERCI];
    int offerta [SO_MERCI];
    int domanda [SO_MERCI];
    int spedita [SO_MERCI];
    int ricevuta[SO_MERCI];
    int scaduta [SO_MERCI];
    int merce_offerta;
    int merce_ricevuta;
    int banchine;
    int banchine_occupate;
    int flagMareggiata;
    int colpito_da_mareggiata;
}Porto;

typedef struct Nave {
    double pos[2];
    int scadenza[SO_DAYS][SO_MERCI];
    int capacita;
    int occupato;
    int stiva[SO_MERCI];
    int scaduta[SO_MERCI];
    short carica;
    short in_mare;
    pid_t pidNavi; /*  -1 : Distrutta
                        0 : Ferma
                       >0 : PID Processo  */
    int rallentata_da_tempesta;
} Nave;

typedef struct Var {
    pid_t pidMovimento;
    int n_navi;
    int n_porti;
    int SO_lato;
    int SO_size;
    int SO_fill;
    int SO_min_vita;
    int SO_max_vita;
    int SO_capacity;
    int SO_banchine;
    int SO_speed;
    int SO_loadspeed;
    int SO_storm_duration;
    int SO_swell_duration;
    int SO_maelstrom;
    int flagTerminazione;
    int fork_navi;
    int giorno;
    int setOnMaraggiata;
    int setOnTempesta;
    int setOnStampaGiornaliera;
    int setOnMaelstrom;
} Var;

typedef struct msg {
    long m_type;
    int  n;
} msg;

/* Coda di messaggi */
int msgPila;
msg message;

/* Semafori */
int semMareggiata, semPila, semShm, semPidNavi, semProcessi; 

/* Memorie Codivise*/
int shmMerci;   Merce* merci;
int shmPorti;   Porto* porti;
int shmNavi;    Nave*  navi;
int shmVar;     Var*   var;
int shmPila;    int*   pila;

/* Flag Terminazione e controlloSegnali*/
int flagTempesta, flagMaelstrom, flagMareggiata, flagTerminazione;

/* File */
FILE* out_progetto;
FILE* in_progetto;

/* Variabili Globali */
int nave, giorno, banchine, p_rand;
pid_t pid, pidNave;
