package univie.ac.at.meineinkaufswagerl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.UserModel;
import univie.ac.at.meineinkaufswagerl.profile.ProfileActivity;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ListSupportPage;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ShoppingManuallyActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";

    Button profilebutton, shoppingbutton;
    Button listButton;
    Button leaveButton;
    TextView infoText;
    UserModel userModel=null;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Felix Anfang
        initializeVariables();

        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        profilebutton.setOnClickListener(HomeActivity.this);
        shoppingbutton.setOnClickListener(HomeActivity.this);
        // Felix Ende

        userModel = new UserModel();

    }


    //Von Felix
    @Override
    public void onClick(View v){
        if(v == shoppingbutton) {
            Intent  intent= new Intent(this, ShoppingManuallyActivity.class);
            startActivity(intent);
        }
        else if(v == profilebutton) {
            // Startet auf Knopfdruck die ProfileActivity
            Intent  intent= new Intent(this, ProfileActivity.class);
            String message="";
            intent.putExtra(EXTRA_MESSAGE,message);
            startActivity(intent);
        }
    }


    public void goToListSupportPage(View v){
        //Prüft ob ein Profil angelegt worden ist, falls nein dann wird eine Fehler Meldung angezeigt. derzeigig für Test und Vorzeigezwecke DEAKTIVIERT
        if(userModel.getCreatedSuccessfullyProfile()){
            // Startet auf Knopfdruck die ListSupportPage
            Intent  intent= new Intent(this, ListSupportPage.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.noProfileCreated),
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void goToLeaveApp(View v){
        // Verlässt auf Knopfdruck die App
        finish();
        System.exit(0);
    }

    public void readInfoText(View v){
        // Startet auf Knopfdruck die Sprachausgabe
        String text = infoText.getText().toString();
        ttsManager.initQueue(text);
    }


    private void initializeVariables() {

        profilebutton= (Button) findViewById(R.id.profilebutton);
        shoppingbutton= (Button) findViewById(R.id.shoppingbutton);
        listButton= (Button) findViewById(R.id.listButton);
        leaveButton= (Button) findViewById(R.id.leaveButton);
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
