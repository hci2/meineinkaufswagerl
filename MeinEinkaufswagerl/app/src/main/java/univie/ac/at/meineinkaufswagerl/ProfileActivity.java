package univie.ac.at.meineinkaufswagerl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Wilson on 08.05.2016.
 * Zeigt das Zwischen Menü für die Profil Einrichtung
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    TextView infotext;
    Button weiterButton;
    Button vorleseButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_intro);


        initializeVariables();
        weiterButton.setOnClickListener(ProfileActivity.this);
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

}
