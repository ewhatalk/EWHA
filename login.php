<?php

    
    $con=mysql_connect("localhost","root","perrier2058");
    
    mysql_select_db("ewhatalk");
    
    mysql_query("set names utf8");
    
    $result=mysql_query("select * from member_table where user_id='$_POST[user_id]");
    $num=mysql_num_rows($result);
    
    if($num==0)
    
?>