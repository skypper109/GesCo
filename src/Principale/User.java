package Principale;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private String username;
    private String password;
    public List<User> userList;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.userList = new ArrayList<>();
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean authentifier(String username, String password){
        for(User user:userList){
            if (user.username.equals(username) && user.password.equals(password)){
                return true;
            }
        }
        return false;
    }
}
