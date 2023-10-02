<?php
    session_start();
    require_once 'assets/lib/connect.php';
    require 'assets/lib/changeTheme.php';
    //Checking user session, redirect
    if (!isset($_SESSION['user'])) {
        header('Location: login.php');
    }
    //CSRF protection
    $_SESSION['token'] = md5(uniqid(mt_rand(), true));
?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>"To-do" - Tasks</title>
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
				<h1><a href="todo.php">To-do</a></h1>
				<h2>Make your day structured</h2>
			</div>
		</div>
        <!--Navigation-->
		<nav class="menubar">
			<ul class="menu">
				<li class="selected"><a href="todo.php">My To-do</a></li>
				<li><a href="assets/lib/logout.php">Log out</a></li>
			</ul>
		</nav>
	</header>
    <!--Main content-->
	<div class="site_content">
		<div class="container_todo">
			<div class='name_of_user'><p><?= $_SESSION['user']['nickname'] ?>'s to-do list</p></div>
			<div class="todo_block">
				<div class="todo_block_container">
					<form action="assets/lib/addTask.php" id="form" method="post" class="sendForm">
                        <label for="task"></label>
                        <input name="task" id="task" type="text" class="form-control" placeholder="Write down your task here" required>
                        <input type="hidden" name="token" value="<?php echo $_SESSION['token'] ?? '' ?>">
                        <button type="submit" name="submit_tsk" class="sendTask"><img src="assets/img/plus.png" alt="+"></button>
					</form>
                    <!--Error-->
                    <?php
                        if (isset($_SESSION['message_task'])) {echo '<p class="message">' . $_SESSION['message_task'] . '</p>';}
                        unset($_SESSION['message_task']);
                        if (isset($_SESSION['message_token'])) {echo '<p class="message">' . $_SESSION['message_token'] . '</p>';}
                        unset($_SESSION['message_token']);
                    ?>
                    <form action='assets/lib/delete.php?id_task=<?= $task ?>'></form>
					<div class="tasks_container">
                        <?php
                            //Tasks
                            require_once 'assets/lib/paging.php';
                            echo '<ul>';
                            $row = mysqli_fetch_array($result1);

                            if ($row) {
                                while ($row = mysqli_fetch_array($result1)) {
                                    $task = $row['task'];
                                    $status = $row['status'];
                                    $updateStatus = $row['updateStatus'];
                                    $id_task = $row['id_task'];
                                    $token1 = $_SESSION['token'] ?? '' ;
                                    //Output of tasks
                                    if ($status == 1 and $updateStatus == 0) {
                                        echo "<div class='task_row'><input readonly class='task_plate' value='$task'><form class='a_del' method='post' action='assets/lib/delete.php?id_task=" . $id_task . "'><button class='del' type='submit'><img src='assets/img/del.png' alt='del'></button></form><form class='a_done' method='post' action='assets/lib/done.php?id_task=" . $id_task . "&status=".$status."'><button class='done'><img src='assets/img/done.png' alt='&#10003;'></button></form><form class='a_upd' method='post' action='assets/lib/update.php?id_task=".$id_task."&updateStatus=".$updateStatus."'><button class='upd'>&#9998;</button></form></div>";
                                    }
                                    elseif ($status == 0 and $updateStatus == 0) {
                                        echo "<div class='task_row'><input readonly class='task_plate_crossed' value='$task'><form class='a_del' method='post' action='assets/lib/delete.php?id_task=" . $id_task . "'><button class='del' type='submit'><img src='assets/img/del.png' alt='del'></button></form><form class='a_done' method='post' action='assets/lib/done.php?id_task=" . $id_task . "&status=".$status."'><button class='done'><img src='assets/img/done.png' alt='&#10003;'></button></form><form class='a_upd' method='post' action='assets/lib/update.php?id_task=".$id_task."&updateStatus=".$updateStatus."'><button class='upd'>&#9998;</button></form></div>";
                                    }
                                    elseif ($updateStatus == 1){
                                        echo "<form class='updForm' action='assets/lib/saveUpdate.php?id_task=".$id_task."' id='form' method='post'><label for='updateTask'></label><input class='updTsk' name='updateTask' id='updateTask' type='text' value='$task' required><input type='hidden' name='token' value='$token1'><button type='submit' name='save' class='save'>Save</button></form>";
                                    }
                                }
                            } else {
                                echo "";
                            }

                            echo '</ul>';
                            if (isset($_SESSION['paging_error'])) {echo '<p class="message">' . $_SESSION['paging_error'] . '</p>';}
                            unset($_SESSION['paging_error']);

                            $sql = "SELECT * FROM `tasks` WHERE `id_user` = '$id_user'";
                            $result = mysqli_query($con, $sql);
                            $total_records = mysqli_num_rows($result);
                            $total_pages = ceil($total_records/$limit);
                            //Paging
                            echo '<div class="paging">';
                            for ($i=1;$i<=$total_pages;$i++) {
                                echo "<a href='todo.php?page=".$i."'><p>".$i."</p></a>";
                            }
                            echo '</div>';
                            ?>
                    </div>
				</div>
			</div>
		</div>
	</div>
	<footer>
        <!--Signature-->
		<div class="created_by">
			<p>&copy; Created by <a href="https://instagram.com/belya4real?igshid=YmMyMTA2M2Y=">Daniil Beliaev</a></p>
		</div>
	</footer>
</body>
</html>