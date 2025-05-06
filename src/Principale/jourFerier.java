import java.util.Date;

public class JourFerie {
    private int idJF;
    private String description;
    private Date dateJourFerie;

    public JourFerie(int idJF, java.lang.String description, Date dateJourFerie) {
        this.idJF = idJF;
        this.description = description;
        this.dateJourFerie = dateJourFerie;
    }

    public int getIdJF() {
        return idJF;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateJourFerie() {
        return dateJourFerie;
    }

    public void setIdJF(int idJF) {
        this.idJF = idJF;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateJourFerie(Date dateJourFerie) {
        this.dateJourFerie = dateJourFerie;
    }


    public void ajouterDateJourFerie(Date date) {
        this.dateJourFerie = date;
    }
}