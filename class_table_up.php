<?php
    
    $con=mysql_connect("localhost","root","perrier2058");
    
    mysql_select_db("ewhatalk");
    
    mysql_query("set names utf8");
    
    $result=mysql_query("select * from class_table",$con);
    
    $total=mysql_num_rows($result);
    
    echo "{\"results\":[";
    
    for($i=0;$i<$total;$i++)
    {
        mysql_data_seek($result,$i);
        
        $row=mysql_fetch_array($result);
        
        echo "{\"class_id\":$row[class_id],\"type\":\"$row[type]\",\"college\":\"$row[college]\",\"major\":\"$row[major]\",\"grade\":$row[grade],\"subj_name\":\"$row[subj_name]\",\"class_num\":$row[class_num],\"prof\":\"$row[prof]\"}";
        
        if($i<$total-1)
            echo ",";
    }
    
    echo "]}";
    
    mysql_close($con);
?>