<?php
    session_start();
    //Connection to DataBase
    require_once 'connect.php';
    require 'csrf.php';
    //Adding task procedure
    $task =  htmlspecialchars($_POST['task'],ENT_QUOTES);
    $id_user = $_SESSION['user']['id'];
    //Checking if task input was not empty
    if ($task == '') {
        $_SESSION['message_task'] = 'Enter your task';
        header('Location: ' . $_SERVER['HTTP_REFERER']);
        exit;
    }
    $insert = "INSERT INTO `tasks` (`id_task`, `id_user`, `task`) VALUES (NULL, '$id_user', '$task')";
    mysqli_query($con, $insert);
    header('Location: ' . $_SERVER['HTTP_REFERER']);

