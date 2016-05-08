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
 * Hier wählt der NUtzer aus welche Art vvon Hilfe er bei der Eingabe haben will.
 */
public class ProfileSupportPage extends AppCompatActivity implements View.OnClickListener {


    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    TextView infoText;
    Button sprachButton;
    Button manuellButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_support_page);


        initializeVariables();
        manuellButton.setOnClickListener(ProfileSupportPage.this);
    }

    public void initializeVariables() {
        sprachButton = (Button) findViewById(R.id.sprachButton);
        manuellButton = (Button) findViewById(R.id.manuellButton);
        infoText = (TextView) findViewById(R.id.infoText);

    }

    @Override
    public void onClick(View v) {
        Intent intent= new Intent(this, ProfileUnvertragActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }


}
