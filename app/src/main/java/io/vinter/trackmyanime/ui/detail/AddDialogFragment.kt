package io.vinter.trackmyanime.ui.detail


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.vinter.trackmyanime.R

/**
 * Dialog for choose list to add new title
 */
class AddDialogFragment : DialogFragment() {

    @BindView(R.id.radio_list)
    internal lateinit var radioGroup: RadioGroup

    internal lateinit var mRootView: View

    @OnClick(R.id.dialog_add)
    internal fun add() {
        when (radioGroup!!.checkedRadioButtonId) {
            R.id.radio_PTW -> {
                (activity as DetailActivity).addToAnimeList("plan to watch")
                dialog.dismiss()
            }
            R.id.radio_watching -> {
                (activity as DetailActivity).addToAnimeList("watching")
                dialog.dismiss()
            }
            R.id.radio_completed -> {
                (activity as DetailActivity).addToAnimeList("completed")
                dialog.dismiss()
            }
            else -> Toast.makeText(context, "Choose list please", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_add_dialog, container, false)
        ButterKnife.bind(this, mRootView)
        return mRootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setContentView(R.layout.fragment_add_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.BOTTOM
        window.attributes = wlp
        window.attributes.windowAnimations = R.style.DialogAnimation
        return dialog
    }

}
