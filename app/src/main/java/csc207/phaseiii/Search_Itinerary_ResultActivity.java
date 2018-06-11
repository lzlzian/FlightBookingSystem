package csc207.phaseiii;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import backend.Administrator;
import backend.Client;
import backend.Flight;
import backend.Itinerary;
import managing.FlightManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/** A class representation of the search flight result activity. */
public class Search_Itinerary_ResultActivity extends AppCompatActivity {

    /** An arrayList of itineraries */
    ArrayList<Itinerary> itineraries;
    /** The client's email and user name */
    String email;
    /** The client object */
    Client client;
    /** The administrator object */
    Administrator admin;
    /** The string determining whether or not the user is an admin */
    String check;
    ListView listView ;
    ArrayAdapter<String> adapter;
    /** The flight manager object */
    FlightManager fm = new FlightManager();
    int itemPosition;

    /**
     * Calls when the activity is first created.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__itinerary__result);
        Intent intent = getIntent();
        this.email = (String) intent.getSerializableExtra(MainActivity.USER);
        this.check = (String) intent.getSerializableExtra(MainActivity.CHECK_ADMIN);
        if(check.equals("true")){
            admin = MainActivity.am.getAdmin(email);
        }
        else{
            client = MainActivity.cm.getClient(email);
        }
        listView = (ListView) findViewById(R.id.list_itinerary);
        itineraries= (ArrayList<Itinerary>)
                intent.getSerializableExtra(
                        Search_FlightActivity.SEARCH_FLIGHT_RESULT);
        String[] values = new String[itineraries.size()];
        for(int i = 0; i<itineraries.size(); i++){
            values[i] = itineraries.get(i).toString();
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                itemPosition = position;
                // ListView Clicked item value
                String  itemValue = (String) listView.getItemAtPosition(position);
                if(check.equals("true")){
                    if(MainActivity.bm.checkBooked(admin, itemValue)){
                        new AlertDialog.Builder(Search_Itinerary_ResultActivity.this)
                                .setTitle( "BOOK ITINERARY" )
                                .setMessage( "You already booked this itinerary: "+"\n" + itemValue)
                                .setNegativeButton( "back", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d( "AlertDialog", "Negative" );
                                    }
                                } )
                                .show();
                    }
                    else{
                        new AlertDialog.Builder(Search_Itinerary_ResultActivity.this )
                                .setTitle( "BOOK ITINERARY" )
                                .setMessage( "Do you want book this itinerary:"+"\n" + itemValue)
                                .setPositiveButton( "YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("AlertDialog", "Positive");
                                        MainActivity.bm.bookItinerary(admin, itineraries.get(itemPosition));
                                        //save the client info
                                        try {
                                            MainActivity.saveFileBook();
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        // modify the fm
                                        for(Flight flight:itineraries.get(itemPosition).itinerary){
                                            MainActivity.fm.getFlight(flight.flightNum).bookFlight();
                                        }
                                        try {
                                            MainActivity.saveFileFlight();
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Intent intent = new Intent(
                                                Search_Itinerary_ResultActivity.this,
                                                View_Booked_ItinerariesActivity.class);
                                        intent.putExtra(MainActivity.USER, email);
                                        intent.putExtra(MainActivity.CHECK_ADMIN, check);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton( "NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d( "AlertDialog", "Negative" );
                                    }
                                } )
                                .show();
                    }

                }
                else{
                    if(MainActivity.bm.checkBooked(client, itemValue)){
                        new AlertDialog.Builder(Search_Itinerary_ResultActivity.this)
                                .setTitle( "BOOK ITINERARY" )
                                .setMessage( "You already booked this itinerary: "+"\n" + itemValue)
                                .setNegativeButton( "back", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d( "AlertDialog", "Negative" );
                                    }
                                } )
                                .show();
                    }
                    // Client
                    else{
                        new AlertDialog.Builder(Search_Itinerary_ResultActivity.this )
                                .setTitle( "BOOK ITINERARY" )
                                .setMessage( "Do you want book this itinerary:"+"\n" + itemValue)
                                .setPositiveButton( "YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d( "AlertDialog", "Positive" );
                                        // if client able to book
                                        System.out.println(itemPosition);
                                        MainActivity.bm.bookItinerary(client, itineraries.get(itemPosition));
                                        //save the client info
                                        try {
                                            MainActivity.saveFileBook();
                                        } catch (ClassNotFoundException e) {

                                            e.printStackTrace();
                                        } catch (IOException e) {

                                            e.printStackTrace();
                                        }
                                        // modify the fm
                                        for(Flight flight:itineraries.get(itemPosition).itinerary){
                                            MainActivity.fm.getFlight(flight.flightNum).bookFlight();
                                        }
                                        try {
                                            MainActivity.saveFileFlight();
                                        } catch (ClassNotFoundException e) {

                                            e.printStackTrace();
                                        } catch (IOException e) {

                                            e.printStackTrace();
                                        }
                                        Intent intent = new Intent(
                                                Search_Itinerary_ResultActivity.this,
                                                View_Booked_ItinerariesActivity.class);
                                        intent.putExtra(MainActivity.USER, email);
                                        intent.putExtra(MainActivity.CHECK_ADMIN, check);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton( "NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d( "AlertDialog", "Negative" );
                                    }
                                } )
                                .show();
                    }
                }
            }

        });
    }


    /**
     * Sorts the itineraries in ascending order of time.
     * Displays the list of itineraries in a user
     * Friendly display on the app
     * @param view
     */
    public void sortByTime(View view){
        listView = (ListView) findViewById(R.id.list_itinerary);
        fm.sortByTime(itineraries);
        String[] values = new String[itineraries.size()];
        for(int i = 0; i<itineraries.size(); i++){
            values[i] = itineraries.get(i).toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }


    /**
     * Sorts the itineraries in ascending order of cost.
     * Displays the list of itineraries in a user
     * Friendly display on the app
     * @param view
     */
    public void sortByCost(View view){
        listView = (ListView) findViewById(R.id.list_itinerary);
        fm.sortByTime(itineraries);
        String[] values = new String[itineraries.size()];
        for(int i = 0; i<itineraries.size(); i++){
            values[i] = itineraries.get(i).toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }


    /**
     * Books the specific itinerary.
     * And adds that to the user's data
     * @param view
     */
    public void book(View view){
        Intent intent = new Intent(this, View_Booked_ItinerariesActivity.class);
        intent.putExtra(MainActivity.USER, email);
        intent.putExtra(MainActivity.CHECK_ADMIN, check);
        startActivity(intent);
    }

    /** The home button for the user
     * It navigates to the admin menu
     * If the user is an admin and it
     * Navigates to the client menu if
     * The user is a client
     * @param view
     */
    public void home(View view){
        if(check.equals("true")){
            Intent intent = new Intent(this, Menu_Admin.class);
            intent.putExtra(MainActivity.USER, this.email);
            intent.putExtra(MainActivity.CHECK_ADMIN, check);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, Menu_Client.class);
            intent.putExtra(MainActivity.USER, this.email);
            intent.putExtra(MainActivity.CHECK_ADMIN, check);
            startActivity(intent);
        }
    }
}