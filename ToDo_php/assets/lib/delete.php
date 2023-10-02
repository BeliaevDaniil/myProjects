<?php
    //Connection to DataBase
    require_once 'connect.php';
    //Task deleting procedure
    $id = $_GET['id_task'];
    $sql = 'DELETE FROM `tasks` WHERE `id_task` = ?';
    $query = $pdo->prepare($sql);
    $query->execute([$id]);
    header('Location: ' . $_SERVER['HTTP_REFERER']);


