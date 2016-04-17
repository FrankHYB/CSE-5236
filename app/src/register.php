<?php
/**
3  * Created by PhpStorm.
4  * User: yubin
5  * Date: 2/27/16
6  * Time: 11:02 AM
7  */
ini_set('display_errors', 1);
$error='';
if (empty($_POST['username'])||empty($_POST['password'])||empty($_POST['email'])||empty($_POST['phoneNum'])){
    $error = "Username or Password is invalid";
}
else{
    $username=$_POST['username'];
    $email=$_POST['email'];
    $password=$_POST['password'];
    $phoneNum=$_POST['phoneNum'];
    $connection= new MongoClient('mongodb://jjshao:jj5914@ds045531.mlab.com:45531/ybhe');
    $db=$connection->ybhe;
    $user=$db->androidUser;
    $checkUsername=$user->findOne(array('username'=>$username));
    $checkEmail=$user->findOne(array('email'=>$email));
    if($checkEmail==NULL && $checkUsername==NULL) {
        $user->insert(array(
                "username" => $username,
                "password" => md5($password),
                "email" => $email,
                "phoneNum"=>$phoneNum
            )
        );
        $connection->close();
    }else if($checkEmail!=NULL && $checkUsername==NULL){
        $error="This email has been registered";
    }else if($checkUsername!=NULL && $checkEmail==NULL){
        $error="This username has been registered";
    }else{
        $error="Both username and email has been registered";
    }
}
$response=$error;
echo $response;
?>