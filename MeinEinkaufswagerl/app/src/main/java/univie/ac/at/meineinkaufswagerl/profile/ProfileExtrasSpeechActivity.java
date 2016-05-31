package univie.ac.at.meineinkaufswagerl.profile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
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

public class ProfileExtrasSpeechActivity extends AppCompatActivity implements Serializable{

    //public final static String EXTRA_INTOLERANCES = "univie.ac.at.meineinkaufswagerl";
    //public final static String EXTRA_DISEASES = "univie.ac.at.meineinkaufswagerl";
    //public final static String EXTRA_EXTRAS = "univie.ac.at.meineinkaufswagerl";

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl.MESSAGE";
    public final static String EXTRA_LIST = "univie.ac.at.meineinkaufswagerl.LIST";

    private TextView infoText;
    private ImageButton btnSpeak;
    private ListView extraListe;
    private ImageButton btnRead;
    private TextToSpeechManager ttsManager = null;
    private Button btnNext;
    //SpeechToTextManager sttManager = null;

    //private ProfileModel profileModel;

    //For Changing
    private ImageButton btnRemove;
    private boolean remove=false;

    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    UserModel userModel;
    ProfileModel profileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_extras_speech);

        //Unwrap the intent and get the temporary list.
        userModel = new UserModel();
        profileModel = new ProfileModel();
        if(getIntent() != null && getIntent().getExtras() != null){
            userModel = (UserModel)getIntent().getExtras().getSerializable(ProfileDiseasesSpeechActivity.EXTRA_MESSAGE);
            profileModel = (ProfileModel)getIntent().getExtras().getSerializable(ProfileDiseasesSpeechActivity.EXTRA_LIST);
        }

        //profileModel=new ProfileModel();
        /*
        //Unwrap the intent and get the temporary list.
        ArrayList<String> listeIntolerances = getIntent().getStringArrayListExtra(ProfileDiseasesSpeechActivity.EXTRA_INTOLERANCES);
        ArrayList<String> listeKrankheiten = getIntent().getStringArrayListExtra(ProfileDiseasesSpeechActivity.EXTRA_DISEASES);
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
        */


        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //initiate SpeechToTextManager
        //sttManager = new SpeechToTextManager(HomeActivity.this);

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        remove=false;

        //initialize all the elements of the layout xml
        infoText = (TextView) findViewById(R.id.infoText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        extraListe = (ListView) findViewById(R.id.extraListe);
        btnRead = (ImageButton) findViewById(R.id.btnRead);
        btnRemove = (ImageButton) findViewById(R.id.btnRemove);
        btnNext = (Button) findViewById(R.id.nextButton);

        // This is used to remove an element at the spoken index of the List
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove=true;
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
                } else{
                    ttsManager.addQueue("Hier können Sie etwas Zusätzliches angeben, dass sie nicht vertragen wie zum Beispiel Bananen");
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
        if(remove){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_remove));
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
                    if(remove){
                        if(profileModel.getExtraListe().size()==0){
                            remove=false;
                            return;
                        } else{
                            try{
                                int index;
                                if(isInt(resultString)){
                                    index=Integer.parseInt(resultString)-1;
                                    profileModel.removeExtra(index);
                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            getString(R.string.speech_index_missunderstand),
                                            Toast.LENGTH_SHORT).show();
                                    remove=false;
                                    return;
                                }
                                remove=false;

                            } catch(Exception pe){
                                remove=false;
                                Toast.makeText(getApplicationContext(),
                                        getString(R.string.speech_index_missunderstand),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
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

    private  boolean isInt(String str)
    {
        try
        {
            int d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    // This is used for SpeechToText
    //check TTS version on executing device
    private void checkSpeech() {
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    public void goToNextPage(View v) {
        Intent intent= new Intent(this, ProfileAddressCharitySpeechActivity.class);
        //intent.putExtra(EXTRA_INTOLERANCES,ProfileModel.getUnvertraeglichkeitenListe());
        //intent.putExtra(EXTRA_DISEASES, ProfileModel.getKrankheitenListe());
        //intent.putExtra(EXTRA_EXTRAS,ProfileModel.getExtraListe());
        if(profileModel!=null){
            intent.putExtra(EXTRA_LIST,profileModel);
        }
        if(userModel!=null){
            intent.putExtra(EXTRA_MESSAGE,userModel);
        }
        startActivity(intent);
    }

    public void readExtraInfoText(View v) {
        Resources res = getResources();
        String text = res.getString(R.string.intro_extras_full);
        ttsManager.initQueue(text);
    }
}
