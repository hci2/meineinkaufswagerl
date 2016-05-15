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
public class DeleteDialog extends DialogFragment implements View.OnClickListener{
    private Button buttonDeleteProduct, buttonBackToAdjust;
    private TextView textProdToDelete;
    private String productName;
    OnDialogButtonEvent listener;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deletedialog, null);
        this.buttonDeleteProduct = (Button)view.findViewById(R.id.buttonDeleteProduct);
        this.buttonDeleteProduct.setOnClickListener(this);
        this.buttonBackToAdjust = (Button)view.findViewById(R.id.buttonBackToAdjust);
        this.buttonBackToAdjust.setOnClickListener(this);
        this.textProdToDelete = (TextView)view.findViewById(R.id.textProdToDelete);
        Bundle bundle = getArguments();
        this.productName = bundle.getString("productName");
        this.textProdToDelete.setText(this.productName);
        return view;
    }

    public interface OnDialogButtonEvent {
        public void DialogButtonEvent(boolean accepted);
    }

    @Override
    public void onClick(View v) {
        if(v == this.buttonDeleteProduct)
            listener.DialogButtonEvent(true);
        else if(v == this.buttonBackToAdjust)
            listener.DialogButtonEvent(false);
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
