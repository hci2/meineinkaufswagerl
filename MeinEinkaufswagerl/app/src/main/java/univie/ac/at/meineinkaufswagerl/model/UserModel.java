package univie.ac.at.meineinkaufswagerl.model;

import java.io.Serializable;

/**
 * Created by Philipp on 5/8/2016.
 */
public final class UserModel implements Serializable {

    private static final long serialVersionUID = 0L;

    private static int id=1;
    private static String firstname  = "Max";
    private static String lastname= "Mustermann";
    private static String street="Musterstraße";
    private static String streetnumber= "63a";
    private static String postalcode="1180";
    private static String country= "Österreich";
    //private static String username;
    //private static String password;
    private static String charity= "Caritas";
    private static boolean createdSuccessfullyProfile=true; //Derzeitig DEAKTIVIERT

    //Felix Addition Platzhalter

    // Default values because we have no serialisation/deserialisation
    private UserModel() {
        /*
        UserModel.id = 1;
        UserModel.firstname = "Max";
        UserModel.lastname = "Mustermann";
        UserModel.street = "Musterstraße";
        UserModel.streetnumber = "63a";
        UserModel.postalcode = "1180";
        UserModel.country = "Österreich";
        //UserModel.username = "Maxi";
        //UserModel.password = "max1234";
        UserModel.charity = "Caritas";
        UserModel.createdSuccessfullyProfile =true; //Derzeitig DEAKTIVIERT
        //TODO: Falls Serialisierung funktioniert, kann man es auf false setzen und wird bei Einkaufslisten erstellen überprüft
        */

    }

    // Getter and Setter for the all the private variables

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UserModel.id = id;
    }

    public static String getFirstname() {
        return firstname;
    }

    public static void setFirstname(String firstname) {
        UserModel.firstname = firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static void setLastname(String lastname) {
        UserModel.lastname = lastname;
    }

    public static String getStreet() {
        return street;
    }

    public static void setStreet(String street) {
        UserModel.street = street;
    }

    public static String getStreetnumber() {
        return streetnumber;
    }

    public static void setStreetnumber(String streetnumber) {
        UserModel.streetnumber = streetnumber;
    }

    public static String getPostalcode() {
        return postalcode;
    }

    public static void setPostalcode(String postalcode) {
        UserModel.postalcode = postalcode;
    }

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        UserModel.country = country;
    }

    /*
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    */
    public static String getCharity() {
        return charity;
    }

    public static void setCharity(String charity) {
        UserModel.charity = charity;
    }
    public static boolean getCreatedSuccessfullyProfile() {
        return createdSuccessfullyProfile;
    }

    public static void setCreatedSuccessfullyProfile(boolean createdSuccessfullyProfile) {
        UserModel.createdSuccessfullyProfile = createdSuccessfullyProfile;
    }
}
