package com.example.alexthomas.myapplication;

/* Created by Kevin on 1/1/2017.*/

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.content.SharedPreferences;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import org.w3c.dom.Text;


public class settings_spinners extends AppCompatActivity {
    private Spinner spinner_fonts;
    private Spinner spinner_ringtones;
    private Spinner spinner_quote_length;
    private Spinner spinner_genre;
    private Spinner spinner_backgrounds;
    private Spinner spinner_repeating_intervals;
    private Spinner spinner_alarm_schedule;
    private Spinner spinner_font_color;
    private Spinner spinner_clock_color;
    private CheckBox snooze_check;
    private TextView snooze_interval_title;


    private Button btnSubmit;

    private String[] fonts = {"Formal", "Roboto", "Weird", "Thick", "Sci-Fi", "Personal", "Samurai"};
    private String[] ringtones = {"Scary", "Shadows", "Superhero", "Different Heaven"};
    private String[] genres = {"All Genres", "Entrepreneur", "Celebrity", "Author", "Athlete", "Anime", "Great Minds", "Book Quotes", "Meme Quotes"};
    private String[] quote_length = {"All Lengths", "Medium", "Short", "Long"};
    private String[] backgrounds = {"Vanilla", "Berkeley", "Sky", "Starry Clouds", "Mountain", "Water", "Sunset", "Golden Gate"};
    private String[] font_colors = {"White", "Blue", "Red", "Yellow", "Green"};
    private String[] clock_colors = {"White", "Blue", "Red", "Yellow", "Green"};
    private String[] repeating_intervals = {"5 Seconds", "30 Seconds", "1 Minute", "2 Minutes", "3 Minutes", "4 Minutes", "5 Minutes"};
    private String[] alarm_schedule = {"None", "12 Hours", "24 Hours"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        getSpinnerEntries("Font", spinner_fonts, fonts, R.id.fonts_spinner);
        getSpinnerEntries("Ringtones", spinner_ringtones, ringtones, R.id.ringtones_spinner);
        getSpinnerEntries("Genres", spinner_genre, genres, R.id.genre_spinner);
        getSpinnerEntries("Quote Length", spinner_quote_length, quote_length, R.id.quote_length_spinner);
        getSpinnerEntries("Backgrounds", spinner_backgrounds, backgrounds, R.id.spinner_backgrounds);
        getSpinnerEntries("Repeating Intervals", spinner_repeating_intervals, repeating_intervals, R.id.spinner_intervals);
        getSpinnerEntries("Alarm Schedule", spinner_alarm_schedule, alarm_schedule, R.id.spinner_alarm_schedule);
        getSpinnerEntries("Font Color", spinner_font_color, font_colors, R.id.spinner_font_colors);
        getSpinnerEntries("Clock Color", spinner_clock_color, clock_colors, R.id.spinner_clock_color);


        snooze_check = (CheckBox) findViewById(R.id.snooze_check);
        spinner_repeating_intervals = (Spinner) findViewById(R.id.spinner_intervals);
        spinner_fonts = (Spinner) findViewById(R.id.fonts_spinner);
        spinner_ringtones = (Spinner) findViewById(R.id.ringtones_spinner);
        spinner_quote_length = (Spinner) findViewById(R.id.quote_length_spinner);
        spinner_genre = (Spinner) findViewById(R.id.genre_spinner);
        spinner_backgrounds = (Spinner) findViewById(R.id.spinner_backgrounds);
        spinner_alarm_schedule = (Spinner) findViewById(R.id.spinner_alarm_schedule);
        spinner_clock_color = (Spinner) findViewById(R.id.spinner_clock_color);
        spinner_font_color = (Spinner) findViewById(R.id.spinner_font_colors);
        snooze_interval_title = (TextView) findViewById(R.id.types_of_intervals);


        getCheckBoxEntry();
        setVisibilitySpinner();
        addListenerOnCheckbox();
        addListenerOnButton();
    }


    @Override
    public void onStart() {
        super.onStart();
        //ListenerClick(spinner_alarm_schedule, alarm_schedule);
        //ListenerClick(spinner_ringtones, ringtones);
        //ListenerClick(spinner_fonts, fonts);
        //ListenerClick(spinner_quote_length, quote_length);
        //ListenerClick(spinner_repeating_intervals, repeating_intervals);
        //ListenerClick(spinner_backgrounds, backgrounds);
        //ListenerClick(spinner_genre, genres);
        //ListenerClick(spinner_font_color, font_colors);
        //ListenerClick(spinner_clock_color, clock_colors);
    }

