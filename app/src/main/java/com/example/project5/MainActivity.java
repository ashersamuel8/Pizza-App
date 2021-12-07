package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    protected ImageButton deluxeButton;
    protected ImageButton pepperoniButton;
    protected ImageButton hawaiianButton;
    protected Button currentOrderButton;
    protected Button storeOrderButton;
    protected EditText phoneNumber;
    protected Order order;
    protected static StoreOrders storeOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deluxeButton = (ImageButton) findViewById(R.id.deluxeButton);
        hawaiianButton = (ImageButton) findViewById(R.id.hawaiinButton);
        pepperoniButton = (ImageButton) findViewById(R.id.pepperoniButton);
        currentOrderButton = (Button) findViewById(R.id.currentOrderButton);
        storeOrderButton = (Button) findViewById(R.id.storeOrdersButton);
        phoneNumber = (EditText) findViewById(R.id.editTextNumber);
        if(storeOrders == null) storeOrders = new StoreOrders();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        deluxeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPizzaCustomizationView("deluxe");
            }
        });

        pepperoniButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPizzaCustomizationView("pepperoni");
            }
        });

        hawaiianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPizzaCustomizationView("hawaiian");
            }
        });

        currentOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCurrentOrderActivity();
            }
        });

        storeOrderButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStoreOrdersActivity();
            }
        }));

        order = (Order) getIntent().getSerializableExtra("order");
        if(order != null){
//            System.out.println(order);
            phoneNumber.setText(String.valueOf(order.getPhoneNumber()));
        }

        phoneNumber.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (order != null){
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("Current order is in progress. Would you like to start a new order?");
                    alert.setCancelable(true);
                    alert.setTitle("Alert");
                    alert.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                order = new Order();
                                phoneNumber.setText("");
                                dialog.cancel();
                            }
                        });

                    alert.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
                return false;
            }
        });

    }

    private void openPizzaCustomizationView(String pizzaType){

        phoneNumber = (EditText) findViewById(R.id.editTextNumber);
        if(phoneNumber.getText().toString().length() != 10 ){
            Context context = getApplicationContext();
            CharSequence text = "Enter a Valid Phone Number";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        else if(storeOrders.numberExists(phoneNumber.getText().toString())){
            CharSequence text = "Phone Number already exists; enter a new number";
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (order ==null) order = new Order();
        order.setPhoneNumber(Long.parseLong(phoneNumber.getText().toString()));
        Intent pizzaCustomizationIntent = new Intent(this, PizzaCustomizationActivity.class);
        pizzaCustomizationIntent.putExtra("pizzaType", pizzaType);
        pizzaCustomizationIntent.putExtra("order", (Serializable) order);
        startActivityForResult(pizzaCustomizationIntent, 0);

    }


    private void openCurrentOrderActivity(){
        if(order == null){
            CharSequence text = "There are no items in your cart; add items to proceed";
            Toast toast = Toast.makeText(getApplicationContext(), text,Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Intent currentOrderIntent = new Intent(this, CurrentOrderActivity.class);
        currentOrderIntent.putExtra("order", (Serializable) order);
//        System.out.println(order);
        startActivity(currentOrderIntent);
    }

    private void openStoreOrdersActivity(){
        Intent storeOrdersIntent = new Intent(this, StoreOrdersActivity.class);
        startActivity(storeOrdersIntent);
    }

    public static void addOrderToStoreOrders(Order order){
        storeOrders.addOrder(order);
        System.out.println(storeOrders.getOrders());
        System.out.println("*************************");
    }
}