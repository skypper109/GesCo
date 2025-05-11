import Principale.AdministrateurRH;
import Principale.User;
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
            int tentative = 3;
            do {
                ss.print("Entrer votre username : ");
                username = sc.nextLine();
                ss.print("Entrer votre mot de passe : ");
                password = sc.nextLine();
                //Verification de qui doit se connecter :
                if (user.authentifier(username,password)){
                    //Verifier qui est connecter si c'est admin ou une autre personne :
                    boolean connect = true;
                    String role = "";
                    for (User us: user.userList){
                        if (us.getEmail().equals(username)&&us.getPassword().equals(password)){
                            role = us.getRole();
                            break;
                        }
                    }

                    if (role.equals("Agent")){
                        tentative=0;
                    }else {
                        GestionAdmin admin = new GestionAdmin(user);
                        ss.println("--------------Bienvenue Admin du systeme !!!----------------------");
                        do {
                            System.out.println("1.) Tapez 1 pour la gestion des agents");
                            int choix = admin.lireEntier("\nFaites un choix : ");

                            switch (choix) {
                                case 1:
                                    new GestionAgent(user);
                                    break;
                                default:
                                    ss.println("Choix invalide, veuillez réessayer.");
                            }
                        }while (connect);
                    }
                }
                else {
                    tentative--;
                    ss.println("Informations invalide.Il vous reste "+tentative+" Veillez reassayez ");
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