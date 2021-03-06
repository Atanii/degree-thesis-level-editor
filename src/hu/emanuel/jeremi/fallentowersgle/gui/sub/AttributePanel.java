package hu.emanuel.jeremi.fallentowersgle.gui.sub;

import hu.emanuel.jeremi.fallentowersgle.common.Labels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import hu.emanuel.jeremi.fallentowersgle.gui.EditorWindow;
import hu.emanuel.jeremi.fallentowersgle.gui.EditorWindow.TileAttributes;
import hu.emanuel.jeremi.fallentowersgle.tile.TileType;

public class AttributePanel extends JPanel {

    EditorWindow gui;

    JPanel mainPanel;
    JPanel right;
    JPanel iconpanel;

    JPanel radio;
    JLabel typeRadio;
    JLabel spriteRadio;
    JLabel wallRadio;
    JLabel msgRadio;
    JLabel doorRadio;
    JLabel itemRadio;
    JLabel enemyRadio;
    ButtonGroup radios;
    JRadioButton spriteRB;
    JRadioButton wallRB;
    JRadioButton msgRB;
    JRadioButton doorRB;
    JRadioButton itemRB;
    JRadioButton enemyRB;

    JPanel attributesPanel;
    // except wall
    JTextField id;
    JTextField value;
    JLabel l_entity_id;
    JLabel l_value;

    // message
    JLabel l_time;
    JLabel l_sender;
    JLabel l_message;
    JTextField time;
    JTextField sendercode;
    JTextField messagecode;

    // wall
    JLabel l_ceiling;
    JTextField ceiling;

    // wall
    // inside ceiling floor wall height virtual storey //
    JPanel buttonPanel;
    JButton submit;

    // all used components but radio buttons
    ArrayList<JComponent> labels;

    public AttributePanel(EditorWindow gui) {
        this.gui = gui;

        ///////// AttributePanel setting: ///////////
        // Design & colours:
        this.setBackground(Color.BLACK);
        // AttributePanel layout manager:
        this.setLayout(new FlowLayout());
        /////////////////////////////////////////////		

        ///////// Component initialization: /////////
        // Two main panel:
        mainPanel = new JPanel();
        right = new JPanel();

        // Iconpanel:
        iconpanel = new JPanel();

        // Radio buttons:
        radio = new JPanel();
        radios = new ButtonGroup();
        spriteRadio = new JLabel(Labels.radioLabelSprite, JLabel.RIGHT);
        wallRadio = new JLabel(Labels.radioLabelWall, JLabel.RIGHT);
        msgRadio = new JLabel(Labels.radioLabelMsg, JLabel.RIGHT);
        doorRadio = new JLabel(Labels.radioLabelDoor, JLabel.RIGHT);
        itemRadio = new JLabel(Labels.radioLabelItem, JLabel.RIGHT);
        enemyRadio = new JLabel(Labels.radioLabelEnemy, JLabel.RIGHT);
        spriteRB = new JRadioButton();
        wallRB = new JRadioButton();
        msgRB = new JRadioButton();
        doorRB = new JRadioButton();
        itemRB = new JRadioButton();
        enemyRB = new JRadioButton();

        // ID field:
        attributesPanel = new JPanel();
        attributesPanel.setLayout(new GridLayout(3, 5, 5, 5));

        l_entity_id = new JLabel("entity id", JLabel.RIGHT);
        l_time = new JLabel("time", JLabel.RIGHT);
        l_value = new JLabel("value", JLabel.RIGHT);
        l_sender = new JLabel("sender", JLabel.RIGHT);
        l_message = new JLabel("message", JLabel.RIGHT);
        l_ceiling = new JLabel("ceiling", JLabel.RIGHT);

        id = new JTextField(10);
        value = new JTextField(10);
        time = new JTextField(10);
        sendercode = new JTextField(10);
        messagecode = new JTextField(10);
        ceiling = new JTextField(10);

        // Checkboxes:
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        submit = new JButton(Labels.attributeSubmit);
        /////////////////////////////////////////////

        ///////// Component setting: ////////////////
        // Two main panel:
        mainPanel.setLayout(new GridLayout(4, 1));
        mainPanel.setBackground(Color.black);
        // Iconpanel:

        // Radiopanel:
        radio.setLayout(new GridLayout(1, 6));

        radio.setBackground(Color.black);
        wallRB.setBackground(Color.black);
        spriteRB.setBackground(Color.black);
        msgRB.setBackground(Color.black);
        doorRB.setBackground(Color.black);
        itemRB.setBackground(Color.black);
        enemyRB.setBackground(Color.black);

        radios.add(spriteRB);
        radios.add(wallRB);
        radios.add(msgRB);
        radios.add(doorRB);
        radios.add(itemRB);
        radios.add(enemyRB);
        radio.add(spriteRadio);
        radio.add(spriteRB);
        radio.add(wallRadio);
        radio.add(wallRB);
        radio.add(msgRadio);
        radio.add(msgRB);
        radio.add(doorRadio);
        radio.add(doorRB);
        radio.add(itemRadio);
        radio.add(itemRB);
        radio.add(enemyRadio);
        radio.add(enemyRB);

        spriteRB.addActionListener((e) -> {
            disableAllNonRadio();
            enableSpriteAttributes();
            select(TileType.sprite);
        });

        wallRB.addActionListener((e) -> {
            disableAllNonRadio();
            enableWallAttributes();
            select(TileType.wall);
        });

        msgRB.addActionListener((e) -> {
            disableAllNonRadio();
            enableMsgAttributes();
            select(TileType.message);
        });

        doorRB.addActionListener((e) -> {
            disableAllNonRadio();
            enableDoorAttributes();
            select(TileType.door);
        });

        itemRB.addActionListener((e) -> {
            disableAllNonRadio();
            enableItemAttributes();
            select(TileType.item);
        });

        enemyRB.addActionListener((e) -> {
            disableAllNonRadio();
            select(TileType.enemy);
        });

        // ID field:
        attributesPanel.add(l_time);
        attributesPanel.add(time);
        attributesPanel.add(l_value);
        attributesPanel.add(value);
        attributesPanel.add(l_sender);
        attributesPanel.add(sendercode);
        attributesPanel.add(l_message);
        attributesPanel.add(messagecode);

        attributesPanel.setBackground(Color.black);
        id.setBackground(Color.black);
        time.setBackground(Color.black);
        value.setBackground(Color.black);
        sendercode.setBackground(Color.black);
        messagecode.setBackground(Color.black);
        ceiling.setBackground(Color.black);

        // Checkboxes:
        buttonPanel.setBackground(Color.black);

        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.GREEN);
        submit.setToolTipText("Refreshes the tile setting. It'll expire by the next submit.");

