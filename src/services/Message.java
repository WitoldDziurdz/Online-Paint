package services;

import sharps.Sharp;
import java.io.Serializable;


public class Message implements Serializable {
    private TypeMessage type;
    private Sharp data;
    private String name;

    public Message(TypeMessage type) {
        this.type = type;
        this.data = null;
        this.name = "";
    }

    public Message(String name, TypeMessage type) {
        this.name = name;
        this.type = type;
        this.data = null;
    }

    public Message(String name, TypeMessage type, Sharp data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public TypeMessage getType() {
        return type;
    }

    public Sharp getData() {
        return data;
    }

    public String getName() {
        return name;
    }
}
