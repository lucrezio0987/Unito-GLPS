package model;

import javafx.beans.property.SimpleDoubleProperty;

public class BeanDipendente {
    // Define a variable to store the property
    private SimpleDoubleProperty amount = new SimpleDoubleProperty();

    // Define a getter for the property's value
    public final double getAmount(){
        return amount.get();
    }
    // Define a getter for the property's value
    public void setAmount(Double val) {
        amount.set(val);
    }

    // Define a getter for the property itself
    public SimpleDoubleProperty amountProperty() {
        return amount;
    }
}
