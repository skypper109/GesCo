package Principale;
import java.util.ArrayList;
import java.util.List;

public class AdministrateurRH {
    private String jourDeRotation;
    private List<Agent> agents;
    private final List<JourFerie> joursFeries;

    public AdministrateurRH(String jourDeRotation) {
        this.jourDeRotation = jourDeRotation;
        this.agents = new ArrayList<>();
        this.joursFeries = new ArrayList<>();
    }

    public void ajouterAgent(Agent agent) {
        agents.add(agent);
    }

    public void retirerAgent(int idAgent) {
        agents.removeIf(agent -> agent.getId() == idAgent);
    }

    public void ajouterJourFerie(JourFerie jourFerie) {
        joursFeries.add(jourFerie);
    }

    // Getters/Setters si besoin
    public String getJourDeRotation() {
        return jourDeRotation;
    }

    public void setJourDeRotation(String jourDeRotation) {
        this.jourDeRotation = jourDeRotation;
    }

}
