package com.example.fhb.pruefungsplaner;

//////////////////////////////
// Terminefragment
//
//
//
// autor:
// inhalt:  Prüfungen aus der Klasse Prüfplaneintrag werden abgefragt und zur darstelllung an den Recycleview adapter übergeben
// zugriffsdatum: 02.05.19
//
//
//
//
//
//
//////////////////////////////

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.ArrayList;
import java.util.List;

import static com.example.fhb.pruefungsplaner.MainActivity.dateneinlesen;
import static com.example.fhb.pruefungsplaner.MainActivity.mAdapter;

public class Terminefragment extends Fragment {
    SharedPreferences mSharedPreferences;
    List<String> WerteZumAnzeigen;
    private FragmentTransaction ft;
    private RecyclerView recyclerView;
    private CalendarView calendar;
    private Button btnsuche;
    private String date;
    private String month2;
    private String day2;
    private String year2;
    SwipeController swipeController = null;
    MyAdapter mAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.terminefragment, container, false);

        WerteZumAnzeigen = dateneinlesen.getab();
        //hinzufügen von recycleview
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView4);



        recyclerView.setVisibility(View.VISIBLE);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        mSharedPreferences = v.getContext().getSharedPreferences("json6", 0);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<String> input2 = new ArrayList<>();
        List<String> input3 = new ArrayList<>();
        List<String> input4 = new ArrayList<>();

        //gr
        for (int i = 0; i < WerteZumAnzeigen.size(); i++) {
            input.add(dateneinlesen.getFach()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getStudiengang()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
            input2.add(dateneinlesen.getProfname()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getProfname2()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getSemester()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " ");
            input3.add(dateneinlesen.getDatum()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
            input4.add(WerteZumAnzeigen.get(i));

        }// define an adapter

        mAdapter = new MyAdapter(input, input2, input3, input4);

        recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            String positionspeichern;
            @Override
            public void onLeftClicked(int position) {

                position = position +1 ;
                final int position2 = position - 1;

                if  ((position+2) >= mAdapter.getItemCount())
                {
                    View viewItem = recyclerView.getLayoutManager().findViewByPosition(position-2);
                    View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position-3);
                    viewItem.setVisibility(View.INVISIBLE);
                    viewItem2.setVisibility(View.INVISIBLE);
                    mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());

                    recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                            //swipeController.onDrawup(c);
                        }
                    });


                }
               else {
                    positionspeichern = mAdapter.values.get(position);
                    View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                    View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position + 1);
                    viewItem.setVisibility(View.INVISIBLE);
                    viewItem2.setVisibility(View.INVISIBLE);

                    recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                           String s =  mAdapter.giveString(position2);
                            swipeController.onDraw(c,s);
                        }
                    });

                }
               // mAdapter.values.remove(position);
            //    mAdapter.notifyItemRemoved(position);
               // mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }


            public void onRightClicked(int position) {
                position = position + 1;

                if  ((position+2) >= mAdapter.getItemCount())
                {
                    View viewItem = recyclerView.getLayoutManager().findViewByPosition(position-2);
                    View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position-3);
                    viewItem.setVisibility(View.VISIBLE);
                    viewItem2.setVisibility(View.VISIBLE);

                }else {
                    View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                    View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position + 1);
                    viewItem.setVisibility(View.VISIBLE);
                    viewItem2.setVisibility(View.VISIBLE);
                }

                //mAdapter.values.set(position,positionspeichern);
                //mAdapter.notifyItemInserted(position);


            }

        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);



        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView4);
        recyclerView.setVisibility(View.VISIBLE);
        calendar = (CalendarView) v.findViewById(R.id.caCalender);
        btnsuche = (Button) v.findViewById(R.id.btnDatum);

        calendar.setVisibility(View.GONE);
        btnsuche.setOnClickListener(new View.OnClickListener() {
            boolean speicher = true;

            @Override
            public void onClick(View v) {
                if (!speicher) {
                    calendar.setVisibility(View.GONE);
                    //calendar.getLayoutParams().height = 0;
                    List<String> input = new ArrayList<>();
                    List<String> input2 = new ArrayList<>();
                    List<String> input3 = new ArrayList<>();
                    List<String> input4 = new ArrayList<>();
                    //gr
                    for (int i = 0; i < WerteZumAnzeigen.size(); i++) {
                        input.add(dateneinlesen.getFach()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getStudiengang()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
                        input2.add(dateneinlesen.getProfname()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getProfname2()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getSemester()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " ");
                        input3.add(dateneinlesen.getDatum()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
                        input4.add(WerteZumAnzeigen.get(i));

                    }// define an adapter
                    mAdapter = new MyAdapter(input, input2, input3, input4);
                    recyclerView.setAdapter(mAdapter);



                    speicher = true;
                } else {
                    calendar.setVisibility(View.VISIBLE);
                    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                            List<String> input = new ArrayList<>();
                            List<String> input2 = new ArrayList<>();
                            List<String> input3 = new ArrayList<>();
                            List<String> input4 = new ArrayList<>();
                            //Creating editor to store values to shared preferences
                            if (month < 10) {
                                month2 = "0" + String.valueOf(month + 1);
                            } else {
                                month2 = String.valueOf(month);
                            }
                            if (dayOfMonth < 10) {
                                day2 = "0" + String.valueOf(dayOfMonth);
                            } else {
                                day2 = String.valueOf(dayOfMonth);
                            }
                            year2 = String.valueOf(year);
                            date = year2 + "-" + month2 + "-" + day2;
                            for (int i = 0; i < WerteZumAnzeigen.size(); i++) {
                                String[] date2 = dateneinlesen.getDatum()[Integer.valueOf(WerteZumAnzeigen.get(i).toString())].toString().split(" ");
                                if (date2[0].equals(date)) {
                                    input.add(dateneinlesen.getFach()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getStudiengang()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
                                    input2.add(dateneinlesen.getProfname()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getProfname2()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getSemester()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " ");
                                    input3.add(dateneinlesen.getDatum()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
                                    input4.add(WerteZumAnzeigen.get(i));
                                }
                            }// define an adapter
                            mAdapter = new MyAdapter(input, input2, input3, input4);
                            recyclerView.setAdapter(mAdapter);
                        }
                    });
                    speicher = false;
                }
            }
        });
        return v;
    }
}
