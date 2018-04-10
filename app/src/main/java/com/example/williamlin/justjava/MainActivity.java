package com.example.williamlin.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int number =1;
    int whipped_cream_price=1;
    int chocolate_price=2;
    int addWhippedCream=0;
    int addChocolate=0;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*This method is called when the order button is clicked*/
    public void submitOrder(View view){

        EditText nameFiled = (EditText) findViewById(R.id.name_editText);
        String name = nameFiled.getText().toString();

        String orderSummary= createSummary(name,number,hasChocolate,hasWhippedCream);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just for Java Order of "+name);
        intent.putExtra(Intent.EXTRA_TEXT,orderSummary);
        if(intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }
        resetOrder();

    }

    @SuppressLint("StringFormatInvalid")
    public String createSummary(String name, int number, boolean hasChocolate,
                                boolean hasWhippedCream){
        String orderSummary;

        orderSummary = getString(R.string.order_summary)
                        +"\n" + getString(R.string.order_summary_name)+name
                        +"\n"+ getString(R.string.order_summary_whipped_cream)+hasWhippedCream
                        +"\n"+ getString(R.string.order_summary_chocolate)+ hasChocolate
                        +"\n"+ getString(R.string.order_summary_quantity) + number
                        +"\n"+getString(R.string.order_summary_total)
                        + NumberFormat.getCurrencyInstance().format(number*10
                            +addChocolate+addWhippedCream);

        return orderSummary;
    }

    public void resetOrder(){

        EditText nameFiled = (EditText) findViewById(R.id.name_editText);
        nameFiled.setText("");

    }

    /*This method is called when the remove order button is clicked*/
    public void removeOrder(View view){
        number = 0;
        addWhippedCream=0;
        addChocolate=0;

    }

    /*This method displays the given quantity value on the screen*/
    private void display(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);


    }



    public void orderIncreasment(View view){
        if(number <100)
            number++;
        else
            Toast.makeText(this,"You cannot have more than 100 coffees",
                    Toast.LENGTH_SHORT).show();
        display(number);
        if(hasWhippedCream == true){
            addWhippedCream = number*whipped_cream_price;
        }
        if(hasChocolate == true){
            addChocolate = number*chocolate_price;
        }

    }

    public void orderDecreasment(View view) {
        if (number > 1)
            number--;
        display(number);
        if(hasWhippedCream==true){
            addWhippedCream = number*whipped_cream_price;
        }
        if(hasChocolate == true) {
            addChocolate = number * chocolate_price;
        }
    }

    public void toppingCheck(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if(checked == true) {
            hasWhippedCream = true;
        }else{
            hasWhippedCream = false;
            addWhippedCream=0;
        }
    }
    public void toppingCheck1(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if(checked == true) {
            hasChocolate = true;
        }else{
            hasChocolate = false;
            addChocolate =0;
        }
    }
}
