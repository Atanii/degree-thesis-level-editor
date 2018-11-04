package hu.emanuel.jeremi.fallentowersgle.gui.sub;

import static hu.emanuel.jeremi.fallentowersgle.common.Tile64.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import hu.emanuel.jeremi.fallentowersgle.common.EnemyType;
import hu.emanuel.jeremi.fallentowersgle.common.ItemType;
import hu.emanuel.jeremi.fallentowersgle.common.Packs;
import hu.emanuel.jeremi.fallentowersgle.gui.EditorWindow;

public class ChoosePanel extends JPanel implements MouseListener {

    /**
     *
     */
    private static final long serialVersionUID = 2851036283511924897L;

    public EditorWindow gui;

    private int w = 100, h = 1000;

    private boolean isDoorChoosingMode = false;

    public ChoosePanel(EditorWindow gui) {
        this.gui = gui;

        this.setBackground(Color.BLACK);

        this.setLayout(new FlowLayout());

        this.addMouseListener(this);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("D"), "Door"
        );
        this.getActionMap().put("Door", new AbstractAction("Door") {
            /**
             *
             */
            private static final long serialVersionUID = 5961636558938002855L;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (gui.chosenPack > Packs.enemy.ordinal()) {
                    toggleDoorMode();
                }
            }
        });

    }

    private void toggleDoorMode() {
        if (!isDoorChoosingMode) {
            isDoorChoosingMode = true;
            System.out.println("<<< DOOR MODE >>>");
        } else {
            isDoorChoosingMode = false;
            System.out.println("<<< DOOR MODE DEACTIVATED >>>");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawIcons(g);
        drawGrid(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }

    private void drawIcons(Graphics g) {
        for (int i = 0; i < gui.getTextureLibrary().tiles[gui.chosenPack].l; i++) {
            g.drawImage(gui.getTextureLibrary().getTexture(gui.chosenPack, i), 10, i * 64, null);
        }
        h = gui.getTextureLibrary().tiles[gui.chosenPack].l * SIZE;
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.WHITE);

        g.drawLine(10, 0, 10, gui.getTextureLibrary().tiles[gui.chosenPack].l * SIZE);
        g.drawLine(SIZE + 10, 0, SIZE + 10, gui.getTextureLibrary().tiles[gui.chosenPack].l * SIZE);

        for (int y = 0; y <= gui.getTextureLibrary().tiles[gui.chosenPack].l; y++) {
            g.drawLine(10, y * SIZE, SIZE + 10, y * SIZE);
        }
    }

    // Mouse listener and mouse events:
    @Override
    public void mouseClicked(MouseEvent e) {
        int y = e.getY();

        if (isDoorChoosingMode) {

            if (e.getButton() == MouseEvent.BUTTON1) {
                this.gui.chosenDoorClosed = (y / SIZE) % gui.getTextureLibrary().tiles[gui.chosenPack].l;
                System.out.println("Door closed: " + this.gui.chosenDoorClosed);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                this.gui.chosenDoorOpened = (y / SIZE) % gui.getTextureLibrary().tiles[gui.chosenPack].l;
                System.out.println("Door opened: " + this.gui.chosenDoorOpened);
            }

        } else if (e.getButton() == MouseEvent.BUTTON1) {

            this.gui.chosenTile = (y / SIZE) % gui.getTextureLibrary().tiles[gui.chosenPack].l;

            if (e.getClickCount() == 2) {
                this.gui.chosenCeiling = (y / SIZE) % gui.getTextureLibrary().tiles[gui.chosenPack].l;
                System.out.println("Ceiling: " + this.gui.chosenCeiling);
            }

            if (gui.chosenPack == Packs.items.ordinal()) {
                gui.chosenItemType = ItemType.values()[this.gui.chosenTile];
                System.out.println("<<< Chosen item-type: " + gui.chosenItemType + " >>>");
            } else if (gui.chosenPack == Packs.enemy.ordinal()) {
                gui.chosenEnemyType = EnemyType.values()[this.gui.chosenTile];
                System.out.println("<<< Chosen enemy-type: " + gui.chosenEnemyType + " >>>");
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
