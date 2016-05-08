package univie.ac.at.meineinkaufswagerl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

import univie.ac.at.meineinkaufswagerl.model.ProfileModel;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";

    Button profilebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Felix Anfang
        initializeVariables();
        profilebutton.setOnClickListener(HomeActivity.this);

        // Felix Ende


        // Test

        //test Felix

        // test Alina

        // test Philip
    }


    //Von Felix
    @Override
    public void onClick(View v){
        // Startet auf Knopfdruck die ProfileActivity
        Intent  intent= new Intent(this, ProfileActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);

    }



    private void initializeVariables() {

        profilebutton= (Button) findViewById(R.id.profilebutton);

    }



}
