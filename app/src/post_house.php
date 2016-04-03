<?php
$response='';
if(empty($_POST['user'])||empty($_POST['name'])||empty($_POST['address'])
    ||empty($_POST['price'])||empty($_POST['description'])||empty($_POST['zipcode'])){
    $response = 'No user or data';
}else{
    $name = $_POST['name'];
    $address = $_POST['address'];
    $price = $_POST['price'];
    $price = intval($price);
    $description = $_POST['description'];
    $zipcode = $_POST['zipcode'];
    $longtitude = $_POST['longtitude'];
    $longtitude = (double) $longtitude;
    $latitude = $_POST['latitude'];
    $latitude = (double) $latitude;
    $username = $_POST['user'];
    $image = $_POST['image'];

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
            "longtitude" => $longtitude,
            "latitude" => $latitude,
            "owner" => $username,
            "image" => $image
        )
    );
    $response = "House information set up successfully";
    $connection->close();
}
echo $response;
?>

