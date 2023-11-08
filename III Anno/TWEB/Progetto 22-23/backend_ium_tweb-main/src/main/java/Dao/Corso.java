package Dao;

 public class Corso {




        private int ID;
        private String nome;

        private boolean attivo=true;

        //object to insert
        public Corso(int ID,String nome){
            this.nome = nome;
            this.ID = ID;
        }


        //object to get
         public Corso(int ID,String nome,String attivo){
             this.nome = nome;
             this.ID = ID;
             if(attivo.equals("false")){
                 this.attivo = false;
             }

         }






     public String getNome() {
        return nome;
    }

     public int getID() {
         return ID;
     }

     @Override
     public String toString() {
         return "Corso{" +
                 "ID=" + ID +
                 ", nome='" + nome + '\'' +
                 ", attivo=" + attivo +
                 '}';
     }

     public boolean isAttivo() {
         return attivo;
     }

     public void setAttivo(boolean attivo) {
         this.attivo = attivo;
     }
 }
