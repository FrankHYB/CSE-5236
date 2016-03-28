<?php

$response='';
if(empty($_POST['user'])||empty($_POST['data'])){
    $response = 'No user or data';
}else{

    $data = $_POST['address'];
    $username = $_POST['user'];
    $data_arr = json_decode($data);
    $connection= new MongoClient('mongodb://jjshao:jj5914@ds045531.mlab.com:45531/ybhe');
    $db=$connection->ybhe;
    $houses=$db->House;
    $data_arr['owner'] = $username;
    $user->insert($data_arr);
    $response = "House information set up successfully";
    $connection->close();
}
echo $response;

