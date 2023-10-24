<?php

class Connexion {

    private $connexion;

    public function __construct() {
        $host = 'localhost';
        $dbname = 'school1';
        $login = 'root';
        $password = '';
        
        try {
            $this->connexion = new PDO("mysql:host=$host;dbname=$dbname", $login, $password);
            $this->connexion->query("SET NAMES UTF8");
        } catch (Exception $e) {
            die('Erreur : ' . $e->getMessage());
        //arrêter immédiatement l'exécution du script PHP. Elle affiche le message passé en argument  
        }
    }

    function getConnexion() {
        return $this->connexion;
    }

}
