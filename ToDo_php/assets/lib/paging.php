<?php
    //Connection to DataBase
    require_once 'connect.php';
    //Paging procedure
    $page = $_GET['page'] ?? 1;

    $limit = 5;
    $id_user = $_SESSION['user']['id'];

    $sql_new = "SELECT * FROM `tasks` WHERE `id_user` = '$id_user'";
    $result_new = mysqli_query($con, $sql_new);
    $total_records_new = mysqli_num_rows($result_new);
    $total_pages_new = ceil($total_records_new/$limit);
    if (!is_numeric($page) || ($page > $total_pages_new))  {
        $page = $total_pages_new;
        $_SESSION["paging_error"] = "Such page does not exist or was entered invalid format";
    }

    $start_from = ($page-1) * 5;
    $sql1 = "SELECT * FROM `tasks` WHERE `id_user` = '$id_user' ORDER BY `id_task` AND `status` DESC LIMIT $start_from, $limit";
    $result1 =mysqli_query($con, $sql1);


