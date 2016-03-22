<?php
$response=''; // Variable To Store Error Message
if (empty($_POST['username']) || empty($_POST['password'])) {
    $error = "Username or Password is invalid";
}
else {
    $username=$_POST['username'];
    $password=$_POST['password'];
    $connection=new MongoClient('mongodb://jjshao:jj5914@ds045531.mlab.com:45531/ybhe');
    $db=$connection->ybhe;
    //Select the collecton
    $user=$db->User;
    $query=$user->findOne(array('username'=>$username,'password'=>$password));
    if($query!=NULL){
        $response=$username;
    }else{
        $response='Username or password is invalid';
    }
    $connection->close();
}
echo $response;
?>