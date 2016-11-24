<?php
    
    $con=mysql_connect("localhost","root","perrier2058");
    
    if($con)
        echo "connect";
    else
        echo "fail";
    
    mysql_select_db("ewhatalk");
    mysql_query("set names utf8");
    
    $sql="delete from posting_table where user_id=$_POST[user_id], title=$_POST[title]";
    
    if(!mysql_query($sql,$con))
        echo "fail";
    
    else
        echo "1 record deleted";
    
    mysql_close($con);
?>