        submit.addActionListener(e -> {
            refreshTileAttr();
        });
        /////////////////////////////////////////////

        ///////// Adding components: ////////////////
        // Checkboxes:
        buttonPanel.add(submit);

        ///// Main panels:
        // left
        mainPanel.add(radio);
        mainPanel.add(attributesPanel);
        mainPanel.add(buttonPanel);
        //left.add(submit);
        // right
        add(mainPanel);
        /////////////////////////////////////////////

        ///////// Events: ///////////////////////////
        // Radio buttons:
        // ID field:
        /////////////////////////////////////////////
        labels = new ArrayList<>();
        labels.addAll(Arrays.asList(
                id, time, value, sendercode, messagecode,
                ceiling,
                l_entity_id, l_time, l_value, l_sender, l_message, l_ceiling,
                ceiling
        ));

        setAllLabelsColor(Color.WHITE);
        disableAllNonRadio();

        refreshTileAttr();
    }

    private void setAllLabelsColor(Color c) {
        spriteRadio.setForeground(c);
        wallRadio.setForeground(c);
        msgRadio.setForeground(c);
        doorRadio.setForeground(c);
        itemRadio.setForeground(c);
        enemyRadio.setForeground(c);
        for (JComponent l : labels) {
            l.setForeground(c);
        }
    }

    private void disableAllNonRadio() {
        for (JComponent l : labels) {
            l.setEnabled(false);
            l.setVisible(false);
        }
    }

    private void enableSpriteAttributes() {
        disableAllNonRadio();
    }

    private void enableWallAttributes() {
        disableAllNonRadio();
    }

    private void enableMsgAttributes() {
        disableAllNonRadio();
        l_sender.setEnabled(true);
        l_message.setEnabled(true);
        time.setEnabled(true);
        sendercode.setEnabled(true);
        messagecode.setEnabled(true);
        l_time.setEnabled(true);

        l_sender.setVisible(true);
        l_message.setVisible(true);
        time.setVisible(true);
        sendercode.setVisible(true);
        messagecode.setVisible(true);
        l_time.setVisible(true);
    }

    private void enableDoorAttributes() {
        disableAllNonRadio();

        l_value.setVisible(true);
        value.setVisible(true);
        l_value.setEnabled(true);
        value.setEnabled(true);
    }

    private void enableItemAttributes() {
        disableAllNonRadio();
    }

    private void enableEnemyAttributes() {
        disableAllNonRadio();
    }

    public void select(TileType type) {
        switch (type) {
            case wall:
                enableWallAttributes();
                gui.chosenType = type;
                wallRB.setSelected(true);
                break;
            case door:
                enableDoorAttributes();
                gui.chosenType = type;
                doorRB.setSelected(true);
                break;
            case item:
                enableItemAttributes();
                gui.chosenType = type;
                itemRB.setSelected(true);
                break;
            case sprite:
                enableSpriteAttributes();
                gui.chosenType = type;
                spriteRB.setSelected(true);
                break;
            case enemy:
                enableEnemyAttributes();
                gui.chosenType = type;
                enemyRB.setSelected(true);
                break;
            case message:
                enableMsgAttributes();
                gui.chosenType = type;
                msgRB.setSelected(true);
                break;
            default:
                disableAllNonRadio();
                break;
        }
    }

    private void refreshTileAttr() {
        TileAttributes temp = gui.ta;

        switch (gui.chosenType) {
            case wall:
                break;
            case door:
                if (value.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Value is not defined!");
                    return;
                }
                temp.value = Integer.parseInt(value.getText());
                break;
            case item:
                if (value.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Value is not defined!");
                    return;
                }
                temp.value = Integer.parseInt(value.getText());
                break;
            case sprite:
                break;
            case enemy:
                break;
            case message:
                if (sendercode.getText().isEmpty() || messagecode.getText().isEmpty() || time.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "One of the message attributes is not defined!");
                    return;
                }
                temp.sendercose = Integer.parseInt(sendercode.getText());
                temp.msgcode = Integer.parseInt(messagecode.getText());
                temp.time = Integer.parseInt(time.getText());
                break;
            default:
                break;
        }
    }

}
