package Principale;

import java.sql.Date;
import java.time.LocalDate;

public class JourFerie {
    private final String description;
    private final LocalDate dateJourFerie;

    public JourFerie(LocalDate dateJourFerie, String description) {
        this.dateJourFerie = dateJourFerie;
        this.description = description;
    }



    public LocalDate getDateJourFerie() {

        return this.dateJourFerie;
    }

    public String getDescription() {
        return description;
    }
}
