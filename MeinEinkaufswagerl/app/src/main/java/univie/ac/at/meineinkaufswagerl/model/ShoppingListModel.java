package univie.ac.at.meineinkaufswagerl.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Philip on 09.05.2016.
 */
public class ShoppingListModel implements Serializable{

    private static final long serialVersionUID = 0L;

    private ArrayList<ProductModel> products;

    public ShoppingListModel() {
        this.products = new ArrayList<ProductModel>();
    }
    public void addProduct(ProductModel product) { this.products.add(product); }
    public ArrayList<ProductModel> getProducts() { return this.products; }
}
