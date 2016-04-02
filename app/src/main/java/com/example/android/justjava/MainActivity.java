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

    private int quantity = 2;
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
        this.displayQuantity();
    }

    private void displayQuantity(){
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText(String.format("%d", this.quantity));
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
        displayQuantity();
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
        String message = this.composeMessage(price);
        String subject = getResources().getString(R.string.order_summary_email_subject) + getResources().getString(R.string.app_name);
        this.composeEmail(subject, message);
    }

    private String composeMessage(int price){
        String message = "";
        EditText et = (EditText)findViewById(R.id.name_field);
        String name = et.getText().toString();
        message += getResources().getString(R.string.order_summary_name) + name + "\n";
        message += getResources().getString(R.string.order_summary_whipped_cream) + " " + this.hasWhippedCream + "\n";
        message += getResources().getString(R.string.order_summary_chocolate) + " " + this.hasChocolate + "\n";
        message += getResources().getString(R.string.order_summary_quantity) + this.quantity + "\n";
        message += getResources().getString(R.string.order_summary_price) + ": $" + price + "\n";
        message += getResources().getString(R.string.thank_you);
        return message;
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
