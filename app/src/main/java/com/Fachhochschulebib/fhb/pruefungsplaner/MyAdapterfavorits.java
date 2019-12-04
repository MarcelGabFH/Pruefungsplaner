package com.Fachhochschulebib.fhb.pruefungsplaner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.Pruefplan;

import java.util.List;

//////////////////////////////
// MyAdapterfavoriten für Recycleview
//
//
//
// autor:
// inhalt:Anzeigen der favorisierten prüfungen in einzelnen tabellen.
// zugriffsdatum: 02.05.19
//
//
//
//
//
//
//////////////////////////////


public class MyAdapterfavorits extends RecyclerView.Adapter<MyAdapterfavorits.ViewHolder> {
    private List<String> moduleUndStudiengangList;
    private List<String> PrueferUndSemesterList;
    private List<String> ppid;
    private List<String> datum;
    private String studiengang;
    private String name;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterfavorits(List<String> moduleList, List<String> studiengangList, List<String> datumList, List<String> pruefplanidList) {
        moduleUndStudiengangList = moduleList;
        datum = datumList;
        PrueferUndSemesterList = studiengangList;
        ppid = pruefplanidList;

    }

    public void add(int position, String item, String studiengang) {
        moduleUndStudiengangList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        moduleUndStudiengangList.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterfavorits.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.favoriten, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        name = moduleUndStudiengangList.get(holder.getAdapterPosition());
        holder.txtHeader.setText(name);
        //Prüfitem von der Favoritenliste löschen
        holder.ivicon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase datenbank =  AppDatabase.getAppDatabase(v.getContext());
                List<Pruefplan> pruefplan = datenbank.userDao().getAll2();
                //second parameter is necessary ie.,Value to return if this preference does not exist.
                for (int i = 0; i < pruefplan.size();i++){
                    if(pruefplan.get(i).getFavorit()){
                        if(pruefplan.get(i).getID().equals(ppid.get(position))){
                            datenbank.userDao().update(false,Integer.valueOf(ppid.get(position)));
                            remove(holder.getAdapterPosition());

                        }
                    }
                }
            }
        });

        holder.txtFooter.setText("Prüfer: " + PrueferUndSemesterList.get(position).toString());
        name = moduleUndStudiengangList.get(position);
        String[] modulname = name.split(" ");
        studiengang = "";
        int b;
        for (b = 0; b < (modulname.length - 1); b++) {
            studiengang = (studiengang + " " + modulname[b]);

        }

        //darstellen der Informationen für das Prüfitem
        String[] splitDatumUndUhrzeit = datum.get(position).split(" ");
        String[] splitTagMonatJahr = splitDatumUndUhrzeit[0].split("-");
        holder.txtthirdline.setText("Uhrzeit: " + splitDatumUndUhrzeit[1].substring(0, 5).toString() + " datum: " + splitTagMonatJahr[2].toString() + "." + splitTagMonatJahr[1].toString() + "." + splitTagMonatJahr[0].toString());
        final String[] splitPrueferUndSemester = PrueferUndSemesterList.get(position).split(" ");
        holder.txtFooter.setText("Prüfer: " + splitPrueferUndSemester[0] + ", " + splitPrueferUndSemester[1] + "  Semester: " + splitPrueferUndSemester[2]);


        //darstellen der Weiteren Informationen
        holder.layout2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String modulUndStudiengang = moduleUndStudiengangList.get(position);
                String[] modulname = modulUndStudiengang.split(" ");
                studiengang = "";
                int b;
                for (b = 0; b < (modulname.length - 1); b++) {
                    studiengang = (studiengang + " " + modulname[b]);

                }
                String[] splittDatumundUhrzeit = datum.get(position).split(" ");
                String[] splitMonatJahrTag = splittDatumundUhrzeit[0].split("-");
                holder.txtthirdline.setText("Uhrzeit: " + splittDatumundUhrzeit[1].substring(0, 5).toString());
                final String[] splittPrueferUndSemster = PrueferUndSemesterList.get(position).split(" ");
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(v.getContext());
                alertdialog.setMessage("Informationen zur Prüfung \n \n studiengang: " + modulname[modulname.length - 1] + "\n Modul: " + studiengang + "\n Erstprüfer: " + splittPrueferUndSemster[0] + " \n Zweitprüfer: " + splittPrueferUndSemster[1] + "\n datum: " + splitMonatJahrTag[2].toString() + "." + splitMonatJahrTag[1].toString() + "." + splitMonatJahrTag[0].toString() + " \n Uhrzeit: " + splittDatumundUhrzeit[1].substring(0, 5).toString() + " \n \n \n \n \n \n ");
                alertdialog.setCancelable(true);
                alertdialog.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = alertdialog.create();
                alert11.show();


                holder.txtthirdline.setText("Uhrzeit: " + splittDatumundUhrzeit[1].substring(0, 5).toString() + " datum: " + splitMonatJahrTag[2].toString() + "." + splitMonatJahrTag[1].toString() + "." + splitMonatJahrTag[0].toString());

                holder.txtFooter.setText("Prüfer: " + splittPrueferUndSemster[0] + ", " + splittPrueferUndSemster[1] + "  Semester: " + splittPrueferUndSemster[2]);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return moduleUndStudiengangList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public Integer zahl1;
        public TextView txtthirdline;
        public ImageView ivicon;
        public LinearLayout layout2;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            ivicon = (ImageView) v.findViewById(R.id.icon);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            txtthirdline = (TextView) v.findViewById(R.id.thirdLine);
            layout2 = (LinearLayout) v.findViewById(R.id.linearLayout);
            //button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

}