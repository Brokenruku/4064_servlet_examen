package servlet;

import model.OrdinateurEtat;
import model.Utilisateur;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        String pwd = (String) session.getAttribute("pwd");

        boolean connecte;
        try {
            connecte = Utilisateur.existUtilisateur(login, pwd);
        } catch (Exception e) {
            connecte = false;
        }

        if (!connecte) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String datej = request.getParameter("datej");

        if (datej == null || datej.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            datej = sdf.format(new Date());
        }

        try {
            SimpleDateFormat sdfIn = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd");
            String dateSql = sdfOut.format(sdfIn.parse(datej));

            request.setAttribute("etats", OrdinateurEtat.findEtatNombreDate(dateSql));
            request.setAttribute("problemes", OrdinateurEtat.findBlemEtatNombreDate(dateSql));
            request.setAttribute("datej", datej);

        } catch (Exception e) {
            request.setAttribute("erreur", "date invalide ou erreur : " + e.getMessage());
            request.setAttribute("datej", datej);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("page1.jsp");
        dispatcher.forward(request, response);
    }
}