import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Notepad extends JFrame {
    Notepad notepad = this;
    public double fontSize = 15;
    private final JTextArea textArea;
    private final JMenuItem itemFormatMenu1;
    private final JLabel currentFileText;
    private File currentFile;

    public Notepad() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Archivo");
        menuBar.add(fileMenu);

        JMenuItem itemFileMenu1 = new JMenuItem("Nuevo", new ImageIcon(getClass().getResource("new.png")));
        JMenuItem itemFileMenu2 = new JMenuItem("Nueva ventana", new ImageIcon(getClass().getResource("newwindow.png")));
        JMenuItem itemFileMenu3 = new JMenuItem("Abrir...", new ImageIcon(getClass().getResource("open.png")));
        JMenuItem itemFileMenu4 = new JMenuItem("Guardar", new ImageIcon(getClass().getResource("save.png")));
        JMenuItem itemFileMenu5 = new JMenuItem("Guardar como...", new ImageIcon(getClass().getResource("save.png")));
        itemFileMenu1.addActionListener(new ItemFileListener());
        itemFileMenu2.addActionListener(new ItemFileListener());
        itemFileMenu3.addActionListener(new ItemFileListener());
        itemFileMenu4.addActionListener(new ItemFileListener());
        itemFileMenu5.addActionListener(new ItemFileListener());
        itemFileMenu1.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        itemFileMenu2.setAccelerator(KeyStroke.getKeyStroke("ctrl shift N"));
        itemFileMenu3.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        itemFileMenu4.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        itemFileMenu5.setAccelerator(KeyStroke.getKeyStroke("ctrl shift S"));
        fileMenu.add(itemFileMenu1);
        fileMenu.add(itemFileMenu2);
        fileMenu.add(itemFileMenu3);
        fileMenu.add(itemFileMenu4);
        fileMenu.add(itemFileMenu5);

        JMenu editMenu = new JMenu("Edicion");
        menuBar.add(editMenu);

        JMenuItem itemEditMenu1 = new JMenuItem("Cortar", new ImageIcon(getClass().getResource("cut.png")));
        JMenuItem itemEditMenu2 = new JMenuItem("Copiar", new ImageIcon(getClass().getResource("copy.png")));
        JMenuItem itemEditMenu3 = new JMenuItem("Pegar", new ImageIcon(getClass().getResource("paste.png")));
        JMenuItem itemEditMenu4 = new JMenuItem("Eliminar", new ImageIcon(getClass().getResource("delete.png")));
        JMenuItem itemEditMenu5 = new JMenuItem("Seleccionar todo", new ImageIcon(getClass().getResource("selectall.png")));
        itemEditMenu1.addActionListener(new ItemEditListener());
        itemEditMenu2.addActionListener(new ItemEditListener());
        itemEditMenu3.addActionListener(new ItemEditListener());
        itemEditMenu4.addActionListener(new ItemEditListener());
        itemEditMenu5.addActionListener(new ItemEditListener());
        itemEditMenu1.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        itemEditMenu2.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        itemEditMenu3.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        itemEditMenu4.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
        itemEditMenu5.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        editMenu.add(itemEditMenu1);
        editMenu.add(itemEditMenu2);
        editMenu.add(itemEditMenu3);
        editMenu.add(itemEditMenu4);
        editMenu.add(new JSeparator());
        editMenu.add(itemEditMenu5);

        JMenu formatMenu = new JMenu("Formato");
        menuBar.add(formatMenu);

        itemFormatMenu1 = new JCheckBoxMenuItem("Ajuste de linea");
        JMenuItem itemFormatMenu2 = new JMenuItem("Fuente...", new ImageIcon(getClass().getResource("font.png")));
        itemFormatMenu1.setSelected(true);
        itemFormatMenu1.addActionListener(new ItemFormatListener());
        itemFormatMenu2.addActionListener(new ItemFormatListener());
        formatMenu.add(itemFormatMenu1);
        formatMenu.add(itemFormatMenu2);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Consolas", Font.PLAIN, (int) fontSize));
        textArea.setMargin(new Insets(0, 3, 0, 0));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        currentFileText = new JLabel("Archivo: Sin titulo.txt");
        currentFileText.setHorizontalAlignment(JLabel.RIGHT);
        currentFileText.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 4));
        currentFileText.setFont(new Font("Consolas", Font.ITALIC, 14));
        panel.add(currentFileText, BorderLayout.SOUTH);

        // Popup Menu
        JPopupMenu popupMenu = new JPopupMenu();
        textArea.setComponentPopupMenu(popupMenu);

        JMenuItem itemPopupMenu1 = new JMenuItem("Cortar", new ImageIcon(getClass().getResource("cut.png")));
        JMenuItem itemPopupMenu2 = new JMenuItem("Copiar", new ImageIcon(getClass().getResource("copy.png")));
        JMenuItem itemPopupMenu3 = new JMenuItem("Pegar", new ImageIcon(getClass().getResource("paste.png")));
        JMenuItem itemPopupMenu4 = new JMenuItem("Eliminar", new ImageIcon(getClass().getResource("delete.png")));
        JMenuItem itemPopupMenu5 = new JMenuItem("Seleccionar todo", new ImageIcon(getClass().getResource("selectall.png")));
        itemPopupMenu1.addActionListener(new ItemEditListener());
        itemPopupMenu2.addActionListener(new ItemEditListener());
        itemPopupMenu3.addActionListener(new ItemEditListener());
        itemPopupMenu4.addActionListener(new ItemEditListener());
        itemPopupMenu5.addActionListener(new ItemEditListener());
        popupMenu.add(itemPopupMenu1);
        popupMenu.add(itemPopupMenu2);
        popupMenu.add(itemPopupMenu3);
        popupMenu.add(itemPopupMenu4);
        popupMenu.add(new JSeparator());
        popupMenu.add(itemPopupMenu5);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!changesAreSaved()) {
                    SaveFileDialog saveFileDialog = new SaveFileDialog(currentFile, textArea.getText());
                    if (saveFileDialog.isNotCanceled()) dispose();
                }
                else dispose();
            }
        });
        setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
        setTitle("Bloc de notas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private class ItemFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Nuevo":
                    if (changesAreSaved() || new SaveFileDialog(currentFile, textArea.getText()).isNotCanceled()) {
                        currentFile = null;
                        currentFileText.setText("Archivo: Sin titulo.txt");
                        textArea.setText("");
                    }
                    break;
                case "Nueva ventana": new Notepad(); break;
                case "Abrir...":
                    if (changesAreSaved() || new SaveFileDialog(currentFile, textArea.getText()).isNotCanceled()) {
                        currentFile = FileFunctions.loadFile(currentFile, textArea);
                        if (currentFile != null) currentFileText.setText("Archivo: " + currentFile.getName());
                    }
                    break;
                case "Guardar":
                    currentFile = FileFunctions.saveFile(currentFile, textArea.getText());
                    if (currentFile != null) currentFileText.setText("Archivo: " + currentFile.getName());
                    break;
                case "Guardar como...":
                    currentFile = FileFunctions.saveFileAs(currentFile, textArea.getText());
                    if (currentFile != null) currentFileText.setText("Archivo: " + currentFile.getName());
                    break;
            }
        }
    }

    private class ItemEditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Cortar": textArea.cut(); break;
                case "Copiar": textArea.copy(); break;
                case "Pegar": textArea.paste(); break;
                case "Eliminar": textArea.replaceSelection("");
                case "Seleccionar todo": textArea.selectAll(); break;
            }
        }
    }

    class ItemFormatListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Ajuste de linea": textArea.setLineWrap(itemFormatMenu1.isSelected()); break;
                case "Fuente...": new FontDialog(textArea, notepad); break;
            }
        }
    }

    private boolean changesAreSaved() {
        return FileFunctions.getText(currentFile).equals(textArea.getText());
    }
}
