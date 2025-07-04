<?php
header("Content-Type: application/json");

$conn = new mysqli("localhost", "root", "", "healing_db");

$user_id = $_POST['user_id'] ?? '';
$old_password = $_POST['old_password'] ?? '';
$new_password = $_POST['new_password'] ?? '';

if (!$user_id || !$old_password || !$new_password) {
    echo json_encode(["success" => false, "message" => "Data tidak lengkap"]);
    exit();
}

$stmt = $conn->prepare("SELECT password FROM users WHERE id = ?");
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    if (!password_verify($old_password, $row['password'])) {
        echo json_encode(["success" => false, "message" => "Password lama salah"]);
        exit();
    }

    $hashedNewPassword = password_hash($new_password, PASSWORD_DEFAULT);
    $update = $conn->prepare("UPDATE users SET password = ? WHERE id = ?");
    $update->bind_param("si", $hashedNewPassword, $user_id);
    $update->execute();

    echo json_encode(["success" => true, "message" => "Password berhasil diganti"]);
} else {
    echo json_encode(["success" => false, "message" => "User tidak ditemukan"]);
}

$stmt->close();
$conn->close();
?>
