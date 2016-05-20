package univie.ac.at.meineinkaufswagerl.model;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by Philip on 08.05.2016.
 */
public class ProductModel implements Serializable{
    private String name;
    private float price;
    private String category;
    private float menge;
    private String einheit;
    private int image;

    public ProductModel(String name, float price, String category, float menge, String einheit, int image) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.menge = menge;
        this.einheit = einheit;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMenge(float menge) {
        this.menge = menge;
    }

    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() { return name; }

    public float getPrice() { return price; }

    public String getCategory() {
        return category;
    }

    public float getMenge() { return menge; }

    public String getEinheit() { return einheit; }

    public int getImage() { return this.image; }
}
