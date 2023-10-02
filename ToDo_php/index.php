<?php
    session_start();
    require 'assets/lib/changeTheme.php';
?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" media="screen" href="assets/css/style.css ">
	<link rel="stylesheet" media="print" href="assets/css/style_print.css">
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
	<title>"To-do" - Main page</title>
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
				<h1><a>To-do</a></h1>
				<h2>Make your day structured</h2>
			</div>
		</div>
        <!--Navigation-->
		<nav class="menubar">
			<ul class="menu">
				<li class="selected"><a href="index.php">Main</a></li>
				<li><a href="todo.php">My To-do</a></li>
			</ul>
		</nav>
	</header>
    <!--Main content-->
	<div class="site_content">
		<div class="container">
            <!--Welcome text-->
			<article class="hi_block">
				<h3>About "To-do"</h3>
				<p>
					Hi! We are the new way of planning your day. Due to our service you can write down your tasks, edit, distribute by importance, share or delete them.
					Make your day easier with us.<br>
					”To-do” - That's what you gonna do!
				</p>				
			</article>
            <!--Create an account button-->
            <form action="registration.php" class="form_create"><button class="btn_create" type="submit">Create an account</button></form>
		</div>
	</div>
    <!--Footer-->
	<footer>
        <!--Signature-->
		<div class="created_by">
			<p>&copy; Created by <a href="https://instagram.com/belya4real?igshid=YmMyMTA2M2Y=">Daniil Beliaev</a></p>
		</div>
	</footer>
</body>
</html>