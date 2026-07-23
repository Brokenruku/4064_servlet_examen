package model;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Model {

    int id;
    String libelle;
    int idMarque;
    String referencee;
    String marqueLibelle;

    public Model() {
    }

    public Model(int id, String libelle, int idMarque, String referencee) {
        setId(id);
        setLibelle(libelle);
        setIdMarque(idMarque);
        setReferencee(referencee);
    }

    public Model(int id, String libelle, int idMarque, String referencee, String marqueLibelle) {
        setId(id);
        setLibelle(libelle);
        setIdMarque(idMarque);
        setReferencee(referencee);
        setMarqueLibelle(marqueLibelle);
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

    public int getIdMarque() {
        return idMarque;
    }

    public void setIdMarque(int idMarque) {
        this.idMarque = idMarque;
    }

    public String getReferencee() {
        return referencee;
    }

    public void setReferencee(String referencee) {
        this.referencee = referencee;
    }

    public String getMarqueLibelle() {
        return marqueLibelle;
    }

    public void setMarqueLibelle(String marqueLibelle) {
        this.marqueLibelle = marqueLibelle;
    }

    public void save() throws SQLException {
        String sql = "INSERT INTO Modele (libelle, idMarque, referencee) VALUES (?, ?, ?)";
        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, libelle);
            ps.setInt(2, idMarque);
            ps.setString(3, referencee);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
    }

    public Vector findAll() throws SQLException {
        Vector modeles = new Vector();
        String sql = "SELECT m.id, m.libelle, m.idMarque, m.referencee, ma.libelle AS marqueLibelle "
                   + "FROM Modele m "
                   + "JOIN Marque ma ON m.idMarque = ma.id "
                   + "ORDER BY m.id";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Model m = new Model(
                        rs.getInt("id"),
                        rs.getString("libelle"),
                        rs.getInt("idMarque"),
                        rs.getString("referencee"),
                        rs.getString("marqueLibelle")
                );
                modeles.addElement(m);
            }
        }
        return modeles;
    }
}