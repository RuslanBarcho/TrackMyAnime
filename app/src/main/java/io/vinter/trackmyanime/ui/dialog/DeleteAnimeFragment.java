package io.vinter.trackmyanime.ui.dialog;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.ui.profile.ProfileFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteAnimeFragment extends DialogFragment {

    View mRootView;
    Dialog dialog;
    int malId;

    @OnClick(R.id.button_delete)
    void delete(){
        ((ProfileFragment) getTargetFragment()).delete(malId);
        dialog.dismiss();
    }

    @OnClick(R.id.button_cancel)
    void cancel(){
        dialog.dismiss();
    }

    public DeleteAnimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_delete_anime, container, false);
        ButterKnife.bind(this,mRootView);
        malId = getArguments().getInt("malId");
        return mRootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.fragment_delete_anime);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

}
