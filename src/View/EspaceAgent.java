package View;

import GestionDB.Tables.Agents;
import Principale.AdministrateurRH;
import Principale.Agent;
import Principale.Historique;
import Principale.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EspaceAgent {
    private final AdministrateurRH admin;
    String email;
    Scanner sc = new Scanner(System.in);
    private User user ;
    public EspaceAgent( AdministrateurRH admin,String email,User user) {
        this.admin = admin;
        this.email = email;
        this.user = user;
        this.afficheAgent();
    }
    public void afficheAgent(){
        List<Agent> listeAgent = new Agents().allAgent();
        List<Agent> connected = new ArrayList<>();
        int agentID = 0;


        for (Agent agent:listeAgent){
            if (agent.getEmail().equals(email)){
                agentID = agent.getIdAgent();
                boolean inBoucle=false;
                while (user.getEmail().equals(email) && user.getPassword().equals("agent1234")){
                    //Changer le mot de passe obligatoirement :
                    if (!inBoucle){
                        System.out.println("-------------------[ Changer le mot de passe par defaut ]-----------------");
                    }
                    System.out.print("Saisi le nouveau Mot de passe : ");
                    String password = sc.nextLine();
                    if (password.isBlank() || password.length()<8){
                        System.out.println("C'est pas un bon mot de passe ca doit depacer 8 caracteres !!");
                        inBoucle=true;
                    }else {
                        user.setPassword(password,email);
                        break;
                    }

                }
                List<Agent> agentCon = new Agents().getAgent(agentID);
                if (agentCon.getFirst().getEtat()==0){
                    System.out.println("-------------------[ Desolé votre compte est en etat Desactivé Veillez contacter l'Admin ]-----------------");
                    pause();
                    break;
                }
                connected.add(agent);
                Agent agentConnected = new Agent(agentID,agent.getNom(),agent.getPrenom(), agent.getEmail());

                System.out.println("****************************** Bienvenue Agent " + connected.getFirst().getPrenom()+" "+ connected.getFirst().getNom() + " de CollabDej ! ******************************");
                Historique tour = agentConnected.voirTourProchaine(agentID,admin);
                if (tour!=null){
                    System.out.println("Votre prochain tour est prevu pour : "+ tour.getDateRotation());
                    agentConnected.rappelSiProcheTour(agentID);
                }else {
                    System.out.println("**********Vous n'avez pas de tour prevu pour l'instant.***********");
                }
                break;
            }
        }

        int ch;
        do {
            System.out.println("\n========= 👤 MENU =========");
            System.out.println("1. 🚫 Signaler une indisponibilité");
            System.out.println("2. 📅 Voir mes prochaines rotations");
            System.out.println("0. 🔙 Deconnexion");
            ch  = lireEntier("Faites un choix : ");
            switch (ch) {
                case 0:
                    System.out.println("🔙 Deconnecter...");
                    break;

                case 1:

                    System.out.print("📆 Entrez la date d'indisponibilité (aaaa-mm-jj) : ");
                    try {
                        LocalDate date = LocalDate.parse(sc.nextLine());
                        System.out.print("✍️ Motif : ");
                        String motif = sc.nextLine();
                        connected.getFirst().signalerIndisponibiliteEtReplanifier(motif,agentID, date,admin);
                        pause();
                        break;
                    } catch (Exception e) {
                        break;
                    }
                case 2:
                    connected.getFirst().voirHistorique(agentID,admin);
                    this.pause();
                    break;
                default:
                    System.out.println("❌ Choix invalide. Réessayez.\"");
            }
        } while (ch != 0);

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
    private void pause(){
        System.out.print("Appuyez sur Entrée pour continuer...");
        sc.nextLine(); // vider éventuelle ligne précédente
        sc.nextLine();
    }

}
