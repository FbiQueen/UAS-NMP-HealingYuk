<?php
header("Content-Type: application/json");
$conn = new mysqli("localhost", "root", "", "healing_db");

$user_id = $_POST['user_id'] ?? '';
$location_id = $_POST['location_id'] ?? '';

if (!$user_id || !$location_id) {
    echo json_encode(["success" => false, "message" => "Data tidak lengkap"]);
    exit();
}

$stmt = $conn->prepare("SELECT * FROM favorites WHERE user_id = ? AND location_id = ?");
$stmt->bind_param("ii", $user_id, $location_id);
$stmt->execute();
$result = $stmt->get_result();

echo json_encode([
    "success" => true,
    "is_favorite" => $result->num_rows > 0
]);
