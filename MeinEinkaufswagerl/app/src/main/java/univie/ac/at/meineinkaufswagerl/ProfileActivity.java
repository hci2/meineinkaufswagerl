package univie.ac.at.meineinkaufswagerl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;

/**
 * Created by Wilson on 08.05.2016.
 * Zeigt das Zwischen Menü für die Profil Einrichtung
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    TextView infotext;
    Button weiterButton;
    Button vorleseButton;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_intro);


        initializeVariables();

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        weiterButton.setOnClickListener(ProfileActivity.this);

        // This is used for TextToSpeech
        vorleseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String text = infotext.getText().toString();
                ttsManager.initQueue(text);
            }
        });
    }

    // Ruft die Support PAge auf wo ma aussuchen kann welche unterstüzug man will.
    @Override
    public void onClick(View v1){
        Intent  intent1= new Intent(this, ProfileSupportPage.class);
        String message="";
        intent1.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent1);

    }




    private void initializeVariables() {

        weiterButton= (Button) findViewById(R.id.weiterButton);
        vorleseButton= (Button) findViewById(R.id.vorleseButton);
        infotext = (TextView) findViewById(R.id.infoText);

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
