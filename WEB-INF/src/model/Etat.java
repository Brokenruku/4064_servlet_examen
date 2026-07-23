package model;

import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Etat {

    int id;
    String libelle;

    public Etat() {
    }

    public Etat(int id, String libelle) {
        setId(id);
        setLibelle(libelle);
    }

    public Vector findAll() throws SQLException {
        Vector etats = new Vector();
        String sql = "SELECT id, libelle FROM Etat ORDER BY id";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Etat e = new Etat(rs.getInt("id"), rs.getString("libelle"));
                etats.addElement(e);
            }
        }
        return etats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
