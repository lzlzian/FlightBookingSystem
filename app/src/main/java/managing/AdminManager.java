package managing;

/**
 * Created by Sunil on 2015-12-04.
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

import backend.Administrator;
import backend.Client;


/**
 * A class representation of a Admin Manager.
 * Manages the saving and loading of Admins.
 */
public class AdminManager implements Serializable {

    private static final long serialVersionUID = -484379373765603767L;

    /** A mapping of all the admin objects. */
    private Map<String, Administrator> Administrators;
    /** Initializes Logger. */
    private static final Logger logger =
            Logger.getLogger(AdminManager.class.getPackage().getName());
    /** Initializes Console Handler */
    private static final Handler consoleHandler = new ConsoleHandler();
    /** Creates a new hash map to store all the admins. */
    public AdminManager(){

        this.Administrators = new HashMap<>();
    }


    /**
     * Populates the records map from the file.
     * @param file the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public void readFromCSVFile(File file) throws FileNotFoundException{
        Scanner scanner  = new Scanner(file);
        String[] record;
        Administrator administrator;
        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            administrator = new Administrator(record[0], record[1], record[2], record[3],
                    record[4], record[5]);
            Administrators.put(administrator.email, administrator);
        }
        scanner.close();
    }

    /**
     * Adds record to this AdminManager.
     * @param record a record to be added.
     */
    public void add(Administrator record) {
        Administrators.put(record.email, record); //No getter and can access.
        // Log the addition of a Admin.
        logger.log(Level.FINE, "Added a new admin " + record.toString());
    }


    /**
     * Writes the admins to file at filePath.
     * @param filePath the file to write the records to
     * @throws IOException
     */
    public void saveToFile(File filePath) throws IOException {
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        // serialize the Map
        output.writeObject(Administrators);
        output.close();
    }


    /**
     * Creates a Map<String, Admin> object based on the file.
     * Then assigns that object to the class variable clients.
     * @param path the file.
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public void readFromFile(File path) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            //deserialize the Map
            this.Administrators = (Map<String, Administrator>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }


    /**
     * Remove the entry with key email from the map.
     * @param email the email
     */
    public void remove(String email){
        this.Administrators.remove(email);

    }
    /**
     * Returns a client object from the given email provided.
     * @param email a string of the email of the specific
     * client wished to search for
     * @return the client object found.
     */
    public Administrator getAdmin(String email){
        return Administrators.get(email);
    }

    public boolean checkAdmin(String email){

        return this.Administrators.containsKey(email);
    }

    /**
     * Returns a string representation of the Client object.
     */
    @Override
    public String toString() {
        String result = "";
        for (Client r : Administrators.values()) {
            result += r.toString() + "\n";
        }
        return result;

    }
}


