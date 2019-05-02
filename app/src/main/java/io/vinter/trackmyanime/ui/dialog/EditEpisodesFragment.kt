package io.vinter.trackmyanime.ui.dialog


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.entity.animelist.AnimeListItem
import kotlin.reflect.KFunction2

/**
 * Fragment for edit user's watched EPS manually
 */
class EditEpisodesFragment : DialogFragment() {

    private lateinit var mRootView: View
    internal lateinit var anime: AnimeListItem
    internal lateinit var listener: KFunction2<@ParameterName(name = "malId") Int, @ParameterName(name = "episodes") Int, Unit>

    @BindView(R.id.edit_episodes)
    internal lateinit var episodes: Button

    @BindView(R.id.edit_watched_episodes)
    internal lateinit var watchedEpisodes: EditText

    @BindView(R.id.edit_status)
    internal lateinit var status: TextView

    @OnClick(R.id.edit_update)
    internal fun edit() {
        val newEpisodes = Integer.valueOf(watchedEpisodes.text.toString())
        if ((anime != null) and (listener != null)) if ((anime.eps != 0) and (anime.eps!! >= newEpisodes) or (anime.eps == 0)) {
            listener(anime.malId!!, newEpisodes - anime.watchedEps!!)
            dialog.dismiss()
        } else {
            Toast.makeText(context, "Too many episodes :/", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_edit_episodes, container, false)
        ButterKnife.bind(this, mRootView)
        anime = arguments!!.getSerializable("anime") as AnimeListItem
        episodes.text = anime.eps.toString()
        watchedEpisodes.hint = anime.watchedEps.toString()
        status.text = "Status: " + anime.status
        watchedEpisodes.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString() != "") {
                    if (Integer.valueOf(editable.toString()) >= anime.eps!!)
                        status.text = "Status: completed"
                    else
                        status.text = "Status: watching"
                }
            }
        })
        return mRootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setContentView(R.layout.fragment_edit_episodes)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.BOTTOM
        window.attributes = wlp
        window.attributes.windowAnimations = R.style.DialogAnimation
        return dialog
    }

    fun setUpdateListener(listener: KFunction2<@ParameterName(name = "malId") Int, @ParameterName(name = "episodes") Int, Unit>) {
        this.listener = listener
    }

}
