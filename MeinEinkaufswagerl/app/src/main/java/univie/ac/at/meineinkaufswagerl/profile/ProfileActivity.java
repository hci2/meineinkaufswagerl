package univie.ac.at.meineinkaufswagerl.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import univie.ac.at.meineinkaufswagerl.HomeActivity;
import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;
import univie.ac.at.meineinkaufswagerl.model.UserModel;

/**
 * Created by Wilson on 08.05.2016.
 * Zeigt das Zwischen Menü für die Profil Einrichtung
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,Serializable {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl.MESSAGE";
    public final static String EXTRA_LIST = "univie.ac.at.meineinkaufswagerl.LIST";

    TextView infotext;
    Button weiterButton;
    Button vorleseButton;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    UserModel userModel;
    ProfileModel profileModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_intro);


        initializeVariables();

        //Unwrap the intent and get the temporary list.
        userModel = new UserModel();
        profileModel = new ProfileModel();
        if(getIntent() != null && getIntent().getExtras() != null){
            profileModel = (ProfileModel)getIntent().getExtras().getSerializable(HomeActivity.EXTRA_LIST);
            userModel = (UserModel)getIntent().getExtras().getSerializable(HomeActivity.EXTRA_MESSAGE);
        }




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
        if(profileModel!=null){
            intent1.putExtra(EXTRA_LIST,profileModel);
        }
        if(userModel!=null){
            intent1.putExtra(EXTRA_MESSAGE,userModel);
        }
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
