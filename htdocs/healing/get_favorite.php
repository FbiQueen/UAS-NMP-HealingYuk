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

$user_id = $_POST['user_id'] ?? 0;

if ($user_id == 0) {
    echo json_encode(["success" => false, "message" => "User ID tidak valid"]);
    exit();
}

$stmt = $conn->prepare("
    SELECT l.* FROM locations l
    JOIN favorites f ON l.id = f.location_id
    WHERE f.user_id = ?
    ");
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

$favorites = [];
while ($row = $result->fetch_assoc()) {
    $favorites[] = $row;
}

echo json_encode([
    "success" => true,
    "data" => $favorites
]);

$stmt->close();
$conn->close();
?>
