package Principale;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private String email;
    private String password;
    public  List<User> userList;
    private  String role;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.userList = new ArrayList<>();
        this.role = role;
    }

    public User() {
        userList = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public List<User> getUserList() {
        return userList;
    }

    public  String getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail(){
        return this.email;
    }
    public  boolean authentifier(String email, String password){
        for(User user:userList){
            if (user.email.equals(email) && user.password.equals(password)){
                return true;
            }
        }
        return false;
    }
}
