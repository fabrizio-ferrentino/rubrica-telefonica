import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Gestisce la lista degli utenti e la verifica delle credenziali di login.
 * Carica gli utenti dal file "utenti.txt".
 * Se il file non esiste, viene usato un utente di default (admin/admin).
 * Formato file: username;password (una coppia per riga)
 */
public class UtenteManager {

    private Vector<Utente> utenti;
    private static final String FILE_PATH = "utenti.txt";

    public UtenteManager() {
        utenti = new Vector<>();
        caricaDaFile();

        // Se il file non esiste o è vuoto, crea un utente di default
        if (utenti.isEmpty()) {
            utenti.add(new Utente("admin", "admin"));
        }
    }

    /**
     * Verifica se le credenziali fornite corrispondono a un utente esistente.
     *
     * @param username username inserito dall'utente
     * @param password password inserita dall'utente
     * @return true se le credenziali sono corrette, false altrimenti
     */
    public boolean verificaCredenziali(String username, String password) {
        for (Utente u : utenti) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Carica gli utenti dal file utenti.txt.
     * Se il file non esiste, non fa nulla e non genera errori.
     */
    private void caricaDaFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String riga = scanner.nextLine().trim();
                if (riga.isEmpty()) continue;

                String[] parti = riga.split(";");
                if (parti.length == 2) {
                    utenti.add(new Utente(parti[0], parti[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Errore apertura file utenti: " + e.getMessage());
        }
    }
}
