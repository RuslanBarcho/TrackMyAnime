package io.vinter.trackmyanime.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Objects;

import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.ui.profile.ProfileFragment;
import io.vinter.trackmyanime.ui.search.SearchFragment;
import io.vinter.trackmyanime.ui.top.TopFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    MainViewModel viewModel;
    FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =  item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showAndHideFragments("top", "search", "profile");
                    return true;
                case R.id.navigation_dashboard:
                    showAndHideFragments("search", "top", "profile");
                    return true;
                case R.id.navigation_notifications:
                    showAndHideFragments("profile", "search", "top");
                    return true;
            }
            return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("top") == null & fragmentManager.findFragmentByTag("search") == null
                & fragmentManager.findFragmentByTag("profile") == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container_content, new TopFragment(), "top")
                    .add(R.id.container_content, new SearchFragment(), "search")
                    .add(R.id.container_content, new ProfileFragment(), "profile")
                    .commit();
            fragmentManager.popBackStackImmediate();
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void showAndHideFragments(String tagShow, String tagHideFirst, String tagHideSecond){
        fragmentManager.beginTransaction()
                .show(Objects.requireNonNull(fragmentManager.findFragmentByTag(tagShow)))
                .hide(Objects.requireNonNull(fragmentManager.findFragmentByTag(tagHideFirst)))
                .hide(Objects.requireNonNull(fragmentManager.findFragmentByTag(tagHideSecond)))
                .commit();
    }

}
