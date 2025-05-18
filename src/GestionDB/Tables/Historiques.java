package GestionDB.Tables;

import GestionDB.Connexion;
import Principale.Historique;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Historiques extends Connexion {
    public Historiques(){}
    Connection con = new Connexion().connect();
    //Pour l'insertion dans la table Historique :
    public void addHistorique(int idAgent, LocalDate dateRotation, String statut, String motif, int idAgentRempl){
        String sql = "INSERT INTO historiques(dateRotation,agentPrevu,statut,motif,agentRemplacant) VALUES(?,?,?,?,?)";
        try(PreparedStatement ptr=con.prepareStatement(sql)){
            ptr.setDate(1, Date.valueOf(dateRotation));
            ptr.setInt(2,idAgent);
            ptr.setString(3,statut);
            ptr.setString(4,motif);
            ptr.setInt(5,idAgentRempl);
            ptr.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erreur lors de l'insertion dans la table historique !!!");
        }
    }
    //Pour affichage de tout l'historique :
    public List<Historique> allHistorique(){
        String sql = "SELECT * FROM historiques";
        try(PreparedStatement ptr=con.prepareStatement(sql)){
            var historique = ptr.executeQuery();
            List<Historique> historiqueList = new ArrayList<>();
            while (historique.next()){
                Historique hist = new Historique(
                        historique.getInt("agentPrevu"),
                        historique.getDate("dateRotation").toLocalDate(),
                        historique.getString("statut"),
                        historique.getString("motif"),
                        historique.getInt("agentRemplacant")
                );
                historiqueList.add(hist);
            }
            return historiqueList;
        }catch (SQLException e){
            System.out.println("Erreur lors des recuperation des elements dans la table historique !!!");
        }
        return null;
    }

    //Pour recuperer uniquement les historiques qui ne concerne que les
    public List<Historique> getHistoriqueById(int id){
        String sql = "SELECT * FROM historiques WHERE agentPrevu=? or agentRemplacant=?";

        try(PreparedStatement ptr=con.prepareStatement(sql)){
            ptr.setInt(1,id);
            ptr.setInt(2,id);
            var historique = ptr.executeQuery();
            List<Historique> historiqueList = new ArrayList<>();
            while (historique.next()){
                Historique hist = new Historique(
                        historique.getInt("agentPrevu"),
                        historique.getDate("dateRotation").toLocalDate(),
                        historique.getString("statut"),
                        historique.getString("motif"),
                        historique.getInt("agentRemplacant")
                );
                historiqueList.add(hist);
            }
            return historiqueList;
        }catch (SQLException e){
            System.out.println("Erreur lors des recuperation des elements dans la table historique!!!");
        }
        return null;

    }

    //Pour la suppression des elements en fonction d'une date precises :
    public void deleteHistoriqueByDate(LocalDate date){
        Date date1 = Date.valueOf(date);
        String commande = "DELETE FROM historiques WHERE dateRotation > ?";

        try(PreparedStatement ptr=con.prepareStatement(commande)){
            ptr.setDate(1,date1);
            ptr.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erreur lors des recuperation des elements dans la table historique!!!");
        }
    }

}
