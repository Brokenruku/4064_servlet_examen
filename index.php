<?php

$cheminJson = __DIR__ . '/ordinateur.json';
$ordinateurs = [];

if (file_exists($cheminJson)) {
    $contenu = file_get_contents($cheminJson);
    $ordinateurs = json_decode($contenu, true);
    if ($ordinateurs === null) {
        $ordinateurs = [];
    }
}
?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>ETU4064</title>
</head>
<body>

<h1>ETU4064</h1>

<table border="1" cellpadding="5">
    <tr>
        <th>ID</th>
        <th>Marque</th>
        <th>Modele</th>
        <th>RAM</th>
        <th>Processeur</th>
        <th>Disque dur</th>
    </tr>
    <?php foreach ($ordinateurs as $o): ?>
    <tr>
        <td><?= htmlspecialchars($o['id'] ?? '') ?></td>
        <td><?= htmlspecialchars($o['marque'] ?? '') ?></td>
        <td><?= htmlspecialchars($o['modele'] ?? '') ?></td>
        <td><?= htmlspecialchars($o['ram'] ?? '') ?></td>
        <td><?= htmlspecialchars($o['processeur'] ?? '') ?></td>
        <td><?= htmlspecialchars($o['disqueDur'] ?? '') ?></td>
    </tr>
    <?php endforeach; ?>
</table>

</body>
</html>
