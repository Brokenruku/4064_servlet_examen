package servlet;

import model.Ordinateur;
import model.OrdinateurEtat;
import model.Problem_ordinateur;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrdinateurEtatServlet extends HttpServlet {

    private static final String CHEMIN_JSON = "C:\\xampp\\htdocs\\ordinateur\\ordinateur.json";
    private static final String URL_PHP = "http://localhost/ordinateur/index.php";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Vector ordinateurs = new Ordinateur().findAll();

            StringBuilder jsonOrdinateurs = new StringBuilder("[");
            for (int i = 0; i < ordinateurs.size(); i++) {
                Ordinateur o = (Ordinateur) ordinateurs.elementAt(i);
                jsonOrdinateurs.append("{");
                jsonOrdinateurs.append("\"id\":").append(o.getId()).append(",");
                jsonOrdinateurs.append("\"marque\":\"").append(escape(o.getMarqueLibelle())).append("\",");
                jsonOrdinateurs.append("\"modele\":\"").append(escape(o.getModeleLibelle())).append("\",");
                jsonOrdinateurs.append("\"ram\":").append(o.getRam()).append(",");
                jsonOrdinateurs.append("\"processeur\":\"").append(escape(o.getProcesseur())).append("\",");
                jsonOrdinateurs.append("\"disqueDur\":").append(o.getDisqueDur());
                jsonOrdinateurs.append("}");
                if (i < ordinateurs.size() - 1) {
                    jsonOrdinateurs.append(",");
                }
            }
            jsonOrdinateurs.append("]");

            try (FileWriter fichier = new FileWriter(CHEMIN_JSON)) {
                fichier.write(jsonOrdinateurs.toString());
            } catch (IOException e) {
                afficherHistorique(request, response);
                return;
            }

            response.sendRedirect(URL_PHP);

        } catch (SQLException e) {
            request.setAttribute("erreur", "Erreur lors de l export : " + e.getMessage());
            afficherHistorique(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idOrdinateur = Integer.parseInt(request.getParameter("ordinateur"));
            int idEtat = Integer.parseInt(request.getParameter("etat"));
            String problemParam = request.getParameter("problem");
            int idProblem = (problemParam == null || problemParam.isEmpty()) ? 0 : Integer.parseInt(problemParam);
            String observation = request.getParameter("observation");
            String datee = request.getParameter("datee");

            OrdinateurEtat oe = new OrdinateurEtat();
            oe.setIdEtat(idEtat);
            oe.setIdOrdinateur(idOrdinateur);
            oe.setIdProblemOrdinateur(idProblem);
            oe.setDate(datee);
            oe.setObservation(observation);
            oe.save();

        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("erreur", "Erreur lors de l enregistrement : " + e.getMessage());
        }

        afficherHistorique(request, response);
    }

    private void afficherHistorique(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setAttribute("historique", new OrdinateurEtat().findAll());
            request.setAttribute("etats", new model.Etat().findAll());
            request.setAttribute("ordinateurs", new Ordinateur().findAll());
            request.setAttribute("problemes", new Problem_ordinateur().findAll());
        } catch (SQLException e) {
            request.setAttribute("erreur", "Erreur lors du chargement : " + e.getMessage());
        }

        request.getRequestDispatcher("historique.jsp").forward(request, response);
    }

    private static String escape(String valeur) {
        if (valeur == null) {
            return "";
        }
        return valeur.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}