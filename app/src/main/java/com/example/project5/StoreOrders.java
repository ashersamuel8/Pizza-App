package com.example.project5;
import java.util.ArrayList;
import java.util.Iterator;

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

    /**
     * This ethod checks if a number already exists in the Store Orders
     * @returns true if exists, false otherwise
     */
    public boolean numberExists(String number){
        Iterator<Order> iterator = orders.iterator();
        while(iterator.hasNext()){
            Order buffOrder = iterator.next();
            if(buffOrder.getPhoneNumber() == Long.parseLong(number)) return true;
        }
        return false;
    }
}
