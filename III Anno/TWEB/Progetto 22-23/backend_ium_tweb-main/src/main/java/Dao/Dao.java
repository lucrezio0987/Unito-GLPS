package Dao;

import java.sql.*;
import java.util.ArrayList;
import org.apache.commons.codec.digest.DigestUtils;


public class Dao {
    
    private static String url ,user ,password;

    public Dao(String url ,String user ,String password){
        this.url =url;
        this.user = user;
        this.password =password;

    }


    public static String encryptMD5(String testoChiaro){

        return DigestUtils.md5Hex(testoChiaro).toUpperCase();
    }
    public static boolean checkMD5(String password, String testoChiaro){
        return password.equals(encryptMD5(testoChiaro).toUpperCase());
    }
    public static void registerDriver() {
        try { // registrazione del driver JDBC per MySQL
            DriverManager.registerDriver(new
                    com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static Connection getConnection() {
        try {
            Dao.registerDriver();
            Connection con = DriverManager.getConnection(url, user, password);
            if(con==null){
                throw new Error("Connection.connection.getConnection() : connessione non riuscita");
            }
            else{
                return con;
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static void closeCon(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    //Utente---------------------------------------------------------------------
    public  ArrayList<Utente> dumpTableUtente() {
        Connection con = null;
        ArrayList<Utente> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From utente");

            while (rs.next()) {

                if(rs.getString("Attivo").equals("true")){

                    Utente u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getDouble("Stelle"),rs.getString("Attivo"));
                    dump.add(u);
                }

            }

            System.out.println("Successful Dump of : " + dump.size());


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return dump;
    }


    public Utente getUtente(String email){
        Connection con = null;
        Utente u = null;

        try {

            con = Dao.getConnection();
            if(!utenteExists(email)){
                con.close();
                return null;
            }
            PreparedStatement prs = con.prepareStatement("SELECT * FROM utente Where Email = ? ;");
            prs.setString(1, email);
            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                 u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getDouble("Stelle"), rs.getString("Attivo"));

            }





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }

        return u;
    }

    public static int getIDbyUtente(String email){

        Connection con = null;
        int ID= -1;

        try {
            con = Dao.getConnection();
            if(!utenteExists(email)){
                throw new Error("Utente.getIDbyUtente.error():User with this mail doesnt exists");

            }


            PreparedStatement prs = con.prepareStatement("SELECT ID FROM utente WHERE Email = ?;");
            prs.setString(1,email);




            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                ID = rs.getInt("ID");

            }

            if(ID==-1){
                System.out.println("Fail Get val -1");
            }
            else {
                System.out.println("Successful Get getIDbyUtente");
            }






        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }

        return ID;
    }

    public static Utente getUtenteByID(int ID){
        Connection con = null;
        Utente u=null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("SELECT * FROM utente WHERE ID = ?;");
            prs.setInt(1,ID);




            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getDouble("Stelle"), rs.getString("Attivo"));


            }

            if(u==null){
                System.out.println("Fail Get User");
            }
            else {
                System.out.println("Successful Get User");
            }






        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }

        return u;
    }


    public boolean insertUtente(Utente u) {
        Connection con = null;

        try {

            con = Dao.getConnection();
            if(utenteExists(u.getEmail())){
                throw new Error("Utente.insertUser.error():User with this mail already exists");

            }
            PreparedStatement prs = con.prepareStatement("INSERT INTO utente (ID, Email, Password, Nome, Cognome, Ruolo, PF, Stelle,Attivo) VALUES (NULL, ?,?,?,?,?, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAbBB_oglMX609dUtMkvQcL3nmKuqOQmVfR2VIj0he6Q&s', '0','true');");
            prs.setString(1, u.getEmail());
            prs.setString(2, encryptMD5(u.getPassword()));
            prs.setString(3, u.getNome());
            prs.setString(4, u.getCognome());
            prs.setString(5, u.getRuolo());
            prs.executeUpdate();




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }

        return utenteExists(u.getEmail());
    }


