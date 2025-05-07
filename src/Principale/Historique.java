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
}
