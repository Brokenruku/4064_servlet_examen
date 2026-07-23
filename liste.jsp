<%@ page import="java.util.Vector" %>
<%@ page import="model.Ordinateur" %>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.OrdinateurEtat" %>
<%@ page import="model.Etat" %>
<%@ page import="model.Problem_ordinateur" %>

<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <title>ETU4064</title>
    </head>
    <body>

        <h1>ETU4064</h1>
        <%
            Vector ordinateurs = (Vector) request.getAttribute("ordinateurs");
            if (ordinateurs == null) {
                ordinateurs = new Vector();
            }
            
            String login = (String) session.getAttribute("login");
            String pwd = (String) session.getAttribute("pwd");
            boolean connecte = Utilisateur.existUtilisateur(login, pwd);
        %>


        <% if (connecte) { %>
        <a href="<%= request.getContextPath() %>/showForm">Ajout</a>
        <a href="<%= request.getContextPath() %>/historique_ordi">Historiques etats</a>
        <% if (connecte) { %>
        | <a href="<%= request.getContextPath() %>/page1">Etat date</a>
        <% } %>
        <% if (!connecte) { %>
        | <a href="<%= request.getContextPath() %>/login.jsp">Se connecter</a>
        <% } %>
        <% } %>


        <form action="<%= request.getContextPath() %>/page1" method="get">
            <input type="text" name="datej" placeholder="dd/mm/yyyy"%>
            <button type="submit">ok</button>
        </form>

        <h1>ETU4064</h1>

        <% if (!connecte) { %>
        | <a href="<%= request.getContextPath() %>/login.jsp">Se connecter</a>
        <% } %>
        <hr>

        <table>
            <tr>
                <td>ID</td>
                <td>Marque</td>
                <td>Modele</td>
                <td>RAM</td>
                <td>Processeur</td>
                <td>Disque dur</td>
                <td>            </td>
            </tr>
            <%
                for (int i = 0; i < ordinateurs.size(); i++) {
                    Ordinateur o = (Ordinateur) ordinateurs.elementAt(i);
                %>
                <tr>
                    <td><%= o.getId() %></td>
                    <td><%= o.getMarqueLibelle() %></td>
                    <td><%= o.getModeleLibelle() %></td>
                    <td><%= o.getRam() %></td>
                    <td><%= o.getProcesseur() %></td>
                    <td><%= o.getDisqueDur() %></td>
                    <td>
                        <% if (connecte) { %>
                        <a href="<%= request.getContextPath() %>/showForm?id=<%= o.getId() %>">M</a>
                        <a href="<%= request.getContextPath() %>/deleteOrdinateur?id=<%= o.getId() %>">S</a>
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </table>

        </body>
    </html>
