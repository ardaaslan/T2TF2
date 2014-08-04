/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author GOX
 */
public class RegisterServlet extends HttpServlet {

    int flag = 0;
    boolean errorFlag = false;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (flag == 0) {
            flag++;

            RequestDispatcher rs = request.getRequestDispatcher("registerpage.html");
            rs.forward(request, response);
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            
            if (username.length() == 0) {
                RequestDispatcher rs = request.getRequestDispatcher("registerpage.html");
                out.println("<font color=");
                out.println('"');
                out.println("red");
                out.println('"');
                out.println('>');

                out.println("<h5>");
                out.println("<center>");
                out.println("Please Enter Your Username");
                out.println("</center>");
                out.println("</h5>");

                out.println("</font>");
                
                rs.include(request, response);
            } else if (password.length() == 0) {
                RequestDispatcher rs = request.getRequestDispatcher("registerpage.html");

                out.println("<font color=");
                out.println('"');
                out.println("red");
                out.println('"');
                out.println('>');

                out.println("<h5>");
                out.println("<center>");
                out.println("Please Enter Your Password");
                out.println("</center>");
                out.println("</h5>");

                out.println("</font>");
                rs.include(request, response);
            } else {
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test",
                            "root", "arda2112");
                    
                    String errorCheck = "select * from users where'" + username + "'" ;

                    PreparedStatement pss = con.prepareStatement(errorCheck);
                    
                    ResultSet rs = pss.executeQuery();
                    
                    if(!rs.next())errorFlag = true;
                    
                    String command = "insert into users values('" + username + "','" + password + "')";

                    PreparedStatement ps = con.prepareStatement(command);

                    ps.executeUpdate();

                } catch (ClassNotFoundException | SQLException e) {
                    
                }
                if(errorFlag == false){ 
                    flag = 0;
                    errorFlag = false;
                    response.sendRedirect("LoginServlet");
                }
                else{
                    RequestDispatcher rs = request.getRequestDispatcher("registerpage.html");

                    out.println("<font color=");
                    out.println('"');
                    out.println("red");
                    out.println('"');
                    out.println('>');

                    out.println("<h5>");
                    out.println("<center>");
                    out.println("User Already Exists");
                    out.println("</center>");
                    out.println("</h5>");

                    out.println("</font>");
                    rs.include(request, response);
                }
            }
        }
    }
}
