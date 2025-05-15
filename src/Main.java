import GestionDB.Connexion;
import GestionDB.Tables.Users;
import Principale.AdministrateurRH;
import Principale.User;
import View.EspaceAgent;
import View.GestionAdmin;
import View.GestionAgent;

import java.io.Console;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean quitt = true;
        Connexion con = new Connexion();
        con.initDB();
        Users users = new Users();
        boolean reconnect = false;
        Scanner sc = new Scanner(System.in);
        PrintStream ss = System.out;
        AdministrateurRH user = new AdministrateurRH(DayOfWeek.of(5));
        if (users.isEmptyUser()){
            user.ajoutAdmin();
        }
        ss.println("----------------- Bienvenue sur CollabDej !!!----------------");
        do {
            String username = "";
            int tentative = 3;
            do {
                ss.print("Entrer votre username : ");
                username = sc.nextLine();
                String password;
                Console console = System.console();

                if (console != null) {
                    char[] passArray = console.readPassword("Entrer votre mot de passe : ");
                    password = new String(passArray);
                } else {
                    ss.print("Entrer votre mot de passe : ");
                    password = sc.nextLine(); // fallback si console non dispo
                }
                //Verification de qui doit se connecter :
                if (user.authentifier(username,password)){
                    Users userConnect = new Users();
                    //Verifier qui est connecter si c'est admin ou une autre personne :
                    boolean connect = true;
                    String role = userConnect.list(username,password).getRole().equals("Agent") ? "Agent" : "Admin";
                    if (role.equals("Agent")){
                        new EspaceAgent(user,username);
                        tentative=0;
                    }else {
                        GestionAdmin admin = new GestionAdmin(user);
                        ss.println("--------------Bienvenue Admin du CollabDej !!!----------------------");
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
                                    tentative=3;
                                    break;

                                default:
                                    System.out.println("Choix invalide, veuillez réessayer.");
                            }
                        }while (connect);
                    }
                }
                else {
                    tentative--;
                    ss.println("Informations invalide.Il vous reste "+tentative+" tentative Veillez reassayez ");
                }
            }while (tentative > 0);

            ss.print("Voulez vous quitter l'application ? (Tapez Q/q ).... ");

            String quit = sc.nextLine();
            if (quit.equals("Q") || quit.equals("q")){
                quitt = false;
            }
        }while (quitt);
        ss.println("A tres bientot !!!");
    }
}