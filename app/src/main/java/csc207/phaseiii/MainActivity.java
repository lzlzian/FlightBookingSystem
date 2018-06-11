package csc207.phaseiii;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import backend.Administrator;
import backend.Client;
import managing.AdminManager;
import managing.BookManager;
import managing.ClientManager;
import managing.FlightManager;
import managing.PasswordManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


/** A class representation of the MainActivity. */
public class MainActivity extends AppCompatActivity {

    //Declares all the constants to be used in this activity.
    public static final String USERDATADIR = "data";
    public static final String USER = "email";
    public static final String CHECK_ADMIN = "check";
    public static final String Password = "passwords.txt";
    public static final String FlightFileString = "Flights.csv";
    public static final String ClientFileString = "Clients.csv";
    public static final String AdminFileString = "Admins.csv";
    public static final String FlightSerial = "FlightSerial.csv";
    public static final String ClientSerial = "ClientSerial.csv";
    public static final String AdminSerial = "AdminSerial.csv";
    public static final String BookSerial = "BookSerial.csv";


    public static Administrator admin;
    public static Client client;
    public static ClientManager cm;
    public static AdminManager am;
    public static FlightManager fm;
    public static PasswordManager pm;
    public static BookManager bm;

    public static File userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userdata = this.getApplicationContext().getDir(USERDATADIR, MODE_PRIVATE);

        // Initializes managers.
        fm = new FlightManager();
        cm = new ClientManager();
        am = new AdminManager();
        pm = new PasswordManager();
        bm = new BookManager();

