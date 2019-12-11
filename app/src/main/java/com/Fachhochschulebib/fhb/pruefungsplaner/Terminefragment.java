package com.Fachhochschulebib.fhb.pruefungsplaner;

//////////////////////////////
// Terminefragment
//
//
//
// autor:
// inhalt:  Prüfungen aus der Klasse Prüfplaneintrag werden abgefragt und zur darstelllung an den Recycleview adapter übergeben
// zugriffsdatum: 11.12.19
//
//
//
//
//
//
//////////////////////////////

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.Pruefplan;
import com.Fachhochschulebib.fhb.pruefungsplaner.model.RetrofitConnect;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.pruefJahr;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.aktuellePruefphase;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.rueckgabeStudiengang;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MyAdapter.favcheck;


public class Terminefragment extends Fragment {

    SharedPreferences mSharedPreferences;
    public List<String> WerteZumAnzeigen;
    private FragmentTransaction ft;
    private RecyclerView recyclerView;
    private CalendarView calendar;
    private Button btnsuche;
    boolean checkclick;
    private String date;
    private String month2;
    private String day2;
    private String positionspeichern;
    private int aktuellePosition = 0;
    private String year2;
    public static String validation;
    SwipeController swipeController = null;
    MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;

    AppDatabase datenbank = AppDatabase.getAppDatabase(getContext());
    List<Pruefplan> pruefplandaten = datenbank.userDao().getAll(validation);


    public void onCreate(Bundle savedInstanceState) {




        LongOperation asynctask = new LongOperation();

        asynctask.execute("");
       validation = pruefJahr + rueckgabeStudiengang + aktuellePruefphase;

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

            List<Pruefplan> datenbank = Terminefragment.this.datenbank.userDao().getAll(validation);

            List<String> studiengangUndModul = new ArrayList<>();
            List<String> prueferundModul = new ArrayList<>();
            List<String> datum = new ArrayList<>();
            List<String> id = new ArrayList<>();
            List<String> ID = new ArrayList<>();
            List<String> pruefform = new ArrayList<>();


            for (int i = 0; i < datenbank.size(); i++) {
                studiengangUndModul.add(datenbank.get(i).getModul() + "\n " + datenbank.get(i).getStudiengang());
                prueferundModul.add(datenbank.get(i).getErstpruefer() + " " + datenbank.get(i).getZweitpruefer() + " " + datenbank.get(i).getSemester() + " ");
                datum.add(datenbank.get(i).getDatum());
                id.add(datenbank.get(i).getID());
                ID.add(datenbank.get(i).getID());
                pruefform.add(datenbank.get(i).getPruefform());
            }// define an adapter


           // System.out.println(String.valueOf(userdaten.size()));

            mAdapter = new MyAdapter(studiengangUndModul, prueferundModul, datum, id,ID,pruefform,mLayout);

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
        mLayout = recyclerView.getLayoutManager();



        List<Pruefplan> pruefplandaten = datenbank.userDao().getAll(validation);

        List<String> modulundstudiengang = new ArrayList<>();
        List<String> prueferundsemester = new ArrayList<>();
        List<String> datum = new ArrayList<>();
        List<String> id = new ArrayList<>();
        List<String> ID = new ArrayList<>();
        List<String> Pruefform = new ArrayList<>();


        for (int i = 0; i < pruefplandaten.size(); i++) {
            modulundstudiengang.add(pruefplandaten.get(i).getModul() + "\n " + pruefplandaten.get(i).getStudiengang());
            prueferundsemester.add(pruefplandaten.get(i).getErstpruefer() + " " + pruefplandaten.get(i).getZweitpruefer() + " " + pruefplandaten.get(i).getSemester() + " ");
            datum.add(pruefplandaten.get(i).getDatum());
            id.add(pruefplandaten.get(i).getID());
            ID.add(pruefplandaten.get(i).getID());
            Pruefform.add(pruefplandaten.get(i).getPruefform());
        }// define an adapter


        // System.out.println(String.valueOf(userdaten.size()));

        checkclick = true;
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new   RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick( final View view, final  int position) {
                        //LinearLayout layout1 =( LinearLayout) view.findViewById(R.id.linearLayout);
                        final TextView txtSecondScreen = (TextView) view.findViewById(R.id.txtSecondscreen);
                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                        LinearLayout layout1 =(LinearLayout) viewItem.findViewById(R.id.linearLayout);

                        layout1.setOnClickListener(new  View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("@@@@@", "" + position);
                                if (txtSecondScreen.getVisibility() == v.VISIBLE) {
                                    txtSecondScreen.setVisibility(v.GONE);

                                } else {
                                    txtSecondScreen.setVisibility(v.VISIBLE);
                                    txtSecondScreen.setText(mAdapter.giveString(position));
                                }
                            }
                        });


                        // TODO Handle item click
                        Log.e("@@@@@",""+ aktuellePosition);
/*
                        try {
                            View viewItem = recyclerView.getLayoutManager().findViewByPosition(aktuellePosition -1);
                            View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(aktuellePosition -2);
                            View viewItem3 = recyclerView.getLayoutManager().findViewByPosition(aktuellePosition +1);
                            View viewItem4 = recyclerView.getLayoutManager().findViewByPosition(aktuellePosition + 2);
                            viewItem.setVisibility(View.VISIBLE);
                            viewItem2.setVisibility(View.VISIBLE);
                            viewItem3.setVisibility(View.VISIBLE);
                            viewItem4.setVisibility(View.VISIBLE);
                        }catch (Exception e)
                        { }
*/
                    }
                })
        );




