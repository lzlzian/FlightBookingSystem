package csc207.phaseiii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.io.File;
import java.io.IOException;
import managing.ClientManager;
import managing.FlightManager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/** A class representation of the upload flight list activity. */
public class Upload_Flight_ListActivity extends AppCompatActivity {
    /** The User's email/username */
    private String email;
    /** The string to check whether or not the user is a client or an admin */
    private String check;

    /**
     * Calls when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__flight__list);
        Intent intent = getIntent();
        this.email = (String) intent.getSerializableExtra(MainActivity.USER);
        this.check = (String) intent.getSerializableExtra(MainActivity.CHECK_ADMIN);
    }

    /**
     * Uploads a flight into the flights file.
     * The Admin can upload Client info and Flight info
     * @param view
     * @throws ClassNotFoundException an exception discarded if the class is not found.
     * @throws IOException an exception discarded if an error has occurred.
     */
    @SuppressLint("NewApi")
    public void upload(View view) throws ClassNotFoundException, IOException{
        RadioGroup genderField = (RadioGroup) findViewById(R.id.file_field);
        int fileChoice = genderField.getCheckedRadioButtonId();
        RadioButton genderButton = (RadioButton) findViewById(fileChoice);
        String type = (String) genderButton.getText();
        EditText fileName = (EditText) findViewById(R.id.fileName);
        String name = fileName.getText().toString();
        File userdata = this.getApplicationContext().getDir("userdata",
                MODE_PRIVATE);
        File file  = new File(userdata, name);
        if(!file.exists()||name.isEmpty()){
            new AlertDialog.Builder(Upload_Flight_ListActivity.this)
                    .setTitle( "ERROR" )
                    .setMessage( "File is not exist, make sure you put the right name")
                    .setNegativeButton( "YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d( "AlertDialog", "negative" );

                        }
                    })
                    .show();
        }
        else if(type.equals("Flight File")){
            int before = MainActivity.fm.size();
            FlightManager fm = new FlightManager();
            fm.readFromCSVFile(file);
            MainActivity.fm.uploadFlight(fm);
            int change = MainActivity.fm.size() - before;
            MainActivity.saveFileFlight();
            new AlertDialog.Builder(Upload_Flight_ListActivity.this)
                    .setTitle( "UPLOAD FLIGHT" )
                    .setMessage( "You have successful upload " + change + " flights")
                    .setPositiveButton( "YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d( "AlertDialog", "Positive" );

                        }
                    })
                    .show();

        }
        else if(type.equals("Client File")){
            int before = MainActivity.cm.size();
            ClientManager cm = new ClientManager();
            cm.readFromCSVFile(file);
            MainActivity.cm.uploadClient(cm);
            int change = MainActivity.cm.size() - before;
            MainActivity.saveFileClient();

            new AlertDialog.Builder(Upload_Flight_ListActivity.this)
                    .setTitle( "UPLOAD CLIENT" )
                    .setMessage( "You have successful uploaded " + change + " clients")
                    .setPositiveButton( "YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d( "AlertDialog", "Positive" );

                        }
                    })
                    .show();
        }
    }

    /**
     * The button to go back to the main admin menu
     * There is no option for clients because they cannot
     * Access this page
     * @param view
     */
    public void home(View view){
        Intent intent = new Intent(this, Menu_Admin.class);
        intent.putExtra(MainActivity.USER, this.email);
        intent.putExtra(MainActivity.CHECK_ADMIN, check);
        startActivity(intent);
    }
}