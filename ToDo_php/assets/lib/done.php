<?php
    //Connection to DataBase
    require_once 'connect.php';

    $id_task = $_GET['id_task'];
    $status = $_GET['status'];
    //Changing of task status
    if ($status == 1){
        $sql = "UPDATE `tasks` SET `status` = '0' WHERE `id_task` = ?";
    }
    elseif ($status == 0) {
        $sql = "UPDATE `tasks` SET `status` = '1' WHERE `id_task` = ?";
    }

    $query = $pdo->prepare($sql);
    $query->execute([$id_task]);
    header('Location: ' . $_SERVER['HTTP_REFERER']);