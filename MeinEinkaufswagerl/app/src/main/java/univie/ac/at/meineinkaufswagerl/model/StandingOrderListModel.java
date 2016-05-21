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
        standingOrderList.add(index, speechInput);
    }

    public  void removeTextListElement(int index){
        standingOrderList.remove(index);
    }

    public  int getSize(){
        return standingOrderList.size();
    }

    private  ArrayList<ProductModel> productList= new ArrayList<>();

    public ArrayList<ProductModel> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductModel> productList) {
        this.productList = productList;
    }

    public int getSizeProductList() {
        return productList.size();
    }

    public ProductModel getElementProductList(int index) {
        return productList.get(index);
    }
    public String getElementNameProductList(int index) {
        return productList.get(index).getName();
    }

}
