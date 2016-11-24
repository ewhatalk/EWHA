<?php
    
    $con=mysql_connect("localhost","root","perrier2058");
    
    if($con)
        echo "connect";
    else
        echo "fail";
    
    mysql_select_db("ewhatalk");
    
    mysql_query("set names utf8");
    
    $sql="insert into member_table values ('$_POST[user_id]', '$_POST[user_password]', '$_POST[user_email]')";
    
    if(!mysql_query($sql,$con))
        echo "fail";
    
    else
        echo "1 record added";
    
    mysql_close($con);
?>