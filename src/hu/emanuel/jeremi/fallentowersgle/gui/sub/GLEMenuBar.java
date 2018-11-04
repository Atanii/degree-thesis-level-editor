package hu.emanuel.jeremi.fallentowersgle.gui.sub;

import javax.swing.JComboBox;
import javax.swing.JMenuBar;

import hu.emanuel.jeremi.fallentowersgle.common.Labels;
import hu.emanuel.jeremi.fallentowersgle.common.Packs;
import hu.emanuel.jeremi.fallentowersgle.gui.EditorWindow;

public class GLEMenuBar extends JMenuBar {

    EditorWindow gui;
    JComboBox<Packs> texturepack;

    public GLEMenuBar(EditorWindow gui) {
        this.gui = gui;
        // ComboBox for selecting texture pack:
        //texturepack = new JComboBox<>(Labels.glemenubarcomboboxelements);
        texturepack = new JComboBox<>(Packs.values());
        add(texturepack);
        texturepack.addActionListener(e -> {
            gui.setSelectedCategory((Packs) texturepack.getSelectedItem());
        });
        texturepack.setSelectedItem(Packs.mainframe);
    }

}
