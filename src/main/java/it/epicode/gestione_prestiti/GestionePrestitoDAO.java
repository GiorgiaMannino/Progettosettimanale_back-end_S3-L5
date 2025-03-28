package it.epicode.gestione_prestiti;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

    // Metodo per trovare i prestiti attivi di un utente
        public List<Prestito> findPrestitiAttiviPerTessera(Long tesseraUtente) {
        TypedQuery<Prestito> query = em.createNamedQuery("Utente.findPrestitiAttivi", Prestito.class);
        query.setParameter("tessera", tesseraUtente);
        return query.getResultList();
    }


    // Metodo per trovare i prestiti scaduti
    public List<Prestito> findPrestitiScaduti() {
        TypedQuery<Prestito> query = em.createNamedQuery("Prestito.findPrestitiScaduti", Prestito.class);
        return query.getResultList();
    }
}

