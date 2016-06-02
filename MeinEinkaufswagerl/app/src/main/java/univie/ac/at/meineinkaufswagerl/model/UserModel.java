package univie.ac.at.meineinkaufswagerl.model;

import java.io.Serializable;

/**
 * Created by Philipp on 5/8/2016.
 */
public class UserModel implements Serializable {

    private static final long serialVersionUID = 0L;
    /*
    //Singleton Construction
    private static UserModel ourInstance = new UserModel();
    public static UserModel getInstance() {
        return ourInstance;
    }
       */

    private int id=1;
    private String firstname  = "Max";
    private String lastname= "Mustermann";
    private String street="Musterstraße";
    private String streetnumber= "63a";
    private String postalcode="1180";
    private String country= "Österreich";
    //private static String username;
    //private static String password;
    private String charity= "Caritas";
    private boolean createdSuccessfullyProfile=false; //Derzeitig AKTIVIERT

    //Felix Addition Platzhalter

    // Default values because we have no serialisation/deserialisation
    public UserModel() {
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

    /*
    public static void setAllAttributes(String firstname, String lastname, String street, String streetnumber, String postalcode, String country,String charity, boolean createdSuccessfullyProfile){
        setFirstname(firstname);
        setLastname(lastname);
        setStreet(street);
        setStreetnumber(streetnumber);
        setPostalcode(postalcode);
        setCountry(country);
        setCharity(charity);
        setCreatedSuccessfullyProfile(createdSuccessfullyProfile);
    }
    */
    // Getter and Setter for the all the private variables

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getFirstname() {
        return firstname;
    }

    public  void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public  String getLastname() {
        return lastname;
    }

    public  void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public  String getStreet() {
        return street;
    }

    public  void setStreet(String street) {
        this.street = street;
    }

    public  String getStreetnumber() {
        return streetnumber;
    }

    public  void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public  String getPostalcode() {
        return postalcode;
    }

    public  void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public  String getCountry() {
        return country;
    }

    public  void setCountry(String country) {
        this.country = country;
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
    public  String getCharity() {
        return charity;
    }

    public  void setCharity(String charity) {
        this.charity = charity;
    }

    public  boolean getCreatedSuccessfullyProfile() {
        return createdSuccessfullyProfile;
    }

    public  void setCreatedSuccessfullyProfile(boolean createdSuccessfullyProfile) {
        this.createdSuccessfullyProfile = createdSuccessfullyProfile;
    }
}
