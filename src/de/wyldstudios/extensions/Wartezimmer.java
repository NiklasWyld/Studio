package de.wyldstudios.extensions;

import de.wyldstudios.studio.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Wartezimmer extends JDialog {
    public Wartezimmer() {
        setTitle("Wartezimmer");
        setLayout(null);
        setResizable(false);
        setSize(510, 700);
        components();
        setVisible(true);
    }

    private void components() {
        DefaultListModel model = new DefaultListModel();
        JList list = new JList(model);
        JScrollPane list_scroll = new JScrollPane(list);
        JTextField username_add = new JTextField();
        JButton add = new JButton("Hinzufügen");
        JButton del = new JButton("Entfernen");

        list_scroll.setViewportView(list);
        list_scroll.setBounds(30, 30, 430, 500);
        username_add.setBounds(30, 550, 430, 50);
        add.setBounds(30, 610, 200, 30);
        del.setBounds(260, 610, 200, 30);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!username_add.getText().isBlank()) {
                    model.addElement(username_add.getText());
                    username_add.setText("");
                }
            }
        });

        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list.getSelectedValue() != null) {
                    model.removeElement(list.getSelectedValue().toString());
                }
            }
        });

        this.add(list_scroll);
        this.add(username_add);
        this.add(add);
        this.add(del);
    }
}
