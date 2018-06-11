package csc207.phaseiii;

import java.io.FileNotFoundException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import backend.Administrator;
import android.content.Intent;
import android.widget.EditText;

/** A class representation of the edit given flight activity. */
public class Edit_Given_FlightActivity extends AppCompatActivity {

    /** The email of the admin */
    String email;
    /** The admin object */
    Administrator admin;

    /**
     * Calls when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__given__flight);
        Intent intent = getIntent();
        this.email = (String) intent.getSerializableExtra(MainActivity.USER);
        this.admin = MainActivity.am.getAdmin(email);

    }

    /**
     * Searches the current available flights.
     * @param view
     * @throws FileNotFoundException an exception discarded if the file is not found.
     */
    public void searchFlights(View view) throws FileNotFoundException{
        Intent intent = new Intent(this, Edit_FlightActivity.class);
        EditText flightNum = (EditText) findViewById(R.id.editFlightNum);
        String number = flightNum.getText().toString();
        intent.putExtra(View_FlightListActivity.EDIT_FLIGHT,
                MainActivity.fm.getFlight(number));
        intent.putExtra(View_FlightListActivity.EDIT_FLIGHT,
                MainActivity.fm.getFlight(number));
        intent.putExtra(MainActivity.USER,admin);

        startActivity(intent);

    }
    /*
    * Takes the admin home to the admin home menu
    * @param view
    */
    public void home(View view){
        // Let the intent know that this is an admin
        Intent intent = new Intent(this, Menu_Admin.class);
        intent.putExtra(MainActivity.USER, this.email);
        intent.putExtra(MainActivity.CHECK_ADMIN, "true");
        startActivity(intent);
    }
}
