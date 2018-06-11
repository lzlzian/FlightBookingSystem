package csc207.phaseiii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import backend.Client;

/** A class representation of the client menu. */
public class Menu_Client extends AppCompatActivity {
    /** The Client object */
    Client client;
    /** The client's email/username */
    String email;
    /** The string to check whether or not the user is a client */
    String check;


    /**The client'
     * Calls when the activity is first created.
     * Much of the functionality is similar to the Admin
     * Menu but without a few buttons because those are
     * Admin specific
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__client);
        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra(MainActivity.USER);
        check = (String) intent.getSerializableExtra(MainActivity.CHECK_ADMIN);
    }

    /**
     * Switches to the search flight activity if the button is clicked.
     * @param view
     */
    public void searchFlights(View view){
        Intent intent = new Intent(this, Search_FlightActivity.class);
        intent.putExtra(MainActivity.USER, email);
        intent.putExtra(MainActivity.CHECK_ADMIN, check);
        startActivity(intent);
    }

    /**
     * Switches to the search itineraries activity if the button is clicked.
     * @param view
     */
    public void searchItineraries(View view){
        Intent intent = new Intent(this, Search_ItineraryActivity.class);
        intent.putExtra(MainActivity.USER, email);
        intent.putExtra(MainActivity.CHECK_ADMIN, check);
        startActivity(intent);
    }

    /**
     * Switches to the view personal information activity if the button is clicked.
     * @param view
     */
    public void viewPersonalInfo(View view){
        Intent intent = new Intent(this, View_Personal_InfoActivity.class);
        intent.putExtra(MainActivity.USER, email);
        intent.putExtra(MainActivity.CHECK_ADMIN, check);
        startActivity(intent);
    }

    /**
     * Switches to the view booked itineraries if the button is clicked.
     * @param view
     */
    public void viewBookedItineraries(View view){
        Intent intent = new Intent(this, View_Booked_ItinerariesActivity.class);
        intent.putExtra(MainActivity.USER, this.email);
        intent.putExtra(MainActivity.CHECK_ADMIN, this.check);
        startActivity(intent);
    }

    /**
     * Switches back to the main activity if the button is clicked.
     * @param view
     */
    public void logOut(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
