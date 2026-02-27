<?php
$db_host = "localhost";
$db_uid = "root";
$db_pass = "";
$db_name = "powerhome_db";


$db_con = mysqli_connect($db_host, $db_uid, $db_pass, $db_name);

header('Content-Type: application/json');


$sql = "SELECT * FROM habitat";
$result = mysqli_query($db_con, $sql);

$output = array();

if ($result) {
    while($row = mysqli_fetch_assoc($result)) {

        $row['id'] = (int)$row['id'];
        $row['floor'] = (int)$row['floor'];
        $row['area'] = (float)$row['area'];
        $output[] = $row;
    }
}

mysqli_close($db_con);

// 3. Envoi de toute la liste
echo json_encode($output);