    public static boolean utenteExists(String email){
        Connection con = null;
        ArrayList<String> existing_users = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select Email From utente  ; ");

            while (rs.next()) {



                    existing_users.add(rs.getString("Email"));


            }




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }
        return existing_users.contains(email);
    }


    public  boolean deleteUtente(String email) {
        Connection con = null;

        try {
            con = Dao.getConnection();
            if(!utenteExists(email)|| !utenteIsActive(email)){
                throw new Error("Utente.deleteUtente.error():User with this mail doesnt exists or is already deleted");
            }


            PreparedStatement prs = con.prepareStatement("UPDATE utente SET Attivo = 'false' WHERE utente.Email = ? AND Ruolo!='docente'AND Ruolo!='admin';");
            prs.setString(1, email);


            prs.executeUpdate();





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }
        if(utenteIsActive(email)){
            throw new Error("Utente.deleteUser.error():Unsuccesful deletion");

        }
        else{
            return true;
        }


    }


    public static boolean utenteIsActive(String email){
        Connection con = null;
        ArrayList<String> existing_users = new ArrayList<>();
        try {


            con = Dao.getConnection();
            if(!utenteExists(email)){
                throw new Error("Utente.utenteIsActive.error():User with this mail doesnt exists");

            }
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select Email From utente Where Attivo = 'true' ; ");

            while (rs.next()) {



                existing_users.add(rs.getString("Email"));


            }




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }
        return existing_users.contains(email);
    }


    public boolean addFoto(String email,String foto_path){
            Connection con = null;
            Utente u = null;
            try {
                con = Dao.getConnection();
                if(!utenteExists(email)){
                    throw new Error("Utente.addFoto.error():User with this mail doesnt exists");

                }


                PreparedStatement prs = con.prepareStatement("UPDATE utente SET PF = ? WHERE utente.Email = ?;");
                prs.setString(1,foto_path);
                prs.setString(2,email);



                prs.executeUpdate();

                u = getUtente(email);





            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                closeCon(con);


            }

        return u.getPf().equals(foto_path);
        }


    public void updateStelle(){
        Connection con = null;
        double stelle = -1;
        try {
            con = Dao.getConnection();
            ArrayList<Utente> doc = getAllProfessori();

            for(Utente x : doc ){
                Utente u = getUtente(x.getEmail());


                stelle = getMediaValutazioniByDocente(x.getEmail());


                PreparedStatement prs = con.prepareStatement("UPDATE utente SET Stelle = ? WHERE utente.Email = ?;");
                prs.setDouble(1,stelle);
                prs.setString(2,x.getEmail());



                prs.executeUpdate();

            }






        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }

    }

    public static double getMediaValutazioniByDocente(String email){
        double sum=0;
        int valutazioni=0;
        ArrayList<Lezione> l =getLeasonsStoryByDocente(email);
        for(Lezione x : l){
            if(x.getValutazione()!=0){
                sum+=x.getValutazione();
                valutazioni++;
            }

        }
        if(sum==0)
        {
            return 0;
        }

        return (sum/valutazioni);
    }

    public int getNumeroValutazioni(String email){
        Connection con = null;

        try {
            con = Dao.getConnection();
            if(!utenteExists(email)){
                throw new Error("Utente.utenteIsActive.error():User with this mail doesnt exists");

            }

            Utente u = getUtente(email);
            // getMediaValutazioniByDocente
            //double stelle=
            int numero_valutazioni = 0;



            PreparedStatement prs = con.prepareStatement("SELECT count(Valutazione) AS numero_valutazioni FROM lezione WHERE Docente_ID = ? AND Valutazione>0 AND Stato='Conclusa';");
            prs.setInt(1,u.getID());



            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                numero_valutazioni= rs.getInt("numero_valutazioni");
            }


            return numero_valutazioni;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }

        return -1;
    }





    //Utente--------------------------------------------------------------------


    //Corso------------------------------------------------------------------


    public ArrayList<Corso> dumpTableCorso() {
        Connection con = null;
        ArrayList<Corso> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From corso");

            while (rs.next()&& rs.getString("Attivo").equals("true")) {
                Corso u = new Corso(rs.getInt("ID"),rs.getString("nome"),rs.getString("Attivo"));
                dump.add(u);
            }

            System.out.println("Successful Dump of : " + dump.size());


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return dump;
    }


    public boolean insertCorso(Corso c) {
        Connection con = null;

        try {
            con = Dao.getConnection();

            if(corsoExists(c.getNome())){
                throw new Error("Corso.insertCorso.error():Corso with this ");
            }


            PreparedStatement prs = con.prepareStatement("INSERT INTO corso (ID,nome,attivo) Values(null,?,'true');");
            prs.setString(1, c.getNome());


            prs.executeUpdate();





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        if(corsoExists(c.getNome())){
            System.out.println("Successful Corso Insert");
            return true;
        }
        return false;
    }

    public static boolean corsoExists(String corso){
        Connection con = null;
        ArrayList<String> existing_courses = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select Nome From Corso  ; ");

            while (rs.next()) {



                existing_courses.add(rs.getString("Nome"));


            }




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }
        return existing_courses.contains(corso);
    }

    public static boolean corsoIsActive(String corso){
        Connection con = null;
        ArrayList<String> existing_courses = new ArrayList<>();
        try {


            con = Dao.getConnection();
            if(!corsoExists(corso)){
                throw new Error("Corso.corsoIsActive.error():Corso with this name doesnt exists");

            }
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select Nome From Corso Where Attivo = 'true' ; ");

            while (rs.next()) {



                existing_courses.add(rs.getString("nome"));


            }

            return existing_courses.contains(corso);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }
        return existing_courses.contains(corso);
    }

    public boolean deleteCorso(String corso) {
        Connection con = null;

        try {
            con = Dao.getConnection();

            if(!corsoExists(corso)){
                throw new Error("Corso.addFoto.error():User with this mail doesnt exists");

            }


            PreparedStatement prs = con.prepareStatement("UPDATE corso SET Attivo = 'false' WHERE corso.Nome = ?;");
            prs.setString(1, corso);


            prs.executeUpdate();





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }
        if(corsoIsActive(corso)){
            throw new Error("Corso.deleteUser.error():Unsuccesful deletion");
        }
        else{
            return true;
        }
    }

    public static Corso getCorsoByID(int ID){
        Connection con = null;
        Corso c=null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("SELECT * FROM corso WHERE ID = ?;");
            prs.setInt(1,ID);




            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                c = new Corso(rs.getInt("ID"),rs.getString("nome"), rs.getString("Attivo"));


            }

            if(c==null){
                System.out.println("Fail Get Corso");
            }
            else {
                System.out.println("Successful Get Corso");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }

        return c;
    }

    //Corso-----------------------------------------------------------------------






    //Lezione------------------------------------------------------------------------------------------


    public ArrayList<Lezione> dumpTableLezione() {
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From lezione");

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"),rs.getInt("Valutazione"), rs.getDouble("prezzo"));
                dump.add(u);
            }

            System.out.println("Successful Dump of : " + dump.size());


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return dump;
    }


    public boolean insertLezione(Lezione l) {
        Connection con = null;

        try {
            con = Dao.getConnection();
            if(lezioneExists(l.getData(),l.getOra(),l.getDocente_ID())){
                throw new Error("Lezione.insertLezione.error() :Lezione already exists");
            }

            PreparedStatement prs = con.prepareStatement("INSERT INTO `lezione` (`Data`, `Ora`, `Stato`, `Corso_ID`, `Docente_ID`,`prezzo`, `Utente_ID`) VALUES (?, ?, 'Libera', ?, ?,?, '0');");
            prs.setString(1, l.getData());
            prs.setString(2, l.getOra());
            prs.setInt(3, l.getCorso_ID());
            prs.setInt(4, l.getDocente_ID());
            prs.setDouble(5, l.getPrezzo());


            prs.executeUpdate();





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }

        if(lezioneExists(l.getData(),l.getOra(),l.getDocente_ID())){
            System.out.println("Successful Lezione Insert");
            return true;
        }
        return false;
    }

    public boolean lezioneExists(String data,String ora,int Docente_ID){
        Connection con = null;
        KeyLezione kl = null;
        ArrayList<KeyLezione> existing_leasons = new ArrayList<>();
        try {
            con = Dao.getConnection();
            //cec docenteexists


            kl = new KeyLezione(ora,data,Docente_ID);
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select Ora,Data,Docente_ID  From Lezione; ");

            while (rs.next()) {

                existing_leasons.add(new KeyLezione(rs.getString("Ora"),rs.getString("Data"),rs.getInt("Docente_ID")));


            }




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }
        return existing_leasons.contains(kl);
    }

    public boolean prenotaLezione(String data,String ora,int Docente_ID,int Utente_ID){
        Connection con = null;
        Lezione l= null;
        try {
            con = Dao.getConnection();
            if(isPrenotata(data,ora,Docente_ID) || isConclusa(data,ora,Docente_ID)){
                throw new Error("Lezione.prenotaLezione.error() : Lezione is already prenotata");
            }
            // se non esiste spunta un null pointer exception


            PreparedStatement prs = con.prepareStatement("UPDATE lezione SET Utente_ID =? , Stato = 'Prenotata' WHERE Data = ? AND Ora = ? AND Docente_ID = ?;");
            prs.setInt(1,Utente_ID);
            prs.setString(2,data);
            prs.setString(3,ora);
            prs.setInt(4,Docente_ID);


            prs.executeUpdate();

            l = getLezione(data,ora,Docente_ID);



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
           closeCon(con);

        }


        return l.getStato().equals("Prenotata") && l.getUtente_ID() == Utente_ID;
    }


    public static boolean isPrenotata(String data,String ora,int Docente_ID){
        Lezione l = getLezione(data,ora,Docente_ID);
        return l.getStato().equals("Prenotata");
    }

    public static boolean isConclusa(String data,String ora,int Docente_ID){
        Lezione l = getLezione(data,ora,Docente_ID);
        return l.getStato().equals("Conclusa");
    }


    private static Lezione getLezione(String data,String ora,int Docente_ID) {
        Connection con = null;
        Lezione l = null;
        try {
            con = Dao.getConnection();
            PreparedStatement prs = con.prepareStatement("Select * From lezione WHERE Data=? AND Ora = ? AND Docente_ID = ? ;");
            prs.setString(1, data);
            prs.setString(2, ora);
            prs.setInt(3, Docente_ID);


            ResultSet rs = prs.executeQuery();

            if (rs.next()) {
                l = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"),rs.getInt("Valutazione"), rs.getDouble("prezzo"));

            }




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }
        return l;
    }

    public boolean annullaLezione(String data, String ora, int Docente_ID){
        Connection con = null;

        try {
            con = Dao.getConnection();
            if(!lezioneExists(data, ora, Docente_ID) || isConclusa(data, ora, Docente_ID) ){
                throw new Error("Lezione.annullaLezione.error() : Lezione you tring to cancell doent exists ");
            }


            PreparedStatement prs = con.prepareStatement("UPDATE `lezione` SET `Utente_ID` = '0' , `Stato` = 'Libera' WHERE `lezione`.`Data` = ? AND `lezione`.`Ora` = ? AND `lezione`.`Docente_ID` = ?;");
            prs.setString(1,data);
            prs.setString(2,ora);
            prs.setInt(3,Docente_ID);


            prs.executeUpdate();





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeCon(con);
        }

        System.out.println(isPrenotata(data, ora, Docente_ID));
        return (!isPrenotata(data, ora, Docente_ID));
    }


    public ArrayList<Lezione> getLezioneByUtente(String mail){
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            int ID = getIDbyUtente(mail);

            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Utente_ID = ?;");
            prs.setInt(1,ID);


            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"),rs.getInt("Valutazione"), rs.getDouble("prezzo"));
                dump.add(u);
            }

            System.out.println("Successful Dump getLezioneByUtente");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return dump;
    }

