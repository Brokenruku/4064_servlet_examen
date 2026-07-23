package servlet;

import model.Ordinateur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrdinateurServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        afficherListe(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String erreur = null;

        try {
            int idModele = Integer.parseInt(request.getParameter("idModele"));
            int ram = Integer.parseInt(request.getParameter("ram"));
            String processeur = request.getParameter("processuer");
            int disqueDur = Integer.parseInt(request.getParameter("disqueDur"));

            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                Ordinateur ordinateur = new Ordinateur(id, idModele, ram, processeur, disqueDur);
                ordinateur.update();
            } else {
                Ordinateur ordinateur = new Ordinateur(idModele, ram, processeur, disqueDur);
                ordinateur.save();
            }

        } catch (SQLException e) {
            erreur = "Erreur lors de l enregistrement : " + e.getMessage();
        }

        afficherListe(request, response);
    }

    private void afficherListe(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Vector ordinateurs = new Vector();

        try {
            ordinateurs = new Ordinateur().findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erreur", e.getMessage());
        }

        request.setAttribute("ordinateurs", ordinateurs);

        RequestDispatcher dispatcher = request.getRequestDispatcher("liste.jsp");
        dispatcher.forward(request, response);
    }
}