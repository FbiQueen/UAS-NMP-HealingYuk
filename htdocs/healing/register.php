<?php
header("Content-Type: application/json");

$host = "localhost";
$db_name = "healing_db";
$username = "root";
$password = "";

$conn = new mysqli($host, $username, $password, $db_name);
if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Koneksi gagal"]);
    exit();
}

$name = $_POST['name'] ?? '';
$email = $_POST['email'] ?? '';
$password_input = $_POST['password'] ?? '';
$repeat_password = $_POST['repeat_password'] ?? '';

if (empty($name) || empty($email) || empty($password_input) || empty($repeat_password)) {
    echo json_encode(["success" => false, "message" => "Semua field wajib diisi"]);
    exit();
}

if ($password_input !== $repeat_password) {
    echo json_encode(["success" => false, "message" => "Password tidak sama"]);
    exit();
}

$check = $conn->prepare("SELECT * FROM users WHERE email = ?");
$check->bind_param("s", $email);
$check->execute();
$result = $check->get_result();
if ($result->num_rows > 0) {
    echo json_encode(["success" => false, "message" => "Email sudah terdaftar"]);
    exit();
}

$hashedPassword = password_hash($password_input, PASSWORD_DEFAULT);
$stmt = $conn->prepare("INSERT INTO users (name, email, password) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $name, $email, $hashedPassword);

$success = $stmt->execute();

if ($success) {
    echo json_encode(["success" => true, "message" => "Registrasi berhasil"]);
} else {
    echo json_encode(["success" => false, "message" => "Gagal mendaftar"]);
}

$stmt->close();
$conn->close();
?>
