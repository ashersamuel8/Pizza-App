package com.example.project5;

import java.io.Serializable;

/**
 * This class defines a deluxe pizza.
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public class Deluxe extends Pizza implements Serializable {

    /**
     * This is the default constructor.
     */
    public Deluxe() {
        this.addTopping(Topping.HAM);
        this.addTopping(Topping.TOMATOES);
        this.addTopping(Topping.MUSHROOMS);
        this.addTopping(Topping.OLIVES);
        this.addTopping(Topping.SAUSAGE);
        this.minToppings = 5;
    }

    /**
     * This method calculates and returns the price of the pizza.
     * @return double
     */
    public double price() {
        double price = 12.99;

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

        if(this.toppings.size() > 5) {
            price += (this.toppings.size() - 5) * 1.49;
        }

        return price;
    }


}
