

<?php
$db_host = "localhost";
$db_uid = "root";
$db_pass = "";
$db_name = "powerhome_db";

$db_con = mysqli_connect($db_host, $db_uid, $db_pass, $db_name);

header('Content-Type: application/json');

// 1. Récupération du token
$token = $_GET['token'] ?? '';

// 2. On cherche l'utilisateur qui possède ce token
$sql_user = "SELECT id, expired_at FROM users WHERE token='$token'";
$result_user = mysqli_query($db_con, $sql_user);
$user = mysqli_fetch_assoc($result_user);

// 3. Vérification de validité
if (!$user || (strtotime($user['expired_at']) < time())) {
    http_response_code(401);
    echo json_encode("Token invalid or is already expired!");
} else {
    $user_id = $user['id'];

    // 4. IMPORTANT : On filtre par l'ID de l'utilisateur trouvé via le token
    $sql_habitats = "SELECT id, floor, area, user_id FROM habitat WHERE user_id = $user_id";
    $res_habitats = mysqli_query($db_con, $sql_habitats);

    $output = array();
    while($row = mysqli_fetch_assoc($res_habitats)){
        $row['id'] = (int)$row['id'];
        $row['floor'] = (int)$row['floor'];
        $row['area'] = (float)$row['area'];
        $row['user_id'] = (int)$row['user_id'];
        $output[] = $row;
    }

    http_response_code(200);
    echo json_encode($output);
}

mysqli_close($db_con);