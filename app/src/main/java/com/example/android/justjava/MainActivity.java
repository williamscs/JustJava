package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private int quantity = 1;
    /**
     * Increase number of coffees to order by 1
     */
    public void increment(View view){
        this.quantity++;
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * Decrease number of coffees to order by 1
     */
    public void decrement(View view){
        this.quantity--;
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String summary = this.createOrderSummary(this.calculatePrice());

        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(summary);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice() {
        return this.quantity * 5;
    }

    /**
     * Creates a custom summary message
     */
    private String createOrderSummary(int price){
        String message = "";
        message += "Name: Kaptain Kunal\n";
        message += "Quantity: " + this.quantity + "\n";
        message += "Total: $" + price + "\n";
        message += "Thank you!";
        return message;
    }
}
