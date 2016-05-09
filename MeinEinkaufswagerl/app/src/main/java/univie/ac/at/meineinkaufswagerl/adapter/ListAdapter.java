package univie.ac.at.meineinkaufswagerl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.model.ProductModel;

/**
 * Created by Philip on 09.05.2016.
 * Reference to: http://www.vogella.com/tutorials/AndroidListView/article.html
 */

public class ListAdapter extends BaseAdapter{
    private ArrayList<ProductModel> lebensmittel, haushalt;
    private LayoutInflater mInflater;
    private Context context;
    private String type="lebensmittel";

    public ListAdapter(Context context, ArrayList<ProductModel> lebensmittel, ArrayList<ProductModel> haushalt) {
        this.context = context;
        this.lebensmittel = lebensmittel;
        this.haushalt = haushalt;
        this.mInflater = LayoutInflater.from(context);
    }

    public void settype(String type) {
        this.type = type;
    }
    @Override
    public int getCount() {
        if(type.equals("lebensmittel"))
            return lebensmittel.size();
        else
            return haushalt.size();
    }

    @Override
    public Object getItem(int position) {
        if(type.equals("lebensmittel"))
            return lebensmittel.get(position);
        else
            return haushalt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linkrow;
        ArrayList<ProductModel> products;
        if(type.equals("lebensmittel"))
            products = this.lebensmittel;
        else
            products = this.haushalt;

        linkrow = mInflater.inflate(R.layout.listitem, null);
        ImageView imageProduct = (ImageView)linkrow.findViewById(R.id.imageProduct);
        TextView textName = (TextView)linkrow.findViewById(R.id.textName);
        TextView textMenge = (TextView)linkrow.findViewById(R.id.textMenge);
        TextView textPrice = (TextView)linkrow.findViewById(R.id.textPrice);

        imageProduct.setImageResource(products.get(position).getImage());
        textName.setText(products.get(position).getName());
        textMenge.setText(products.get(position).getMenge() + " " + products.get(position).getEinheit());
        textPrice.setText(String.valueOf(products.get(position).getPrice()) + " Euro");

        linkrow.setPadding(0,0,0,0);
        return linkrow;
    }
}
