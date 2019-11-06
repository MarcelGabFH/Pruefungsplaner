package com.Fachhochschulebib.fhb.pruefungsplaner;

//////////////////////////////
// sucheFragment
//
//
//
// autor:
// inhalt:  auswählen und suchen von professoren,modulen,semestern, prüfungsphase
// zugriffsdatum: 05.07.19
//
//
//
//
//
//
//////////////////////////////

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
import android.widget.Spinner;
import android.widget.TextView;

import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.User;

import java.util.ArrayList;
import java.util.List;


import static com.Fachhochschulebib.fhb.pruefungsplaner.Tabelle.ft;
import static com.Fachhochschulebib.fhb.pruefungsplaner.Terminefragment.validation;


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
    private AppDatabase database = AppDatabase.getAppDatabase(getContext());
    public sucheFragment()
    {

    }
    AppDatabase roomdaten = AppDatabase.getAppDatabase(getContext());
    List<User>dateneinlesen = roomdaten.userDao().getAll(validation);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_suche, container, false);
        //setContentView(R.layout.hauptfenster);
        Spinners spinner = new Spinners();
        final Button btnSemester1 = (Button) v.findViewById(R.id.btns1);
        final Button btnSemester2 = (Button) v.findViewById(R.id.btns2);
        final Button btnSemester3 = (Button) v.findViewById(R.id.btns3);
        final Button btnSemester4 = (Button) v.findViewById(R.id.btns4);
        final Button btnSemester5 = (Button) v.findViewById(R.id.btns5);
        final Button btnSemester6 = (Button) v.findViewById(R.id.btns6);

        RueckgabeSemester.clear();
        btnSemester1.setOnClickListener(new Button.OnClickListener() {
            boolean geklickt = true;

            @Override
            public void onClick(View v) {
                if (geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.size()); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }

                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(1).equals(dateneinlesen.get(i).getSemester())) {
                            btnSemester1.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i, i);

                        }
                    }
                    geklickt = false;
                } else {

                    btnSemester1.setBackgroundResource(R.drawable.button_rounded_corners2);

                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(1).equals(dateneinlesen.get(i).getSemester())) {
                            RueckgabeSemester.set(i, 99999);
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
                if (geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.size()); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }

                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(2).equals(dateneinlesen.get(i).getSemester())) {
                            btnSemester2.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i, i);

                        }
                    }
                    geklickt = false;
                } else {

                    btnSemester2.setBackgroundResource(R.drawable.button_rounded_corners2);

                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(2).equals(dateneinlesen.get(i).getSemester())) {
                            RueckgabeSemester.set(i, 99999);
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
                if (geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.size()); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }

                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(3).equals(dateneinlesen.get(i).getSemester().toString())) {
                            btnSemester3.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i, i);

                        }
                    }
                    geklickt = false;
                } else {

                    btnSemester3.setBackgroundResource(R.drawable.button_rounded_corners2);

                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(3).equals(dateneinlesen.get(i).getSemester().toString())) {
                            RueckgabeSemester.set(i, 99999);
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
                if (geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.size()); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }

                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(4).equals(dateneinlesen.get(i).getSemester())) {
                            btnSemester4.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i, i);

                        }
                    }
                    geklickt = false;
                } else {

                    btnSemester4.setBackgroundResource(R.drawable.button_rounded_corners2);

                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(4).equals(dateneinlesen.get(i).getSemester().toString())) {
                            RueckgabeSemester.set(i, 99999);
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
                if (geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.size()); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }
                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(5).equals(dateneinlesen.get(i).getSemester().toString())) {
                            btnSemester5.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i, i);
                        }
                    }
                    geklickt = false;
                } else {
                    btnSemester5.setBackgroundResource(R.drawable.button_rounded_corners2);
                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(5).equals(dateneinlesen.get(i).getSemester().toString())) {
                            RueckgabeSemester.set(i, 99999);
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
                if (geklickt) {
                    if (RueckgabeSemester.size() <= 0) {
                        for (int i = 0; i < (dateneinlesen.size()); i++) {
                            RueckgabeSemester.add(99999);
                        }
                    }
                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(6).equals(dateneinlesen.get(i).getSemester())) {
                            btnSemester6.setBackgroundResource(R.drawable.button_rounded_corners);
                            RueckgabeSemester.set(i, i);
                        }
                    }
                    geklickt = false;
                } else {

                    btnSemester6.setBackgroundResource(R.drawable.button_rounded_corners2);

                    for (int i = 0; i < dateneinlesen.size(); i++) {
                        if (String.valueOf(6).equals(dateneinlesen.get(i).getSemester())) {
                            RueckgabeSemester.set(i, 99999);
                        }
                    }
                    geklickt = true;
                }
            }
        });
        //Spinner aufruf und setzen der Spinner mit werten
        List<String> spinnerArray = new ArrayList<String>();
        List<String> spinnerArrayProf = new ArrayList<String>();

        //aufruf spinnerklasse

        spinnerArray = database.userDao().getModul();
        spinnerArray.add(0,"Alle Module");
        spinnerArrayProf = database.userDao().getErstpruefer();

        for (int i = 0;i< spinnerArray.size();i++){
            for (int a = i; a < spinnerArray.size();a++) {

                if (spinnerArray.get(i).equals(spinnerArray.get(a))){

                    spinnerArray.remove(a);
                }
            }}

        for (int i = 0;i< spinnerArrayProf.size();i++){
            for (int a = i;a< spinnerArrayProf.size();a++) {
                if (spinnerArrayProf.get(i).equals(spinnerArrayProf.get(a))) {
                    spinnerArrayProf.remove(a);
                }
            }}



        //adapter aufruf
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                v.getContext(), R.layout.simple_spinner_item, spinnerArray);
        ArrayAdapter<String> adapterProf = new ArrayAdapter<String>(
                v.getContext(), android.R.layout.simple_spinner_item, spinnerArrayProf);

        ArrayAdapter<String> adapteracPof = new ArrayAdapter<String>
                (v.getContext(), android.R.layout.simple_list_item_1, spinnerArrayProf);

        //grafische ausgabe dropdown
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        adapterProf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //grafische ausgabe
        Spinner spStudiengang = (Spinner) v.findViewById(R.id.spStudiengang);
        spStudiengang.setAdapter(adapter);
        Spinner spProf = (Spinner) v.findViewById(R.id.spProf);
        spProf.setAdapter(adapterProf);

        final AutoCompleteTextView acProf = (AutoCompleteTextView) v.findViewById(R.id.acProfessor);
        acProf.setThreshold(1);//will start working from first character
        acProf.setAdapter(adapteracPof);//setting the adapter data into the AutoCompleteTextView


        Prof.add("Alle");
        Studiengang.add("Alle");
        //initialisierung der anfagswerte
        int i;
        for (i = 0; i < dateneinlesen.size(); i++) {
            Rueckgabe.add(i);
            RueckgabeStudiengang.add(i);
            RueckgabeDatum.add(i);

        }
        acProf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int a;
                Rueckgabe.clear();
                for (a = 0; a < dateneinlesen.size(); a++) {
                    if (acProf.getText().toString().equals("Alle")) {
                        Rueckgabe.add(a);

                    } else if (acProf.getText().toString().equals(dateneinlesen.get(a).getErstpruefer())) {
                        Rueckgabe.add(a);
                        TextView textt = (TextView) v.findViewById(R.id.txtmessage);

                    }
                }
                //txtview.setText(Prof.get(Prof.size()-1).toString() + dateneinlesen.profname[i].toString());
            }
            //... your stuf
        });




        spStudiengang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RueckgabeStudiengang.clear();
                Studiengang.add(parent.getItemAtPosition(position).toString()); //this is your selected item
                int i;
                String a;
                for (i = 0; i < (dateneinlesen.size()); i++) {
                    if (Studiengang.get(Studiengang.size() - 1).toString().equals("Alle Module")) {
                        RueckgabeStudiengang.add(i);
                    } else {
                        if (Studiengang.get(Studiengang.size() - 1).toString().equals(dateneinlesen.get(i).getModul().toString())) {
                            RueckgabeStudiengang.add(i);
                           // database.userDao().update(Tabellenrueckgabe());

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
                    for (a = 0; a < (dateneinlesen.size()); a++) {
                        Rueckgabe.add(a);
                    }
                }

                database.userDao().suchezuruecksetzen(false);
                List<User> userdaten = AppDatabase.getAppDatabase(v.getContext()).userDao().getAll(validation);
                for (int i =0; i< Tabellenrueckgabe().size();i++) {
                   // Toast.makeText(getContext(),Tabellenrueckgabe().get(i), Toast.LENGTH_SHORT).show();
                    database.userDao().update2(true,Integer.valueOf(userdaten.get(Integer.valueOf(Tabellenrueckgabe().get(i))).getID()));
                }

                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_placeholder, new TerminefragmentSuche());
                ft.commit();

            }
        });

        return v;
    }
        public List<String> Tabellenrueckgabe () {
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

