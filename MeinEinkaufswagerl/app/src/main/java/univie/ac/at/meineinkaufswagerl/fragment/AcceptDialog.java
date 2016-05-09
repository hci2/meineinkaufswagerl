package univie.ac.at.meineinkaufswagerl.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import univie.ac.at.meineinkaufswagerl.R;

/**
 * Created by Philip on 09.05.2016.
 */
public class AcceptDialog extends DialogFragment implements View.OnClickListener{
    private Button buttonAccept, buttonDecline;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acceptdialog, null);
        this.buttonAccept = (Button)view.findViewById(R.id.buttonAccept);
        this.buttonAccept.setOnClickListener(this);
        this.buttonDecline = (Button)view.findViewById(R.id.buttonDecline);
        this.buttonDecline.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
