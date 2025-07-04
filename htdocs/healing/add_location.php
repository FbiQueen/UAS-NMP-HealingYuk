<?php
header("Content-Type: application/json");

$host = "localhost";
$db_name = "healing_db";
$username = "root";
$password = "";

$conn = new mysqli($host, $username, $password, $db_name);
if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Koneksi ke database gagal"]);
    exit();
}

$name = $_POST['name'] ?? '';
$image_url = $_POST['image_url'] ?? '';
$short_desc = $_POST['short_desc'] ?? '';
$full_desc = $_POST['full_desc'] ?? '';
$category = $_POST['category'] ?? '';

if (empty($name) || empty($image_url) || empty($short_desc) || empty($full_desc) || empty($category)) {
    echo json_encode(["success" => false, "message" => "Semua kolom wajib diisi"]);
    exit();
}

$stmt = $conn->prepare("INSERT INTO locations (name, image_url, short_desc, full_desc, category) VALUES (?, ?, ?, ?, ?)");
$stmt->bind_param("sssss", $name, $image_url, $short_desc, $full_desc, $category);

if ($stmt->execute()) {
    echo json_encode(["success" => true, "message" => "Data lokasi berhasil ditambahkan"]);
} else {
    echo json_encode(["success" => false, "message" => "Gagal menambahkan lokasi"]);
}

$stmt->close();
$conn->close();
?>
