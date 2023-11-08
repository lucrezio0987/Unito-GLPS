
package com.example.servlet.api.utenti;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import Dao.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@WebServlet(name = "login", value = "/apiUtente")
public class apiUtente extends HttpServlet {
    static Dao dao;


    public void init(ServletConfig config) {


        dao  = (Dao) config.getServletContext().getAttribute("Dao");
        System.out.println(dao==null);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        System.out.println(request.getParameter("path"));
        response.setContentType("application/json");


    if(request.getParameter("path")!=null){
        switch(request.getParameter("path")){
            case "login" : {
                if (dao == null) {
                    out.println("dao is null");
                }else {
                    String userName = request.getParameter("mail");
                    String password = request.getParameter("pass");
                    Utente u = dao.getUtente(userName);

                    if(u==null){
                        out.print("{" +
                                "\"login_state\"" + ":" + "\"other\"" +" ,"+
                                "\"state_description\"" + ":" + "\"User not exists\"" +

                                "}");
                        out.flush();
                        break;
                    }


                     if (Dao.checkMD5(u.getPassword(), password)&&u!=null) {

                        out.print("{" +
                                "\"login_state\"" + ":" + "\"true\"" +" ,"+
                                "\"state_description\"" + ":" + "\"successful login\"" +

                                "}");
                        out.flush();


                    }
                     else {


                             out.print("{" +
                                     "\"login_state\"" + ":" + "\"false\"" +" ,"+
                                     "\"state_description\"" + ":" + "\"Password does not match\"" +

                                     "}");
                             out.flush();


                    }
                    break;
                }
            }
            case "registration" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    String email = request.getParameter("mail");
                    String password = request.getParameter("pass");
                    String nome = request.getParameter("nome");
                    String cognome = request.getParameter("cognome");
                    //ceck temporaneo --> succesivamente con la user session si fara il check se la mail esiste gia
                    //prima check   --> se true ritorno alla pagina login con i dati settati dalla sessione
                    //se check false si procede con la insertion

                    try {
                        if(dao.insertUtente(new Utente(0,email,password,nome,cognome,"utente"))){
                            out.print("{" +
                                    "\"registration_state\"" + ":" + "\"succesful\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"user succesfuly registred\"" +
                                    "}");
                            out.flush();
                        }
                        else {
                            out.print("{" +
                                    "\"registration_state\"" + ":" + "\"fail\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"failed to register\"" +
                                    "}");
                            out.flush();
                        }

                    }
                    catch (Error erro){
                        out.print("{" +
                                "\"registration_state\"" + ":" + "\"exists\"" +" ,"+
                                "\"state_description\"" + ":" + "\"user with this email already exists\"" +
                                "}");
                        out.flush();
                    }

                }
                break;
            }
            case "getAllDocenti" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {

                    ArrayList<Utente> docenti = dao.getAllProfessori();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String json = gson.toJson(docenti);
                    JsonElement je = JsonParser.parseString(json);
                    out.println( gson.toJson(je) );
                    out.flush();


                }
                break;
            }
            case "getDocByCorso" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    String corso = request.getParameter("corso");
                    ArrayList<Utente> docenti = dao.getDocByCorso(corso);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String json = gson.toJson(docenti);
                    JsonElement je = JsonParser.parseString(json);
                    out.println( gson.toJson(je) );
                    out.flush();


                }
                break;
            }
            case "deleteUtente" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    boolean flag= false;
                    String email = request.getParameter("mail");
                    try {
                        flag = dao.deleteUtente(email);
                        if(flag){
                            out.print("{" +
                                    "\"delete_state\"" + ":" + "\"succes\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"user with this email was deleted\"" +
                                    "}");
                            out.flush();
                        }
                    }
                    catch (Error error){
                        out.print("{" +
                                "\"delete_state\"" + ":" + "\"already\"" +" ,"+
                                "\"state_description\"" + ":" + "\"user with this email is already deleted or doesnt exist\"" +
                                "}");
                        out.flush();
                    }





                }
                break;

            }






        }
    }
    else{
          out.println("Selected path doesnt exists ");
          out.flush();
      }
    }
}





