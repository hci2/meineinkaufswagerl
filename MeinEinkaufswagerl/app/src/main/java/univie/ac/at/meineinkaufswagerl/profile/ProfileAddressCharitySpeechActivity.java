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

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.SerializableManager;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.CharityModel;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.UserModel;

public class ProfileAddressCharitySpeechActivity extends AppCompatActivity implements Serializable {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl.MESSAGE";
    public final static String EXTRA_LIST = "univie.ac.at.meineinkaufswagerl.LIST";

    TextView infoText, charityOrganisation;
    ImageButton speakAddress, readAddress;
    Button readAdressButton, nextButton;
    ListView listViewAddress;

    CharityModel charityModel=null;
    //UserModel userModel=null;
    //ProfileModel profileModel=null;
    ArrayList<String> adressList=null;
    private int positionSpeech;

    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;
    private boolean sprachausgabe;

    UserModel userModel;
    ProfileModel profileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_address_charity_speech);

        initializeVariables();

        //Unwrap the intent and get the temporary list.
        userModel = new UserModel();
        profileModel = new ProfileModel();
        if(getIntent() != null && getIntent().getExtras() != null){
            userModel = (UserModel)getIntent().getExtras().getSerializable(ProfileDiseasesSpeechActivity.EXTRA_MESSAGE);
            profileModel = (ProfileModel)getIntent().getExtras().getSerializable(ProfileDiseasesSpeechActivity.EXTRA_LIST);
        }



        //userModel= new UserModel();
        //profileModel=new ProfileModel();
        /*
        //Unwrap the intent and get the temporary list.
        ArrayList<String> listeIntolerances = getIntent().getStringArrayListExtra(ProfileExtrasSpeechActivity.EXTRA_INTOLERANCES);
        ArrayList<String> listeKrankheiten = getIntent().getStringArrayListExtra(ProfileExtrasSpeechActivity.EXTRA_DISEASES);
        ArrayList<String> listeExtras = getIntent().getStringArrayListExtra(ProfileExtrasSpeechActivity.EXTRA_EXTRAS);
        if(listeIntolerances.size()!=0){
            for(int i=0;i<listeIntolerances.size();i++){
                ProfileModel.addUnvertraeglichkeit(listeIntolerances.get(i));
            }
        }
        if(listeKrankheiten.size()!=0){
            for(int i=0;i<listeKrankheiten.size();i++){
                ProfileModel.addKrankheit(listeKrankheiten.get(i));
            }
        }
        if(listeExtras.size()!=0){
            for(int i=0;i<listeExtras.size();i++){
                ProfileModel.addExtra(listeExtras.get(i));
            }
        }
        */


        positionSpeech=0;

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        sprachausgabe=false;


        adressList=new ArrayList<>();
        charityModel=new CharityModel();

    }



    public void readOneAddress(View v){
        if(positionSpeech==0){
            ttsManager.addQueue("In welchen Land wohnen Sie? ");
            sprachausgabe=true;
        } else if(positionSpeech==1){
            ttsManager.addQueue("In welcher Straße wohnen Sie?");
            sprachausgabe=true;
        } else if(positionSpeech==2){
            ttsManager.addQueue("Wie lautet ihre Hausnummer?");
            sprachausgabe=true;
        } else if(positionSpeech==3){
            ttsManager.addQueue("Wie lautet ihre Postleitzahl?");
            sprachausgabe=true;
        }


    }

    public void speakAddress(View v){
        speechText();
    }

    public void goToNextPage(View v){
        // Startet auf Knopfdruck die ListSupportPage
        Intent intent= new Intent(this, ProfileFinishedSpeechActivity.class);
        userModel.setCreatedSuccessfullyProfile(true);
        //TODO: Daten des Profiles serialisieren um sie persistent zu speichern
        String pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();
        String filePathProfile = pathToAppFolder +File.separator + "profile.ser";
        String filePathUser = pathToAppFolder +File.separator + "user.ser";

        //delete old profile class
        SerializableManager.removeSerializable(filePathProfile);
        SerializableManager.saveSerializable(profileModel,filePathProfile); //this,


        //delete old user class
        SerializableManager.removeSerializable(filePathUser);
        SerializableManager.saveSerializable(userModel,filePathUser); //this,

        //stellt sicher dass das Profil erfolgreich erstellt wurde, muss beim Listen erstellen geprüft werden
        startActivity(intent);
    }

    public void readAddress(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, adressList);
        listViewAddress.setAdapter(adapter);
        if(!(adressList.size()==0)){
            for(int i=0;i<adressList.size();i++){
                if(i==0){
                    ttsManager.addQueue("Land: "+adressList.get(i));
                } else if(i==1){
                    ttsManager.addQueue("Straße: "+adressList.get(i));
                }else if(i==2){
                    ttsManager.addQueue("Hausnummer: "+adressList.get(i));
                }else if(i==3){
                    ttsManager.addQueue("Postleitzahl: "+adressList.get(i));
                }else {
                    ttsManager.addQueue(adressList.get(i));
                }

            }
        }
        ttsManager.addQueue("Ihre Einkaufsliste wird von der Organisation "+userModel.getCharity()+" ausgeliefert.");

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
        if(positionSpeech==0){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_country));
        } else if(positionSpeech==1){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_street));
        } else if(positionSpeech==2){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_streetnumber));
        } else if(positionSpeech==3){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_plz));
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
                    if(sprachausgabe) {

                        switch (positionSpeech) {
                            case 0: userModel.setCountry(resultString);
                                if (adressList.size() == 4) {
                                    adressList.remove(positionSpeech);
                                }
                                adressList.add(positionSpeech, resultString);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, adressList);
                                listViewAddress.setAdapter(adapter);
                                sprachausgabe=false;
                                break;
                            case 1:
                                userModel.setStreet(resultString);
                                if (adressList.size() == 4) {
                                    adressList.remove(positionSpeech);
                                }
                                adressList.add(positionSpeech, resultString);
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, adressList);
                                listViewAddress.setAdapter(adapter1);
                                sprachausgabe=false;
                                break;
                            case 2:
                                userModel.setStreetnumber(resultString);
                                if (adressList.size() == 4) {
                                    adressList.remove(positionSpeech);
                                }
                                adressList.add(positionSpeech, resultString);
                                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, adressList);
                                listViewAddress.setAdapter(adapter2);
                                sprachausgabe=false;
                                break;
                            case 3:
                                if (!(resultString.matches("[0-9]+"))) {
                                    Toast.makeText(getApplicationContext(),
                                            getString(R.string.speech_noPlz),
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                userModel.setPostalcode(resultString);
                                if (adressList.size() == 4) {
                                    adressList.remove(positionSpeech);
                                }
                                adressList.add(positionSpeech, resultString);
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, adressList);
                                listViewAddress.setAdapter(adapter3);
                                userModel.setCharity(charityModel.getCharityForPLZ(resultString));
                                charityOrganisation.setText(userModel.getCharity());
                                sprachausgabe=false;
                                break;
                            default:
                                sprachausgabe=false;
                                break;
                        }
                        if (positionSpeech == 3) {
                            positionSpeech = 0;
                        } else {
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
        readAdressButton= (Button) findViewById(R.id.readButton);
        nextButton= (Button) findViewById(R.id.nextButton);
        speakAddress= (ImageButton) findViewById(R.id.speakButton);
        readAddress=(ImageButton) findViewById(R.id.readOneButton);
        infoText = (TextView) findViewById(R.id.infoText);
        charityOrganisation = (TextView) findViewById(R.id.charityOrganisation);
        listViewAddress = (ListView) findViewById(R.id.addressList);

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
