<?php
    //Nickname validation
    function validateNickname($nickname): bool
    {
        return strlen($nickname) >= 3;
    } 
    //E-mail validation
    function validateEmail($email): bool
    {
        return (bool)strpos($email, '@');
    } 
    //Passwords validation
    function validatePassword($password): bool
    {
        return strlen($password) >= 6;
    }
    function validatePassword2($password, $password2): bool
    {
        if ($password2 !== $password) {
            return false;
        }
        return true;
    }



