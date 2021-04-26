package com.ninjacode98.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import data.DatabaseHandler;
import model.ShoppingItem;
import ui.RecyclerViewAdapter;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<ShoppingItem> itemList;
    private DatabaseHandler dbHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    private Button saveButton;
    private EditText itemName;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerview = findViewById(R.id.shoppingItemsRecyclerView);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.listFab);

        dbHandler = new DatabaseHandler(this);

        itemList = new ArrayList<>();
        itemList = dbHandler.getAllItems();

        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(),itemList);
        recyclerview.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });
    }

    private void createPopDialog() {
        builder = new AlertDialog.Builder(getApplicationContext());
        View popup = getLayoutInflater().inflate(R.layout.popup,null);

        itemName = popup.findViewById(R.id.itemNameEditText);
        itemQuantity = popup.findViewById(R.id.quantityEditText);
        itemColor = popup.findViewById(R.id.quantityEditText);
        itemSize = popup.findViewById(R.id.sizeEditText);
        saveButton = popup.findViewById(R.id.saveBtn);

        builder.setView(popup);
        alertDialog = builder.create();
        alertDialog.show();

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

    private void saveShoppingItem(View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),ListActivity.class));
                finish();
            }
        },1000);
    }
}