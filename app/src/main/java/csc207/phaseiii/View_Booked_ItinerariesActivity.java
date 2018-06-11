package csc207.phaseiii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.io.IOException;
import java.util.ArrayList;

import backend.Administrator;
import backend.Client;
import backend.Flight;
import backend.Itinerary;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/** A class representation of the upload flight list activity. */
public class View_Booked_ItinerariesActivity extends AppCompatActivity {
    /** The user's email/username */
    private String email;
    /** The client object */
    private Client client;
    /** The administrator object */
    private Administrator admin;
    /** The string to check whether or not the user is an admin or a client */
    private String check;
    /** The position of each itinerary booked */
    private int itemPosition;
    ListView listView ;
    /** The arrayList of Itineraries booked */
    private ArrayList<Itinerary> booked;

    /**
     * Calls when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__booked__itineraries);
        listView = (ListView) findViewById(R.id.booked_itineraries);
        Intent intent = getIntent();

        this.email = (String) intent.getSerializableExtra(MainActivity.USER);
        this.check = (String) intent.getSerializableExtra(MainActivity.CHECK_ADMIN);
        if(check.equals("true")){
            admin = MainActivity.am.getAdmin(email);
            booked = MainActivity.bm.checkBookedItinerary(admin);

        }
        else{
            client = MainActivity.cm.getClient(this.email);
            booked = MainActivity.bm.checkBookedItinerary(client);
        }


        String[] values = new String[booked.size()];
        for(int i = 0; i<booked.size(); i++){
            values[i] = booked.get(i).toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                itemPosition  = position;

                // ListView Clicked item value
                //String  itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                new AlertDialog.Builder(View_Booked_ItinerariesActivity.this )
                        .setTitle( "REMOVE BOOKED FLIGHT" )
                        .setMessage( "Do you want remove Itinerary from your Itinerary list")

                        .setPositiveButton( "NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d( "AlertDialog", "cancel" );
                            }
                        } )
                        .setNegativeButton( "YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d( "AlertDialog", "edit" );
                                if(check.equals("true")){
                                    for(Flight flight : booked.get(itemPosition)
                                            .itinerary){
                                        MainActivity.fm.getFlight(flight.flightNum).cancelBook();
                                    }
                                    MainActivity.bm.cancelItinerary(admin, itemPosition);
                                    try {
                                        MainActivity.saveFileBook();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        MainActivity.saveFileFlight();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    admin = MainActivity.am.getAdmin(email);
                                    booked = MainActivity.bm.checkBookedItinerary(admin);
                                }
                                else{

                                    for(Flight flight : booked.get(itemPosition)
                                            .itinerary){
                                        MainActivity.fm.getFlight(flight.flightNum).cancelBook();
                                    }
                                    MainActivity.bm.cancelItinerary(client, itemPosition);


                                    try {
                                        MainActivity.saveFileBook();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        MainActivity.saveFileFlight();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    client = MainActivity.cm.getClient(email);
                                    booked = MainActivity.bm.checkBookedItinerary(client);

                                }




                                String[] values = new String[booked.size()];
                                for(int i = 0; i<booked.size(); i++){
                                    values[i] = booked.get(i).toString();
                                }

                                listView = (ListView) findViewById(R.id.booked_itineraries);
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                        View_Booked_ItinerariesActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        android.R.id.text1, values);
                                // Assign adapter to ListView
                                listView.setAdapter(adapter);
                            }
                        })

                        .show();

            }

        });
    }


    /**
     * Changes the activity to back to the corresponding
     * Activity based on whether the user was an admin
     * Or a client
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
            startActivity(intent);}
    }

    /**
     * If the user want to keep booking they use
     * This option which keeps them on the same activity
     * @param view
     */
    public void keepBooking(View view){

        Intent intent = new Intent(this, Search_ItineraryActivity.class);
        intent.putExtra(MainActivity.USER, this.email);
        intent.putExtra(MainActivity.CHECK_ADMIN, check);
        startActivity(intent);

    }

}