import javax.swing.*;

/**
 * This class executes a new instance of Notepad.
 *
 * @author PRONOOB895
 * @since 15
 */
public class Execute {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new Notepad().setVisible(true));
    }
}
