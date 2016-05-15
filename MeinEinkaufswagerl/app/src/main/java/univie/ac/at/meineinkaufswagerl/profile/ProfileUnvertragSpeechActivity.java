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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.UserModel;

public class ProfileUnvertragSpeechActivity extends AppCompatActivity implements Serializable{

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    public final static String EXTRA_LIST = "univie.ac.at.meineinkaufswagerl";

    TextView infoText;
    ImageButton speakIntolerances, readIntolerances;
    Button readButton, nextButton;
    ListView listIntolerances;

    //UserModel userModel=null;
    //ProfileModel profileModel=null;
    ArrayList<String> intolerances=null;
    private int positionSpeech;

    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    UserModel userModel;
    ProfileModel profileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_unvertrag_speech);

        initializeVariables();

        //Unwrap the intent and get the temporary list.
        userModel = new UserModel();
        profileModel = new ProfileModel();
        if(getIntent() != null && getIntent().getExtras() != null){
            userModel = (UserModel)getIntent().getExtras().getSerializable(ProfileNameSpeechActivity.EXTRA_MESSAGE);
            profileModel = (ProfileModel)getIntent().getExtras().getSerializable(ProfileNameSpeechActivity.EXTRA_LIST);
        }


        positionSpeech=0;

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        //userModel= new UserModel();
        //profileModel=new ProfileModel();

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, intolerances);
        listIntolerances.setAdapter(adapter);

    }



    public void readOneIntolerance(View v){
        ttsManager.addQueue("Vertragen Sie "+intolerances.get(positionSpeech)+"?");

    }

    public void speakIntolerances(View v){
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
        Intent intent= new Intent(this, ProfileDiseasesSpeechActivity.class);
        //intent.putExtra(EXTRA_MESSAGE,ProfileModel.getUnvertraeglichkeitenListe());
        if(profileModel!=null){
            intent.putExtra(EXTRA_LIST,profileModel);
        }
        if(userModel!=null){
            intent.putExtra(EXTRA_MESSAGE,userModel);
        }
        startActivity(intent);
    }

    public void readIntolerances(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, intolerances);
        listIntolerances.setAdapter(adapter);
        if(!(intolerances.size()==0)){
            for(int i=0;i<intolerances.size();i++){
                ttsManager.addQueue(intolerances.get(i));
            }
        }

    }

    public void readMyIntolerances(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, profileModel.getUnvertraeglichkeitenListe());
        listIntolerances.setAdapter(adapter);
        if(!(profileModel.getUnvertraeglichkeitenListe().size()==0)){
            for(int i=0;i<profileModel.getUnvertraeglichkeitenListe().size();i++){
                ttsManager.addQueue(profileModel.getUnvertraeglichkeitenListe().get(i));
            }
        } else {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, intolerances);
            listIntolerances.setAdapter(adapter1);
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_noOwnIntolerances),
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_intolerances));
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
                            case 0: profileModel.setLactose(0);
                                break;
                            case 1: profileModel.setGluten(0);
                                break;
                            case 2: profileModel.setFructose(0);
                                break;
                            case 3: profileModel.setEi(0);
                                break;
                            case 4: profileModel.setFisch(0);
                                break;
                            case 5: profileModel.setPhenylalanin(0);
                                break;
                            case 6: profileModel.setHistamin(0);
                                break;
                            case 7: profileModel.setSorbin(0);
                                break;
                            case 8: profileModel.setSaccharose(0);
                                break;
                            case 9: profileModel.setErdnüsse(0);
                                break;
                            default: break;
                        }
                        if(positionSpeech==9){
                            positionSpeech=0;
                        } else{
                            positionSpeech++;
                        }
                    } else if(resultString.equals("nein") || resultString.equals("Nein") || resultString.contains("nein") || resultString.contains("Nein")){
                        switch (positionSpeech){
                            case 0: profileModel.setLactose(1);
                                profileModel.addUnvertraeglichkeit(intolerances.get(positionSpeech));
                                break;
                            case 1: profileModel.setGluten(1);
                                profileModel.addUnvertraeglichkeit(intolerances.get(positionSpeech));
                                break;
                            case 2: profileModel.setFructose(1);
                                profileModel.addUnvertraeglichkeit(intolerances.get(positionSpeech));
                                break;
                            case 3: profileModel.setEi(1);
                                profileModel.addUnvertraeglichkeit(intolerances.get(positionSpeech));
                                break;
                            case 4: profileModel.setFisch(1);
                                profileModel.addUnvertraeglichkeit(intolerances.get(positionSpeech));
                                break;
                            case 5: profileModel.setPhenylalanin(1);
                                profileModel.addUnvertraeglichkeit(intolerances.get(positionSpeech));
                                break;
                            case 6: profileModel.setHistamin(1);
                                profileModel.addUnvertraeglichkeit(intolerances.get(positionSpeech));
                                break;
                            case 7: profileModel.setSorbin(1);
                                profileModel.addUnvertraeglichkeit(intolerances.get(positionSpeech));
                                break;
                            case 8: profileModel.setSaccharose(1);
                                profileModel.addUnvertraeglichkeit(intolerances.get(positionSpeech));
                                break;
                            case 9: profileModel.setErdnüsse(1);
                                profileModel.addUnvertraeglichkeit(intolerances.get(positionSpeech));
                                break;
                            default: break;
                        }
                        if(positionSpeech==9){
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
        speakIntolerances= (ImageButton) findViewById(R.id.speakButton);
        readIntolerances=(ImageButton) findViewById(R.id.readOneButton);
        infoText = (TextView) findViewById(R.id.infoText);
        listIntolerances = (ListView) findViewById(R.id.listIntolerances);

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
