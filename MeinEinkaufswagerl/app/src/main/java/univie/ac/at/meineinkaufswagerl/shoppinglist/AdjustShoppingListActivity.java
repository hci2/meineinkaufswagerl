package univie.ac.at.meineinkaufswagerl.shoppinglist;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.adapter.ListAdapter;
import univie.ac.at.meineinkaufswagerl.fragment.DeleteDialog;
import univie.ac.at.meineinkaufswagerl.model.ShoppingListModel;

public class AdjustShoppingListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, DeleteDialog.OnDialogButtonEvent {

    private ListAdapter adapter;
    private ShoppingListModel shoppingList;
    private ListView listview;
    private Button buttonErgaenzen;
    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_shopping_list);
        this.listview = (ListView)findViewById(R.id.listShoppingFinish);
        this.buttonErgaenzen = (Button)findViewById(R.id.buttonErgaenzen);
        this.buttonErgaenzen.setOnClickListener(this);
        this.shoppingList = (ShoppingListModel)getIntent().getSerializableExtra("list");
        this.adapter = new ListAdapter(this,this.shoppingList.getProducts(),null,this.shoppingList.getAmounts());
        this.adapter.showAmount(true);
        this.listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void onClick(View v) {
        if(v == this.buttonErgaenzen) {
            Intent intent= new Intent(this, ShoppingManuallyActivity.class);
            intent.putExtra("list", this.shoppingList);
            startActivity(intent);
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
