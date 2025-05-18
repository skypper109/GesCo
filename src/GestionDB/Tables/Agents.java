package GestionDB.Tables;

import GestionDB.Connexion;
import Principale.Agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agents {
    Connection con = new Connexion().connect();
    public Agents(){}
    //Pour l'insertion dans la table agents :
    public void ajoutAgent(String nom,String prenom,String email){
        String sql = "INSERT INTO agents(nom,prenom,email) VALUES(?,?,?)";
        try(PreparedStatement pst = con.prepareStatement(sql)){
            pst.setString(1,nom);
            pst.setString(2,prenom);
            pst.setString(3,email);
            pst.executeUpdate();
            System.out.println("Agent Ajouter avec succes !!!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion dans la table Agents!!!");
        }
    }
    //Pour lister l'ensemble des agents :
    public List<Agent> allAgent(){
        String commande = "SELECT * FROM agents";
        try(PreparedStatement stmt = con.prepareStatement(commande)){
            List<Agent> agents = new ArrayList<>();
            var agent = stmt.executeQuery();
            while (agent.next()){
                Agent ag = new Agent(
                        agent.getInt("idAgent"),
                        agent.getString("nom"),
                        agent.getString("prenom"),
                        agent.getString("email")
                );
                agents.add(ag);
            }
            return agents;
        }catch (SQLException e){
            System.out.println("Erreur dans l'affichage !!!");
        }
        return null;
    }
    //Pour lister les informations d'un agent specifique hein :

    public List<Agent> getAgent(int id){
        String sql = "SELECT * FROM agents WHERE idAgent=?";
        try(PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1,id);
            var agent = pst.executeQuery();
            List<Agent> agents = new ArrayList<>();
            while (agent.next()){
                Agent ag = new Agent(
                        agent.getInt("idAgent"),
                        agent.getString("nom"),
                        agent.getString("prenom"),
                        agent.getString("email")
                );
                agents.add(ag);
            }
            return agents;
        }catch (SQLException e){
            System.out.println("Erreur lors de recuperation de l'agent avec id : "+id);
        }
        return null;
    }
    //Pour la suppression d'un agent a travers sont email :
    public boolean delAgent(String email){
        String sql = "DELETE FROM agents WHERE email=?";
        try(PreparedStatement ptr = con.prepareStatement(sql)) {
            ptr.setString(1,email);
            int nbLigne = ptr.executeUpdate();

            if (nbLigne > 0) {
                System.out.println("✅ Agent supprimé avec succès !");
                return true;
            } else {
                System.out.println("❗ Aucun agent trouvé avec cet email.");
                return false;
            }
        }catch (SQLException e){
            System.out.println("Erreur lors de suppression dans la table agent !!! "+e.getMessage());
        }
        return false;
    }

    public boolean updateAgent(int idAgent,String nom,String prenom,String email){return true;}

    //Pour la methode estDisponible :
    public boolean estDisponible(LocalDate date,int idAgent){
        String sql = "SELECT * FROM indisponibilites WHERE idAgent=? AND dateIndisponible=?";
        try(PreparedStatement ptr = con.prepareStatement(sql)){
            ptr.setInt(1,idAgent);
            ptr.setDate(2,java.sql.Date.valueOf(date));
            var response = ptr.executeQuery();
            return !response.next();
        }catch (SQLException e){
            System.out.println("Erreur lors de la recuperation des dates d'indiponibiliter !!!");
        }
        return false;
    }

    //

}
