package Principale;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdministrateurRH {
    private DayOfWeek jourRotation;
    private int positionActuelle;
    public List<Agent> agentList;
    public Set<Historique> historiqueList;
    public Set<JourFerie> jourFerieList;
    public User user;
    public JourFerie jourFerie;

    public AdministrateurRH(DayOfWeek jourRotation) {
        this.jourRotation = jourRotation;
        this.positionActuelle = 0;
        this.agentList = new ArrayList<>();
        this.historiqueList = new HashSet<>();
        this.jourFerieList = new HashSet<>();
    }
    //Pour ajouter les agents :

    private boolean emailEstValide(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    private boolean emailExisteDeja(String email) {
        for (Agent agent : agentList) {
            if (agent.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private String formatDate(LocalDate date) {
        return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
    }

    public boolean ajoutAgent(int id,String nom,String prenom,String email){
        if (emailEstValide(email) && !emailExisteDeja(email)){
            Agent agent = new Agent(id,nom,prenom,email);
            agentList.add(agent);
            user = new User(email,"1234","Agent");
            user.userList.add(user);
            return true;
        }
        return false;
    }
    //Pour retirer un agent
    public boolean retireAgent(String email){
        return agentList.removeIf(agent -> agent.getEmail().equalsIgnoreCase(email));
    }
    //Pour l'ajout des jours Fériés
    public boolean ajoutJourFerie(LocalDate date,String description){
        if (!jourFerieList.stream().filter(jourFerie -> jourFerie.getDateJourFerie().equals(date)).isParallel()){
            JourFerie jour = new JourFerie(date,description);
            jourFerieList.add(jour);
            return true;
        }
        return false;
    }

    //Pour Trouver la prochaine date de rotation:
    public LocalDate prochaineDateRotation(LocalDate ref){
        LocalDate date = ref.with(TemporalAdjusters.nextOrSame(jourRotation));
        while (jourFerieList.stream().filter(jourFerie -> jourFerie.getDateJourFerie().equals(date)).isParallel()){
            do {
                date.plusWeeks(1);
            } while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
        }
        return date;
    }

    //Pour la mathode de Recherche de l'agent disponible :
    public Agent trouveAgentDisponible(LocalDate date){
        int tentative = 0;
        int position = positionActuelle;
        while (tentative < agentList.size()){
            Agent agent = agentList.get(position);
            if (agent.estDisponible(date)){
                return agent;
            }else {
                position = (position+1) % agentList.size();
                tentative++;
            }

        }
        return null;
    }

    // Pour afficher les rotations futures
    public void afficherRotationAvenir(int nombreSemaines) {
        if (agentList.isEmpty()) {
            System.out.println("Aucun agent disponible pour la rotation.");
            return;
        }

        LocalDate aujourdhui = LocalDate.now();
        System.out.println("Calendrier des rotations à venir (" + nombreSemaines + " semaines):");
        System.out.println("----------------------------------------");

        for (int i = 0; i < nombreSemaines; i++) {
            LocalDate dateRotation = prochaineDateRotation(aujourdhui.plusWeeks(i));
            Agent agent = trouveAgentDisponible(dateRotation);

            if (agent != null) {
                System.out.printf("Semaine du %s: %s %s (%s)%n",
                        dateRotation,
                        agent.getPrenom(),
                        agent.getNom(),
                        agent.getEmail());

                // Mettre à jour la position actuelle pour la prochaine rotation
                positionActuelle = (agentList.indexOf(agent) + 1) % agentList.size();

                // Enregistrer dans l'historique
                historiqueList.add(new Historique(
                        agent.getIdAgent(),        // idAgent
                        dateRotation,        // dateRotation
                        "Planifié",         // statut
                        "Rotation normale",  // motif
                        -1                   // idAgentRemp (aucun remplaçant)
                ));
            } else {
                System.out.printf("Semaine du %s: Aucun agent disponible%n", dateRotation);
            }
        }

    }

    //Methode pour afficher Historique de rotation
    public void afficherHistorique() {
        if (historiqueList.isEmpty()) {
            System.out.println("Aucun historique de rotation disponible.");
            return;
        }

        System.out.println("Historique des rotations:");
        System.out.println("----------------------------------------");

        for (Historique historique : historiqueList) {
            // Trouver l'agent correspondant
            Agent agent = agentList.stream()
                    .filter(a -> a.getIdAgent() == historique.getIdAgent())
                    .findFirst()
                    .orElse(null);

            System.out.println("Date: " + historique.getDateRotation());
            System.out.println("Agent: " + (agent != null ?
                    agent.getPrenom() + " " + agent.getNom() : "Inconnu"));
            System.out.println("Statut: " + historique.getStatut());
            System.out.println("Motif: " + historique.getMotif());


            if (historique.getIdAgentRemp() != -1) {
                Agent remplacant = agentList.stream()
                        .filter(a -> a.getIdAgent() == historique.getIdAgentRemp())
                        .findFirst()
                        .orElse(null);
                System.out.println("Remplaçant: " + (remplacant != null ?
                        remplacant.getPrenom() + " " + remplacant.getNom() : "Inconnu"));
            }
            System.out.println("----------------------------------------");
        }
    }
}



