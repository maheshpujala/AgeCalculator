package com.universal.agecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {
    int birthYear,birthMonth,birthDay,ageOfToday;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = getIntent().getStringExtra("name");
        birthYear = getIntent().getIntExtra("birth_year",2019);
        birthMonth = getIntent().getIntExtra("birth_month",7);
        birthDay = getIntent().getIntExtra("birth_day",23);

        ageOfToday =  getAge(birthYear,birthMonth,birthDay);

        ((TextView)findViewById(R.id.tv_name)).setText(String.format("Hello %s", name));
        ((TextView)findViewById(R.id.tv_age)).setText(String.format("Your current age is  %d years", ageOfToday));
    }


    public int getAge(int DOByear, int DOBmonth, int DOBday) {
        int age;
        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if(DOBmonth > currentMonth) {
            --age;
        } else if(DOBmonth == currentMonth) {
            if(DOBday > todayDay){
                --age;
            }
        }
        return age;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            ResultActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
