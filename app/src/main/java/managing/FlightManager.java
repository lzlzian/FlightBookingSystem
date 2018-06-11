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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import backend.Flight;
import backend.Itinerary;

/**
 * This is a class representation of a Flight Manager
 * @author Sunil
 * Manages the saving and loading of Flights.
 */
public class FlightManager implements Serializable {

    private static final long serialVersionUID = 8099443834493365277L;

    /** A mapping of all the flight objects. */
    private Map<String,Flight> flights;
    /** Initializes Logger. */
    private static final Logger logger =
            Logger.getLogger(FlightManager.class.getPackage().getName());
    /** Initializes console handler. */
    private static final Handler consoleHandler = new ConsoleHandler();
    /** Creates a new hash map to store all the flights. */
    public FlightManager(){
        this.flights = new HashMap<>();

    }


    /**
     * Populates the records map from the file at path filePath.
     * @param filePath the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public void readFromCSVFile(String filePath)
            throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] record;
        Flight flight;
        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            flight = new Flight(record[0], record[1], record[2], record[3],
                    record[4],record[5],
                    Double.parseDouble(record[6]), Integer.parseInt(record[7]));
            flights.put(flight.flightNum, flight);
        }
        scanner.close();
    }


    /**
     * Adds record to this FlightManager.
     * @param record a record to be added.
     */
    public void add(Flight record) {
        flights.put(record.flightNum, record);
        // Log the addition of a flight.
        logger.log(Level.FINE, "Added a new flight " + record.toString());
    }


    /**
     * Writes the flights to file at filePath.
     * @param filePath the file to write the records to
     * @throws IOException
     */
    public void saveToFile(File filePath) throws IOException {
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        // serialize the Map
        output.writeObject(flights);
        output.close();
    }


