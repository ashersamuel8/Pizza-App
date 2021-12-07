package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton deluxeButton;
    private ImageButton pepperoniButton;
    private ImageButton hawaiianButton;
    private Button currentOrderButton;
    private Button storeOrderButton;
    private EditText phoneNumber;
    private Order order;
    protected static StoreOrders storeOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deluxeButton = findViewById(R.id.deluxeButton);
        hawaiianButton = findViewById(R.id.hawaiinButton);
        pepperoniButton = findViewById(R.id.pepperoniButton);
        currentOrderButton = findViewById(R.id.currentOrderButton);
        storeOrderButton = findViewById(R.id.storeOrdersButton);
        phoneNumber = findViewById(R.id.editTextNumber);
        if(storeOrders == null) storeOrders = new StoreOrders();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        deluxeButton.setOnClickListener(view -> openPizzaCustomizationView("deluxe"));

        pepperoniButton.setOnClickListener(view -> openPizzaCustomizationView("pepperoni"));

        hawaiianButton.setOnClickListener(view -> openPizzaCustomizationView("hawaiian"));

        currentOrderButton.setOnClickListener(view -> openCurrentOrderActivity());

        storeOrderButton.setOnClickListener((view -> openStoreOrdersActivity()));

        order = (Order) getIntent().getSerializableExtra("order");
        if(order != null){
            phoneNumber.setText(String.valueOf(order.getPhoneNumber()));
        }

        phoneNumber.setOnTouchListener((view, event) -> {
            if (order != null){
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Current order is in progress. Would you like to start a new order?");
                alert.setCancelable(true);
                alert.setTitle("Alert");
                alert.setPositiveButton(
                    "Yes",
                        (dialog, id) -> {
                            phoneNumber.setText("");
                            dialog.cancel();
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        });

                alert.setNegativeButton(
                    "No",
                        (dialog, id) -> {
                            dialog.cancel();
                            startActivity(getIntent());
                        });

                AlertDialog alertBox = alert.create();
                alertBox.show();
            }
            return false;
        });
    }

    private void openPizzaCustomizationView(String pizzaType){

        phoneNumber = findViewById(R.id.editTextNumber);
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
        pizzaCustomizationIntent.putExtra("order", order);
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
        currentOrderIntent.putExtra("order", order);
        startActivity(currentOrderIntent);
    }

    private void openStoreOrdersActivity(){
        Intent storeOrdersIntent = new Intent(this, StoreOrdersActivity.class);
        startActivity(storeOrdersIntent);
    }

    public static void addOrderToStoreOrders(Order order){
        storeOrders.addOrder(order);
    }
}