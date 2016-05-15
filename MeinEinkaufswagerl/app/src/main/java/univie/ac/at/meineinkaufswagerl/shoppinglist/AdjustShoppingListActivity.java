package univie.ac.at.meineinkaufswagerl.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.adapter.ListAdapter;
import univie.ac.at.meineinkaufswagerl.model.ShoppingListModel;

public class AdjustShoppingListActivity extends AppCompatActivity implements View.OnClickListener {

    private ListAdapter adapter;
    private ShoppingListModel shoppingList;
    private ListView listview;
    private Button buttonErgaenzen;

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
    }

    @Override
    public void onClick(View v) {
        if(v == this.buttonErgaenzen) {
            Intent intent= new Intent(this, ShoppingManuallyActivity.class);
            intent.putExtra("list", this.shoppingList);
            startActivity(intent);
        }
    }
}
