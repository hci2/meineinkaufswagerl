package univie.ac.at.meineinkaufswagerl.profile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.TemporaryListModel;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ListConfirmationSpeechActivity;

public class ProfileExtrasSpeechActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    private TextView infoText;
    private ImageButton btnSpeak;
    private ListView extraListe;
    private ImageButton btnRead;
    private TextToSpeechManager ttsManager = null;
    private Button btnNext;
    //SpeechToTextManager sttManager = null;

    private ProfileModel profileModel;

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
        setContentView(R.layout.activity_profile_extras_speech);

        //Unwrap the intent and get the temporary list.
        profileModel=new ProfileModel();
        ArrayList<String> listeIntolerances = getIntent().getStringArrayListExtra(ProfileDiseasesSpeechActivity.EXTRA_INTOLERANCES);
        ArrayList<String> listeKrankheiten = getIntent().getStringArrayListExtra(ProfileDiseasesSpeechActivity.EXTRA_DISEASES);
        for(int i=0;i<listeIntolerances.size();i++){
            profileModel.addUnvertraeglichkeit(listeIntolerances.get(i));
        }
        for(int i=0;i<listeKrankheiten.size();i++){
            profileModel.addKrankheit(listeKrankheiten.get(i));
        }


        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //initiate SpeechToTextManager
        //sttManager = new SpeechToTextManager(HomeActivity.this);

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        //initialize all the elements of the layout xml
        infoText = (TextView) findViewById(R.id.infoText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        extraListe = (ListView) findViewById(R.id.extraListe);
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
                ArrayList<String> textList=profileModel.getExtraListe();
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
                    getString(R.string.speech_extra));
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
                        if(profileModel.getExtraListe().size()==0){
                            change=false;
                            index=false;
                            return;
                        }
                        profileModel.removeExtra(indexChange); //Integer.parseInt(number)
                        profileModel.changeExtra(resultString, indexChange); //resultString.substring(resultString.lastIndexOf(number)+1),Integer.parseInt(number)
                        change=false;
                        index=false;
                    } else {
                        profileModel.addExtra(resultString);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, profileModel.getExtraListe());
                    extraListe.setAdapter(adapter);

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
        Intent intent= new Intent(this, ProfileCharitySpeechActivity.class);
        startActivity(intent);
    }

    public void readExtraInfoText(View v) {
        ttsManager.initQueue(infoText.getText().toString());
    }
}
