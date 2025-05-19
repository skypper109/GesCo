package GestionDB.Tables;

import GestionDB.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class AdministrateurRHs {
    Connection con = new Connexion().connect();
    public AdministrateurRHs(){}

    //Pour l'ajout d'un administrateur avec son nom,prenom,email,password et jourRotation en int
    public void addAdminRH(String nom,String prenom,String email,String password,int jourRotation){
        String sql = "INSERT INTO administrateurRHs(nom,prenom,email,password,jourRotation) VALUES(?,?,?,?,?)";
        try( PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1,nom);
            stmt.setString(2,prenom);
            stmt.setString(3,email);
            stmt.setString(4,password);
            stmt.setInt(5,jourRotation);
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("Erreur lors de l'ajout dans la table AdministrateurRHs !!!");
        }
    }

    //Pour la suppression d'un administrateur dans la table AdministrateurRHs :
    public void updateAdminRHMDP(String mdp,String email){
        String sql = "UPDATE administrateurRHs SET password = ? WHERE email = ?";
        try( PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1,mdp);
            stmt.setString(2,email);
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("Erreur lors de la modification du mot de passe dans la table AdministrateurRHs !!!");
        }
    }

    //Pour verifier si la table ,'est pas vide :
    public boolean isEmptyAdminRH(){
        String sql = "SELECT * FROM administrateurRHs";
        try( PreparedStatement stmt = con.prepareStatement(sql)){
            var response = stmt.executeQuery();
            return !response.next();
        }catch (Exception e){
            System.out.println("Erreur lors de la verification de la table AdministrateurRHs !!!");

        }
        return false;
    }

    //Pour la modification d'un administrateur dans la table AdministrateurRHs :
    public void updateAdminRH(String email,int jourRotation){
        String sql = "UPDATE administrateurRHs SET jourRotation = ? WHERE email = ?";
        try( PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1,jourRotation);
            stmt.setString(2,email);
            stmt.executeUpdate();
            System.out.println("Modification du jour de rotation avec succes !!!");
        }catch (Exception e){
            System.out.println("Erreur lors de la modification du jour de rotation dans la table AdministrateurRHs !!!");
        }
    }

    //Pour Recuperer la date du jourRotation dans la table :
    public int getDateRotation(String email){
        String sql = "SELECT jourRotation FROM administrateurRHs WHERE email = ?";
        try( PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1,email);
            var response = stmt.executeQuery();
            if (response.next()){
                return response.getInt("jourRotation");
            }
        }catch (Exception e){
            System.out.println("Erreur lors de la recuperation de la date du jour de rotation dans la table AdministrateurRHs !!!");
        }
        return 0;
    }


}
