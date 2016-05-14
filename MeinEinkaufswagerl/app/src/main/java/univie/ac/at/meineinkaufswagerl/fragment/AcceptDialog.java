package univie.ac.at.meineinkaufswagerl.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import univie.ac.at.meineinkaufswagerl.R;

/**
 * Created by Philip on 09.05.2016.
 * // Quelle: http://developer.android.com/guide/components/fragments.html#CommunicatingWithActivity
 */
public class AcceptDialog extends DialogFragment implements View.OnClickListener{
    private Button buttonAccept, buttonDecline;
    private TextView textProductName;
    private String productName;
    private EditText editAnzahl;
    OnDialogButtonEvent listener;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acceptdialog, null);
        this.buttonAccept = (Button)view.findViewById(R.id.buttonAccept);
        this.buttonAccept.setOnClickListener(this);
        this.buttonDecline = (Button)view.findViewById(R.id.buttonDecline);
        this.buttonDecline.setOnClickListener(this);
        this.textProductName = (TextView)view.findViewById(R.id.textProductName);
        this.editAnzahl = (EditText)view.findViewById(R.id.editAnzahl);
        Bundle bundle = getArguments();
        this.productName = bundle.getString("productName");
        this.textProductName.setText(this.productName);
        return view;
    }

    public interface OnDialogButtonEvent {
        public void DialogButtonEvent(boolean accepted, int anzahl);
    }

    @Override
    public void onClick(View v) {
        int anzahl = Integer.parseInt(this.editAnzahl.getText().toString());
        if(v == this.buttonAccept)
            listener.DialogButtonEvent(true,anzahl);
        else if(v == this.buttonDecline)
            listener.DialogButtonEvent(false,0);
        dismiss();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnDialogButtonEvent) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
}
