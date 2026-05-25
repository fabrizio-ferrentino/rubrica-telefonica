import javax.swing.*;
import java.awt.*;

/**
 * Finestra di dialogo per creare o modificare una Persona.
 * Si apre sopra la finestra principale.
 */
public class EditorPersonaDialog extends JDialog {

    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtIndirizzo;
    private JTextField txtTelefono;
    private JTextField txtEta;

    private boolean salvato = false; // true se l'utente ha premuto Salva

    public EditorPersonaDialog(JFrame parent, String titolo, Persona persona) {
        super(parent, titolo, true); // true = modale (blocca la finestra padre)

        setSize(380, 280);
        setLocationRelativeTo(parent); // centrata sopra la finestra padre
        setResizable(false);

        // Layout principale
        JPanel pannelloCampi = new JPanel(new GridLayout(5, 2, 10, 8));
        pannelloCampi.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        txtNome      = new JTextField();
        txtCognome   = new JTextField();
        txtIndirizzo = new JTextField();
        txtTelefono  = new JTextField();
        txtEta       = new JTextField();

        pannelloCampi.add(new JLabel("Nome*:"));
        pannelloCampi.add(txtNome);
        pannelloCampi.add(new JLabel("Cognome:"));
        pannelloCampi.add(txtCognome);
        pannelloCampi.add(new JLabel("Indirizzo:"));
        pannelloCampi.add(txtIndirizzo);
        pannelloCampi.add(new JLabel("Telefono*:"));
        pannelloCampi.add(txtTelefono);
        pannelloCampi.add(new JLabel("Età:"));
        pannelloCampi.add(txtEta);

        //--- Bottoni --
        JButton btnSalva   = new JButton("Salva",   IconUtils.iconaSalva());
        JButton btnAnnulla = new JButton("Annulla", IconUtils.iconaAnnulla());

        btnSalva.setFocusPainted(false);
        btnAnnulla.setFocusPainted(false);

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));

        toolbar.add(btnSalva);
        toolbar.addSeparator();
        toolbar.add(btnAnnulla);

        // Se modifica: precompila i campi
        if (persona != null) {
            txtNome.setText(persona.getNome());
            txtCognome.setText(persona.getCognome());
            txtIndirizzo.setText(persona.getIndirizzo());
            txtTelefono.setText(persona.getTelefono());
            txtEta.setText(String.valueOf(persona.getEta()));
        }

        // Azioni bottoni
        btnSalva.addActionListener(e -> {
            if (campiValidi()) {
                salvato = true;
                dispose(); // chiude la finestra
            }
        });

        btnAnnulla.addActionListener(e -> {
            salvato = false;
            dispose();
        });

        // Assembla finestra
        add(toolbar,       BorderLayout.NORTH);
        add(pannelloCampi, BorderLayout.CENTER);
    }

    /**
     * Valida i campi prima di salvare.
     */
    private boolean campiValidi() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il campo Nome è obbligatorio.", "Errore", JOptionPane.ERROR_MESSAGE);
            txtNome.requestFocus();
            return false;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il campo Telefono è obbligatorio.", "Errore", JOptionPane.ERROR_MESSAGE);
            txtCognome.requestFocus();
            return false;
        }
        if (!txtEta.getText().trim().isEmpty()) {
            try {
                int eta = Integer.parseInt(txtEta.getText().trim());
                if (eta < 0 || eta > 150) {
                    JOptionPane.showMessageDialog(this, "Inserisci un'età valida (0-150).", "Errore", JOptionPane.ERROR_MESSAGE);
                    txtEta.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "L'età deve essere un numero intero.", "Errore", JOptionPane.ERROR_MESSAGE);
                txtEta.requestFocus();
                return false;
            }
        }
        return true;
    }

    /**
     * Restituisce true se l'utente ha premuto Salva con dati validi.
     */
    public boolean isSalvato() {
        return salvato;
    }

    /**
     * Costruisce e restituisce un oggetto Persona con i dati inseriti.
     */
    public Persona getPersona() {
        int eta = 0;
        try {
            if (!txtEta.getText().trim().isEmpty()) {
                eta = Integer.parseInt(txtEta.getText().trim());
            }
        } catch (NumberFormatException ignored) {}

        return new Persona(
                txtNome.getText().trim(),
                txtCognome.getText().trim(),
                txtIndirizzo.getText().trim(),
                txtTelefono.getText().trim(),
                eta
        );
    }
}