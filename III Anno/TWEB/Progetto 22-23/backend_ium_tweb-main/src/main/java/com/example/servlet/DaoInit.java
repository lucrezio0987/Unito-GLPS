package com.example.servlet;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;
@WebServlet(name = "DaoInit", value = "/DaoInit", loadOnStartup = 1, asyncSupported = false)
public class DaoInit extends HttpServlet {











    public void init(ServletConfig config) {
       Dao dao = new Dao(config.getServletContext().getInitParameter("dbURL"),config.getServletContext().getInitParameter("dbUser"),config.getServletContext().getInitParameter("dbUserPass"));
       config.getServletContext().setAttribute("Dao",dao);
    }







}