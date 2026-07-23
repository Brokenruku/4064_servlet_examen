package model;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class OrdinateurEtat {

    int id;
    int idEtat;
    int idOrdinateur;
    int idProblemOrdinateur;
    String date;
    String observation;
    String etatLibelle;

    public OrdinateurEtat() {
    }

    public OrdinateurEtat(int idEtat, int idOrdinateur, int idProblemOrdinateur, String observation) {
        setIdEtat(idEtat);
        setIdOrdinateur(idOrdinateur);
        setIdProblemOrdinateur(idProblemOrdinateur);
        setObservation(observation);
    }

    public OrdinateurEtat(int id, int idEtat, int idOrdinateur, String date, String observation) {
        setId(id);
        setIdEtat(idEtat);
        setIdOrdinateur(idOrdinateur);
        setDate(date);
        setObservation(observation);
    }

    public OrdinateurEtat(int id, int idEtat, int idOrdinateur, String date, String observation, String etatLibelle) {
        setId(id);
        setIdEtat(idEtat);
        setIdOrdinateur(idOrdinateur);
        setDate(date);
        setObservation(observation);
        setEtatLibelle(etatLibelle);
    }

    public void save() throws SQLException {
        String sql = "INSERT INTO Ordinateur_etat (idEtat, idOrdinateur, date, observation, id_problem_ordinateur) " +
                "VALUES (?, ?, CAST(? AS TIMESTAMP), ?, ?)";
        try (Connection cnx = DBConnection.getConnection();
                PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idEtat);
            ps.setInt(2, idOrdinateur);
            ps.setString(3, date);
            ps.setString(4, observation);
            if (idProblemOrdinateur == 0) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                if(idEtat == 1){
                    ps.setNull(5, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(5, idProblemOrdinateur);
                }
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
    }

    public Vector findAll() throws SQLException {
        Vector historique = new Vector();

        String sql = "SELECT oe.id, oe.idEtat, oe.idOrdinateur, oe.date, oe.observation, e.libelle AS etatLibelle "
                + "FROM Ordinateur_etat oe "
                + "JOIN Etat e ON oe.idEtat = e.id "
                + "ORDER BY oe.id";

        try (Connection cnx = DBConnection.getConnection();
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                OrdinateurEtat oe = new OrdinateurEtat(
                        rs.getInt("id"),
                        rs.getInt("idEtat"),
                        rs.getInt("idOrdinateur"),
                        String.valueOf(rs.getTimestamp("date")),
                        rs.getString("observation"),
                        rs.getString("etatLibelle"));
                historique.addElement(oe);
            }
        }
        return historique;
    }

    
    public static Vector findEtatNombreDate(String date) throws SQLException {
        Vector resultat = new Vector();
        String sql = "SELECT e.libelle, COUNT(*) as nb FROM Ordinateur_etat oe JOIN Etat e ON e.id = oe.idEtat WHERE oe.date::date = ? GROUP BY e.libelle";
        try (Connection cnx = DBConnection.getConnection();
            PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultat.addElement(new String[]{rs.getString("libelle"), String.valueOf(rs.getInt("nb"))});
                }
            }
        }
        return resultat;
    }

    public static Vector findBlemEtatNombreDate(String date) throws SQLException {
        Vector resultat = new Vector();
        String sql = "SELECT p.nom_problem, COUNT(*) as nb FROM Ordinateur_etat oe JOIN Problem_ordinateur p ON p.id = oe.id_problem_ordinateur WHERE oe.date::date = ? AND oe.idetat = 2 GROUP BY p.nom_problem";
        try (Connection cnx = DBConnection.getConnection();
            PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultat.addElement(new String[]{rs.getString("nom_problem"), String.valueOf(rs.getInt("nb"))});
                }
            }
        }
        return resultat;
    }

    public static String toJson(Vector historique) {
        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < historique.size(); i++) {
            OrdinateurEtat oe = (OrdinateurEtat) historique.elementAt(i);

            json.append("{");
            json.append("\"id\":").append(oe.getId()).append(",");
            json.append("\"idOrdinateur\":").append(oe.getIdOrdinateur()).append(",");
            json.append("\"idEtat\":").append(oe.getIdEtat()).append(",");
            json.append("\"etat\":\"").append(escape(oe.getEtatLibelle())).append("\",");
            json.append("\"date\":\"").append(escape(oe.getDate())).append("\",");
            json.append("\"observation\":\"").append(escape(oe.getObservation())).append("\"");
            json.append("}");

            if (i < historique.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        return json.toString();
    }

    private static String escape(String valeur) {
        if (valeur == null) {
            return "";
        }
        return valeur.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEtat() {
        return idEtat;
    }

    public void setIdEtat(int idEtat) {
        this.idEtat = idEtat;
    }

    public int getIdOrdinateur() {
        return idOrdinateur;
    }

    public void setIdOrdinateur(int idOrdinateur) {
        this.idOrdinateur = idOrdinateur;
    }

    public int getIdProblemOrdinateur() {
        return idProblemOrdinateur;
    }

    public void setIdProblemOrdinateur(int idProblemOrdinateur) {
        this.idProblemOrdinateur = idProblemOrdinateur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getEtatLibelle() {
        return etatLibelle;
    }

    public void setEtatLibelle(String etatLibelle) {
        this.etatLibelle = etatLibelle;
    }
}