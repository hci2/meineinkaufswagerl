package univie.ac.at.meineinkaufswagerl.profile;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import univie.ac.at.meineinkaufswagerl.R;

public class ProfileDiseasesManualActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    Button buttonConitnue;
    ListView listDiseases, listUnvertrag;
    ArrayList<String> diseases,intolerances;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_diseases_manual);

        listDiseases = (ListView)findViewById(R.id.listDiseases);
        diseases=new ArrayList<>();
        diseases.add("Diabetes");
        diseases.add("Morbus Crohn");
        diseases.add("Gicht");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, diseases);
        listDiseases.setAdapter(adapter1);
        listDiseases.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listDiseases.setOnItemClickListener(this);

        listUnvertrag= (ListView)findViewById(R.id.listUnvertrag);
        intolerances=new ArrayList<>();
        intolerances.add("Lactose");
        intolerances.add("Gluten");
        intolerances.add("Fructose");
        intolerances.add("Ei");
        intolerances.add("Fisch");
        intolerances.add("Phenylalanin");
        intolerances.add("Histamin");
        intolerances.add("Sorbin");
        intolerances.add("Saccharose");
        intolerances.add("Erdnüsse");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, intolerances);
        listUnvertrag.setAdapter(adapter2);
        listUnvertrag.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listUnvertrag.setOnItemClickListener(this);
        buttonConitnue = (Button)findViewById(R.id.buttonContinue);
        buttonConitnue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==buttonConitnue){
            
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
