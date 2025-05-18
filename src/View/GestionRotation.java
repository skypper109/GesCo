package View;

import Principale.AdministrateurRH;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionRotation {
    Scanner sc = new Scanner(System.in);
    AdministrateurRH admin;
    public GestionRotation(AdministrateurRH admin){
        this.admin = admin;
        this.menuRotation();
    }


    // Menu simple pour tester tout le système
    private void menuRotation() {
        int choix;
        do {
            System.out.println("\n========= ⚙️ MENU ROTATION  =========");
            System.out.println("1. 🚫 Signaler une indisponibilité et replanifier");
            System.out.println("2. 🔁 Lancer une rotation automatique maintenant");
            System.out.println("3. 📌 Lancer une nouvelle rotation");
            System.out.println("4. 📖 Afficher les rotations a venir");
            System.out.println("5. 📜 Afficher l’historique");
            System.out.println("0. 🔙 Quitter le menu");
            choix = lireEntier("Faites un choix : ");

            switch (choix) {
                case 1 -> {
                    System.out.print("🆔 ID de l’agent : ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("📅 Date d’indisponibilité (aaaa-mm-jj) : ");
                    LocalDate date = LocalDate.parse(sc.nextLine());
                    System.out.print("✍️ Motif : ");
                    String motif = sc.nextLine();
                    admin.signalerIndisponibiliteAvecRotation(motif, id, date);
                }
                case 2 -> admin.planifierRotationAuto();
                case 3 -> this.planifierRotation();
                case 4 -> this.afficherRotationAvenir();
                case 5 -> admin.afficheHistorique();
                case 0 -> System.out.println("🔙 Retour...");
                default -> System.out.println("❌ Choix invalide.");
            }

        } while (choix != 0);
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

    private void afficherRotationAvenir(){
        int saisi = lireEntier("Entrez le nombre de Semaines à venir dont vous voulez voir : ");
        //admin.afficherRotationAvenir(saisi);
        this.pause();
    }

    //
    private void pause() {
        System.out.println("\n\n");
        System.out.print("🔁 Appuyez sur Entrée pour revenir au menu...");
        sc.nextLine();
    }


    private void planifierRotation() {
        System.out.print("Entrez la date a laquelle commence la prochaine rotation (AAAA-MM-JJ) : ");
        String dat =sc.next();
        LocalDate date = LocalDate.parse(dat);
        admin.planifierRotationAutoDepuis(date);
        pause();
    }
}
