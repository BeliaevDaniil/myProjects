<?php
    //Connection to DataBase
    require_once 'connect.php';
    require "validate.php";
    //Authorization procedure
    if (isset($_POST['login'])) {
        $email = htmlspecialchars($_POST['email'],ENT_QUOTES);
        $password = htmlspecialchars($_POST['password'],ENT_QUOTES);
        //Checking email
        $check_email = mysqli_query($con, "SELECT * FROM `users` WHERE `email` = '$email'");
        if (mysqli_num_rows($check_email) == 0) {
            $_SESSION['message_email_login'] = 'User with this e-mail does not exist';
        }
        //Checking password
        $check_user = mysqli_query($con, "SELECT * FROM `users` WHERE `email` = '$email'");
        $user = mysqli_fetch_assoc($check_user);
        $hash = $user['hash'] ?? "";
        if (!password_verify($password, $hash)) {
            $_SESSION['message_password_login'] = 'Password was written wrong';
        }
        //Checking user
        if (mysqli_num_rows($check_user) > 0) {
            if (password_verify($password, $hash)) {
                //Adding user to session procedure
                $_SESSION['user'] = [
                    'id' => $user['id'],
                    'nickname' => $user['nickname'],
                    'email' => $user['email']
                ];
            }
        }
    }









