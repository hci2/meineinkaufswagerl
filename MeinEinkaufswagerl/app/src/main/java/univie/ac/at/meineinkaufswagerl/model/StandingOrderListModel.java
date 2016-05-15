package univie.ac.at.meineinkaufswagerl.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Philipp on 5/8/2016.
 */
public class StandingOrderListModel implements Serializable{

    private static final long serialVersionUID = 0L;

    /*
    //Singleton Construction
    private static StandingOrderListModel ourInstance = new StandingOrderListModel();

    public static StandingOrderListModel getInstance() {
        return ourInstance;
    }

    private StandingOrderListModel() {
    }
    */
    private  ArrayList<String> standingOrderList= new ArrayList<>();



    public StandingOrderListModel() {
        standingOrderList = new ArrayList<>();
    }

    public   ArrayList<String> getTextList() {
        return standingOrderList;
    }

    public  void addTextList(String speechInput) {
        this.standingOrderList.add(speechInput);
    }

    public  void changeTextListElement(String speechInput, int index){
        standingOrderList.add(index,speechInput);
    }

    public  void removeTextListElement(int index){
        standingOrderList.remove(index);
    }

    public  int getSize(){
        return standingOrderList.size();
    }
}
