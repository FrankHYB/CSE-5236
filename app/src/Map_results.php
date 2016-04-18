<?php
/**
 * Created by PhpStorm.
 * User: yubin
 * Date: 4/4/16
 * Time: 12:14 AM
 */
$connection= new MongoClient('mongodb://jjshao:jj5914@ds045531.mlab.com:45531/ybhe');
$db=$connection->ybhe;
$houses=$db->House;
$result = array();
if(empty($_POST['price'])) {
    $cursor = $houses->find();
    $cursor = $cursor->fields(array("image" => false));
    $num = 0;
    while ($cursor != null && $cursor->hasNext()) {
        $result[$num] = $cursor->next();
        $num = $num + 1;
    }
}else{
    $price = $_POST['price'];
    $cursor = $houses->find(array('price' => array('$lt' => intval($_POST['price']))),array('image'=>false));
    $num = 0;
    while ($cursor != null && $cursor->hasNext()) {
        $result[$num] = $cursor->next();
        $num = $num + 1;
    }
}
echo json_encode($result);

?>