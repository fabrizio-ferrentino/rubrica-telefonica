import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Finestra principale dell'applicazione Rubrica.
 * Mostra la tabella dei contatti e i bottoni Nuovo / Modifica / Elimina.
 */
public class MainFrame extends JFrame {

    private RubricaManager manager;
    private JTable tabella;
    private DefaultTableModel modelloTabella;

    public MainFrame() {
        manager = new RubricaManager();

        setTitle("Rubrica Telefonica");
        setSize(600, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrata nello schermo
        setMinimumSize(new Dimension(480, 320));

        // Tabella
        String[] colonne = {"Nome", "Cognome", "Telefono"};
        modelloTabella = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // tabella non modificabile direttamente
            }
        };

        tabella = new JTable(modelloTabella);
        tabella.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabella.setRowHeight(24);
        tabella.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabella);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Bottoni
        JButton btnNuovo    = new JButton("Nuovo");
        JButton btnModifica = new JButton("Modifica");
        JButton btnElimina  = new JButton("Elimina");

        btnNuovo.setPreferredSize(new Dimension(130, 34));
        btnModifica.setPreferredSize(new Dimension(130, 34));
        btnElimina.setPreferredSize(new Dimension(130, 34));

        // colore rosso per il bottone elimina
        btnElimina.setForeground(new Color(180, 30, 30));

        JPanel pannelloBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        pannelloBottoni.add(btnNuovo);
        pannelloBottoni.add(btnModifica);
        pannelloBottoni.add(btnElimina);

        // Azioni
        btnNuovo.addActionListener(e -> apriNuovoContatto());
        btnModifica.addActionListener(e -> apriModificaContatto());
        btnElimina.addActionListener(e -> eliminaContatto());

        // Assembla finestra
        add(scrollPane, BorderLayout.CENTER);
        add(pannelloBottoni, BorderLayout.SOUTH);

        // Carica dati iniziali nella tabella
        aggiornaTabella();
    }

    // NUOVO Contatto
    private void apriNuovoContatto() {
        EditorPersonaDialog dialog = new EditorPersonaDialog(this, "Nuovo Contatto", null);
        dialog.setVisible(true);

        if (dialog.isSalvato()) {
            manager.aggiungiPersona(dialog.getPersona());
            aggiornaTabella();
        }
    }

    // MODIFICA
    private void apriModificaContatto() {
        int rigaSelezionata = tabella.getSelectedRow();

        if (rigaSelezionata == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Per modificare è necessario prima selezionare una persona dalla tabella.",
                    "Nessuna selezione",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Persona personaSelezionata = manager.getPersona(rigaSelezionata);
        EditorPersonaDialog dialog = new EditorPersonaDialog(this, "Modifica Contatto", personaSelezionata);
        dialog.setVisible(true);

        if (dialog.isSalvato()) {
            manager.modificaPersona(rigaSelezionata, dialog.getPersona());
            aggiornaTabella();
            // riseleziona la stessa riga dopo la modifica
            if (rigaSelezionata < tabella.getRowCount()) {
                tabella.setRowSelectionInterval(rigaSelezionata, rigaSelezionata);
            }
        }
    }

    // ELIMINA
    private void eliminaContatto() {
        int rigaSelezionata = tabella.getSelectedRow();

        if (rigaSelezionata == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Per eliminare è necessario prima selezionare una persona dalla tabella.",
                    "Nessuna selezione",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Persona persona = manager.getPersona(rigaSelezionata);
        int conferma = JOptionPane.showConfirmDialog(
                this,
                "Eliminare la persona " + persona.getNome() + " " + persona.getCognome() + "?",
                "Conferma eliminazione",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (conferma == JOptionPane.YES_OPTION) {
            manager.eliminaPersona(rigaSelezionata);
            aggiornaTabella();
        }
    }

    // AGGIORNA TABELLA
    /**
     * Svuota e ricostruisce la tabella con i dati aggiornati dal manager.
     */
    private void aggiornaTabella() {
        modelloTabella.setRowCount(0); // svuota la tabella
        for (Persona p : manager.getPersone()) {
            modelloTabella.addRow(new Object[]{
                    p.getNome(),
                    p.getCognome(),
                    p.getTelefono()
            });
        }
    }
}