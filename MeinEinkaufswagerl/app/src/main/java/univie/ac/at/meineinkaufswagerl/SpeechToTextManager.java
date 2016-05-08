package univie.ac.at.meineinkaufswagerl;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Philipp on 5/7/2016.
 */
public class SpeechToTextManager extends Activity {
    private int MY_DATA_CHECK_CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String spokenWords;
    private Activity activity;

    public SpeechToTextManager(Activity activity) {
        this.activity = activity;
    }

    public void init(Intent intent){
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                activity.getString(R.string.speech_prompt));
        try {
            activity.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    //check TTS version on executing device
    public void checkSpeech(Activity activity) {
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        activity.startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    setSpokenWords(result);
                }
                break;
            }

        }

    }
    private void setSpokenWords(ArrayList<String> result){
        spokenWords=result.get(0);
    }
    public String getSpokenWords(){
        return spokenWords;
    }


}
