package it.epicode.elementi;

import it.epicode.elementi.libri.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ElementoDAO {

    private EntityManager em;

    public ElementoDAO(EntityManager em) {
        this.em = em;
    }


    // Aggiunta di un elemento del catalogo
    public void insert(ElementoCatalogo elemento) {
        if (elemento.getCodiceISBN() != 0) {
            em.merge(elemento);
        } else {
            em.persist(elemento);
        }
        System.out.println("Elemento inserito: " + elemento);
    }

    // Rimozione di un elemento del catalogo dato un codice ISBN
    public void deleteByIsbn(long isbn) {
        ElementoCatalogo elemento = em.find(ElementoCatalogo.class, isbn);
        if (elemento != null) {
            em.remove(elemento);
            System.out.println("Elemento eliminato: " + elemento);
        }
    }

    // Ricerca per ISBN
    public ElementoCatalogo findIsbn(long isbn) {
        TypedQuery<ElementoCatalogo> query = em.createNamedQuery("ElementoCatalogo.find.isbn", ElementoCatalogo.class);
        query.setParameter("isbn", isbn);
        return query.getSingleResult();
    }

    // Ricerca per anno di pubblicazione
    public List<ElementoCatalogo> findAnnoPubblicazione(int anno) {
        TypedQuery<ElementoCatalogo> query = em.createNamedQuery("ElementoCatalogo.find.annoPubblicazione", ElementoCatalogo.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    // Ricerca per titolo
    public List<ElementoCatalogo> findTitolo(String titolo) {
        TypedQuery<ElementoCatalogo> query = em.createNamedQuery("ElementoCatalogo.find.titolo", ElementoCatalogo.class);
        query.setParameter("titolo", "%" + titolo + "%");
        return query.getResultList();
    }

    // Ricerca per autore (solo per i libri)
    public List<Libro> findAutore(String autore) {
        TypedQuery<Libro> query = em.createNamedQuery("Libro.find.autore", Libro.class);
        query.setParameter("autore", "%" + autore + "%");
        return query.getResultList();
    }
}
