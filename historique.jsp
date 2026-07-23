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

<%
    Vector historique = (Vector) request.getAttribute("historique");
    if (historique == null) historique = new Vector();

    Vector etats = (Vector) request.getAttribute("etats");
    if (etats == null) etats = new Vector();

    Vector problemes = (Vector) request.getAttribute("problemes");
    if (problemes == null) problemes = new Vector();

    Vector ordinateurs = (Vector) request.getAttribute("ordinateurs");
    if (ordinateurs == null) ordinateurs = new Vector();

    String erreur = (String) request.getAttribute("erreur");
%>

<h1>ETU4064</h1>
<a href="<%= request.getContextPath() %>/ordi">Liste des ordinateurs</a>
<hr>

<h2>Historique d etats</h2>

<% if (erreur != null) { %>
<p style="color:red;"><%= erreur %></p>
<% } %>

<table border="1" cellpadding="5">
    <tr>
        <td>ID</td>
        <td>Ordinateur</td>
        <td>Etat</td>
        <td>Date</td>
        <td>Observation</td>
    </tr>
    <%
        for (int i = 0; i < historique.size(); i++) {
            OrdinateurEtat oe = (OrdinateurEtat) historique.elementAt(i);
    %>
    <tr>
        <td><%= oe.getId() %></td>
        <td><%= oe.getIdOrdinateur() %></td>
        <td><%= oe.getEtatLibelle() %></td>
        <td><%= oe.getDate() %></td>
        <td><%= oe.getObservation() %></td>
    </tr>
    <% } %>
</table>

<form action="<%= request.getContextPath() %>/creer_etat" method="post">
    <p>Ordinateur :
        <select name="ordinateur">
            <% for (int i = 0; i < ordinateurs.size(); i++) {
                Ordinateur o = (Ordinateur) ordinateurs.elementAt(i);
            %>
            <option value="<%= o.getId() %>"><%= o.getId() %> - <%= o.getModeleLibelle() %></option>
            <% } %>
        </select>
    </p>
    <p>Etat :
        <select name="etat" id="etatSelect" onchange="miporta_blem()">
            <% for (int i = 0; i < etats.size(); i++) {
                Etat e = (Etat) etats.elementAt(i);
            %>
            <option value="<%= e.getId() %>"><%= e.getLibelle() %></option>
            <% } %>
        </select>
    </p>
    <div class="eto" style="display:none;">
        <p>Probleme :
            <select name="problem">
                <option value="">Aucun</option>
                <% for (int i = 0; i < problemes.size(); i++) {
                    Problem_ordinateur p = (Problem_ordinateur) problemes.elementAt(i);
                %>
                <option value="<%= p.getId() %>"><%= p.getNom_problem() %></option>
                <% } %>
            </select>
        </p>
    </div>

    <p>Observation : <input type="text" name="observation"></p>

    <p>datee : <input type="text" name="datee"></p>

    <button type="submit">Enregistrer</button>
</form>

<hr>
<a href="<%= request.getContextPath() %>/historique_ordi">Exporter</a>

</body>
<script>
    function miporta_blem() {
        var select = document.getElementById("etatSelect");
        var bloc = document.querySelector(".eto");
        var libelle = select.options[select.selectedIndex].text;
        if (libelle.indexOf("Non") !== -1) {
            bloc.style.display = "block";
        } else {
            bloc.style.display = "none";
        }
    }
</script>
</html>
