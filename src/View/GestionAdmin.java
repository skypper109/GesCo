package View;

import Principale.AdministrateurRH;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionAdmin {
    private final Scanner sc = new Scanner(System.in);
    private final AdministrateurRH admin;
    EmailService service = new EmailService();
    public GestionAdmin(AdministrateurRH admin) {
        this.admin = admin;
        afficherMenu();
    }

    public void afficherMenu() {
        int choix = -1;
        while (choix != 0) {
            System.out.println("\n=== MENU - GESTION DES JOURS ===");
            System.out.println("1. 🔁 Changer le jour de rotation");
            System.out.println("2. 🗓️ Ajouter un jour férié");
            System.out.println("3. 📖 Voir les jours fériés enregistrés");
            System.out.println("0. 🔙 Retour au menu principal");

            choix = lireEntier("Choisissez une option : ");

            switch (choix) {
                case 1 -> changerJourRotation();
                case 2 -> ajouterJourFerie();
                case 3 -> afficherJoursFeries();
                case 0 -> System.out.println("Retour au menu principal...");
                default -> System.out.println("❌ Option invalide. Essayez encore.");
            }
        }
    }

    private void afficherHistorique() {
        admin.afficheHistorique();
        pause();
    }

    private void changerJourRotation() {
        System.out.println("\n🔁 Choisissez le nouveau jour de rotation :");
        int jour;
        do{
            System.out.println("1.) Tapez 1 pour Lundi");
            System.out.println("2.) Tapez 2 pour Mardi");
            System.out.println("3.) Tapez 3 pour Mercredi");
            System.out.println("4.) Tapez 4 pour Jeudi");
            System.out.println("5.) Tapez 5 pour Vendredi");
            jour =  lireEntier("Votre choix (1-5) : ");

        }while (jour >5 || jour ==0);

        DayOfWeek nouveauJour = DayOfWeek.of(jour);
        admin.setJourRotation(nouveauJour);
        System.out.println("✅ Nouveau jour de rotation défini : " + nouveauJour);
        admin.planifierRotationAuto();
        pause();
    }

    private void ajouterJourFerie() {
        try {
            System.out.print("Entrez la date du jour férié (AAAA-MM-JJ) : ");
            String dateStr = sc.next();
            LocalDate jourFerie = LocalDate.parse(dateStr);
            System.out.print("Entrez le motif pour la date du jour férié ("+jourFerie+") : ");
            sc.next();
            String desc = sc.nextLine();
            admin.ajoutJourFerie(jourFerie,desc);
            System.out.println("✅ Jour férié ajouté : " + jourFerie + " C'est le jour de : "+desc);
            admin.planifierRotationAutoDepuis(LocalDate.now());
        } catch (Exception e) {
            System.out.println("❌ Format de date invalide. Veuillez réessayer.");
        }
        pause();
    }

    private void afficherJoursFeries() {
        admin.afficherJoursFeries();
        pause();
    }

    public int lireEntier(String message) {
        int valeur = -1;
        boolean valide = false;

        while (!valide) {
            System.out.print(message);
            try {
                valeur = sc.nextInt();
                valide = true;
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Entrez un nombre valide.");
                sc.next(); // nettoyer la mauvaise saisie
            }
        }
        return valeur;
    }

    //
    private void pause() {
        System.out.println("\n\n");
        System.out.print("🔁 Appuyez sur Entrée pour revenir au menu...");
        sc.nextLine();
    }

}
