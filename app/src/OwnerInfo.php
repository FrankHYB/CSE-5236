<?php
/**
 * Created by PhpStorm.
 * User: yubin
 * Date: 4/10/16
 * Time: 3:27 PM
 */
$response = array();
if(!empty($_POST['owner'])){
    $owner = $_POST['owner'];
    $connection = new MongoClient('mongodb://jjshao:jj5914@ds045531.mlab.com:45531/ybhe');
    $db = $connection->ybhe;
    $users = $db->androidUser;
    $query = $users->findOne(array('username'=>$owner),array('email'=>true,'phoneNum'=>true));
    $response['email'] = $query['email'];
    $response['phoneNum'] = $query['phoneNum'];
    $connection->close();
}
echo json_encode($response);
?>