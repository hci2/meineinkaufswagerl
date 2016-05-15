package univie.ac.at.meineinkaufswagerl.shoppinglist;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;
import univie.ac.at.meineinkaufswagerl.model.TemporaryListModel;

public class ListCreateSpeechActivity extends AppCompatActivity implements Serializable {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    public final static String EXTRA_LIST = "univie.ac.at.meineinkaufswagerl";
    private TextView lastInputText;
    private ImageButton btnSpeak;
    private ListView txtSpeechList;
    private TemporaryListModel tempList=new TemporaryListModel();
    private ImageButton btnRead;
    private TextToSpeechManager ttsManager = null;
    private Button btnNext;
    //SpeechToTextManager sttManager = null;

    //For Changing
    private ImageButton btnChange;
    private ImageButton btnIndex;
    private boolean change=false;
    private boolean index=false;
    private int indexChange;

    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    StandingOrderListModel standingOrderListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_create_speech);

        //Unwrap the intent and get the temporary list.
        standingOrderListModel = new StandingOrderListModel();
        if(getIntent() != null && getIntent().getExtras() != null){
            standingOrderListModel = (StandingOrderListModel)getIntent().getExtras().getSerializable(ListSupportPage.EXTRA_MESSAGE);
        }



        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //initiate SpeechToTextManager
        //sttManager = new SpeechToTextManager(HomeActivity.this);

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        //initialize all the elements of the layout xml
        lastInputText = (TextView) findViewById(R.id.lastInputText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        txtSpeechList = (ListView) findViewById(R.id.extraListe);
        btnRead = (ImageButton) findViewById(R.id.btnRead);
        btnChange = (ImageButton) findViewById(R.id.btnChange);
        btnIndex = (ImageButton) findViewById(R.id.btnIndex);
        btnNext = (Button) findViewById(R.id.nextButton);

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

        //This is used for SpeechToText
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechText();
                //promptSpeechInput();
                /*TODO: Trying to use a own class for SpeechToText
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                sttManager.init(intent);
                String spokenWords=sttManager.getSpokenWords();
                txtTextView.setText(spokenWords);
                list.addTextList(spokenWords);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_dropdown_item, list.getTextList());
                txtSpeechList.setAdapter(adapter);
                */
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

    // This is used for SpeechToText
    private void speechText(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.GERMAN);
        if(!change && index){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_index));
        } else if(change && index){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_change));
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_prompt));
        }
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // This is used for SpeechToText
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String resultString=result.get(0);
                    if(!change && index){
                        try{
                            indexChange=Integer.parseInt(resultString)-1;
                        } catch(Exception pe){
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.speech_index_missunderstand),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else if(change && index){
                        //To get just the number as String
                        //String number=resultString.replaceAll("[^0-9]", "");
                        if(tempList.getTextList().size()==0){
                            change=false;
                            index=false;
                            return;
                        }
                        tempList.removeTextListElement(indexChange); //Integer.parseInt(number)
                        tempList.changeTextListElement(resultString, indexChange); //resultString.substring(resultString.lastIndexOf(number)+1),Integer.parseInt(number)
                        lastInputText.setText("Letzte Änderung: "+(indexChange+1)+" Zeile. "+resultString);
                        change=false;
                        index=false;
                    } else {
                        lastInputText.setText("Letzte Eingabe: "+resultString);
                        tempList.addTextList(resultString);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempList.getTextList());
                    txtSpeechList.setAdapter(adapter);
                }
                break;
            }

        }
    }

    // This is used for SpeechToText
    //check TTS version on executing device
    private void checkSpeech() {
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    public void goToNextPage(View v) {
        Intent intent= new Intent(this, ListConfirmationSpeechActivity.class);
        intent.putExtra(EXTRA_LIST,tempList.getTextList());
        if(standingOrderListModel!=null){
            intent.putExtra(EXTRA_MESSAGE,standingOrderListModel);
        }
        startActivity(intent);
    }
}
