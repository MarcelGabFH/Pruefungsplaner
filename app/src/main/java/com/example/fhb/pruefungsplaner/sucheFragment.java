package com.example.fhb.pruefungsplaner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.fhb.pruefungsplaner.MainActivity.dateneinlesen;
import static com.example.fhb.pruefungsplaner.MainActivity.spStudiengangMain;
import static com.example.fhb.pruefungsplaner.Tabelle.ft;


public class sucheFragment extends Fragment {
    RecyclerView.Adapter mAdapter;
    final List<String> Semester = new ArrayList();
    final List<String> Studiengang = new ArrayList();
    final List<String> Datum = new ArrayList();
    final List<String> Prof = new ArrayList();
    final List<Integer> Rueckgabe = new ArrayList();
    final List<Integer> RueckgabeStudiengang = new ArrayList();
    final List<Integer> RueckgabeDatum = new ArrayList();
    final List<Integer> RueckgabeSemester = new ArrayList();
    final List<String> Fertigsortiert = new ArrayList();

    public sucheFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_suche, container, false);
        //setContentView(R.layout.hauptfenster);
            Spinners spinner = new Spinners();
            final Button btnSemester1 = (Button)  v.findViewById(R.id.btns1);
            final Button btnSemester2 = (Button)  v.findViewById(R.id.btns2);
            final Button btnSemester3 = (Button)  v.findViewById(R.id.btns3);
            final Button btnSemester4 = (Button)  v.findViewById(R.id.btns4);
            final Button btnSemester5 = (Button)  v.findViewById(R.id.btns5);
            final Button btnSemester6 = (Button)  v.findViewById(R.id.btns6);
            final Button btnWoche1 = (Button) v.findViewById(R.id.btnWoche1);
            final Button btnWoche2 = (Button) v.findViewById(R.id.btnWoche2);


            RueckgabeSemester.clear();
            btnSemester1.setOnClickListener(new Button.OnClickListener() {
                boolean geklickt = true;
                @Override
                public void onClick(View v) {
                    if(geklickt) {
                        if (RueckgabeSemester.size() <= 0) {
                            for (int i = 0; i < (dateneinlesen.laenge); i++) {
                                RueckgabeSemester.add(99999);
                            }
                        }

                        for(int i = 0;i < dateneinlesen.laenge;i++) {
                            if (String.valueOf(1).equals(dateneinlesen.semester[i].toString())) {
                                btnSemester1.setBackgroundResource(R.drawable.button_rounded_corners);
                                RueckgabeSemester.set(i,i);

                            }
                        }
                        geklickt = false;
                    }else {

                        btnSemester1.setBackgroundResource(R.drawable.button_rounded_corners2);

                        for(int i = 0;i < dateneinlesen.laenge;i++)
                        {
                            if (String.valueOf(1).equals(dateneinlesen.semester[i].toString()))
                            {
                                RueckgabeSemester.set(i,99999);
                            }
                        }

                        geklickt = true;
                    }
                }
            });

        btnSemester2.setOnClickListener(new Button.OnClickListener() {
            boolean geklickt = true;
            @Override
            public void onClick(View v) {
                if(geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.laenge); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }

                    for(int i = 0;i < dateneinlesen.laenge;i++) {
                        if (String.valueOf(2).equals(dateneinlesen.semester[i].toString())) {
                            btnSemester2.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i,i);

                        }
                    }
                    geklickt = false;
                }else {

                    btnSemester2.setBackgroundResource(R.drawable.button_rounded_corners2);

                    for(int i = 0;i < dateneinlesen.laenge;i++)
                    {
                        if (String.valueOf(2).equals(dateneinlesen.semester[i].toString()))
                        {
                            RueckgabeSemester.set(i,99999);
                        }
                    }

                    geklickt = true;
                }
            }
        });
        btnSemester3.setOnClickListener(new Button.OnClickListener() {
            boolean geklickt = true;
            @Override
            public void onClick(View v) {
                if(geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.laenge); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }

                    for(int i = 0;i < dateneinlesen.laenge;i++) {
                        if (String.valueOf(3).equals(dateneinlesen.semester[i].toString())) {
                            btnSemester3.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i,i);

                        }
                    }
                    geklickt = false;
                }else {

                    btnSemester3.setBackgroundResource(R.drawable.button_rounded_corners2);

                    for(int i = 0;i < dateneinlesen.laenge;i++)
                    {
                        if (String.valueOf(3).equals(dateneinlesen.semester[i].toString()))
                        {
                            RueckgabeSemester.set(i,99999);
                        }
                    }

                    geklickt = true;
                }
            }
        });

        btnSemester4.setOnClickListener(new Button.OnClickListener() {
            boolean geklickt = true;
            @Override
            public void onClick(View v) {
                if(geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.laenge); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }

                    for(int i = 0;i < dateneinlesen.laenge;i++) {
                        if (String.valueOf(4).equals(dateneinlesen.semester[i].toString())) {
                            btnSemester4.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i,i);

                        }
                    }
                    geklickt = false;
                }else {

                    btnSemester4.setBackgroundResource(R.drawable.button_rounded_corners2);

                    for(int i = 0;i < dateneinlesen.laenge;i++)
                    {
                        if (String.valueOf(4).equals(dateneinlesen.semester[i].toString()))
                        {
                            RueckgabeSemester.set(i,99999);
                        }
                    }

                    geklickt = true;
                }
            }
        });

        btnSemester5.setOnClickListener(new Button.OnClickListener() {
            boolean geklickt = true;
            @Override
            public void onClick(View v) {
                if(geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.laenge); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }
                    for(int i = 0;i < dateneinlesen.laenge;i++) {
                        if (String.valueOf(5).equals(dateneinlesen.semester[i].toString())) {
                            btnSemester5.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i,i);

                        }
                    }
                    geklickt = false;
                }else {
                    btnSemester5.setBackgroundResource(R.drawable.button_rounded_corners2);
                    for(int i = 0;i < dateneinlesen.laenge;i++)
                    {
                        if (String.valueOf(5).equals(dateneinlesen.semester[i].toString()))
                        {
                            RueckgabeSemester.set(i,99999);
                        }
                    }
                    geklickt = true;
                }
            }
        });

        btnSemester6.setOnClickListener(new Button.OnClickListener() {
            boolean geklickt = true;
            @Override
            public void onClick(View v) {
                if(geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.laenge); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }
                    for(int i = 0;i < dateneinlesen.laenge;i++) {
                        if (String.valueOf(6).equals(dateneinlesen.semester[i].toString())) {
                            btnSemester6.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i,i);

                        }
                    }
                    geklickt = false;
                }else {

                    btnSemester6.setBackgroundResource(R.drawable.button_rounded_corners2);

                    for(int i = 0;i < dateneinlesen.laenge;i++)
                    {
                        if (String.valueOf(6).equals(dateneinlesen.semester[i].toString()))
                        {
                            RueckgabeSemester.set(i,99999);
                        }
                    }
                    geklickt = true;
                }
            }
        });
        //Spinner aufruf und setzen der Spinner mit werten
            List<String> spinnerArray = new ArrayList<String>();
            List<String> spinnerArrayProf = new ArrayList<String>();
            List<String> spinnerArrayDatum = new ArrayList<String>();
            List<String> spinnerArraySemester = new ArrayList<String>();
            List<String> spinnerArrayFach = new ArrayList<String>();

            //anzahl der elemente
            int laenge = dateneinlesen.getlaenge();

            //aufruf spinnerklasse
            spinnerArray = spinner.Spinner(dateneinlesen.getStudiengang(), laenge);
            spinnerArrayProf = spinner.Spinner(dateneinlesen.getProfname(), laenge);
            spinnerArrayDatum = spinner.Spinner(dateneinlesen.getDatum(), laenge);
            spinnerArraySemester = spinner.Spinner(dateneinlesen.getSemester(), laenge);


            //adapter aufruf
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    v.getContext(), R.layout.simple_spinner_item, spinnerArray);
            ArrayAdapter<String> adapterProf = new ArrayAdapter<String>(
                    v.getContext(), android.R.layout.simple_spinner_item, spinnerArrayProf);
            ArrayAdapter<String> adapterDatum = new ArrayAdapter<String>(
                    v.getContext(), android.R.layout.simple_spinner_item, spinnerArrayDatum);
            ArrayAdapter<String> adapterSemester = new ArrayAdapter<String>(
                    v.getContext(), android.R.layout.simple_spinner_item, spinnerArraySemester);

            ArrayAdapter<String> adapteracPof = new ArrayAdapter<String>
                    (v.getContext(), android.R.layout.simple_list_item_1, spinnerArrayProf);

            //grafische ausgabe dropdown
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            adapterProf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterDatum.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //grafische ausgabe
            Spinner spStudiengang = (Spinner) v.findViewById(R.id.spStudiengang);
            spStudiengang.setAdapter(adapter);
            Spinner spProf = (Spinner) v.findViewById(R.id.spProf);
            spProf.setAdapter(adapterProf);
            Spinner spDatum = (Spinner) v.findViewById(R.id.spDatum);
            spDatum.setAdapter(adapterDatum);
            Spinner spSemester = (Spinner) v.findViewById(R.id.spSemester);
            spSemester.setAdapter(adapterSemester);
            final AutoCompleteTextView acProf = (AutoCompleteTextView) v.findViewById(R.id.acProfessor);
            acProf.setThreshold(1);//will start working from first character
            acProf.setAdapter(adapteracPof);//setting the adapter data into the AutoCompleteTextView
            Datum.add("Alle");

            Prof.add("Alle");
            Studiengang.add("Alle");
            //initialisierung der anfagswerte
            int i;
            for (i = 0; i < dateneinlesen.laenge; i++) {
                Rueckgabe.add(i);
                RueckgabeStudiengang.add(i);
                RueckgabeDatum.add(i);

            }
            acProf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                    int a;

                    for (a = 0; a < (dateneinlesen.laenge); a++) {
                        if (acProf.getText().toString().equals("Alle")) {
                            Rueckgabe.add(a);

                        } else
                        if (acProf.getText().toString().equals(dateneinlesen.profname[a].toString())) {

                            Rueckgabe.add(a);
                            TextView textt = (TextView) v.findViewById(R.id.txtmessage);
                            dateneinlesen.ab = Tabellenrueckgabe();
                        }
                    }
                    //txtview.setText(Prof.get(Prof.size()-1).toString() + dateneinlesen.profname[i].toString());
                }
                //... your stuf
            });


            spDatum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    RueckgabeDatum.clear();
                    Datum.add(parent.getItemAtPosition(position).toString());
                    ; //this is your selected item
                    int i;
                    for (i = 0; i < (dateneinlesen.laenge); i++) {

                        if (Datum.get(Datum.size() - 1).toString().equals("Alle")) {
                            RueckgabeDatum.add(i);
                        } else {
                            if (Datum.get(Datum.size() - 1).toString().equals(dateneinlesen.datum[i].toString())) {

                                RueckgabeDatum.add(i);
                            }
                        }
                        //txtview.setText(Prof.get(Prof.size()-1).toString() + dateneinlesen.profname[i].toString());
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spProf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Rueckgabe.clear();
                    Prof.add(parent.getItemAtPosition(position).toString()); //this is your selected item
                    int i;
                    for (i = 0; i < (dateneinlesen.laenge); i++) {
                        if (Prof.get(Prof.size() - 1).toString().equals("Alle")) {
                            //Rueckgabe.add(i);
                        } else {
                            if (Prof.get(Prof.size() - 1).toString().equals(dateneinlesen.profname[i].toString())) {

                                //Rueckgabe.add(i);
                                TextView textt = (TextView) v.findViewById(R.id.txtmessage);
                                //dateneinlesen.ab = Tabellenrueckgabe();
                                textt.setText(dateneinlesen.ab.toString());
                            }
                        }
                        //txtview.setText(Prof.get(Prof.size()-1).toString() + dateneinlesen.profname[i].toString());
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spStudiengang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    RueckgabeStudiengang.clear();
                    Studiengang.add(parent.getItemAtPosition(position).toString()); //this is your selected item
                    int i;
                    String a;
                    for (i = 0; i < (dateneinlesen.laenge); i++) {
                        if (Studiengang.get(Studiengang.size() - 1).toString().equals("Alle")) {
                            RueckgabeStudiengang.add(i);
                        } else {
                            if (Studiengang.get(Studiengang.size() - 1).toString().equals(dateneinlesen.studiengang[i].toString())) {


                                RueckgabeStudiengang.add(i);
                                dateneinlesen.ab = Tabellenrueckgabe();
                            }
                        }
                        //txtview.setText(Prof.get(Prof.size()-1).toString() + dateneinlesen.profname[i].toString());
                    }
                }
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Button but1 = (Button) v.findViewById(R.id.BtnOk);
            final TextView textt = (TextView) v.findViewById(R.id.txtmessage);
            but1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (acProf.getText().toString().equals("Alle")) {
                        int a;
                        Rueckgabe.clear();
                    for (a = 0; a < (dateneinlesen.laenge); a++) {

                        Rueckgabe.add(a);
                    }
                        }

                    dateneinlesen.ab = Tabellenrueckgabe();
                    textt.setText(dateneinlesen.ab.toString());
                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_placeholder, new Terminefragment());
                    ft.commit();

                }
            });

        btnWoche1.setOnClickListener(new View.OnClickListener() {
                boolean geklickt = true;
                @Override
                public void onClick(View v) {


                    if(geklickt) {
                        btnWoche1.setBackgroundColor(333333);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String dateTime2 = "0";
                        Date dateTime;
                        Calendar calendarendzeit =  Calendar.getInstance();
                        try {
                        String[] dtStart = dateneinlesen.datum[dateneinlesen.laenge-1].split(" ");
                        dateTime = dateFormat.parse(dtStart[0]);
                        dateTime2 = dateFormat.format(dateTime);
                        String[] sa = dateTime2.split("-");
                        calendarendzeit = Calendar.getInstance();
                        calendarendzeit.setTime(dateTime);
                        dateTime2 = String.valueOf(calendarendzeit.getTime());
                        calendarendzeit.set(Calendar.DAY_OF_MONTH,  Integer.valueOf(sa[2]));// for 5 days
                        } catch (ParseException e) {

                        }
                        for(int i = 0;i < dateneinlesen.laenge;i++) {
                            String[] dtStart = dateneinlesen.datum[i].split(" ");
                            try {
                                dateTime = dateFormat.parse(dtStart[0]);
                                dateTime2 = dateFormat.format(dateTime);
                                String[] sa = dateTime2.split("-");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dateTime);
                                calendar.set(Calendar.DAY_OF_MONTH,  Integer.valueOf(sa[2]));

                                if(calendar.after(calendarendzeit))
                                {
                                    Datum.add(dateTime2);
                                    ; //this is your selected item
                                    for (int a = 0; a < (dateneinlesen.laenge); a++) {
                                            String[] vergleich = dateneinlesen.datum[a].toString().split(" ");
                                            if (Datum.get(Datum.size() - 1).toString().equals(vergleich[0])) {
                                                //Toast.makeText(getContext(),Datum.get(Datum.size() - 1)+" / "+  dtStart[0] ,Toast.LENGTH_SHORT/2).show();
                                                RueckgabeDatum.add(a);
                                            }

                                        //txtview.setText(Prof.get(Prof.size()-1).toString() + dateneinlesen.profname[i].toString());
                                    }
                                }
                                dateTime2 = String.valueOf(calendar.getTime());
                            } catch (ParseException e) {
                            }
                        }
                        Toast.makeText(getContext(),calendarendzeit.toString(),Toast.LENGTH_SHORT).show();
                        geklickt = false;
                    }else
                    {
                        btnWoche1.setBackgroundResource(R.drawable.button_rounded_corners);

                        RueckgabeDatum.clear();
                        geklickt = true;
                    }
                }
            });

        RueckgabeSemester.clear();
        return v;
    }



    public List<String> Tabellenrueckgabe() {
        int i;
        int j;
        int k;
        int l;
        String test = "a";
        Fertigsortiert.clear();
        for (i = 0; i < (RueckgabeStudiengang.size()); i++) {
                        for (j = 0; j < (RueckgabeSemester.size()); j++) {
                        if (RueckgabeStudiengang.get(i).equals(RueckgabeSemester.get(j))) {
                            for (k = 0; k < (RueckgabeDatum.size()); k++) {
                        if (RueckgabeDatum.get(k).equals(RueckgabeStudiengang.get(i))) {
                            for (l = 0; l < (Rueckgabe.size()); l++) {
                                if (Rueckgabe.get(l).equals(RueckgabeStudiengang.get(i))) {
                                    Fertigsortiert.add(String.valueOf(RueckgabeStudiengang.get(i)));
                                    test = String.valueOf(RueckgabeDatum.get(k)) + test;
                                }
                            }
                        }
                    }
                    //RueckgabeStudiengang.add(i);
                }
            }
            //txtview.setText(Prof.get(Prof.size()-1).toString() + dateneinlesen.profname[i].toString());
        }
        return (Fertigsortiert);
    }
}
