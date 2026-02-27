package com.example.powerhome;

/**
 * TP5 (Services Web) :
 * - Sur émulateur Android : 10.0.2.2 pointe vers ton PC.
 * - Sur téléphone réel : remplace par l'IP de ton PC (ex: http://192.168.1.20).
 *
 * Mets ici l'URL de ton endpoint JSON qui renvoie la liste des habitats.
 * Si tu n'as pas de serveur, laisse tel quel : l'app utilisera des données locales.
 */
public final class ApiConfig {

    private ApiConfig() {}

    // Exemple : "http://10.0.2.2/powerhome/get_habitats.php"
    public static final String HABITATS_URL = "";
}
