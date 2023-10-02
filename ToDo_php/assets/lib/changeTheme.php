<?php
    //Checking if cookie was set
    if (!isset($_COOKIE['theme'])) {
        setcookie('theme', 'light',time() + 60*60*24, '/');
    }




