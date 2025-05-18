package GestionDB.Tables;

import GestionDB.Connexion;
import Principale.User;
import View.EmailService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Users {
    public Users(){}
    //Pour l'insertion dans la table Users :
    public void insert(String email, String password, String role){
        String sql = "INSERT INTO users(email, password, role) VALUES ( ?, ?, ?)";
        try(Connection con = new Connexion().connect(); PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1,email);
            stmt.setString(2,password);
            stmt.setString(3,role);
            stmt.executeUpdate();
            System.out.println("Insertion effectuer avec succès !!!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion dans la table Users : "+e.getMessage());
        }
    }
    //Pour la liste des users dans la tables :
    public User list(String email, String password){
        String sql = "SELECT * FROM users WHERE email =? AND password =?";
        try(Connection con = new Connexion().connect(); PreparedStatement stmt = con.prepareStatement(sql)){
            String role = "";
            stmt.setString(1,email);
            stmt.setString(2,password);
            var response = stmt.executeQuery();
            if (response.next()){
                return new User(
                        response.getString("email"),
                        response.getString("password"),
                        response.getString("role")
                );
            }
            return null;
        }catch (SQLException e){
            System.out.println("Erreur lors de la recuperation des users !!!");
            return null;
        }
    }
    //Pour la liste des users qui sont enregistrés dans la tables users:
    public boolean isEmptyUser(){
        String sql="SELECT * FROM users";
        try(Connection con = new Connexion().connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            var resp = stmt.executeQuery();
            return !resp.next();
        } catch (SQLException e) {
            System.out.println("Aucun user dans la base de donnée!");
            return true;
        }
    }

    //Supprimer un utilisateur :
    public boolean deleteUser(String email){
        String sql = "DELETE FROM users WHERE email =?";

        try(Connection con = new Connexion().connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1,email);
            stmt.executeUpdate();
            System.out.println("L'utilisateur supprimer avec succes !!!");
            return true;
        } catch (SQLException e) {
            System.out.println("Suppression de user dans la base de donnée n'a pas aboutie!");
        }
        return false;
    }

    //Pour changer le mot de passe dans la table users:
    public void updatePwd(String password,String email){
        String sql = "UPDATE users SET password=? WHERE email=?";
        try (Connection con = new Connexion().connect();
             PreparedStatement ptr = con.prepareStatement(sql)){
            ptr.setString(1,password);
            ptr.setString(2,email);
            ptr.executeUpdate();
            System.out.println("Mot de passe changer avec succès");
            new EmailService().envoyerEmail(email,"Changement de mot de passe dans ANKA-DRAKAA","Votre mot de passe à ete changer avec succès. Votre nouveau mot de passe est : "+password);
        }catch (SQLException e){
            System.out.println("Erreur lors de modification de ton mot de passe !!! ");
        }
    }
}
