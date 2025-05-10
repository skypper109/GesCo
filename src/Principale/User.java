package Principale;

import java.util.ArrayList;
import java.util.List;

public class User {
    public List<User> userList;
    private String email;
    private String password;
    private String role;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.userList = new ArrayList<>();
    }

    public User(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    // declaration de la methode qui vas permettre aux agents et l'admin de se connecter

    public boolean authentifier(String email, String password){
        for (User user:userList){
            if (user.email.equals(email) && user.password.equals(password)){
                return true;
            }
        }
        return false;
    }
}
