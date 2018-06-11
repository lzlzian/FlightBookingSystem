package csc207.phaseiii;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import java.util.Map;

import backend.Administrator;
import backend.Flight;
import managing.FlightManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/** A class representation of the search flight result activity. */
public class View_FlightListActivity extends AppCompatActivity {

    public static final String EDIT_FLIGHT = "edit_flight";
    /** The administrator object */
    Administrator admin;
    /** The user's email/username */
    private String email;
    ListView listView ;
    ArrayAdapter<String> adapter;
    /** The flight manager object */
    protected FlightManager fm;
    /** The arrayList of Flights */
    protected ArrayList<Flight> flights;
    /** Th position of the Flight */
    private int itemPosition;

    /**
     * Calls when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__flight_list);
        Intent intent = getIntent();
        this.email = (String) intent.getSerializableExtra(MainActivity.USER);
        this.admin = MainActivity.am.getAdmin(email);
        listView = (ListView) findViewById(R.id.listFlights);

        fm = MainActivity.fm;

        flights = new ArrayList<>();
        for(Map.Entry<String, Flight> entry: fm.getFlights().entrySet()){
            flights.add(entry.getValue());
        }
        String[] values = new String[flights.size()];
        for(int i = 0; i< flights.size(); i++){
            values[i] = flights.get(i).toString();
        }


        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        //final Dialog dialog = new Dialog(this);
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert

                new AlertDialog.Builder(View_FlightListActivity.this )
                        .setTitle( "EDIT FLIGHT" )
                        .setMessage( "Do you want edit this flight:"+"\n" + itemValue)
                        .setPositiveButton( "YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d( "AlertDialog", "Positive" );
                                Intent intent = new Intent(View_FlightListActivity.this,
                                        Edit_FlightActivity.class);
                                intent.putExtra(EDIT_FLIGHT, flights.get(itemPosition));
                                intent.putExtra(MainActivity.CHECK_ADMIN, "true");
                                intent.putExtra(MainActivity.USER, admin);
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

        });
    }

    /**
     * The method used to go back to the
     * Home screen For the Admin
     * There is no client option because only an admin can access this page
     * @param view
     */
    public void home(View view){
        Intent intent = new Intent(this, Menu_Admin.class);
        intent.putExtra(MainActivity.USER, this.email);
        intent.putExtra(MainActivity.CHECK_ADMIN, "true");
        startActivity(intent);
    }

}
