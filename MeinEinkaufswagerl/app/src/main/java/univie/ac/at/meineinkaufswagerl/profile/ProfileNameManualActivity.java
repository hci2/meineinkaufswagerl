package univie.ac.at.meineinkaufswagerl.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.model.CharityModel;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.UserModel;

public class ProfileNameManualActivity extends AppCompatActivity implements View.OnClickListener,Serializable {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl.MESSAGE";
    public final static String EXTRA_LIST = "univie.ac.at.meineinkaufswagerl.LIST";

    Button buttonContinue;
    UserModel userModel;
    ProfileModel profileModel;
    CharityModel charityModel;
    EditText inputVorname, inputNachname, inputStrasse, inputHausnummer, inputPLZ;
    TextView charityOrganisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_name_manual);

        buttonContinue = (Button)findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(this);
        inputVorname = (EditText)findViewById(R.id.inputVorname);
        inputNachname = (EditText)findViewById(R.id.inputNachname);
        inputStrasse = (EditText)findViewById(R.id.inputStrasse);
        inputHausnummer = (EditText)findViewById(R.id.inputHausnummer);
        inputPLZ = (EditText)findViewById(R.id.inputPLZ);
        charityOrganisation = (TextView) findViewById(R.id.charityView);

        //Unwrap the intent and get the objects.
        userModel = new UserModel();
        profileModel = new ProfileModel();
        charityModel = new CharityModel();
        if(getIntent() != null && getIntent().getExtras() != null){
            userModel = (UserModel)getIntent().getExtras().getSerializable(ProfileNameSpeechActivity.EXTRA_MESSAGE);
            profileModel = (ProfileModel)getIntent().getExtras().getSerializable(ProfileNameSpeechActivity.EXTRA_LIST);

            //set the values of the last profile editing
            inputVorname.setText(userModel.getFirstname());
            inputNachname.setText(userModel.getLastname());
            inputStrasse.setText(userModel.getStreet());
            inputHausnummer.setText(userModel.getStreetnumber());
            inputPLZ.setText(userModel.getPostalcode());
            charityOrganisation.setText("Die zugeordnete Hilfsorganisation lautet: "+userModel.getCharity());
        }else{
            charityOrganisation.setText("Keine Hilfsorgganisation zugeordnet");
        }

    }

    @Override
    public void onClick(View v) {
        if(v == buttonContinue) {
            String vorname = inputVorname.getText().toString();
            String nachname = inputNachname.getText().toString();
            String strasse = inputStrasse.getText().toString();
            String hausnummer = inputHausnummer.getText().toString();
            String plz = inputPLZ.getText().toString();
            if(!vorname.isEmpty() && !nachname.isEmpty() && !strasse.isEmpty() && !hausnummer.isEmpty() && !plz.isEmpty()) {
                userModel.setFirstname(vorname);
                userModel.setLastname(nachname);
                userModel.setStreet(strasse);
                userModel.setStreetnumber(hausnummer);
                userModel.setPostalcode(plz);
                //get the charity organisation with the plz
                userModel.setCharity(charityModel.getCharityForPLZ(plz));

                Intent intent = new Intent(this, ProfileDiseasesManualActivity.class);
                if(profileModel!=null){
                    intent.putExtra(EXTRA_LIST,profileModel);
                }
                if(userModel!=null){
                    intent.putExtra(EXTRA_MESSAGE,userModel);
                }
                startActivity(intent);
            }
            else
                Toast.makeText(this,"Bitte alle Eingabefelder ausfüllen !", Toast.LENGTH_LONG).show();
        }
    }
}
