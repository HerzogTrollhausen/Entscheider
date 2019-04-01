package model;

import view.MainFrame;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;

public class Starter {
    private static String directory;
    private static final String GITHUB_URL = "https://raw.githubusercontent.com/HerzogTrollhausen/Entscheider/master/textFiles";
    private static String url;
    private static LinkedList<String> names = new LinkedList<>();
    private static boolean on = false;

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
        String[] selectionValues = {"Github", "Andere Website", "Lokaler Ordner"};
        String answer = (String) JOptionPane.showInputDialog(null,
                "Wo kriegst du deine Daten her?", "Quellenauswahl",
                JOptionPane.QUESTION_MESSAGE, null,
                selectionValues, selectionValues[0]);
        if (answer != null) {
            if (answer.equals(selectionValues[0]) || answer.equals(selectionValues[1])) {
                if (answer.equals(selectionValues[0])) {
                    url = GITHUB_URL;
                } else {
                    url = JOptionPane.showInputDialog("Geb die URL ein.");
                }
                MainFrame.showChooser();
            } else {
                chooseDirectory();
            }
        }
    }

    public static void startWithNames(LinkedList<String> names) {
        Starter.names = names;
        try {
            reset(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Ung\u00fcltige Website oder ung\u00fcltige Namen");
            System.exit(-1);
        }
    }

    private static void chooseDirectory() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            directory = chooser.getSelectedFile().getPath();
            try {
                reset(false);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Es fehlen ben\u00f6tigte Dateien im Ordner.");
            }
        }
    }

    private static void reset(boolean online) throws IOException {
        on = online;
        if (online) {
            new ModelMain(url, names);
        } else {
            new ModelMain(directory);
        }
        MainFrame.showSelector();
        MainFrame.showUnmatchedItems(ModelMain.getUnmatchedItems());
    }

    public static void reset() {
        try {
            reset(on);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
