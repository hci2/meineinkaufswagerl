package univie.ac.at.meineinkaufswagerl.shoppinglist;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.adapter.ListAdapter;
import univie.ac.at.meineinkaufswagerl.fragment.AcceptDialog;
import univie.ac.at.meineinkaufswagerl.management.SerializableManager;
import univie.ac.at.meineinkaufswagerl.model.ProductModel;
import univie.ac.at.meineinkaufswagerl.model.ProductNotFittingModel;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.ShoppingListModel;
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;
import univie.ac.at.meineinkaufswagerl.standingorder.StandingOrderAdjustManual;

public class ShoppingManuallyActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AcceptDialog.OnDialogButtonEvent{

    private ArrayList<ProductModel>lebensmittel, haushalt, currentList;
    private ArrayList<Integer> prodAmounts;
    private Button buttonLebensmittel, buttonHaushalt, buttonSearch, buttonToList;
    private ListView listview;
    private ListAdapter adapter;
    private EditText editText;
    private ShoppingListModel shoppingList = null;
    private int currentPos = 0;
    private ProfileModel profileModel;
    private StandingOrderListModel standingOrderListModel;
    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    private boolean standingorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_manually);

        this.shoppingList = (ShoppingListModel)getIntent().getSerializableExtra("list");
        this.standingorder = getIntent().getBooleanExtra("standingorder",false);
        System.out.println("BOOOOLEEEEAAAN: "+this.standingorder);

        if(this.shoppingList == null)
            this.shoppingList = new ShoppingListModel();

        this.buttonHaushalt = (Button)findViewById(R.id.buttonHaushalt);
        this.buttonHaushalt.setOnClickListener(this);
        this.buttonLebensmittel = (Button)findViewById(R.id.buttonLebensmittel);
        this.buttonLebensmittel.setOnClickListener(this);
        this.buttonSearch = (Button)findViewById(R.id.buttonSearch);
        this.buttonSearch.setOnClickListener(this);
        this.buttonToList = (Button)findViewById(R.id.buttonToList);
        this.buttonToList.setOnClickListener(this);
        this.listview = (ListView)findViewById(R.id.listview);
        this.editText = (EditText)findViewById(R.id.editText);
        createProducts();
        this.adapter = new ListAdapter(this,lebensmittel,haushalt, null);
        this.adapter.showAmount(false);
        this.listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(this);
        this.currentList = this.lebensmittel;
        prodAmounts = new ArrayList<Integer>();

        if(getIntent() != null && getIntent().getExtras() != null)
            standingOrderListModel = (StandingOrderListModel)getIntent().getExtras().getSerializable(ListSupportPage.EXTRA_MESSAGE);
        if(standingOrderListModel == null)
            standingOrderListModel = new StandingOrderListModel();

        String pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();
        String filePathProfile = pathToAppFolder + File.separator + "profile.ser";
        profileModel=new ProfileModel();
        if(new File(filePathProfile).exists())
            profileModel= SerializableManager.readSerializable(filePathProfile);
    }

    private void createProducts() {
        this.lebensmittel = new ArrayList<ProductModel>();
        this.haushalt = new ArrayList<ProductModel>();
        this.lebensmittel.add(new ProductModel("Milch", 1.0f, "Lebensmittel", 1.0f, "Liter", R.drawable.milch));
        this.lebensmittel.add(new ProductModel("Brot",0.50f,"Lebensmittel",1.0f,"Kilo", R.drawable.brot));
        this.lebensmittel.add(new ProductModel("Joghurt", 0.30f, "Lebensmittel", 0.25f, "Kilo", R.drawable.joghurt));
        this.lebensmittel.add(new ProductModel("Karotten", 1.25f, "Lebensmittel", 1.0f, "Kilo", R.drawable.karotten));
        this.lebensmittel.add(new ProductModel("Apfel", 2.15f, "Lebensmittel", 2.0f, "Kilo", R.drawable.apfel));
        this.lebensmittel.add(new ProductModel("Cola", 1.85f, "Lebensmittel", 2.0f, "Liter", R.drawable.cola));
        this.haushalt.add(new ProductModel("Waschmittel", 5.20f, "Haushalt", 3.0f, "Kilo", R.drawable.waschmittel));
        this.haushalt.add(new ProductModel("Zahnpasta", 1.50f, "Haushalt", 0.20f, "Kilo", R.drawable.zahnpasta));
        this.haushalt.add(new ProductModel("Duschgel",2.0f,"Haushalt",0.03f,"Liter",R.drawable.duschgel));
        this.haushalt.add(new ProductModel("Staubsauger", 80.0f, "Haushalt", 1.0f, "Stück", R.drawable.staubsauger));
    }

    private void showDialog() {
        FragmentManager manager=getFragmentManager();
        AcceptDialog acceptDialog = new AcceptDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("productName", this.currentList.get(this.currentPos).getName());
        acceptDialog.setArguments(bundle);
        acceptDialog.show(manager, "Produkt hinzufügen?");
    }

    @Override
    public void onClick(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        if(v == this.buttonLebensmittel) {
            this.adapter.settype("lebensmittel");
            this.listview.setAdapter(adapter);
            this.currentList = this.lebensmittel;
        }
        else if(v == this.buttonHaushalt) {
            this.adapter.settype("haushalt");
            this.listview.setAdapter(adapter);
            this.currentList = this.haushalt;
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
            Toast.makeText(this,"Kein passendes Produkt gefunden !", Toast.LENGTH_LONG).show();
        }
        else if(v == this.buttonToList) {
            Intent intent;
            if(this.standingorder)
                intent= new Intent(this, StandingOrderAdjustManual.class);
            else
                intent= new Intent(this, AdjustShoppingListActivity.class);
            if(standingOrderListModel!=null){
                intent.putExtra(EXTRA_MESSAGE,standingOrderListModel);
            }
            intent.putExtra("list", this.shoppingList);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(isUserCompatibleWithProduct(this.currentList.get(position).getName())) {
            this.currentPos = position;
            showDialog();
        }
        else
            Toast.makeText(this,"Sie vertragen dieses Produkt nicht !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void DialogButtonEvent(boolean accepted, int anzahl) {
        if(accepted) {
            this.shoppingList.addProduct(this.currentList.get(this.currentPos));
            this.shoppingList.addAmount(anzahl);
        }
    }

    private boolean isUserCompatibleWithProduct(String product){
        ProductNotFittingModel productNotFittingModel=new ProductNotFittingModel();
        String[][] productUnfitList=productNotFittingModel.getProductUnfitList();
        for (int row =0;row<productUnfitList.length;row++){
            int col=0;
            if(productUnfitList[row][col].equals(product)){
                for (col =1;col<productUnfitList[row].length;col++) {
                    if(profileModel.notInIntolerance(productUnfitList[row][col]) && profileModel.notInDisease(productUnfitList[row][col]) && profileModel.notInExtra(product)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
