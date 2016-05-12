package univie.ac.at.meineinkaufswagerl.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import univie.ac.at.meineinkaufswagerl.HomeActivity;
import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ListConfirmationSpeechActivity;

public class ProfileFinishedSpeechActivity extends AppCompatActivity {

    private String liste;
    private TextView showFinishedText;
    private Button btnHome;
    private ImageButton btnRead;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_finished_speech);

        //initialize view variables in the UI (function definition is under the onCreate() method)
        initializeVariables();

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        // This is used for TextToSpeech
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String text = showFinishedText.getText().toString();
                ttsManager.initQueue(text);
            }
        });

    }

    //The initilization function for the views controlled by your activity
    private void initializeVariables(){
        showFinishedText = (TextView) findViewById(R.id.showFinishedText);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnRead = (ImageButton) findViewById(R.id.btnRead);
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

    public void goToHomeScreen(View v) {
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
