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


        //Pour la methode de l'agent est disponible :
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
        public void signalerIndisponibilite(String motif,int idAgent, LocalDate date,AdministrateurRH admin){
            int i = 0;
            for (Agent agent: admin.agentList){
                if (agent.getIdAgent() == idAgent){
                    agent.ajoutIndisponible(motif,date,idAgent);
                    System.out.println("Indisponibilité ajoutée pour le " + date);

                    admin.historiqueList.removeIf(historique -> historique.getDateRotation().isAfter(date.minusDays(1)));
                    admin.positionActuelle = i;
                    admin.planifierRotationAuto();
                    break;
                }
                i++;
            }
        }

        // methode voir tour prochaine
        public Historique voirTourProchaine(int idAgent,AdministrateurRH admin) {
            for (Historique h: admin.historiqueList){
                if (h.getIdAgent() == idAgent && h.getDateRotation().isAfter(LocalDate.now())){
                    return h;
                }
            }
            return null;
        }
        // methode voir historique
    public void voirHistorique(int idAgent, AdministrateurRH admin) {
        for (Historique h : admin.historiqueList) {
            // Vérifie si l’agent est concerné (soit prévu, soit remplaçant)
            boolean estConcerne = (h.getIdAgent() == idAgent && h.getIdAgentRemp() == 0) || h.getIdAgentRemp() == idAgent;

            if (estConcerne) {
                if (h.getDateRotation().isBefore(LocalDate.now())) {
                    System.out.println("✅ Tu as servi le petit déjeuner le : " + h.getDateRotation());
                } else {
                    System.out.println("📅 Tu dois servir le petit déjeuner le : " + h.getDateRotation());
                }
                System.out.println("-------------------------------------------------------------------");
            }
            // sinon on n’affiche rien
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
                }
            }

            // Aucun rappel
            System.out.println("Aucun rappel pour vous aujourd’hui.");
        }
    }


