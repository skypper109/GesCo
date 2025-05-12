package View;

import Principale.AdministrateurRH;
import Principale.Agent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.InputMismatchException;
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
        int choix = -1;
        while (choix != 0) {
            System.out.println("\n=== MENU - GESTION DES AGENTS ===");
            System.out.println("1. ➕ Ajouter un agent");
            System.out.println("2. 📄 Lister les agents");
            System.out.println("3. 🗑️ Supprimer un agent");
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

    private void pause(){
        System.out.print("Appuyez sur Entrée pour continuer...");
        sc.nextLine(); // vider éventuelle ligne précédente
        sc.nextLine();
    }
    private void ajoutAgent() {
        System.out.print("Combien d’agents voulez-vous enregistrer ? : ");
        int nbAgent = sc.nextInt();
        int nbrAgent = admin.agentList.size();
        for (int i = 0; i < nbAgent; i++) {
            if (i!=0){
                System.out.println("--------------------------------------------------------");
            }
            System.out.print("Saisir le nom de l'agent " + (i + 1) + " : ");
            sc.nextLine();
            String nom = sc.nextLine();
            System.out.print("Saisir le prenom de l'agent " + nom + " : ");
            String prenom = sc.nextLine();
            String email;
            do{
                System.out.print("Saisir l'email de l'agent " + nom +" " + prenom  + " : ");
                email = sc.next();
            }while (!admin.emailEstValide(email) || admin.emailExisteDeja(email));
            nbrAgent++;
            admin.ajoutAgent(nbrAgent,nom,prenom,email);
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
        System.out.printf("%-25s | %-20s | %-25s\n", " Prenom de l'agent", " Nom de L'agent", " Email de l'Agent");
        System.out.println("____________________________________________________________________");
        for (Agent ag : admin.agentList) {
            System.out.printf("%-25s | %-20s | %-25s\n",ag.getPrenom(),ag.getNom(), ag.getEmail());
            System.out.println("____________________________________________________________________");
        }
        this.pause();
    }


    private void retireAgent() {
        System.out.print("Entrez l'email de l'agent à retirer : ");
        String valeur = sc.next();
        if (admin.retireAgent(valeur)){
            System.out.println("Agent Supprimer avec succès !!");
        }else{
            System.out.println("Email saisi est soit incorrect soit inexistant dans la base. Veillez Reassayez plus tard!");
        }
        pause();
    }

}
