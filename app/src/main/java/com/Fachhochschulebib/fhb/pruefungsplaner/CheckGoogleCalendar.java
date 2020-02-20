//////////////////////////////
// CheckGoogleCalendar
//
//
//
// autor:
// inhalt: Hinzufügen, Löschen, Aktualisieren von Prüfobjekten in den Google Calendar
// zugriffsdatum: 11.12.19
//
//
//
//
//
//
//////////////////////////////



package com.Fachhochschulebib.fhb.pruefungsplaner;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.Log;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.Pruefplan;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;




public class CheckGoogleCalendar {

    private int pruefID;
    private int googleID;
    private Context context;
    private List<String>  ids = new ArrayList<String>();
    //private String[] ids = new String[100];


    public void setCtx (Context cx){
        context = cx;


    }


    //Mehtode um Prüfid und Google Kalender Id zu speichern
    public void insertCal(int pruefid, int googleid){

       //Variablen
        pruefID = pruefid;
        googleID = googleid;


        //Creating editor to store uebergebeneModule to shared preferences
        SharedPreferences mSharedPreferences = context.getSharedPreferences("GoogleID-und-PruefID13", 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        String stringids = mSharedPreferences.getString("IDs","");

        // step one : converting comma separate String to array of String
        String[] elements = null;
        try {
            elements = stringids.split("/");
        }catch (Exception e){
            Log.d("Fehler CheckGoogleCal","Fehler beim aufteilen der String elemente");
        }
        // step two : convert String array to list of String
        List<String> fixedLenghtList = Arrays.asList(elements);

       // Log.i("test", String.valueOf(elements.length));

        // step three : copy fixed list to an ArrayList
        ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);

        //step four : check size and add new element
        listOfString.add("-" + String.valueOf(pruefid) + "," + String.valueOf(googleid));

        //step fifth : change Stringarray to String type
        String idsTOstring = "/";
        for(int i = 0; i < listOfString.size();i++)
        {
            idsTOstring = idsTOstring + listOfString.get(i);
        }

        //Log.i("test", String.valueOf(idsTOstring));



        //step six : add to database

        mEditor.putString("IDs",String.valueOf(idsTOstring));
        mEditor.apply();
    }

    //Methode zum überprüfen ob der eintrag im Googlekalender vorhanden ist
    public boolean checkCal(int pruefid) {
        //Variablen
        pruefID = pruefid;

        //Creating editor to store uebergebeneModule to shared preferences
        SharedPreferences mSharedPreferences = context.getSharedPreferences("GoogleID-und-PruefID13", 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        String stringids = mSharedPreferences.getString("IDs", "");

        // step one : converting comma separate String to array of String
        String[] elements = stringids.split("-");

        // step two : convert String array to list of String
        List<String> fixedLenghtList = Arrays.asList(elements);

        // step three : copy fixed list to an ArrayList
        ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);

        //step fifth : change Stringarray to String type
        Log.i("check_Googlekalender", String.valueOf(listOfString.size()));

        String idsTOstring = null;
        for (int i = 1; i < listOfString.size(); i++) {
            // step six : split Prüfid und GoogleID
            String[] element = listOfString.get(i).split(",");
            Log.i("check_Checkbool", element[0].toString());

            if (element[0].equals(String.valueOf(pruefID))) {
                //wenn prüfid vorhanden return false
                Log.i("check_Checkbool", "Pid stimmt überein");

                return false;
            }
        }

        //wenn prüfid nicht vorhanden return true

        return true;
    }


    //alle bisherigen Google Kalender einträge löschen
    public void clearCal()
    {

        //Creating editor to store uebergebeneModule to shared preferences
        SharedPreferences mSharedPreferences = context.getSharedPreferences("GoogleID-und-PruefID13", 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        String stringids = mSharedPreferences.getString("IDs", "");

        // step one : converting comma separate String to array of String
        String[] elements = stringids.split("-");

        // step two : convert String array to list of String
        List<String> fixedLenghtList = Arrays.asList(elements);

        // step three : copy fixed list to an ArrayList
        ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);


        //hier werden die einträge aus dem google Kalender gelöscht
        for (int i = 1; i < listOfString.size(); i++) {
            // step six : split Prüfid und GoogleID
            String[] element = listOfString.get(i).split(",");
            //Google Calendar einträge löschen
            //output tag
            String DEBUG_TAG = "MyActivity";
            //element[1] enthält die googleid
            long eventID = Long.valueOf(element[1]);
            //getContentResolver wird benötigt zum zugriff auf die Kalender API
            ContentResolver cr = context.getContentResolver();
            Uri deleteUri = null;
            //delete eintrag mit eventID
            Uri baseUri;
            if (Build.VERSION.SDK_INT >= 8) {
                baseUri = Uri.parse("content://com.android.calendar/events");

            } else {
                baseUri = Uri.parse("content://calendar/events");
            }

            deleteUri = ContentUris.withAppendedId(baseUri, eventID);
            int rows = cr.delete(deleteUri, null, null);
            //outputlog for Debug
            Log.i(DEBUG_TAG, "Rows deleted: " + rows);

        }
        //step six : reset Database
        mEditor.putString("IDs","");
        mEditor.apply();
    }

