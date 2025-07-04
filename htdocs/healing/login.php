<?php
header("Content-Type: application/json");

$host = "localhost";
$db_name = "healing_db";
$username = "root";
$password = "";

$conn = new mysqli($host, $username, $password, $db_name);

if ($conn->connect_error) {
    echo json_encode([
        "success" => false,
        "message" => "Koneksi ke database gagal: " . $conn->connect_error
    ]);
    exit();
}

$email = $_POST['email'] ?? '';
$password_input = $_POST['password'] ?? '';

if (empty($email) || empty($password_input)) {
    echo json_encode([
        "success" => false,
        "message" => "Email dan password wajib diisi"
    ]);
    exit();
}

$stmt = $conn->prepare("SELECT * FROM users WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 1) {
    $row = $result->fetch_assoc();

    if (password_verify($password_input, $row['password'])) {
        echo json_encode([
            "success" => true,
            "message" => "Login berhasil",
            "data" => [
                "id" => $row['id'],
                "name" => $row['name'],
                "email" => $row['email'],
                "created_at" => $row['created_at']
            ]
        ]);
    } else {
        echo json_encode([
            "success" => false,
            "message" => "Password salah"
        ]);
    }
} else {
    echo json_encode([
        "success" => false,
        "message" => "Email tidak ditemukan"
    ]);
}

$stmt->close();
$conn->close();
?>
