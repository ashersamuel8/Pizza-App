package com.example.project5;

import java.io.Serializable;

/**
 * This class defines a pepperoni pizza.
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public class Pepperoni extends Pizza implements Serializable {

    /**
     * This is the default constructor.
     */
    public Pepperoni() {
        this.addTopping(Topping.PEPPERONI);
        this.minToppings = 1;
    }

    /**
     * This method calculates and returns the price of the pizza.
     * @return double
     */
    public double price() {
        double price = 8.99;

        switch (this.size) {
            case SMALL:
                price += 0;
                break;

            case MEDIUM:
                price += 2;
                break;

            case LARGE:
                price += 4;
                break;
        }

        if(this.toppings.size() > 1) {
            price += (this.toppings.size() - 1) * 1.49;
        }

        return price;
    }

}
