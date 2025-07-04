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

$sql = "SELECT * FROM locations ORDER BY id DESC";
$result = $conn->query($sql);

$locations = [];

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $locations[] = [
            "id" => $row["id"],
            "name" => $row["name"],
            "image_url" => $row["image_url"],
            "short_description" => $row["short_desc"],
            "full_description" => $row["full_desc"],
            "category" => $row["category"]
        ];
    }

    echo json_encode([
        "success" => true,
        "message" => "Data lokasi ditemukan",
        "data" => $locations
    ]);
} else {
    echo json_encode([
        "success" => true,
        "message" => "Tidak ada data lokasi",
        "data" => []
    ]);
}

$conn->close();
?>