    public void readFromCSVFile(File file) throws FileNotFoundException{
        Scanner scanner = new Scanner(file);
        String[] record;
        Flight flight;
        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            flight = new Flight(record[0], record[1], record[2], record[3],
                    record[4],record[5],
                    Double.parseDouble(record[6]), Integer.parseInt(record[7]));
            flights.put(flight.flightNum, flight);
        }
        scanner.close();

    }
    /**
     * Returns all the specific flights as an itinerary from the flights
     * information provided.
     * @param departDate a search criteria for the flights (departure date).
     * @param origin a search criteria for the flights (origin).
     * @param destination a search criteria for the flights (destination).
     * @return an itinerary of the flights found through the given criteria.
     */
    public Itinerary getFlight(String departDate,String origin,
                               String destination){

        Itinerary temp = new Itinerary();

        for(Map.Entry<String, Flight> entry: flights.entrySet()){
            if(entry.getValue().departDate.equals(departDate)){
                if(entry.getValue().origin.equals(origin)){
                    if(entry.getValue().destination.equals(destination)){
                        if(entry.getValue().bookable()) {
                            temp.append(entry.getValue());
                        }
                    }
                }
            }
        }
        return temp;
    }

    /**
     * Returns all the specific flights as an ArrayList of itineraries from the flights
     * information provided.
     * @param departDate a search criteria for the flights (departure date).
     * @param origin a search criteria for the flights (origin).
     * @param destination a search criteria for the flights (destination).
     * @return an ArrayList of itineraries of the flights found through the given criteria.
     */
    public ArrayList<Itinerary> getFlightItinerary(String departDate,String origin,
                                                   String destination){
        Itinerary temp = this.getFlight(departDate, origin, destination);
        ArrayList<Itinerary> result = new ArrayList<>();
        for(Flight flight: temp.itinerary){
            if(flight.departDate.equals(departDate)){
                if(flight.origin.equals(origin)){
                    if(flight.destination.equals(destination)){
                        if(flight.bookable()) {
                            Itinerary newTemp = new Itinerary();
                            newTemp.append(flight);
                            result.add(newTemp);
                        }
                    }
                }
            }
        }
        return result;



    }

    /**
     * Returns all the specific flights as an itinerary from the flights
     * information provided.
     * @param departDate a search criteria for the flights (departure date).
     * @param origin a search criteria for the flights (origin).
     * @return all flights with same departDate, origin as required.
     */
    public Itinerary getItinerariesHelper1(String departDate,String origin){
        Itinerary temp = new Itinerary();
        for(Map.Entry<String, Flight> entry: flights.entrySet()){
            if(entry.getValue().departDate.equals(departDate)){
                if(entry.getValue().origin.equals(origin)){
                    if (entry.getValue().bookable()) {
                        temp.append(entry.getValue());
                    }
                }
            }
        }
        return temp;
    }


    /**
     * Adds all the specific flights as an itinerary from the flights
     * information provided.
     * @param origin a search criteria for the flights (origin).
     * @param destination a search criteria for the flights (destination).
     * @param ItineraryList a search criteria for the flights (itinerary list).
     * @param temp a search criteria for the flights (temp).
     */
    public void getItinerariesHelper2(String origin, String destination,
                                      ArrayList<Itinerary> ItineraryList, Itinerary temp){
        for(Map.Entry<String, Flight> entry: flights.entrySet()){
            if(entry.getValue().origin.equals(origin)){
                if(temp.lastFlight().validWait(entry.getValue())){
                    if(entry.getValue().bookable()){
                        if(entry.getValue().destination.equals(destination)){
                            Itinerary newTemp = new Itinerary();
                            newTemp.addItinerary(temp);
                            newTemp.append(entry.getValue());
                            ItineraryList.add(newTemp);
                        }
                        else {
                            Itinerary newTemp = new Itinerary();
                            newTemp.addItinerary(temp);
                            newTemp.append(entry.getValue());
                            this.getItinerariesHelper2(entry.getValue().destination,
                                    destination,
                                    ItineraryList, newTemp);
                        }
                    }
                }
            }
        }

    }


    /**
     * Returns the itinerary list which matches the given criteria.
     * @param departDate a search criteria for the flights (departure date).
     * @param origin a search criteria for the flights (origin).
     * @param destination a search criteria for the flights (destination).
     * @return the itinerary list which matches the criteria.
     */
    public ArrayList<Itinerary> getItineraries(String departDate, String origin,
                                               String destination) {
        Itinerary start = this.getItinerariesHelper1(departDate, origin);
        ArrayList<Itinerary> ItineraryList = new ArrayList<>();
        for(Flight flight: start.itinerary){
            Itinerary temp = new Itinerary();
            temp.append(flight);
            if(flight.destination.equals(destination)){
                ItineraryList.add(temp);
            }
            else
                this.getItinerariesHelper2(flight.destination,
                        destination,ItineraryList, temp);
        }
        return ItineraryList;
    }


    /**
     * Returns a sorted itinerary list in ascending order in respect
     * to the travel time.
     * @param TotalItinerary the whole current itinerary available
     * @return a sorted list of type itinerary.
     */
    public ArrayList<Itinerary> sortByTime(ArrayList<Itinerary> TotalItinerary) {

        Itinerary temp;
        for(int i = 0; i< TotalItinerary.size(); i++ ){
            for(int j = 1; j < TotalItinerary.size();j++){
                if(TotalItinerary.get(j-1).totalTime()
                        > TotalItinerary.get(j).totalTime()){
                    temp = TotalItinerary.get(j-1);
                    TotalItinerary.set(j-1, TotalItinerary.get(j));
                    TotalItinerary.set(j, temp);
                }

            }
        }

        return TotalItinerary;
    }

    /**
     * Returns a sorted itinerary list in
     * ascending order in respect to the cost.
     * @param TotalItinerary the whole current itinerary available
     * @return a sorted list of type itinerary.
     */
    public ArrayList<Itinerary> sortByCost(ArrayList<Itinerary> TotalItinerary) {

        Itinerary temp;
        for(int i = 0; i< TotalItinerary.size(); i++ ){
            for(int j = 1; j <TotalItinerary.size();j++){
                if(TotalItinerary.get(j-1).totalCost()
                        > TotalItinerary.get(j).totalCost()){
                    temp = TotalItinerary.get(j-1);
                    TotalItinerary.set(j-1, TotalItinerary.get(j));
                    TotalItinerary.set(j, temp);
                }
            }
        }
        return TotalItinerary;
    }


    /**
     * Returns the flight object required.
     * @param key the unique string which distinguishes each flight.
     * @return the flight object which matches the string provided.
     */
    public Flight getFlight(String key){

        return flights.get(key);
    }


    /**
     * Returns the map containing all the Flights.
     * @return a map of all the Flights.
     */
    public Map<String,Flight> getFlights(){

        return flights;
    }


    /**
     * Populates the Flight map by reading from the given file.
     * @param fileName the file containing Flight information.
     */
    @SuppressWarnings("unchecked")
    public void readFromFile(File fileName) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(fileName);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize the Map
            this.flights = (Map<String,Flight>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }


    /**
     * Checks if Flight with the given Flight number exists in the map.
     * @param number the Flight number.
     * @return True - the Flight exists in the map. False - the Flight doesn't exist in the map.
     */
    public boolean checkFlight(String number){

        return this.flights.containsKey(number);
    }


    /**
     * Upload the Flight info give to this map.
     * @param fm the FlightManager object containing a map of Flights to be added to this map.
     */
    public void uploadFlight(FlightManager fm){
        for(Map.Entry<String, Flight> entry: fm.flights.entrySet()){
            if(!this.checkFlight((entry.getValue().flightNum))){
                this.add(entry.getValue());
            }
        }
    }


    /**
     * Get the size of this map.
     * @return the size of this map.
     */
    public int size(){

        return this.flights.size();
    }


    /**
     * Returns a string representation of the flights in flight manager.
     * @return  a string representation of the flights in flight manager.
     */
    @Override
    public String toString() {
        String result = "";
        for (Flight r : flights.values()) {
            result += r.toString() + "\n";
        }
        return result;
    }
}

