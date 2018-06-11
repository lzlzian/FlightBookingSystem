package backend;

/**
 * Created by Sunil on 2015-12-04.
 */
import java.io.Serializable;

/** An administrator class which inherits Client class methods. */
public class Administrator extends Client implements Serializable{

    private static final long serialVersionUID = 3264124345079640407L;

    /**
     * This is the Administrator constructor method.
     * Creates a new administrator object with the given parameters.
     *
     * @param lastName   a string of the last name of the administrator
     * @param firstName  a string of the first name of the administrator
     * @param email      a string of the the email address of the administrator
     * @param address    a string of the the address of the administrator
     * @param creditCard a string of the credit card
     *                   number of the administrator
     * @param ccExpDate  credit card expiration date of the administrator
     */
    public Administrator(String lastName, String firstName, String email,
                         String address, String creditCard, String ccExpDate) {

        /** Inherits the Client class methods */
        super(lastName, firstName, email, address, creditCard, ccExpDate);

    }


    /**
     * Edits the current available flights.
     *
     * @param flight the flight which the administrator wishes to edit.
     * @param option the section which the administrator wishes to edit.
     * @param info   the new information the administrator
     *               wishes to replace the chosen option.
     */
    public void editFlight(Flight flight, String option, String info) {

        if (option.equals("number")) {

            flight.setFlightNum(info);

        } else if (option.equals("airline")) {

            flight.setAirline(info);

        } else if (option.equals("origin")) {

            flight.setOrigin(info);

        } else if (option.equals("destination")) {

            flight.setDestination(info);

        } else if (option.equals("price")) {

            flight.setPrice(info);

        } else if (option.equals("departDateTime")) {

            flight.setDepartTimeAndDate(info);

        } else if (option.equals("arrivalDateTime")) {
            flight.setArrivalTimeAndDate(info);

        } else if (option.equals("numSeats")) {
            flight.setNumSeats(info);
        }
    }
}