    //Google Kalender Checkverbindung Methode
    public void updateCal()
    {
        //Creating editor to store uebergebeneModule to shared preferences
        SharedPreferences Googleeintrag = context.getSharedPreferences("GoogleID-und-PruefID13", 0);
        String stringids = Googleeintrag.getString("IDs", "");

        // step one : converting comma separate String to array of String
        String[] elements = stringids.split("-");

        Log.i("userID", String.valueOf(elements.length));
        // step two : convert String array to list of String
        List<String> fixedLenghtList = Arrays.asList(elements);

        // step three : copy fixed list to an ArrayList
        ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);


        //step four: Database connect
        List<Pruefplan> pruefplan = databaseConnect();

        //step fifth: Schleifen zum vergleichen
        Log.i("userID", String.valueOf(listOfString.size()));
        for (int i = 1; i < listOfString.size(); i++) {
            //Variable mit Element[0] prüfplanID und element[1] Google Calendar id
            String[] element = listOfString.get(i).split(",");
            Log.i("userID", element.toString());
            Log.i("elemnt[0}", String.valueOf(element[0]));
            for (int j = 0; j< pruefplan.size(); j++) {
                // wenn id  gleich id vom google Calendar dann get element[1] dieser id, element[1]
                // ist die GoogleCalendar Id für den gespeicherten eintrag
                Log.i("userID2", pruefplan.get(j).getID());
                if(pruefplan.get(j).getID().equals(element[0])) {
                    //output tag
                    String DEBUG_TAG = "MyActivity";
                    //eventID ist die Google calendar Id
                    long eventID = Long.valueOf(element[1]);
                    //Klasse für das updaten von werten
                    ContentResolver cr = context.getContentResolver();
                    ContentValues values = new ContentValues();
                    Uri updateUri = null;
                    //Datum und Uhrzeit aufteilen.
                    // Sieht so aus wie 22-01-2019 10:00 Uhr
                    // es wird nach dem Leerzeichen getrennt
                    //trennen von datum und Uhrzeit
                    String[] s = pruefplan.get(j).getDatum().split(" ");
                    //print Datum
                    System.out.println(s[0]);
                    //aufteilen von tag, monat und jahr.
                    //sieht aus wie 22-01-2019 aufgeteilt in ss[0] =  22 ,ss[1] = 01, ss[2] = 2019
                    String[] ss = s[0].split("-");
                    //aufteilen von der Uhrzeit Stunden der prüfung und Minuten der prüfung
                    int uhrzeit1 = Integer.valueOf(s[1].substring(0, 2));
                    int uhrzeit2 = Integer.valueOf(s[1].substring(4, 5));
                    // The new title for the updatet event
                    values.put(CalendarContract.Events.TITLE, pruefplan.get(j).getModul());
                    values.put(CalendarContract.Events.EVENT_LOCATION, "Fachhochschule BielefeldUpdate");
                    values.put(CalendarContract.Events.DESCRIPTION, "");
                    //umwandeln von Datum und uhrzeit in GregorianCalender für eine leichtere weiterverarbeitung
                    GregorianCalendar calDate = new GregorianCalendar(Integer.valueOf(ss[0]), (Integer.valueOf(ss[1]) - 1), Integer.valueOf(ss[2]), uhrzeit1, uhrzeit2);
                    values.put(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                    values.put(CalendarContract.Events.DTSTART, calDate.getTimeInMillis());
                    values.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis() + (90 * 60000));
                    //uebergebeneModule.put(CalendarContract.Events.);
                    //Checkverbindung Eintrag
                    Uri baseUri;
                    if (Build.VERSION.SDK_INT >= 8) {
                        baseUri = Uri.parse("content://com.android.calendar/events");

                    } else {
                        baseUri = Uri.parse("content://calendar/events");
                    }

                    updateUri = ContentUris.withAppendedId(baseUri, eventID);
                    //variable zum anzeigen der geänderten werte
                    int rows = cr.update(updateUri, values, null, null);

                    //testausgabe
                    Log.i(DEBUG_TAG, "Rows updated: 240 " + rows);
                }
            }
        }
    }

    public List<Pruefplan> databaseConnect(){
        AppDatabase database2 = AppDatabase.getAppDatabase(context);
        List<Pruefplan> userdaten2 = database2.userDao().getAll2();
     return(userdaten2);
    }

    @SuppressLint("InlinedApi")
    public int UpdateCalendarEntry(int entryID) {
        int iNumRowsUpdated = 0;

        Uri eventUri;
        if (android.os.Build.VERSION.SDK_INT <= 7) {
            // the old way

            eventUri = Uri.parse("content://calendar/events");
        } else {
            // the new way

            eventUri = Uri.parse("content://com.android.calendar/events");
        }

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.TITLE, "test");
        values.put(CalendarContract.Events.EVENT_LOCATION, "Chennai");

        Uri updateUri = ContentUris.withAppendedId(eventUri, entryID);
        iNumRowsUpdated = context.getContentResolver().update(updateUri, values, null,
                null);

        return iNumRowsUpdated;
    }
}
