<?php

include_once '../racine.php';
include_once RACINE . '/service/EtudiantService.php';

// extraire les données passées via la méthode GET de l'URL
//exemple :  http://example.com/index.php?nom=John&prenom=Doe...

extract($_GET);
$es = new EtudiantService();
$es->create(new Etudiant(1, $nom, $prenom, $ville, $sexe));

header("location:../index.php");

