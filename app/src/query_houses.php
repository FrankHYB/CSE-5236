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
    $lonRange = array('longtitude' => array('$gt' => (double) $_POST['longtitudeMin'], '$lt' => (double) $_POST['longtitudeMax']));
    $latRange = array('latitude' => array('gt' => (double) $_POST['latitudeMin']), '$lt' => (double) $_POST['latitudeMax']);
    $cursor = $houses -> find(array('$and' => array($lonRange , $latRange)));

    $num = 0;
    while($cursor!=null && $cursor->hasNext()){
        $array[$num] = $cursor->next();
        $num = $num + 1;
    }
    $tuple["connection"] -> close();


}else if(!empty($_POST['price'])){
    //output houses that are between this price

}
echo json_encode($array);



?>