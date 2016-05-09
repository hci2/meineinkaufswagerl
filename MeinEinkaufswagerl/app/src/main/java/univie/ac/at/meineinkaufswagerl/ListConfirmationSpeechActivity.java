package univie.ac.at.meineinkaufswagerl;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.TemporaryListModel;

public class ListConfirmationSpeechActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";

    private ListView txtSpeechList;
    private TemporaryListModel tempList=new TemporaryListModel();
    private ImageButton btnRead;
    private TextToSpeechManager ttsManager = null;
    private Button addStandButton;
    private Button replaceStandButton;
    private Button finishTempButton;
    private Button finishStandButton;

    //SpeechToTextManager sttManager = null;

    //For Changing
    private ImageButton btnChange;
    private ImageButton btnIndex;
    private boolean change=false;
    private boolean index=false;
    private int indexChange;

    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_confirmation_speech);


        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //initiate SpeechToTextManager
        //sttManager = new SpeechToTextManager(HomeActivity.this);

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        //initialize all the elements of the layout xml
        txtSpeechList = (ListView) findViewById(R.id.textListView);
        btnRead = (ImageButton) findViewById(R.id.btnRead);
        btnChange = (ImageButton) findViewById(R.id.btnChange);
        btnIndex = (ImageButton) findViewById(R.id.btnIndex);
        addStandButton = (Button) findViewById(R.id.addStandButton);
        replaceStandButton = (Button) findViewById(R.id.replaceStandButton);
        finishTempButton = (Button) findViewById(R.id.finishTempButton);
        finishStandButton = (Button) findViewById(R.id.finishStandButton);

        // This is used to Change an index of the List
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change=true;
                speechText();
            }
        });
        // This is used to get the index for changing a line of the List
        btnIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=true;
                speechText();
            }
        });


        // This is used for TextToSpeech
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //String text = txtTextView.getText().toString();
                ArrayList<String> textList=tempList.getTextList();
                if(!(textList.size()==0)){
                    ttsManager.initQueue(textList.get(0));
                    for(int i=1;i<textList.size();i++){
                        ttsManager.addQueue(textList.get(i));
                    }
                }
            }
        });
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

    //TODO: Sinnvolle Inhalte in die vier Methoden einfügen

    public void addStandButton(View v) {
        Intent intent= new Intent(this, ListConfirmationSpeechActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }

    public void replaceStandButton(View v) {
        Intent intent= new Intent(this, ListConfirmationSpeechActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
    public void goToFinishTempPage(View v) {
        Intent intent= new Intent(this, ListConfirmationSpeechActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
    public void goToFinishStandPage(View v) {
        Intent intent= new Intent(this, ListConfirmationSpeechActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }

}
