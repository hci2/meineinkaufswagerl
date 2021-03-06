package univie.ac.at.meineinkaufswagerl.standingorder;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import univie.ac.at.meineinkaufswagerl.R;
import univie.ac.at.meineinkaufswagerl.management.SerializableManager;
import univie.ac.at.meineinkaufswagerl.management.TextToSpeechManager;
import univie.ac.at.meineinkaufswagerl.model.ProductModel;
import univie.ac.at.meineinkaufswagerl.model.ProductNotFittingModel;
import univie.ac.at.meineinkaufswagerl.model.ProfileModel;
import univie.ac.at.meineinkaufswagerl.model.StandingOrderListModel;
import univie.ac.at.meineinkaufswagerl.model.TemporaryListModel;
import univie.ac.at.meineinkaufswagerl.model.UserModel;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ListConfirmationSpeechActivity;
import univie.ac.at.meineinkaufswagerl.shoppinglist.ListSupportPage;

public class StandingOrderEditSpeechActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "univie.ac.at.meineinkaufswagerl.MESSAGE";
    public final static String EXTRA_LIST = "univie.ac.at.meineinkaufswagerl.LIST";
    public final static String EXTRA_PRODUCT = "univie.ac.at.meineinkaufswagerl.PRODUCT";
    private ImageButton btnSpeak;
    private ListView txtSpeechList;
    private ImageButton btnRead;
    private TextToSpeechManager ttsManager = null;
    private Button btnNext;
    //SpeechToTextManager sttManager = null;

    //For Changing
    private ImageButton btnRemove;
    private boolean remove=false;

    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private StandingOrderListModel standingOrderListModel;
    private ArrayList<ProductModel> currentAvailableProductList;
    private ArrayList<ProductModel>standingOrderProductList;
    private ArrayList<String>currentListView;
    private ArrayList<String>currentAllProductView;
    private ProfileModel profileModel;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standing_order_edit_speech);

        String pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();
        String filePathProfile = pathToAppFolder +File.separator + "profile.ser";
        String filePathUser = pathToAppFolder +File.separator + "user.ser";
        String filePathStandingOrder = pathToAppFolder +File.separator + "standingorder.ser";
        userModel= new UserModel();
        if(new File(filePathUser).exists()){
            userModel=SerializableManager.readSerializable(filePathUser); // this,
        }

        profileModel=new ProfileModel();
        //profileModel=ProfileModel.getInstance();
        if(new File(filePathProfile).exists()){
            profileModel=SerializableManager.readSerializable(filePathProfile); //this,
        }


        standingOrderListModel=new StandingOrderListModel();
        //standingOrderListModel=StandingOrderListModel.getInstance();
        if(new File(filePathStandingOrder).exists()){
            standingOrderListModel=SerializableManager.readSerializable(filePathStandingOrder); //this,

        }

        createProducts();
        standingOrderProductList=new ArrayList<ProductModel>();
        currentListView=new ArrayList<String>();


        //initiate TextToSpeechManager
        ttsManager = new TextToSpeechManager();
        ttsManager.init(this);

        //initiate SpeechToTextManager
        //sttManager = new SpeechToTextManager(HomeActivity.this);

        //check TTS version on executing device - needed for SpeechToText
        checkSpeech();

        //initialize all the elements of the layout xml
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        txtSpeechList = (ListView) findViewById(R.id.extraListe);
        btnRead = (ImageButton) findViewById(R.id.btnRead);
        btnRemove = (ImageButton) findViewById(R.id.btnRemove);
        btnNext = (Button) findViewById(R.id.nextButton);

        // This is used to get the index for changing a line of the List
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove=true;
                speechText();
            }
        });

        //This is used for SpeechToText
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove=false;
                speechText();
            }
        });

        // This is used for TextToSpeech
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //String text = txtTextView.getText().toString();
                ttsManager.initQueue(currentListView.get(0));
                for(int i=1;i<currentListView.size();i++){
                    ttsManager.addQueue(currentListView.get(i));
                }
            }
        });



        if(standingOrderListModel.getSizeProductList()!=0){
            standingOrderProductList=standingOrderListModel.getProductList();
            for(int i=0;i<standingOrderListModel.getSizeProductList();i++){
                currentListView.add(((int)standingOrderProductList.get(i).getMenge())+" "+standingOrderProductList.get(i).getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currentListView);
            txtSpeechList.setAdapter(adapter);
        }else{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currentAllProductView);
            txtSpeechList.setAdapter(adapter);
        }

    }

    private boolean isUserCompatibleWithProduct(String product){
        ProductNotFittingModel productNotFittingModel=new ProductNotFittingModel();
        String[][] productUnfitList=productNotFittingModel.getProductUnfitList();
        for (int row =0;row<productUnfitList.length;row++){
            int col=0;
            if(productUnfitList[row][col].equals(product)){
                for (col =1;col<productUnfitList[row].length;col++) {
                    if(profileModel.notInIntolerance(productUnfitList[row][col]) && profileModel.notInDisease(productUnfitList[row][col]) && profileModel.notInExtra(product)){
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private  boolean isInt(String str)
    {
        try
        {
            int d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    private int getNumberFromWrittenForm(String number){
        String [][] textToNumber = new String[][]{
                {"ein", "1"},
                {"zwei", "2"},
                {"drei", "3"},
                {"vier", "4"},
                {"fünf", "5"},
                {"sechs", "6"},
                {"sieben", "7"},
                {"acht", "8"},
                {"neun", "9"},
                {"zehn", "10"},
                {"elf", "11"},
                {"zwölf", "12"},
                {"dreizehn", "13"},
                {"vierzehn", "14"},
                {"fünfzehn", "15"},
                {"sechzehn", "16"},
                {"siebzehn", "17"},
                {"achtzehn", "18"},
                {"neunzehn", "19"},
                {"zwanzig", "20"},};

        for(int row=0;row<textToNumber.length;row++){
            int col=0;
            if(textToNumber[row][col].equals(number) || textToNumber[row][col].contains(number)){
                ++col;
                return Integer.parseInt(textToNumber[row][col]);
            }
        }
        return 1;
    }

    private void createProducts() {
        this.currentAvailableProductList = new ArrayList<ProductModel>();
        this.currentAvailableProductList.add(new ProductModel("Milch", 1.0f, "Lebensmittel", 1.0f, "Liter", R.drawable.milch));
        this.currentAvailableProductList.add(new ProductModel("Brot",0.50f,"Lebensmittel",1.0f,"Kilo", R.drawable.brot));
        this.currentAvailableProductList.add(new ProductModel("Joghurt", 0.30f, "Lebensmittel", 0.25f, "Kilo", R.drawable.joghurt));
        this.currentAvailableProductList.add(new ProductModel("Karotten",1.25f,"Lebensmittel",1.0f,"Kilo",R.drawable.karotten));
        this.currentAvailableProductList.add(new ProductModel("Apfel", 2.15f, "Lebensmittel", 2.0f, "Kilo", R.drawable.apfel));
        this.currentAvailableProductList.add(new ProductModel("Cola",1.85f,"Lebensmittel",2.0f,"Liter",R.drawable.cola));
        this.currentAvailableProductList.add(new ProductModel("Waschmittel",5.20f,"Haushalt",3.0f,"Kilo",R.drawable.waschmittel));
        this.currentAvailableProductList.add(new ProductModel("Zahnpasta", 1.50f, "Haushalt", 0.20f, "Kilo", R.drawable.zahnpasta));
        this.currentAvailableProductList.add(new ProductModel("Duschgel",2.0f,"Haushalt",0.03f,"Liter",R.drawable.duschgel));
        this.currentAvailableProductList.add(new ProductModel("Staubsauger", 80.0f, "Haushalt", 1.0f, "Stück", R.drawable.staubsauger));
        currentAllProductView=new ArrayList<String>();
        for(int i=0;i<currentAvailableProductList.size();i++){
            currentAllProductView.add(currentAvailableProductList.get(i).getName());
        }

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

    // This is used for SpeechToText
    private void speechText(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.GERMAN);
        if(remove){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_remove));
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_prompt));
        }
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // This is used for SpeechToText
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String resultString=result.get(0);
                    if(remove){
                        boolean changeSuccess=false;
                        try{
                            int removeLine=Integer.parseInt(resultString)-1;

                            //To get just the number as String
                            //String number=resultString.replaceAll("[^0-9]", "");
                            if(currentListView.size()==0 || currentListView.size()<removeLine){
                                ttsManager.addQueue("Sie können keinen Bereich löschen der nicht vorhanden ist!");
                                remove=false;
                                return;
                            }
                            int zeile=removeLine+1;
                            ttsManager.addQueue("Es wurde erfolgreich Zeile " +zeile+" mit dem Inhalt "+currentListView.get(removeLine)+" aus ihrer dauerhaften Einkaufsliste gelöscht!");

                            currentListView.remove(removeLine); //Integer.parseInt(number)
                            standingOrderListModel.getProductList().remove(removeLine);

                            //tempList.changeTextListElement(resultString, indexChange); //resultString.substring(resultString.lastIndexOf(number)+1),Integer.parseInt(number)

                            remove=false;
                            changeSuccess = true;


                        } catch(Exception pe){
                            remove=false;
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.speech_index_missunderstand),
                                    Toast.LENGTH_SHORT).show();
                        }
                        if(!changeSuccess){
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.speech_index_missunderstand),
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String splitResult[];
                        splitResult=resultString.split(" ");
                        if(splitResult.length<2){
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.speech_arguments_too_short),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        String product=splitResult[(splitResult.length)-1];
                        int amount;
                        if(isInt(splitResult[0])){
                            amount=Integer.parseInt(splitResult[0]);
                        }else{
                            amount=getNumberFromWrittenForm(splitResult[0]);
                        }

                        //boolean addSuccess=false;
                        check:
                        {
                            for (int i = 0; i < currentAllProductView.size(); i++) {
                                if (currentAllProductView.get(i).equals(product) || currentAllProductView.get(i).contains(product) || currentAllProductView.get(i).contentEquals(product)
                                        || currentAllProductView.get(i).equalsIgnoreCase(product) || product.matches(currentAllProductView.get(i)) || currentAllProductView.get(i).contains(product.substring(1, 3))) {
                                    if (isUserCompatibleWithProduct(product)) {

                                        //Hinzufügen zur arraylist products
                                        for(int u=0; u<currentAvailableProductList.size();u++){
                                            if(currentAllProductView.get(i).equals(currentAvailableProductList.get(u).getName())){
                                                //Hinzufügen der Menge des Produktes zum ProductModel
                                                if(amount!=1&& amount!=0){
                                                    currentListView.add(amount+" "+product);

                                                    standingOrderProductList.add(currentAvailableProductList.get(u));
                                                    standingOrderProductList.get(standingOrderProductList.size()-1).setMenge((float)amount);
                                                    ttsManager.addQueue("Es wurden erfolgreich " +((int)currentAvailableProductList.get(u).getMenge())+" "+currentAvailableProductList.get(u).getName()+" zur dauerhaften Einkaufsliste hinzugefügt!");
                                                } else{
                                                    currentListView.add(currentAvailableProductList.get(u).getMenge()+" "+product);
                                                    //Annahme der default Menge des Produktes
                                                    standingOrderProductList.add(currentAvailableProductList.get(u));
                                                    //standingOrderProductList.get(standingOrderProductList.size()-1).setMenge(currentAvailableProductList.get(u).getMenge());
                                                    ttsManager.addQueue("Es wurden erfolgreich " +((int)currentAvailableProductList.get(u).getMenge())+" "+currentAvailableProductList.get(u).getName()+" zur  dauerhaften Einkaufsliste hinzugefügt!");
                                                }

                                            }
                                        }
                                        //addSuccess = true;

                                        break check;
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                getString(R.string.product_incompatible_with_user),
                                                Toast.LENGTH_LONG).show();
                                        break check;
                                    }


                                }

                            }
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.product_not_available),
                                    Toast.LENGTH_LONG).show();
                        }
                        /*if(!addSuccess){
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.speech_index_missunderstand),
                                    Toast.LENGTH_LONG).show();
                        }*/

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currentListView);
                    txtSpeechList.setAdapter(adapter);
                }
                break;
            }

        }
    }

    // This is used for SpeechToText
    //check TTS version on executing device
    private void checkSpeech() {
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    public void goToNextPage(View v) {
        Intent intent= new Intent(this, StandingOrderFinishedActivity.class);

        //Hinzufügen der Temporären Liste zur dauerhaften und dann abschicken und serialisieren
        for(int i=0;i<currentListView.size();i++){
            standingOrderListModel.addTextList(currentListView.get(i));
        }
        //Hinzufügen der Produkte
        standingOrderListModel.setProductList(standingOrderProductList);


        String pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();
        String filePathStandingOrder = pathToAppFolder +File.separator + "standingorder.ser";

        //delete old standing order class
        SerializableManager.removeSerializable(filePathStandingOrder);
        SerializableManager.saveSerializable(standingOrderListModel,filePathStandingOrder); //this,

        startActivity(intent);
    }
}
