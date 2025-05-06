package Principale;

import java.time.LocalDate;
import java.util.Date;

public class Indisponibilte {
    private int idAgant;
    private String motif;
    private LocalDate dateIdisponible;
    Indisponibilte(int idAgent, String motif, LocalDate dateIndisponible)
    {
        this.idAgant= idAgent;
        this.motif= motif;
        this.dateIdisponible = dateIndisponible;
    }

    public int getIdAgant() {
        return idAgant;
    }

    public String getMotif() {
        return motif;
    }

    public LocalDate getDateIdisponible() {
        return dateIdisponible;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setDateIdisponible(LocalDate dateIdisponible) {
        this.dateIdisponible = dateIdisponible;
    }
}
