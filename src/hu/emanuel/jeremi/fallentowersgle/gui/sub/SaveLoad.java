package hu.emanuel.jeremi.fallentowersgle.gui.sub;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hu.emanuel.jeremi.fallentowersgle.gui.EditorWindow;

public final class SaveLoad extends JInternalFrame {

    /**
     *
     */
    private static final long serialVersionUID = -9033640829939440189L;

    JPanel main;
    JButton selectFolder;
    JTextArea filename;
    JButton saveOrLoad;

    String path = null;

    EditorWindow gui;

    public SaveLoad(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable, EditorWindow gui, boolean saveMode) {
        super(title, resizable, closable, maximizable, iconifiable);

        this.gui = gui;

        // Main panel:
        main = new JPanel();
        main.setLayout(new FlowLayout());
        main.setBackground(Color.BLACK);

        // TextArea settings:
        filename = new JTextArea(1, 10);
        filename.setBackground(Color.BLUE);
        filename.setForeground(Color.GREEN);
        filename.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        selectFolder = new JButton("Select");

        if (saveMode) {
            saveOrLoad = new JButton("Save");

            saveOrLoad.addActionListener(e -> {
                if (path != null && !path.isEmpty()) {
                    gui.save(path);
                    setVisible(false);
                }
            });
        } else {
            saveOrLoad = new JButton("Load");

            saveOrLoad.addActionListener(e -> {
                if (path != null && !path.isEmpty()) {
                    gui.load(path);
                    setVisible(false);
                }
            });
        }

        selectFolder.setBackground(Color.BLACK);
        selectFolder.setForeground(Color.YELLOW);

        saveOrLoad.setBackground(Color.BLACK);
        saveOrLoad.setForeground(Color.YELLOW);

        selectFolder.addActionListener(e -> {
            path = chooseAndGetPath();
        });

        main.add(selectFolder);
        main.add(saveOrLoad);

        add(main);

        setBackground(Color.BLACK);
        pack();
    }

    private String chooseAndGetPath() {
        JFileChooser j = new JFileChooser();

        j.setCurrentDirectory(new java.io.File("."));
        j.setDialogTitle("Choose folder");
        j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        j.setAcceptAllFileFilterUsed(false);

        if (j.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return j.getSelectedFile().getPath(); // j.getCurrentDirectory().toPath().toString() + j.getSelectedFile().toPath();
        } else {
            return "";
        }
    }

}
