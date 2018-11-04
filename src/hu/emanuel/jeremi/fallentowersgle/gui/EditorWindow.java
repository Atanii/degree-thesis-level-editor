package hu.emanuel.jeremi.fallentowersgle.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

import hu.emanuel.jeremi.fallentowersgle.common.EnemyType;
import hu.emanuel.jeremi.fallentowersgle.common.ItemType;
import hu.emanuel.jeremi.fallentowersgle.common.Packs;
import hu.emanuel.jeremi.fallentowersgle.gui.dialog.NewFile;
import hu.emanuel.jeremi.fallentowersgle.gui.sub.AttributePanel;
import hu.emanuel.jeremi.fallentowersgle.gui.sub.ChoosePanel;
import hu.emanuel.jeremi.fallentowersgle.gui.sub.Console;
import hu.emanuel.jeremi.fallentowersgle.gui.sub.EditorPanel;
import hu.emanuel.jeremi.fallentowersgle.gui.sub.GLEMenuBar;
import hu.emanuel.jeremi.fallentowersgle.gui.sub.SaveLoad;
import hu.emanuel.jeremi.fallentowersgle.saveload.TowHandler;
import hu.emanuel.jeremi.fallentowersgle.saveload.TowHandler.LevelData;
import hu.emanuel.jeremi.fallentowersgle.tile.TextureLibrary;
import hu.emanuel.jeremi.fallentowersgle.tile.TileType;

