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
}




