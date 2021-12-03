package com.example.project5;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class defines a pizza and its properties (size, toppings, and price)
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public abstract class Pizza {
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;

    /**
     * Abstract method that calculates the price, it must be implemented in all subclasses.
     * @return double
     */
    public abstract double price();

    /**
     * Abstract method that returns additional toppings of a pizza
     */
    public abstract ArrayList<Topping> getAdditionalToppings();

    /**
     * Method to add a topping to the pizza.
     * @param topping
     */
    public void addTopping(Topping topping) {
        this.toppings.add(topping);
    }

    /**
     * Method to set the size of the pizza.
     * @param size
     */
    public void setSize(Size size) {
        this.size = size;
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
