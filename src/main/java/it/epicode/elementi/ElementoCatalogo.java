package it.epicode.elementi;

import it.epicode.gestione_prestiti.Prestito;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "elementi_catalogo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(name = "ElementoCatalogo.find.isbn",
                query = "SELECT e FROM ElementoCatalogo e WHERE e.codiceISBN = :isbn"),

        @NamedQuery(name = "ElementoCatalogo.find.annoPubblicazione",
                query = "SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno"),

        @NamedQuery(name = "ElementoCatalogo.find.titolo",
                query = "SELECT e FROM ElementoCatalogo e WHERE e.titolo LIKE :titolo"),

})

public abstract class ElementoCatalogo {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private long codiceISBN;

    @Column (length = 50,  nullable = false)
    private String titolo;

    private int annoPubblicazione;

    private int numeroPagine;

    @OneToMany(mappedBy = "elementoCatalogo", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Prestito> prestiti;


    @Override
    public String toString() {
        return "Catalogo{" +
                "codiceISBN=" + codiceISBN +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", numeroPagine=" + numeroPagine +
                '}';
    }

}
