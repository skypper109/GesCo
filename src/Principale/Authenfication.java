public class Authenfication {
    private int idAuth;
    private char username;
    private char email;
    private char role;

    public Authenfication(int idAuth, char username, char email, char role) {
        this.idAuth = idAuth;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public int getIdAuth() {
        return idAuth;
    }

    public char getUsername() {
        return username;
    }

    public char getEmail() {
        return email;
    }

    public char getRole() {
        return role;
    }

    public void setIdAuth(int idAuth) {
        this.idAuth = idAuth;
    }

    public void setUsername(char username) {
        this.username = username;
    }

    public void setEmail(char email) {
        this.email = email;
    }

    public void setRole(char role) {
        this.role = role;
    }
}
