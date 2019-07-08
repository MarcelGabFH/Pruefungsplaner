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

import com.example.fhb.pruefungsplaner.data.AppDatabase;
import com.example.fhb.pruefungsplaner.data.User;
import com.example.fhb.pruefungsplaner.model.RetrofitConnect;

import java.util.ArrayList;
import java.util.List;

import static com.example.fhb.pruefungsplaner.MainActivity.Jahr;
import static com.example.fhb.pruefungsplaner.MainActivity.Pruefphase;
import static com.example.fhb.pruefungsplaner.MainActivity.RueckgabeStudiengang;


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
                    if (RetrofitConnect.checkuebertragung)
                    {

                        return "Executed";
                    }
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    Thread.interrupted();
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
        protected void onPreExecute() {}

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






        swipeController = new SwipeController(new SwipeControllerActions() {
            String positionspeichern;

            @Override
            public void onLeftClicked(int position) {

                position = position + 1;
                final int position2 = position - 1;

                if ((position + 2) >= mAdapter.getItemCount()) {
                    try {
                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position - 2);
                        View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position - 3);
                        viewItem.setVisibility(View.INVISIBLE);
                        viewItem2.setVisibility(View.INVISIBLE);
                        mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());

                        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                                //swipeController.onDrawup(c);
                            }
                        });
                    } catch (NullPointerException e) {


                    }

                } else {
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
                    } catch (NullPointerException e) {

                    }

                }
                // mAdapter.values.remove(position);
                //    mAdapter.notifyItemRemoved(position);
                // mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }


            public void onRightClicked(int position) {
                position = position + 1;

                try {
                    if ((position + 2) >= mAdapter.getItemCount()) {

                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position - 2);
                        View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position - 3);
                        viewItem.setVisibility(View.VISIBLE);
                        viewItem2.setVisibility(View.VISIBLE);

                    } else {
                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                        View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position + 1);
                        viewItem.setVisibility(View.VISIBLE);
                        viewItem2.setVisibility(View.VISIBLE);
                    }
                } catch (NullPointerException e) {

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

                    mAdapter = new MyAdapter(input, input2, input3, input4,ID,Pruefform);

                    recyclerView.setAdapter(mAdapter);

                    speicher = true;
                } else {
                    calendar.setVisibility(View.VISIBLE);
                    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                            List<User> userdaten = roomdaten.userDao().getAll(validation);

                            List<String> input = new ArrayList<>();
                            List<String> input2 = new ArrayList<>();
                            List<String> input3 = new ArrayList<>();
                            List<String> input4 = new ArrayList<>();
                            List<String> ID = new ArrayList<>();
                            List<String> Pruefform = new ArrayList<>();
                            //Creating editor to store values to shared preferences
                            if (month < 10) {
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

                            for (int i = 0; i < userdaten.size(); i++) {
                                String[] date2 = userdaten.get(i).getDatum().split(" ");
                                if (date2[0].equals(date)) {
                                    input.add(userdaten.get(i).getModul() + "\n " + userdaten.get(i).getStudiengang());
                                    input2.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester() + " ");
                                    input3.add(userdaten.get(i).getDatum());
                                    input4.add(userdaten.get(i).getModul());
                                    ID.add(userdaten.get(i).getID());
                                    Pruefform.add(userdaten.get(i).getID());
                                }
                            }// define an adapter
                            mAdapter = new MyAdapter(input, input2, input3, input4,ID,Pruefform);

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






