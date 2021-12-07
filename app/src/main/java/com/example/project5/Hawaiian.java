package com.example.project5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class defines a Hawaiian pizza.
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public class Hawaiian extends Pizza implements Serializable {

    /**
     * This is the default constructor.
     */
    public Hawaiian() {
        this.addTopping(Topping.HAM);
        this.addTopping(Topping.PINEAPPLE);
        this.minToppings = 2;
    }

    /**
     * This method calculates and returns the price of the pizza.
     * @return double
     */
    public double price() {
        double price = 10.99;

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

        if(this.toppings.size() > 2) {
            price += (this.toppings.size() - 2) * 1.49;
        }

        return price;
    }



}
