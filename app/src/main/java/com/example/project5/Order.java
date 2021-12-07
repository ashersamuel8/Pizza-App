package com.example.project5;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class takes a customer's order and stores the customer's phone number
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public class Order implements Serializable {
    private long phoneNumber;
    private ArrayList<Pizza> pizzas;
    private double orderTotal;

    /**
     * This is the default constructor.
     */
    public Order() {
        this.pizzas = new ArrayList<>();
    }

    /**
     * Method to add a pizza to the order.
     * @param pizza
     */
    public void addPizza(Pizza pizza) {
        this.pizzas.add(pizza);
    }

    /**
     * Method to get order (phone) number
     */
    public long getPhoneNumber(){
        return this.phoneNumber;
    }

    /**
     * Method to set phone number
     */
    public void setPhoneNumber(long phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    /**
     * Method to get list of pizzas
     */
    public ArrayList<Pizza> getPizzas(){
        return this.pizzas;
    }

    /**
     * Method to set order total
     */
    public void setOrderTotal(double total){
        this.orderTotal = total;
    }

    /**
     * This method converts order to string
     * @return String
     */
    @Override
    public String toString(){
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        return "Phone Number: " + this.phoneNumber + " || Pizzas: " + this.pizzas.toString() + " || Total: " +
                decimalFormat.format(this.orderTotal);
    }
}
