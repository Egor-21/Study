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

$sql = "SELECT * FROM users WHERE username='$username'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    if (password_verify($password, $row['password'])) {
        echo "Login successful";
    } else {
        echo "Incorrect username or password";
    }
} else {
    echo "User not found";
}

$conn->close();
?>