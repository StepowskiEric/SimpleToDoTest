package com.example.simpletodotest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Date;


public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

        public static final String KEY_ITEM_CHANGED = "item_changed";
        public static final String EXTRA_ITEM_NAME = "edit";
        public static final String EXTRA_ITEM_DATE = "EditActivity.EXTRA_ITEM_DATE";
        public static final String EXTRA_ITEM_PRIORITY = "itemNAME";
        public static final String EXTRA_ITEM_POSITION = "pos";

        EditText updateItemName;
        Button btnSave;
        Button btnDate;
        TextView tvDate;
        RadioButton updateCbHighPrio;
        RadioButton updateCbMediumPrio;
        RadioButton updateCbLowPrio;


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit);
            btnDate = (Button) findViewById(R.id.btnDate);
            tvDate = (TextView) findViewById(R.id.dateTextView);
            updateCbHighPrio = (RadioButton) findViewById(R.id.cbHighPrio);
            updateCbMediumPrio = (RadioButton) findViewById(R.id.cbMediumPriority);
            updateCbLowPrio = (RadioButton) findViewById(R.id.cbLowPrio);

            updateItemName = (EditText) findViewById(R.id.etItem2);
            btnSave = (Button) findViewById(R.id.btnSave);
            setTitle("Update Items");
            getSupportActionBar().setTitle("Edit Items");



            updateItemName.setText(getIntent().getStringExtra(EXTRA_ITEM_NAME));
            tvDate.setText(getIntent().getStringExtra(EXTRA_ITEM_DATE));



             String priority = (String) getIntent().getStringExtra(EXTRA_ITEM_PRIORITY);


              if (priority.equals("High Priority")) {
                 updateCbHighPrio.setChecked(true);
             }
             else if (priority.equals("Medium Priority"))  {
                 updateCbMediumPrio.setChecked(true);
             }
             else {
                 updateCbLowPrio.setChecked(true);
             }



             btnDate.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     DialogFragment datePicker = new DatePickerFragment();
                     datePicker.show(getSupportFragmentManager(), "date picker");


                 }
             });








            //when the user is doing editing, they click the save button

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveItem();

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
        TextView tvDate = (TextView) findViewById(R.id.dateTextView);
        tvDate.setText(currentDateString);

    }
            public void saveItem() {
                String nameChanged = updateItemName.getText().toString();
                String dateChanged =  tvDate.getText().toString();
                String priority = "";
                int position = (int) getIntent().getIntExtra(EXTRA_ITEM_POSITION,0);
                if (updateCbHighPrio.isChecked()) {
                    priority = updateCbHighPrio.getText().toString();

                }
                else if (updateCbMediumPrio.isChecked()) {
                    priority =  updateCbMediumPrio.getText().toString();

                }
                else  {
                    priority =  updateCbLowPrio.getText().toString();
                }
                Intent data = new Intent();
                //Pass the data (results of editing)
                data.putExtra(EXTRA_ITEM_NAME, nameChanged);
                data.putExtra(EXTRA_ITEM_DATE, dateChanged);
                data.putExtra(EXTRA_ITEM_PRIORITY, priority);
                data.putExtra(EXTRA_ITEM_POSITION, position);
                Log.d("EditActivity", "The variable for prioChanged is " + priority);
                Log.d("EditActivity", "The variable for dateChanged is " + dateChanged);


                setResult(RESULT_OK, data);
                finish();
            }

}
