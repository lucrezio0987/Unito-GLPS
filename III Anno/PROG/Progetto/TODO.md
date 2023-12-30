
    //TODO: AGGIUNGERE COMMENTI!!!!!!

    //TODO: aggiugere una connessione Server -> client (brodcast) su un altra porta che notifica quando il server viene avviato/fermato

    //TODO: far funzionare i pulsati del server (faccio io)

    //TODO: aggiungere una connessione Client -> Server su un altra porta che invia un oggetto MailModifyInfo che notifica la lettura o la cancelazione di una mail

    //TODO: le mail che il server deve mandare al client quanvo si collega non sono solo quelle ricevute ma anche quelle inviate

    /* TODO: fare in modo che il Server quando un client si connette non gli invii tutti i messaggi ma solo quelli che non ha gia
          IDEE:
             1. il client potrebbe mandare l'ultima mail (uuid) che ha nella lista in modo che il server possa controllare se è effettivamente l'ultima
             2. servirebbe anceh una struttura dati di qualche genere per indicare quali mail sono state lette o cancellate tra quelle gia possedute
                (ad esempio una mappa con uuid come chiave e come valore 0 se è stata letta e 1 se è stata cancellata)
                (ovviamente conterrebbe solo quelle che sono state modificate dall'ultimo aggiornamento, quindi viene svuotata alla fine di ogni aggioranmento)
             3. è possibile che serve costruire un macrooggetto Operazione (passato con Json) contenente: lista mail da agggiungere, mappa mail modificate
     */

    /* TODO: lo stesso sistema serve per il client, nel senso che quando il server è offline deve avere un modo per salvarsi le modifiche fatte e le mail inviate in una lista a parte
             e mandandare tutto quando il server viene connesso
     */

    /*TODO: fare in modo che i client sullo stesso host (quindi se li avvi su stesso pc ad esempio) possano essere distinguibili:
        IDEE:
            1. ho notato che l'oggetto Socket oltre a contenere la portaLocale e l'indirizzo locale contiene anche un altra porta che sembra variaare all'interno della stessa rete
               è possibile che si possa usare quella ma bisogna informarsi
            2. l'alternativa potrebbe essere che il messaggio viene mandato a tutti su quella rete poi i client in ascolto controllano che il messaggio "sia effettivamente destinato a loro"
               ad esempio controllando che il ricevende sia uguale al localAddress, ma sarebbe da evitare per ragioni di sicurezza
            3. non mi piace dato il numero di porte che utilizza anche il client (almeno 2 per ogni client: ricezione mail, ricezione messaggi brodcast dal server),
               ma un altra idea potrebbe essere fare in modo che il client si metta su "una porta libera" (causerebbe più problemi e renderebbe il sistema poco scalabile a parer mio)
     */
