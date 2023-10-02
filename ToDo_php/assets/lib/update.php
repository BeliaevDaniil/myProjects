<?php
    //Connection to DataBase
    require_once 'connect.php';
    //Updating of task procedure
    $id_task = $_GET['id_task'];
    $updateStatus = $_GET['updateStatus'];
    $sql = "UPDATE `tasks` SET `updateStatus` = '1' WHERE `id_task` = ?";
    $query = $pdo->prepare($sql);
    $query->execute([$id_task]);
    header('Location: ' . $_SERVER['HTTP_REFERER']);
