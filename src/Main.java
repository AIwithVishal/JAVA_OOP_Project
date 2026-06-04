import javax.swing.SwingUtilities;
import ui.AppWindow;

public class Main {
    public static void main(String[] args) {
        // Runs the GUI on the Event Dispatch Thread (Good OOP practice)
        SwingUtilities.invokeLater(() -> {
            new AppWindow().setVisible(true);
        });
    }
}



