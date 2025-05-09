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


}
