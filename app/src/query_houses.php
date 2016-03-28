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

}else if(empty($_POST['price']) && !empty($_POST['longtitudeMax']) && !empty($_POST['latitudeMax'])
    && !empty($_POST['longtitudeMin']) && !empty($_POST['latitudeMin'])){
    //output houses that sits in such lo and la
    $tuple = getCollection();
    $houses = $tuple["collection"];
    $lonRange = array('longtitude' => array('$gt' => (float) $_POST['longtitudeMin'], '$lt' => (float) $_POST['longtitudeMax']));
    $latRange = array('latitude' => array('$gt' => (float) $_POST['latitudeMin']), '$lt' => (float) $_POST['latitudeMax']);
    $cursor = $houses -> find(array('$and' => array($lonRange , $latRange)));

    $num = 0;
    while($cursor!=null && $cursor->hasNext()){
        $array[$num] = $cursor->next();
        $num = $num + 1;
    }
    $tuple["connection"] -> close();


}else if(!empty($_POST['price'])){
    //output houses that are between this price
    $tuple = getCollection();
    $houses = $tuple["collection"];
    $priceRange = array('price' => array('$lt' => intval($_POST['price'])));
    $cursor = $houses -> find($priceRange);
    $num = 0;
    while($cursor!=null && $cursor->hasNext()){
        $array[$num] = $cursor->next();
        $num = $num + 1;
    }
    $tuple["connection"] -> close();
}
echo json_encode($array);



?>