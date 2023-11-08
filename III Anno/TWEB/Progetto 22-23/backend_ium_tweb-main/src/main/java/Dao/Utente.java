package Dao;

public class Utente {
    private int ID;
    private String email;
    private  String password;
    private String nome;
    private String cognome;
    private String ruolo;

    private String pf=null;  //profile foto
    private double stelle=0;
    private boolean attivo = true;



    //object to insert
    public Utente(int ID,String email, String password,String nome,String cognome, String ruolo){
        this.ID=ID;
        this.email=email;
        this.password=password;
        this.nome=nome;
        this.cognome  = cognome;
        this.ruolo = ruolo;


    }

    //object to get
    public Utente(int ID,String email, String password,String nome,String cognome, String ruolo,String PF,double Stelle,String attivo){
        this.ID=ID;
        this.email=email;
        this.password=password;
        this.nome=nome;
        this.cognome  = cognome;
        this.ruolo = ruolo;
        this.pf=PF;
        this.stelle=Stelle;
        if(attivo=="false"){
            this.attivo = false;
        }
        else if(attivo=="true") {
            this.attivo=true;
        }

    }



    public int getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getRuolo() {
        return ruolo;
    }


    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }




    public double getStelle() {
        return stelle;
    }

    public void setStelle(double stelle) {
        this.stelle = stelle;
    }



    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "ID=" + ID +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", ruolo='" + ruolo + '\'' +
                ", pf='" + pf + '\'' +
                ", stelle=" + stelle +
                ", attivo=" + attivo +
                '}';
    }
}
