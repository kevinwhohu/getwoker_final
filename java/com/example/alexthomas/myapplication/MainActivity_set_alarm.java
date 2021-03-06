package com.example.alexthomas.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;

import static java.lang.StrictMath.abs;


/* Created by alexthomas on 12/31/16. */

public class MainActivity_set_alarm extends AppCompatActivity {

    public AlarmManager alarmManager;
    public TimePicker timePicker;
    private TextView text_update;
    public PendingIntent pendingIntent;
    public static Intent alarm_intent;
    public long time;
    public static RelativeLayout alarm_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("started", "set_alarm page");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm_);
        alarm_layout = (RelativeLayout) findViewById(R.id.set_alarm_);
        //background_changer();
        //Intialize layout material
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        text_update = (TextView) findViewById(R.id.text_update);
        //Buttons
        Button set_alarms = (Button) findViewById(R.id.alarm_set);
        //Calendar
        final Calendar calendar = Calendar.getInstance();
        set_alarm_text(getInput());




        //Triggers onClick for 'Alarm Set' button
        set_alarms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Cancels alarm if alarm is currently running.
                if (alarm_service.isRunning) {
                    alarm_intent = new Intent(MainActivity_set_alarm.this, alarm_receiver.class);
                    pendingIntent = PendingIntent.getBroadcast(MainActivity_set_alarm.this, 1, alarm_intent,
                            PendingIntent.FLAG_CANCEL_CURRENT);
                    sendBroadcast(alarm_intent);
                    Log.e("Cancel service", "Cancelled");
                    pendingIntent.cancel();
                }

                int theHour = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    theHour = timePicker.getHour();
                }
                else{
                    theHour = timePicker.getCurrentHour();
                }
                int theMinute = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    theMinute = timePicker.getMinute();
                }
                else{
                    theMinute = timePicker.getCurrentMinute();
                }


                calendar.set(Calendar.HOUR_OF_DAY, theHour);
                calendar.set(Calendar.MINUTE, theMinute);

                //Triggers Alarm
                alarm_intent = new Intent(MainActivity_set_alarm.this, alarm_receiver.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity_set_alarm.this, 1, alarm_intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                alarm_intent.putExtra("From Main Alarm", true);
                Log.e("hi",String.valueOf(alarm_intent.getExtras().getBoolean("From Main Alarm")));
                Log.e("System time", String.valueOf(System.currentTimeMillis()));
                Log.e("Calendar time", String.valueOf(calendar.getTimeInMillis()));

                //Checks whether time is before system's time

                if ((abs(System.currentTimeMillis() - calendar.getTimeInMillis()) < 60000)
                        && (System.currentTimeMillis() > calendar.getTimeInMillis())) {
                    time = calendar.getTimeInMillis();
                } else if (System.currentTimeMillis() > calendar.getTimeInMillis()) {

                    time = 86400000 + System.currentTimeMillis();
                } else {

                    time = calendar.getTimeInMillis();
                }

                //Stores time in sharedpref
                storeTime(theHour, theMinute);
                store_timer_null(false);
                set_alarm_text(getInput());
                MainActivity.alarm_confirmation.setText(getInput());
                Log.e("Time", String.valueOf(calendar.getTimeInMillis()));
                Toast.makeText(getApplicationContext(), "Your alarm is set!", Toast.LENGTH_SHORT).show();
                MainActivity.powerButton_on = true;
                MainActivity.on_off_boolean(true);
                store_PowerButtonText(true);
                alarm_service.fromMainAlarm = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                }
                else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                    }
                    else{
                        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

                    }

                }


            }
        });

    }

    private void set_alarm_text(String input) {
        text_update.setText(input);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_alarm, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private String getInput() {
        SharedPreferences sharedPref = getSharedPreferences("Alarm Time", MODE_PRIVATE);
        SharedPreferences sharedPref_null = getSharedPreferences("Last Timer", MODE_PRIVATE);
        Boolean lastTimerisNull = sharedPref_null.getBoolean("Message", false);

        if (!lastTimerisNull) {
            String message = sharedPref.getString("Message", "Your alarm is unset.");
            Log.e("Setting previous alarm", message);
            return message;
        } else {
            return "Your alarm is unset.";
        }
    }


    private void storeTime(int hour, int minute) {

        String str_minute = String.valueOf(minute);
        String str_hour;
        SharedPreferences sharedPref = getSharedPreferences("Alarm Time", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Hour", hour);
        editor.putInt("Minute", minute);
        String am_pm = (hour < 12) ? "AM" : "PM";


        //Converts military time to standard time
        if (hour > 12) {
            str_hour = String.valueOf(hour - 12);
        } else if (hour == 0) {
            str_hour = "12";
        } else {
            str_hour = String.valueOf(hour);
        }

        if (minute < 10) {
            str_minute = "0" + minute;
        }

        editor.putString("Message", "Alarm set to " + str_hour + ":" + str_minute + " " + am_pm);
        editor.apply();
    }

    public void store_PowerButtonText(Boolean message) {

        SharedPreferences sharedPref = getSharedPreferences("Power Button", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Message", message);
        editor.apply();
    }

    public void store_timer_null(Boolean message) {
        SharedPreferences sharedPref = getSharedPreferences("Last Timer", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Message", message);
        editor.apply();
    }

    public void background_changer() {
        SharedPreferences sharedPref_background = getSharedPreferences("Backgrounds", MODE_PRIVATE);
        String last_background = sharedPref_background.getString("Message", "--Choose your Background--");
        switch (last_background) {
            case "Starry Clouds":
                //set background as starry clouds
                alarm_layout.setBackgroundResource(R.drawable.stars_clouds);
                break;
            case "Vanilla":
                alarm_layout.setBackgroundResource(R.drawable.vanilla);
                break;
            case "Sky":
                //set background as starry clouds
                alarm_layout.setBackgroundResource(R.drawable.sky);
                break;
            case "Mountain":
                alarm_layout.setBackgroundResource(R.drawable.mountain);
                break;
            case "Water":
                alarm_layout.setBackgroundResource(R.drawable.water);
                break;
            case "Sunset":
                alarm_layout.setBackgroundResource(R.drawable.sunset);
                break;
            case "Golden Gate":
                alarm_layout.setBackgroundResource(R.drawable.golden_gate);
                break;
            default:
                break;
        }
    }


}
