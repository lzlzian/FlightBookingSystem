package managing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import backend.Itinerary;
import backend.User;

/**
 * Created by Ian on 12/8/2015.
 */
public class BookManager implements Serializable {

    private static final long serialVersionUID = -3551197418696377386L;

    /** A mapping of all the flight objects. */
    private Map<User, ArrayList<Itinerary>> Bookings;
    /** Initializes Logger. */
    private static final Logger logger =
            Logger.getLogger(BookManager.class.getPackage().getName());
    /** Initializes Console Handler */
    private static final Handler consoleHandler = new ConsoleHandler();


    /** Creates a new hash map to store all the users' bookings. */
    public BookManager(){

        this.Bookings = new HashMap<>();
    }


    @SuppressWarnings("unchecked")
    public void readFromFile(File path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize the Map
            this.Bookings = (Map<User, ArrayList<Itinerary>>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }


    /**
     * Writes the bookings to file at filePath.
     * @param filePath the file to write the records to
     * @throws IOException
     */
    public void saveToFile(File filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(Bookings);
        output.close();
    }


    /**
     * Returns the string representation of the current
     * itinerary the user currently has.
     * @return a string of the current itinerary that the client has.
     */
    public ArrayList<Itinerary> checkBookedItinerary(User user) {

        return this.Bookings.get(user);
    }


    /**
     * Add a specific itinerary from the user's current itinerary list.
     * @param user the user who books the itinerary.
     * @param  newItinerary the itinerary which the user wishes to book.
     */
    public void bookItinerary(User user, Itinerary newItinerary) {

        ArrayList<Itinerary> booked = this.Bookings.get(user);
        booked.add(newItinerary);
        this.Bookings.put(user, booked);
    }


    /**
     * Removes a specific itinerary from the user's current itinerary list.
     * @param user the user who cancels booking.
     * @param index the itinerary which the user wishes to remove.
     */
    public void cancelItinerary(User user, int index) {

        ArrayList<Itinerary> booked = this.Bookings.get(user);
        booked.remove(index);
        this.Bookings.put(user, booked);
    }


    /**
     * Checks if the itinerary is already booked by the user.
     * @param user the user who books the itinerary.
     * @param  newItinerary the itinerary which the user wishes to compare.
     */
    public boolean checkBooked(User user, String newItinerary) {

        ArrayList<Itinerary> booked = this.Bookings.get(user);
        for (Itinerary itinerary : booked) {
            if (itinerary.toString().equals(newItinerary)) {
                return true;
            }
        }
        return false;
    }
}
