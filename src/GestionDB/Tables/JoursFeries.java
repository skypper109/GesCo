package GestionDB.Tables;

import GestionDB.Connexion;
import Principale.JourFerie;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JoursFeries {
    Connection con = new Connexion().connect();

    public JoursFeries(){}

    //Pour l'ajout des jours feriés
    public boolean addJourFerie(LocalDate date,String description){
        String insertJour = "INSERT INTO jourFeries(description,dateJourFerie) VALUES(?,?)";
        try(PreparedStatement prs = con.prepareStatement(insertJour)){
            prs.setString(1,description);
            prs.setDate(2, Date.valueOf(date));
            prs.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("Erreur lors de l'insertion de la date du jour ferié "+e.getMessage());
        }
        return false;
    }
    public boolean delDateJourFerie(LocalDate date){
        String deleteJour = "DELETE FRON jourFeries WHERE dateJourFerie=?";
        try (PreparedStatement pst = con.prepareStatement(deleteJour)){
            pst.setDate(1,Date.valueOf(date));
            int nbLigne = pst.executeUpdate();
            if (nbLigne>0){
                System.out.println("Le jour Ferié supprimer avec succès !!!");
                return true;
            }
        }catch (SQLException e){
            System.out.println("Erreur lors de suppression de la date du jour ferié !!! "+e.getMessage());
        }
        return false;
    }

    //Pour lister les jours feriés :
    public List<JourFerie> allJourFeries(){
        String afficheJour = "SELECT * FROM jourFeries";
        try(PreparedStatement pre = con.prepareStatement(afficheJour)){
            List<JourFerie> jouF = new ArrayList<>();
            var jourFerie = pre.executeQuery();
            while (jourFerie.next()){
                JourFerie jf = new JourFerie(
                        jourFerie.getDate("dateJourFerie").toLocalDate(),
                        jourFerie.getString("description")
                );
                jouF.add(jf);
            }
            return jouF;
        }catch (SQLException e){
            System.out.println("Erreur lors de l'appel des dates des jours feriés !!! "+e.getMessage());
        }
        return null;
    }

    //Verifier si c'est dans la table jour Ferié :
    public boolean estFerie(LocalDate date){
        String sql = "SELECT * FROM jourFeries WHERE dateJourFerie=?";
        try(PreparedStatement ptr = con.prepareStatement(sql)){
            ptr.setDate(1, Date.valueOf(date));
            var response = ptr.executeQuery();
            return response.next();
        }catch (SQLException e){
            System.out.println("Erreur lors de la recuperation des dates d'indiponibiliter !!!");
        }
        return false;
    }

}
