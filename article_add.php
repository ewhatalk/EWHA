<?php
    
    $con=mysql_connect("localhost","root","perrier2058");
    
    if($con)
        echo "connect";
    else
        echo "fail";
    
    mysql_select_db("ewhatalk");
    mysql_query("set names utf8");
    
    $sql="insert into posting_table (user_id,class_id,title,content) values ('$_POST[user_id]', '$_POST[class_id]', '$_POST[title]', '$_POST[content]')";
    
    if(!mysql_query($sql,$con))
        echo "fail";
    
    else
        echo "1 record added";
    
    
    mysql_close($con);
?>