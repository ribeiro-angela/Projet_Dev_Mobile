<?php
// 1. Connexion à la base de données
$host = "localhost";
$user = "root";
$pass = ""; // Vide par défaut sur XAMPP
$dbname = "powerhome_db"; // REMPLACEZ PAR LE NOM DE VOTRE BASE

$db_con = mysqli_connect($host, $user, $pass, $dbname);

if (!$db_con) {
    die(json_encode("Erreur de connexion : " . mysqli_connect_error()));
}

$email = $_GET['email'] ?? '';
$password = $_GET['password'] ?? '';

$sql = "SELECT token, expired_at FROM users WHERE email='$email' AND password='$password'";
$result = mysqli_query($db_con, $sql);
$row = mysqli_fetch_assoc($result);

if ($row == null) {
    // Si l'utilisateur n'existe pas ou mauvais mot de passe
    echo json_encode('incorrect email or password !');
} elseif ($row['token'] == null || strtotime($row['expired_at']) < time()) {

    // Si le token est vide ou expiré, on en génère un nouveau
    $token = md5(uniqid() . rand(10000, 99999));
    $expire = date('Y-m-d H:i:s', strtotime('+30 days'));

    // Mise à jour de l'utilisateur avec le nouveau token
    $sql_update = "UPDATE users SET token='$token', expired_at='$expire' WHERE email='$email'";
    mysqli_query($db_con, $sql_update);

    // Réponse JSON avec le nouveau token
    echo json_encode(array("token" => $token, "expired_at" => $expire));
} else {
    // Si le token est encore valide, on renvoie les infos actuelles
    echo json_encode($row);
}