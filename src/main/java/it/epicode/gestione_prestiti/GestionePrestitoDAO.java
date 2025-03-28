package it.epicode.gestione_prestiti;

import it.epicode.elementi.ElementoCatalogo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionePrestitoDAO {

    private EntityManager em;

    public GestionePrestitoDAO(EntityManager em) {
        this.em = em;
    }

    // Aggiunta di un nuovo utente
    public void insert(Utente utente) {
        if (utente.getNumeroTessera() != null && utente.getNumeroTessera() != 0) {
            em.merge(utente);
        } else {
            em.persist(utente);
        }
        System.out.println("Utente inserito: " + utente);
    }

    // Aggiunta di un nuovo prestito
    public void insert(Prestito prestito) {
        em.persist(prestito);
        System.out.println("Prestito inserito: " + prestito);
    }

    public List<Prestito> findPrestitiAttiviPerTessera(Long tesseraUtente) {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.utente.id = :tessera AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class
        );
        query.setParameter("tessera", tesseraUtente);
        return query.getResultList();
    }


    // Ricerca di tutti i prestiti scaduti e non ancora restituiti
    public List<Prestito> findPrestitiScaduti() {
        List<Prestito> prestitiScaduti = new ArrayList<>();
        LocalDate oggi = LocalDate.now();
        List<Prestito> tuttiIPrestiti = em.createQuery("SELECT p FROM Prestito p", Prestito.class).getResultList();
        for (Prestito prestito : tuttiIPrestiti) {
            if (prestito.getDataRestituzionePrevista().isBefore(oggi) && prestito.getDataRestituzioneEffettiva() == null) {
                prestitiScaduti.add(prestito);
            }
        }

        return prestitiScaduti;
    }


}
