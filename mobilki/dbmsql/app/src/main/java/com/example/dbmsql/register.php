<?php
$servername = "localhost";
$username = "root";
$password = "1029384756";
$dbname = "loginbase";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$username = $_POST['username'];
$password = $_POST['password'];

$hashed_password = password_hash($password, PASSWORD_DEFAULT);

$stmt = $conn->prepare("INSERT INTO users (username, password) VALUES (?, ?)");
$stmt->bind_param("ss", $username, $hashed_password);

if ($stmt->execute() === TRUE) {
    echo "Registration successful";
} else {
    echo "Error: " . $stmt->error;
}

$stmt->close();
$conn->close();
?>