package com.universal.agecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText etName,etDob;
    int birthYear,birthMonth,birthDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName =  findViewById(R.id.et_name);
        etDob =  findViewById(R.id.et_dob);

        etDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeKeyboard(v);
                    openCalendar();
                }
            }
        });

        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(v);
                openCalendar();
            }
        });

    }

    private void openCalendar() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                birthYear = year;
                birthMonth = month+1;
                birthDay = dayOfMonth;
                etDob.setText(String.format(Locale.ENGLISH,"%d-%d-%d", dayOfMonth, month + 1, year));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dialog.setCancelable(false);
        dialog.show();
    }

    public void submitDataForResult(View view) {
        if(TextUtils.isEmpty(etName.getText())){
            etName.setError(getString(R.string.enter_your_name));
            etName.requestFocus();
        }else if (TextUtils.isEmpty(etDob.getText())){
            etDob.setError(getString(R.string.enter_your_dob));
            etDob.requestFocus();
        }else{
            Intent resultIntent = new Intent(this,ResultActivity.class);
            resultIntent.putExtra("name",etName.getText().toString());
            resultIntent.putExtra("birth_year",birthYear);
            resultIntent.putExtra("birth_month",birthMonth);
            resultIntent.putExtra("birth_day",birthDay);
            startActivity(resultIntent);
        }
    }

    private void closeKeyboard(View v){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }
}
