package model;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Utilisateur {

    int id;
    String login;
    String pwd;
    String role;

    public Utilisateur() {
    }

    public Utilisateur(int id, String login, String pwd, String role) {
        setId(id);
        setLogin(login);
        setPwd(pwd);
        setRole(role);
    }

    public void save() throws SQLException {
        String sql = "INSERT INTO Utilisateur (login, pwd, role) VALUES (?, ?, ?)";
        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, login);
            ps.setString(2, pwd);
            ps.setString(3, role);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
    }

    public Vector findAll() throws SQLException {
        Vector utilisateurs = new Vector();
        String sql = "SELECT id, login, pwd, role FROM Utilisateur ORDER BY id";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Utilisateur u = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("pwd"),
                        rs.getString("role")
                );
                utilisateurs.addElement(u);
            }
        }
        return utilisateurs;
    }

    public static boolean existUtilisateur(String login, String pwd) throws SQLException {
        if (login == null || pwd == null) {
            return false;
        }

        String sql = "SELECT EXISTS(SELECT 1 FROM Utilisateur WHERE login = ? AND pwd = ?) AS exist";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, pwd);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("exist");
                }
            }
        }

        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
