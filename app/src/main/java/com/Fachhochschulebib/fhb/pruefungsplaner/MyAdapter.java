package com.Fachhochschulebib.fhb.pruefungsplaner;

//////////////////////////////
// MyAdapter Recycleview
//
//
//
// autor:
// inhalt:  unterteilung von allen Prüfungen in einzelne tabellen und darstellung
// zugriffsdatum: 02.05.19
//
//
//
//
//
//
//////////////////////////////

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.User;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public List<String> values;
    private List<String> studiengang2;
    private List<String> index;
    private List<String> Datum;
    private List<String> pruefform;
    private boolean speicher;
    private String studiengang;
    private Context context;
    private Intent calIntent;
    private RecyclerView.LayoutManager Mlayout;
    private List<String> ppid2;
   private GregorianCalendar calDate =new GregorianCalendar();

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<String> myDataset, List<String> myDataset2, List<String> myDatasetDatum, List<String> index2,List<String> ppid,List<String> Pruefform,RecyclerView.LayoutManager mLayout) {
        values = myDataset;
        Datum = myDatasetDatum;
        studiengang2 = myDataset2;
        index = index2;
        ppid2 = ppid;
        pruefform = Pruefform;
        Mlayout = mLayout;



    }

    public void add(int position, String item, String studiengang) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.termine, parent, false);
        context = v.getContext();
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String name = values.get(position);
        String[] modulname = name.split(" ");
        studiengang = "";

        int b;
        for (b = 0; b < (modulname.length - 1); b++) {
            studiengang = (studiengang + " " + modulname[b]);

        }




        holder.txtHeader.setText(name);
        holder.ivicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.zahl1 = position;
                AppDatabase roomdaten2 =  AppDatabase.getAppDatabase(context);

                List<User> userdaten = roomdaten2.userDao().getAll2();


                //Überprüfung ob Prüfitem Favorisiert wurde und angeklickt
                int i;
                //Toast.makeText(v.getContext(),String.valueOf(userdaten.size()), Toast.LENGTH_SHORT).show();
                speicher = false;
                for (i = 0; i < userdaten.size(); i++) {
                            if ((userdaten.get(i).getID().toString().equals(ppid2.get(position) )& ( userdaten.get(i).getFavorit() ))) {
                                speicher = true;
                               // Toast.makeText(v.getContext(), "129", Toast.LENGTH_SHORT).show();
                            } }

                //Speichern des Prüfitem als Favorit
                if (!speicher) {
                   // Toast.makeText(v.getContext(), "137", Toast.LENGTH_SHORT).show();
                    for (i = 0; i < userdaten.size(); i++) {
                            if ((userdaten.get(i).getID().toString().equals(ppid2.get(position) ) & (!userdaten.get(i).getFavorit() ))) {
                                roomdaten2.userDao().update(true,Integer.valueOf(ppid2.get(position)));
                               // Toast.makeText(v.getContext(), "140", Toast.LENGTH_SHORT).show();
                                List<User> userdaten2 = roomdaten2.userDao().getAll2();
                                //Toast.makeText(v.getContext(), String.valueOf(userdaten2.get(i).getFavorit()), Toast.LENGTH_SHORT).show();
                            }
                    }


                    //Überprüfung ob Pürfungen zum Google Kalender Hinzugefügt werden sollen
                    SharedPreferences mSharedPreferences2 = v.getContext().getSharedPreferences("json8", 0);
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor mEditor2 = mSharedPreferences2.edit();
                    mEditor2.apply();
                    String strJson2 = mSharedPreferences2.getString("jsondata2", "0");

                    //Überprüfung des Wertes, wenn strJson2 "true" ist dann ist der Google Kalender aktiviert
                    boolean speicher2 = false;
                    int a = 0;
                    for (a = 0; a < strJson2.length(); a++) {
                         String ss1 = String.valueOf(strJson2.charAt(a));
                        if (ss1.equals(String.valueOf(1))) {
                            speicher2 = true;

                        }
                    }

                    //Hinzufügen der Prüfungen zum Google Kalender
                    CheckGoogleCalendar checkeintrag = new CheckGoogleCalendar();
                    checkeintrag.setCtx(context);


                    //Abrage vom geklickten Item
                    if(checkeintrag.checkCal(Integer.valueOf(ppid2.get(position)))) {
                        if (speicher2) {

                            //ermitteln von benötigten Variablen
                            String[] s = Datum.get(position).split(" ");
                            System.out.println(s[0]);
                            String[] ss = s[0].split("-");
                            System.out.println(s[0]);
                            holder.txtthirdline.setText("Uhrzeit: " + s[1].substring(0, 5).toString());
                            holder.button.setText(ss[2].toString() + "." + ss[1].toString() + "." + ss[0].toString());
                            final String[] sa = studiengang2.get(position).split(" ");
                            holder.txtFooter.setText("Prüfer: " + sa[0] + ", " + sa[1] + "  Semester: " + sa[2]);
                            String Semester = String.valueOf(holder.txtFooter.getText());
                            String Semester5 = Semester.toString();
                            String name = values.get(position);
                            String[] modulname = name.split(" ");
                            studiengang = "";
                            int b;
                            for (b = 0; b < (modulname.length - 1); b++) {
                                studiengang = (studiengang + " " + modulname[b]);
                            }

                            int uhrzeit1 = Integer.valueOf(s[1].substring(0, 2));
                            int uhrzeit2 = Integer.valueOf(s[1].substring(4, 5));
                            calDate = new GregorianCalendar(Integer.valueOf(ss[0]), (Integer.valueOf(ss[1]) - 1), Integer.valueOf(ss[2]), uhrzeit1, uhrzeit2);


                            //Methode zum speichern im Kalender
                            int calendarid = calendarID(studiengang);


                            //funktion im Google Kalender um PrüfID und calenderID zu speichern
                            checkeintrag.insertCal(Integer.valueOf(ppid2.get(position)), calendarid);


//voher genutzte Kalenderfunktion
/*
                            calIntent = new Intent(Intent.ACTION_INSERT);
                            calIntent.setType("vnd.android.cursor.item/event");
                            //ContentResolver cr = context.getContentResolver();
                            //ContentValues calIntent = new ContentValues();
                            int uhrzeit1 = Integer.valueOf(s[1].substring(0, 2));
                            int uhrzeit2 = Integer.valueOf(s[1].substring(4, 5));
                            calIntent.putExtra(CalendarContract.Events.TITLE, studiengang);
                            calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Fachhochschule Bielefeld");
                            calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "");
                            calDate = new GregorianCalendar(Integer.valueOf(ss[0]), (Integer.valueOf(ss[1]) - 1), Integer.valueOf(ss[2]), uhrzeit1, uhrzeit2);
                            calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                    calDate.getTimeInMillis());
                            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                    (calDate.getTimeInMillis() + (90 * 60000)));
                            //v.getContext().startActivity(calIntent);
                            //Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, calIntent);
                            //String eventID = uri.getLastPathSegment();
                            //checkeintrag.insertCal(Integer.valueOf(ppid2.get(position)), 22);
*/
                            //calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                        }
                    }
                        Toast.makeText(v.getContext(), "Hinzugefügt", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Aufteilung Nach Verschieden tagen
        String[] s = Datum.get(position).split(" ");
        System.out.println(s[0]);
        if (position > 0) {
            String[] s2 = Datum.get(position - 1).split(" ");
            if (s[0].toString().equals(s2[0].toString())) {
                holder.button.setHeight(0);
            }
        }


        //darstellen der Werte in der Prüfitem Komponente
        String[] ss = s[0].split("-");
        System.out.println(s[0]);
        holder.txtthirdline.setText("Uhrzeit: " + s[1].substring(0, 5).toString());
        holder.button.setText(ss[2].toString() + "." + ss[1].toString() + "." + ss[0].toString());
        final String[] sa = studiengang2.get(position).split(" ");
        holder.txtFooter.setText("Prüfer: " + sa[0] + ", " + sa[1] + "  Semester: " + sa[2]);
        String Semester = String.valueOf(holder.txtFooter.getText());
        String Semester5 = Semester.toString();
        //holder.txtthirdline.setText("Semester: " + Semester5.toString());

    }

    //Methode zum darstellen der "weiteren Informationen"
    public String giveString(int position) {

        try {
            String name = values.get(position);
            String[] modulname = name.split(" ");
            studiengang = "";
            int b;
            for (b = 0; b < (modulname.length - 1); b++) {
                studiengang = (studiengang + " " + modulname[b]);

            }
            AppDatabase roomdaten2 = AppDatabase.getAppDatabase(context);
            List<User> userdaten = roomdaten2.userDao().getAll2();


        String[] aufteilung1 = Datum.get(position).split(" ");
        String[] aufteilung2 = aufteilung1[0].split("-");
        //holder.txtthirdline.setText("Uhrzeit: " + aufteilung1[1].substring(0, 5).toString());
        final String[] sa = studiengang2.get(position).split(" ");
        //AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
        String s = ("Informationen zur Prüfung \n \n Studiengang: " + modulname[modulname.length - 1] + "\n Modul: " + studiengang + "\n Erstprüfer: " + sa[0] + " \n Zweitprüfer: " + sa[1] + "\n Datum: " + aufteilung2[2].toString() + "." + aufteilung2[1].toString() + "." + aufteilung2[0].toString() + " \n Uhrzeit: " + aufteilung1[1].substring(0, 5).toString() +"Uhr" + " \n Raum: Unbekannt " +"\n Prüfungsform: "+ pruefform.get(position
        ) + "\n \n \n \n \n \n ");

        return (s);
        }catch(Exception e){

        }
        return ("0");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }


    public int disabbleview() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {


        return position;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView txtHeader;
        private TextView txtFooter;
        private Integer zahl1;
        private TextView txtthirdline;
        public LinearLayout layout2;
        private ImageView ivicon;
        private Button button;
        private View layout;


        private ViewHolder(View v) {
            super(v);
            layout = v;
            ivicon = (ImageView) v.findViewById(R.id.icon);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            txtthirdline = (TextView) v.findViewById(R.id.thirdLine);
            button = (Button) v.findViewById(R.id.button7);
            //button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout2 = (LinearLayout) v.findViewById(R.id.linearLayout);

        }
    }

    public int calendarID(String eventtitle){

        final ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.CALENDAR_ID, 2);
        event.put(CalendarContract.Events.TITLE, studiengang);
        event.put(CalendarContract.Events.DESCRIPTION, "Fachhochschule Bielefeld");
        event.put(CalendarContract.Events.DTSTART, calDate.getTimeInMillis());
        event.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis() + (90 * 60000));
        event.put(CalendarContract.Events.ALL_DAY, 0);   // 0 for false, 1 for true
        event.put(CalendarContract.Events.HAS_ALARM, 0); // 0 for false, 1 for true
        String timeZone = TimeZone.getDefault().getID();
        event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);
        Uri baseUri;

        if (Build.VERSION.SDK_INT >= 8) {
            baseUri = Uri.parse("content://com.android.calendar/events");

        } else {
            baseUri = Uri.parse("content://calendar/events");
        }

        context.getContentResolver().insert(baseUri, event);


        int result = 0;
        String projection[] = { "_id", "title" };
        Cursor cursor = context.getContentResolver().query(baseUri, null, null, null,
                null);

        if (cursor.moveToFirst()) {

            String calName;
            String calID;

            int nameCol = cursor.getColumnIndex(projection[1]);
            int idCol = cursor.getColumnIndex(projection[0]);
            do {
                calName = cursor.getString(nameCol);
                calID = cursor.getString(idCol);

                if (calName != null && calName.contains(eventtitle)) {
                    result = Integer.parseInt(calID);
                }

            } while (cursor.moveToNext());
            cursor.close();

        }
        return (result);
    }

}