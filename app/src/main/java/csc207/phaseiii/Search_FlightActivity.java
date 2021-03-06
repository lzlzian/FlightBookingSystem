package csc207.phaseiii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import java.io.FileNotFoundException;


/** A class representation of the search flight result activity. */
public class Search_FlightActivity extends AppCompatActivity {


    public static final String SEARCH_FLIGHT_RESULT = "resultKey";

    /** The date picker object used to search flights */
    DatePicker datePicker;
    /** The user's email and log in */
    String email;
    /** The string to check whether or not a user is a client or an admin */
    String check;
    /** The year of the flight */
    int year;
    /** The month of the flight */
    int month;
    /** The day of the flight */
    int day;
    /** The whole date of the flight as a String */
    String date;


    /**
     * Calls when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__flight);
        Intent intent = getIntent();
        this.email = (String) intent.getSerializableExtra(MainActivity.USER);
        this.check = (String) intent.getSerializableExtra(MainActivity.CHECK_ADMIN);
    }

    /**
     * Searches the flight from the origin and destination.
     * Switch the activity to show all flight possibilities
     * Based off of the information given and display
     * The flight options on the screen for the user
     * @param view
     * @throws FileNotFoundException an exception discarded if the file is not found.
     */
    public void searchFlights(View view) throws FileNotFoundException{
        Intent intent = new Intent(this, Search_Flight_ResultActivity.class);
        // Converts date into a string.
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        year = datePicker.getYear();
        month = datePicker.getMonth()+1;
        day = datePicker.getDayOfMonth();
        date = String.format("%04d-%02d-%02d", year, month, day);
        System.out.println(date);

        // Gets the origin from the 1st EditText field.
        EditText Origin = (EditText) findViewById(R.id.originInput);
        String origin = Origin.getText().toString();

        // Gets the destination from the 2st EditText field.
        EditText Destination = (EditText) findViewById(R.id.destinationInput);
        String destination = Destination.getText().toString();
        intent.putExtra(SEARCH_FLIGHT_RESULT,
                MainActivity.fm.getFlightItinerary(date, origin, destination));
        intent.putExtra(MainActivity.USER, email);
        intent.putExtra(MainActivity.CHECK_ADMIN, check);
        System.out.println(MainActivity.fm.getFlightItinerary(date, origin, destination));
        startActivity(intent);

    }

}
