<?php
    session_start();
    //Log out procedure
    unset($_SESSION['user']);
    header('Location: ../../login.php');
