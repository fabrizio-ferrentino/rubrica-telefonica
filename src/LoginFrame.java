import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Finestra di login dell'applicazione Rubrica.
 * È la prima finestra mostrata all'avvio.
 * Se le credenziali sono corrette, si chiude e apre la finestra principale.
 * Se le credenziali sono errate, mostra un messaggio di errore.
 */
public class LoginFrame extends JFrame {

    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private UtenteManager  utenteManager;

    public LoginFrame() {
        utenteManager = new UtenteManager();

        setTitle("Login - Rubrica Telefonica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // --- Campi username e password ---
        JPanel pannelloCampi = new JPanel(new GridBagLayout());
        pannelloCampi.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spazio tra i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);

        // Riga 0: Username
        gbc.gridx = 0; gbc.gridy = 0;
        pannelloCampi.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        pannelloCampi.add(txtUsername, gbc);

        // Riga 1: Password
        gbc.gridx = 0; gbc.gridy = 1;
        pannelloCampi.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        pannelloCampi.add(txtPassword, gbc);

        // --- Bottone LOGIN ---
        JPanel pannelloBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pannelloBottoni.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JButton btnLogin = new JButton("Login", IconUtils.iconaLogin());
        btnLogin.setFocusPainted(false);
        // Premi Invio per attivare il bottone di default
        getRootPane().setDefaultButton(btnLogin);

        pannelloBottoni.add(btnLogin);

        // --- Azioni ---
        // Clic sul bottone
        btnLogin.addActionListener(e -> {
            try {
                effettuaLogin();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // --- Assembla finestra ---
        add(pannelloCampi, BorderLayout.CENTER);
        add(pannelloBottoni, BorderLayout.SOUTH);

        // pack() calcola la dimensione perfetta in base ai componenti interni
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Legge le credenziali dai campi e verifica il login.
     * Se corretto: chiude questa finestra e apre la MainFrame.
     * Se errato: mostra un messaggio di errore e svuota la password.
     */
    private void effettuaLogin() throws SQLException {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (utenteManager.verificaCredenziali(username, password)) {
            dispose(); // chiude la finestra di login

            // Apre la finestra principale
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Login errato. Verificare username e password.",
                    "Accesso negato",
                    JOptionPane.ERROR_MESSAGE
            );
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }
}
