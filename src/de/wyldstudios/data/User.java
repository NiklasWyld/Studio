package de.wyldstudios.data;

import java.io.Serializable;

public class User implements Serializable {
    public String name;
    public String id;
    public String old;
    public String gender;
    public String information;

    public User() {}

    public User(String name, String id, String old, String information, String gender) {
        this.name = name;
        this.id = id;
        this.old = old;
        this.gender = gender;
        this.information = information;
    }
}
