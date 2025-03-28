package it.epicode.main;

import it.epicode.elementi.ElementoCatalogo;
import it.epicode.elementi.ElementoDAO;
import it.epicode.elementi.libri.Libro;
import it.epicode.elementi.riviste.Periodicita;
import it.epicode.elementi.riviste.Rivista;
import it.epicode.gestione_prestiti.GestionePrestitoDAO;
import it.epicode.gestione_prestiti.Prestito;
import it.epicode.gestione_prestiti.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("epicode");
        EntityManager em = emf.createEntityManager();

        ElementoDAO elementoDAO = new ElementoDAO(em);

        GestionePrestitoDAO gestionePrestitoDAO = new GestionePrestitoDAO(em);

        // Creazione degli oggetti Libro
        Libro l1 = Libro.builder()
                .autore("Dante Alighieri")
                .genere("Epico")
                .titolo("Divina Commedia")
                .annoPubblicazione(1315)
                .numeroPagine(1000)
                .build();

        Libro l2 = Libro.builder()
                .autore("J.R.R. Tolkien")
                .genere("Fantasy")
                .titolo("Il Signore degli Anelli")
                .annoPubblicazione(1954)
                .numeroPagine(1100)
                .build();

        Libro l3 = Libro.builder()
                .autore("Harper Lee")
                .genere("Drammatico")
                .titolo("Il buio oltre la siepe")
                .annoPubblicazione(1960)
                .numeroPagine(280)
                .build();

        Libro l4 = Libro.builder()
                .autore("George Orwell")
                .genere("Drammatico")
                .titolo("1984")
                .annoPubblicazione(1949)
                .numeroPagine(328)
                .build();

        // Creazione delle Riviste
        Rivista r1 = Rivista.builder()
                .titolo("Il Giornale")
                .annoPubblicazione(2000)
                .numeroPagine(100)
                .periodicita(Periodicita.SETTIMANALE)
                .build();

        Rivista r2 = Rivista.builder()
                .titolo("La Stampa")
                .annoPubblicazione(2001)
                .numeroPagine(150)
                .periodicita(Periodicita.MENSILE)
                .build();

        Rivista r3 = Rivista.builder()
                .titolo("Focus")
                .annoPubblicazione(2002)
                .numeroPagine(200)
                .periodicita(Periodicita.SEMESTRALE)
                .build();

        Rivista r4 = Rivista.builder()
                .titolo("National Geographic")
                .annoPubblicazione(2003)
                .numeroPagine(300)
                .periodicita(Periodicita.MENSILE)
                .build();


        // Creazione degli utenti
        Utente utente1 = new Utente(null, "Mario", "Rossi", LocalDate.of(1990, 5, 14), new ArrayList<>());
        Utente utente2 = new Utente(null, "Luca", "Bianchi", LocalDate.of(1985, 8, 22), new ArrayList<>());
        Utente utente3 = new Utente(null, "Giulia", "Verdi", LocalDate.of(1992, 11, 3), new ArrayList<>());
        Utente utente4 = new Utente(null, "Alessandro", "Gialli", LocalDate.of(1988, 2, 17), new ArrayList<>());

        // Creazione dei prestiti
        Prestito prestito1 = new Prestito(null, utente1, l3, LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 31), LocalDate.of(2025, 3, 2)  );
        Prestito prestito2 = new Prestito(null, utente2, l4, LocalDate.of(2025, 3, 5), LocalDate.of(2025, 4, 5), LocalDate.of(2025, 3, 10) );
        Prestito prestito3 = new Prestito(null, utente3, r2, LocalDate.of(2025, 3, 10), LocalDate.of(2025, 4, 10), LocalDate.of(2025, 3, 28) );
        Prestito prestito4 = new Prestito(null, utente4, r3, LocalDate.of(2025, 2, 12), LocalDate.of(2025, 3, 12), null);
        Prestito prestito5 = new Prestito(null, utente3, l1, LocalDate.of(2025, 3, 15), LocalDate.of(2025, 4, 15), null);

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        // Inserimento degli oggetti nel catalogo
        System.out.println("Inserimento degli oggetti nel catalogo...");
        elementoDAO.insert(l1);
        elementoDAO.insert(l2);
        elementoDAO.insert(l3);
        elementoDAO.insert(l4);

        elementoDAO.insert(r1);
        elementoDAO.insert(r2);
        elementoDAO.insert(r3);
        elementoDAO.insert(r4);


        // Inserimento degli utenti
        System.out.println("Inserimento degli utenti...");
        gestionePrestitoDAO.insert(utente1);
        gestionePrestitoDAO.insert(utente2);
        gestionePrestitoDAO.insert(utente3);
        gestionePrestitoDAO.insert(utente4);

        // Inserimento dei prestiti
        System.out.println("Inserimento dei prestiti...");
        gestionePrestitoDAO.insert(prestito1);
        gestionePrestitoDAO.insert(prestito2);
        gestionePrestitoDAO.insert(prestito3);
        gestionePrestitoDAO.insert(prestito4);
        gestionePrestitoDAO.insert(prestito5);

        // Rimozione di un elemento del catalogo tramite il codice ISBN
        elementoDAO.deleteByIsbn(2);

        transaction.commit();

        // Ricerca per ISBN
        long isbnToSearch = 1;
        Libro libroFound = (Libro) elementoDAO.findIsbn(isbnToSearch);
        System.out.println("Libro trovato per ISBN " + isbnToSearch + ": " + libroFound);

        // Ricerca per anno di pubblicazione
        int annoToSearch = 2000;
        List<ElementoCatalogo> elementiPerAnno = elementoDAO.findAnnoPubblicazione(annoToSearch);
        System.out.println("Elementi trovati per anno di pubblicazione " + annoToSearch + ": " + elementiPerAnno);

        // Ricerca per autore
        String autoreToSearch = "Dante";
        List<Libro> libriPerAutore = elementoDAO.findAutore(autoreToSearch);
        System.out.println("Libri trovati per autore " + autoreToSearch + ": " + libriPerAutore);

        // Ricerca per titolo
        String titoloToSearch = "Il";
        List<ElementoCatalogo> elementiPerTitolo = elementoDAO.findTitolo(titoloToSearch);
        System.out.println("Elementi trovati per titolo " + titoloToSearch + ": " + elementiPerTitolo);


        // Ricerca prestiti attivi per tessera utente con prestitiByTessera
        long tesseraUtente = 3;
        List<Prestito> prestitiAttivi = gestionePrestitoDAO.findPrestitiAttiviPerTessera(tesseraUtente);
        System.out.println("Prestiti attivi per la tessera " + tesseraUtente + ": " + prestitiAttivi);

        // Ricerca di tutti i prestiti scaduti e non ancora restituiti
        List<Prestito> prestitiScaduti = gestionePrestitoDAO.findPrestitiScaduti();
        System.out.println("Prestiti scaduti e non ancora restituiti: " + prestitiScaduti);


        // Chiusura delle risorse
        em.close();
        emf.close();
    }
}
