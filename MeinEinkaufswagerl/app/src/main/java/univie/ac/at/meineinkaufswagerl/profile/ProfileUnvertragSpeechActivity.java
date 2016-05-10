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

public class ProfileUnvertragSpeechActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";

    TextView infoText;
    ImageButton speakIntolerances;
    Button readButton, nextButton;
    ListView listIntolerances;

    UserModel userModel=null;
    ProfileModel profileModel=null;
    ArrayList<String> intolerances=null;
    ArrayList<String> userIntolerances=null;
    private int position=0;

    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_unvertrag_speech);

        initializeVariables();

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        userModel= new UserModel();
        userIntolerances =new ArrayList<String>();
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

        profileModel=new ProfileModel();

    }

    public void speakIntolerances(View v){
        ttsManager.initQueue(intolerances.get(0));
        speechText();
        for(int i=1;i<intolerances.size();i++){
            ttsManager.addQueue(intolerances.get(i));
            speechText();
        }

    }


    public void goToNextPage(View v){
        // Startet auf Knopfdruck die ListSupportPage
        Intent intent= new Intent(this, ProfileDiseasesSpeechActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }

    public void readIntolerances(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, intolerances);
        listIntolerances.setAdapter(adapter);
        if(!(intolerances.size()==0)){
            for(position=0;position<intolerances.size();position++){
                ttsManager.addQueue(intolerances.get(position));
            }
        }

    }

    public void readMyIntolerances(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, userIntolerances);
        listIntolerances.setAdapter(adapter);
        if(!(userIntolerances.size()==0)){
            for(position=0;position<userIntolerances.size();position++){
                ttsManager.addQueue(userIntolerances.get(position));
            }
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
                    if(resultString.equals("ja") || resultString.equals("Ja")){
                        switch (position){
                            case 0: profileModel.setLactose();
                                userIntolerances.add(intolerances.get(position));
                                break;
                            case 1: profileModel.setGluten();
                                userIntolerances.add(intolerances.get(position));
                                break;
                            case 2: profileModel.setFructose();
                                userIntolerances.add(intolerances.get(position));
                                break;
                            case 3: profileModel.setEi();
                                userIntolerances.add(intolerances.get(position));
                                break;
                            case 4: profileModel.setFisch();
                                userIntolerances.add(intolerances.get(position));
                                break;
                            case 5: profileModel.setPhenylalanin();
                                userIntolerances.add(intolerances.get(position));
                                break;
                            case 6: profileModel.setHistamin();
                                userIntolerances.add(intolerances.get(position));
                                break;
                            case 7: profileModel.setSorbin();
                                userIntolerances.add(intolerances.get(position));
                                break;
                            case 8: profileModel.setSaccharose();
                                userIntolerances.add(intolerances.get(position));
                                break;
                            case 9: profileModel.setErdnüsse();
                                userIntolerances.add(intolerances.get(position));
                                break;
                            default: break;
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
