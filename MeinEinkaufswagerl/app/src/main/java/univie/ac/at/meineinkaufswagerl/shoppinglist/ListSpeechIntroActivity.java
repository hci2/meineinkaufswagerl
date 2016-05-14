package univie.ac.at.meineinkaufswagerl.shoppinglist;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;

public class ListSpeechIntroActivity extends AppCompatActivity implements Serializable {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    TextView infoText;
    Button nextButton;
    Button readButton;

    StandingOrderListModel standingOrderListModel;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_speech_intro);

        //Unwrap the intent and get the temporary list.
        standingOrderListModel = new StandingOrderListModel();
        standingOrderListModel = (StandingOrderListModel)getIntent().getExtras().getSerializable(ListSupportPage.EXTRA_MESSAGE);


        initializeVariables();

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        // This is used for TextToSpeech
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String text = infoText.getText().toString();
                ttsManager.initQueue(text);
            }
        });
    }

    public void goToListCreateSpeechActivity(View v) {
        Intent intent= new Intent(this, ListCreateSpeechActivity.class);
        if(standingOrderListModel!=null){
            intent.putExtra(EXTRA_MESSAGE,standingOrderListModel);
        }
        startActivity(intent);
    }
    private void initializeVariables() {

        nextButton= (Button) findViewById(R.id.nextButton);
        readButton= (Button) findViewById(R.id.readButton);
        infoText = (TextView) findViewById(R.id.infoText);

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
