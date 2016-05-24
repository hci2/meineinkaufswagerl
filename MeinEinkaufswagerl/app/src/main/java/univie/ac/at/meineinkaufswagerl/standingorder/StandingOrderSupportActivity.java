package univie.ac.at.meineinkaufswagerl.standingorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;


public class StandingOrderSupportActivity extends AppCompatActivity {

    TextView infoText;
    Button sprachButton;
    Button manuellButton;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standing_order_support);




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
        Intent intent= new Intent(this, StandingOrderEditSpeechActivity.class);
        startActivity(intent);
    }

    public void goToShoppingManuallyActivity(View v) {
        Intent intent= new Intent(this, StandingOrderEditSpeechActivity.class); // TODO: StandingOrderEditManuallyActivity oder so

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
