<?php
    
    $con=mysql_connect("localhost","root","perrier2058");
    
    mysql_select_db("ewhatalk");
    
    mysql_query("set names utf8");
    
    $result=mysql_query("select * from posting_table2",$con);
    
    $total=mysql_num_rows($result);
    
    echo "{\"results\":[";
    
    for($i=0;$i<$total;$i++)
    {
        mysql_data_seek($result,$i);
        
        $row=mysql_fetch_array($result);
        
        echo "{\"post_id\":$row[post_id],\"user_id\":\"$row[user_id]\",\"class_id\":$row[class_id],\"title\":\"$row[title]\",\"content\":\"$row[content]\",\"date\":\"$row[date]\"}";
        
        if($i<$total-1)
            echo ",";
    }
    
    echo "]}";
    
    mysql_close($con);
?>