<?php
    session_start();
    require 'assets/lib/changeTheme.php';
    require 'assets/lib/signin.php';

    //Checking user session, redirect
    if (isset($_SESSION['user'])) {
        header('Location: todo.php');
    }
?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>"To-do" - Registration</title>
	<link rel="stylesheet" media="screen" href="assets/css/style.css ">
	<link rel="stylesheet" media="print" href="assets/css/style_print.css">
    <script src="assets/js/validate.js"></script>
    <?php
    if (isset($_COOKIE['theme'])) {
        if ($_COOKIE['theme'] == 'light') {
            echo '<link rel="stylesheet" title="theme" href="assets/css/theme-light.css">';
        }
        elseif ($_COOKIE['theme'] == 'dark') {
            echo '<link rel="stylesheet" title="theme" href="assets/css/theme-dark.css">';
        }
    }
    ?>
</head>
<body>
    <!--Changing themes-->
    <div class="theme">
        <form action="assets/lib/light_theme.php"><button class="btn_light"><img src="assets/img/sun.png" alt="light"></button></form>
        <form action="assets/lib/dark_theme.php"><button class="btn_dark"><img src="assets/img/moon.png" alt="dark"></button></form>
    </div>
    <!--Header-->
	<header>
		<div class="logo"> 
			<div class="logo_text">
				<h1><a href="index.php">To-do</a></h1>
				<h2>Make your day structured</h2>
			</div>
		</div>
        <!--Navigation-->
		<nav class="menubar">
			<ul class="menu">
				<li><a href="index.php">Main</a></li>
				<li><a href="todo.php">My To-do</a></li>
			</ul>
		</nav>
	</header>
    <!--Main content-->
	<div class="site_content">
		<div class="container">
            <!--Registration window-->
			<section class="reg_window">
				<h2 class="reg_title">Log in</h2>
                <!--Form-->
			    <form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']) ?>"  method="post" class="form" id="form">
			        <fieldset>
			            <div class="row">
			                <label for="email_login"><span>*</span>E-mail:</label>
			                <input type="email" placeholder="Enter your e-mail" value="<?= isset($email) ? $email : '' ?>" name="email" id="email_login" required pattern="[^\s]+[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}" title="The minimum nickname length is 3 characters, can contain only letters and digits">
                            <!--Error-->
                            <?php
                                if (isset($_SESSION['message_email_login'])) {echo '<p class="message">' . $_SESSION['message_email_login'] . '</p>';}
                                unset($_SESSION['message_email_login']);
							?>
                            <p class="error" id="error_email"></p>
			            </div>
			            <div class="row">
			            	<label for="password_login"><span>*</span>Password:</label>
			            	<input type="password" name="password" id="password_login" placeholder="Enter your password" required pattern="\w{6,}" title="The minimum password length is 6 characters, password must not contain spaces">
                            <!--Error-->
                            <?php
                                if (isset($_SESSION['message_password_login'])) {echo '<p class="message">' . $_SESSION['message_password_login'] . '</p>';}
                                unset($_SESSION['message_password_login']);
                            ?>
                            <p class="error" id="error_password"></p>
			            </div>
                        <div class="explain"><p>Fields with "<span>*</span>" are required</p></div>
                        <!--Error-->
                        <?php
                            if (isset($_SESSION['message_success'])) {echo '<p class="message_success">' . $_SESSION['message_success'] . '</p>';}
                            unset($_SESSION['message_success']);
                        ?>
                        <!--Log In button-->
			            <input class="btn_form" type="submit" name="login" value="Log in">
                        <p>Don`t have an account? - <a class="btn_login" href="registration.php">Register!</a></p>
			        </fieldset>
			    </form>
                <!--Connection of validation-->
			    <script>init();</script>
			</section>
		</div>
	</div>
    <!--Footer-->
	<footer>
        <!--Signature-->
		<div class="created_by">
			<p>&copy; Created by <a href="https://instagram.com/belya4real?igshid=YmMyMTA2M2Y=">Daniil Beliaev</a></p>
		</div>
	</footer>
    <script src="assets/js/dynValidateLogin.js"></script>
</body>
</html>