package io.vinter.trackmyanime.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

import butterknife.ButterKnife
import butterknife.OnClick
import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.ui.profile.ProfileFragment

/**
 * DialogFragment to delete user's title
 */
class DeleteAnimeFragment : DialogFragment() {

    private lateinit var mRootView: View
    internal var malId: Int = 0

    @OnClick(R.id.button_delete)
    internal fun delete() {
        (targetFragment as ProfileFragment).delete(malId)
        dialog.dismiss()
    }

    @OnClick(R.id.button_cancel)
    internal fun cancel() {
        dialog.dismiss()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_delete_anime, container, false)
        ButterKnife.bind(this, mRootView)
        malId = arguments!!.getInt("malId")
        return mRootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setContentView(R.layout.fragment_delete_anime)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.BOTTOM
        window.attributes = wlp
        window.attributes.windowAnimations = R.style.DialogAnimation
        return dialog
    }

}
