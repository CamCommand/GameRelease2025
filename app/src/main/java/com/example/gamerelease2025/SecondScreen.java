package com.example.gamerelease2025;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.gamerelease2025.R.id.numberPicker3;
import static com.example.gamerelease2025.R.id.search_button;

public class SecondScreen extends AppCompatActivity implements NumberPicker.OnValueChangeListener, View.OnClickListener {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

//        Button button_not = findViewById(R.id.button2);
        Button buttonsave = findViewById(R.id.buttonsave);

        Button search = findViewById(search_button);
        View view = findViewById(search_button);
        view.setOnClickListener(this);

        NumberPicker hour = findViewById(R.id.numberPicker);//for num picker hours
        hour.setMaxValue(12);
        hour.setMinValue(1);
        hour.setOnValueChangedListener(this);
        long hourConverted;

        NumberPicker min = findViewById(R.id.numberPicker2);//for num picker min
        min.setMaxValue(59);
        min.setMinValue(0);
        String[] str = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        //the reason I have to use a string array and not concatenation is because adding a 0 in front of an int will basically ignore itself, and having it display a string of 00 or 01 sometimes and an int 21 22 others breaks the app
        //min.setDisplayedValues(new String[]{str[min.getValue()]});
        //min.setValue(Integer.parseInt("0"+min.getValue()));
        min.setDisplayedValues(str);
        min.setOnValueChangedListener(this);

        String[] timezone = {"AM", "PM"};//setting am and pm number picker
        NumberPicker tz = findViewById(R.id.numberPicker3);
        tz.setMinValue(0);
        tz.setMaxValue(1);
        tz.setDisplayedValues(timezone);

        if (tz.getValue() == 1) {
            hourConverted = hour.getValue()+ 1;

        } else{
            hourConverted = hour.getValue();
        }

        EditText alarm = findViewById(R.id.messagealarm);
        EditText eventtitle = findViewById(R.id.eventdes);

        //fix me
        buttonsave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!eventtitle.getText().toString().isEmpty()){

                    Intent intentTwo = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events.TITLE, alarm.getText().toString())
                            .putExtra(CalendarContract.Events.DESCRIPTION, eventtitle.getText().toString())
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, hourConverted)
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, (hourConverted + 1))
                            .putExtra(CalendarContract.Events.HAS_ALARM, 1);

                    startActivity(intentTwo);

//                    if(intent.resolveActivity(getPackageManager()) != null){
//                        startActivity(intent);
//                    }else{
//                        Toast.makeText(SecondScreen.this, "Cannot find app to support this action", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });


        search.setOnClickListener(new View.OnClickListener() {//button going to second screen
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondScreen.this, SearchScreen.class);
                startActivity(intent);
            }
        });
    }

    private String getTodaysDate() {//for getting the date for the default setting
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {//button confirmation
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();//style
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;//return most popular format of date
    }

    private String getMonthFormat(int month) {
        String[] mons = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};//call array faster than 12 if statements
        for(int i = 1; i <= 12; i++){
            if(month == i){
                return mons[i];
            }
        }
        return null;
    }

    @Override
    public void onValueChange(NumberPicker min, int oldVal, int newVal) {

    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
    }
}