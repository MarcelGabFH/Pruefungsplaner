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
import android.os.AsyncTask;
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

import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.User;
import com.Fachhochschulebib.fhb.pruefungsplaner.model.RetrofitConnect;

import java.util.ArrayList;
import java.util.List;

import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.Jahr;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.Pruefphase;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.RueckgabeStudiengang;


public class Terminefragment extends Fragment {

    SharedPreferences mSharedPreferences;
    public List<String> WerteZumAnzeigen;
    private FragmentTransaction ft;
    private RecyclerView recyclerView;
    private CalendarView calendar;
    private Button btnsuche;
    private String date;
    private String month2;
    private String day2;
    private int position2 = 0;
    private String year2;
    public static String validation;
    SwipeController swipeController = null;
    MyAdapter mAdapter;

    AppDatabase roomdaten = AppDatabase.getAppDatabase(getContext());
    List<User>dateneinlesen = roomdaten.userDao().getAll(validation);


    public void onCreate(Bundle savedInstanceState) {




        LongOperation asynctask = new LongOperation();

        asynctask.execute("");
       validation = Jahr+RueckgabeStudiengang+Pruefphase;

       super.onCreate(savedInstanceState);


    }

    class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            for (int c = 0; c < 1000; c++) {
                try {
                    Thread.sleep(3000);
                    if (RetrofitConnect.checkuebertragung)
                    {

                        return "Executed";
                    }

                } catch (InterruptedException e) {
                    Thread.interrupted();
                    return"Executed";
                }
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            List<User> userdaten = roomdaten.userDao().getAll(validation);

            List<String> input = new ArrayList<>();
            List<String> input2 = new ArrayList<>();
            List<String> input3 = new ArrayList<>();
            List<String> input4 = new ArrayList<>();
            List<String> ID = new ArrayList<>();
            List<String> Pruefform = new ArrayList<>();


            for (int i = 0; i < userdaten.size(); i++) {
                input.add(userdaten.get(i).getModul() + "\n " + userdaten.get(i).getStudiengang());
                input2.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester() + " ");
                input3.add(userdaten.get(i).getDatum());
                input4.add(userdaten.get(i).getID());
                ID.add(userdaten.get(i).getID());
                Pruefform.add(userdaten.get(i).getPruefform());
            }// define an adapter


           // System.out.println(String.valueOf(userdaten.size()));

            mAdapter = new MyAdapter(input, input2, input3, input4,ID,Pruefform);

