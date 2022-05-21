package de.wyldstudios.data;

import javax.swing.*;
import java.io.Serializable;

public class Settings implements Serializable {
    public ImageIcon icon;
    public String company;

    public Settings(ImageIcon icon, String company) {
        this.icon = icon;
        this.company = company;
    }
}
