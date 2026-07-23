<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>ETU4064</title>
</head>
<body>

<h1>ETU4064</h1>
<h2>Connexion</h2>

<form action="<%= request.getContextPath() %>/verLogin" method="post">
    <p>Login : <input name="login" type="text"></p>
    <p>Mot de passe : <input name="pwd" type="password"></p>
    <input type="submit" value="Se connecter">
</form>

</body>
</html>
