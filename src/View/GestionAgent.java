package View;

import GestionDB.Tables.Agents;
import GestionDB.Tables.Users;
import Principale.AdministrateurRH;
import Principale.Agent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class GestionAgent {
    private DayOfWeek date;
    public Agents tableAgent = new Agents();
    public Users tableUser = new Users();
    private final AdministrateurRH admin;
    private final Scanner sc= new Scanner(System.in);
    public EmailService service = new EmailService();
    public GestionAgent(AdministrateurRH admin){
        this.date = admin.getJourRotation();
        this.admin = admin;
        this.choix();
    }
    private int lireEntier() {
        int valeur = -1;
        boolean valide = false;

        while (!valide) {
            System.out.print("Choisissez une option : ");
            try {
                valeur = sc.nextInt();
                valide = true;
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Entrez un nombre valide.");
                sc.next(); // vider la mauvaise saisie
            }
        }

        return valeur;
    }
    private void choix() {
        int choix = -1;
        while (choix != 0) {
            System.out.println("\n=== MENU - GESTION DES AGENTS ===");
            System.out.println("1. ➕ Ajouter un agent");
            System.out.println("2. 📄 Lister les agents");
            System.out.println("3. 🗑️ Désactiver un agent");
            System.out.println("0. 🔙 Retour au menu principal");

            choix = lireEntier();

            switch (choix) {
                case 1 ->this.ajoutAgent();
                case 2 -> this.listAgent();
                case 3 -> this.retireAgent();
                case 0 -> System.out.println("Retour au menu principal...");
                default -> System.out.println("❌ Option invalide. Essayez encore.");
            }
        }
    }

    private void ajoutAgent() {
        System.out.print("🔹 Combien d’agents voulez-vous enregistrer ? : ");
        int nbAgent = sc.nextInt();
        sc.nextLine();
        int nbrAgent = admin.agentList.size();
        for (int i = 0; i < nbAgent; i++) {
            System.out.println("\n🧾 Agent #" + (i + 1));

            System.out.print("👉 Prénom : ");
            String prenom = sc.nextLine().trim();

            System.out.print("👉 Nom : ");
            String nom = sc.nextLine().trim();

            System.out.print("📧 Email : ");
            String email = sc.nextLine().trim().toLowerCase();

            // Vérification email:
            while (!admin.ajoutAgent(nom,prenom,email)){
                System.out.println("Ressaisi l'email !");
                System.out.print("📧 Email : ");
                email = sc.nextLine().trim().toLowerCase();
            }

            nbrAgent++;

            System.out.println("✅ Agent ajouté : " + prenom + " " + nom);
            service.envoyerEmail(email,"Creation de votre compte sur ANKA-DRAKAA","Votre compte a ete créer avec succes votre mot de passe est: agent1234 ");
        }


        System.out.println(nbAgent + (nbAgent > 1 ? " agents ont été ajoutés avec succès !" : " agent a été ajouté avec succès !"));
        System.out.println("Voulez vous faire une rotation Automatique en fonction de la date d'aujourd'hui (Oui/Non) ?");
        String reponse = sc.next();
        if (reponse.equals("oui")||reponse.equals("OUI") || reponse.equals("Oui")){
            admin.planifierRotationAuto();
        }
        this.pause();
    }

    private void listAgent(){
        System.out.println("\nListe des agents :");

        String leftAlignFormat = "| %-15s | %-15s | %-30s |%n";
        String ligne = "+-----------------+-----------------+--------------------------------+";

        System.out.println(ligne);
        System.out.format(leftAlignFormat, "Prénom", "Nom", "Email");
        System.out.println(ligne);
        List<Agent> listAgent = tableAgent.allAgent();
        for (Agent ag :listAgent ) {
            System.out.format(leftAlignFormat, ag.getPrenom(), ag.getNom(), ag.getEmail());
            System.out.println(ligne);
        }
        this.pause();
    }





    private void retireAgent() {
        System.out.print("📧 Entrez l’email de l’agent à désactiver : ");
        sc.nextLine();
        String email = sc.nextLine().trim().toLowerCase();
        admin.emailEstValide(email);
        if (admin.retireAgent(email)) {
            System.out.println("✅ Agent désactivé avec succès !");
        }else{
            System.out.println("❌ Aucun agent trouvé avec cet email.");
        }
        pause();
    }

    // ajoutAgent() et listAgent() sont déjà améliorées
    private void pause() {
        System.out.println("\n\n");
        System.out.print("🔁 Appuyez sur Entrée pour revenir au menu...");
        sc.nextLine();
        sc.nextLine();
    }

}

