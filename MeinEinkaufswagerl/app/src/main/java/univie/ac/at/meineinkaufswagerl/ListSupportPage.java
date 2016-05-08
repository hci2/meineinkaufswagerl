package univie.ac.at.meineinkaufswagerl;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListSupportPage extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";
    TextView infoText;
    Button sprachButton;
    Button manuellButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_support_page);


        initializeVariables();
    }

    public void initializeVariables() {
        sprachButton = (Button) findViewById(R.id.sprachButton);
        manuellButton = (Button) findViewById(R.id.manuellButton);
        infoText = (TextView) findViewById(R.id.infoText);

    }

    public void goToSpeechSupportedList(View v) {
        Intent intent= new Intent(this, ProfileUnvertragActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }

    public void goToTextSupportedList(View v) {
        Intent intent= new Intent(this, ProfileUnvertragActivity.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
}
