package Principale;

import java.time.LocalDate;

public class Indisponibilite {

    private String motif;
    private LocalDate dateIndisponible;

    public Indisponibilite(String motif, LocalDate dateIndisponible) {
        this.motif = motif;
        this.dateIndisponible = dateIndisponible;
    }

    public LocalDate getDateIndisponible() {
        return dateIndisponible;
    }
}

