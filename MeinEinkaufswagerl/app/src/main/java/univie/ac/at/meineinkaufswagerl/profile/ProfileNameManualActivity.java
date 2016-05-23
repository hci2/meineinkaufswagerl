package univie.ac.at.meineinkaufswagerl.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import univie.ac.at.meineinkaufswagerl.R;

public class ProfileNameManualActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_name_manual);

        buttonContinue = (Button)findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonContinue) {
            Intent intent = new Intent(this,ProfileDiseasesManualActivity.class);
            startActivity(intent);
        }
    }
}
