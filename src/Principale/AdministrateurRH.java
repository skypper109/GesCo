package Principale;

import
        java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class AdministrateurRH  extends User {
    private DayOfWeek jourRotation;
    private int positionActuelle;
    public List<Agent> agentList;


    public AdministrateurRH(DayOfWeek jourRotation) {
        super();
        this.jourRotation = jourRotation;
        this.positionActuelle = 0;
        this.agentList = new ArrayList<>();
    }

    //Ajout des identifiants de l'admin:
    public void ajoutAdmin() {
        User admin = new User("admin@gmail.com", "admin1234", "Admin");
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
        System.out.println("Cet email est invalide. Veuillez Saisir un email valide");
        return email != null && email.contains("@") && email.contains(".");
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

    public void ajoutAgent(int id, String nom, String prenom, String email) {
        if (emailEstValide(email) && !emailExisteDeja(email)) {
            Agent agent = new Agent(id, nom, prenom, email);
            agentList.add(agent);
            userList.add(new User(email, "agent1234", "Agent"));
        }
    }

    //Pour retirer un agent
    public boolean retireAgent(String email) {
        //Avec une fonction conditionnelle(Lambda) qui agit en supprimmant tout élément x qui vérifie la condition x ->
        return agentList.removeIf(a -> a.getEmail().equalsIgnoreCase(email)) &&
                userList.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
    }
}