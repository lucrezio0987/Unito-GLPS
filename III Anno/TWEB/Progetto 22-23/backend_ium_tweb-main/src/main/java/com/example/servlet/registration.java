/*
package com.example.servlet;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;
@WebServlet(name = "helloServlet", value = "/registration")
public class registration extends HttpServlet {
    Dao dao ;










    public void init(ServletConfig config) {
        dao = (Dao) config.getServletContext().getAttribute("dao");


    }



    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("account");
        String password = request.getParameter("pass");
        String ruolo = request.getParameter("ruolo");

        int flag = Dao.insertUtente(new Utente(username,password,ruolo));
        HttpSession s = request.getSession();
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet Login</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>Sei collegato come: " + s.getAttribute("username") + "</p>");
        out.println("<p>Ruolo: " + s.getAttribute("ruolo") + "</p>");
        out.print("<p>Stato della sessione: ");
        if (s.isNew())
            out.println(" nuova sessione</p>");
        else out.println(" vecchia sessione</p>");
        out.println("<p>ID di sessione: " + s.getId() + "</p>");


        out.println("<h1> " +"Stato :" );
        if(flag!=0){
            out.println("Successo");
        }
        else {
            out.println("Fallimento");
        }

        out.println("</h1>");
        out.println("</body>");
        out.println("</html>");
    }



}



 */