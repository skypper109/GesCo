package Principale;

import GestionDB.Tables.Users;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    public Users userTable = new Users();
    private String email;
    private String password;
    public List<User> userList;
    private String role;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.userList = new ArrayList<>();
        this.role = role;
    }

    public User() {
        this.userList = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public List<User> getUserList() {
        return userList;
    }

    public String getRole() {
        return role;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password,String email) {
        userTable.updatePwd(password,email);
        this.password = password;
    }
    public String getEmail(){
        return this.email;
    }
    public boolean authentifier(String email, String password){
       User user = userTable.list(email,password);
        return user != null;
    }
}
