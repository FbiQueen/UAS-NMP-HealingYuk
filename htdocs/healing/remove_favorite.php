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

if (!$user_id || !$location_id) { // DIGANTI
    echo json_encode(["success" => false, "message" => "Parameter tidak lengkap"]);
    exit();
}

$delete_sql = "DELETE FROM favorites WHERE user_id = ? AND location_id = ?"; 
$stmt = $conn->prepare($delete_sql);
$stmt->bind_param("ii", $user_id, $location_id); // DIGANTI

if ($stmt->execute()) {
    if ($stmt->affected_rows > 0) {
        echo json_encode(["success" => true, "message" => "Berhasil dihapus dari favorit"]);
    } else {
        echo json_encode(["success" => false, "message" => "Lokasi tidak ditemukan di favorit Anda"]);
    }
} else {
    echo json_encode(["success" => false, "message" => "Gagal menghapus dari favorit"]);
}

$stmt->close();
$conn->close();
?>