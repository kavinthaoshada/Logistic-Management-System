package com.ozzz.web.servlet;

import com.ozzz.ejb.remote.LoginService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/LoginServiceImpl")
    private LoginService login;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String log = login.login(email, password);
        if (!log.equals("empty")) {
            if (log.equals("admin")) {
                req.login("admin", "admin1234");
            } else if (log.equals("employee")) {
                req.login("employee", "employee1234");
            }

            HttpSession session = req.getSession();
            session.setAttribute("login", log);
            session.setAttribute("email", email);

            resp.sendRedirect("home.jsp");

            resp.getWriter().write("Login : " + log);

        } else {

        }

    }
}
