package csc207.phaseiii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/** A class representation of the search flight result activity. */
public class View_Personal_InfoActivity extends AppCompatActivity {
    /** the string that checks whether or not a user is a client or admin */
    private String check;
    /** The client's email/username */
    private String email;

    /**
     * Calls when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__personal__info);
        Intent intent = getIntent();
        check = (String) intent.getSerializableExtra(MainActivity.CHECK_ADMIN);
        email = (String) intent.getSerializableExtra(MainActivity.USER);
        if(check.equals("true")){
            TextView info = (TextView) findViewById(R.id.personalInfo);
            info.setText(MainActivity.am.getAdmin(email).viewPersonalInfo());
        }
        else{
            TextView info = (TextView) findViewById(R.id.personalInfo);
            info.setText(MainActivity.cm.getClient(email).viewPersonalInfo());
        }

    }

    /**
     * Returns the admin back to the
     * Edit client menu
     * @param view
     */
    public void editClient(View view) {
        Intent intent = new Intent(this, Edit_ClientActivity.class);
        intent.putExtra(MainActivity.USER, email);
        intent.putExtra(MainActivity.CHECK_ADMIN, check);
        startActivity(intent);
    }

    /**
     * Returns the user back to the
     * Main home page based off of
     * If they're a client or admin.
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
}