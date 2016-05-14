package univie.ac.at.meineinkaufswagerl.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Philip on 09.05.2016.
 */
public class ShoppingListModel implements Serializable{

    private static final long serialVersionUID = 0L;

    private ArrayList<ProductModel> products;
    private ArrayList<Integer> prodAmount;

    public ShoppingListModel() {
        this.products = new ArrayList<ProductModel>();
        this.prodAmount = new ArrayList<Integer>();
    }
    public void addProduct(ProductModel product) { this.products.add(product); }
    public ArrayList<ProductModel> getProducts() { return this.products; }
    public void addAmount(int amount) { this.prodAmount.add(amount); }
    public ArrayList<Integer> getAmounts() { return this.prodAmount; }
}
