package View;

import Principale.AdministrateurRH;
import Principale.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Authentification {

    private final Scanner sc = new Scanner(System.in);

    public void start(AdministrateurRH admin) {
        System.out.println("\n🔐 Connexion à DEJ - Veuillez entrer vos identifiants");

        System.out.print("📧 Email : ");
        String email = sc.nextLine().trim();

        System.out.print("🔑 Mot de passe : ");
        String password = new String(System.console() != null
                ? System.console().readPassword()
                : sc.nextLine().toCharArray());


        if (admin.authentifier(email, password)) {
            System.out.println("✅ Connexion réussie en tant que " + User.getRole());
            if (User.getRole().equals("Admin")) {
                new Accueil().espaceAdmin();
                int choix;
                System.out.println("\n=========[ 🟢 ESPACE ADMIN - GESTION DES AGENTS ]=========");
                do {
                    try {
                        System.out.println("1. 👤 Gestion des Agents");
                        System.out.println("2. 📋 Rotation et Autres Gestions ");
                        System.out.println("0. ❌ Se deconnecter");
                        System.out.print("Votre choix : ");
                        choix = sc.nextInt();
                        sc.nextLine();
                        if (choix==1){
                            new GestionAgent(admin);
                        } else if (choix == 2) {
                            new GestionAdmin(admin);
                        }
                    }catch (InputMismatchException e){
                        System.out.println("Saisi Invalide!!!!!");
                        choix = -1;
                    }
                }while (choix != 0);
                System.out.println("Deconnexion...");
            }
            else if (User.getRole().equals("Agent")) {
                new Accueil().espaceAgent();
                new EspaceAgent(admin, email);
            }
            else {System.out.println("❌ Rôle non reconnu. Accès refusé.");}

        } else {
            System.out.println("❌ Identifiants incorrects. Réessayez.");
        }
    }
}