/*
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked( int position) {
                aktuellePosition = position;
                Log.e("@@",""+position);

                if( position < (mAdapter.getItemCount() - 2)) {
                    position = position + 1;
                    final int aktuellepos = position - 1;
                    try {
                        positionspeichern = mAdapter.uebergebeneModule.get(position);
                       View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                       View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position + 1);
                        viewItem.setVisibility(View.INVISIBLE);
                        viewItem2.setVisibility(View.INVISIBLE);

                        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                            @Override
                            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                                String s = mAdapter.giveString(aktuellepos);
                                swipeController.onDraw(c, s);
                            }
                        });
                    } catch (Exception e) {

                    }

                }
                else{
                    final int aktuellepos = position - 1;
                    positionspeichern = mAdapter.uebergebeneModule.get(position);
                    View viewItem = recyclerView.getLayoutManager().findViewByPosition(position -1);
                    View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position - 2);
                    viewItem.setVisibility(View.INVISIBLE);
                    viewItem2.setVisibility(View.INVISIBLE);
                    recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                            String s = mAdapter.giveString(aktuellepos + 1);
                            swipeController.onDraw2(c, s);
                        }
                    });
                }
                    // mAdapter.uebergebeneModule.remove(position);
                    //    mAdapter.notifyItemRemoved(position);
                    // mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
                }


            public void onRightClicked(int position) {
                position = position + 1;
                    try {
                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                        View viewItem2 = recyclerView.getLayoutManager().findViewByPosition(position + 1);
                        viewItem.setVisibility(View.VISIBLE);
                        viewItem2.setVisibility(View.VISIBLE);
                    } catch (NullPointerException e) {

                    }

                try {
                    View viewItem3 = recyclerView.getLayoutManager().findViewByPosition(position - 2);
                    View viewItem4 = recyclerView.getLayoutManager().findViewByPosition(position -3);
                    viewItem3.setVisibility(View.VISIBLE);
                    viewItem4.setVisibility(View.VISIBLE);
                } catch (NullPointerException e) {

                }

                //mAdapter.uebergebeneModule.set(position,positionspeichern);
                //mAdapter.notifyItemInserted(position);
            }
        });

*/

        mAdapter = new MyAdapter(modulundstudiengang, prueferundsemester, datum, id,ID,Pruefform,mLayout);
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });



        //Tocuhhelper für die Recyclerview-Komponente, zum überprüfen ob gescrollt wurde
        //ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        //itemTouchhelper.attachToRecyclerView(recyclerView);

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
                    List<Pruefplan> userdaten = datenbank.userDao().getAll(validation);

                    //variablen für die Datenbankwerte
                    List<String> studiengangundmodul = new ArrayList<>();
                    List<String> prueferundsemester = new ArrayList<>();
                    List<String> datum = new ArrayList<>();
                    List<String> id = new ArrayList<>();
                    List<String> ID = new ArrayList<>();
                    List<String> pruefform = new ArrayList<>();


                    //hinzufügen der Datenbank werte zu den Variablen
                    for (int i = 0; i < userdaten.size(); i++) {
                        studiengangundmodul.add(userdaten.get(i).getModul() + "\n " + userdaten.get(i).getStudiengang());
                        prueferundsemester.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester() + " ");
                        datum.add(userdaten.get(i).getDatum());
                        id.add(userdaten.get(i).getID());
                        ID.add(userdaten.get(i).getID());
                        pruefform.add(userdaten.get(i).getPruefform());
                    }// define an adapter


                    if (Terminefragment.this.pruefplandaten.size() < 1) {
                        for (int i = 0; i < userdaten.size(); i++){
                            id.add(userdaten.get(i).getID());
                        }
                    }
                    else {
                        for (int i = 0; i < Terminefragment.this.pruefplandaten.size(); i++){
                            id.add(userdaten.get(i).getID());
                        }}
                   // System.out.println(String.valueOf(userdaten.size()));
                    //Recyclerview Adapter mit Werten füllen
                    mAdapter = new MyAdapter(studiengangundmodul, prueferundsemester, datum, id,ID,pruefform,mLayout);
                    recyclerView.setAdapter(mAdapter);
                    speicher = true;
                } else {
                    //Kalender wurde eingeschalet
                    calendar.setVisibility(View.VISIBLE);

                    //Es werden durch setOnDateChangeListener nur Prüfungen mit dem ausgewählten Datum angezeigt
                    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                            //Datenbank
                            List<Pruefplan> userdaten = datenbank.userDao().getAll(validation);

                            //Variablen
                            List<String> studiengangundmodul = new ArrayList<>();
                            List<String> prueferundsemester = new ArrayList<>();
                            List<String> datum = new ArrayList<>();
                            List<String> id = new ArrayList<>();
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
                                    studiengangundmodul.add(userdaten.get(i).getModul() + "\n " + userdaten.get(i).getStudiengang());
                                    prueferundsemester.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester() + " ");
                                    datum.add(userdaten.get(i).getDatum());
                                    id.add(userdaten.get(i).getModul());
                                    ID.add(userdaten.get(i).getID());
                                    Pruefform.add(userdaten.get(i).getID());
                                }
                            }// define an adapter

                            //Adapter mit Werten füllen
                            mAdapter = new MyAdapter(studiengangundmodul, prueferundsemester, datum, id,ID,Pruefform,mLayout);

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






