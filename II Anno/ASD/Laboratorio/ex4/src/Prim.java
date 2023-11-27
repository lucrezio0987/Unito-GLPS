import java.util.Comparator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/*
 * Si implementi una libreria che realizza la struttura dati Grafo in modo che sia 
 * ottimale per dati sparsi (attenzione: le scelte implementative che farete dovranno
 * essere giustificate in relazione alle nozioni presentate durante le lezioni in aula). 
 * 
 * La struttura deve consentire di rappresentare sia grafi diretti che grafi non 
 * diretti 
 * 
 * (SUGGERIMENTO: un grafo non diretto può essere rappresentato usando un'implementazione 
 * per grafi diretti modificata per garantire che, per ogni arco (a,b) etichettato w,
 * presente nel grafo, sia presente nel grafo anche l'arco (b,a) etichettato w. 
 * Ovviamente, il grafo dovrà mantenere l'informazione che specifica se esso è 
 * un grafo diretto o non diretto). 
 */

      /*
      * FORMATO:
      *  [ villa bentivoglio,villa san silvestro,39479.23647356985 ]
      *  
      * Tale file contiene le distanze in metri tra varie località italiane e una 
      * frazione delle località a loro più vicine. 
      * 
      * Il formato è un CSV standard: i campi sono separati da virgole; 
      * i record sono separati dal carattere di fine riga (\n). 
      * 
      * Ogni record contiene i seguenti dati: 
      *  - place1: (tipo stringa) nome della località “sorgente” (la stringa può contenere spazi ma non può contenere virgole);
      *  - place2: (tipo stringa) nome della località “destinazione” (la stringa può contenere spazi ma non può contenere virgole);
      *  - distance: (tipo float) distanza in metri tra le due località. 
      *   
      * Note: 
      *  - Nel caso in cui il grafo sia costituito da una sola componente connessa, 
      *    l'algoritmo restituirà un albero. Nel caso in cui, invece, 
      *    vi siano piùcomponenti connesse, l'algoritmo restituirà una foresta costituita 
      *    dai minimi alberi ricoprenti di ciascuna componente connessa.
      *  - Potete intrepretare le informazioni presenti nelle righe del file come archi 
      *    non diretti (per cui probabilmente vorrete inserire nel vostro grafo sia l'arco 
      *    di andata che quello di ritorno a fronte di ogni riga letta).
      *  - Il file è stato creato a partire da un dataset poco accurato. 
      *    I dati riportati contengono inesattezze e imprecisioni.
      *  - Un'implementazione corretta dell'algoritmo di Prim, eseguita sui dati contenuti 
      *    nel file italian_dist_graph.csv, dovrebbe determinare una minima foresta 
      *    ricoprente con 18.640 nodi, 18.637 archi (non orientati) e di peso complessivo 
      *    di circa 89.939,913 Km. 
      */

public class Prim {

  public static void main(String[] args) {

    GrafoInterface<String> grafo = new Grafo<>(String::compareTo, false);

      //BufferedReader inputFile;
      String[] lnBufferSplitted;
      String lnBuffer, sorgente, destinazione;
      float distance;
      Arch arch;
      String FileName;
      int Limit = 0;
      
      if(!args[0].isEmpty()) FileName = args[0]; else 
      FileName = "../italian_dist_graph.csv";
      
      try ( BufferedReader inputFile = new BufferedReader(new FileReader(args[0]))) {

        while ((lnBuffer = inputFile.readLine()) != null) {
          //if(++Limit > 1000) break; // <--- LIMITATORE
          lnBufferSplitted = lnBuffer.split(",");
          sorgente = lnBufferSplitted[0];
          destinazione = lnBufferSplitted[1];
          distance = Float.parseFloat(lnBufferSplitted[2]);

          arch = new Arch(sorgente, destinazione, distance);
          grafo.addArch(arch);
        }
        inputFile.close();
      } catch (IOException e) {
        System.err.println("An error occurred while reading the file: " + e.getMessage());
      }
    System.out.println("");
    
    System.out.println("Numero_Archi:  " + grafo.getArchNumber());
    System.out.println("Numero_di_Nodi(Erticigrafo): " + grafo.getNodesNumber());
    System.out.println("Peso_Grafo: " + grafo.getGraphWeight()/1000 + " Km");
    
    
    System.out.println("");

    grafo.MinForestPrim();
    System.out.println("APPLICATO: Algoritmo di Prim");
    
    System.out.println("");

    System.out.println("Numero_Archi:  " + grafo.getArchNumber());
    System.out.println("Numero_di_Nodi(Erticigrafo): " + grafo.getNodesNumber());
    System.out.println("Peso_Grafo: " + grafo.getGraphWeight()/1000 + " Km");
    
    /*
    System.out.println("    EXPECTED");
    System.out.println("    | Numero Archi:  18637");
    System.out.println("    | Numero di Nodi(Erticigrafo): 18640");
    System.out.println("    | Peso Grafo: 89939.913 Km");
    */
  }
}
