package Principale;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//La partie de MAmoutou
    //classe Agent
    public class Agent {
        //La partie de MAmoutou
        private int idAgent;
        private String nom;
        private String prenom;
        private String email;
        public AdministrateurRH admin;
        public List<Indisponibilite> indisponibiliteList;
        //constructeur

        public Agent(int idAgent, String nom, String prenom, String email) {
            this.idAgent = idAgent;
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.indisponibiliteList = new ArrayList<>();
        }

        //getter idAgent
        public int getIdAgent() {
            return idAgent;
        }
        //getter Nom
        public String getNom() {
            return nom;
        }
        //getter prénom
        public String getPrenom() {
            return prenom;
        }
        //getter email
        public String getEmail() {
            return email;
        }
        //setter idagent
        public void setIdAgent(int idAgent) {
            this.idAgent = idAgent;
        }
        //setter nom
        public void setNom(String nom) {
            this.nom = nom;
        }
        //setter prénom
        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }
        //setter email
        public void setEmail(String email) {
            this.email = email;
        }


        //Pour la methode de l'agent est indisponible :
        public boolean estDisponible(LocalDate date,int idAgent){
            for (Indisponibilite ind:indisponibiliteList){
                if (ind.getId() == idAgent && ind.getDateIndisponible().equals(date)){
                    return false;
                }
            }
            return true;
        }

        //La methode Pour l'indisponibilité :
        public void ajoutIndisponible(String motif, LocalDate date,int idAgent){
            Indisponibilite ind = new Indisponibilite(idAgent,motif,date);
            indisponibiliteList.add(ind);
        }


        //Pour methode pour signalerIndisponibilite des agents :
        public void signalerIndisponibilite(String motif, int idAgent, LocalDate date, AdministrateurRH admin) {
            for (Agent agent : admin.agentList) {
                if (agent.getIdAgent() == idAgent) {
                    agent.ajoutIndisponible(motif, date, idAgent);
                    System.out.println("✅ Indisponibilité ajoutée pour le " + date);

                    // Supprimer uniquement l’historique de cet agent à cette date précise
                    admin.historiqueList.removeIf(h ->
                            h.getIdAgent() == agent.getIdAgent() &&
                                    h.getDateRotation().equals(date)
                    );

                    // Replanifier uniquement à partir de cette date
                    admin.planifierRotationAutoDepuis(date.minusDays(1));
                    return;
                }
            }

            System.out.println("❌ Agent non trouvé.");
        }

    public void signalerIndisponibiliteEtReplanifier(String motif, int idAgent, LocalDate date) {
        // Vérifier si la date est dans le passé
        if (date.isBefore(LocalDate.now())) {
            System.out.println("❌ Erreur : Impossible de déclarer une indisponibilité pour une date passée !");
            return;
        }

        // Chercher l'agent concerné
        Agent agentCible = null;
        for (Agent agent : admin.agentList) {
            if (agent.getIdAgent() == idAgent) {
                agentCible = agent;
                break;
            }
        }

        if (agentCible == null) {
            System.out.println("❌ Erreur : Agent non trouvé.");
            return;
        }

        // Ajouter l’indisponibilité
        agentCible.ajoutIndisponible(motif, date, idAgent);
        System.out.println("✅ Indisponibilité ajoutée pour " + date);

        // Supprimer uniquement les historiques à partir de cette date
        admin.historiqueList.removeIf(h -> !h.getDateRotation().isBefore(date));

        // On remet la position à l’agent concerné (s’il existe encore)
        admin.positionActuelle = admin.agentList.indexOf(agentCible);
        if (admin.positionActuelle == -1) {
            admin.positionActuelle = 0;
        }

        // Replanifier uniquement les dates à venir
        admin.planifierRotationAutoDepuis(date);
    }



    // methode voir tour prochaine
        public Historique voirTourProchaine(int idAgent,AdministrateurRH admin) {
            for (Historique h: admin.historiqueList){
                if (h.getIdAgent() == idAgent && (h.getDateRotation().isAfter(LocalDate.now())||h.getDateRotation().isEqual(LocalDate.now())) ){
                    return h;
                }
            }
            return null;
        }
        // methode voir historique
        public void voirHistorique(int idAgent,AdministrateurRH admin) {
            for (Historique h : admin.historiqueList) {
                if ( ((h.getIdAgent() == idAgent && h.getIdAgentRemp()==0) &&  h.getDateRotation().isBefore(LocalDate.now()) ) ) {
                    System.out.println("tu as servie le petit dejeuner le : " + h.getDateRotation());
                } else if (((h.getIdAgent() == idAgent && h.getIdAgentRemp()==0) &&  h.getDateRotation().isAfter(LocalDate.now()) ) ) {
                    System.out.println("tu dois servir le petit dejeuner le : " + h.getDateRotation());
                } else if (h.getIdAgentRemp() == idAgent){
                    System.out.println("tu as remplacé un agent le  : " + h.getDateRotation());
                }
                System.out.println("-------------------------------------------------------------------");
            }
        }


        //Methode pour le rappel et envoie de l'email:

        public void rappelSiProcheTour(AdministrateurRH admin) {
            LocalDate dansDeuxJours = LocalDate.now().plusDays(2);
            LocalDate demain = LocalDate.now().plusDays(1);

            for (Historique h : admin.historiqueList) {
                if (h.getIdAgent() == this.idAgent && h.getDateRotation().equals(dansDeuxJours)) {
                    System.out.println("\nRappel : Vous êtes prévu pour le petit-déjeuner dans 2 jours (" + dansDeuxJours + ").");
                    return;
                } else if (h.getIdAgent() == this.idAgent && h.getDateRotation().equals(demain)) {
                    System.out.println("\nRappel : Vous êtes prévu pour le petit-déjeuner damain.");
                    return;
                }else if (h.getIdAgent() == this.idAgent && h.getDateRotation().equals(LocalDate.now())) {
                    System.out.println("\nRappel : Vous êtes prévu pour le petit-déjeuner Aujourd'hui");
                    return;
                }
            }

            // Aucun rappel
            System.out.println("Aucun rappel pour vous aujourd’hui.");
        }

    }


