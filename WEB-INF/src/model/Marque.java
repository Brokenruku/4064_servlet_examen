package model;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Marque {

    int id;
    String libelle;

    public Marque() {
    }

    public Marque(int id, String libelle){
        setId(id);
        setLibelle(libelle);
    }

    public void save() throws SQLException {
        String sql = "INSERT INTO Marque (libelle) VALUES (?)";
        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, libelle);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
    }

    public Vector findAll() throws SQLException {
        Vector marques = new Vector();
        String sql = "SELECT id, libelle FROM Marque ORDER BY id";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Marque ma = new Marque(rs.getInt("id"), rs.getString("libelle"));
                marques.addElement(ma);
            }
        }
        return marques;
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