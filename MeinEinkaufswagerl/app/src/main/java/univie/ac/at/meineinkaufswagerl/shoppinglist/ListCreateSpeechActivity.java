package univie.ac.at.meineinkaufswagerl.shoppinglist;

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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
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

public class ListCreateSpeechActivity extends AppCompatActivity implements Serializable {

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
    private TemporaryListModel tempList;
    private ArrayList<ProductModel>currentAvailableProductList;
    private ArrayList<ProductModel>temporaryProductList;
    private ArrayList<String>currentListView;
    private ProfileModel profileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_create_speech);

        //Unwrap the intent and get the temporary list.
        standingOrderListModel = new StandingOrderListModel();
        if(getIntent() != null && getIntent().getExtras() != null){
            standingOrderListModel = (StandingOrderListModel)getIntent().getExtras().getSerializable(ListSupportPage.EXTRA_MESSAGE);
        }
        tempList=new TemporaryListModel();
        createProducts();


        String pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();
        String filePathProfile = pathToAppFolder +File.separator + "profile.ser";
        profileModel=new ProfileModel();
        //profileModel=ProfileModel.getInstance();
        if(new File(filePathProfile).exists()){
            profileModel= SerializableManager.readSerializable(filePathProfile); //this,
        }


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
                speechText();
            }
        });

        // This is used for TextToSpeech
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //String text = txtTextView.getText().toString();
                ArrayList<String> textList=tempList.getTextList();
                if(!(textList.size()==0)){
                    ttsManager.initQueue(textList.get(0));
                    for(int i=1;i<textList.size();i++){
                        ttsManager.addQueue(textList.get(i));
                    }
                } else{
                    for(int i=0;i<currentListView.size();i++){
                        ttsManager.addQueue(currentListView.get(i));
                    }
                }
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currentListView);
        txtSpeechList.setAdapter(adapter);
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
                //TODO: Überlegen zu welchen Produkten welche Unverträglichkeiten und Krankheiten sich nicht vertragen
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
        return 0;
    }

    private void createProducts() {
        temporaryProductList= new  ArrayList<ProductModel>();
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
        currentListView=new ArrayList<String>();
        for(int i=0;i<currentAvailableProductList.size();i++){
            currentListView.add(currentAvailableProductList.get(i).getName());
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
                            if(tempList.getTextList().size()==0 || tempList.getTextList().size()<removeLine){
                                remove=false;
                                return;
                            }


                            tempList.removeTextListElement(removeLine); //Integer.parseInt(number)
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
                                    getString(R.string.speech_index_missunderstand),
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

                        boolean addSuccess=false;
                        check:
                        {
                            for (int i = 0; i < currentListView.size(); i++) {
                                if (currentListView.get(i).equals(product) || currentListView.get(i).contains(product) || currentListView.get(i).contentEquals(product)
                                        || currentListView.get(i).equalsIgnoreCase(product) || product.matches(currentListView.get(i)) || currentListView.get(i).contains(resultString.substring(1, 3))) {
                                    //TODO: Prüfung User auf Intoleranzen,Krankheiten, Extras               MENGE PARSEN und SETTEN
                                    if (isUserCompatibleWithProduct(product)) { //TODO: Methode if auslösendes currentListView.get(i).getName() übergeben
                                        tempList.addTextList(resultString);

                                        //Hinzufügen zur arraylist products
                                        for(int u=0; u<currentAvailableProductList.size();u++){
                                            if(currentListView.get(i).equals(currentAvailableProductList.get(u).getName())){
                                                //Hinzufügen der Menge des Produktes zum ProductModel
                                                if(amount!=0){
                                                    currentAvailableProductList.get(u).setMenge((float)amount);
                                                }
                                                temporaryProductList.add(currentAvailableProductList.get(u));
                                            }
                                        }

                                        addSuccess = true;
                                        break check;
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                getString(R.string.product_incompatible_with_user),
                                                Toast.LENGTH_LONG).show();
                                        break check;
                                    }


                                }

                            }
                        }
                        if(!addSuccess){
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.speech_index_missunderstand),
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempList.getTextList());
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
        Intent intent= new Intent(this, ListConfirmationSpeechActivity.class);
        //intent.putExtra(EXTRA_LIST,tempList.getTextList());
        if(tempList!=null){
            intent.putExtra(EXTRA_LIST,tempList);
        }
        if(standingOrderListModel!=null){
            intent.putExtra(EXTRA_MESSAGE,standingOrderListModel);
        }
        if(temporaryProductList!=null){
            intent.putExtra(EXTRA_PRODUCT,temporaryProductList);
        }
        startActivity(intent);
    }
}
