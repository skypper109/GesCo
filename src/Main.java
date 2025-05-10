import Principale.AdministrateurRH;
import View.GestionAdmin;
import View.GestionAgent;

import java.io.PrintStream;
import java.time.DayOfWeek;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean quitt = true;
        boolean reconnect = false;
        Scanner sc = new Scanner(System.in);
        PrintStream ss = System.out;
        AdministrateurRH user = new AdministrateurRH(DayOfWeek.of(5));
        user.ajoutAdmin();
        do {
            String username = "";
            String password = "";
            ss.print("Entrer votre username : ");
            username = sc.nextLine();
            ss.print("Entrer votre mot de passe : ");
            password = sc.nextLine();
            //Verification de qui doit se connecter :
            if (user.authentifier(username, password)) {
                //Verifier qui est connecter si c'est admin ou une autre personne :
                boolean connect = true;
                ss.println();
                if (user.getRole() == "Agent") {
                    ss.println("Agent connecté est : " + user.getEmail());
                } else {
                    GestionAdmin admin = new GestionAdmin(user);
                    ss.println("--------------Bienvenue Admin du systeme !!!----------------------");
                    do {
                        System.out.println("1.) Tapez 1 pour la gestion des agents");
                        System.out.println("2.) Tapez 2 pour la gestion de rotation et de jour");
                        System.out.println("0.) Tapez 0 pour se deconnecter");
                        int choix = admin.lireEntier("\nFaites un choix : ");

                        switch (choix) {
                            case 1:
                                new GestionAgent(user);
                                break;

                            case 2:
                                admin.afficherMenu();
                                break;

                            case 0:
                                connect = false;
                                ss.println("Deconnecter...");
                                break;

                            default:
                                System.out.println("Choix invalide, veuillez réessayer.");
                        }
                    } while (connect);
                }
                reconnect = true;
            } else {
                ss.println("Informations invalide. Veillez reassayez");
            }

            ss.print("Voulez vous quitter l'application ? (Tapez Q/q ).... ");
            String quit = sc.nextLine();
            if (quit.equals("Q") || quit.equals("q")) {
                quitt = false;
            }
        } while (quitt);
        ss.println("A tres bientot !!!");
    }
}