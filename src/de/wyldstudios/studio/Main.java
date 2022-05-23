package de.wyldstudios.studio;

import de.wyldstudios.data.Ereignis;
import de.wyldstudios.data.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Main {
    public static LoginPanel loginPanel = new LoginPanel(UI.frame);
    public static boolean running;
    public static ImageIcon img = new ImageIcon("maus.jpg");
    public static Settings settings;

    public static void main(String[] args) {
        // Starts to main method of ui
        UI.startApp("Wyld-Studios Studio");
        settings = loadSettings();
        setIcon();
        loginPanel.setTitle(settings.company + " Studio");
        File directory = new File("./users");
        if(!directory.exists()) {
            directory.mkdir();
        }
    }

    public static void setIcon() {
        Window[] windows = Window.getWindows();
        for(Window win : windows) {
            win.setIconImage(img.getImage());
        }
    }

    public static Settings loadSettings() {
        try (FileInputStream fis = new FileInputStream("./settings.wssf");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Settings s = (Settings) ois.readObject();

            img = s.icon;

            return s;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new Settings(new ImageIcon("./maus.jpg"), "Wyld-Studios");
    }

    public static void writeSettings(Settings settings) {
        try (FileOutputStream fos = new FileOutputStream("./settings.wssf");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Write data to file
            oos.writeObject(settings);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
