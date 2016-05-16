package univie.ac.at.meineinkaufswagerl;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;

import univie.ac.at.meineinkaufswagerl.management.SerializableManager;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;
import univie.ac.at.meineinkaufswagerl.model.UserModel;
import univie.ac.at.meineinkaufswagerl.profile.ProfileActivity;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ListSupportPage;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ShoppingManuallyActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,Serializable {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl.MESSAGE";
    public final static String EXTRA_LIST = "univie.ac.at.meineinkaufswagerl.LIST";

    Button profilebutton, shoppingbutton;
    Button listButton;
    Button leaveButton;
    TextView infoText;

    //This variable is used to get access to the TextToSpeech
    private TextToSpeechManager ttsManager = null;

    //This is used for deserialisation
    UserModel userModel;
    ProfileModel profileModel;
    StandingOrderListModel standingOrderListModel;

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


        userModel=new UserModel();
        //userModel=UserModel.getInstance();
        String pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();
        String filePathProfile = pathToAppFolder +File.separator + "profile.ser";
        String filePathUser = pathToAppFolder +File.separator + "user.ser";
        String filePathStandingOrder = pathToAppFolder +File.separator + "standingorder.ser";
        if(new File(filePathUser).exists()){
            userModel=SerializableManager.readSerializable(filePathUser); // this,
        }

        profileModel=new ProfileModel();
        //profileModel=ProfileModel.getInstance();
        if(new File(filePathProfile).exists()){
            profileModel=SerializableManager.readSerializable(filePathProfile); //this,
        }

        /*
        if(profileModel!=null){
            profileModel.set
        }
        */
        standingOrderListModel=new StandingOrderListModel();
        //standingOrderListModel=StandingOrderListModel.getInstance();
        if(new File(filePathStandingOrder).exists()){
            standingOrderListModel=SerializableManager.readSerializable(filePathStandingOrder); //this,
        }

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
            if(profileModel!=null){
                intent.putExtra(EXTRA_LIST,profileModel);
            }
            if(userModel!=null){
                intent.putExtra(EXTRA_MESSAGE,userModel);
            }
            startActivity(intent);
        }
    }


    public void goToListSupportPage(View v){
        //Prüft ob ein Profil angelegt worden ist, falls nein dann wird eine Fehler Meldung angezeigt. derzeigig für Test und Vorzeigezwecke DEAKTIVIERT
        if(userModel.getCreatedSuccessfullyProfile()){
            // Startet auf Knopfdruck die ListSupportPage
            Intent  intent= new Intent(this, ListSupportPage.class);
            if(standingOrderListModel!=null){
                intent.putExtra(EXTRA_MESSAGE,standingOrderListModel);
            }
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
