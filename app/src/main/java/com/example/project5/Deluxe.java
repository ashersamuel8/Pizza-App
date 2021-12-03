package com.example.project5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class defines a deluxe pizza.
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public class Deluxe extends Pizza {

    private Topping[] remainingToppings = {Topping.OLIVES, Topping.PINEAPPLE};
    private List<Topping> additionalToppingsList = Arrays.asList(remainingToppings);
    private ArrayList<Topping> additionalToppings =  new ArrayList<>();

    /**
     * This is the default constructor.
     */
    public Deluxe() {
        this.addTopping(Topping.HAM);
        this.addTopping(Topping.TOMATOES);
        this.addTopping(Topping.MUSHROOMS);
        this.addTopping(Topping.PEPPERONI);
        this.addTopping(Topping.SAUSAGE);
        additionalToppings.addAll(additionalToppingsList);
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

    /**
     * A method to get additional toppings of pepperoni pizza
     * @return additionalToppings
     */
    public ArrayList<Topping> getAdditionalToppings(){
        return additionalToppings;
    }

}
