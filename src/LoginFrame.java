import javax.swing.*;
import java.awt.*;

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
        setSize(320, 210);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrata nello schermo
        setResizable(false);

        // --- Campi username e password ---
        JPanel pannelloCampi = new JPanel(new GridLayout(2, 2, 10, 8));
        pannelloCampi.setBorder(BorderFactory.createEmptyBorder(25, 25, 10, 25));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        pannelloCampi.add(new JLabel("Username:"));
        pannelloCampi.add(txtUsername);
        pannelloCampi.add(new JLabel("Password:"));
        pannelloCampi.add(txtPassword);

        // --- Bottone LOGIN ---
        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setPreferredSize(new Dimension(120, 34));

        JPanel pannelloBottone = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pannelloBottone.add(btnLogin);

        // --- Azioni ---
        // Clic sul bottone
        btnLogin.addActionListener(e -> effettuaLogin());

        // Premere Invio nel campo password fa scattare il login
        txtPassword.addActionListener(e -> effettuaLogin());

        // --- Assembla finestra ---
        add(pannelloCampi, BorderLayout.CENTER);
        add(pannelloBottone, BorderLayout.SOUTH);
    }

    /**
     * Legge le credenziali dai campi e verifica il login.
     * Se corretto: chiude questa finestra e apre la MainFrame.
     * Se errato: mostra un messaggio di errore e svuota la password.
     */
    private void effettuaLogin() {
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
