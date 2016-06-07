package univie.ac.at.meineinkaufswagerl.standingorder;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import univie.ac.at.meineinkaufswagerl.HomeActivity;
import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.adapter.ListAdapter;
import univie.ac.at.meineinkaufswagerl.fragment.DeleteDialog;
import univie.ac.at.meineinkaufswagerl.management.SerializableManager;
import univie.ac.at.meineinkaufswagerl.model.ProductModel;
import univie.ac.at.meineinkaufswagerl.model.ShoppingListModel;
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ListFinishedSpeechActivity;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ListSupportPage;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ShoppingManuallyActivity;

/**
 * Created by Philip on 06.06.2016.
 */
public class StandingOrderAdjustManual extends AppCompatActivity implements AdapterView.OnItemClickListener, DeleteDialog.OnDialogButtonEvent, View.OnClickListener {

    private StandingOrderListModel standingOrderListModel;
    private ListAdapter adapter;
    private ListView listview;
    private ShoppingListModel shoppingList=null;
    private int currentPos;
    private Button buttonAbsenden, buttonErgaenzen;
    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_standingorder_manual);

        this.listview = (ListView) findViewById(R.id.listShoppingFinish);
        this.buttonAbsenden = (Button) findViewById(R.id.buttonAbsenden);
        this.buttonAbsenden.setOnClickListener(this);
        this.buttonErgaenzen = (Button) findViewById(R.id.buttonErgaenzen);
        this.buttonErgaenzen.setOnClickListener(this);

        this.shoppingList = (ShoppingListModel)getIntent().getSerializableExtra("list");
        String pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();
        String filePathStandingOrder = pathToAppFolder + File.separator + "standingorder.ser";
        standingOrderListModel = new StandingOrderListModel();
        if (new File(filePathStandingOrder).exists())
            standingOrderListModel = SerializableManager.readSerializable(filePathStandingOrder);
        if(this.shoppingList == null)
            this.shoppingList = this.standingOrderListModel.getShoppingListModel();

        this.adapter = new ListAdapter(this, shoppingList.getProducts(), null, shoppingList.getAmounts());
        this.adapter.showAmount(true);
        this.listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
            String name = this.shoppingList.getProducts().get(this.currentPos).getName();
            this.shoppingList.getProducts().remove(this.currentPos);
            this.shoppingList.getAmounts().remove(this.currentPos);
            this.adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == this.buttonAbsenden) {
            standingOrderListModel.setShoppingListModel(shoppingList);
            for(int i=0; i<shoppingList.getProducts().size(); i++)
                standingOrderListModel.addTextList(shoppingList.getProducts().get(i).getName());
            standingOrderListModel.setProductList(shoppingList.getProducts());
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
        if(v == this.buttonErgaenzen) {
            Intent intent= new Intent(this, ShoppingManuallyActivity.class);
            intent.putExtra("list", this.shoppingList);
            intent.putExtra("standingorder", true);
            startActivity(intent);
        }
    }
}