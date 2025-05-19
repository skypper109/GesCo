package GestionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion {
    private static final String url="jdbc:sqlite:collabdej.db";
    public Connexion(){
    }
    public Connection connect(){
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Erreur de connexion a la base de donnée "+e.getMessage());
            return null;
        }
    }
    public void initDB(){
        //Pour la suppression de l"enselbles des tables :
       /* String sql = "DROP TABLE IF EXISTS users";
        String sql2 = "DROP TABLE IF EXISTS administrateurRHs";
        String sql3 = "DROP TABLE IF EXISTS agents";
        String dropTableHis = "DROP TABLE IF EXISTS historiques";
        String dropTableJF = "DROP TABLE IF EXISTS jourFeries";
        String dropTableInd = "DROP TABLE IF EXISTS indisponibilites";
        */
        String tableUser= """
                CREATE TABLE IF NOT EXISTS users (
                    idUser INTEGER PRIMARY KEY AUTOINCREMENT,
                    email TEXT NOT NULL,
                    password TEXT NOT NULL DEFAULT "agent1234",
                    role TEXT CHECK(role IN ('Admin','Agent')) NOT NULL
                );
                """;
        String tableAgent = """
                CREATE TABLE IF NOT EXISTS agents(
                    idAgent INTEGER PRIMARY KEY AUTOINCREMENT,
                    nom TEXT NOT NULL,
                    prenom TEXT NOT NULL,
                    etat INTEGER NOT NULL DEFAULT 1,
                    email TEXT UNIQUE
                );
                """;
        String tableAdministrateurRH = """
                CREATE TABLE IF NOT EXISTS administrateurRHs(
                    idAdmin INTEGER PRIMARY KEY AUTOINCREMENT,
                    nom TEXT NOT NULL,
                    prenom TEXT NOT NULL,
                    email TEXT UNIQUE DEFAULT "<admin@gmail.com>",
                    password TEXT NOT NULL DEFAULT "<admin1234>",
                    jourRotation INTEGER DEFAULT 5
                );
                """;
        String tableHistorique = """
                CREATE TABLE IF NOT EXISTS historiques(
                    idHistorique INTEGER PRIMARY KEY AUTOINCREMENT,
                    dateRotation DATE,
                    agentPrevu INTEGER,
                    statut TEXT,
                    motif TEXT,
                    agentRemplacant INTEGER DEFAULT 0,
                    FOREIGN KEY (agentPrevu) REFERENCES agents(idAgent)
                );
                """;
        String tableFerier = """
                CREATE TABLE IF NOT EXISTS jourFeries(
                    idJF INTEGER PRIMARY KEY AUTOINCREMENT,
                    description TEXT,
                    dateJourFerie DATE
                );
                """;
        String tableIndispo = """
                CREATE TABLE IF NOT EXISTS indisponibilites(
                    idIndisponibilite INTEGER PRIMARY KEY AUTOINCREMENT,
                    idAgent INTEGER,
                    motif TEXT,
                    dateIndisponible DATE,
                    FOREIGN KEY (idAgent) REFERENCES agents(idAgent)
                );
                """;
        try(Connection con = connect(); Statement stmt = con.createStatement()) {
            stmt.execute(tableAgent);
            stmt.execute(tableAdministrateurRH);
            stmt.execute(tableUser);
            stmt.execute(tableFerier);
            stmt.execute(tableHistorique);
            stmt.execute(tableIndispo);
            System.out.println("Les Tables sont créées avec succès !!!");
        } catch (Exception e) {
            System.out.println("Erreur lors de la creation des tables : "+e.getMessage());
        }
    }
}
