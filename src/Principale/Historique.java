package Principale;

import java.util.ArrayList;
import java.util.List;

public class Historique {
    private String tour;

    public Historique(String tour) {
        this.tour = tour;
    }

    public String getTour() {
        return tour;
    }

    // Simuler une base de données
    public static List<Historique> getHistorique() {
        List<Historique> list = new ArrayList<>();
        list.add(new Historique("Tour 1 : Joueur A"));
        list.add(new Historique("Tour 2 : Joueur B"));
        list.add(new Historique("Tour 3 : Joueur C"));
        return list;
    }

    public boolean isEstPasse() {

    }
}
