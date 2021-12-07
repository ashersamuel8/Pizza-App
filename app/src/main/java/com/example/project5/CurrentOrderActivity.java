package com.example.project5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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
import java.util.Iterator;

public class CurrentOrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private TextView orderNumberText;
    private TextView subtotalText;
    private TextView taxText;
    private TextView totalText;
    private Button placeOrderButton;
    private Button removePizzaButton;
    private ListView pizzaList;
    private double subtotal;
    private Order order;
    private ArrayAdapter<Pizza> pizzaArrayAdapter;
    private final DecimalFormat decimalFormat = new DecimalFormat();
    private Pizza removePizza;

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
        orderNumberText.setText(String.valueOf(order.getPhoneNumber()));
        pizzaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, order.getPizzas());
        pizzaList.setAdapter(pizzaArrayAdapter);
        pizzaList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        pizzaList.setOnItemClickListener(this);
        Iterator<Pizza> iterator = order.getPizzas().iterator();
        while(iterator.hasNext()){
            Pizza buffPizza = iterator.next();
            subtotal += buffPizza.price();
        }
        double tax = 0.06625 * subtotal;
        double total = tax + subtotal;
        order.setOrderTotal(total);

        subtotalText.setText(decimalFormat.format(subtotal) + "$");
        taxText.setText(decimalFormat.format(tax) + "$");
        totalText.setText(decimalFormat.format(total) + "$");

        removePizzaButton.setOnClickListener(view -> {
            if(removePizza == null){
                CharSequence text = "Select Pizzas to Remove";
                Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            order.getPizzas().remove(removePizza);
            removePizza = null;
            pizzaArrayAdapter.notifyDataSetChanged();
            pizzaList.setAdapter(pizzaArrayAdapter);

        });

        placeOrderButton.setOnClickListener(view -> {
            if(order.getPizzas().size() == 0){
                CharSequence text = "Empty orders cant be placed";
                Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            MainActivity.addOrderToStoreOrders(order);
            CharSequence text = "Order Placed";
            Toast toast = Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
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
        removePizza = (Pizza) parent.getItemAtPosition(position);
        pizzaList.setSelector(android.R.color.darker_gray);

    }
}
