package io.vinter.trackmyanime.ui.detail;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vinter.trackmyanime.R;

/**
 * Dialog for choose list to add new title
 */
public class AddDialogFragment extends DialogFragment {

    @BindView(R.id.radio_list)
    RadioGroup radioGroup;

    @OnClick(R.id.dialog_add)
    void add(){
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.radio_PTW:
                ((DetailActivity) getActivity()).addToAnimeList("plan to watch");
                dialog.dismiss();
                break;
            case R.id.radio_watching:
                ((DetailActivity) getActivity()).addToAnimeList("watching");
                dialog.dismiss();
                break;
            case R.id.radio_completed:
                ((DetailActivity) getActivity()).addToAnimeList("completed");
                dialog.dismiss();
                break;
                default:
                    Toast.makeText(getContext(), "Choose list please", Toast.LENGTH_SHORT).show();
                    break;
        }
    }

    View mRootView;
    public Dialog dialog;

    public AddDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_add_dialog, container, false);
        ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.fragment_add_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

}
