package univie.ac.at.meineinkaufswagerl.shoppinglist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;
import univie.ac.at.meineinkaufswagerl.model.TemporaryListModel;

public class ListConfirmationSpeechActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";

    private ListView txtSpeechList;
    private TemporaryListModel tempList;
    //private StandingOrderListModel standList = new StandingOrderListModel();
    private ImageButton btnRead;
    private TextToSpeechManager ttsManager = null;
    private Button addStandButton;
    private Button replaceStandButton;
    private Button finishTempButton;
    private Button finishStandButton;

    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private boolean temp;

    //SpeechToTextManager sttManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_confirmation_speech);

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //initialize all the elements of the layout xml
        txtSpeechList = (ListView) findViewById(R.id.extraListe);
        btnRead = (ImageButton) findViewById(R.id.btnRead);
        addStandButton = (Button) findViewById(R.id.addStandButton);
        replaceStandButton = (Button) findViewById(R.id.replaceStandButton);
        finishTempButton = (Button) findViewById(R.id.finishTempButton);
        finishStandButton = (Button) findViewById(R.id.finishStandButton);

        //Unwrap the intent and get the temporary list.
        tempList=new TemporaryListModel();
        ArrayList<String> stringList = getIntent().getStringArrayListExtra(ListCreateSpeechActivity.EXTRA_MESSAGE);
        for(int i=0;i<stringList.size();i++){
            tempList.addTextList(stringList.get(i));
        }
        //This is used to display the temporary list on the view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempList.getTextList());
        txtSpeechList.setAdapter(adapter);

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

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        temp=false;

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

    public void addStandButton(View v) {
        ArrayList<String> textList=tempList.getTextList();
        if(!(textList.size()==0)){
            for(int i=0;i<textList.size();i++){
                StandingOrderListModel.addTextList(textList.get(i));
            }
            Toast.makeText(getApplicationContext(),
                    getString(R.string.finished_addStandingOrder),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.notfinished_addStandingOrder),
                    Toast.LENGTH_LONG).show();
        }

    }

    public void replaceStandButton(View v) {
        ArrayList<String> textList=tempList.getTextList();
        if(!(textList.size()==0)){
            if(textList.size()<=StandingOrderListModel.getSize()){
                for(int i=0;i<StandingOrderListModel.getSize();i++){
                    StandingOrderListModel.removeTextListElement(i);
                    if(i<=textList.size()){
                        StandingOrderListModel.changeTextListElement(textList.get(i),i);
                    }
                }
            } else if(textList.size()>StandingOrderListModel.getSize()){
                for(int i=0;i<textList.size();i++){
                    if(i<=StandingOrderListModel.getSize()){
                        StandingOrderListModel.removeTextListElement(i);
                    }
                    StandingOrderListModel.addTextList(textList.get(i));
                }
            }
            Toast.makeText(getApplicationContext(),
                    getString(R.string.finished_replaceStandingOrder),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.notfinished_replaceStandingOrder),
                    Toast.LENGTH_LONG).show();
        }

    }
    public void goToFinishTempPage(View v) {
        temp=true;
        ttsManager.initQueue("Sind Sie sicher, ob sie die eigene Liste kaufen wollen?");
        //This is used to make a 4 second pause
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 4000ms
                speechText();
            }
        }, 4000);
    }
    public void goToFinishStandPage(View v) {
        temp=false;
        ttsManager.initQueue("Sind Sie sicher, ob sie die dauerhafte Liste kaufen wollen?");
        //This is used to make a 4 second pause
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 4000ms
                speechText();
            }
        }, 4000);
    }

    // This is used for SpeechToText
    //check TTS version on executing device
    private void checkSpeech() {
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    // This is used for SpeechToText
    private void speechText(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.GERMAN);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_confirmation));

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
                    if((!temp) && (resultString.equals("ja") || resultString.equals("Ja") || resultString.contains("ja") || resultString.contains("Ja"))){
                        //TODO: dauerhafte Einkaufsliste + Adresse des Empfängers der Hilfsorganisation übermitteln
                        //TODO: Dauerhafte Liste serialisieren!
                        Intent intent= new Intent(this, ListFinishedSpeechActivity.class);
                        String message="Ihr Auftrag der dauerhaften Liste wurde erfolgreich gesendet";
                        intent.putExtra(EXTRA_MESSAGE,message);
                        startActivity(intent);

                    } else if ((temp) && (resultString.equals("ja") || resultString.equals("Ja") || resultString.contains("ja") || resultString.contains("Ja"))) {
                        //TODO: temporäre Einkaufsliste + Adresse des Empfängers der Hilfsorganisation übermitteln
                        Intent intent= new Intent(this, ListFinishedSpeechActivity.class);
                        String message="Ihr Auftrag der eigenen Liste wurde erfolgreich gesendet";
                        intent.putExtra(EXTRA_MESSAGE,message);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.speech_abort),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }

        }
    }

}
