package com.example.project5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class defines a Hawaiian pizza.
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public class Hawaiian extends Pizza {

    private Topping[] remainingToppings = {Topping.SAUSAGE, Topping.TOMATOES, Topping.OLIVES, Topping.MUSHROOMS};
    private List<Topping> additionalToppingsList = Arrays.asList(remainingToppings);
    private ArrayList<Topping> additionalToppings =  new ArrayList<>();

    /**
     * This is the default constructor.
     */
    public Hawaiian() {
        this.addTopping(Topping.HAM);
        this.addTopping(Topping.PINEAPPLE);
        additionalToppings.addAll(additionalToppingsList);
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

    /**
     * A method to get additional toppings of pepperoni pizza
     * @return additionalToppings
     */
    public ArrayList<Topping> getAdditionalToppings(){
        return additionalToppings;
    }

}
