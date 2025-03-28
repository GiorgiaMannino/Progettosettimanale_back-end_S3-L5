package it.epicode.gestione_prestiti;

import it.epicode.elementi.ElementoCatalogo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prestiti")
@NamedQuery(
        name = "Prestito.findPrestitiScaduti",
        query = "SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < CURRENT_DATE AND p.dataRestituzioneEffettiva IS NULL"
)
@NamedQuery(
        name = "Utente.findPrestitiAttivi",
        query = "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :tessera AND p.dataRestituzioneEffettiva IS NULL"
)

public class Prestito {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Utente utente;

    @ManyToOne
    private ElementoCatalogo elementoCatalogo;


    @Column(nullable = false)
    private LocalDate dataInizioPrestito;

    private LocalDate dataRestituzionePrevista;

    private LocalDate dataRestituzioneEffettiva;


 @Override
    public String toString() {
        return "Prestito{" +
                "id=" + id +
                ", utente=" + utente +
                ", elementoCatalogo=" + elementoCatalogo +
                ", dataInizioPrestito=" + dataInizioPrestito +
                ", dataRestituzionePrevista=" + dataRestituzionePrevista +
                ", dataRestituzioneEffettiva=" + dataRestituzioneEffettiva +
                '}';
    }
}
