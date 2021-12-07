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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class CurrentOrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    protected TextView orderNumberText;
    protected TextView subtotalText;
    protected TextView taxText;
    protected TextView totalText;
    protected Button placeOrderButton;
    protected Button removePizzaButton;
    protected ListView pizzaList;
    protected double subtotal;
    protected double tax;
    protected double total;
    protected Order order;
    protected ArrayAdapter<Pizza> pizzaArrayAdapter;
    protected ArrayList<Pizza> removePizzas;
    protected DecimalFormat decimalFormat = new DecimalFormat();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order_layout);
        orderNumberText = findViewById(R.id.orderNumber);
        subtotalText = findViewById(R.id.subtotal);
        taxText = findViewById(R.id.tax);
        totalText = findViewById(R.id.total);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        pizzaList = findViewById(R.id.pizzaList);
        removePizzaButton = findViewById(R.id.removePizzaButton);
        decimalFormat.setMaximumFractionDigits(2);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        order = (Order) getIntent().getSerializableExtra("order");
//        System.out.println(order);
        orderNumberText.setText(String.valueOf(order.getPhoneNumber()));
        pizzaArrayAdapter = new ArrayAdapter<Pizza>(this, android.R.layout.simple_list_item_multiple_choice, order.getPizzas());
        pizzaList.setAdapter(pizzaArrayAdapter);
        pizzaList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        pizzaList.setOnItemClickListener(this);
        removePizzas = new ArrayList<>();
        Iterator<Pizza> iterator = order.getPizzas().iterator();
        while(iterator.hasNext()){
            Pizza buffPizza = iterator.next();
            subtotal += buffPizza.price();
        }
        tax = 0.06625 * subtotal;
        total = tax + subtotal;
        order.setOrderTotal(total);

        subtotalText.setText(decimalFormat.format(subtotal) + "$");
        taxText.setText(decimalFormat.format(tax) + "$");
        totalText.setText(decimalFormat.format(total) + "$");

        removePizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator<Pizza> iterator = removePizzas.iterator();
                while(iterator.hasNext()){
                    Pizza pizza = iterator.next();
                    order.getPizzas().remove(pizza);
                }
                removePizzas = new ArrayList<>();
                pizzaArrayAdapter.notifyDataSetChanged();
                pizzaList.setAdapter(pizzaArrayAdapter);

            }
        });

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.addOrderToStoreOrders(order);
                CharSequence text = "Order Placed";
                Toast toast = Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Pizza buffPizza = (Pizza) parent.getItemAtPosition(position);
        if(removePizzas.contains(buffPizza)){
            removePizzas.remove(buffPizza);
        }
        else removePizzas.add(buffPizza);
    }
}
