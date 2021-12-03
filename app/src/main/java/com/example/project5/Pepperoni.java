package com.example.project5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class defines a pepperoni pizza.
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public class Pepperoni extends Pizza {

    private Topping[] remainingToppings = {Topping.HAM, Topping.SAUSAGE, Topping.TOMATOES, Topping.OLIVES, Topping.MUSHROOMS, Topping.PINEAPPLE};
    private List<Topping> additionalToppingsList = Arrays.asList(remainingToppings);
    private ArrayList<Topping> additionalToppings =  new ArrayList<>();

    /**
     * This is the default constructor.
     */
    public Pepperoni() {
        this.addTopping(Topping.PEPPERONI);
        additionalToppings.addAll(additionalToppingsList);
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

    /**
     * A method to get additional toppings of pepperoni pizza
     * @return additionalToppings
     */
    public ArrayList<Topping> getAdditionalToppings(){
        return additionalToppings;
    }

}
