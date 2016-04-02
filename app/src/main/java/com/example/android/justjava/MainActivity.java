package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private int quantity = 1;
    private boolean hasWhippedCream = false;
    private boolean hasChocolate = false;
    /**
     * Increase number of coffees to order by 1
     */
    public void increment(View view){
        if(this.quantity < 100){
            this.quantity++;
        } else {
            this.popUp("You cannot order more than 100 coffees.");
        }
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * Decrease number of coffees to order by 1
     */
    public void decrement(View view){
        if(this.quantity > 1){
            this.quantity--;
        } else {
            this.popUp("You cannot order fewer than 1 coffee.");
        }
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    private void popUp(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice() {
        int price  = this.quantity * 5;
        if(this.hasChocolate){
            price += 2;
        }
        if(this.hasWhippedCream){
            price += 1;
        }
        return price;
    }

    public void setHasWhippedCream(View v){
        CheckBox cb = (CheckBox) v;
        this.hasWhippedCream = cb.isChecked();
    }

    public void setHasChocolate(View v){
        CheckBox cb = (CheckBox) v;
        this.hasChocolate = cb.isChecked();
    }

    /**
     * Creates a custom summary message
     */
    public void sendOrderSummary(View v){
        int price = this.calculatePrice();
        String message = "";
        EditText et = (EditText)findViewById(R.id.name_field);
        String name = et.getText().toString();
        message += "Name: " + name + "\n";
        message += "Add whipped cream? " + this.hasWhippedCream + "\n";
        message += "Add chocolate? " + this.hasChocolate + "\n";
        message += "Quantity: " + this.quantity + "\n";
        message += "Total: $" + price + "\n";
        message += "Thank you!";
        //return message;
        String subject = getResources().getString(R.string.app_name);
        this.composeEmail(subject, message);
    }

    private void composeEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
