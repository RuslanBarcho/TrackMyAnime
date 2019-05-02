package io.vinter.trackmyanime.utils

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import java.util.ArrayList

import io.vinter.trackmyanime.ui.profile.ProfileListFragment

class ProfileViewPagerAdapter(fm: FragmentManager, private val fragments: ArrayList<ProfileListFragment>, private val titles: Array<String>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position < titles.size)
            titles[position]
        else
            "null"
    }
}
