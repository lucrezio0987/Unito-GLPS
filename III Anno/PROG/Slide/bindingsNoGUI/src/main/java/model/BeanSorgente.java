package model;

import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author liliana
 */
public class BeanSorgente {

    // Define a variable to store the property
    private SimpleDoubleProperty amount = new SimpleDoubleProperty();

    // Define a getter for the property's value
    public final double getAmount(){
        return amount.get();
    }

    // Define a setter for the property's value
    public final void setAmount(double value){
        amount.set(value);
    }

    // Define a getter for the property itself
    public SimpleDoubleProperty amountProperty() {
        return amount;
    }
}

