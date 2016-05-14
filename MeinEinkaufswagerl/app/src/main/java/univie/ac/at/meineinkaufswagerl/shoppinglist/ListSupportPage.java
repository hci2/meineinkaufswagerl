package univie.ac.at.meineinkaufswagerl.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import univie.ac.at.meineinkaufswagerl.HomeActivity;
import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;

public class ListSupportPage extends AppCompatActivity implements Serializable {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    TextView infoText;
    Button sprachButton;
    Button manuellButton;

    StandingOrderListModel standingOrderListModel;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_support_page);

        //Unwrap the intent and get the temporary list.
        standingOrderListModel = new StandingOrderListModel();
        standingOrderListModel = (StandingOrderListModel)getIntent().getExtras().getSerializable(HomeActivity.EXTRA_MESSAGE);
        /*
        ArrayList<String> stringList = getIntent().getStringArrayListExtra(ListCreateSpeechActivity.EXTRA_MESSAGE);
        for(int i=0;i<stringList.size();i++){
            tempList.addTextList(stringList.get(i));
        }
        */

        initializeVariables();

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);
    }

    public void initializeVariables() {
        sprachButton = (Button) findViewById(R.id.sprachButton);
        manuellButton = (Button) findViewById(R.id.manuellButton);
        infoText = (TextView) findViewById(R.id.infoText);

    }

    public void goToSpeechSupportedList(View v) {
        Intent intent= new Intent(this, ListSpeechIntroActivity.class);
        if(standingOrderListModel!=null){
            intent.putExtra(EXTRA_MESSAGE,standingOrderListModel);
        }
        startActivity(intent);
    }

    public void goToTextSupportedList(View v) {
        Intent intent= new Intent(this, ListCreateTextActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
    public void readInfoText(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        String text = infoText.getText().toString();
        ttsManager.initQueue(text);
    }

    /**
     * Releases the resources used by the TextToSpeech engine.
     */
    // This is used for TextToSpeech
    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }
}
