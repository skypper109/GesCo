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
}
