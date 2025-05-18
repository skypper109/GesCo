import GestionDB.Connexion;
import GestionDB.Tables.AdministrateurRHs;
import Principale.AdministrateurRH;
import Principale.Agent;
import Principale.User;
import View.*;

import java.io.Console;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 1. Affichage d'accueil avec logo DEJ
        new Accueil().accueil();
        //new Connexion().initDB();

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
        if (admin.agentList.isEmpty()) {
            System.out.println("Aucun agent trouvé.");
            return;
        }
        for (Agent ag : admin.agentList) {
            System.out.println(" - " + ag.getPrenom() + " " + ag.getNom() + " | " + ag.getEmail());
        }
    }
}