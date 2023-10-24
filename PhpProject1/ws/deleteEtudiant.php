<?php

if($_SERVER["REQUEST_METHOD"] == "POST"){
 include_once("../racine.php");
 include_once RACINE.'/service/EtudiantService.php';
 delete();
}

function delete(){
   
if (isset($_GET['id'])) {
    $id = $_GET['id'];
 $es = new EtudiantService();
 $es->delete($es->findById($id));
}
}