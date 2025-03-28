package it.epicode.elementi.libri;

import it.epicode.elementi.ElementoCatalogo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@NamedQuery(name = "Libro.find.autore", query = "SELECT l FROM Libro l WHERE l.autore LIKE :autore")
public class Libro extends ElementoCatalogo {

    @Column (length = 50)
    private String autore;

    @Column (length = 50)
    private String genere;

    @Override
    public String toString() {
        return "Libro{" +
                "titolo='" + getTitolo() + '\'' +
                ", annoPubblicazione=" + getAnnoPubblicazione() +
                ", numeroPagine=" + getNumeroPagine() +
                ", autore='" + autore + '\'' +
                ", genere='" + genere + '\'' +
                '}';
    }

}