        // Reads all the files from the managers.
        try {
            this.readFileAdmin();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.readFileClient();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.readFileFlight();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.readFilePassword();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.readFileBook();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Validates whether the user is a valid client or a valid administrator.
     * If tests pass as a client, then the user is taken to the Client Menu.
     * If tests pass as an administrator, then the user is taken to the Administrator Menu.
     * Otherwise, presents an error.
     * @param view
     * @throws ClassNotFoundException an exception discarded if the class is not found.
     * @throws IOException an exception discarded if an error has occurred.
     */
    public void login(View view) throws ClassNotFoundException, IOException{

        if(checkClient() != null){
            Intent intent = new Intent(this, Menu_Client.class);
            intent.putExtra(USER, checkClient().email);
            intent.putExtra(CHECK_ADMIN, "false");
            startActivity(intent);
        }


        else if (checkAdmin() != null){

            Intent intent = new Intent(this, Menu_Admin.class);
            intent.putExtra(USER, checkAdmin().email);
            intent.putExtra(CHECK_ADMIN, "true");
            startActivity(intent);
        }
        else
        {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle( "ERROR" )
                    .setMessage( "Your username or password is wrong")
                    .setNegativeButton( "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d( "AlertDialog", "Negative" );
                        }
                    } )
                    .show();
        }
    }


    /**
     * Checks whether or not the client is valid
     * @return null
     * @throws FileNotFoundException an exception discarded if the file is not found.
     */
    public Client checkClient() throws FileNotFoundException{


        EditText usernameText = (EditText) findViewById(R.id.editUsername);
        String username = usernameText.getText().toString();

        EditText passwordText =	(EditText) findViewById(R.id.editPassword);
        String password = passwordText.getText().toString();
        if(pm.checkClient(cm, username, password)){
            return cm.getClient(username);
        }
        return null;
    }


    /**
     * Checks whether or not the admin is valid
     * @return null
     * @throws FileNotFoundException an exception discarded if the file is not found.
     */
    public Administrator checkAdmin() throws FileNotFoundException{


        EditText usernameText = (EditText) findViewById(R.id.editUsername);
        String username = usernameText.getText().toString();

        EditText passwordText =	(EditText) findViewById(R.id.editPassword);
        String password = passwordText.getText().toString();
        if(pm.checkAdmin(am, username, password)){
            return am.getAdmin(username);
        }

        return null;
    }


    /**
     * Reads the client file.
     * @throws ClassNotFoundException an exception discarded if the class is not found
     * @throws IOException an exception discarded if an error has occurred.
     */
    public void readFileClient() throws ClassNotFoundException, IOException{
        File userdata = this.getApplicationContext().getDir(USERDATADIR,
                MODE_PRIVATE);
        File clientSerialFile = new File(userdata, ClientSerial);
        File clientFile = new File(userdata, ClientFileString);

        if (clientSerialFile.exists()) {
            cm.readFromFile(clientSerialFile);
        } else {
            cm.readFromCSVFile(clientFile);
        }
    }


    /**
     * Reads the admin file.
     * @throws ClassNotFoundException an exception discarded if the class is not found
     * @throws IOException an exception discarded if an error has occurred.
     */
    public void readFileAdmin() throws ClassNotFoundException, IOException{
        File userdata = this.getApplicationContext().getDir(USERDATADIR,
                MODE_PRIVATE);
        File adminSerialFile = new File(userdata, AdminSerial);
        File adminFile = new File(userdata, AdminFileString);

        if (adminSerialFile.exists()) {
            am.readFromFile(adminSerialFile);
        } else {
            am.readFromCSVFile(adminFile);
        }
    }


    /**
     * Reads the flight file.
     * @throws ClassNotFoundException an exception discarded if the class is not found
     * @throws IOException an exception discarded if an error has occurred.
     */
    public void readFileFlight() throws ClassNotFoundException, IOException{
        File userdata = this.getApplicationContext().getDir(USERDATADIR,
                MODE_PRIVATE);
        File flightSerialFile = new File(userdata, FlightSerial);
        File flightFile = new File(userdata, FlightFileString);

        if (flightSerialFile.exists()) {
            fm.readFromFile(flightSerialFile);
        } else {
            fm.readFromCSVFile(flightFile);
        }
    }


    /**
     * Reads the password file.
     * @throws ClassNotFoundException an exception discarded if the class is not found
     * @throws IOException an exception discarded if an error has occurred.
     */
    public void readFilePassword() throws ClassNotFoundException, IOException{
        File userdata = this.getApplicationContext().getDir(USERDATADIR,
                MODE_PRIVATE);
        File passwordFile = new File(userdata, Password);

        pm.readFromCSVFile(passwordFile);
    }

    /**
     * Reads the book file.
     * @throws ClassNotFoundException an exception discarded if the class is not found
     * @throws IOException an exception discarded if an error has occurred.
     */
    public void readFileBook() throws ClassNotFoundException, IOException{
        File userdata = this.getApplicationContext().getDir(USERDATADIR,
                MODE_PRIVATE);
        File bookSerialFile = new File(userdata, BookSerial);

        if (bookSerialFile.exists()) {
            bm.readFromFile(bookSerialFile);
        }
    }


    /**
     * Saves the client file.
     * @throws ClassNotFoundException an exception discarded if the class is not found
     * @throws IOException an exception discarded if an error has occurred.
     */
    public static void saveFileClient() throws ClassNotFoundException, IOException{
        File clientSerialFile = new File(userdata, ClientSerial);

        if (clientSerialFile.exists()) {
            cm.saveToFile(clientSerialFile);
        } else {
            clientSerialFile.createNewFile();
            cm.saveToFile(clientSerialFile);
        }
    }


    /**
     * Saves the admin file.
     * @throws ClassNotFoundException an exception discarded if the class is not found
     * @throws IOException an exception discarded if an error has occurred.
     */
    public static void saveFileAdmin() throws ClassNotFoundException, IOException{
        File adminSerialFile = new File(userdata, AdminSerial);

        if (adminSerialFile.exists()) {
            am.saveToFile(adminSerialFile);
        } else {
            adminSerialFile.createNewFile();
            am.saveToFile(adminSerialFile);
        }
    }


    /**
     * Saves the flight file.
     * @throws ClassNotFoundException an exception discarded if the class is not found
     * @throws IOException an exception discarded if an error has occurred.
     */
    public static void saveFileFlight() throws ClassNotFoundException, IOException{
        File flightSerialFile = new File(userdata, FlightSerial);

        if (flightSerialFile.exists()) {
            fm.saveToFile(flightSerialFile);
        } else {
            flightSerialFile .createNewFile();
            fm.saveToFile(flightSerialFile);
        }
    }

    /**
     * Saves the flight file.
     * @throws ClassNotFoundException an exception discarded if the class is not found
     * @throws IOException an exception discarded if an error has occurred.
     */
    public static void saveFileBook() throws ClassNotFoundException, IOException{
        File bookSerialFile = new File(userdata, BookSerial);

        if (bookSerialFile.exists()) {
            bm.saveToFile(bookSerialFile);
        } else {
            bookSerialFile .createNewFile();
            bm.saveToFile(bookSerialFile);
        }
    }
}