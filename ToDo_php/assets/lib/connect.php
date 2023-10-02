<?php
    //Connection to DataBase
    $con = mysqli_connect('localhost','beliadan','webove aplikace', 'beliadan');
    $dsn = 'mysql:host=localhost;dbname=beliadan';
    $pdo = new PDO($dsn, 'beliadan', 'webove aplikace');
    //Error
    if (!$con) {
        die('Error: Connection to database went wrong');
    }






