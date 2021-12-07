package com.example.project5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;


public class PizzaCustomizationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private ImageView pizzaImage;
    private TextView pizzaLabel;
    private Spinner sizeSpinner;
    private ListView toppingsListView;
    private TextView price;
    private Order order;
    private Pizza pizza;
    private Button addToOrderButton;
    private final Size[] sizes = {Size.SMALL, Size.MEDIUM, Size.LARGE};
    private ArrayAdapter<Size> sizeArrayAdapter;
    private final Topping[] toppingsArray = {Topping.PEPPERONI, Topping.HAM, Topping.SAUSAGE, Topping.TOMATOES, Topping.OLIVES, Topping.MUSHROOMS, Topping.PINEAPPLE};
    private ArrayAdapter<Topping> toppingArrayAdapter;
    private final DecimalFormat decimalFormat = new DecimalFormat();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_customization_layout);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        pizzaImage = findViewById(R.id.pizzaImage);
        price = findViewById(R.id.price);
        pizzaLabel = findViewById(R.id.pizzaLabel);
        toppingsListView = findViewById(R.id.toppingsList);
        sizeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizes);
        toppingArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, toppingsArray);
        decimalFormat.setMaximumFractionDigits(2);
        addToOrderButton = findViewById(R.id.addToOrderButton);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String pizzaType = intent.getStringExtra("pizzaType");
        int resourceID = getResources().getIdentifier("com.example.project5:drawable/" + pizzaType, null, null);
        pizzaImage.setImageResource(resourceID);
        pizzaLabel.setText(pizzaType + " pizza");
        sizeSpinner.setAdapter(sizeArrayAdapter);
        toppingsListView.setAdapter(toppingArrayAdapter);
        toppingsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        pizza = PizzaMaker.createPizza(pizzaType);
        pizza.setSize(Size.SMALL);
        price.setText(decimalFormat.format(pizza.price()) + "$");
        sizeSpinner.setOnItemSelectedListener(this);
        toppingsListView.setOnItemClickListener(this);
        switch(pizzaType){
            case "deluxe":
                toppingsListView.setItemChecked(1, true);
                toppingsListView.setItemChecked(2, true);
                toppingsListView.setItemChecked(3, true);
                toppingsListView.setItemChecked(4, true);
                toppingsListView.setItemChecked(5, true);
                break;

            case "pepperoni":
                toppingsListView.setItemChecked(0, true);
                break;

            case "hawaiian":
                toppingsListView.setItemChecked(1, true);
                toppingsListView.setItemChecked(6, true);
                break;
        }
        order = (Order) intent.getSerializableExtra("order");
        addToOrderButton.setOnClickListener(view -> {
            order.addPizza(pizza);
            goHome();
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Size size = (Size) parent.getItemAtPosition(position);
        pizza.setSize(size);
        price.setText(decimalFormat.format(pizza.price()) + "$");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            goHome();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Topping topping = (Topping) parent.getItemAtPosition(position);
        if(pizza.toppings.contains(topping)){
            pizza.toppings.remove(topping);
        }
        else{
            pizza.toppings.add(topping);
        }

        price.setText(decimalFormat.format(pizza.price()) + "$");

        if(pizza.toppings.size() < pizza.minToppings){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage("Price of the pizza doesn't decrease if the number of toppings drop below minimum");
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Warning");

            alertBuilder.setPositiveButton(
                    "OK",
                    (dialog, id1) -> dialog.cancel());

            AlertDialog warning = alertBuilder.create();
            warning.show();
        }
    }

    private void goHome(){
        Intent goHomeIntent = new Intent(this, MainActivity.class);
        goHomeIntent.putExtra("order", order);
        startActivity(goHomeIntent);
        super.finish();
    }
}