public class EditorWindow extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 7026265820916822665L;

    /////////////////////////////// MAP-RELATED //////////////////////////////////////////////
    private TowHandler s;

    public TextureLibrary tl;
    public int chosenPack = 3;
    public int chosenTexturePack = 3;
    public int chosenTile = 0;
    public int chosenCeiling = 0;
    public int chosenDoorOpened = 0;
    public int chosenDoorClosed = 0;

    public TileType chosenType = TileType.wall;
    public ItemType chosenItemType = ItemType.ZAPPER;
    public EnemyType chosenEnemyType = EnemyType.BZZZZ_TOWER;

    public static class TileAttributes {

        public static int _id = (int) (Math.random() * 1000) >> 1;

        public int id;
        public int ceiling, floor, wall, inside;
        public int value;
        public int sendercose, msgcode, time;

        public TileAttributes() {
            this.id = _id++;
        }

        public int id() {
            return _id++;
        }

        public TileAttributes(int value) {
            this.value = value;
            this.id = _id++;
        }

        public TileAttributes(int sendercode, int msgcode) {
            this.sendercose = sendercode;
            this.msgcode = msgcode;
            this.id = _id++;
        }

        public TileAttributes(int ceiling, int floor, int wall, int inside) {
            this.ceiling = ceiling;
            this.floor = floor;
            this.wall = wall;
            this.id = _id++;
        }
    }
    public TileAttributes ta = new TileAttributes();

    public int mapW, mapH;

    public int playerX = 1, playerY = 1;

    public EditorPanel getGridPane() {
        return gridPane;
    }
    //////////////////////////////////////////////////////////////////////////////////////////

    JSplitPane upperPanel;

    EditorPanel gridPane;
    ChoosePanel choicePane;
    AttributePanel attrPane;

    Console terminal;

    SaveLoad saver;
    SaveLoad loader;

    GLEMenuBar mb;
    public int selectedCategoryIndex;
    public Packs selectedPack = Packs.mainframe;

    public EditorWindow() {
        super("FallenTowers Map Editor v0.5");

        s = new TowHandler(this);

        // This, main window
        setMinimumSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
        setBackground(Color.black);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // TextureLibrary
        tl = new TextureLibrary("sprites.png", "items.png", "mainframe.png", "winter.png", "office.png");

        // JDesktopPane
        JDesktopPane d = new JDesktopPane();
        d.setBackground(new Color(246, 215, 176));
        d.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        add(d, BorderLayout.CENTER);

        /////////////////////////////// INTERNAL FRAMES //////////////////////////////////////////
        // Tile Attributes
        JInternalFrame attributeInternal = new JInternalFrame("Tile Attributes", true, false, true, true);
        d.add(attributeInternal);
        attrPane = new AttributePanel(this);
        attrPane.setPreferredSize(new Dimension(900, 200));
        attributeInternal.add(attrPane);
        attributeInternal.setBounds(110, 0, 900, 220);
        attributeInternal.setVisible(true);

        // TileChooser
        JInternalFrame choiceInternal = new JInternalFrame("Tiles", true, false, true, true);
        choicePane = new ChoosePanel(this);
        choicePane.setPreferredSize(new Dimension(100, getSize().height - 40 - 30));
        JScrollPane choiceScroll = new JScrollPane(choicePane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        d.add(choiceInternal);
        choiceInternal.add(choiceScroll);
        choiceInternal.setBounds(0, 0, 110, getSize().height - 40 - 30);
        choiceInternal.setVisible(true);
        mb = new GLEMenuBar(this);
        choiceInternal.setJMenuBar(mb);

        // Editor
        JInternalFrame editorInternal = new JInternalFrame("Grid", true, false, true, true);
        d.add(editorInternal);
        gridPane = new EditorPanel(this, 2000, 2000);
        gridPane.setPreferredSize(new Dimension(800, 700));
        JScrollPane editorScroll = new JScrollPane(gridPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        editorInternal.add(editorScroll);
        editorInternal.setBounds(110, 230, 1000, 700);
        editorInternal.setVisible(true);

        // Console
        terminal = new Console("Terminal", true, false, true, true);
        terminal.setResizable(false);
        d.add(terminal);
        iconify(terminal);

        // FileSaver
        saver = new SaveLoad("Save", false, false, false, false, this, true);
        d.add(saver);
        loader = new SaveLoad("Load", false, false, false, false, this, false);
        d.add(loader);
        //////////////////////////////////////////////////////////////////////////////////////////

        attrPane.select(chosenType);

        setVisible(true);

        new NewFile(this, true);

        gridPane.setMapSize(mapW, mapH);

        /////////////////////////////// KEYBINDINGS //////////////////////////////////////////////
        d.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK), "Save"
        );
        d.getActionMap().put("Save", new AbstractAction("Save") {
            /**
             *
             */
            private static final long serialVersionUID = 5961636558938002855L;

            @Override
            public void actionPerformed(ActionEvent e) {
                saver.setVisible(true);
            }
        });

        d.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK), "Load"
        );
        d.getActionMap().put("Load", new AbstractAction("Load") {
            /**
             *
             */
            private static final long serialVersionUID = 5961636558938002855L;

            @Override
            public void actionPerformed(ActionEvent e) {
                loader.setVisible(true);
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////

        System.out.println(chosenTexturePack);
    }

    public void iconify(JInternalFrame j) {
        try {
            j.setIconifiable(true);
            j.setIcon(true);
        } catch (PropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gridPane.repaint();
        choicePane.repaint();
    }

    public TextureLibrary getTextureLibrary() {
        return this.tl;
    }

    public void setSelectedCategory(Packs selected) {
        this.selectedPack = selected;
        this.chosenPack = selected.ordinal();
        if (selected.ordinal() > 1) {
            this.chosenTexturePack = selected.ordinal();
        }
        if (selected == Packs.items) {
            attrPane.select(TileType.item);
            this.chosenType = TileType.item;
        } else if (selected == Packs.enemy) {
            attrPane.select(TileType.enemy);
            this.chosenType = TileType.enemy;
        } else {
            attrPane.select(TileType.wall);
            this.chosenType = TileType.wall;
        }
        refreshGUI();
    }

    private void refreshGUI() {
        choicePane.repaint();
    }

    public void save(String path) {
        s.SaveLevel(path);
    }

    public void load(String path) {
        LevelData d = s.LoadLevel(path);

        if (d == null) {
            new NewFile(this, true);
            gridPane.setMapSize(mapW, mapH);
            return;
        }

        chosenPack = d.pack;
        chosenTexturePack = d.pack;
        chosenCeiling = d.walls.get(0).ceiling;

        playerX = d.playerX;
        playerY = d.playerY;

        gridPane.width = d.mapW;
        gridPane.height = d.mapH;
        gridPane.walls = d.walls;
        gridPane.sprites = d.sprites;
        gridPane.doors = d.doors;
        gridPane.items = d.items;
        gridPane.enemies = d.enemies;
        gridPane.messages = d.messages;

        gridPane.repaint();
    }

    public void setPlayerPose(int x, int y) {
        playerX = x;
        playerY = y;
    }

    public void setMapSize(int w, int h) {
        this.mapW = w;
        this.mapH = h;
    }

}
