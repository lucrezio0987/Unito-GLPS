# INTRODUZIONE

## Unit Test
  - *Automatici*
  - *Verificano una porzione di codice* (in specifiche situazioni particolari)
  - lavorano attraverso *asserzioni*.
  
  <Asserzioni: verificano che il risultato attesio coincida con il risultato ottenuto>
  > Test superato <--- tuttte le asserzioni sono soddisfatte

  [Devono_essere]:
   + **Focalizzati**  (una unit deve testare un singolo caso d'uso)
   + **Indipendenti** (l'ordine di esecuzone non deve influire sul loro risultato)
   + **Automatici**   (non viene scritto nulla su console durante il test)
  
### Unit_test: Java
  - libreria più usata per unit test in ambito Java
  - La versione più recente di JUnit è JUnit 5. Per semplicità, nell'esecuzione di questo laboratorio, suggeriamo l'uso della versione JUnit 4 (cui faremo riferimento qui)
  - test in JUnit = metodo marcato con l'annotazione @Test
  - Tali metodi possono usare un certo numero di funzioni messe a disposizione dalla libreria per verificare la correttezza del programma
  - JUnit provvederà ad eseguire i test in ordine casuale ricaricando la classe prima di ogni singolo test

### Unit_test: C
  - purtroppo non esiste una libreria “standard” 
  - per questa ragione:
      + è ammesso utilizzare un programma ad-hoc per effettuare il test, a patto che si scrivano le funzioni di test prestando attenzione a quanto detto
      + è ammesso utilizzare librerie, ma sarà responsabilità dello studente/gruppo installarle e utilizzarle correttamente
  - Semplice e molto usata:
      + Unity (http://www.throwtheswitch.org/unity/) (_non è uno standard come JUnit per Java_)
      + potete trovare Unity nella cartella Resources/C/Unity del repository Git

