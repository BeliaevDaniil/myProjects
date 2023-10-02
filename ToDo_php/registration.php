<?php
    session_start();
    require "assets/lib/validate.php";
    require "assets/lib/reg.php";
    require 'assets/lib/changeTheme.php';
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
			<section class="reg_window">
				<h2 class="reg_title">Registration</h2>
			    <form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']) ?>"  method="POST" class="form" id="form">
			        <fieldset>
			            <div class="row">
			                <label for="nickname"><span>*</span>Nickname:</label>
			                <input type="text" id="nickname" placeholder="Enter your nickname"  value="<?= isset($nickname) ? $nickname : '' ?>" name="nickname" required pattern="[^\s]+[A-Za-zА-яЁё0-9]{2,}" title="The minimum nickname length is 3 characters, can contain only letters and digits">
                            <!--Error-->
                            <?php
                                if (isset($_SESSION['message_nickname'])) {echo '<p class="message">' . $_SESSION['message_nickname'] . '</p>';}
                                unset($_SESSION['message_nickname']);
							?>
                            <p class="error" id="error_nickname"></p>
			            </div>
			            <div class="row">
			                <label for="email"><span>*</span>E-mail:</label>
			                <input type="email" id="email" placeholder="Enter your e-mail" value="<?= isset($email) ? $email : '' ?>" name="email"  required pattern="[^\s]+[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}" title="E-mail should contain '@', must not contain spaces">
                            <!--Error-->
                            <?php
                                if (isset($_SESSION['message_email'])) {echo '<p class="message">' . $_SESSION['message_email'] . '</p>';}
                                unset($_SESSION['message_email']);
							?>
                            <p class="error" id="error_email"></p>
			            </div>
			            <div class="row">
			            	<label for="password"><span>*</span>Password:</label>
			            	<input type="password" name="password" id="password" placeholder="Enter your password"  required pattern="\w{6,}" title="The minimum password length is 6 characters, must not contain spaces">
                            <!--Error-->
                            <?php
                                if (isset($_SESSION['message_password'])) {echo '<p class="message">' . $_SESSION['message_password'] . '</p>';}
                                unset($_SESSION['message_password']);
                            ?>
                            <p class="error" id="error_password"></p>
			            </div>
			            <div class="row">
			            	<label for="password2"><span>*</span>Repeat password:</label>
			            	<input type="password" name="password2" id="password2" placeholder="Repeat password" required pattern="\w{6,}" title="The minimum password length is 6 characters, must not contain spaces">
                            <!--Error-->
                            <?php
                                if (isset($_SESSION['message_password2'])) {echo '<p class="message">' . $_SESSION['message_password2'] . '</p>';}
                                unset($_SESSION['message_password2']);
                            ?>
                            <p class="error" id="error_password2"></p>
			            </div>
                        <div class="row">
                            <label for="tel">Phone number:</label>
                            <input type="tel" name="tel" id="tel" placeholder="XXXXXXXXX" pattern="[0-9]{3}[0-9]{3}[0-9]{3}" title="Czech format of mobile numbers contains 9 digits">
                        </div>
                        <!--Error-->
                        <?php
                            if (isset($_SESSION['message'])) {echo '<p class="message">' . $_SESSION['message'] . '</p>';}
                            unset($_SESSION['message']);
                        ?>
                        <div class="explain"><p>Fields with "<span>*</span>" are required</p></div>
			            <input class="btn_form" type="submit" name="reg" value="Register">
                        <p>Already have an account? - <a class="btn_login" href="login.php">Log in!</a></p>
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
    <script src="assets/js/dynValidate.js"></script>
</body>
</html>