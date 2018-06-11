package csc207.phaseiii;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import java.util.Map;
import backend.Client;
import managing.ClientManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/** A class representation of the search flight result activity. */
public class View_ClientsActivity extends AppCompatActivity {



    ListView listView ;
    ArrayAdapter<String> adapter;
    /** The client manager object */
    ClientManager cm;
    /** The arrayList of clients */
    ArrayList<Client> clients;
    /** The position of each client */
    int itemPosition;
    /** The email/username of the client */
    String email;
    /** The check string knows that these objects are clients so it initializes to false */
    String check = "false";

    /**
     * Calls when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__clients);
        Intent intent = getIntent();
        this.email = (String) intent.getSerializableExtra(MainActivity.USER);
        listView = (ListView) findViewById(R.id.listClients);

        cm = MainActivity.cm;

        clients = new ArrayList<>();
        for(Map.Entry<String, Client> entry: cm.getClients().entrySet()){
            clients.add(entry.getValue());
        }
        String[] values = new String[clients.size()];
        for(int i = 0; i< clients.size(); i++){
            values[i] = clients.get(i).viewPersonalInfo();
        }


        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert

                new AlertDialog.Builder(View_ClientsActivity.this )
                        .setTitle( "EDIT CLIENT" )
                        .setMessage( "Do you want switch to this client:"+"\n" + itemValue)

                        .setPositiveButton( "CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d( "AlertDialog", "cancel" );
                            }
                        } )
                        .setNegativeButton( "EDIT", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d( "AlertDialog", "edit" );
                                Intent intent = new Intent(View_ClientsActivity.this,
                                        Menu_Client.class);
                                intent.putExtra(MainActivity.USER, clients.get(itemPosition).email);
                                intent.putExtra(MainActivity.CHECK_ADMIN, check);
                                startActivity(intent);
                            }
                        })

                        .show();

            }

        });



    }

    /**
     * If the user wants to return to their home screen they
     * Can press the button that uses this method.
     * It changes the admin activity
     * @param view
     */
    public void home(View view){
        Intent intent = new Intent(this, Menu_Admin.class);
        intent.putExtra(MainActivity.USER, this.email);
        intent.putExtra(MainActivity.CHECK_ADMIN, "true");
        startActivity(intent);
    }
}