package com.example.servlet.api.corso;


import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@WebServlet(name = "apiCorso", value = "/apiCorso")
public class apiCorso extends HttpServlet {
    private Dao dao;

    @Override
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

        HttpSession s = request.getSession();
        PrintWriter out = response.getWriter();
        System.out.println(request.getParameter("path"));
        response.setContentType("application/json");


        if(request.getParameter("path")!=null){
            switch(request.getParameter("path")){
                case "getAllCorsi" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {


                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String json = gson.toJson(dao.getCorsiByDoc("all"));
                        JsonElement je = JsonParser.parseString(json);
                        out.println( gson.toJson(je) );
                        out.flush();



                    }
                    break;
                }
                case "getCorsiByDoc" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {

                        String mail = request.getParameter("mail");
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String json = gson.toJson(dao.getCorsiByDoc(mail));
                        JsonElement je = JsonParser.parseString(json);
                        out.println( gson.toJson(je) );
                        out.flush();



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






