<?php
    session_start();
    //Changing of cookies values
    setcookie('theme', 'dark',time() - 60*60*24, '/');
    setcookie('theme', 'light',time() + 60*60*24, '/');
    header('Location: ' . $_SERVER['HTTP_REFERER']);