//    public ArrayList<Lezione> getLeasonsStory(String mail){
//        Connection con = null;
//        ArrayList<Lezione> dump = new ArrayList<>();
//        try {
//            con = Dao.getConnection();
//            int ID = getIDbyUtente(mail);
//
//            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Utente_ID = ? AND Stato = 'Conclusa' ;");
//            prs.setInt(1,ID);
//
//
//            ResultSet rs = prs.executeQuery();
//
//            while (rs.next()) {
//                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"),rs.getInt("Valutazione"), rs.getDouble("prezzo"));
//                dump.add(u);
//            }
//
//            System.out.println("Successful Dump ");
//
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//           closeCon(con);
//
//        }
//
//
//        return dump;
//    }

    public static ArrayList<Lezione> leasonStory(){
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            assert con != null;


            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Stato = 'Conclusa' ;");



            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"),rs.getInt("Valutazione"), rs.getDouble("prezzo"));
                dump.add(u);
            }

            System.out.println("Successful Dump ");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return dump;

    }

    public static ArrayList<Lezione> getLeasonsStoryByDocente(String mail){
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            int ID = getIDbyUtente(mail);

            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Docente_ID = ? AND Stato = 'Conclusa' ;");
            prs.setInt(1,ID);


            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"),rs.getInt("Valutazione"), rs.getDouble("prezzo"));
                dump.add(u);
            }

            System.out.println("Successful Dump ");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return dump;
    }

    public boolean concludiLezione(String data, String ora, int Docente_ID){
        Connection con = null;

        try {
            con = Dao.getConnection();
            if(!lezioneExists(data, ora, Docente_ID) || !isPrenotata(data, ora, Docente_ID)){
                throw new Error("Lezione.annullaLezione.error() : Lezione you tring to conclude doent exists ");
            }


            PreparedStatement prs = con.prepareStatement("UPDATE `lezione` SET  `Stato` = 'Conclusa' WHERE `lezione`.`Data` = ? AND `lezione`.`Ora` = ? AND `lezione`.`Docente_ID` = ?;");
            prs.setString(1,data);
            prs.setString(2,ora);
            prs.setInt(3,Docente_ID);


            prs.executeUpdate();





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeCon(con);
        }


        return (isConclusa(data, ora, Docente_ID));

    }

    //se lezione gia valutata riscrive la vlutazione
    public boolean valutaLezione(String data, String ora, int Docente_ID,int stelle){
        Connection con = null;

        try {
            con = Dao.getConnection();
            if((!lezioneExists(data, ora, Docente_ID)) || (!isConclusa(data, ora, Docente_ID)) || ( stelle>5 || stelle<1) ){

                throw new Error("Lezione.annullaLezione.error() : Lezione you tring to rate doent exists or is non concluded  ");
            }


            PreparedStatement prs = con.prepareStatement("UPDATE `lezione` SET `Valutazione` = ? WHERE `lezione`.`Data` = ? AND `lezione`.`Ora` = ? AND `lezione`.`Docente_ID` = ?;");
            prs.setInt(1,stelle);
            prs.setString(2,data);
            prs.setString(3,ora);
            prs.setInt(4,Docente_ID);


            prs.executeUpdate();





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeCon(con);
        }

        Lezione l = getLezione(data, ora, Docente_ID);

        return l.getValutazione()==stelle;
    }

    public ArrayList<Lezione> getLezioneByUtenteAndByDay(String mail,String data){
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            int ID = getIDbyUtente(mail);

            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Utente_ID = ? AND Data = ? AND Stato = 'Prenotata';");
            prs.setInt(1,ID);
            prs.setString(2,data);


            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"),rs.getInt("Valutazione"), rs.getDouble("prezzo"));
                dump.add(u);
            }

            System.out.println("Successful Dump getLezioneByUtenteAndByDay");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return dump;
    }



    //Lezione-----------------------------------------------------


    //Helper

    public ArrayList<Utente> getAllProfessori(){
        Connection con = null;
        ArrayList<Utente> docenti = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From utente Where ruolo = 'docente' AND Attivo='true';");

            while (rs.next()) {

                Utente u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getDouble("Stelle"), rs.getString("Attivo"));
                docenti.add(u);
            }

            System.out.println("Successful Dump getAllProfessori");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return docenti;
    }
    public ArrayList<Utente> getDocByCorso(String corso){
        Connection con = null;
        ArrayList<Utente> docenti = new ArrayList<>();
        try {
            con = Dao.getConnection();
            PreparedStatement prs = con.prepareStatement("call getDocByCorso(?);");
            prs.setString(1,corso);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {

                Utente u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getDouble("Stelle"), rs.getString("Attivo"));
                docenti.add(u);
            }

            System.out.println("Successful Dump getAllProfessori");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeCon(con);

        }


        return docenti;
    }


    public static Corso getCorsoByNome(String corso){
        Connection con = null;
        Corso c=null;
        try {
            con = Dao.getConnection();
            if(!corsoExists(corso)){
                throw new Error("Helper.getCorsoByNome.Error : Corso with this name doent exists");
            }
            PreparedStatement prs = con.prepareStatement("Select * From corso Where Nome = ?;");
            prs.setString(1,corso);


            ResultSet rs = prs.executeQuery();

            if (rs.next()) {
                c= new Corso(rs.getInt("ID"), rs.getString("Nome"),rs.getString("Attivo"));

            }

            System.out.println("Successful Dump ");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return c;
    }


    public ArrayList<Lezione> getLezioniLibereByCorso(String corso) {
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        Corso c = getCorsoByNome(corso);
        try {
            con = Dao.getConnection();
            int ID_corso = c.getID();


            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Corso_ID = ? AND Stato = 'Libera' ;");
            prs.setInt(1,ID_corso);



            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"),rs.getInt("Valutazione"), rs.getDouble("prezzo"));
                dump.add(u);
            }

            System.out.println("Successful Dump ");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return dump;
    }


    public ArrayList<Lezione> getLezioniLibereByDocente(String docente) {
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        Utente doc = getUtenteByID(getIDbyUtente(docente));
        try {
            con = Dao.getConnection();



            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Docente_ID = ? AND Stato = 'Libera' ;");
            prs.setInt(1,doc.getID());



            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"),rs.getInt("Valutazione"), rs.getDouble("prezzo"));
                dump.add(u);
            }

            System.out.println("Successful Dump ");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           closeCon(con);

        }


        return dump;
    }
    public ArrayList<Lezione> getLezioniLibereByDocenteAndCorso(String docente,String corso) {
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        Utente doc = getUtenteByID(getIDbyUtente(docente));
        Corso cor= getCorsoByNome(corso);
        try {
            con = Dao.getConnection();



            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Docente_ID = ? AND Corso_ID=? AND Stato = 'Libera' ;");
            prs.setInt(1,doc.getID());
            prs.setInt(2,cor.getID());



            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"),rs.getInt("Valutazione"), rs.getDouble("prezzo"));
                dump.add(u);
            }

            System.out.println("Successful Dump ");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeCon(con);

        }


        return dump;
    }

    public ArrayList<String> getCorsiByDoc(String docente) {
        Connection con = null;
        ArrayList<String> corsi = new ArrayList<>();
        try {
            con = Dao.getConnection();



            PreparedStatement prs = con.prepareStatement("CALL getCorsiByDoc(?)");
            prs.setString(1,docente);




            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                corsi.add(rs.getString("Corsi"));
            }

            System.out.println("Successful Dump ");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeCon(con);

        }


        return corsi;
    }

    //Helper





}