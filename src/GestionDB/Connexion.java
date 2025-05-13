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
        String tableUserDrop= """
                DROP TABLE IF EXISTS users ;
                """;
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
                    email TEXT UNIQUE
                );
                """;
        String tableAdministrateurRH = """
                CREATE TABLE IF NOT EXISTS administrateurRHs(
                    idAdmin INTEGER PRIMARY KEY AUTOINCREMENT,
                    jourRotation INTEGER
                );
                """;
        String tableHistorique = """
                CREATE TABLE IF NOT EXISTS historiques(
                    idHistorique INTEGER PRIMARY KEY AUTOINCREMENT,
                    dateRotation DATE,
                    agentPrevu INTEGER,
                    statut TEXT,
                    motif TEXT,
                    agentRemplacant INTEGER,
                    FOREIGN KEY (agentPrevu) REFERENCES agents(idAgent),
                    FOREIGN KEY (agentRemplacant) REFERENCES agents(idAgent)
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