            recyclerView.setAdapter(mAdapter);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.terminefragment, container, false);


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


        List<User> userdaten = roomdaten.userDao().getAll(validation);

        List<String> input = new ArrayList<>();
        List<String> input2 = new ArrayList<>();
        List<String> input3 = new ArrayList<>();
        List<String> input4 = new ArrayList<>();
        List<String> ID = new ArrayList<>();
        List<String> Pruefform = new ArrayList<>();


        for (int i = 0; i < userdaten.size(); i++) {
            input.add(userdaten.get(i).getModul() + "\n " + userdaten.get(i).getStudiengang());
            input2.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester() + " ");
            input3.add(userdaten.get(i).getDatum());
            input4.add(userdaten.get(i).getID());
            ID.add(userdaten.get(i).getID());
            Pruefform.add(userdaten.get(i).getPruefform());
        }// define an adapter


        // System.out.println(String.valueOf(userdaten.size()));

        mAdapter = new MyAdapter(input, input2, input3, input4,ID,Pruefform);

        recyclerView.setAdapter(mAdapter);




        swipeController = new SwipeController(new SwipeControllerActions() {
            String positionspeichern;

            @Override
            public void onLeftClicked( int position) {

                position2 = position;
                if( position < (mAdapter.getItemCount() -1)) {
                    position = position + 1;
                    final int position2 = position - 1;
                    try {
                        positionspeichern = mAdapter.values.get(position);
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
                    } catch (Exception e) {

                    }

                }
                else{
                    final int position2 = position - 1;
                    recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                            String s = mAdapter.giveString(position2);
                            swipeController.onDraw(c, s);
                        }
                    });

                }
                    // mAdapter.values.remove(position);
                    //    mAdapter.notifyItemRemoved(position);
                    // mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
                }


            public void onRightClicked(int position) {
                if( position < (mAdapter.getItemCount() - 1)) {
                position = position + 1;
                    try {

                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                        View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position + 1);
                        viewItem.setVisibility(View.VISIBLE);
                        viewItem2.setVisibility(View.VISIBLE);
                    } catch (NullPointerException e) {

                    }
                }
                //mAdapter.values.set(position,positionspeichern);
                //mAdapter.notifyItemInserted(position);
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if( position2 < (mAdapter.getItemCount() - 1)) {
                    try {
                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position2 +1);
                        View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position2 + 2);
                        viewItem.setVisibility(View.VISIBLE);
                        viewItem2.setVisibility(View.VISIBLE);
                    } catch (NullPointerException e) {
                    }
                }
            }
        });



        //Tocuhhelper für die Recyclerview-Komponente, zum überprüfen ob gescrollt wurde
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        //initialisieren der UI-Komponenten
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView4);
        recyclerView.setVisibility(View.VISIBLE);
        calendar = (CalendarView) v.findViewById(R.id.caCalender);
        btnsuche = (Button) v.findViewById(R.id.btnDatum);
        calendar.setVisibility(View.GONE);

        //Clicklistener für den Kalender,
        //Es wird überprüft welches Datum ausgewählt wurde
        btnsuche.setOnClickListener(new View.OnClickListener() {
            boolean speicher = true;

            @Override
            public void onClick(View v) {
                if (!speicher) {
                    calendar.setVisibility(View.GONE);

                    //Datenbankaufruf
                    List<User> userdaten = roomdaten.userDao().getAll(validation);

                    //variablen für die Datenbankwerte
                    List<String> input = new ArrayList<>();
                    List<String> input2 = new ArrayList<>();
                    List<String> input3 = new ArrayList<>();
                    List<String> input4 = new ArrayList<>();
                    List<String> ID = new ArrayList<>();
                    List<String> Pruefform = new ArrayList<>();


                    //hinzufügen der Datenbank werte zu den Variablen
                    for (int i = 0; i < userdaten.size(); i++) {
                        input.add(userdaten.get(i).getModul() + "\n " + userdaten.get(i).getStudiengang());
                        input2.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester() + " ");
                        input3.add(userdaten.get(i).getDatum());
                        input4.add(userdaten.get(i).getID());
                        ID.add(userdaten.get(i).getID());
                        Pruefform.add(userdaten.get(i).getPruefform());
                    }// define an adapter


                    if (dateneinlesen.size() < 1) {
                        for (int i = 0; i < userdaten.size(); i++){
                            input4.add(userdaten.get(i).getID());
                        }
                    }
                    else {
                        for (int i = 0; i < dateneinlesen.size(); i++){
                            input4.add(userdaten.get(i).getID());
                        }}


                   // System.out.println(String.valueOf(userdaten.size()));
                    //Recyclerview Adapter mit Werten füllen
                    mAdapter = new MyAdapter(input, input2, input3, input4,ID,Pruefform);
                    recyclerView.setAdapter(mAdapter);
                    speicher = true;
                } else {
                    //Kalender wurde eingeschalet
                    calendar.setVisibility(View.VISIBLE);

                    //Es werden durch setOnDateChangeListener nur Prüfungen mit dem ausgewählten Datum angezeigt
                    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                            //Datenbank
                            List<User> userdaten = roomdaten.userDao().getAll(validation);

                            //Variablen
                            List<String> input = new ArrayList<>();
                            List<String> input2 = new ArrayList<>();
                            List<String> input3 = new ArrayList<>();
                            List<String> input4 = new ArrayList<>();
                            List<String> ID = new ArrayList<>();
                            List<String> Pruefform = new ArrayList<>();

                            //unnötige Werte entfernen
                            if (month < 9) {
                                month2 = "0" + String.valueOf(month + 1);
                            } else {
                                month2 = String.valueOf(month+1);
                            }
                            if (dayOfMonth < 10) {
                                day2 = "0" + String.valueOf(dayOfMonth);
                            } else {
                                day2 = String.valueOf(dayOfMonth);
                            }
                            year2 = String.valueOf(year);
                            date = year2 + "-" + month2 + "-" + day2;
                            System.out.println(date);
                            for (int i = 0; i < userdaten.size(); i++) {
                                String[] date2 = userdaten.get(i).getDatum().split(" ");
                                System.out.println(date2[0]);

                                //Überprüfung ob das Prüfitem Datum mit dem ausgewählten Kalender datum übereinstimmt
                                if (date2[0].equals(date)) {
                                    System.out.println("check");
                                    input.add(userdaten.get(i).getModul() + "\n " + userdaten.get(i).getStudiengang());
                                    input2.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester() + " ");
                                    input3.add(userdaten.get(i).getDatum());
                                    input4.add(userdaten.get(i).getModul());
                                    ID.add(userdaten.get(i).getID());
                                    Pruefform.add(userdaten.get(i).getID());
                                }
                            }// define an adapter

                            //Adapter mit Werten füllen
                            mAdapter = new MyAdapter(input, input2, input3, input4,ID,Pruefform);

                            //Anzeigen
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






