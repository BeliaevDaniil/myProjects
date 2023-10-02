<?php
    //CSRF protection procedure
    $token = filter_input(INPUT_POST, 'token', FILTER_SANITIZE_STRING);
    if (!$token || $token !== $_SESSION['token']) {
        $_SESSION['message_token'] = 'ERROR: CSRF ATTACK';
        header('Location: ../../todo.php');
        exit;
    }
