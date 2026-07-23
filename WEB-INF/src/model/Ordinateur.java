package model;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Ordinateur {

    int id;
    int idModele;
    int ram;
    String processeur;
    int disqueDur;
    String modeleLibelle;
    String marqueLibelle;

    public Ordinateur() {
    }

    public Ordinateur(int idModele, int ram, String processeur, int disqueDur) {
        setIdModele(idModele);
        setRam(ram);
        setProcesseur(processeur);
        setDisqueDur(disqueDur);
    }

    public Ordinateur(int id, int idModele, int ram, String processeur, int disqueDur) {
        setId(id);
        setIdModele(idModele);
        setRam(ram);
        setProcesseur(processeur);
        setDisqueDur(disqueDur);
    }

    public Ordinateur(int id, int idModele, int ram, String processeur, int disqueDur, String modeleLibelle, String marqueLibelle) {
        setId(id);
        setIdModele(idModele);
        setRam(ram);
        setProcesseur(processeur);
        setDisqueDur(disqueDur);
        setModeleLibelle(modeleLibelle);
        setMarqueLibelle(marqueLibelle);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdModele() {
        return idModele;
    }

    public void setIdModele(int idModele) {
        this.idModele = idModele;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getProcesseur() {
        return processeur;
    }

    public void setProcesseur(String processeur) {
        this.processeur = processeur;
    }

    public int getDisqueDur() {
        return disqueDur;
    }

    public void setDisqueDur(int disqueDur) {
        this.disqueDur = disqueDur;
    }

    public String getModeleLibelle() {
        return modeleLibelle;
    }

    public void setModeleLibelle(String modeleLibelle) {
        this.modeleLibelle = modeleLibelle;
    }

    public String getMarqueLibelle() {
        return marqueLibelle;
    }

    public void setMarqueLibelle(String marqueLibelle) {
        this.marqueLibelle = marqueLibelle;
    }

    public void save() throws SQLException {
        String sql = "INSERT INTO Ordinateur (idModele, ram, processuer, disqueDur) VALUES (?, ?, ?, ?)";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idModele);
            ps.setInt(2, ram);
            ps.setString(3, processeur);
            ps.setInt(4, disqueDur);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        String sql = "UPDATE Ordinateur SET idModele = ?, ram = ?, processuer = ?, disqueDur = ? WHERE id = ?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, idModele);
            ps.setInt(2, ram);
            ps.setString(3, processeur);
            ps.setInt(4, disqueDur);
            ps.setInt(5, id);

            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM Ordinateur WHERE id = ?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Ordinateur findById(int id) throws SQLException {
        Ordinateur ordinateur = null;
        String sql = "SELECT id, idModele, ram, processuer, disqueDur FROM Ordinateur WHERE id = ?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ordinateur = new Ordinateur(
                            rs.getInt("id"),
                            rs.getInt("idModele"),
                            rs.getInt("ram"),
                            rs.getString("processuer"),
                            rs.getInt("disqueDur")
                    );
                }
            }
        }
        return ordinateur;
    }

    public Vector findAll() throws SQLException {
        Vector ordinateurs = new Vector();

        String sql = "SELECT o.id, o.idModele, o.ram, o.processuer, o.disqueDur, "
                   + "m.libelle AS modeleLibelle, ma.libelle AS marqueLibelle "
                   + "FROM Ordinateur o "
                   + "JOIN Modele m ON o.idModele = m.id "
                   + "JOIN Marque ma ON m.idMarque = ma.id "
                   + "ORDER BY o.id";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Ordinateur o = new Ordinateur(
                        rs.getInt("id"),
                        rs.getInt("idModele"),
                        rs.getInt("ram"),
                        rs.getString("processuer"),
                        rs.getInt("disqueDur"),
                        rs.getString("modeleLibelle"),
                        rs.getString("marqueLibelle")
                );
                ordinateurs.addElement(o);
            }
        }
        return ordinateurs;
    }
}