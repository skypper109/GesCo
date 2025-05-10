package Principale;

import java.time.LocalDate;

public class Indisponibilite {
    private int id;
    private String motif;
    private LocalDate dateIndisponible;

    public Indisponibilite(int id,String motif, LocalDate dateIndisponible) {
        this.motif = motif;
        this.id = id;
        this.dateIndisponible = dateIndisponible;
    }

    public LocalDate getDateIndisponible() {
        return dateIndisponible;
    }

    public int getId() {
        return id;
    }

    public String getMotif() {
        return motif;
    }
}

