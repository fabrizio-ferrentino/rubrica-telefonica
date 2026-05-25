import java.io.*;
import java.util.Scanner;
import java.util.Vector;

/**
 * Gestisce la lista di persone e la persistenza su file.
 * Questo è il "layer logico" dell'applicazione.
 */
public class RubricaManager {

    private Vector<Persona> persone;
    private static final String FILE_PATH = "informazioni.txt";

    public RubricaManager() {
        persone = new Vector<>();
        caricaDaFile(); // carica i dati all'avvio
    }

    // CRUD
    public void aggiungiPersona(Persona p) {
        persone.add(p);
        salvaSuFile();
    }

    public void modificaPersona(int indice, Persona nuoviDati) {
        Persona p = persone.get(indice);
        p.setNome(nuoviDati.getNome());
        p.setCognome(nuoviDati.getCognome());
        p.setIndirizzo(nuoviDati.getIndirizzo());
        p.setTelefono(nuoviDati.getTelefono());
        p.setEta(nuoviDati.getEta());
        salvaSuFile();
    }

    public void eliminaPersona(int indice) {
        persone.remove(indice);
        salvaSuFile();
    }

    public Persona getPersona(int indice) {
        return persone.get(indice);
    }

    public Vector<Persona> getPersone() {
        return persone;
    }

    public int getNumeroPersone() {
        return persone.size();
    }

    // PERSISTENZA
    /**
     * Carica i contatti dal file informazioni.txt all'avvio.
     * Se il file non esiste, non fa nulla.
     * Formato: nome;cognome;indirizzo;telefono;eta
     */
    private void caricaDaFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return; // nessun errore se il file non esiste

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String riga = scanner.nextLine().trim();
                if (riga.isEmpty()) continue;

                String[] parti = riga.split(";");
                if (parti.length == 5) {
                    try {
                        String nome      = parti[0];
                        String cognome   = parti[1];
                        String indirizzo = parti[2];
                        String telefono  = parti[3];
                        int eta          = Integer.parseInt(parti[4]);
                        persone.add(new Persona(nome, cognome, indirizzo, telefono, eta));
                    } catch (NumberFormatException e) {
                        // riga malformata, la saltiamo
                        System.err.println("Riga non valida nel file: " + riga);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Errore apertura file: " + e.getMessage());
        }
    }

    /**
     * Salva tutti i contatti su file dopo ogni modifica.
     * Sovrascrive completamente il file.
     */
    private void salvaSuFile() {
        try (PrintStream ps = new PrintStream(new FileOutputStream(FILE_PATH))) {
            for (Persona p : persone) {
                ps.println(
                        p.getNome()      + ";" +
                                p.getCognome()   + ";" +
                                p.getIndirizzo() + ";" +
                                p.getTelefono()  + ";" +
                                p.getEta()
                );
            }
        } catch (FileNotFoundException e) {
            System.err.println("Errore salvataggio file: " + e.getMessage());
        }
    }
}