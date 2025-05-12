package Principale;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class AdministrateurRH  extends User{
    private DayOfWeek jourRotation;
    public int positionActuelle;
    public List<Agent> agentList;
    public Set<Historique> historiqueList;
    public Set<JourFerie> jourFerieList;
    public JourFerie jourFerie;

    public AdministrateurRH(DayOfWeek jourRotation) {
        super();
        this.jourRotation = jourRotation;
        this.positionActuelle = 0;
        this.agentList = new ArrayList<>();
        this.historiqueList = new HashSet<>();
        this.jourFerieList = new HashSet<>();
    }

    //Ajout des identifiants de l'admin:
    public void ajoutAdmin(){
        User admin = new User("admin@gmail.com","admin1234","Admin");
        userList.add(admin);
    }

    public DayOfWeek getJourRotation() {
        return jourRotation;
    }

    public void setJourRotation(DayOfWeek jourRotation) {
        this.jourRotation = jourRotation;
    }
    //Pour ajouter les agents :

    public boolean emailEstValide(String email) {

        if (email  != null && email.contains("@") && email.contains(".")) {
           return true;
        }else {
            System.out.println("Cet email est invalide. Veuillez Saisir un email valide");
        }
        return false;
    }
    public boolean emailExisteDeja(String email) {
        for (Agent agent : agentList) {
            if (agent.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Cet email existe deja. Veuillez Saisir un autre");
                return true;
            }
        }
        return false;
    }
    public void ajoutAgent(int id, String nom, String prenom, String email){
        if (emailEstValide(email) && !emailExisteDeja(email)){
            Agent agent = new Agent(id,nom,prenom,email);
            agentList.add(agent);
            userList.add(new User(email,"agent1234","Agent"));
        }
    }
    //Pour retirer un agent
    public boolean retireAgent(String email){
        //Avec une fonction conditionnelle(Lambda) qui agit en supprimmant tout élément x qui vérifie la condition x ->
        return  agentList.removeIf(a -> a.getEmail().equalsIgnoreCase(email)) &&
                userList.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
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

    public void afficherJoursFeries() {
        if (jourFerieList.isEmpty()) {
            System.out.println("Aucun jour férié enregistré.");
            return;
        }

        System.out.println("\nListe des jours fériés enregistrés :");
        List<LocalDate> feriesTries = new ArrayList<>();
        Collections.sort(feriesTries);

        for (JourFerie date : jourFerieList) {
            System.out.println("******* → " + date.getDateJourFerie()+" ****** Le jour de :  "+date.getDescription());
        }
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

    //Pour la methode de Recherche de l'agent disponible :
    public Agent trouveAgentDisponible(LocalDate date){
        int tentative = 0;
        int position = positionActuelle;
        while (tentative < agentList.size()){
            Agent agent = agentList.get(position);
            if (agent.estDisponible(date,agent.getIdAgent())){
                return agent;
            }else {
                position = (position+1) % agentList.size();
                tentative++;
            }

        }
        return null;
    }

    //La methode pour la rotation :
    public void planifieRotation(LocalDate date){
        //On declare les deux types d'agents qui sont l'agent prevu et l'agent dispo:
        LocalDate dateRotation = prochaineDateRotation(date);

        for (int i = 0; i < agentList.size(); i++) {
            boolean verif = true;
            for (Historique hist:historiqueList){
                if (agentList.get(i).getIdAgent() == hist.getIdAgent() && hist.getDateRotation().equals(dateRotation)){
                    verif=false;
                    System.out.println("Agent existe deja");
                    break;
                }
            }
            // L’agent qui devait normalement faire le tour
            Agent agentPrevu = agentList.get(positionActuelle);

            // Trouver un agent disponible pour cette date
            Agent agentDisponible = trouveAgentDisponible(dateRotation);

            if (agentDisponible == null) {
                System.out.println("Aucun agent n'est disponible pour la date " + dateRotation + " !");
                break;
            }

            // Déterminer le statut du tour
            String statut = agentDisponible.equals(agentPrevu) && dateRotation.isAfter(LocalDate.now()) ? "En cour" : "Indisponible";
            int remplacant = statut.equals("Indisponible") ? agentDisponible.getIdAgent() : 0;
            String motif = "Pas de motif";
            if (remplacant!=0){
                for (Indisponibilite list:agentPrevu.indisponibiliteList){
                    if (list.getDateIndisponible().equals(dateRotation) && list.getId() == agentPrevu.getIdAgent()){
                        motif = list.getMotif();
                    }
                }
            }


            if (verif){

                // Ajouter à l’historique
                historiqueList.add(new Historique(agentPrevu.getIdAgent(),dateRotation,statut,motif,remplacant));

            }

            // Avancer dans la rotation circulaire
            positionActuelle = (agentList.indexOf(agentDisponible) + 1) % agentList.size();

            // Passer à la prochaine date valide
            dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
        }

        System.out.println("Assignation terminée avec succès pour la date du : "+date + " qui commencera le : " +dateRotation);

    }

    //La Rotation automatique :
    public void planifierRotationAuto() {
        LocalDate dateRotation = prochaineDateRotation(LocalDate.now());

        for (int i = 0; i < agentList.size(); i++) {
            boolean verif = true;
            for (Historique hist:historiqueList){
                if (agentList.get(i).getIdAgent() == hist.getIdAgent() && hist.getDateRotation().equals(dateRotation)){
                    verif=false;
                    break;
                }
            }
            // L’agent qui devait normalement faire le tour
            Agent agentPrevu = agentList.get(positionActuelle);

            // Trouver un agent disponible pour cette date
            Agent agentDisponible = trouveAgentDisponible(dateRotation);

            if (agentDisponible == null) {
                System.out.println("Aucun agent n'est disponible pour la date " + dateRotation + " !");
                break;
            }

            // Déterminer le statut du tour
            String statut = agentDisponible.equals(agentPrevu) ? "En cour" : "Indisponible";
            int remplacant = statut.equals("Indisponible") ? agentDisponible.getIdAgent() : 0;
            String motif = "Pas de motif";
            if (remplacant!=0){
                for (Indisponibilite list:agentPrevu.indisponibiliteList){
                    if (list.getDateIndisponible().equals(dateRotation) && list.getId() == agentPrevu.getIdAgent()){
                        motif = list.getMotif();
                        verif=true;
                    }
                }
            }

            if (verif){

                // Ajouter à l’historique
                historiqueList.add(new Historique(agentPrevu.getIdAgent(),dateRotation,statut,motif,remplacant));

            }

            // Avancer dans la rotation circulaire
            positionActuelle = (agentList.indexOf(agentDisponible) + 1) % agentList.size();
            // Passer à la prochaine date valide
            dateRotation = prochaineDateRotation(dateRotation.plusDays(1));

        }

        System.out.println("Assignation terminée avec succès !");
    }

    public void afficheHistorique() {

        if (historiqueList.isEmpty()) {
            System.out.println(" Aucun historique disponible pour le moment.");
            return;
        }

        System.out.println("\n HISTORIQUE DES ROTATIONS\n");
        //On utilise des spécificateurs de format pour organiser les colonnes a l'affichage
        // % debut du format; - aligner a gauche; 15 nombre de caractere; s le type de contenu
        System.out.printf("%-15s | %-25s | %-15s | %-20s | %-20s\n", " Date", " Agent Prévu", " Statut", " Remplaçant","Motif");
        System.out.println("----------------------------------------------------------------------------------------------------");

        for (Historique h : historiqueList) {
            String date = h.getDateRotation().toString();
            String agentNom = "";
            String remplacant = "-";
            String motif = "-";
            String statut = "" ;
            for (Agent agent:agentList){
                if (agent.getIdAgent() == h.getIdAgent()){
                    agentNom = agent.getNom()+" "+agent.getPrenom();
                    if (LocalDate.now().isBefore(h.getDateRotation())){
                        statut = "A venir";
                    } else if (LocalDate.now().isAfter(h.getDateRotation())) {
                        statut = "Deja Effectué";
                    }else {
                        statut = "En cour";
                    }
                }else if (agent.getIdAgent() == h.getIdAgentRemp()){
                    remplacant = agent.getNom()+" "+agent.getPrenom();
                    motif = h.getMotif();
                    statut = "Remplaçer ->";
                }
            }
            System.out.printf("%-15s | %-25s | %-15s | %-20s | %-20s\n", date, agentNom, statut, remplacant,motif);
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
    }

}




