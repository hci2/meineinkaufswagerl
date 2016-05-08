package univie.ac.at.meineinkaufswagerl.model;

import java.util.ArrayList;

/**
 * Created by Philipp on 5/8/2016.
 */
public class TemporaryListModel {
    private ArrayList<String> temporaryList;

    public TemporaryListModel() {
        this.temporaryList = new ArrayList<>();
    }

    public ArrayList<String> getTextList() {
        return temporaryList;
    }

    public void addTextList(String speechInput) {
        this.temporaryList.add(speechInput);
    }

    public void changeTextListElement(String speechInput, int index){
        temporaryList.add(index,speechInput);
    }

    public void removeTextListElement(int index){
        temporaryList.remove(index);
    }
}
