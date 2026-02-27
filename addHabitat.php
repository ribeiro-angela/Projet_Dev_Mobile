    <?php
    $db_host = "localhost";
    $db_uid = "root";
    $db_pass = "";
    $db_name = "powerhome_db";

    header('Content-Type: application/json');

    $db_con = mysqli_connect($db_host, $db_uid, $db_pass, $db_name);

    if (!$db_con) {
        die(json_encode(["status" => "error", "message" => "Échec de connexion"]));
    }

    // 1. Récupération directe des données depuis l'URL
    // Exemple : addHabitat.php?floor=1&area=85&user_id=1
    $floor   = $_GET['floor'] ?? null;
    $area    = $_GET['area'] ?? null;
    $user_id = $_GET['user_id'] ?? null;

    // Vérification que les données sont là
    if ($floor === null || $area === null || $user_id === null) {
        die(json_encode(["status" => "error", "message" => "Données manquantes (floor, area ou user_id)"]));
    }

    // 2. Nettoyage des données pour la sécurité
    $floor   = intval($floor);
    $area    = floatval($area);
    $user_id = intval($user_id);

    // 3. Insertion directe dans la base
    $sql = "INSERT INTO habitat (floor, area, user_id) VALUES ($floor, $area, $user_id)";

    if (mysqli_query($db_con, $sql)) {
        echo json_encode([
            "status" => "success",
            "added_habitat" => [
                "id" => mysqli_insert_id($db_con),
                "floor" => $floor,
                "area" => $area,
                "user_id" => $user_id
            ]
        ]);
    } else {
        echo json_encode(["status" => "error", "message" => mysqli_error($db_con)]);
    }

    mysqli_close($db_con);