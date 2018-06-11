package csc207.phaseiii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import java.io.FileNotFoundException;

/** A class representation of the search itinerary activity. */
public class Search_ItineraryActivity extends AppCompatActivity {

    public static final String SEARCH_FLIGHT_RESULT = "resultKey";
    /** The date picker object that helps
     * The user select what date to start  from
     */
    DatePicker datePicker;

    /** The year of the flight */
    int year;
    /** The month of the flight */
    int month;
    /** The day of the flight */
    int day;
    /** The User's email */
    String email;
    /** The string to check whether or not */
    String check;
    /** The string date of the flight */
    String date;

    /**
     * Calls when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__itinerary);
        Intent intent = getIntent();
        this.email = (String) intent.getSerializableExtra(MainActivity.USER);
        this.check = (String) intent.getSerializableExtra(MainActivity.CHECK_ADMIN);

    }

    /**
     * Searches the itineraries from the origin and destination.
     * The user selects a date with a datePicker in a user friendly
     * Way from a calendar they can click on. They then type in a origin
     * And destination and itineraries are listed
     * @param view
     * @throws FileNotFoundException an exception discarded if the file is not found.
     */
    public void searchItinerary(View view) throws FileNotFoundException{
        Intent intent = new Intent(this, Search_Itinerary_ResultActivity.class);
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
        // Gets the destination from the 2nd EditText field.
        EditText Destination = (EditText) findViewById(R.id.destinationInput);
        String destination = Destination.getText().toString();
        intent.putExtra(SEARCH_FLIGHT_RESULT,
                MainActivity.fm.getItineraries(date, origin, destination));
        intent.putExtra(MainActivity.USER, email);
        intent.putExtra(MainActivity.CHECK_ADMIN, check);
        startActivity(intent);
    }
}
