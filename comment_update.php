<?php
    
    $con=mysql_connect("localhost","root","perrier2058");
    
    if($con)
        echo "connect";
    else
        echo "fail";
    
    mysql_select_db("ewhatalk");
    mysql_query("set names utf8");
    
    $sql="update posting_table set boolean=1 where post_id=$_POST[post_id]";
    
    if(!mysql_query($sql,$con))
        echo "fail";
    
    else
        echo "1 record added";
    
    
    mysql_close($con);
?>