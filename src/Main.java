import GestionDB.Connexion;
import GestionDB.Tables.AdministrateurRHs;
import GestionDB.Tables.Agents;
import GestionDB.Tables.Historiques;
import Principale.AdministrateurRH;
import Principale.Agent;
import Principale.Historique;
import Principale.User;
import View.*;

import java.io.Console;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 1. Affichage d'accueil avec logo DEJ
        new Accueil().accueil();
        //new Connexion().initDB();
        /**List<Historique> listHistorique = new Historiques().allHistorique();
        if (listHistorique!=null){

            listHistorique = listHistorique.reversed();
            LocalDate dateHist = listHistorique.getFirst().getDateRotation();
            LocalDate dansDeuxJours = LocalDate.now().plusDays(2);
            LocalDate demain = LocalDate.now().plusDays(1);
            int agentID = listHistorique.getFirst().getIdAgent();
            if (dateHist.equals(dansDeuxJours) ){
                List<Agent> agentList = new ArrayList<>();
                agentList.add(new Agents().getAgentBy(agentID));

            } else if (dateHist.equals(demain)) {

            } else if (dateHist.equals(LocalDate.now())) {

            }
        }**/

        // 2. Instanciation de l'administrateur RH
        AdministrateurRH admin = new AdministrateurRH(DayOfWeek.of(5));
        if (new AdministrateurRHs().isEmptyAdminRH()){
            admin.ajoutAdmin();
        }

        // 3. Menu principal de navigation
        boolean quitter = false;
        while (!quitter) {
            System.out.println("\n========= 🟢 MENU PRINCIPAL =========");
            System.out.println("1. 👤 Se connecter (Admin/Agent)");
            System.out.println("2. 📋 Voir tous les agents enregistrés");
            System.out.println("0. ❌ Quitter l'application");
            System.out.print("Votre choix : ");

            String choix = sc.nextLine().trim();

            switch (choix) {
                case "1" -> new Authentification().start(admin);
                case "2" -> afficherAgents(admin);
                case "0" -> {
                    quitter = true;
                    System.out.println("\n👋 Merci d’avoir utilisé DEJ ! À bientôt.");
                }
                default -> System.out.println("❌ Choix invalide. Veuillez réessayer.");
            }
        }
    }


    private static void afficherAgents(AdministrateurRH admin) {

        System.out.println("\n👥 Agents enregistrés :");
        if (admin.tableAgent.allAgent()==null) {
            System.out.println("Aucun agent trouvé.");
            return;
        }
        int i = 0;
        for (Agent ag : admin.tableAgent.allAgent()) {
            i++;
            System.out.println(" ->  "+i+"  | " + ag.getPrenom() + " " + ag.getNom() + " | " + ag.getEmail());
            System.out.println("_______________________________________________________________________");
        }
    }
}