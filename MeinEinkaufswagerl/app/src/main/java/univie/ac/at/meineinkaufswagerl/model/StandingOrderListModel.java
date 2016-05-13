package univie.ac.at.meineinkaufswagerl.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Philipp on 5/8/2016.
 */
public final class StandingOrderListModel implements Serializable{

    private static final long serialVersionUID = 0L;

    private static ArrayList<String> standingOrderList= new ArrayList<>();

    private StandingOrderListModel(){}

    /*
    public StandingOrderListModel() {
        StandingOrderListModel.standingOrderList = new ArrayList<>();
    }
    */
    public static  ArrayList<String> getTextList() {
        return standingOrderList;
    }

    public static void addTextList(String speechInput) {
        StandingOrderListModel.standingOrderList.add(speechInput);
    }

    public static void changeTextListElement(String speechInput, int index){
        standingOrderList.add(index,speechInput);
    }

    public static void removeTextListElement(int index){
        standingOrderList.remove(index);
    }

    public static int getSize(){
        return standingOrderList.size();
    }
}
