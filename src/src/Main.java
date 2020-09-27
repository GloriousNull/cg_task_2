package src;

import javax.swing.*;
import java.util.Locale;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Locale.setDefault(Locale.ROOT);

        java.awt.EventQueue.invokeLater(() -> (new MainForm()).setVisible(true));
    }
}
