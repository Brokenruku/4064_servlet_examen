<%@ page import="java.util.Vector" %>
<%@ page import="model.Model" %>
<%@ page import="model.Ordinateur" %>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <title>4064</title>
    </head>
    <body>

        <h1>ETU4064</h1>
        <%
            Vector modeles = (Vector) request.getAttribute("modeles");
            if (modeles == null) {
                modeles = new Vector();
            }
            Ordinateur ordinateur = (Ordinateur) request.getAttribute("ordinateur");
            String erreur = (String) request.getAttribute("erreur");
        %>

        <h1>ETU4064</h1>

        <form action="<%= request.getContextPath() %>/ordi" method="post">

            <input type="hidden" name="id" value="<%
            if (ordinateur != null) {
            out.print(ordinateur.getId());
            } else {
            out.print("");
            } %>">

            <label for="idModele">Modele</label>
            <select id="idModele" name="idModele">
                <%
                    for (int i = 0; i < modeles.size(); i++) {
                        Model m = (Model) modeles.elementAt(i);
                        boolean selected = (ordinateur != null) && (ordinateur.getIdModele() == m.getId());
                    %>
                    <option value="<%= m.getId() %>" <%
                        if (selected) {
                        out.print("selected");
                        } %>><%= m.getMarqueLibelle() %> <%= m.getLibelle() %></option>
                        <% } %>
                    </select>
                    <br><br>

                    <label for="ram">RAM (Go)</label>
                    <input type="number" id="ram" name="ram" value="<%
                    if (ordinateur != null) {
                    out.print(ordinateur.getRam());
                    } else {
                    out.print("");
                    }
                    %>" required>
                    <br><br>

                    <label for="processuer">Processeur</label>
                    <input type="text" id="processuer" name="processuer" value="<%
                    if (ordinateur != null) {
                    out.print(ordinateur.getProcesseur());
                    } else {
                    out.print("");
                    }
                    %>" required>
                    <br><br>

                    <label for="disqueDur">Disque dur (Go)</label>
                    <input type="number" id="disqueDur" name="disqueDur" value="<%
                    if (ordinateur != null) {
                    out.print(ordinateur.getDisqueDur());
                    } else {
                    out.print("");
                    }
                    %>" required>
                    <br><br>

                    <button type="submit">
                        <%
                            if (ordinateur != null) {
                                out.print("Modifier");
                            } else {
                                out.print("Enregistrer");
                            }
                            %></button>
                            </form>
                            
                            <a href="<%= request.getContextPath() %>/ordi">Liste</a>
                            </body>
                            </html>
                            