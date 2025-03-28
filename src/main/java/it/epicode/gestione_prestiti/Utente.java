package it.epicode.gestione_prestiti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long numeroTessera;

    @Column (length = 50,  nullable = false)
    private String nome;

    @Column (length = 50, nullable = false)
    private String cognome;

    private LocalDate dataNascita;

    @OneToMany(mappedBy = "utente")
    private List<Prestito> prestiti;

    @Override
    public String toString() {
        return "Utente{" +
                "numeroTessera=" + numeroTessera +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                '}';
    }


}
