package io.vinter.trackmyanime.ui.dialog;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.entity.animelist.AnimeListItem;
import io.vinter.trackmyanime.utils.AnimeEditListener;

/**
 * Fragment for edit user's watched EPS manually
 */
public class EditEpisodesFragment extends DialogFragment {

    View mRootView;
    public Dialog dialog;
    AnimeListItem anime;
    AnimeEditListener listener;

    @BindView(R.id.edit_episodes)
    Button episodes;

    @BindView(R.id.edit_watched_episodes)
    EditText watchedEpisodes;

    @BindView(R.id.edit_status)
    TextView status;

    @OnClick(R.id.edit_update)
    void edit(){
        Integer newEpisodes = Integer.valueOf(watchedEpisodes.getText().toString());
        if (anime != null & listener != null){
            if ((anime.getEps() != 0 & anime.getEps() >= newEpisodes) | (anime.getEps() == 0)){
                listener.onEdit(anime.getMalId(),newEpisodes - anime.getWatchedEps());
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Too many episodes :/", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public EditEpisodesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_edit_episodes, container, false);
        ButterKnife.bind(this, mRootView);
        anime = (AnimeListItem) getArguments().getSerializable("anime");
        episodes.setText(String.valueOf(anime.getEps()));
        watchedEpisodes.setHint(String.valueOf(anime.getWatchedEps()));
        status.setText(String.valueOf("Status: " + anime.getStatus()));
        watchedEpisodes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")){
                    if (Integer.valueOf(editable.toString()) >= anime.getEps()) status.setText(String.valueOf("Status: completed"));
                    else status.setText(String.valueOf("Status: watching"));
                }
            }
        });
        return mRootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.fragment_edit_episodes);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

    public void setUpdateListener(AnimeEditListener listener){
        this.listener = listener;
    }

}
