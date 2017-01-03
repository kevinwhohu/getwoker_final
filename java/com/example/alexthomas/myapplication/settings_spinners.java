package com.example.alexthomas.myapplication;

/* Created by Kevin on 1/1/2017.*/

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
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
import android.widget.Spinner;
import android.widget.Toast;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.content.SharedPreferences;






public class settings_spinners extends AppCompatActivity {
    private Spinner spinner_fonts;
    private Spinner spinner_quote_length;
    private Spinner spinner_genre;
    private Button btnSubmit;

    private String[] fonts = {"Formal", "Roboto", "Weird"};
    private String[] genres ={"Entrepreneur", "Celebrity", "Author"};
    private String[] quote_length = {"Medium", "Short", "Long"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        getSpinnerEntries("Font",spinner_fonts, fonts,R.id.fonts_spinner);
        getSpinnerEntries("Genres",spinner_genre, genres,R.id.genre_spinner);
        getSpinnerEntries("Quote Length",spinner_quote_length, quote_length,R.id.quote_length_spinner);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.e("Hi", "Opened Settings Page!");
        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_alarm, menu);
        return true;
    }






    public void getSpinnerEntries(String item, Spinner spinner, String[] args, int XMLSpinner) {
        //shared preferences
        SharedPreferences sharedPref = getSharedPreferences(item, MODE_PRIVATE);
        String message = sharedPref.getString("Message", "Default " + item);

        spinner = (Spinner) findViewById(XMLSpinner);

        List<String> new_item = new ArrayList<String>();



        new_item.add(message);
        for (int i = 0; i<args.length;i++){
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




    public void storeValue(String value,Spinner spinner) {
        SharedPreferences sharedPref = getSharedPreferences(value, MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Message",String.valueOf(spinner.getSelectedItem()));
        editor.apply();
    }






    // get the selected dropdown list value when button is clicked
    public void addListenerOnButton() {
        Log.e("Hi", "Listener on Duty!");
        spinner_fonts = (Spinner) findViewById(R.id.fonts_spinner);
        spinner_quote_length = (Spinner) findViewById(R.id.quote_length_spinner);
        spinner_genre = (Spinner) findViewById(R.id.genre_spinner);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //Initialized different font-family
                Typeface font_roboto = Typeface.createFromAsset(getAssets(),"fonts/roboto-medium.ttf");
                Typeface font_cursive = Typeface.createFromAsset(getAssets(),"fonts/Otto.ttf");
                Typeface font_weird = Typeface.createFromAsset(getAssets(),"fonts/weird.otf");



                //Stores Value and Chooses font-family from spinner_fonts
                storeValue("Font",spinner_fonts);
                Toast.makeText(getApplicationContext(), "Settings Updated!",
                        Toast.LENGTH_LONG).show();
                switch (String.valueOf(spinner_fonts.getSelectedItem())) {
                    case "Formal":
                        MainActivity.motivational_quote.setTypeface(font_cursive);
                        break;
                    case "Roboto":
                        MainActivity.motivational_quote.setTypeface(font_roboto);
                        break;
                    case "Weird":
                        MainActivity.motivational_quote.setTypeface(font_weird);
                        break;
                    default:
                        break;
                }

                randomQuote Quote = new randomQuote();
                //Chooses size of quotes from quotes_length_spinner
                storeValue("Quote Length",spinner_quote_length);
                switch (String.valueOf(spinner_quote_length.getSelectedItem())) {
                    case "Medium":
                        break;
                    case "Long":
                        break;
                    case "Short":
                        break;
                    default:
                        break;
                }



                //Chooses genre
                storeValue("Genres",spinner_genre);
                Log.e("Whoa!", "Genre right now is " +MainActivity.genre);
                if (MainActivity.genre != String.valueOf(spinner_genre.getSelectedItem())) {
                    Log.e("Whoa!", "New Genre Detected Called " +String.valueOf(spinner_genre.getSelectedItem()));
                    MainActivity.genre = String.valueOf(spinner_genre.getSelectedItem());
                    switch (String.valueOf(spinner_genre.getSelectedItem())) {
                        case "Entrepreneur":
                            Quote.quote_generator("Entrepreneur");
                            break;
                        case "Celebrity":
                            Quote.quote_generator("Celebrity");
                            break;
                        case "Author":
                            Quote.quote_generator("Author");
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_home:
                finish();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "You're already at 'Settings'!",
                        Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}