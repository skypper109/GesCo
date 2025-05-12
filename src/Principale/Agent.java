package Principale;

import java.time.LocalDate;
import java.util.List;

//La partie de MAmoutou
    //classe Agent
    public class Agent {
        //La partie de MAmoutou
        private int idAgent;
        private String nom;
        private String prenom;
        private String email;

        public List<Indisponibilite> indisponibiliteList;
        //constructeur

        public Agent(int idAgent, String nom, String prenom, String email) {
            this.idAgent = idAgent;
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
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
        public boolean estDisponible(LocalDate date){
            return !indisponibiliteList.stream().filter(indisponibilite -> indisponibilite.getDateIndisponible().isEqual(date)).isParallel();
        }

        //La methode Pour l'indisponibilité :
        public boolean ajoutIndisponible(String motif,LocalDate date){
            if (!indisponibiliteList.stream().filter(indisponibilite -> indisponibilite.getDateIndisponible().isEqual(date)).isParallel()){
                Indisponibilite indispo = new Indisponibilite(motif,date);
                indisponibiliteList.add(indispo);
                return true;
            }
            return false;
        }

        public void voirTourProchaine(AdministrateurRH admin) {

            boolean trouve = false;

        }



    }


