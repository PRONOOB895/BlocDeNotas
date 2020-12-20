import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class SaveFileDialog extends JDialog {
    private final File file;
    private final String textToSave;
    private boolean isCanceled = false;

    public SaveFileDialog(File file, String textToSave) {
        super((Window) null);
        setModal(true);

        this.file = file;
        this.textToSave = textToSave;

        setLayout(new BorderLayout());

        JLabel label = new JLabel("Quieres guardar los cambios?");
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);

        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(new SaveFileButtonListener());
        panel.add(saveButton);

        JButton dontSaveButton = new JButton("No guardar");
        dontSaveButton.addActionListener(new SaveFileButtonListener());
        panel.add(dontSaveButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new SaveFileButtonListener());
        panel.add(cancelButton);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isCanceled = true;
            }
        });
        setSize(300, 140);
        setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
        setTitle("Bloc de notas");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private class SaveFileButtonListener extends FileFunctions implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();

            if (e.getActionCommand().equals("Guardar")) saveFile(file, textToSave);
            else if (e.getActionCommand().equals("Cancelar")) isCanceled = true;
        }
    }

    public boolean isNotCanceled() {
        return !isCanceled;
    }
}
