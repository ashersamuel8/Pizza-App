package com.example.project5;

/**
 * This class actually creates the pizza objects and
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public class PizzaMaker {

    /**
     * Method to actually create the pizzas using the subclasses of Pizza, this follows the "Factory Method"
     * @param flavor
     * @return
     */
    public static Pizza createPizza(String flavor) {
        //write the code for creating different instances of subtypes of pizza

        Pizza pizza = null;

        switch (flavor) {
            case "Pepperoni":
                pizza = new Pepperoni();
                break;

            case "Hawaiian":
                pizza = new Hawaiian();
                break;

            case "Deluxe":
                pizza = new Deluxe();
                break;
        }

        return pizza;
    }
}
