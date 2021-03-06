package univie.ac.at.meineinkaufswagerl.profile;

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
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.UserModel;

public class ProfileNameSpeechActivity extends AppCompatActivity implements Serializable {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl.MESSAGE";
    public final static String EXTRA_LIST = "univie.ac.at.meineinkaufswagerl.LIST";

    TextView infoText, firstnameText, lastnameText;
    ImageButton firstnameMouth, lastnameMouth;
    Button readButton, nextButton;
    //UserModel userModel=null;

    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private boolean firstname=false;
    private boolean lastname=false;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    UserModel userModel;
    ProfileModel profileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_name_speech);

        initializeVariables();

        //Unwrap the intent and get the temporary list.
        userModel = new UserModel();
        profileModel = new ProfileModel();
        if(getIntent() != null && getIntent().getExtras() != null){
            userModel = (UserModel)getIntent().getExtras().getSerializable(ProfileActivity.EXTRA_MESSAGE);
            profileModel = (ProfileModel)getIntent().getExtras().getSerializable(ProfileActivity.EXTRA_LIST);
        }

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        //userModel= new UserModel();
        firstnameText.setText(userModel.getFirstname());
        lastnameText.setText(userModel.getLastname());
    }

    public void goToNextPage(View v){
        // Startet auf Knopfdruck die ListSupportPage
        Intent intent= new Intent(this, ProfileUnvertragSpeechActivity.class);
        if(profileModel!=null){
            intent.putExtra(EXTRA_LIST,profileModel);
        }
        if(userModel!=null){
            intent.putExtra(EXTRA_MESSAGE,userModel);
        }
        startActivity(intent);
    }

    public void speakLastname(View v){
        lastname=true;
        speechText();
    }

    public void speakFirstname(View v){
        firstname=true;
        speechText();
    }

    public void readNames(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        String text = firstnameText.getText().toString();
        ttsManager.initQueue(text);
        text =lastnameText.getText().toString();
        ttsManager.addQueue(text);
    }

    public void readInfoText(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        String text = infoText.getText().toString();
        ttsManager.initQueue(text);
    }

    // This is used for SpeechToText
    private void speechText(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.GERMAN);
        if(firstname && !lastname){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_firstname));
        } else if(!firstname && lastname){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_lastname));
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_defaultcase_name));
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
                    if(firstname && !lastname){
                        userModel.setFirstname(resultString);
                        firstnameText.setText(resultString);
                        ttsManager.addQueue("Es wurde erfolgreich der Vorname " +resultString+" hinzugefügt!");
                        firstname=false;
                    } else if(!firstname && lastname){
                        userModel.setLastname(resultString);
                        lastnameText.setText(resultString);
                        ttsManager.addQueue("Es wurde erfolgreich der Nachname " +resultString+" hinzugefügt!");
                        lastname=false;
                    }
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




    private void initializeVariables() {

        readButton= (Button) findViewById(R.id.profilebutton);
        nextButton= (Button) findViewById(R.id.nextButton);
        firstnameMouth= (ImageButton) findViewById(R.id.firstnameMouth);
        lastnameMouth= (ImageButton) findViewById(R.id.lastnameMouth);
        infoText = (TextView) findViewById(R.id.infoText);
        firstnameText = (TextView) findViewById(R.id.firstnameView);
        lastnameText = (TextView) findViewById(R.id.lastnameView);

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
