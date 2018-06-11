package managing;

/**
 * Created by Sunil on 2015-12-03.
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import backend.Client;

/**
 * A class representation of a Client Manager.
 * Manages the saving and loading of Clients.
 */
public class ClientManager implements Serializable {

    private static final long serialVersionUID = 7999766474664436197L;

    /** A mapping of all the client objects. */
    public Map<String, Client> Clients;

    /** Initializes Logger. */
    private static final Logger logger =
            Logger.getLogger(ClientManager.class.getPackage().getName());

    /** Initializes Handler. */
    private static final Handler consoleHandler = new ConsoleHandler();

    /** Creates a new hash map to store all the clients. */
    public ClientManager(){

        this.Clients = new HashMap<>();
    }


    /**
     * Populates the records map from the file at path filePath.
     * @param filePath the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public void readFromCSVFile(String filePath)
            throws FileNotFoundException {

        // FileInputStream can be used for reading raw bytes, like an image.
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] record;
        Client client;

        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");

            if (Clients.containsKey(record[2])){
                Clients.remove(record[2]);}

            client = new Client(record[0], record[1], record[2], record[3],
                    record[4], record[5]);

            Clients.put(client.email, client); //No getter and can access
        }
        scanner.close();
    }


    /**
     * Populates the records map from the file.
     * @param file the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public void readFromCSVFile(File file) throws FileNotFoundException{

        Scanner scanner  = new Scanner(file);
        String[] record;
        Client client;

        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");

            client = new Client(record[0], record[1], record[2], record[3],
                    record[4], record[5]);

            Clients.put(client.email, client); //No getter and can access
        }
        scanner.close();
    }

    /**
     * Adds record to this ClientManager.
     * @param record a record to be added.
     */
    public void add(Client record) {
        Clients.put(record.email, record); //No getter and can access.

        // Log the addition of a client.
        logger.log(Level.FINE, "Added a new client " + record.toString());
    }


    /**
     * Writes the clients to file at filePath.
     * @param filePath the file to write the records to
     * @throws IOException
     */
    public void saveToFile(File filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(Clients);
        output.close();
    }


    @SuppressWarnings("unchecked")
    public void readFromFile(File path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize the Map
            this.Clients = (Map<String, Client>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }


    /**
     * Returns a client object from the given email provided.
     * @param email a string of the email of the specific
     * client wished to search for
     * @return the client object found.
     */
    public Client getClient(String email){

        return Clients.get(email);
    }

    public Client removeClient(String email){

        return Clients.remove(email);
    }

    public boolean CheckClient(String email){
        return Clients.containsKey(email);
    }

    public void uploadClient(ClientManager cm){
        for(Map.Entry<String, Client> entry: cm.Clients.entrySet()){
            if(!this.CheckClient(entry.getValue().email)){
                this.add(entry.getValue());
            }
        }
    }

    public Map<String, Client> getClients(){

        return this.Clients;
    }
    public int size(){
        return this.Clients.size();
    }
    /**
     * Returns a string representation of the Client object.
     */
    @Override
    public String toString() {
        String result = "";

        for (Client r : Clients.values()) {
            result += r.toString() + "\n";
        }

        return result;

    }
}