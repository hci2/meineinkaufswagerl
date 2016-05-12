package univie.ac.at.meineinkaufswagerl.model;

/**
 * Created by Philipp on 5/8/2016.
 */
public class UserModel {
    private int id;
    private String firstname;
    private String lastname;
    private String street;
    private String streetnumber;
    private String postalcode;
    private String country;
    private String username;
    private String password;
    private String charity;
    private boolean createdSuccessfullyProfile;

    //Felix Addition Platzhalter

    // Default values because we have no serialisation/deserialisation
    public UserModel() {
        this.id = 1;
        this.firstname = "Max";
        this.lastname = "Mustermann";
        this.street = "Musterstraße";
        this.streetnumber = "63a";
        this.postalcode = "1180";
        this.country = "Österreich";
        this.username = "Maxi";
        this.password = "max1234";
        this.charity = "Caritas";
        this.createdSuccessfullyProfile =true; //Derzeitig DEAKTIVIERT
        //TODO: Falls Serialisierung funktioniert, kann man es auf false setzen und wird bei Einkaufslisten erstellen überprüft


    }

    // Getter and Setter for the all the private variables

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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

    public String getCharity() {
        return charity;
    }

    public void setCharity(String charity) {
        this.charity = charity;
    }
    public boolean getCreatedSuccessfullyProfile() {
        return createdSuccessfullyProfile;
    }

    public void setCreatedSuccessfullyProfile(boolean createdSuccessfullyProfile) {
        this.createdSuccessfullyProfile = createdSuccessfullyProfile;
    }
}
