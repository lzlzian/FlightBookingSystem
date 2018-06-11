package managing;

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

/**
 * A class representation of a Password Manager.
 * Manages the saving and loading of all the passwords of the users.
 */
public class PasswordManager {

    private Map<String, String> passwords;

    public PasswordManager(){
        this.passwords = new HashMap<>();
    }

    /**
     * Adds a password.
     * @param username string the username of the user.
     * @param password string the password of the user.
     */
    public void addPassword(String username, String password){
        this.passwords.put(username, password);
    }

    /**
     * Checks if this is whether a legal/illegal password
     * @return boolean true or false on whether it's legal or not.
     */
    public boolean checkPassword(String username, String password){

        return this.passwords.containsKey(username)
                && this.passwords.get(username).equals(password);
    }

    /**
     * Checks whether client and password match.
     * @param cm the client manager which manages saving and loading of client.
     * @param username a string of the username of the user.
     * @param password a string of the password of the user.
     * @return boolean true or false whether the password matches or not.
     */
    public boolean checkClient(ClientManager cm, String username, String password){

        return this.checkPassword(username, password)
                && cm.CheckClient(username);
    }

    /**
     * Checks whether admin and password match.
     * @param am the client manager which manages saving and loading of admin.
     * @param username a string of the username of the user.
     * @param password a string of the password of the user.
     * @return boolean true or false whether the password matches or not.
     */
    public boolean checkAdmin(AdminManager am, String username, String password){

        return this.checkPassword(username, password) && am.checkAdmin(username);
    }

    /**
     * Changes the password of the user.
     * @param username a string of the username of the user.
     * @param newUsername a string of the new password of the user.
     */
    public void changePassword(String username, String newUsername){
        String password = this.passwords.get(username);
        this.passwords.remove(password);
        this.passwords.put(newUsername, password);
    }

    /**
     * Populates the records map from the file at path filePath.
     * @param file the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public void readFromCSVFile(File file) throws FileNotFoundException{
        Scanner scanner  = new Scanner(file);
        String[] record;
        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            this.addPassword(record[0], record[1]);
        }
        scanner.close();
    }


    /**
     * Check if the given username is in the map.
     * @param username username to be checked.
     * @return True or False.
     */
    public boolean checkUsername(String username){

        return this.passwords.containsKey(username);
    }


    /**
     * Return the size of this map.
     * @return the size of this map.
     */
    public int size(){

        return this.passwords.size();
    }
}
