package com.example.alexthomas.myapplication;
/* Created by Kevin on 1/2/2017.*/

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
import java.util.*;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import android.util.Log;
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

public class randomQuote {
    private Map<String, String[]> quotes = new HashMap<String, String[]>();


    String[][] Entrepreneur_quotes = {
            {"lol","Bill Gates"},
            {"wow I'm an entrepreneur","Mark Zuckerberg"},
            {"fuck this country lol","Donald Trump"},
            {"do you jerk off???", "Elon Musk"}
    };

    String[][] Celebrity_quotes = {
            {"lolol","sdfs"},
            {"wow I'm an celebrity","sdfssdf"},
            {"do you jerk off 2 times a day?","sdfsdfdsfni"}
    };


    String[][] Author_quotes = {
            {"lol writing is stupid","J.K. Rowling"},
            {"wow I'm an author","J.R.R Tolkien"}
    };



    public void quote_generator (String genre) {

        if (genre.equals("Entrepreneur")) {
            int rnd = new Random().nextInt(Entrepreneur_quotes.length);
            MainActivity.motivational_quote.setText(Entrepreneur_quotes[rnd][0]);
            alarm_service.genre = "Entrepreneur";
        }

        if (genre.equals("Celebrity")) {
            int rnd1 = new Random().nextInt(Celebrity_quotes.length);
            MainActivity.motivational_quote.setText(Celebrity_quotes[rnd1][0]);
            alarm_service.genre = "Celebrity";
        }


        if (genre.equals("Author")) {
            int rnd2 = new Random().nextInt(Author_quotes.length);
            MainActivity.motivational_quote.setText(Author_quotes[rnd2][0]);
            alarm_service.genre = "Author";
        }
    }
}
