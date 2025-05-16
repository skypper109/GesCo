package View;

import Principale.AdministrateurRH;
import Principale.Agent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GestionAgent {
    private DayOfWeek date;
    private final AdministrateurRH admin;
    private final Scanner sc= new Scanner(System.in);
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
        int choix;

        do {
            System.out.println("\n========= 📁 GESTION DES AGENTS =========");
            System.out.println("1. ➕ Ajouter un agent");
            System.out.println("2. 📄 Lister les agents");
            System.out.println("3. ❌ Retirer un agent");
            System.out.println("0. 🔙 Retour à l’accueil");
            System.out.print("Faites un choix : ");
            choix = sc.nextInt();
            sc.nextLine(); // clean buffer

            switch (choix) {
                case 1 -> ajoutAgent();
                case 2 -> listAgent();
                case 3 -> retirerAgent();
                case 0 -> System.out.println("🔙 Retour à l'accueil...");
                default -> System.out.println("❌ Choix invalide. Réessayez.");
            }
        } while (choix != 0);
    }

    private void ajoutAgent() {
        System.out.print("🔹 Combien d’agents voulez-vous enregistrer ? : ");
        int nbAgent = sc.nextInt();
        sc.nextLine(); // vider le buffer

        List<Agent> agentsAjoutes = new ArrayList<>();

        for (int i = 0; i < nbAgent; i++) {
            System.out.println("\n🧾 Agent #" + (i + 1));

            System.out.print("👉 Prénom : ");
            String prenom = sc.nextLine().trim();

            System.out.print("👉 Nom : ");
            String nom = sc.nextLine().trim();

            System.out.print("📧 Email : ");
            String email = sc.nextLine().trim().toLowerCase();

            // Vérification email:
            while (!admin.emailEstValide(email) || admin.emailExisteDeja(email)){
                System.out.print("📧 Email : ");
                email = sc.nextLine().trim().toLowerCase();
            }

            int nouvelId = admin.agentList.isEmpty() ? 1 : admin.agentList.get(admin.agentList.size() - 1).getIdAgent() + 1;
            Agent agent = new Agent(nouvelId, nom, prenom, email);
            admin.ajoutAgent(nouvelId,nom,prenom,email);
            agentsAjoutes.add(agent);

            System.out.println("✅ Agent ajouté : " + prenom + " " + nom);
        }

        if (agentsAjoutes.isEmpty()) {
            System.out.println("\n❌ Aucun agent n’a été ajouté.");
        } else {
            System.out.println("\n✅ Total ajouté : " + agentsAjoutes.size() + " agent(s)");
        }

        // Optionnel : déclencher la rotation
        System.out.print("\n📌 Voulez-vous replanifier la rotation maintenant ? (oui/non) : ");
        String reponse = sc.nextLine().trim().toLowerCase();
        if (reponse.equals("oui") || reponse.equals("o")) {
            admin.planifierRotationAutoDepuis(LocalDate.now());
        }

        this.menu();// retour au menu
    }


    private void listAgent() {
        List<Agent> agents = admin.agentList;

        if (agents.isEmpty()) {
            System.out.println("\n❌ Aucun agent n’est encore enregistré.");
            this.menu();
            return;
        }

        System.out.println("\n📋 LISTE DES AGENTS ENREGISTRÉS");
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-20s |\n", "ID", "Nom Complet", "Email");
        System.out.println("---------------------------------------------------------------------");

        for (Agent a : agents) {
            String nomComplet = a.getPrenom() + " " + a.getNom();
            System.out.printf("| %-4d | %-20s | %-20s |\n", a.getIdAgent(), nomComplet, a.getEmail());
            System.out.println("---------------------------------------------------------------------");
        }

        this.menu(); // retour au menu
    }



    private void retirerAgent() {
        System.out.print("📧 Entrez l’email de l’agent à retirer : ");
        String email = sc.nextLine().trim().toLowerCase();
        admin.emailEstValide(email);
        if (admin.retireAgent(email)) {
            System.out.println("✅ Agent retiré avec succès !");
        } else {
            System.out.println("❌ Aucun agent trouvé avec cet email.");
        }

        this.menu(); // retour au menu
    }

    // ajoutAgent() et listAgent() sont déjà améliorées
    private void menu() {
        System.out.print("🔁 Appuyez sur Entrée pour revenir au menu...");
        sc.nextLine();
    }

}
