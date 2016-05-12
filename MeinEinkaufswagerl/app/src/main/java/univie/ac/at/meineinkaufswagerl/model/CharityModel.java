package univie.ac.at.meineinkaufswagerl.model;

import java.util.ArrayList;

/**
 * Created by Philipp on 5/11/2016.
 */
public class CharityModel {

    private ArrayList<String> charityList;
    private String[][]  charityListVienna;

    public CharityModel() {
        charityListVienna= new String[][]{
                {"1010", "Care"},
                {"1020", "Essen auf Rädern"},
                {"1030", "Caritas"},
                {"1040", "Caritas"},
                {"1050", "Caritas"},
                {"1060", "Rote Kreuz"},
                {"1070", "Rote Kreuz"},
                {"1080", "Care"},
                {"1090", "Essen auf Rädern"},
                {"1100", "Life Call"},
                {"1110", "Life Call"},
                {"1120", "Volkshilfe"},
                {"1130", "Volkshilfe"},
                {"1140", "Home Instead"},
                {"1150", "Home Instead"},
                {"1160", "Hilfswerk"},
                {"1170", "Hilfswerk"},
                {"1180", "Hilfswerk"},
                {"1190", "Humana"},
                {"1200", "Humana"},
                {"1210", "Humana"},};

        this.charityList = new ArrayList<>();
        addCharity("Care");
        addCharity("Hilfswerk");
        addCharity("Caritas");
        addCharity("Rote Kreuz");
        addCharity("Humana");
        addCharity("Hilfswerk");
        addCharity("Home Instead");
        addCharity("Volkshilfe");
        addCharity("Life Call");

    }

    public String[][] getCharityListVienna() {
        return charityListVienna;
    }

    public void setCharityListVienna(String[][] charityListVienna) {
        this.charityListVienna = charityListVienna;
    }

    public String getCharityForPLZ(String postalcode){
        String result=null;
        for (int row =0;row<charityListVienna.length;row++){
            String[] rowObject = charityListVienna[row];
            //if(rowObject!= null){
                for (int col =0;col<charityListVienna[row].length;col++) {
                    if(charityListVienna[row][col].equals(postalcode)){
                        result= charityListVienna[row][++col];
                        return result;
                    }
                }
            //}
        }
        return result;
    }

    public ArrayList<String> getTextList() {
        return charityList;
    }

    public void addCharity(String speechInput) {
        this.charityList.add(speechInput);
    }

    public void changeCharity(String speechInput, int index){
        charityList.add(index,speechInput);
    }

    public void removeCharity(int index){
        charityList.remove(index);
    }
}
