package View;

import java.util.Scanner;

public class Accueil {
    Scanner sc = new Scanner(System.in);
    public Accueil(){}
    public void accueil(){
        System.out.println("                                                                                                                                                          \n" +
                "                                                                       bbbbbbbb                                                                          \n" +
                "        CCCCCCCCCCCCC                 lllllll lllllll                  b::::::b                      DDDDDDDDDDDDD                             jjjj      \n" +
                "     CCC::::::::::::C                 l:::::l l:::::l                  b::::::b                      D::::::::::::DDD                         j::::j     \n" +
                "   CC:::::::::::::::C                 l:::::l l:::::l                  b::::::b                      D:::::::::::::::DD                        jjjj      \n" +
                "  C:::::CCCCCCCC::::C                 l:::::l l:::::l                   b:::::b                      DDD:::::DDDDD:::::D                                 \n" +
                " C:::::C       CCCCCC   ooooooooooo    l::::l  l::::l   aaaaaaaaaaaaa   b:::::bbbbbbbbb                D:::::D    D:::::D     eeeeeeeeeeee   jjjjjjj     \n" +
                "C:::::C               oo:::::::::::oo  l::::l  l::::l   a::::::::::::a  b::::::::::::::bb              D:::::D     D:::::D  ee::::::::::::ee j:::::j     \n" +
                "C:::::C              o:::::::::::::::o l::::l  l::::l   aaaaaaaaa:::::a b::::::::::::::::b             D:::::D     D:::::D e::::::eeeee:::::eej::::j     \n" +
                "C:::::C              o:::::ooooo:::::o l::::l  l::::l            a::::a b:::::bbbbb:::::::b            D:::::D     D:::::De::::::e     e:::::ej::::j     \n" +
                "C:::::C              o::::o     o::::o l::::l  l::::l     aaaaaaa:::::a b:::::b    b::::::b            D:::::D     D:::::De:::::::eeeee::::::ej::::j     \n" +
                "C:::::C              o::::o     o::::o l::::l  l::::l   aa::::::::::::a b:::::b     b:::::b            D:::::D     D:::::De:::::::::::::::::e j::::j     \n" +
                "C:::::C              o::::o     o::::o l::::l  l::::l  a::::aaaa::::::a b:::::b     b:::::b            D:::::D     D:::::De::::::eeeeeeeeeee  j::::j     \n" +
                " C:::::C       CCCCCCo::::o     o::::o l::::l  l::::l a::::a    a:::::a b:::::b     b:::::b            D:::::D    D:::::D e:::::::e           j::::j     \n" +
                "  C:::::CCCCCCCC::::Co:::::ooooo:::::ol::::::ll::::::la::::a    a:::::a b:::::bbbbbb::::::b          DDD:::::DDDDD:::::D  e::::::::e          j::::j     \n" +
                "   CC:::::::::::::::Co:::::::::::::::ol::::::ll::::::la:::::aaaa::::::a b::::::::::::::::b           D:::::::::::::::DD    e::::::::eeeeeeee  j::::j     \n" +
                "     CCC::::::::::::C oo:::::::::::oo l::::::ll::::::l a::::::::::aa:::ab:::::::::::::::b            D::::::::::::DDD       ee:::::::::::::e  j::::j     \n" +
                "        CCCCCCCCCCCCC   ooooooooooo   llllllllllllllll  aaaaaaaaaa  aaaabbbbbbbbbbbbbbbb             DDDDDDDDDDDDD            eeeeeeeeeeeeee  j::::j     \n" +
                "                                                                                                                                              j::::j     \n" +
                "                                                                                                                                    jjjj      j::::j     \n" +
                "                                                                                                                                   j::::jj   j:::::j     \n" +
                "                                                                                                                                   j::::::jjj::::::j     \n" +
                "                                                                                                                                    jj::::::::::::j      \n" +
                "                                                                                                                                      jjj::::::jjj       \n" +
                "                                                                                                                                         jjjjjj          " );

        System.out.print("Appuyez sur entrer pour continuer ...");
        sc.nextLine();
    }

    public void espaceAdmin(){
        System.out.println("                                                                                               \n" +
                "                  ,------.                                         ,---.     ,--.          ,--.            \n" +
                "                  |  .---' ,---.  ,---.  ,--,--. ,---. ,---.      /  O  \\\\  ,-|  |,--,--,--.`--',--,--,     " +
                "                  |  `--, (  .-' | .-. |' ,-.  || .--'| .-. :    |  .-.  |' .-. ||        |,--.|      \\\\    \n" +
                "                  |  `---..-'  `)| '-' '\\\\ '-'  |\\\\ `--.\\\\   --.    |  | |  |\\\\ `-' ||  |  |  ||  ||  ||  |    \n" +
                "                  `------'`----' |  |-'  `--`--' `---' `----'    `--' `--' `---' `--`--`--'`--'`--''--'    \n" +
                "                                 `--'                                                                      ");
    }

    public void espaceAgent(){
        System.out.println("                                                                                          \n" +
                ",------.                                         ,---.                          ,--.      \n" +
                "|  .---' ,---.  ,---.  ,--,--. ,---. ,---.      /  O  \\  ,---.  ,---. ,--,--, ,-'  '-.    \n" +
                "|  `--, (  .-' | .-. |' ,-.  || .--'| .-. :    |  .-.  || .-. || .-. :|      \\'-.  .-'    \n" +
                "|  `---..-'  `)| '-' '\\ '-'  |\\ `--.\\   --.    |  | |  |' '-' '\\   --.|  ||  |  |  |      \n" +
                "`------'`----' |  |-'  `--`--' `---' `----'    `--' `--'.`-  /  `----'`--''--'  `--'      \n" +
                "               `--'                                     `---'                             ");
    }

}
