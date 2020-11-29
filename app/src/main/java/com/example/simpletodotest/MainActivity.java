package com.example.simpletodotest;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final String KEY_ITEM_TEXT = "com.example.simpletodotest.KEY_ITEM_TEXT";
    public static final String KEY_ITEM_DATEDAY = "com.example.simpletodotest.KEY_ITEM_DATE";
    public static final String KEY_ITEM_DATEMONTH = "com.example.simpletodotest.KEY_ITEM_DATE";
    public static final String KEY_ITEM_DATEYEAR = "com.example.simpletodotest.KEY_ITEM_DATE";
    public static final String KEY_ITEM_PRIORITY = "com.example.simpletodotest.KEY_ITEM_PRIORITY";
    public static final String KEY_ITEM_POSITION = "item_position";
    private static final String TAG = "MainActivity";


    ArrayList<Items> toDoItems;
    Button btnAdd;
    Button btnUpdate;
    EditText etItem;
    CheckBox checkBox;
    CalendarView calendarView;
    TextView tvTaskList;
    TextView tvPriority;
    RecyclerView rvItems;
    ItemsAdapter mAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Context context;


    private void setUpRecycler() {

        mRecyclerView = (RecyclerView) findViewById(R.id.rvItems);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ItemsAdapter(toDoItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                toDoItems.get(position);
            }


            @Override
            public void onUpdateClick(int position) {
                Log.d("MainActivity", "Single click at position " + position);
                //create the new activity
                Items currentItem = toDoItems.get(position);
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(EditActivity.EXTRA_ITEM_NAME, currentItem.getName());
                i.putExtra(EditActivity.EXTRA_ITEM_DATE,currentItem.getDate());
                Log.d("MainActivity", "Single click at position " + currentItem.getDate());
                i.putExtra(EditActivity.EXTRA_ITEM_PRIORITY, currentItem.getPriority());
                i.putExtra(EditActivity.EXTRA_ITEM_POSITION, position);
                startActivityForResult(i, EDIT_NOTE_REQUEST);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);

            }

            @Override
            public void onCompletedClick(int position) {
               //TO ADD - sends all data that is completed to a new activity.
            }
        });

    }

    public void removeItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Item Confirmation");
        builder.setMessage("Are you sure you wish to delete this item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toDoItems.remove(position);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Item was not removed", Toast.LENGTH_SHORT).show();
                return;

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadItems();
        setUpRecycler();
        btnAdd = findViewById(R.id.btnAdd);





        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,addItemsActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);

            }

        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);


        if (requestCode == ADD_NOTE_REQUEST &&  resultCode == RESULT_OK ) {
            String itemName = data.getStringExtra(addItemsActivity.EXTRA_NAME);
            String dueDateDay = data.getStringExtra(addItemsActivity.EXTRA_DUEDATE);
            String priority = data.getStringExtra(addItemsActivity.EXTRA_PRIORITY);

            Items newItem = new Items(true, itemName, dueDateDay, priority);
            toDoItems.add(newItem);
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Item saved.", Toast.LENGTH_SHORT).show();
            saveItems();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
                String name = data.getStringExtra(EditActivity.EXTRA_ITEM_NAME);
                String dateDayVar = data.getStringExtra(EditActivity.EXTRA_ITEM_DATE);
                String priority =  data.getStringExtra(EditActivity.EXTRA_ITEM_PRIORITY);
                Log.d("MainActivity", "What is the priority variable? " + priority);
                Log.d("MainActivity", "What is the due date variable? " +dateDayVar);
                int position = data.getExtras().getInt(EditActivity.EXTRA_ITEM_POSITION);
                Items updatedItem = new Items(true,name,dateDayVar,priority);
                Log.d("MainActivity", "What are the vars for the updated position?? " + position);
                Log.d("MainActivity", "What are the vars for the updated Item prio? " + updatedItem.getPriority());
                Log.d("MainActivity", "What are the vars for the updated Item name? " + updatedItem.getName());
                Log.d("MainActivity", "What are the vars for the updated Item day? " + updatedItem.getDate());
                toDoItems.set(position,updatedItem);
                mAdapter.notifyItemChanged(position);
                Toast.makeText(this, "Item updated.", Toast.LENGTH_SHORT).show();
                saveItems();
        }
        else {
            Toast.makeText(this,"Item not saved.", Toast.LENGTH_SHORT).show();

        }


    }

    private void loadItems()  {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("To Do Items", null);
        Type toDoItemsType = new TypeToken<ArrayList<Items>>() {}.getType();
        toDoItems = gson.fromJson(json, toDoItemsType);

        if (toDoItems == null) {
            toDoItems = new ArrayList<>();

        }

    }
    private void saveItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(toDoItems);
        editor.putString("To Do Items", json);
        editor.apply();

    }


}