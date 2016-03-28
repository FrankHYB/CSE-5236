<?php

$response='';
if(empty($_POST['user'])||empty($_POST['name'])||empty($_POST['address'])
    ||empty($_POST['price'])||empty($_POST['description'])||empty($_POST['zipcode'])){
    $response = 'No user or data';
}else{
    $name = $_POST['data'];
    $address = $_POST['addresss'];
    $price = $_POST['price'];
    $description = $_POST['description'];
    $zipcode = $_POST['zipcode'];
    $username = $_POST['user'];

    $connection= new MongoClient('mongodb://jjshao:jj5914@ds045531.mlab.com:45531/ybhe');
    $db=$connection->ybhe;
    $houses=$db->House;
    $houses->insert(
        array(
            "name" => $name,
            "address" => $address,
            "price" => $price,
            "description" => $description,
            "zipcode" => $zipcode,
            "owner" => $username
        )
    );
    $response = "House information set up successfully";
    $connection->close();
}
echo $response;
?>

