package com.example.fhb.pruefungsplaner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.fhb.pruefungsplaner.MainActivity.RueckgabeStudiengang;
import static com.example.fhb.pruefungsplaner.MainActivity.dateneinlesen;
import static com.example.fhb.pruefungsplaner.MainActivity.mAdapter;
import static com.example.fhb.pruefungsplaner.MainActivity.spStudiengangMain;

public class Tabelle extends AppCompatActivity  {

    SharedPreferences mSharedPreferences;
    static public FragmentTransaction ft;
    public RecyclerView recyclerView;
    public CalendarView calendar;
    public Button btnsuche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hauptfenster);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView4);
        recyclerView.setVisibility(View.VISIBLE);
        calendar = (CalendarView) findViewById(R.id.caCalender);
        btnsuche = (Button) findViewById(R.id.btnDatum);
        recyclerView.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.GONE);
        btnsuche.setVisibility(View.GONE);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_placeholder, new Terminefragment());
        ft.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_calender:
                    recyclerView.setVisibility(View.INVISIBLE);
                    calendar.setVisibility(View.GONE);
                    btnsuche.setVisibility(View.GONE);
                    ft.replace(R.id.frame_placeholder, new Terminefragment());
                    ft.commit();
                    return true;

                case R.id.navigation_medication:
                    recyclerView.setVisibility(View.INVISIBLE);
                    calendar.setVisibility(View.GONE);
                    btnsuche.setVisibility(View.GONE);
                    ft.replace(R.id.frame_placeholder, new sucheFragment());
                    ft.commit();
                    return true;

                case R.id.navigation_diary:
                    recyclerView.setVisibility(View.INVISIBLE);
                    calendar.setVisibility(View.GONE);
                    btnsuche.setVisibility(View.GONE);
                    ft.replace(R.id.frame_placeholder, new Favoritenfragment());
                    ft.commit();
                    return true;

                case R.id.navigation_settings:
                    recyclerView.setVisibility(View.INVISIBLE);
                    calendar.setVisibility(View.GONE);
                    btnsuche.setVisibility(View.GONE);
                    ft.replace(R.id.frame_placeholder, new optionen());
                    ft.commit();
                    return true;
            }
            return false;
        }
    };
}
