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

//methode voir prochaine tour
    public List<Historique> voirTourProchaine() {
        List<Historique> historiques = Historique.getHistorique();
        List<Historique> prochainesTours = new ArrayList<>();
        for (Historique h : historiques) {
            if (!h.isEstPasse()) { // Si le tour n’est pas passé
                prochainesTours.add(h);
            }
        }
        return prochainesTours;
    }

    //methode voir historique
    public List<Historique> voirHistorique() {
        List<Historique> historiques = Historique.getHistorique();
        List<Historique> historiquesPasse = new ArrayList<>();
        for (Historique h : historiques) {
            if (h.isEstPasse()) {
                historiquesPasse.add(h);
            }
        }
        return historiquesPasse;
    }


}


