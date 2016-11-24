<?php

    $con=mysql_connect("localhost","root","perrier2058");
    
    mysql_select_db("ewhatalk");
    
    mysql_query("set names utf8");
    
    $result=mysql_query("select * from member_table",$con);
    
    $total=mysql_num_rows($result);
    
    echo "{\"results\":[";
    
    for($i=0;$i<$total;$i++)
    {
        mysql_data_seek($result,$i);
        
        $row=mysql_fetch_array($result);
        
        echo "{\"user_id\":\"$row[user_id]\",\"user_password\":\"$row[user_password]\",\"user_email\":\"$row[user_email]\"}";
        
        if($i<$total-1)
            echo ",";
    }
    
    echo "]}";
    
    mysql_close($con);
?>