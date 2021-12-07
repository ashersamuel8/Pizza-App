package com.example.project5;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class defines a pizza and its properties (size, toppings, and price)
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public abstract class Pizza implements Serializable {
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;
    protected int minToppings;

    /**
     * Abstract method that calculates the price, it must be implemented in all subclasses.
     * @return double
     */
    public abstract double price();

    /**
     * Method to add a topping to the pizza.
     * @param topping
     */
    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    /**
     * Method to set the size of the pizza.
     * @param size
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Method to clear toppings
     */
    public void clearToppings(){
        toppings.clear();
    }
    /**
     * Method to convert Pizza to String
     * @return String
     */
    @Override
    public String toString(){
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        return this.getClass().getSimpleName() + " || Size: " + this.size + " || Toppings: " + toppings.toString() +
                " || Sub Total: " + decimalFormat.format(price()) +"$";
    }
}
