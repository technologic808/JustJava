/**
 * Java Code
 */

package com.example.android.justjava;


import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method increments the value of the quantity by one
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(MainActivity.this,
                    R.string.max_cups_toast,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method decrements the value of the quantity by one
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(MainActivity.this,
                    R.string.min_cups_toast,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText userName = findViewById(R.id.name_text);
        String getName = userName.getText().toString();

        CheckBox whippedCream = findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, getName);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject) + getName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasChocolate    if the user wants chocolate
     * @param hasWhippedCream if the user wants whipped cream
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCream) {
            basePrice += 1;
        }
        if (hasChocolate) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    /**
     * This method creates the order summary based on the input parameters and returns it as a string
     *
     * @param price           contains the total cost of the order
     * @param hasWhippedCream is whether or not the user wants whipped cream
     * @param hasChocolate    is whwther user wants chocolate or not
     */
    private String createOrderSummary(int price, boolean hasWhippedCream,
                                      boolean hasChocolate, String nameText) {
        String orderSummary = getString(R.string.order_summary_name);
        orderSummary += nameText;
        orderSummary += "\n";
        orderSummary += getString(R.string.order_summary_whipped_cream);
        orderSummary += hasWhippedCream;
        orderSummary += "\n";
        orderSummary += getString(R.string.order_summary_chocolate);
        orderSummary += hasChocolate;
        orderSummary += "\n";
        orderSummary += getString(R.string.order_summary_quantity);
        orderSummary += quantity;
        orderSummary += "\n";
        orderSummary += getString(R.string.order_summary_total);
        orderSummary += price;
        orderSummary += "\n";
        orderSummary += getString(R.string.order_summary_greeting);
        return orderSummary;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}