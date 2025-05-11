package View;

import Principale.AdministrateurRH;
import Principale.Agent;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public void afficheAgent(){
        List<Agent> listeAgent = admin.agentList;
        List<Agent> connected = new ArrayList<>();

        for (Agent agent:listeAgent){
            if (agent.getEmail().equals(email)){
                connected.add(agent);
                Agent agentConnected = new Agent(agent.getIdAgent(),agent.getNom(),agent.getPrenom(), agent.getEmail());

                System.out.println("******************************\nBienvenue Agent " + connected.getFirst().getPrenom()+" "+ connected.getFirst().getNom() + " !******************************");
                agentConnected.voirTourProchaine(admin);
                agentConnected.rappelSiProcheTour(admin);
                break;
            }
        }

        int ch;
        do {

            System.out.println("1.) Signaler une indisponibilité");
            System.out.println("2.) Voir Historiques");
            System.out.println("0.) Se deconnecter");
            System.out.print("\nFaites un choix : ");
            ch = sc.nextInt();

            switch (ch) {
                case 0:
                    System.out.println("Deconnecter...");
                    break;

                case 1:
                    System.out.print("Entrez la date d'indisponibilité (aaaa-mm-jj) : ");
                    String dateStr = sc.next();
                    LocalDate indispo = LocalDate.parse(dateStr);
                    System.out.print("Entrez le motif de ton indisponibilité : ");
                    sc.next();
                    String motif = sc.nextLine();
                    connected.getFirst().signalerIndisponibilite(motif,connected.getFirst().getIdAgent(), indispo,admin);
                    System.out.println("Indisponibilité enregistrée avec succès.");
                    this.pause();
                    break;
                case 2:
                    admin.afficheHistorique();
                    this.pause();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        } while (ch != 0);

    }
    public void pause(){
        System.out.print("Appuyez sur Entrée pour continuer...");
        sc.nextLine(); // vider éventuelle ligne précédente
        sc.nextLine();
    }

}
