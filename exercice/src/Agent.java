import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agent {
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
        this.indisponibiliteList = new ArrayList<>();}

    public int getIdAgent() {
        return idAgent;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // Methode pour a
    //vérifie si un agent (employé) est disponible ou non à une date donnée en fonction d'une liste d'indisponibilités.
    public boolean estDisponible(LocalDate date, int idAgent){
        for(Indisponibilite ind :indisponibiliteList ){
            if( ind.getId()==idAgent && ind.getDateIndisponible().equals(date)){
                return false;
            }

        }
        return true;

    }
    //La methode Pour l'indisponibilité :
    //Cette méthode permet de signaler une indisponibilité pour un agent spécifique,
    // puis de mettre à jour la planification automatique des rotations gérée par un
    // administrateur RH.
    public void ajoutIndisponible(String motif,LocalDate date, int idAgent){
        Indisponibilite ind = new Indisponibilite(idAgent, motif, date);
        indisponibiliteList.add(ind);

    }

}
