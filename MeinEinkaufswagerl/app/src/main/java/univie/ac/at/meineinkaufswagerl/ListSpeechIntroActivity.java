package univie.ac.at.meineinkaufswagerl;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;

public class ListSpeechIntroActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    TextView infoText;
    Button nextButton;
    Button readButton;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_speech_intro);


        initializeVariables();

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        // This is used for TextToSpeech
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String text = infoText.getText().toString();
                ttsManager.initQueue(text);
            }
        });
    }

    public void goToListCreateSpeechActivity(View v) {
        Intent intent= new Intent(this, ListCreateSpeechActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
    private void initializeVariables() {

        nextButton= (Button) findViewById(R.id.nextButton);
        readButton= (Button) findViewById(R.id.readButton);
        infoText = (TextView) findViewById(R.id.infoText);

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
