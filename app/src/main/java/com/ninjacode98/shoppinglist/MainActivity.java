package com.ninjacode98.shoppinglist;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DatabaseHandler;
import model.ShoppingItem;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText itemName;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                createPopup();
            }
        });
    }

    private void createPopup(){
        builder = new AlertDialog.Builder(this);
        View popup = getLayoutInflater().inflate(R.layout.popup,null);
        itemName = popup.findViewById(R.id.itemNameEditText);
        itemQuantity = popup.findViewById(R.id.quantityEditText);
        itemColor = popup.findViewById(R.id.quantityEditText);
        itemSize = popup.findViewById(R.id.sizeEditText);
        saveButton = popup.findViewById(R.id.saveBtn);

        builder.setView(popup);
        dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(
                        !TextUtils.isEmpty(itemName.getText().toString().trim())
                        && !TextUtils.isEmpty(itemColor.getText().toString().trim())
                        && !TextUtils.isEmpty(itemSize.getText().toString().trim())
                        && !TextUtils.isEmpty(itemQuantity.getText().toString().trim())
                ){
                    saveShoppingItem(v);
                }else{
                    Snackbar.make(v,"Empty fields not allowed.",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void saveShoppingItem(View view){
        ShoppingItem item = new ShoppingItem();

        item.setItemName(itemName.getText().toString().trim());
        item.setItemColor(itemColor.getText().toString().trim());
        item.setQuantity(Integer.parseInt(itemQuantity.getText().toString().trim()));
        item.setSize(Integer.parseInt(itemSize.getText().toString().trim()));

        databaseHandler.addItem(item);

        Snackbar.make(view,"Item saved.",Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        },1000);
    }
}