<?php
include_once '../racine.php';
include_once RACINE . '/service/EtudiantService.php';

extract($_GET);
$es = new EtudiantService();

$id = $_GET['id'];
$nom = $_GET['nom'];
$prenom = $_GET['prenom'];
$ville = $_GET['ville'];
$sexe = $_GET['sexe'];

$upetudiant = $es->create(new Etudiant($id,$nom,$prenom,$ville,$sexe));

$es->update($upetudiant);

header("location:../index.php");









