package csc207.phaseiii;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import backend.Administrator;
import backend.Client;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/** A class representation of the edit client activity. */
public class Edit_ClientActivity extends AppCompatActivity {

    /** The Client's email */
    String email;
    /** The client object */
    Client client;
    /** The Administrator Object*/
    Administrator admin;
    /** A string to determine if the user is an admin*/
    String check;

    /**
     * Calls when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__client);
        Intent intent = getIntent();
        this.email = (String) intent.getSerializableExtra(MainActivity.USER);
        this.client = MainActivity.cm.getClient(email);
        check = (String) intent.getSerializableExtra(MainActivity.CHECK_ADMIN);

        if(check.equals("true")){
            this.admin = MainActivity.am.getAdmin(email);
        }
        else{
            this.client = MainActivity.cm.getClient(email);
        }
    }
    /**
     * Processes the new client information to be edited.
     * A new User object is created based off of new information
     * The old object is deleted and a new updated object takes
     * Its place.
     * @param view
     * @throws ClassNotFoundException an exception discarded if the class is not found.
     * @throws IOException an exception discarded if an error has occurred.
     */
    @SuppressLint("NewApi")
    public void submitInfo(View view) throws ClassNotFoundException, IOException {
        Intent intent = new Intent(this, View_Personal_InfoActivity.class);
        // Gets the first name from the 1st EditText field.
        EditText editFirstName = (EditText) findViewById(R.id.editFirstName);
        String firstName = editFirstName.getText().toString();

        // Gets the last name from the 2nd EditText field.
        EditText editLastName = (EditText) findViewById(R.id.editLastName);
        String lastName = editLastName.getText().toString();

        // Gets the email from the 3rd EditText field.
        EditText editEmail = (EditText) findViewById(R.id.editEmail);
        String c_email = editEmail.getText().toString();

        // Gets the address from the 4th EditText field.
        EditText editAddress = (EditText) findViewById(R.id.editAddress);
        String address = editAddress.getText().toString();

        // Gets the credit card from the 5th EditText field.
        EditText editCreditCard = (EditText) findViewById(R.id.editCreditCard);
        String creditCard = editCreditCard.getText().toString();

        // Gets the expire date from the 6th EditText field.
        EditText editExpiryDate = (EditText) findViewById(R.id.editExpiryDate);
        String expiryDate = editExpiryDate.getText().toString();
        // if the user is an admin
        if(check.equals("true")){
            // check if input is valid
            if((isValidDate(expiryDate)||expiryDate.isEmpty())
                    &&(c_email.isEmpty()||isValidEmailAddress(c_email))){
                //check to see if the user already exists
                if(MainActivity.cm.CheckClient(c_email)
                        ||MainActivity.am.checkAdmin(c_email)){
                    new AlertDialog.Builder(Edit_ClientActivity.this)
                            .setTitle( "ERROR" )
                            .setMessage( "This email is already exist")
                            .setNegativeButton( "YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d( "AlertDialog", "negative" );

                                }
                            })
                            .show();
                }
                // put information in if the user doesn't exist
                else{
                    if (!firstName.isEmpty()) {
                        this.admin.editPersonalInfo("firstname", firstName);
                    }
                    if (!lastName.isEmpty()) {
                        this.admin.editPersonalInfo("lastname", lastName);
                    }
                    if (!c_email.isEmpty()) {
                        this.admin.editPersonalInfo("email", c_email);
                        MainActivity.pm.changePassword(this.email, c_email);

                    }
                    if (!address.isEmpty()) {
                        this.admin.editPersonalInfo("address", address);
                    }
                    if (!creditCard.isEmpty()) {
                        this.admin.editPersonalInfo("creditCard", creditCard);
                    }
                    if (!expiryDate.isEmpty()) {
                        this.admin.editPersonalInfo("ccExpDate", expiryDate);
                    }

                    MainActivity.am.remove(this.email);
                    MainActivity.am.add(admin);
                    MainActivity.saveFileAdmin();
                    intent.putExtra(MainActivity.USER,this.admin.email);
                    intent.putExtra(MainActivity.CHECK_ADMIN, check);
                    startActivity(intent);
                }

            }
            //if input is invalid print an error message
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Invalid input!")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }

        }
        // Checking the same things only if the user is now a Client
        else{
            if((isValidDate(expiryDate)||expiryDate.isEmpty())
                    &&(c_email.isEmpty()||isValidEmailAddress(c_email))){

                if(MainActivity.cm.CheckClient(c_email)
                        ||MainActivity.am.checkAdmin(c_email)){
                    new AlertDialog.Builder(Edit_ClientActivity.this)
                            .setTitle( "ERROR" )
                            .setMessage( "This email is already exist")
                            .setNegativeButton( "YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d( "AlertDialog", "negative" );

                                }
                            })
                            .show();
                }
                else{
                    if (!firstName.isEmpty()) {
                        this.client.editPersonalInfo("firstname", firstName);
                    }
                    if (!lastName.isEmpty()) {
                        this.client.editPersonalInfo("lastname", lastName);
                    }
                    if (!c_email.isEmpty() && isValidEmailAddress(c_email)) {
                        this.client.editPersonalInfo("email", c_email);
                        MainActivity.pm.changePassword(this.email, c_email);

                    }
                    if (!address.isEmpty()) {
                        this.client.editPersonalInfo("address", address);
                    }
                    if (!creditCard.isEmpty()) {
                        this.client.editPersonalInfo("creditCard", creditCard);
                    }
                    if (!expiryDate.isEmpty() && isValidDate(expiryDate)) {
                        this.client.editPersonalInfo("ccExpDate", expiryDate);
                    }
                    MainActivity.cm.removeClient(this.email);
                    MainActivity.cm.add(this.client);
                    MainActivity.saveFileClient();
                    intent.putExtra(MainActivity.USER, this.client.email);
                    intent.putExtra(MainActivity.CHECK_ADMIN, check);
                    startActivity(intent);
                }


            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Invalid input!")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

        }

    }

    /**
     * Checks if the email being entered is a valid email address for eg. xyz@gmail.com
     * @param email string of the email being verified
     * @return boolean true or false if it's valid or not.
     */
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]"
                + "+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\."
                + "[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)"
                + "+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Checks if the date being entered is a valid date for eg. 14/02/2012
     * If the date is invalid for any reason it will return false and
     * Print an error message.
     * @param inDate string of the date being verified.
     * @return boolean true or false if it's valid or not.
     */
    public static boolean isValidDate(String inDate) {
        Pattern datePattern = Pattern.compile("((19|20)\\d{2})-([1-9]|0[1-9]"
                + "|1[0-2])-(0[1-9]|[1-9]|[12][0-9]|3[01])");
        Matcher dateMatcher = datePattern.matcher(inDate);
        if(!dateMatcher.matches()){
            System.out.println("Invalid date format!!! -> " + inDate);
            return false;
        }
        System.out.println("Valid date format.");
        return true;
    }

}
