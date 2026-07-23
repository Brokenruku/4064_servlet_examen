package servlet;

import model.Ordinateur;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOrdinateurServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Ordinateur.delete(id);
            } catch (SQLException e) {
                request.setAttribute("erreur", "Erreur lors de la suppression : " + e.getMessage());
            }
        }

        response.sendRedirect(request.getContextPath() + "/ordi");
    }
}