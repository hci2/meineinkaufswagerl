package univie.ac.at.meineinkaufswagerl.model;

import java.util.ArrayList;

/**
 * Created by Philipp on 5/11/2016.
 */
public class CharityModel {

    private ArrayList<String> charityList;

    public CharityModel() {

        this.charityList = new ArrayList<>();
        addCharity("Care");
        addCharity("Essen auf Rädern");
        addCharity("Caritas");
        addCharity("Rote Kreuz");
        addCharity("Humana");
        addCharity("Hilfswerk");
        addCharity("Home Instead");
        addCharity("Volkshilfe");
        addCharity("Life Call");

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
