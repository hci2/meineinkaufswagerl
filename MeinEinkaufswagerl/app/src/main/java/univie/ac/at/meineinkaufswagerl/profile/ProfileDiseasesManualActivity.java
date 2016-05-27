package univie.ac.at.meineinkaufswagerl.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.SerializableManager;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.UserModel;

public class ProfileDiseasesManualActivity extends AppCompatActivity implements View.OnClickListener,Serializable {

    Button buttonContinue;
    ListView listDiseases, listUnvertrag;
    ArrayList<String> diseases,intolerances;
    UserModel userModel;
    ProfileModel profileModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_diseases_manual);

        //Unwrap the intent and get the objects
        userModel = new UserModel();
        profileModel = new ProfileModel();
        if(getIntent() != null && getIntent().getExtras() != null){
            userModel = (UserModel)getIntent().getExtras().getSerializable(ProfileNameSpeechActivity.EXTRA_MESSAGE);
            profileModel = (ProfileModel)getIntent().getExtras().getSerializable(ProfileNameSpeechActivity.EXTRA_LIST);

            //TODO: Bestehende Krankheiten von vorheriger Profileditierung in Checkboxen Häckchen setzen
        }

        /*
        userModel = (UserModel)getIntent().getSerializableExtra("userModel");
        System.out.println(userModel.getFirstname());

        profileModel = new ProfileModel();
        */
        listDiseases = (ListView)findViewById(R.id.listDiseases);
        diseases=new ArrayList<>();
        diseases.add("Diabetes");
        diseases.add("Morbus Crohn");
        diseases.add("Gicht");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, diseases);
        listDiseases.setAdapter(adapter1);
        listDiseases.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //listDiseases.setOnItemClickListener(this);

        listUnvertrag= (ListView)findViewById(R.id.listUnvertrag);
        intolerances=new ArrayList<>();
        intolerances.add("Lactose");
        intolerances.add("Gluten");
        intolerances.add("Fructose");
        intolerances.add("Ei");
        intolerances.add("Fisch");
        intolerances.add("Phenylalanin");
        intolerances.add("Histamin");
        intolerances.add("Sorbin");
        intolerances.add("Saccharose");
        intolerances.add("Erdnüsse");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, intolerances);
        listUnvertrag.setAdapter(adapter2);
        listUnvertrag.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
       // listUnvertrag.setOnItemClickListener(this);
        buttonContinue = (Button)findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==buttonContinue){
            for(int i=0; i<listUnvertrag.getCheckedItemPositions().size(); i++) {
                switch (listUnvertrag.getCheckedItemPositions().keyAt(i)) {
                    case 0:
                        profileModel.setLactose(1);
                        profileModel.addUnvertraeglichkeit(intolerances.get(listUnvertrag.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 1:
                        profileModel.setGluten(1);
                        profileModel.addUnvertraeglichkeit(intolerances.get(listUnvertrag.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 2:
                        profileModel.setFructose(1);
                        profileModel.addUnvertraeglichkeit(intolerances.get(listUnvertrag.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 3:
                        profileModel.setEi(1);
                        profileModel.addUnvertraeglichkeit(intolerances.get(listUnvertrag.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 4:
                        profileModel.setFisch(1);
                        profileModel.addUnvertraeglichkeit(intolerances.get(listUnvertrag.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 5:
                        profileModel.setPhenylalanin(1);
                        profileModel.addUnvertraeglichkeit(intolerances.get(listUnvertrag.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 6:
                        profileModel.setHistamin(1);
                        profileModel.addUnvertraeglichkeit(intolerances.get(listUnvertrag.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 7:
                        profileModel.setSorbin(1);
                        profileModel.addUnvertraeglichkeit(intolerances.get(listUnvertrag.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 8:
                        profileModel.setSaccharose(1);
                        profileModel.addUnvertraeglichkeit(intolerances.get(listUnvertrag.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 9:
                        profileModel.setErdnüsse(1);
                        profileModel.addUnvertraeglichkeit(intolerances.get(listUnvertrag.getCheckedItemPositions().keyAt(i)));
                        break;
                    default:
                        break;
                }
            }
            for(int i=0; i<listDiseases.getCheckedItemPositions().size(); i++) {
                switch (listDiseases.getCheckedItemPositions().keyAt(i)) {
                    case 0: profileModel.setDiabetes(1);
                        profileModel.addKrankheit(diseases.get(listDiseases.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 1: profileModel.setMorbus(1);
                        profileModel.addKrankheit(diseases.get(listDiseases.getCheckedItemPositions().keyAt(i)));
                        break;
                    case 2: profileModel.setGicht(1);
                        profileModel.addKrankheit(diseases.get(listDiseases.getCheckedItemPositions().keyAt(i)));
                        break;
                    default: break;
                }
            }

            userModel.setCreatedSuccessfullyProfile(true);
            String pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();
            String filePathProfile = pathToAppFolder + File.separator + "profile.ser";
            String filePathUser = pathToAppFolder +File.separator + "user.ser";
            SerializableManager.saveSerializable(profileModel, filePathProfile);
            SerializableManager.saveSerializable(userModel,filePathUser);

            Intent intent= new Intent(this, ProfileFinishedSpeechActivity.class);
            startActivity(intent);
        }
    }
}
