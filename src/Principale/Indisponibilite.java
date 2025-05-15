package Principale;

import java.time.LocalDate;

public class Indisponibilite {
    private final int id;
    private final String motif;
    private final LocalDate dateIndisponible;

    public Indisponibilite(int agentID,String motif, LocalDate dateIndisponible) {
        this.motif = motif;
        this.id = agentID;
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

