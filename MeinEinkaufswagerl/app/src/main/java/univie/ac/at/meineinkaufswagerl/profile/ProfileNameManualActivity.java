package univie.ac.at.meineinkaufswagerl.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.model.UserModel;

public class ProfileNameManualActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonContinue;
    UserModel userModel;
    EditText inputVorname, inputNachname, inputStrasse, inputHausnummer, inputPLZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_name_manual);

        userModel = new UserModel();
        buttonContinue = (Button)findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(this);
        inputVorname = (EditText)findViewById(R.id.inputVorname);
        inputNachname = (EditText)findViewById(R.id.inputNachname);
        inputStrasse = (EditText)findViewById(R.id.inputStrasse);
        inputHausnummer = (EditText)findViewById(R.id.inputHausnummer);
        inputPLZ = (EditText)findViewById(R.id.inputPLZ);
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
                Intent intent = new Intent(this, ProfileDiseasesManualActivity.class);
                intent.putExtra("userModel", userModel);
                startActivity(intent);
            }
            else
                Toast.makeText(this,"Bitte alle Eingabefelder ausfüllen !", Toast.LENGTH_LONG).show();
        }
    }
}
