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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.UserModel;

public class ProfileDiseasesSpeechActivity extends AppCompatActivity {
    public final static String EXTRA_INTOLERANCES = "univie.ac.at.meineinkaufswagerl";
    public final static String EXTRA_DISEASES = "univie.ac.at.meineinkaufswagerl";


    TextView infoText;
    ImageButton speakDiseases, readDiseases;
    Button readButton, nextButton;
    ListView listDiseases;

    UserModel userModel=null;
    ProfileModel profileModel=null;
    ArrayList<String> diseases=null;
    private int positionSpeech;

    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_diseases_speech);

        initializeVariables();

        //Unwrap the intent and get the temporary list.
        profileModel=new ProfileModel();
        ArrayList<String> listeIntolerances = getIntent().getStringArrayListExtra(ProfileUnvertragSpeechActivity.EXTRA_MESSAGE);
        for(int i=0;i<listeIntolerances.size();i++){
            profileModel.addUnvertraeglichkeit(listeIntolerances.get(i));
        }

        positionSpeech=0;

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        userModel= new UserModel();
        diseases=new ArrayList<>();
        diseases.add("Diabetes");
        diseases.add("Morbus Crohn");
        diseases.add("Gicht");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, diseases);
        listDiseases.setAdapter(adapter);

        profileModel=new ProfileModel();

    }



    public void readOneDisease(View v){
        ttsManager.addQueue("Haben Sie "+diseases.get(positionSpeech)+"?");

    }

    public void speakDiseases(View v){
        speechText();
    }

    /*
    Test für Kombination von Sprachausgabe mit danach folgender Spracheingabe und damit Intoleranzenliste erstellen
    public void speakIntolerances(View v){
        positionSpeech=0;
        speechText();
        ttsManager.initQueue(intolerances.get(positionSpeech));
        ttsManager.makePause();
        for(positionSpeech=1;positionSpeech<intolerances.size();positionSpeech++){
            speechText();
            ttsManager.addQueue(intolerances.get(positionSpeech));
            ttsManager.makePause();
        }
    }

*/
    public void goToNextPage(View v){
        // Startet auf Knopfdruck die ListSupportPage
        Intent intent= new Intent(this, ProfileExtrasSpeechActivity.class);
        intent.putExtra(EXTRA_INTOLERANCES,profileModel.getUnvertraeglichkeitenListe());
        intent.putExtra(EXTRA_DISEASES, profileModel.getKrankheitenListe());
        startActivity(intent);
    }

    public void readDiseases(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, diseases);
        listDiseases.setAdapter(adapter);
        if(!(diseases.size()==0)){
            for(int i=0;i<diseases.size();i++){
                ttsManager.addQueue(diseases.get(i));
            }
        }

    }

    public void readMyDiseases(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, profileModel.getKrankheitenListe());
        listDiseases.setAdapter(adapter);
        if(!(profileModel.getKrankheitenListe().size()==0)){
            for(int i=0;i<profileModel.getKrankheitenListe().size();i++){
                ttsManager.addQueue(profileModel.getKrankheitenListe().get(i));
            }
        } else {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, diseases);
            listDiseases.setAdapter(adapter1);
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_noOwnDiseases),
                    Toast.LENGTH_SHORT).show();

        }
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_diseases));
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
                    if(resultString.equals("ja") || resultString.equals("Ja") || resultString.contains("ja") || resultString.contains("Ja")){
                        switch (positionSpeech){
                            case 0: profileModel.setDiabetes(1);
                                profileModel.addKrankheit(diseases.get(positionSpeech));
                                break;
                            case 1: profileModel.setMorbus(1);
                                profileModel.addKrankheit(diseases.get(positionSpeech));
                                break;
                            case 2: profileModel.setGicht(1);
                                profileModel.addKrankheit(diseases.get(positionSpeech));
                                break;
                            default: break;
                        }
                        if(positionSpeech==2){
                            positionSpeech=0;
                        } else{
                            positionSpeech++;
                        }
                    } else if(resultString.equals("nein") || resultString.equals("Nein") || resultString.contains("nein") || resultString.contains("Nein")){
                        switch (positionSpeech){
                            case 0: profileModel.setDiabetes(0);
                                break;
                            case 1: profileModel.setMorbus(0);
                                break;
                            case 2: profileModel.setGicht(0);
                                break;
                            default: break;
                        }
                        if(positionSpeech==2){
                            positionSpeech=0;
                        } else{
                            positionSpeech++;
                        }
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
        nextButton= (Button) findViewById(R.id.shoppingbutton);
        speakDiseases= (ImageButton) findViewById(R.id.speakButton);
        readDiseases=(ImageButton) findViewById(R.id.readOneButton);
        infoText = (TextView) findViewById(R.id.infoText);
        listDiseases = (ListView) findViewById(R.id.listDiseases);

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