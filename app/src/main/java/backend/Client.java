package backend;

/**
 * Created by Sunil on 2015-12-03.
 */
import java.io.Serializable;
import java.util.ArrayList;


public class Client extends User implements Serializable {

    private static final long serialVersionUID = -3115852105712025256L;

    /**
     * This is the Client constructor method.
     * Creates a new Client object with the given parameters.
     * @param lastname a string of the last name of the client.
     * @param firstname a string of the first name of the client.
     * @param email a string of the email of the client.
     * @param address a string of the address of the client.
     * @param creditCard a string of the credit information of the client.
     * @param ccExpDate a string of the credit card expiration
     * date of the client.
     */
    public Client(String lastname, String firstname, String email,
                  String address, String creditCard, String ccExpDate) {

        /** Inherits the User class methods */
        super(lastname, firstname, email, address, creditCard, ccExpDate);
    }


    /**
     * Returns the current clients stored personal information.
     * @return a string of all the clients personal information.
     */
    public String viewPersonalInfo() {

        return
                "First Name: " + firstName + "\n"
                        + "Last Name: " + lastName + "\n"
                        + "Email: " + email + "\n"
                        + "Address: " + address + "\n"
                        + "Credit Card: " + creditCard + "\n"
                        + "Expiry Date: " + ccExpDate;
    }


    /**
     * Sets the client's Credit Card number to what string
     * is represented in the parameter.
     * @param creditCard a string of the client's credit card number.
     */
    public void setCreditCard(String creditCard) {

        this.creditCard = creditCard;
    }


    /**
     * Sets the client's Credit Card expiration date to what
     * string is represented in the parameter.
     * @param ccExpDate a string of the client's credit card expiration date.
     */
    public void setccExpDate(String ccExpDate) {

        this.ccExpDate = ccExpDate;
    }


    /**
     * Edits the personal information of the current client.
     * @param option a string lets the client input which section
     * he/she wishes to edit.
     * @param info a string which the client writes to update the
     * chosen section to the new value.
     */
    public void editPersonalInfo(String option, String info){

        /** Inherits the editUserInfo class methods */
        super.editUserInfo(option, info);

        /** Implements the ability for clients to change
         * their credit card details. */
        if(option.equals("creditCard")){
            this.setCreditCard(info);

        }
        if(option.equals("ccExpDate")){
            this.setccExpDate(info);
        }
    }


    /**
     * A to-String method which returns a string
     * representation about the client.
     */
    @Override
    public String toString() {

        return lastName + ","+ firstName + ","+ email
                + "," + address + "," + creditCard + "," + ccExpDate;
    }
}