    // get the selected dropdown list value when button is clicked
    public void addListenerOnButton() {
        Log.e("Hi", "Listener on Duty!");
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks if snooze_checkbox is checked
                if (snooze_check.isChecked()) {
                    alarm_service.if_RepeatingAlarm = Boolean.valueOf("true");
                } else {
                    alarm_service.if_RepeatingAlarm = Boolean.valueOf("false");
                }

                SharedPreferences shared_snooze = getSharedPreferences("Snooze Boolean", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared_snooze.edit();
                editor.putString("Message", String.valueOf(alarm_service.if_RepeatingAlarm));
                Log.e("Changed", "boolean to " + String.valueOf(alarm_service.if_RepeatingAlarm));
                editor.apply();
                snooze_check.setChecked(Boolean.valueOf(String.valueOf(alarm_service.if_RepeatingAlarm)));


                //Stores Values//
                storeValue("Font", spinner_fonts);
                storeValue("Quote Length", spinner_quote_length);
                storeValue("Genres", spinner_genre);
                storeValue("Backgrounds", spinner_backgrounds);
                storeValue("Font Color", spinner_font_color);
                storeValue("Repeating Intervals", spinner_repeating_intervals);
                storeValue("Alarm Schedule", spinner_alarm_schedule);
                storeValue("Clock Color", spinner_clock_color);
                storeValue("Ringtones", spinner_ringtones);


                Toast.makeText(getApplicationContext(), "Settings Updated!",
                        Toast.LENGTH_LONG).show();

                //Changes Fonts
                font_changer(String.valueOf(spinner_fonts.getSelectedItem()));

                //Font Color changer
                color_changer(String.valueOf(spinner_font_color.getSelectedItem()));

                //Clock color changer
                clock_color_changer(String.valueOf(spinner_clock_color.getSelectedItem()));


                //Quote length changer
                Log.e("new", "min and max length set");
                quote_length_changer(String.valueOf(spinner_quote_length.getSelectedItem()));

                //Repeating interval changer
                repeating_intervals_changer(String.valueOf(spinner_repeating_intervals.getSelectedItem()));

                //Alarm schedule changer
                alarm_schedule_changer(String.valueOf(spinner_alarm_schedule.getSelectedItem()));

                //Chooses background
                Log.e("wow", "the selected background is " + String.valueOf(spinner_backgrounds.getSelectedItem()));
                background_changer(String.valueOf(spinner_backgrounds.getSelectedItem()));

                //Chooses genre
                Log.e("Whoa!", "Genre right now is " + MainActivity.genre);
                //MainActivity.motivational_quote.setText(""); //makes quote blank
                if (MainActivity.genre != String.valueOf(spinner_genre.getSelectedItem())) {
                    Log.e("Whoa!", "New Genre Detected Called " + String.valueOf(spinner_genre.getSelectedItem()));
                    MainActivity.genre = String.valueOf(spinner_genre.getSelectedItem());
                }
            }
        });
    }


    public void addListenerOnCheckbox() {
        snooze_check.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibilitySpinner();
            }
        });
    }


    public void setVisibilitySpinner() {
        if (!snooze_check.isChecked()) {
            spinner_repeating_intervals.setVisibility(View.GONE);
            snooze_interval_title.setVisibility(View.GONE);
        } else {
            spinner_repeating_intervals.setVisibility(View.VISIBLE);
            getSpinnerEntries("Repeating Intervals", spinner_repeating_intervals, repeating_intervals, R.id.spinner_intervals);
            snooze_interval_title.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aboutus, menu);
        return true;
    }


    public void updateSpinnerEntries(Spinner spinner, String[] args) {
        String message = String.valueOf(spinner.getSelectedItem());
        List<String> new_item = new ArrayList<String>();

        new_item.add(message);
        for (int i = 0; i < args.length; i++) {
            new_item.add(args[i]);
        }
        for (int i = 0; i < new_item.size(); i++) {
            if (i != 0 && new_item.get(i).equals(message)) {
                new_item.remove(i);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public void getSpinnerEntries(String item, Spinner spinner, String[] args, int XMLSpinner) {
        //shared preferences
        SharedPreferences sharedPref = getSharedPreferences(item, MODE_PRIVATE);
        String message = sharedPref.getString("Message", "--Choose your " + item + "--");
        spinner = (Spinner) findViewById(XMLSpinner);

        List<String> new_item = new ArrayList<String>();

        new_item.add(message);
        for (int i = 0; i < args.length; i++) {
            new_item.add(args[i]);
        }
        for (int i = 0; i < new_item.size(); i++) {
            if (i != 0 && new_item.get(i).equals(message)) {
                new_item.remove(i);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public void storeValue(String value, Spinner spinner) {
        SharedPreferences sharedPref = getSharedPreferences(value, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Message", String.valueOf(spinner.getSelectedItem()));
        editor.apply();
    }


    public void getCheckBoxEntry() {
        SharedPreferences checkbox_boolean = getSharedPreferences("Snooze Boolean", MODE_PRIVATE);
        String message = checkbox_boolean.getString("Message", "false");
        alarm_service.if_AlarmSchedule = Boolean.getBoolean(message);
        snooze_check.setChecked(Boolean.valueOf(message));
    }


    public void quote_length_changer(String input) {
        switch (input) {
            case "Medium":
                randomQuote.minlength = 101;
                randomQuote.maxlength = 200;
                break;
            case "Long":
                randomQuote.minlength = 201;
                randomQuote.maxlength = 1000;
                break;
            case "Short":
                randomQuote.minlength = 0;
                randomQuote.maxlength = 100;
                break;
            case "All Lengths":
                randomQuote.minlength = 0;
                randomQuote.maxlength = 10000;
                break;
            default:
                break;
        }
        saveMaxMin();
    }


    public void saveMaxMin() {
        SharedPreferences sharedPref_max = getSharedPreferences("Max", MODE_PRIVATE);
        SharedPreferences sharedPref_min = getSharedPreferences("Min", MODE_PRIVATE);
        SharedPreferences.Editor editor_max = sharedPref_max.edit();
        SharedPreferences.Editor editor_min = sharedPref_min.edit();
        editor_max.putInt("Maximum", randomQuote.maxlength);
        editor_min.putInt("Minimum", randomQuote.minlength);
        Log.e("Maximum minimum", String.valueOf(randomQuote.maxlength) + "|" + String.valueOf(randomQuote.minlength));
        editor_max.apply();
        editor_min.apply();
    }


    public void alarm_schedule_changer(String input) {
        SharedPreferences sharedPreferences_schedule = getSharedPreferences("Alarm Schedule", MODE_PRIVATE);
        SharedPreferences.Editor editor_schedule = sharedPreferences_schedule.edit();

        switch (input) {
            case "None":
                editor_schedule.putLong("Interval", 0);
                editor_schedule.putBoolean("Schedule Enabled", false);
                break;
            case "12 Hours":
                editor_schedule.putLong("Interval", AlarmManager.INTERVAL_HALF_DAY);
                editor_schedule.putBoolean("Schedule Enabled", true);
                break;
            case "24 Hours":
                editor_schedule.putLong("Interval", AlarmManager.INTERVAL_DAY);
                editor_schedule.putBoolean("Schedule Enabled", true);
                break;
            default:
                break;

        }
        editor_schedule.apply();

    }

    public void repeating_intervals_changer(String input) {
        SharedPreferences sharedPreferences_interval = getSharedPreferences("Repeating Intervals", MODE_PRIVATE);
        SharedPreferences.Editor editor_intervals = sharedPreferences_interval.edit();

        switch (input) {
            case "5 Seconds":
                editor_intervals.putLong("Interval", 0);
                break;
            case "30 Seconds":
                editor_intervals.putLong("Interval", 30000);
                break;
            case "1 Minute":
                editor_intervals.putLong("Interval", 60000);
                break;
            case "2 Minutes":
                editor_intervals.putLong("Interval", 120000);
                break;
            case "3 Minutes":
                editor_intervals.putLong("Interval", 180000);
                break;
            case "4 Minutes":
                editor_intervals.putLong("Interval", 240000);
                break;
            case "5 Minutes":
                editor_intervals.putLong("Interval", 300000);
                break;
            default:
                break;
        }
        editor_intervals.apply();
    }


    public void background_changer(String background) {
        switch (background) {
            case "Starry Clouds":
                //set background as starry clouds
                MainActivity.content_main.setBackgroundResource(R.drawable.stars_clouds);
                break;
            case "Sky":
                //set background as starry clouds
                MainActivity.content_main.setBackgroundResource(R.drawable.sky);
                break;
            case "Berkeley":
                //set background as starry clouds
                MainActivity.content_main.setBackgroundResource(R.drawable.berkeley);
                break;
            case "Vanilla":
                MainActivity.content_main.setBackgroundResource(R.drawable.vanilla);
                break;
            case "Mountain":
                MainActivity.content_main.setBackgroundResource(R.drawable.mountain);
                break;
            case "Water":
                MainActivity.content_main.setBackgroundResource(R.drawable.water);
                break;
            case "Sunset":
                MainActivity.content_main.setBackgroundResource(R.drawable.sunset);
                break;
            case "Golden Gate":
                MainActivity.content_main.setBackgroundResource(R.drawable.golden_gate);
                break;
            default:
                break;
        }
    }

    public void clock_color_changer(String color) {
        switch (color) {
            case "White":
                //set background as starry clouds
                MainActivity.digitalClock.setTextColor(Color.WHITE);
                break;
            case "Red":
                MainActivity.digitalClock.setTextColor(Color.parseColor("#ffafaf"));
                break;
            case "Blue":
                MainActivity.digitalClock.setTextColor(Color.parseColor("#a8c9ff"));
                break;
            case "Yellow":
                MainActivity.digitalClock.setTextColor(Color.parseColor("#fffad8"));
                break;
            case "Green":
                MainActivity.digitalClock.setTextColor(Color.parseColor("#defcd6"));
                break;
            default:
                break;
        }
    }


    //Changes font color
    public void color_changer(String color) {
        switch (color) {
            case "White":
                MainActivity.motivational_quote.setTextColor(Color.WHITE);
                MainActivity.quoter.setTextColor(Color.WHITE);
                break;
            case "Red":
                MainActivity.motivational_quote.setTextColor(Color.parseColor("#ffafaf"));
                MainActivity.quoter.setTextColor(Color.parseColor("#ffafaf"));
                break;
            case "Blue":
                MainActivity.motivational_quote.setTextColor(Color.parseColor("#a8c9ff"));
                MainActivity.quoter.setTextColor(Color.parseColor("#a8c9ff"));
                break;
            case "Yellow":
                MainActivity.motivational_quote.setTextColor(Color.parseColor("#fffad8"));
                MainActivity.quoter.setTextColor(Color.parseColor("#fffad8"));
                break;
            case "Green":
                MainActivity.motivational_quote.setTextColor(Color.parseColor("#defcd6"));
                MainActivity.quoter.setTextColor(Color.parseColor("#defcd6"));
                break;
            default:
                break;
        }
    }


    public void font_changer(String font) {
        SharedPreferences sharedPreferences = getSharedPreferences("Font", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Typeface font_roboto = Typeface.createFromAsset(getAssets(), "fonts/roboto-medium.ttf");
        Typeface font_cursive = Typeface.createFromAsset(getAssets(), "fonts/Otto.ttf");
        Typeface font_weird = Typeface.createFromAsset(getAssets(), "fonts/weird.otf");
        Typeface font_scifi = Typeface.createFromAsset(getAssets(), "fonts/scifi.ttf");
        Typeface font_personal = Typeface.createFromAsset(getAssets(), "fonts/personal.ttf");
        Typeface font_thick = Typeface.createFromAsset(getAssets(), "fonts/Lato-Black.ttf");
        Typeface font_samurai = Typeface.createFromAsset(getAssets(), "fonts/samurai.otf");
        Log.e("yes", "font changing to " + font);
        switch (font) {
            case "Formal":
                MainActivity.motivational_quote.setTypeface(font_cursive);
                MainActivity.motivational_quote.setTextSize(33);
                editor.putInt("Size", 33);
                break;
            case "Roboto":
                MainActivity.motivational_quote.setTypeface(font_roboto);
                MainActivity.motivational_quote.setTextSize(20);
                editor.putInt("Size", 20);
                break;
            case "Weird":
                MainActivity.motivational_quote.setTypeface(font_weird);
                MainActivity.motivational_quote.setTextSize(22);
                editor.putInt("Size", 22);
                break;
            case "Sci-Fi":
                MainActivity.motivational_quote.setTypeface(font_scifi);
                MainActivity.motivational_quote.setTextSize(18);
                editor.putInt("Size", 18);
                break;
            case "Samurai":
                MainActivity.motivational_quote.setTypeface(font_samurai);
                MainActivity.motivational_quote.setTextSize(20);
                editor.putInt("Size", 20);
                break;
            case "Personal":
                MainActivity.motivational_quote.setTypeface(font_personal);
                MainActivity.motivational_quote.setTextSize(30);
                editor.putInt("Size", 30);
                break;
            case "Thick":
                MainActivity.motivational_quote.setTypeface(font_thick);
                MainActivity.motivational_quote.setTextSize(20);
                editor.putInt("Size", 20);
                break;
            default:
                break;
        }
        editor.apply();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_home:
                finish();
                return true;
            case R.id.about_us:
                Intent about_us = new Intent(settings_spinners.this, MainActivity_aboutus.class);
                startActivity(about_us);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}