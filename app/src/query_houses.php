<?php
/**
 * Created by PhpStorm.
 * User: yubin
 * Date: 3/28/16
 * Time: 12:50 PM
 */
function getCollection(){
    $connection = new MongoClient('mongodb://jjshao:jj5914@ds045531.mlab.com:45531/ybhe');
    $db=$connection->ybhe;
    $houses=$db->House;
    $tuple = array("collection" => $houses, "connection" => $connection);
    return $tuple;
}

$array = array();
if(empty($_POST['price']) && empty($_POST['longtitude']) && empty($_POST['latitude'])){
    //output all results
    $tuple = getCollection();
    $houses = $tuple["collection"];
    $cursor = $houses -> find();
    $num = 0;
    while($cursor!=null && $cursor->hasNext()){
        $array[$num] = $cursor->next();
        $num = $num + 1;
    }
    $tuple["connection"] -> close();
}else if(empty($_POST['price']) && !empty($_POST['longtitude']) && !empty($_POST['latitude'])){
    //output houses that sits in such lo and la
    //$houses = getCollection();

}else if(!empty($_POST['price'])){
    //output houses that are between this price

}
echo json_encode($array);



?>