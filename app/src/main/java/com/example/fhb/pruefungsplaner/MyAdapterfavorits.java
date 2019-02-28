package com.example.fhb.pruefungsplaner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

//////////////////////////////
// Adapter fuer Recycleview///
//////////////////////////////


public class MyAdapterfavorits extends RecyclerView.Adapter<MyAdapterfavorits.ViewHolder> {
    private List<String> values;
    private List<String> studiengang2;
    private List<String> index;
    private List<String> Datum;

    private  String studiengang;
    private  String name;

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

    public void add(int position, String item,String studiengang) {
        values.add(position, item);


        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterfavorits(List<String> myDataset, List<String> myDataset2, List<String>myDatasetDatum, List<String>index2) {
        values = myDataset;
        Datum = myDatasetDatum;
        studiengang2 = myDataset2;
        index = index2;

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
        name = values.get(position);
        holder.txtHeader.setText(name);
        holder.ivicon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.zahl1 = position;
                //holder.txtthirdline.setText(index.get(position));
                remove(holder.getAdapterPosition());
                SharedPreferences mSharedPreferences = v.getContext().getSharedPreferences("json6", 0);
                //Creating editor to store values to shared preferences
                SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                JSONArray response = new JSONArray();
                String strJson = mSharedPreferences.getString("jsondata","0");
                //second parameter is necessary ie.,Value to return if this preference does not exist.
                if (strJson != null) {
                    try {
                        response = new JSONArray(strJson);
                        JSONArray list = new JSONArray();
                        int len = response.length();
                        if (response != null) {
                            int i;
                            for ( i= 0;i < len;i++)
                            {
                                //Excluding the item at position
                                if (i != position)
                                {
                                    list.put(response.get(i));
                                }
                                else
                                {
                                    //holder.txtthirdline.setText(response.get(i).toString());
                                }
                            }
                        }
                        mEditor.clear();
                        mEditor.putString("jsondata",list.toString());
                        mEditor.apply();
                    } catch (JSONException e) {
                    }
                }
            }
        });
        holder.txtFooter.setText("Prüfer: " + studiengang2.get(position).toString());

        name = values.get(position);
        String[] modulname = name.split(" ");
        studiengang = "";
        int b;
        for (b=0;b< (modulname.length-1);b++)
        {
            studiengang = (studiengang +" "+modulname[b]);

        }

        String[] s = Datum.get(position).split(" ");
        String[] ss = s[0].split("-");
        holder.txtthirdline.setText("Uhrzeit: " + s[1].substring(0,5).toString() +" Datum: " + ss[2].toString() +"."+ ss[1].toString() +"."+  ss[0].toString());
        final String[] sa = studiengang2.get(position).split(" ");
        holder.txtFooter.setText("Prüfer: " + sa[0] +", "+ sa[1] +"  Semester: "+ sa[2]);

        holder.layout2.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String name = values.get(position);
                String[] modulname = name.split(" ");
                studiengang = "";
                int b;
                for (b=0;b< (modulname.length-1);b++)
                {
                    studiengang = (studiengang +" "+modulname[b]);

                }
                String[] s = Datum.get(position).split(" ");
                String[] ss = s[0].split("-");
                holder.txtthirdline.setText("Uhrzeit: " + s[1].substring(0,5).toString());
                final String[] sa = studiengang2.get(position).split(" ");
                String Semester =  String.valueOf(holder.txtFooter.getText());
                String Semester5 = Semester.toString();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setMessage("Informationen zur Prüfung \n \n Studiengang: "+ modulname[modulname.length-1]+ "\n Modul: "+ studiengang  + "\n Erstprüfer: " + sa[0]  +" \n Zweitprüfer: "+ sa[1] + "\n Datum: " + ss[2].toString() +"."+ ss[1].toString() +"."+  ss[0].toString() +" \n Uhrzeit: "+s[1].substring(0,5).toString()+" \n Raum: \n Prüfungsform: \n \n \n \n \n \n ");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}