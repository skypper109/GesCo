package Principale;

import java.time.LocalDate;

public class Historique {
    //La partie de Djire Historique :
    private int idAgent;
    private LocalDate dateRotation;
    private String statut;
    private String motif;
    private int idAgentRemp;

    public Historique(int idAgent, LocalDate dateRotation, String statut, String motif, int idAgentRemp) {
        this.idAgent = idAgent;
        this.dateRotation = dateRotation;
        this.statut = statut;
        this.motif = motif;
        this.idAgentRemp = idAgentRemp;
    }


    public int getIdAgent() {
        return idAgent;
    }

    public LocalDate getDateRotation() {
        return dateRotation;
    }

    public String getStatut() {
        return statut;
    }

    public String getMotif() {
        return motif;
    }

    public int getIdAgentRemp() {
        return idAgentRemp;
    }
}
