<?php
//if($_SERVER["REQUEST_METHOD"] == "POST"){
// include_once '../racine.php';
// include_once RACINE.'/service/EtudiantService.php';
// create();
//}
//function create(){
// extract($_POST);
// $es = new EtudiantService();
// $es->create(new Etudiant(1, $nom, $prenom, $ville, $sexe));
//}


if($_SERVER["REQUEST_METHOD"] == "POST"){
 include_once("../racine.php");
 include_once RACINE.'/service/EtudiantService.php';
 create();
}
function create(){
 extract($_POST);
 $es = new EtudiantService();
 //insertion d’un étudiant
 $es->create(new Etudiant(1, $nom, $prenom, $ville, $sexe));
 //chargement de la liste des étudiants sous format json
 header('Content-type: application/json');
 echo json_encode($es->findAllApi());
}





//if ($_SERVER["REQUEST_METHOD"] == "GET") {
//    include_once("../racine.php");
//    include_once RACINE.'/service/EtudiantService.php';
//    create();
//}
//
//function create(){
//    $nom = $_GET["nom"];
//    $prenom = $_GET["prenom"];
//    $ville = $_GET["ville"];
//    $sexe = $_GET["sexe"];
//
//    $es = new EtudiantService();
//
//    // Insertion d’un étudiant
//    $es->create(new Etudiant(1, $nom, $prenom, $ville, $sexe));
//
//    // Chargement de la liste des étudiants sous format json
//    header('Content-type: application/json');
//    echo json_encode($es->findAllApi());
//}
