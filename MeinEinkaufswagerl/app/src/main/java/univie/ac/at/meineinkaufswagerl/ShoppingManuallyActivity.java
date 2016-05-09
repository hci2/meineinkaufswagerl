package univie.ac.at.meineinkaufswagerl;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import univie.ac.at.meineinkaufswagerl.adapter.ListAdapter;
import univie.ac.at.meineinkaufswagerl.fragment.AcceptDialog;
import univie.ac.at.meineinkaufswagerl.model.ProductModel;

public class ShoppingManuallyActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    ArrayList<ProductModel>lebensmittel, haushalt;
    Button buttonLebensmittel, buttonHaushalt, buttonSearch;
    ListView listview;
    ListAdapter adapter;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_manually);
        this.buttonHaushalt = (Button)findViewById(R.id.buttonHaushalt);
        this.buttonHaushalt.setOnClickListener(this);
        this.buttonLebensmittel = (Button)findViewById(R.id.buttonLebensmittel);
        this.buttonLebensmittel.setOnClickListener(this);
        this.buttonSearch = (Button)findViewById(R.id.buttonSearch);
        this.buttonSearch.setOnClickListener(this);
        this.listview = (ListView)findViewById(R.id.listview);
        this.editText = (EditText)findViewById(R.id.editText);
        createProducts();
        this.adapter = new ListAdapter(this,lebensmittel,haushalt);
        this.listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(this);
    }

    private void createProducts() {
        this.lebensmittel = new ArrayList<ProductModel>();
        this.haushalt = new ArrayList<ProductModel>();
        this.lebensmittel.add(new ProductModel("Milch", 1.0f, "Lebensmittel", 1.0f, "Liter", R.drawable.milch));
        this.lebensmittel.add(new ProductModel("Brot",0.50f,"Lebensmittel",1.0f,"Kilo", R.drawable.brot));
        this.lebensmittel.add(new ProductModel("Joghurt", 0.30f, "Lebensmittel", 0.25f, "Kilo", R.drawable.joghurt));
        this.lebensmittel.add(new ProductModel("Karotten",1.25f,"Lebensmittel",1.0f,"Kilo",R.drawable.karotten));
        this.lebensmittel.add(new ProductModel("Äpfel", 2.15f, "Lebensmittel", 2.0f, "Kilo", R.drawable.apfel));
        this.lebensmittel.add(new ProductModel("Cola",1.85f,"Lebensmittel",2.0f,"Liter",R.drawable.cola));
        this.haushalt.add(new ProductModel("Waschmittel",5.20f,"Haushalt",3.0f,"Kilo",R.drawable.waschmittel));
        this.haushalt.add(new ProductModel("Zahnpasta", 1.50f, "Haushalt", 0.20f, "Kilo", R.drawable.zahnpasta));
        this.haushalt.add(new ProductModel("Duschgel",2.0f,"Haushalt",0.03f,"Liter",R.drawable.duschgel));
        this.haushalt.add(new ProductModel("Staubsauger", 80.0f, "Haushalt", 1.0f, "Stück", R.drawable.staubsauger));
    }

    public void showDialog() {
        FragmentManager manager=getFragmentManager();
        AcceptDialog acceptDialog = new AcceptDialog();
        acceptDialog.show(manager, "Produkt hinzufügen?");
    }

    @Override
    public void onClick(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        if(v == this.buttonLebensmittel) {
            this.adapter.settype("lebensmittel");
            this.listview.setAdapter(adapter);
        }
        else if(v == this.buttonHaushalt) {
            this.adapter.settype("haushalt");
            this.listview.setAdapter(adapter);
        }
        else if(v == this.buttonSearch) {
            String search = this.editText.getText().toString();
            for(int i=0; i<this.lebensmittel.size(); i++)
                if(this.lebensmittel.get(i).getName().equals(search)){
                    this.listview.smoothScrollToPosition(i);
                    return;
                }
            for(int i=0; i<this.haushalt.size(); i++)
                if(this.haushalt.get(i).getName().equals(search)){
                    this.listview.smoothScrollToPosition(i);
                    return;
                }
            Toast.makeText(this,"Kein passendes Produkt gefunden !", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog();
    }
}
