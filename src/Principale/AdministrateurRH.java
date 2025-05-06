package Principale;

public class AdministrateurRH {
    //partie de Simpara
    //creation des atrributs admin
    private List <Agent> listAgent;
    private int positionActuelle;
    private DayOfWeek joursRotation;
    private set <LocalDate> joursFeries;
    private List <LoclDate> historique;

   //constructeur


    public AdministrateurRH(DayOfWeek joursRotation) {
        this.joursRotation = joursRotation;
        this.positionActuelle;
        this.joursRotation;
        this.joursFeries
        this.historique;
    }

    //GETTERS
    public List<Agent> getListAgent() {
        return listAgent;
    }


    public List<LocalDate> getJoursFeries() {
        return joursFeries;
    }

    public List<LocalDate> getJoursDeRotation() {
        return joursDeRotation;
    }

    public List<LoclDate> getHistorique() {
        return historique;
    }

    //SETTER


    public void setListAgent(List<Agent> listAgent) {
        this.listAgent = listAgent;
    }

    public void setJoursDeRotation(List<LocalDate> joursDeRotation) {
        this.joursDeRotation = joursDeRotation;
    }

    public void setJoursFeries(List<LocalDate> joursFeries) {
        this.joursFeries = joursFeries;
    }

    public void setHistorique(List<LoclDate> historique) {
        this.historique = historique;
    }
}


