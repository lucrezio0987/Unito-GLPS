package com.example.prova_server.model;

import javafx.beans.property.SimpleStringProperty;

public class DataModel {
    private SimpleStringProperty textAreaProperty = null;

    public DataModel() {
        textAreaProperty = new SimpleStringProperty();
    }

    public SimpleStringProperty getTextAreaProperty() {
        return textAreaProperty;
    }

    public void addElement() {
        for(int i = 0; i< 50; ++i) textAreaProperty.set(textAreaProperty.get() + "ciaoooooo\n");
    }
}
