package View;

import Principale.AdministrateurRH;
import Principale.Agent;
import Principale.Historique;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EspaceAgent {
    private final AdministrateurRH admin;
    String email;
    Scanner sc = new Scanner(System.in);
    public EspaceAgent( AdministrateurRH admin,String email) {
        this.admin = admin;
        this.email = email;
        this.afficheAgent();
    }
    private void afficheAgent(){
        List<Agent> listeAgent = admin.agentList;
        List<Agent> connected = new ArrayList<>();
        int agentID = 0;

        for (Agent agent:listeAgent){
            if (agent.getEmail().equals(email)){
                agentID = agent.getIdAgent();
                connected.add(agent);
                Agent agentConnected = new Agent(agentID,agent.getNom(),agent.getPrenom(), agent.getEmail());

                System.out.println("****************************** Bienvenue Agent " + connected.getFirst().getPrenom()+" "+ connected.getFirst().getNom() + " !******************************");
                Historique tour = agentConnected.voirTourProchaine(agentID,admin);
                if (tour!=null){
                    System.out.println("Votre prochain tour est prevu pour : "+ tour.getDateRotation());
                    agentConnected.rappelSiProcheTour(admin);
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
            sc.nextLine();
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
                    } catch (Exception e) {
                        break;
                    }
                case 2:
                    System.out.println("Id de L'agent est : "+agentID);
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
