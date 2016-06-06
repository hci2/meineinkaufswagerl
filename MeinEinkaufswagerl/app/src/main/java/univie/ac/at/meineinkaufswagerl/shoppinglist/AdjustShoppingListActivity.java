package univie.ac.at.meineinkaufswagerl.shoppinglist;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.adapter.ListAdapter;
import univie.ac.at.meineinkaufswagerl.fragment.DeleteDialog;
import univie.ac.at.meineinkaufswagerl.management.SerializableManager;
import univie.ac.at.meineinkaufswagerl.model.ProductModel;
import univie.ac.at.meineinkaufswagerl.model.ShoppingListModel;
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;
import univie.ac.at.meineinkaufswagerl.model.TemporaryListModel;

public class AdjustShoppingListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, DeleteDialog.OnDialogButtonEvent {

    private ListAdapter adapter;
    private ShoppingListModel shoppingList;
    private ListView listview;
    private Button buttonErgaenzen;
    private Button buttonAbsenden;
    private Button buttonDauerauftrag;
    private int currentPos;
    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    StandingOrderListModel standingOrderListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_shopping_list);
        this.listview = (ListView)findViewById(R.id.listShoppingFinish);
        this.buttonErgaenzen = (Button)findViewById(R.id.buttonErgaenzen);
        this.buttonErgaenzen.setOnClickListener(this);
        this.buttonAbsenden = (Button)findViewById(R.id.buttonAbsenden);
        this.buttonAbsenden.setOnClickListener(this);
        this.buttonDauerauftrag = (Button)findViewById(R.id.buttonDauerauftrag);
        this.buttonDauerauftrag.setOnClickListener(this);
        this.shoppingList = (ShoppingListModel)getIntent().getSerializableExtra("list");
        this.adapter = new ListAdapter(this,this.shoppingList.getProducts(),null,this.shoppingList.getAmounts());
        this.adapter.showAmount(true);
        this.listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if(getIntent() != null && getIntent().getExtras() != null)
            standingOrderListModel = (StandingOrderListModel)getIntent().getExtras().getSerializable(ListSupportPage.EXTRA_MESSAGE);
        if(standingOrderListModel == null)
            standingOrderListModel = new StandingOrderListModel();
    }

    @Override
    public void onClick(View v) {
        if(v == this.buttonErgaenzen) {
            Intent intent= new Intent(this, ShoppingManuallyActivity.class);
            intent.putExtra("list", this.shoppingList);
            startActivity(intent);
        }
        if(v == this.buttonAbsenden) {
            if(shoppingList.getProducts().isEmpty())
                Toast.makeText(this,"Sie können keine leere Einkaufsliste absenden !", Toast.LENGTH_LONG).show();
            else {
                Intent intent= new Intent(this, ListFinishedSpeechActivity.class);
                String message="Ihr Auftrag wurde erfolgreich gesendet! Die Bezahlung erfolgt bar bei Warenannahme.";
                intent.putExtra(EXTRA_MESSAGE,message);
                startActivity(intent);
            }
        }
        if(v == this.buttonDauerauftrag) {
            if(shoppingList.getProducts().isEmpty())
                Toast.makeText(this,"Sie können keine leere Einkaufsliste absenden !", Toast.LENGTH_LONG).show();
            else {
                ArrayList<ProductModel> list = standingOrderListModel.getProductList();

                for(int i=0; i<shoppingList.getProducts().size(); i++)
                    list.add(shoppingList.getProducts().get(i));
                for(int i=0; i<shoppingList.getProducts().size(); i++)
                    standingOrderListModel.addTextList(shoppingList.getProducts().get(i).getName());
                standingOrderListModel.setProductList(list);
                //Hinzufügen der Produkte
                String pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();
                String filePathStandingOrder = pathToAppFolder + File.separator + "standingorder.ser";
                //delete old standing order class
                SerializableManager.removeSerializable(filePathStandingOrder);
                SerializableManager.saveSerializable(standingOrderListModel, filePathStandingOrder);

                Intent intent= new Intent(this, ListFinishedSpeechActivity.class);
                String message="Ihr Auftrag wurde der dauerhaften Liste erfolgreich hinzugefügt! Die Bezahlung erfolgt bar bei Warenannahme.";
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        }
    }

    private void showDialog() {
        FragmentManager manager=getFragmentManager();
        DeleteDialog deleteDialog = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("productName", this.shoppingList.getProducts().get(this.currentPos).getName());
        deleteDialog.setArguments(bundle);
        deleteDialog.show(manager, "Produkt hinzufügen?");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.currentPos = position;
        showDialog();
    }

    @Override
    public void DialogButtonEvent(boolean accepted) {
        if(accepted) {
            this.shoppingList.getProducts().remove(this.currentPos);
            this.shoppingList.getAmounts().remove(this.currentPos);
            this.adapter.notifyDataSetChanged();
        }
    }
}
