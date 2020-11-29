package com.example.simpletodotest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;

public class addItemsActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {


    public static final String EXTRA_NAME = "com.example.simpletodotest.EXTRA_NAME";
    public static final String EXTRA_DUEDATE = "com.example.simpletodotest.EXTRA_DUEDAY";
    public static final String EXTRA_PRIORITY = "com.example.simpletodotest.EXTRA_PRIORITY";

    private EditText editTextName;
    private Button btnChangeDate;
    private RadioButton rbHighPrio;
    private RadioButton rbMediumPrio;
    private RadioButton rbLowPrio;
    private ImageView addButton;
    private TextView tvDateAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        btnChangeDate = (Button) findViewById(R.id.btnDateAdd);
        editTextName = (EditText) findViewById(R.id.edit_text_name);
        TextView tvDateAdd = (TextView) findViewById(R.id.dateTextViewAdd);
        rbHighPrio = (RadioButton) findViewById(R.id.rbHighPrio);
        rbMediumPrio = (RadioButton) findViewById(R.id.rbMediumPrio);
        rbLowPrio = (RadioButton) findViewById(R.id.rbLowPrio);
        addButton = (ImageView) findViewById(R.id.ivAddBtn);
        setTitle("Add Item");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveItem();
            }

        });

        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");


            }
        });



    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tvDateAdd = findViewById(R.id.dateTextViewAdd);
        tvDateAdd.setText(currentDateString);
    }



        private void saveItem() {
            String name = editTextName.getText().toString();
            String date = tvDateAdd.getText().toString();
            String priority = "ok";
            if (rbHighPrio.isChecked()) {
                priority = rbHighPrio.getText().toString();
            }
            else if (rbMediumPrio.isChecked()) {
                priority = rbMediumPrio.getText().toString();
            }
            else if (rbLowPrio.isChecked()) {
                priority = rbLowPrio.getText().toString();
            }

            if (name.trim().isEmpty() || priority.isEmpty() || date.trim().equals("date")) {
                Toast.makeText(this, "Please insert a name, date and priority", Toast.LENGTH_SHORT);
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_NAME, name);
            data.putExtra(EXTRA_DUEDATE, date);
            data.putExtra(EXTRA_PRIORITY, priority);

            setResult(RESULT_OK, data);
            finish();


    }


}