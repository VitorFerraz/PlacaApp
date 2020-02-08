package br.com.vitor.placaapp.ui.game.awayteam


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import br.com.vitor.placaapp.R
import br.com.vitor.placaapp.ui.score.ScoreActivity
import kotlinx.android.synthetic.main.fragment_away_team.*

/**
 * A simple [Fragment] subclass.
 */
class AwayTeamFragment : Fragment() {
    interface AwayTeamSelectedListener {
        fun onAwayTeamSelected(awayName: String)
    }
    private var listener: AwayTeamSelectedListener? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AwayTeamSelectedListener) {
            listener = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_away_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btNextStep.setOnClickListener {
            //sendAwayTeamName()
            sendAwayTeamNameLister()
        }
    }
    private fun nextScreen() {
        sendAwayTeamName()
        val nextScreen = Intent(activity, ScoreActivity::class.java)
        startActivity(nextScreen)
        activity?.finish()
    }

    private fun sendAwayTeamName() {
        val intent = Intent("FILTER_AWAY_TEAM")
        intent.putExtra("away_team", inputAwayTeam.text.toString())
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }
    //Outra forma de enviar dados (delegate)
    private fun sendAwayTeamNameLister() {
        listener?.onAwayTeamSelected(inputAwayTeam.text.toString())
    }
}
