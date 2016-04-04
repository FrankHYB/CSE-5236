<?php
/**
 * Created by PhpStorm.
 * User: yubin
 * Date: 4/4/16
 * Time: 1:20 AM
 */
$result = '';
if(!empty($_POST['address'])) {
    $connection = new MongoClient('mongodb://jjshao:jj5914@ds045531.mlab.com:45531/ybhe');
    $db = $connection->ybhe;
    $houses = $db->House;
    $address = $_POST['address'];
    $result = $houses -> findOne(array("address" => $address), array("image" => true));
    if($result==null){
        $result = "DB Error";
    }
    $connection->close();
}
echo $result;



?>