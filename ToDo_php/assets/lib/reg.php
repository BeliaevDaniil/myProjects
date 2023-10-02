<?php
    //Connection to DataBase
    require_once 'connect.php';
    //Registration of user procedure
    if (isset($_POST['reg'])) {
        //Sanitizing of input values
        $nickname = htmlspecialchars($_POST['nickname'], ENT_QUOTES);
        $email = htmlspecialchars($_POST['email'], ENT_QUOTES);
        $password = htmlspecialchars($_POST['password'], ENT_QUOTES);
        $password2 = htmlspecialchars($_POST['password2'],ENT_QUOTES);
        $tel = htmlspecialchars($_POST['tel'],ENT_QUOTES);
        //Validation of input values
        $validateNickname = validateNickname($nickname);
        $validateEmail = validateEmail($email);
        $validatePassword = validatePassword($password);
        $validatePassword2 = validatePassword2($password, $password2);
        //Checking if user is already registered
        $check_acc = mysqli_query($con, "SELECT * FROM `users` WHERE `email` = '$email'");
        if (mysqli_num_rows($check_acc) == 0) {
            //Errors
            if (isset($validateNickname) && $validateNickname == false) {
                $_SESSION['message_nickname'] = 'Nickname is too short';
            }
            if (isset($validateEmail) && $validateEmail == false) {
                $_SESSION['message_email'] = 'E-mail was written wrong';
            }
            if (isset($validatePassword) && $validatePassword == false) {
                $_SESSION['message_password'] = 'Password is too short';
            }
            if (isset($validatePassword2) && $validatePassword2 == false) {
                $_SESSION['message_password2'] = 'Passwords do not match';
            }
            //Registration of user procedure
            if ($validateNickname && $validateEmail && $validatePassword && $validatePassword2) {
                $password_hashed = password_hash($password, PASSWORD_DEFAULT);
                $insert = "INSERT INTO `users` (`nickname`, `email`, `hash`) VALUES ('$nickname', '$email', '$password_hashed')";
                mysqli_query($con, $insert);
                $_SESSION['message_success'] = 'Registration was successful!';
                header("Location: login.php");
            }
        } else {
            //Errors
            $_SESSION['message_email'] = 'User with this e-mail already exists';
            $_SESSION['message'] = 'Registration has not been completed';
        }
    }









