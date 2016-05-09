package univie.ac.at.meineinkaufswagerl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

import univie.ac.at.meineinkaufswagerl.model.ProfileModel;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl";

    Button profilebutton, shoppingbutton;
    Button listButton;
    Button leaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Felix Anfang
        initializeVariables();
        profilebutton.setOnClickListener(HomeActivity.this);
        shoppingbutton.setOnClickListener(HomeActivity.this);
        // Felix Ende

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
        // Startet auf Knopfdruck die ListSupportPage
        Intent  intent= new Intent(this, ListSupportPage.class);
        String message="";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }

    public void goToLeaveApp(View v){
        // Verlässt auf Knopfdruck die App
        finish();
        System.exit(0);
    }


    private void initializeVariables() {

        profilebutton= (Button) findViewById(R.id.profilebutton);
        shoppingbutton= (Button) findViewById(R.id.shoppingbutton);
        listButton= (Button) findViewById(R.id.listButton);
        leaveButton= (Button) findViewById(R.id.leaveButton);

    }



}
