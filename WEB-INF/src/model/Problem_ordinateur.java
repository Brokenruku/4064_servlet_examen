package model;

import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Problem_ordinateur {
    int id;
    String nom_problem;

    public Problem_ordinateur() {
    }

    public Problem_ordinateur(int id, String nom_problem) {
        setId(id);
        setNom_problem(nom_problem);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_problem() {
        return nom_problem;
    }

    public void setNom_problem(String nom_problem) {
        this.nom_problem = nom_problem;
    }

    public Vector findAll() throws SQLException {
        Vector problem_ordinateur = new Vector();
        String sql = "SELECT id, nom_problem FROM Problem_ordinateur ORDER BY id";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Problem_ordinateur p = new Problem_ordinateur(
                        rs.getInt("id"),
                        rs.getString("nom_problem")
                );
                problem_ordinateur.addElement(p);
            }
        }
        return problem_ordinateur;
    }
}