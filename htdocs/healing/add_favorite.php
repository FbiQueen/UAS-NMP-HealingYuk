<?php
header("Content-Type: application/json");

$host = "localhost";
$db_name = "healing_db";
$username = "root";
$password = "";

$conn = new mysqli($host, $username, $password, $db_name);
if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Koneksi database gagal"]);
    exit();
}

$user_id = $_POST['user_id'] ?? '';
$location_id = $_POST['location_id'] ?? ''; 

if (!$user_id || !$location_id) { 
    echo json_encode(["success" => false, "message" => "Parameter tidak lengkap"]);
    exit();
}

$check_sql = "SELECT * FROM favorites WHERE user_id = ? AND location_id = ?"; 
$stmt = $conn->prepare($check_sql);
$stmt->bind_param("ii", $user_id, $location_id); 
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    echo json_encode(["success" => false, "message" => "Sudah ada di favorit"]);
} else {
    $insert_sql = "INSERT INTO favorites (user_id, location_id) VALUES (?, ?)"; 
    $stmt = $conn->prepare($insert_sql);
    $stmt->bind_param("ii", $user_id, $location_id); 
    if ($stmt->execute()) {
        echo json_encode(["success" => true, "message" => "Berhasil ditambahkan ke favorit"]);
    } else {
        echo json_encode(["success" => false, "message" => "Gagal menambahkan ke favorit"]);
    }
}

$stmt->close();
$conn->close();
?>