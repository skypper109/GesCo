package View;

import Principale.AdministrateurRH;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionAdmin {
    private final Scanner sc = new Scanner(System.in);
    private final AdministrateurRH admin;

    public GestionAdmin(AdministrateurRH admin) {
        this.admin = admin;
    }

    public void afficherMenu() {
        int choix = -1;
        while (choix != 0) {
            System.out.println("\n=== MENU - GESTION DE ROTATION & JOURS ===");
            System.out.println("1. 📅 Afficher les agents avec leurs jours de rotation");
            System.out.println("2. 🔁 Changer le jour de rotation");
            System.out.println("3. 📌 Lancer une nouvelle rotation");
            System.out.println("4. 🗓️ Ajouter un jour férié");
            System.out.println("5. 📖 Voir les jours fériés enregistrés");
            System.out.println("0. 🔙 Retour au menu principal");

            choix = lireEntier("Choisissez une option : ");

            switch (choix) {
                case 1 -> afficherHistorique();
                case 2 -> changerJourRotation();
                case 3 -> planifierRotation();
                case 4 -> ajouterJourFerie();
                case 5 -> afficherJoursFeries();
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
        for (int i = 1; i <= 5; i++) {
            System.out.println(i + ". " + DayOfWeek.of(i));
        }

        int jour = lireEntier("Votre choix (1-5) : ");
        if (jour < 1 || jour > 5) {
            System.out.println("❌ Jour invalide.");
            return;
        }

        DayOfWeek nouveauJour = DayOfWeek.of(jour);
        admin.setJourRotation(nouveauJour);
        System.out.println("✅ Nouveau jour de rotation défini : " + nouveauJour);
        admin.planifieRotation(LocalDate.now());
        pause();
    }

    private void planifierRotation() {
        String dat =sc.next();
        LocalDate date = LocalDate.parse(dat);
        admin.planifieRotation(date);
        pause();
    }

    private void ajouterJourFerie() {
        System.out.print("Entrez la date du jour férié (AAAA-MM-JJ) : ");
        String dateStr = sc.next();
        try {
            LocalDate jourFerie = LocalDate.parse(dateStr);
            admin.ajoutJourFerie(jourFerie,null)
            ;
            System.out.println("✅ Jour férié ajouté : " + jourFerie);
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

    private void pause() {
        System.out.print("Appuyez sur Entrée pour continuer...");
        sc.nextLine(); // vider le buffer
        sc.nextLine();
    }

}
