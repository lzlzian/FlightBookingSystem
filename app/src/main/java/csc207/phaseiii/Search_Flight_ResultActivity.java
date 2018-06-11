package csc207.phaseiii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;

import backend.Administrator;
import backend.Flight;
import backend.Itinerary;
import managing.FlightManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/** A class representation of the search flight result activity. */
public class Search_Flight_ResultActivity extends AppCompatActivity {

    /** The user's email */
    String email;
    /** The string that determines if the user is an admin or client */
    String check;
    /** The position of the flights in the list */
    int itemPosition;
    /** The administrator object */
    Administrator admin;
    /** ArrayList of all the flight itineraries */
    ArrayList<Itinerary> itineraries;
    /** The flight manager object */
    FlightManager fm = new FlightManager();
    ListView listView ;

    ArrayAdapter<String> adapter;

    /**
     * Calls when the activity is first created.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__flight__result);
        Intent intent = getIntent();
        this.email = (String) intent.getSerializableExtra(MainActivity.USER);
        this.check = (String) intent.getSerializableExtra(MainActivity.CHECK_ADMIN);

        listView = (ListView) findViewById(R.id.list_itinerary_flight);
        itineraries= (ArrayList<Itinerary>)intent.getSerializableExtra
                (Search_FlightActivity.SEARCH_FLIGHT_RESULT);

        String[] values = new String[itineraries.size()];
        for(int i = 0; i<itineraries.size(); i++){
            values[i] = itineraries.get(i).itinerary.get(0).toString();

        }


        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        if(check.equals("true")){
            listView.setOnItemClickListener(new OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    itemPosition     = position;

                    // ListView Clicked item value
                    String  itemValue    = (String) listView.getItemAtPosition(position);

                    // Show Alert

                    new AlertDialog.Builder(Search_Flight_ResultActivity.this )
                            .setTitle( "EDIT Flight" )
                            .setMessage( "Do you want edit this flight:"+"\n" + itemValue)

                            .setPositiveButton( "CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d( "AlertDialog", "cancel" );
                                }
                            } )
                            .setNegativeButton( "EDIT", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d( "AlertDialog", "edit" );
                                    admin = MainActivity.am.getAdmin(email);
                                    Flight flight = itineraries.get(itemPosition).itinerary.get(0);
                                    Intent intent = new Intent(Search_Flight_ResultActivity.this,
                                            Edit_FlightActivity.class);
                                    intent.putExtra(View_FlightListActivity.EDIT_FLIGHT, flight);
                                    intent.putExtra(MainActivity.USER, admin);
                                    intent.putExtra(MainActivity.CHECK_ADMIN, check);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            });
        }
    }

    /**
     * Sorts the flights by ascending order of time.
     * Print out the list in a proper format and user
     * Friendly display on the app page
     * @param v the view used display images and text for the app
     */
    public void sortByTime(View v){


        listView = (ListView) findViewById(R.id.list_itinerary_flight);
        fm.sortByTime(itineraries);
        String[] values = new String[itineraries.size()];
        for(int i = 0; i<itineraries.size(); i++){
            values[i] = itineraries.get(i).itinerary.get(0).toString();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

    }

    /**
     * Sorts the flights by ascending order of cost of flight.
     * Print out the list in proper format and in a user
     * Friendly display on the app page
     * @param v the view used to display the app
     */
    public void sortByCost(View v){

        listView = (ListView) findViewById(R.id.list_itinerary_flight);
        fm.sortByCost(itineraries);
        String[] values = new String[itineraries.size()];
        for(int i = 0; i<itineraries.size(); i++){
            values[i] = itineraries.get(i).itinerary.get(0).toString();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

    }

    /**
     * Take the user to their respective home page
     * If the user if an admin then it goes to the admin
     * Main menu and goes to the client main menu if
     * It can tell it's a client
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
