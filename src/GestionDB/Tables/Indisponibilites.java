package GestionDB.Tables;

import GestionDB.Connexion;
import Principale.Indisponibilite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Indisponibilites {
    Connection con = new Connexion().connect();
    public Indisponibilites(){}
    //Pour la insertion des nouvels elements
    public void addIndisponible(int idAgent, String motif, LocalDate dateIndisponible){
        String sql = "INSERT INTO indisponibilites(idAgent,motif,dateIndisponible) VALUES(?,?,?)";
        try(PreparedStatement ptm = con.prepareStatement(sql)) {
            ptm.setInt(1,idAgent);
            ptm.setString(2,motif);
            ptm.setDate(3, Date.valueOf(dateIndisponible));
            ptm.executeUpdate();
            System.out.println("Declaration d'indisponibilité effectué avec succès !!! ");
        }catch (SQLException e){
            System.out.println("Erreur lors de declaration d'indisponibiliter !!!"+e.getMessage());
        }
    }

    //Pour la liste des dates declarer dans la table indisponibilité :
    public List<Indisponibilite> getDateIndiponibilite(int idAgent){
        String sql = "SELECT * FROM indisponibilites WHERE idAgent=?";
        try(PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1,idAgent);
            var requete = pst.executeQuery();
            List<Indisponibilite> indisponibiliteList = new ArrayList<>();
            while (requete.next()){
                Indisponibilite indispoElement = new Indisponibilite(
                        requete.getInt("idAgent"),
                        requete.getString("motif"),
                        requete.getDate("dateIndiponible").toLocalDate()
                );
                indisponibiliteList.add(indispoElement);
            }
        }catch (SQLException e){
            System.out.println("Erreur lors de l'appel des dates d'indiponibiliter !!! "+e.getMessage());
        }
        return null;
    }

    //La liste des elements de la table :
    public List<Indisponibilite> allIndisponibilite(){
        String sql = "SELECT * FROM indisponibilites";
        try (PreparedStatement ptr = con.prepareStatement(sql)){
            var reponse = ptr.executeQuery();
            List<Indisponibilite> indispo = new ArrayList<>();
            while (reponse.next()){
                Indisponibilite indis = new Indisponibilite(
                        reponse.getInt("idAgent"),
                        reponse.getString("motif"),
                        reponse.getDate("dateIndisponible").toLocalDate()
                );
                indispo.add(indis);
            }
            return indispo;
        }catch (Exception e){
            System.out.println("Erreur lors de la recuperation des dates declarer indisponible");
        }
        return null;
    }

}
