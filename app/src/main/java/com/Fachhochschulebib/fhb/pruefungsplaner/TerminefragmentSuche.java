package com.Fachhochschulebib.fhb.pruefungsplaner;

//////////////////////////////
// Terminefragment
//
//
//
// autor:
// inhalt:  Prüfungen aus der Klasse Prüfplaneintrag werden abgefragt und zur darstelllung an den Recycleview adapter übergeben
// zugriffsdatum: 07.09.19
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.Pruefplan;

import java.util.ArrayList;
import java.util.List;


import static com.Fachhochschulebib.fhb.pruefungsplaner.Terminefragment.validation;


public class TerminefragmentSuche extends Fragment {


    private RecyclerView recyclerView;
    private CalendarView calendar;
    private Button btnsuche;
    private String date;
    private String month2;
    private String day2;
    private int position2 = 0;
    private String year2;
    private RecyclerView.LayoutManager mLayout;
    SwipeController swipeController = null;
    MyAdapter mAdapter;
    List<Integer> WerteZumAnzeigen= new ArrayList<>();



    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.terminefragment, container, false);

        //Datenbank initialisieren
        AppDatabase datenbank = AppDatabase.getAppDatabase(v.getContext());
        List<Pruefplan> pruefplandaten = datenbank.userDao().getAll(validation);


        //
        for (int i =0;i<pruefplandaten.size();i++) {
            System.out.println(pruefplandaten.get(i).getAusgewaehlt());
            if(pruefplandaten.get(i).getAusgewaehlt()) {
                WerteZumAnzeigen.add(i);
            }
        }


        //hinzufügen von recycleview
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView4);
        recyclerView.setVisibility(View.VISIBLE);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mLayout = recyclerView.getLayoutManager();


        //Variablen zum speichern der Werte
        List<String> studiengangundmodul = new ArrayList<>();
        List<String> prueferundsemster = new ArrayList<>();
        List<String> datum = new ArrayList<>();
        List<String> id = new ArrayList<>();
        List<String> ID = new ArrayList<>();
        List<String> Pruefform = new ArrayList<>();

        //Variablen mit Werten aus der lokalen Datenbank füllen
        for (int i = 0; i < WerteZumAnzeigen.size(); i++) {
            studiengangundmodul.add(pruefplandaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getModul() + "\n " + pruefplandaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getStudiengang());
            prueferundsemster.add(pruefplandaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getErstpruefer() + " " + pruefplandaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getZweitpruefer() + " " + pruefplandaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getSemester() + " ");
            datum.add(pruefplandaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getDatum());
            id.add(pruefplandaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getModul());
            ID.add(pruefplandaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getID());
            Pruefform.add(pruefplandaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getPruefform());

        }

        // define an adapter
        mAdapter = new MyAdapter(studiengangundmodul, prueferundsemster, datum, id,ID,Pruefform,mLayout);
        recyclerView.setAdapter(mAdapter);


        //überprüfung ob das Prüfitem geswippt wurde
        swipeController = new SwipeController(new SwipeControllerActions() {
            String positionspeichern;
            @Override
            public void onLeftClicked(int position) {
                position = position + 1;
                position2 = position;
                final int position2 = position - 1;

                if (mAdapter.getItemCount() < 2)
                {
                    recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                            String s = mAdapter.giveString(position2);
                            swipeController.onDraw(c, s);
                        }
                    });

                }

                if( position < (mAdapter.getItemCount() -1)) {
                    try {
                        positionspeichern = mAdapter.uebergebeneModule.get(position);
                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                        View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position + 1);
                        viewItem.setVisibility(View.INVISIBLE);
                        viewItem2.setVisibility(View.INVISIBLE);

                        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                            @Override
                            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                                String s = mAdapter.giveString(position2);
                                swipeController.onDraw(c, s);
                            }
                        });
                    } catch (NullPointerException e) {
                    }

                }else{
                    try {
                        if( mAdapter.getItemCount() < 4)
                        {
                            try {

                                recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                                    @Override
                                    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                                        String s = mAdapter.giveString(position2);
                                        swipeController.onDraw(c, s);
                                    }
                                });

                                View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                                viewItem.setVisibility(View.INVISIBLE);

                            } catch (NullPointerException e) {
                            }

                        }
                        else {
                            positionspeichern = mAdapter.uebergebeneModule.get(position - 1);
                            View viewItem = recyclerView.getLayoutManager().findViewByPosition(position - 2);
                            View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position - 3);
                            viewItem.setVisibility(View.INVISIBLE);
                            viewItem2.setVisibility(View.INVISIBLE);

                            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                                @Override
                                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                                    String s = mAdapter.giveString(position2);
                                    swipeController.onDraw2(c, s);
                                }
                            });
                        }
                    } catch (NullPointerException e) {

                    }
                }
                // mAdapter.uebergebeneModule.remove(position);
                //    mAdapter.notifyItemRemoved(position);
                // mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }
            public void onRightClicked(int position) {
                position = position + 1;

                try {
                    if (position < (mAdapter.getItemCount()-1)) {


                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                        viewItem.setVisibility(View.VISIBLE);
                        View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position + 1);
                        viewItem2.setVisibility(View.VISIBLE);
                    }

                } catch (NullPointerException e) {

                }

                try {

                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position-2);
                        View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position-3);
                        viewItem.setVisibility(View.VISIBLE);
                        viewItem2.setVisibility(View.VISIBLE);

                    View viewItem3 = recyclerView.getLayoutManager().findViewByPosition(position);
                    viewItem3.setVisibility(View.VISIBLE);

                } catch (NullPointerException e) {

                }

                try {
                    View viewItem3 = recyclerView.getLayoutManager().findViewByPosition(position);
                    viewItem3.setVisibility(View.VISIBLE);

                } catch (NullPointerException e) {

                }
                //mAdapter.uebergebeneModule.set(position,positionspeichern);
                //mAdapter.notifyItemInserted(position);


            }

        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);


        //Userinterface Komponenten Initialiseren
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView4);
        recyclerView.setVisibility(View.VISIBLE);
        calendar = (CalendarView) v.findViewById(R.id.caCalender);
        btnsuche = (Button) v.findViewById(R.id.btnDatum);

        //btnsuche clicklistener überprüft ob der Kalender öffnen button angetippt wurde
        //Es werden bei eingeschalteten Kalender nur Prüfobjekte mit übereinstimmenden Datum angezeigt
        calendar.setVisibility(View.GONE);
        btnsuche.setOnClickListener(new View.OnClickListener() {
            boolean speicher = true;
            @Override
            public void onClick(View v) {
                if (!speicher) {
                    calendar.setVisibility(View.GONE);
                    //calendar.getLayoutParams().height = 0;
                    List<String> modulundstudiengang = new ArrayList<>();
                    List<String> prueferundsemster = new ArrayList<>();
                    List<String> datum = new ArrayList<>();
                    List<String> id = new ArrayList<>();
                    List<String> ID = new ArrayList<>();
                    List<String> Pruefform = new ArrayList<>();
                    //Lokale Datenbank initialisieren
                    AppDatabase roomdaten = AppDatabase.getAppDatabase(getContext());
                    List<Pruefplan> userdaten = roomdaten.userDao().getAll(validation);

                    for (int i = 0; i < WerteZumAnzeigen.size(); i++) {
                        modulundstudiengang.add(userdaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getModul() + "\n " + userdaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getStudiengang());
                        prueferundsemster.add(userdaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getErstpruefer() + " " + userdaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getZweitpruefer() + " " + userdaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getSemester() + " ");
                        datum.add(userdaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getDatum());
                        id.add(userdaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getModul());
                        ID.add(userdaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getID());
                        Pruefform.add(userdaten.get(Integer.valueOf(WerteZumAnzeigen.get(i))).getPruefform());

                    }
                    // define an adapter
                    mAdapter = new MyAdapter(modulundstudiengang, prueferundsemster, datum, id,ID,Pruefform,mLayout);
                    recyclerView.setAdapter(mAdapter);
                    speicher = true;
                } else {
                    //Kalender ist geöffnet, nur übereinstimmende Prüfungen anzeigen
                    calendar.setVisibility(View.VISIBLE);
                    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                            AppDatabase roomdaten = AppDatabase.getAppDatabase(getContext());
                            List<Pruefplan> userdaten = roomdaten.userDao().getAll(validation);
                            List<String> modulundstudiengang = new ArrayList<>();
                            List<String> prueferundsemester = new ArrayList<>();
                            List<String> datum = new ArrayList<>();
                            List<String> modul = new ArrayList<>();
                            List<String> ID = new ArrayList<>();
                            List<String> Pruefform = new ArrayList<>();
                            //Creating editor to store uebergebeneModule to shared preferences
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
                            for (int i = 0; i < userdaten.size(); i++) {
                                String[] date2 = userdaten.get(i).getDatum().split(" ");
                                if (date2[0].equals(date)) {
                                    modulundstudiengang.add(userdaten.get(i).getModul() + "\n " + userdaten.get(i).getStudiengang());
                                    prueferundsemester.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester() + " ");
                                    datum.add(userdaten.get(i).getDatum());
                                    modul.add(userdaten.get(i).getModul());
                                    ID.add(userdaten.get(i).getID());
                                    Pruefform.add(userdaten.get(i).getPruefform());
                                }
                            }
                            // define an adapter
                            //Werte an den Adapter übergeben
                            mAdapter = new MyAdapter(modulundstudiengang, prueferundsemester, datum, modul,ID,Pruefform,mLayout);
                            //Anzeigen von recyclerview
                            recyclerView.setAdapter(mAdapter);
                        }
                    });
                    speicher = false;
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    View viewItem3 = recyclerView.getLayoutManager().findViewByPosition(position2 +1);
                    View viewItem4 = recyclerView.getLayoutManager().findViewByPosition(position2 + 2);
                    viewItem3.setVisibility(View.VISIBLE);
                    viewItem4.setVisibility(View.VISIBLE);


                } catch (NullPointerException e) {
                }

                try {
                    View viewItem = recyclerView.getLayoutManager().findViewByPosition(position2-2);
                    View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position2-3);
                    viewItem.setVisibility(View.VISIBLE);
                    viewItem2.setVisibility(View.VISIBLE);

                } catch (NullPointerException e) {
                }

            }
        });



        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new   RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Log.e("@@@@@",""+position2);
                        try {
                            View viewItem = recyclerView.getLayoutManager().findViewByPosition(position2-2);
                            View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position2-3);
                            View viewItem3 = recyclerView.getLayoutManager().findViewByPosition(position2 +1);
                            View viewItem4 = recyclerView.getLayoutManager().findViewByPosition(position2 + 2);
                            viewItem.setVisibility(View.VISIBLE);
                            viewItem2.setVisibility(View.VISIBLE);
                            viewItem3.setVisibility(View.VISIBLE);
                            viewItem4.setVisibility(View.VISIBLE);
                        }catch (Exception e)
                        {
                        }
                    }
                })
        );
        return v;
    }
}






