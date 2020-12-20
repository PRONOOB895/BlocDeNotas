import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The {@code FileFunctions} class contains methods for manipulating files.
 *
 * @author PRONOOB895
 * @since 15
 */
public class FileFunctions {
    private static final JFrame fileDialogFrame = new JFrame();

    /**
     * Writes text in a file.
     *
     * @param file the current file in use
     * @param text the text to be saved
     * @return the current file in use if is not null, otherwise returns a file selected with FileDialog
     */
    public static File saveFile(File file, String text) {
        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(text);
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else return saveFileAs(null, text);
        return file;
    }

    /**
     * Writes text in the file loaded with {@code FileDialog}.
     *
     * @param file the current file in use
     * @param text the text to be saved
     * @return the file where the text was saved if user provided one, otherwise returns the current file
     */
    public static File saveFileAs(File file, String text) {
        FileDialog fileDialog = new FileDialog(fileDialogFrame, "Guardar como", FileDialog.SAVE);
        fileDialog.setVisible(true);

        try {
            file = fileDialog.getFiles()[0];
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.close();
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        fileDialogFrame.dispose();
        return file;
    }

    /**
     * Returns a file loaded by the user with {@code FileDialog}.
     *
     * @param file the current file loaded
     * @return the loaded file if the user gave one, otherwise returns the current file
     */
    public static File loadFile(File file, JTextArea textArea) {
        FileDialog fileDialog = new FileDialog(fileDialogFrame, "Cargar archivo", FileDialog.LOAD);
        fileDialog.setVisible(true);

        try {
            file = fileDialog.getFiles()[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        fileDialogFrame.dispose();
        textArea.setText(getText(file));
        textArea.setCaretPosition(0);
        return file;
    }

    /**
     * Read a file and returns text in it.
     *
     * @param file the file to read
     * @return a String with the content of the file
     */
    public static String getText(File file) {
        if (file == null) return "";

        StringBuilder text = null;
        try {
            text = new StringBuilder();
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) text.append(reader.nextLine()).append(System.lineSeparator());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return text.toString();
    }
}
