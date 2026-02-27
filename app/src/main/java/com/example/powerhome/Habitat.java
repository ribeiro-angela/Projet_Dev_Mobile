package com.example.powerhome;

import java.util.ArrayList;
import java.util.List;

/**
 * Modèle Habitat (TP3/TP5)
 * - Compatible Gson (champs publics + constructeur vide)
 */
public class Habitat {

    public int id;
    public String name;
    public int floor;
    public double area;
    public List<Appliance> appliances = new ArrayList<>();

    // Constructeur vide requis pour Gson
    public Habitat() {}

    public Habitat(String name, int floor, double area, List<Appliance> appliances) {
        this.name = name;
        this.floor = floor;
        this.area = area;
        if (appliances != null) {
            this.appliances = appliances;
        }
    }

    public int getEquipmentCount() {
        return appliances == null ? 0 : appliances.size();
    }
}
