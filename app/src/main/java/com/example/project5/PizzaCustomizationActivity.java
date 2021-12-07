package com.example.project5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.security.ProtectionDomain;
import java.text.DecimalFormat;


public class PizzaCustomizationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    protected ImageView pizzaImage;
    protected TextView pizzaLabel;
    protected Spinner sizeSpinner;
    protected ListView toppingsListView;
    protected TextView price;
    protected Order order;
    protected Pizza pizza;
    protected Button addToOrderButton;
    Size[] sizes = {Size.SMALL, Size.MEDIUM, Size.LARGE};
    ArrayAdapter<Size> sizeArrayAdapter;
    Topping[] toppingsArray = {Topping.PEPPERONI, Topping.HAM, Topping.SAUSAGE, Topping.TOMATOES, Topping.OLIVES, Topping.MUSHROOMS, Topping.PINEAPPLE};
    ArrayAdapter<Topping> toppingArrayAdapter;
    DecimalFormat decimalFormat = new DecimalFormat();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_customization_layout);
        Intent intent = getIntent();
        String pizzaType = intent.getStringExtra("pizzaType");
//        orderID = intent.getStringExtra("phoneNumber");
        sizeSpinner = (Spinner) findViewById(R.id.sizeSpinner);
        pizzaImage = (ImageView) findViewById(R.id.pizzaImage);
        price = (TextView) findViewById(R.id.price);
        pizzaLabel = (TextView) findViewById(R.id.pizzaLabel);
        toppingsListView = (ListView) findViewById(R.id.toppingsList);
        sizeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizes);
        toppingArrayAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_multiple_choice, toppingsArray);
        decimalFormat.setMaximumFractionDigits(2);
        addToOrderButton = (Button) findViewById(R.id.addToOrderButton);
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
        price.setText(String.valueOf(decimalFormat.format(pizza.price())) + "$");
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
        addToOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.addPizza(pizza);
                goHome();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Size size = (Size) parent.getItemAtPosition(position);
        pizza.setSize(size);
        price.setText(String.valueOf(decimalFormat.format(pizza.price())) + "$");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
               goHome();
               return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Topping topping = (Topping) parent.getItemAtPosition(position);
        if(pizza.toppings.contains(topping)){
            pizza.toppings.remove(topping);
        }
        else{
            pizza.toppings.add(topping);
        }

        price.setText(String.valueOf(decimalFormat.format(pizza.price())) + "$");

        if(pizza.toppings.size() < pizza.minToppings){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage("Price of the pizza doesn't decrease if the number of toppings drop below minimum");
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Warning");

            alertBuilder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog warning = alertBuilder.create();
            warning.show();
        }
    }

    private void goHome(){
        Intent goHomeIntent = new Intent(this, MainActivity.class);
        goHomeIntent.putExtra("order", (Serializable) order);
        startActivity(goHomeIntent);
        super.finish();
    }
}
