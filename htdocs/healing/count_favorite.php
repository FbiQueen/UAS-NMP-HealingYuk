<?php
header("Content-Type: application/json");

$conn = new mysqli("localhost", "root", "", "healing_db");

$user_id = $_POST['user_id'] ?? 0;

if (!$user_id) {
    echo json_encode(["success" => false, "message" => "User ID tidak valid"]);
    exit();
}

$stmt = $conn->prepare("SELECT COUNT(*) AS count FROM favorites WHERE user_id = ?");
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result()->fetch_assoc();

echo json_encode([
    "success" => true,
    "count" => $result['count']
]);
