package Principale;

import GestionDB.Tables.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

public class AdministrateurRH  extends User{
    private DayOfWeek jourRotation;
    public int positionActuelle;
    public List<Agent> agentList;
    //La liste des tables :
    public Agents tableAgent = new Agents();
    public JoursFeries tableJourFerie = new JoursFeries();
    public Indisponibilites tableIndisponibilite = new Indisponibilites();
    public Historiques tableHistorique = new Historiques();
    public Users tableUser = new Users();
    public AdministrateurRHs tableAdmin = new AdministrateurRHs();

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
        String nom = "Admin";
        String prenom = "Admin";
        String email = "admin@gmail.com";
        String password = "admin1234";
        String role = "Admin";
        tableUser.insert(email,password,role);
        new AdministrateurRHs().addAdminRH(nom,prenom,email,password,5);
    }

    public DayOfWeek getJourRotation() {
        return jourRotation;
    }

    public void setJourRotation(DayOfWeek jourRotation) {
        this.jourRotation = jourRotation;
    }
    //Pour ajouter les agents :

    public boolean emailEstValide(String email) {
        if (email != null && email.contains("@") && email.contains(".")){
            return true;
        }
        System.out.println("Cet email est invalide. Veuillez Saisir un email valide");
        return false;
    }

    public boolean emailExisteDeja(String email) {
        if (tableAgent.toutAgent()==null){
            return false;
        }
        for (Agent agent : tableAgent.toutAgent()) {
            if (agent.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Cet email existe deja. Veuillez Saisir un autre");
                return true;
            }
        }
        return false;
    }

    public boolean ajoutAgent(String nom, String prenom, String email){
        if (emailEstValide(email) && !emailExisteDeja(email)){
            tableAgent.ajoutAgent(nom,prenom,email);
            userTable.insert(email,"agent1234","Agent");
            return true;
        }
        return false;
    }
    //Pour Desactiver un agent
    public boolean desactiverAgent(String email){
        return tableAgent.desactiverAgent(email);
    }
    //Pour Activer un agent
    public boolean activerAgent(String email){
        return tableAgent.activerAgent(email);
    }
    //Pour reinitialiser le mot de passe d'un agent :
    public boolean reinitialiserPwd(String email){return tableAgent.changePwdAgent(email);}
    //Pour l'ajout des jours Fériés
    public void ajoutJourFerie(LocalDate date, String description){
        if (tableJourFerie.addJourFerie(date,description)){
            System.out.println("La date ajouter avec succès !!!");
        }
    }

    //Pour afficher les jours feriés :
    public void afficherJoursFeries() {
        List<JourFerie> jourFeries = tableJourFerie.allJourFeries();
        if (jourFeries.isEmpty()) {
            System.out.println("Aucun jour férié enregistré.");
            return;
        }

        System.out.println("==============[ Liste des jours fériés enregistrés ]===============");

        for (JourFerie date : jourFeries) {
            System.out.println("******* → " + date.getDateJourFerie()+" ****** Le jour de :  "+date.getDescription());
            System.out.println("---------------------------------------------------------");
        }
    }


    // Pour trouver la prochaine date de rotation
    public LocalDate prochaineDateRotation(LocalDate ref) {
        int jour = tableAdmin.getDateRotation("admin@gmail.com");
        LocalDate date = ref.with(TemporalAdjusters.nextOrSame(DayOfWeek.of(jour)));

        List<JourFerie> jourFerieList = tableJourFerie.allJourFeries();

        if (jourFerieList==null){
            return date;
        }
        for (JourFerie jourF:jourFerieList){
            if (jourF.getDateJourFerie().equals(date)){
                date = date.plusWeeks(1);
                break;
            }
        }
        return date;
    }

    // Méthode pour trouver un agent disponible à une date
    public Agent trouveAgentDisponible(LocalDate date) {
        int tentative = 0;
        int position = positionActuelle;
        List<Agent> agentList = tableAgent.allAgent();

        while (tentative < agentList.size()) {
            Agent agent = agentList.get(position);
            if (tableAgent.estDisponible(date, agent.getIdAgent())) {
                return agent;
            } else {
                position = (position + 1) % agentList.size();
                tentative++;
            }
        }
        return null;
    }

    //Pour signaler une indisponibilité coté Admin :
    public void signalerIndisponibiliteAvecRotation(String motif, String email, LocalDate dateIndispo) {
        if (dateIndispo.isBefore(LocalDate.now())) {
            System.out.println("❌ Vous ne pouvez pas signaler une indisponibilité dans le passé.");
            return;
        }
        // Chercher l'agent concerné
        Agent agentCible = null;
        List<Agent> listeAgent = tableAgent.allAgent();
        for (Agent agent : listeAgent) {
            if (agent.getEmail().equals(email)) {
                agentCible = agent;
                break;
            }
        }

        if (agentCible == null) {
            System.out.println("❌ Erreur : Agent non trouvé.");
            return;
        }

        tableIndisponibilite.addIndisponible(agentCible.getIdAgent(), motif, dateIndispo);
        System.out.println("✅ Indisponibilité enregistrée pour le " + dateIndispo);

        // On remet la position à l’agent concerné (s’il existe encore)
        positionActuelle = listeAgent.indexOf(agentCible)+1;
        if (positionActuelle > listeAgent.size() ) {
            positionActuelle = 0;

        }
        // On relance la planification à partir de cette date
        planifierRotationAuto();
    }

    // Rotation automatique complète
    public void planifierRotationAuto() {
        positionActuelle = 0;
        LocalDate dateRotation = prochaineDateRotation(LocalDate.now());

        tableHistorique.deleteHistoriqueByDate(dateRotation);
        List<Agent> agentList = tableAgent.allAgent();
        List<Indisponibilite> indisponibiliteList = tableIndisponibilite.allIndisponibilite();

        List<Integer> agentsPlanifies = new ArrayList<>();
        List<LocalDate> agentsPlanifiesDate = new ArrayList<>();
        int tentatives = 0;
        int totalAgents = agentList.size();

        while (agentsPlanifies.size() < totalAgents && tentatives < totalAgents * 2) {
            Agent agentPrevu = agentList.get(positionActuelle);

            if (agentsPlanifies.contains(agentPrevu.getIdAgent()) || agentsPlanifiesDate.contains(dateRotation)) {
                positionActuelle = (positionActuelle + 1) % totalAgents;
                tentatives++;
                continue;
            }

            Agent agentDisponible = trouveAgentDisponible(dateRotation);
            int idAgent = agentPrevu.getIdAgent();

            if (agentDisponible == null) {
                System.out.println("❌ Aucun agent disponible pour la date " + dateRotation);
                break;
            }

            boolean memePersonne = agentPrevu.equals(agentDisponible);

            String statut = memePersonne ? "En cours" : "Indisponible";
            int idRemplacant = memePersonne ? 0 :  agentPrevu.getIdAgent();

            String motif = null;
            if (!memePersonne) {
                for (Indisponibilite ind : indisponibiliteList) {
                    if (ind.getDateIndisponible().equals(dateRotation)) {
                        motif = ind.getMotif();
                        idAgent = agentDisponible.getIdAgent();
                        break;
                    }
                }
            }

            tableHistorique.addHistorique(idAgent, dateRotation, statut, motif, idRemplacant);

            agentsPlanifies.add(idAgent);
            agentsPlanifiesDate.add(dateRotation);
            positionActuelle = (agentList.indexOf(agentDisponible) + 1) % totalAgents;
            dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
            tentatives++;
        }

        System.out.println("✅ Rotation actualiser avec succès !!");
    }

   
    // Planifie une nouvelle rotation complète à partir d’une date future
    public void planifierRotationAutoDepuis(LocalDate dateDebut) {
        if (dateDebut.isBefore(LocalDate.now())) {
            System.out.println("⚠️ La date de rotation ne peut pas être antérieure à aujourd’hui.");
            return;
        }

        List<Agent> agentList = tableAgent.allAgent();
        List<Indisponibilite> indisponibiliteList = tableIndisponibilite.allIndisponibilite();

        // Début de la planification
        LocalDate dateRotation = prochaineDateRotation(dateDebut);
        int rotationsAjoutees = 0;

        while (rotationsAjoutees < agentList.size()) {
            Agent agentPrevu = agentList.get(positionActuelle);
            Agent agentDisponible = trouveAgentDisponible(dateRotation);

            if (agentDisponible == null) {
                System.out.println("❌ Aucun agent disponible pour la date " + dateRotation);
                break;
            }

            boolean memeAgent = agentPrevu.equals(agentDisponible);
            String statut = memeAgent ? "En cours" : "Indisponible";
            int idRemplacant = memeAgent ? 0 : agentDisponible.getIdAgent();
            int idEffectif = memeAgent ? agentPrevu.getIdAgent() : agentDisponible.getIdAgent();

            // Motif s’il y a remplacement
            String motif = null;
            if (!memeAgent) {
                for (Indisponibilite ind : indisponibiliteList) {
                    if (ind.getDateIndisponible().equals(dateRotation) && ind.getId() == agentPrevu.getIdAgent()) {
                        motif = ind.getMotif();
                        break;
                    }
                }
            }

            tableHistorique.addHistorique(idEffectif, dateRotation, statut, motif, idRemplacant);

            // Marquer comme fait et passer au suivant
            positionActuelle = (agentList.indexOf(agentDisponible) + 1) % agentList.size();
            dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
            rotationsAjoutees++;
        }

        System.out.println("✅ Nouvelle rotation planifiée à partir de " + dateDebut);
    }
    // Obtenir le nom complet d'un agent
    private String getNomParId(int id) {
        for (Agent a : tableAgent.toutAgent()) {
            if (a.getIdAgent() == id) {
                return a.getPrenom() + " " + a.getNom();
            }
        }
        return "Inconnu";
    }

    // Obtenir le statut d'une ligne d'historique
    private String getStatutRotation(Historique h) {
        if (h.getIdAgentRemp() != 0) return "à Remplacer";
        if (h.getDateRotation().isAfter(LocalDate.now())) return "À venir";
        if (h.getDateRotation().isEqual(LocalDate.now())) return "En cours";
        return "Effectué";
    }

    // Affichage de l'historique complet
    public void afficheHistorique() {
        List<Historique> historiqueList = tableHistorique.allHistorique();
        if (historiqueList.isEmpty()) {
            System.out.println("❌ Aucun historique disponible.");
            return;
        }

        System.out.println("\n📚 HISTORIQUE DES ROTATIONS 📚\n");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-12s | %-20s | %-12s | %-20s | %-20s |\n", "Date", "Agent Prévu", "Statut", "Remplaçant", "Motif");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        List<Agent> agentList = tableAgent.allAgent();
        List<Indisponibilite> indisponibiliteList = tableIndisponibilite.allIndisponibilite();
        for (Historique h : historiqueList) {
            /*for (Indisponibilite ind : indisponibiliteList){
                if (ind.getDateIndisponible().equals(h.getDateRotation())){
                    for (Agent agent:agentList){
                        if (agent.getIdAgent()==ind.getId()){
                            h.setIdAgentRemp(ind.getId());
                            h.setMotif(ind.getMotif());
                            break;
                        }
                    }
                }
            }*/
            String date = h.getDateRotation().toString();
            String agentNom = getNomParId(h.getIdAgent());
            String remplacant = h.getIdAgentRemp() != 0 ? getNomParId(h.getIdAgentRemp()) : "-";
            String motif = h.getMotif() != null ? h.getMotif() : "-";
            String statut = getStatutRotation(h);

            System.out.printf("| %-12s | %-20s | %-12s | %-20s | %-20s |\n", date, agentNom, statut, remplacant, motif);
            System.out.println("___________________________________________________________________________________________________________");
        }

    }


    public void afficherRotationAvenir(int nombreSemaines) {
        List<Agent> agentList = tableAgent.allAgent();

        List<Historique> historiqueList = tableHistorique.allHistorique();

        if (agentList.isEmpty()) {
            System.out.println("❌ Aucun agent disponible pour la rotation.");
            return;
        }

        LocalDate dateRotation = prochaineDateRotation(LocalDate.now());
        List<LocalDate> datesDejaPlanifiees = new ArrayList<>();
        for (Historique h:historiqueList){
            if (h.getDateRotation().isAfter(LocalDate.now())){
                datesDejaPlanifiees.add(h.getDateRotation());
            }
        }

        System.out.println("📅 Calendrier des rotations à venir (" + nombreSemaines + " semaines)");
        System.out.println("---------------------------------------------------");

        int rotationsAjoutees = 0;
        int tentativeMax = nombreSemaines * 3;  // 🔐 Sécurité anti-boucle infinie

        while (rotationsAjoutees < nombreSemaines && tentativeMax > 0) {
            tentativeMax--;

            // Sauter si la date est déjà utilisée
            if (datesDejaPlanifiees.contains(dateRotation)) {
                dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
                continue;
            }

            Agent agent = trouveAgentDisponible(dateRotation);
            if (agent != null) {
                System.out.printf("✅ Semaine du %s: %s %s (%s)%n",
                        dateRotation,
                        agent.getPrenom(),
                        agent.getNom(),
                        agent.getEmail()
                );

                positionActuelle = (agentList.indexOf(agent) + 1) % agentList.size();
                rotationsAjoutees++;

            } else {
                System.out.printf("⚠️ Semaine du %s: Aucun agent disponible%n", dateRotation);
            }

            // Avancer la date quoiqu’il arrive
            dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
        }

        System.out.println("---------------------------------------------------");
    }

}




