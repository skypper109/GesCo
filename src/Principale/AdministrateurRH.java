package Principale;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class AdministrateurRH  extends User{
    private DayOfWeek jourRotation;
    public int positionActuelle;
    public List<Agent> agentList;
    public List<Historique> historiqueList;
    public List<JourFerie> jourFerieList;
    public JourFerie jourFerie;

    public AdministrateurRH(DayOfWeek jourRotation) {
        super();
        this.jourRotation = jourRotation;
        this.positionActuelle = 0;
        this.agentList = new ArrayList<>();
        this.historiqueList = new ArrayList<>();
        this.jourFerieList = new ArrayList<>();
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

        if (email  != null && email.contains("@gmail.com")) {
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
    public void ajoutJourFerie(LocalDate date, String description){
        if (!jourFerieList.stream().filter(jourFerie -> jourFerie.getDateJourFerie().equals(date)).isParallel()){
            JourFerie jour = new JourFerie(date,description);
            jourFerieList.add(jour);
        }
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
    public void planifieRotation(LocalDate date) {
        // 🔒 Vérification : la date fournie ne doit pas être dans le passé
        if (date.isBefore(LocalDate.now())) {
            System.out.println("❌ Erreur : Impossible de planifier une rotation dans le passé (" + date + ").");
            return;
        }

        // Définir la première date de rotation valide
        LocalDate dateRotation = prochaineDateRotation(date);

        // Supprimer les historiques à partir de cette date
        historiqueList.removeIf(historique -> historique.getDateRotation().isAfter(date.minusDays(1)));

        for (int i = 0; i < agentList.size(); i++) {
            boolean verif = true;

            for (Historique hist : historiqueList) {
                if (agentList.get(i).getIdAgent() == hist.getIdAgent()
                        && hist.getDateRotation().equals(dateRotation)) {
                    verif = false;
                    System.out.println("⚠️ Agent déjà planifié pour " + dateRotation);
                    break;
                }
            }

            Agent agentPrevu = agentList.get(positionActuelle);
            Agent agentDisponible = trouveAgentDisponible(dateRotation);

            if (agentDisponible == null) {
                System.out.println("❌ Aucun agent n'est disponible pour la date " + dateRotation + " !");
                break;
            }

            String statut = agentDisponible.equals(agentPrevu) && dateRotation.isAfter(LocalDate.now())
                    ? "En cours"
                    : "Indisponible";

            int remplacant = statut.equals("Indisponible") ? agentDisponible.getIdAgent() : 0;
            String motif = "Pas de motif";

            if (remplacant != 0) {
                for (Indisponibilite list : agentPrevu.indisponibiliteList) {
                    if (list.getDateIndisponible().equals(dateRotation) && list.getId() == agentPrevu.getIdAgent()) {
                        motif = list.getMotif();
                        break;
                    }
                }
            }

            if (verif) {
                historiqueList.add(new Historique(agentPrevu.getIdAgent(), dateRotation, statut, motif, remplacant));
            }

            positionActuelle = (agentList.indexOf(agentDisponible) + 1) % agentList.size();
            dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
        }

        System.out.println("✅ Assignation terminée avec succès pour la date : " + date);
    }


    //La Rotation automatique :
    public void planifierRotationAuto() {
        LocalDate dateRotation = prochaineDateRotation(LocalDate.now());

        int rotationsAjoutees = 0;

        while (rotationsAjoutees < agentList.size()) {

            // Vérifie si une rotation existe déjà à cette date
            LocalDate finaldateRot = dateRotation;
            boolean dateDejaPlanifiee = historiqueList.stream()
                    .anyMatch(h -> h.getDateRotation().equals(finaldateRot));
            if (dateDejaPlanifiee) {
                dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
                continue;
            }

            Agent agentPrevu = agentList.get(positionActuelle);
            Agent agentDisponible = trouveAgentDisponible(dateRotation);

            if (agentDisponible == null) {
                System.out.println("❌ Aucun agent disponible pour le " + dateRotation);
                break;
            }

            String statut = agentDisponible.equals(agentPrevu) ? "En cours" : "Indisponible";
            int remplacant = statut.equals("Indisponible") ? agentDisponible.getIdAgent() : 0;

            String motif = "Pas de motif";
            if (!statut.equals("En cours")) {
                for (Indisponibilite indispo : agentPrevu.indisponibiliteList) {
                    if (indispo.getDateIndisponible().equals(dateRotation)) {
                        motif = indispo.getMotif();
                        break;
                    }
                }
            }

            historiqueList.add(new Historique(
                    agentPrevu.getIdAgent(),
                    dateRotation,
                    statut,
                    motif,
                    remplacant
            ));

            // Rotation circulaire
            positionActuelle = (agentList.indexOf(agentDisponible) + 1) % agentList.size();
            dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
            rotationsAjoutees++;
        }

        System.out.println("✅ Rotation planifiée avec succès !");
    }



    //Apres le signalement d'indiponibilité d'un agent :
    public void planifierRotationAutoDepuis(LocalDate dateDebut) {
        // ✅ Filtrer les historiques à conserver : tout ce qui est AVANT la date donnée
        historiqueList.removeIf(h -> !h.getDateRotation().isBefore(dateDebut));

        // ✅ Garder trace des agents déjà passés
        Set<Integer> agentsDéjàPlanifiés = new HashSet<>();
        for (Historique h : historiqueList) {
            agentsDéjàPlanifiés.add(h.getIdAgent());
        }

        // 🔁 On recommence à partir de la date donnée
        LocalDate dateRotation = prochaineDateRotation(dateDebut);
        int tentatives = 0;
        int totalAgents = agentList.size();

        while (agentsDéjàPlanifiés.size() < totalAgents && tentatives < totalAgents * 2) {
            Agent agentPrevu = agentList.get(positionActuelle);

            // Passer s’il est déjà passé
            if (agentsDéjàPlanifiés.contains(agentPrevu.getIdAgent())) {
                positionActuelle = (positionActuelle + 1) % totalAgents;
                tentatives++;
                continue;
            }

            Agent agentDisponible = trouveAgentDisponible(dateRotation);

            if (agentDisponible == null) {
                System.out.println("❌ Aucun agent disponible pour la date " + dateRotation);
                break;
            }

            // 🟢 Déterminer le statut
            boolean memePersonne = agentPrevu.equals(agentDisponible);
            String statut = memePersonne ? "En cours" : "Indisponible";
            int idRemplacant = memePersonne ? -1 : agentDisponible.getIdAgent();

            // 📝 Récupérer motif si remplacement
            String motif = "Pas de motif";
            if (!memePersonne) {
                for (Indisponibilite ind : agentPrevu.indisponibiliteList) {
                    if (ind.getDateIndisponible().equals(dateRotation)) {
                        motif = ind.getMotif();
                        break;
                    }
                }
            }

            // ✅ Ajouter à l’historique
            historiqueList.add(new Historique(
                    agentPrevu.getIdAgent(),
                    dateRotation,
                    statut,
                    motif,
                    idRemplacant
            ));

            // ✅ Marquer comme planifié
            agentsDéjàPlanifiés.add(agentPrevu.getIdAgent());

            // 🔁 Avancer dans la rotation
            positionActuelle = (agentList.indexOf(agentDisponible) + 1) % totalAgents;
            dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
            tentatives++;
        }

        System.out.println("✅ Nouvelle rotation planifiée à partir de " + dateDebut);
    }

    //Pour voir les rotation a venir en foonction de nombre de semaines :
    public void afficherRotationAvenir(int nombreSemaines) {
        if (agentList.isEmpty()) {
            System.out.println("❌ Aucun agent enregistré.");
            return;
        }

        System.out.println("\n📆 PROCHAINES ROTATIONS (" + nombreSemaines + " semaines)\n");
        System.out.println("--------------------------------------------------------------");
        System.out.printf("| %-12s | %-20s | %-10s |\n", "Date", "Agent Assigné", "Disponibilité");
        System.out.println("--------------------------------------------------------------");

        LocalDate dateRotation = prochaineDateRotation(LocalDate.now());
        int currentPosition = positionActuelle;

        Set<LocalDate> datesDéjàMontrées = new HashSet<>();

        for (int i = 0; i < nombreSemaines; i++) {
            // Trouver la prochaine date unique
            while (datesDéjàMontrées.contains(dateRotation)) {
                dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
            }

            Agent agent = trouveAgentDisponible(dateRotation);
            String nom = (agent != null) ? agent.getPrenom() + " " + agent.getNom() : "Aucun disponible";
            String dispo = (agent != null) ? "✅" : "❌";

            System.out.printf("| %-12s | %-20s | %-10s |\n", dateRotation, nom, dispo);

            // Enregistrer la date utilisée pour éviter les doublons
            datesDéjàMontrées.add(dateRotation);

            // Préparer la date suivante
            dateRotation = prochaineDateRotation(dateRotation.plusDays(1));

            // Avancer virtuellement la rotation sans modifier positionActuelle globale
            currentPosition = (currentPosition + 1) % agentList.size();
        }

        System.out.println("--------------------------------------------------------------");
    }

    // Trouve le nom complet d’un agent par ID
    private String getNomParId(int id) {
        for (Agent a : agentList) {
            if (a.getIdAgent() == id) {
                return a.getPrenom() + " " + a.getNom();
            }
        }
        return "Inconnu";
    }

    public void afficheHistorique() {
        if (historiqueList.isEmpty()) {
            System.out.println("❌ Aucun historique disponible.");
            return;
        }

        System.out.println("\n📚 HISTORIQUE DES ROTATIONS 📚\n");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-12s | %-20s | %-12s | %-20s | %-20s |\n", "Date", "Agent Prévu", "Statut", "Remplaçant", "Motif");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        for (Historique h : historiqueList) {
            String date = h.getDateRotation().toString();
            String agentNom = getNomParId(h.getIdAgent());
            String remplacant = h.getIdAgentRemp() != -1 ? getNomParId(h.getIdAgentRemp()) : "-";
            String motif = h.getMotif() != null ? h.getMotif() : "-";

            String statut;
            if (h.getIdAgentRemp() != -1) {
                statut = "Remplacé";
            } else if (h.getDateRotation().isAfter(LocalDate.now())) {
                statut = "À venir";
            } else if (h.getDateRotation().isEqual(LocalDate.now())) {
                statut = "En cours";
            } else {
                statut = "Effectué";
            }

            System.out.printf("| %-12s | %-20s | %-12s | %-20s | %-20s |\n", date, agentNom, statut, remplacant, motif);
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");
    }


}




