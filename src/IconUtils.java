import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Utility per generare piccole icone disegnate "al volo" con Java2D.
 * Vantaggio: niente file immagine esterni da gestire / da impacchettare nel JAR.
 * Le icone vengono usate sui bottoni delle JToolbar.
 */
public class IconUtils {

    private static final int SIZE = 16;

    // Tratto arrotondato per linee più morbide ed eleganti
    private static final BasicStroke ROUND_STROKE_2 = new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final BasicStroke ROUND_STROKE_3 = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    // ----- Icone pubbliche -----

    /** Icona "Nuovo": cerchio verde con un + bianco. */
    public static Icon iconaNuovo() {
        return creaIcona((g) -> {
            g.setColor(new Color(40, 160, 70));
            g.fillOval(1, 1, 14, 14); // 14x14 centrato nel 16x16

            g.setColor(Color.WHITE);
            g.setStroke(ROUND_STROKE_2);
            g.drawLine(8, 4, 8, 12); // Linea verticale
            g.drawLine(4, 8, 12, 8); // Linea orizzontale
        });
    }

    /** Icona "Modifica": matita blu inclinata. */
    public static Icon iconaModifica() {
        return creaIcona((g) -> {
            // Corpo della matita
            g.setColor(new Color(40, 110, 200));
            g.setStroke(ROUND_STROKE_3);
            g.drawLine(6, 10, 13, 3);

            // Legno della punta
            g.setColor(new Color(230, 200, 60));
            g.setStroke(ROUND_STROKE_2);
            g.drawLine(5, 11, 4, 12);

            // Grafite (Punta nera)
            g.setColor(Color.DARK_GRAY);
            g.drawLine(3, 13, 2, 14);
        });
    }

    /** Icona "Elimina": cerchio rosso con una X bianca. */
    public static Icon iconaElimina() {
        return creaIcona((g) -> {
            g.setColor(new Color(200, 50, 50));
            g.fillOval(1, 1, 14, 14);

            g.setColor(Color.WHITE);
            g.setStroke(ROUND_STROKE_2);
            g.drawLine(5, 5, 11, 11);
            g.drawLine(11, 5, 5, 11);
        });
    }

    /** Icona "Salva": cerchio verde con un check bianco. */
    public static Icon iconaSalva() {
        return creaIcona((g) -> {
            g.setColor(new Color(40, 160, 70));
            g.fillOval(1, 1, 14, 14);

            g.setColor(Color.WHITE);
            g.setStroke(ROUND_STROKE_2);
            // Spunta perfettamente proporzionata
            g.drawLine(4, 8, 7, 11);
            g.drawLine(7, 11, 12, 5);
        });
    }

    /** Icona "Annulla": cerchio grigio con una X bianca. */
    public static Icon iconaAnnulla() {
        return creaIcona((g) -> {
            g.setColor(new Color(120, 120, 120));
            g.fillOval(1, 1, 14, 14);

            g.setColor(Color.WHITE);
            g.setStroke(ROUND_STROKE_2);
            g.drawLine(5, 5, 11, 11);
            g.drawLine(11, 5, 5, 11);
        });
    }

    /** Icona "Login": chiave stilizzata blu. */
    public static Icon iconaLogin() {
        return creaIcona((g) -> {
            g.setColor(new Color(40, 110, 200));
            g.setStroke(ROUND_STROKE_2);

            // Testa della chiave (anello)
            g.drawOval(2, 5, 5, 5);

            // Gambo della chiave
            g.drawLine(7, 8, 14, 8);

            // Denti della chiave
            g.drawLine(14, 8, 14, 11);
            g.drawLine(11, 8, 11, 11);
        });
    }

    // ----- Helper interno -----

    /** Crea una BufferedImage trasparente, applica il disegno, ritorna un ImageIcon. */
    private static Icon creaIcona(Disegnatore d) {
        BufferedImage img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        // Attiva l'antialiasing per linee e forme morbide
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        d.disegna(g);
        g.dispose();
        return new ImageIcon(img);
    }

    /** Interfaccia funzionale per passare il "blocco di disegno" come parametro. */
    @FunctionalInterface
    private interface Disegnatore {
        void disegna(Graphics2D g);
    }
}