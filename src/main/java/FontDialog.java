import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontDialog extends JDialog {
    JTextArea textArea;
    JList<String> fontList;
    JTextField fontSizeField;
    Notepad notepad;

    public FontDialog(JTextArea textArea, Notepad notepad) {
        this.textArea = textArea;
        this.notepad = notepad;

        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);

        JLabel fontText = new JLabel("Fuente");
        fontText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        centerPanel.add(fontText);

        fontList = new JList<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontList.setSelectedValue(textArea.getFont().getName(), true);

        JScrollPane fontListScroll = new JScrollPane(fontList);
        centerPanel.add(fontListScroll);

        JLabel sizeText = new JLabel("Tamaño");
        sizeText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        centerPanel.add(sizeText);

        fontSizeField = new JTextField(20);
        fontSizeField.setText((int) notepad.fontSize + "");
        centerPanel.add(fontSizeField);

        JPanel buttonsPanel = new JPanel();
        add(buttonsPanel, BorderLayout.SOUTH);

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.addActionListener(new ButtonListener());
        buttonsPanel.add(acceptButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ButtonListener());
        buttonsPanel.add(cancelButton);

        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(200, 290);
        setTitle("Fuente");
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
        setResizable(false);
        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Aceptar")) {
                try {
                    notepad.fontSize = Integer.parseInt(fontSizeField.getText());
                    textArea.setFont(new Font(fontList.getSelectedValue(), Font.PLAIN, (int) notepad.fontSize));
                    dispose();
                } catch (NumberFormatException exception) {
                    JDialog messageDialog = new JDialog();
                    JOptionPane.showMessageDialog(messageDialog,"El tamaño debe ser numerico.");
                    messageDialog.dispose();
                }
            }
            else dispose();
        }
    }

}

