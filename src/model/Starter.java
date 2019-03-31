package model;

import view.MainFrame;

import javax.swing.*;
import java.io.IOException;

public class Starter {
    private static String directory;

    public static void main(String[] args) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception ignored) {
                }
                break;
            }
        }
        chooseDirectory();
    }

    private static void chooseDirectory() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            directory = chooser.getSelectedFile().getPath();
            reset();
        }
    }

    public static void reset() {
        try {
            new ModelMain(directory);
            MainFrame.reset();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Es fehlen benötigte Dateien im Order.",
                    "Falscher Ordner", JOptionPane.ERROR_MESSAGE);
            chooseDirectory();
        }
    }
}
