<%@ page import="java.util.Vector" %>
<%@ page import="model.Ordinateur" %>
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
            Vector etats = (Vector) request.getAttribute("etats");
            if (etats == null) etats = new Vector();
            Vector problemes = (Vector) request.getAttribute("problemes");
            if (problemes == null) problemes = new Vector();
            String datej = (String) request.getAttribute("datej");
            String erreur = (String) request.getAttribute("erreur");
        %>


        <form action="<%= request.getContextPath() %>/page1" method="get">
            <input type="text" name="datej" placeholder="dd/mm/yyyy" value="<%= datej %>">
            <button type="submit">ok</button>
        </form>

        <% if (erreur != null) { %>
        <p style="color:red;"><%= erreur %></p>
        <% } %>

        <% for (int i = 0; i < etats.size(); i++) {
            String[] e = (String[]) etats.elementAt(i);
        %> <br>
        <%= e[0] %> <%= e[1] %>
        <% } %>

        <br>
        <br>

        <% for (int i = 0; i < problemes.size(); i++) {
            String[] p = (String[]) problemes.elementAt(i); 
        %> <br>
        <%= p[0] %> <%= p[1] %>
        <% } %>

    </body>
</html>

