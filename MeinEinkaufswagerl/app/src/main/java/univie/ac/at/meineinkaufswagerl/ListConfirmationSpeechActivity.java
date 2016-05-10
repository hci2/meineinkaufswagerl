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
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;
import univie.ac.at.meineinkaufswagerl.model.TemporaryListModel;

public class ListConfirmationSpeechActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";

    private ListView txtSpeechList;
    private TemporaryListModel tempList;
    private StandingOrderListModel standList = new StandingOrderListModel();
    private ImageButton btnRead;
    private TextToSpeechManager ttsManager = null;
    private Button addStandButton;
    private Button replaceStandButton;
    private Button finishTempButton;
    private Button finishStandButton;

    //SpeechToTextManager sttManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_confirmation_speech);

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //initialize all the elements of the layout xml
        txtSpeechList = (ListView) findViewById(R.id.textListView);
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
        ArrayList<String> textList=tempList.getTextList();
        if(!(textList.size()==0)){
            for(int i=0;i<textList.size();i++){
                standList.addTextList(textList.get(i));
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
            if(textList.size()<=standList.getSize()){
                for(int i=0;i<standList.getSize();i++){
                    standList.removeTextListElement(i);
                    if(i<=textList.size()){
                        standList.changeTextListElement(textList.get(i),i);
                    }
                }
            } else if(textList.size()>standList.getSize()){
                for(int i=0;i<textList.size();i++){
                    if(i<=standList.getSize()){
                        standList.removeTextListElement(i);
                    }
                    standList.addTextList(textList.get(i));
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
        Intent intent= new Intent(this, ListFinishedSpeechActivity.class);
        String message="Ihr Auftrag der dauerhaften Liste wurde erfolgreich gesendet";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
    public void goToFinishStandPage(View v) {
        Intent intent= new Intent(this, ListFinishedSpeechActivity.class);
        String message="Ihr Auftrag der eigenen Liste wurde erfolgreich gesendet";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }

}
