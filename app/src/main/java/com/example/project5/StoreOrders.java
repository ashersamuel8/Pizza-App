package com.example.project5;
import java.util.ArrayList;
/**
 * This class stores all of the orders that have been placed
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */
public class StoreOrders {
    private ArrayList<Order> orders;

    /**
     * This is the default constructor.
     */
    public StoreOrders() {
        this.orders = new ArrayList<>();
    }

    /**
     * This method adds an order to the list.
     * @param order
     */
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    /**
     * This method returns the list of store orders
     * @returns ArrayList<Order>
     */
    public ArrayList<Order> getOrders(){
        return this.orders;
    }

}
