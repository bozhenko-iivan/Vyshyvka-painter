package ua.university;

import javax.swing.SwingUtilities;

public class Main {
    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VyshyvkaPainterFrame frame = new VyshyvkaPainterFrame();
            frame.setVisible(true);
            frame.startInitialAnimation("іван");
        });
    }
}