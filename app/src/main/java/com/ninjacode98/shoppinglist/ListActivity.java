package com.ninjacode98.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerview = findViewById(R.id.shoppingItemsRecyclerView);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        dbHandler = new DatabaseHandler(this);

        itemList = new ArrayList<>();
        itemList = dbHandler.getAllItems();

        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(),itemList);
        recyclerview.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}