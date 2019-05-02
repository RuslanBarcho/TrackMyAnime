package io.vinter.trackmyanime.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import java.util.Objects

import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.ui.profile.ProfileFragment
import io.vinter.trackmyanime.ui.search.SearchFragment
import io.vinter.trackmyanime.ui.top.TopFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var fragmentManager: FragmentManager

    private val mOnNavigationItemSelectedListener = { item: MenuItem ->
        when (item.itemId) {
            R.id.navigation_home -> showAndHideFragments("top", "search", "profile")
            R.id.navigation_dashboard -> showAndHideFragments("search", "top", "profile")
            R.id.navigation_notifications -> showAndHideFragments("profile", "search", "top")
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val navigation = navigation

        fragmentManager = supportFragmentManager
        if ((fragmentManager.findFragmentByTag("top") == null) and (fragmentManager.findFragmentByTag("search") == null)
                and (fragmentManager.findFragmentByTag("profile") == null)) {
            fragmentManager.beginTransaction()
                    .add(R.id.container_content, TopFragment(), "top")
                    .add(R.id.container_content, SearchFragment(), "search")
                    .add(R.id.container_content, ProfileFragment(), "profile")
                    .commit()
            fragmentManager.popBackStackImmediate()
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun showAndHideFragments(tagShow: String, tagHideFirst: String, tagHideSecond: String) {
        fragmentManager.beginTransaction()
                .show(Objects.requireNonNull<Fragment>(fragmentManager.findFragmentByTag(tagShow)))
                .hide(Objects.requireNonNull<Fragment>(fragmentManager.findFragmentByTag(tagHideFirst)))
                .hide(Objects.requireNonNull<Fragment>(fragmentManager.findFragmentByTag(tagHideSecond)))
                .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            22 -> (Objects.requireNonNull<Fragment>(fragmentManager.findFragmentByTag("profile")) as ProfileFragment).update(true)
        }
    }

}
