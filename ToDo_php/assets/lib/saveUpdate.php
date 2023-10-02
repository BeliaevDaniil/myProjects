<?php
    session_start();
    //Connection to DataBase
    require_once 'connect.php';
    require 'csrf.php';

    $id_task = $_GET['id_task'];
    $newTask = htmlspecialchars($_POST['updateTask']);
    //Checking if input was not empty
    if ($newTask == '') {
        $_SESSION['message_task'] = "Enter your task";
        header('Location: ' . $_SERVER['HTTP_REFERER']);
        exit();
    }
    //Saving update of task procedure
    $sql = "UPDATE `tasks` SET `task` = '$newTask' WHERE `id_task` = '$id_task'";
    mysqli_query($con, $sql);

    $sql2 = "UPDATE `tasks` SET `updateStatus` = '0' WHERE `id_task` = '$id_task'";
    mysqli_query($con, $sql2);

    header('Location: ' . $_SERVER['HTTP_REFERER']);






