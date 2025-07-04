<?php
header("Content-Type: application/json");

$host = "localhost";
$db_name = "healing_db";
$username = "root";
$password = "";
$conn = new mysqli($host, $username, $password, $db_name);

$user_id = $_POST['user_id'] ?? 0;

if ($user_id == 0) {
    echo json_encode(["success" => false, "message" => "User ID tidak valid"]);
    exit();
}

$stmt = $conn->prepare("
    SELECT 
    u.name, 
    u.email, 
    u.created_at, 
    COUNT(f.place_id) as total_favorites
    FROM 
    users u
    LEFT JOIN 
    favorites f ON u.id = f.user_id
    WHERE 
    u.id = ?
    GROUP BY
    u.id
    ");
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 1) {
    $data = $result->fetch_assoc();
    echo json_encode([
        "success" => true,
        "data" => $data
    ]);
} else {
    echo json_encode(["success" => false, "message" => "User tidak ditemukan"]);
}

$stmt->close();
$conn->close();
?>