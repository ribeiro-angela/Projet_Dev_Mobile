package com.example.powerhome;

/**
 * Modèle Appliance (TP3/TP5)
 */
public class Appliance {

    public int id;
    public String name;
    public int iconResId;
    public int wattage;

    // Constructeur vide pour Gson
    public Appliance() {}

    public Appliance(String name, int iconResId, int wattage) {
        this.name = name;
        this.iconResId = iconResId;
        this.wattage = wattage;
    }
}
