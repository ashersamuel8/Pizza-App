package com.example.project5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class StoreOrdersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner orderSpinner;
    private ListView pizzaList;
    private Button goBackButton;
    private Button removeOrderButton;
    private TextView subtotalText;
    private TextView totalText;
    private TextView taxText;
    private final DecimalFormat decimalFormat = new DecimalFormat();
    private Order order;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_orders);
        orderSpinner = findViewById(R.id.orderIDSpinner);
        pizzaList = findViewById(R.id.pizzaList);
        goBackButton = findViewById(R.id.goBackButton);
        removeOrderButton = findViewById(R.id.removeOrderButton);
        subtotalText = findViewById(R.id.subtotalText);
        taxText = findViewById(R.id.taxText);
        totalText = findViewById(R.id.totalText);
        decimalFormat.setMaximumFractionDigits(2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Long> orderNumbers = new ArrayList<>();
        for (Order buffOrder : MainActivity.storeOrders.getOrders()) {
            orderNumbers.add(buffOrder.getPhoneNumber());
        }

        ArrayAdapter<Long> orderIDs = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, orderNumbers);
        orderSpinner.setAdapter(orderIDs);
        orderSpinner.setOnItemSelectedListener(this);
        removeOrderButton.setOnClickListener(view -> {
            CharSequence text;
            if(order != null) {
                MainActivity.storeOrders.getOrders().remove(order);
                finish();
                startActivity(getIntent());
                text = "Order Removed";
            }
            else{
                text = "No Orders to Remove";
            }
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
        });

        goBackButton.setOnClickListener(view -> finish());

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ArrayAdapter<Pizza> pizzaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                MainActivity.storeOrders.getOrders().get(position).getPizzas());
        pizzaList.setAdapter(pizzaArrayAdapter);
        order = MainActivity.storeOrders.getOrders().get(position);
        Iterator<Pizza> iterator = MainActivity.storeOrders.getOrders().get(position).getPizzas().iterator();
        double subtotal = 0.00;
        double tax;
        double total;
        while(iterator.hasNext()){
            Pizza buffPizza = iterator.next();
            subtotal += buffPizza.price();
        }
        tax = 0.06625 * subtotal;
        total = tax + subtotal;

        subtotalText.setText(decimalFormat.format(subtotal) + "$");
        taxText.setText(decimalFormat.format(tax) + "$");
        totalText.setText(decimalFormat.format(total) + "$");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        super.finish();
    }
}
