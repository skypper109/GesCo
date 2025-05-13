import javax.swing.text.StyledEditorKit;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String BLEU = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {System.out.print("\033[H\033[2J");

        // Encadré supérieur
        Scanner scanner = new Scanner(System.in);
        String texte = "COLLABDEJ - Plateforme Collaborative Digitale";
        int longueur = texte.length();
        int largeurConsole = 150; // Ajustez selon votre console

        try {
            while (true) {
                for (int i = 0; i < longueur + largeurConsole; i++) {
                    System.out.print("\r"); // Retour au début de la ligne (corrigé: \r au lieu de \n)

                    // Construction de la portion visible
                    StringBuilder ligne = new StringBuilder();
                    for (int j = 0; j < largeurConsole; j++) {
                        int pos = i - j;
                        ligne.append((pos >= 0 && pos < longueur) ? texte.charAt(pos) : ' ');
                    }

                    System.out.print(ligne.toString());
                    Thread.sleep(100); // Vitesse de défilement (100ms)
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("\nAnimation arrêtée");
        }

        System.out.print("Entrez votre nom : ");
        String nom = scanner.nextLine();

    }}