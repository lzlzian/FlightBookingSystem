package backend;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable {

    private static final long serialVersionUID = 134960752691212585L;

    /**
     * These are all the details a user is expected to have.
     * Whether it be an Administrator or Client.
     */

    /** The first name of the User. */
    protected String firstName;


    /** The last name of the User. */
    protected String lastName;


    /** The email of the User. */
    public String email;


    /** The address of the User. */
    protected String address;


    /** An itinerary list of which would store
     * information about the current User's
     * flight information.
     */
    protected ArrayList<Itinerary> ItineraryList;


    /** Credit card number of the User. */
    protected String creditCard;


    /** Credit card expiration date for verification purposes. */
    protected String ccExpDate;


    /**
     * This is the User constructor method.
     * Creates a new User object with the given parameters.
     * @param lastName a string of the last name of the User
     * @param firstName a string of the first name of the User
     * @param email a string of the the email address of the User
     * @param address a string of the the address of the User
     * @param creditCard a string of the credit card number of the User
     * @param ccExpDate credit card expiration date of the User
     */


    public User(String lastName, String firstName, String email,
                String address, String creditCard, String ccExpDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.creditCard = creditCard;
        this.ccExpDate = ccExpDate;
        this.ItineraryList = new ArrayList<>();
    }


    /**
     * Sets the user's firstName to what string is represented in the parameter.
     * @param firstName a string of the User's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * Sets the user's lastName to what string is represented in the parameter.
     * @param lastName a string of the User's first name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * Sets the user's email to what string is represented in the parameter.
     * @param email a string of the User's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Sets the user's email to what string is represented in the parameter.
     * @param address a string of the User's email.
     */
    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * Edits the information of the current User.
     * @param option a string lets the user input which
     * section he/she wishes to edit.
     * @param info a string which the user writes to
     * update the chosen section to the new value.
     */
    public void editUserInfo(String option, String info ){

        if(option.equals("firstname")){
            this.setFirstName(info);
        }
        if(option.equals("lastname")){
            this.setLastName(info);
        }
        if(option.equals("email")){
            this.setEmail(info);
        }
        if(option.equals("address")){
            this.setAddress(info);
        }
    }
}