package servlet;

import model.Model;
import model.Ordinateur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowFormOrdinateurServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Vector modeles = new Vector();

        try {
            modeles = new Model().findAll();
        } catch (SQLException e) {
            request.setAttribute("erreur", "Erreur lors du chargement des modeles : " + e.getMessage());
        }

        request.setAttribute("modeles", modeles);

        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Ordinateur ordinateur = new Ordinateur().findById(id);
                request.setAttribute("ordinateur", ordinateur);
            } catch (NumberFormatException e) {
                request.setAttribute("erreur", "Identifiant invalide");
            } catch (SQLException e) {
                request.setAttribute("erreur", "Erreur lors du chargement de l ordinateur : " + e.getMessage());
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}