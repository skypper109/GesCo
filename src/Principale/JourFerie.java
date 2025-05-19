package Principale;

import java.time.LocalDate;

public class JourFerie {
    private final String description;
    private final LocalDate dateJourFerie;

    public JourFerie(LocalDate dateJourFerie, String description) {
        this.dateJourFerie = dateJourFerie;
        this.description = description;
    }



    public LocalDate getDateJourFerie() {

        return dateJourFerie;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JourFerie agent = (JourFerie) obj;
        return dateJourFerie == agent.dateJourFerie;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(dateJourFerie);
    }

    public String getDescription() {
        return description;
    }
}
