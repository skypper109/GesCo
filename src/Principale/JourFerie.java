package Principale;

import java.time.LocalDate;

public class JourFerie {
    private String description;
    private LocalDate dateJourFerie;

    public JourFerie(LocalDate dateJourFerie, String description) {
        this.dateJourFerie = dateJourFerie;
        this.description = description;
    }

    public LocalDate getDateJourFerie() {
        return dateJourFerie;
    }
}
