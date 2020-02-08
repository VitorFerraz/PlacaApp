package br.com.vitor.placaapp.ui.game

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.vitor.placaapp.R
import br.com.vitor.placaapp.ui.game.awayteam.AwayTeamFragment
import br.com.vitor.placaapp.ui.game.event.EventFragment
import br.com.vitor.placaapp.ui.game.hometeam.HomeTeamFragment
import br.com.vitor.placaapp.ui.score.ScoreActivity
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), AwayTeamFragment.AwayTeamSelectedListener {

    override fun onAwayTeamSelected(awayName: String) {
        gameViewModel.awayTeam = awayName
        showScoreActivity()
    }

    private lateinit var gameViewModel: GameViewModel
    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.hasExtra("event_name")) {
                gameViewModel.eventName = intent.getStringExtra("event_name")
                showHomeTeamFragment()
            }
            if (intent.hasExtra("home_team")) {
                gameViewModel.homeTeam = intent.getStringExtra("home_team")
                showAwayTeamFragment()
            }
            if (intent.hasExtra("away_team")) {
                gameViewModel.awayTeam = intent.getStringExtra("away_team")
                showScoreActivity()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        showEventFragment()
        ivBack.setOnClickListener {
            onBackPressed()
        }
        initViewModel()
        registerBroadcastReceiver()
    }

    private fun initViewModel() {
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    private fun showHomeTeamFragment() {
        nextFragment(HomeTeamFragment())
    }
    private fun showAwayTeamFragment() {
        nextFragment(AwayTeamFragment())
    }
    private fun nextFragment(fragment: Fragment) {
        val ft = supportFragmentManager?.beginTransaction()
        ft?.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        ft?.replace(R.id.containerGame, fragment)
        ft?.addToBackStack(null)
        ft?.commit()
    }
    private fun showScoreActivity() {
        val nextScreen = Intent(this, ScoreActivity::class.java)
        nextScreen.putExtra("eventName", gameViewModel.eventName)
        nextScreen.putExtra("homeTeam", gameViewModel.homeTeam)
        nextScreen.putExtra("awayTeam", gameViewModel.awayTeam)
        startActivity(nextScreen)
        finish()
    }

    private fun showEventFragment() {
        val ft = supportFragmentManager.beginTransaction()
        if(supportFragmentManager.findFragmentByTag("EventFragment") == null) {
            ft.add(R.id.containerGame, EventFragment())
            ft.commit()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private fun registerBroadcastReceiver() {
        val intentFilter = IntentFilter("FILTER_EVENT")
        intentFilter.addAction("FILTER_HOME_TEAM")
        intentFilter.addAction("FILTER_AWAY_TEAM")
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, intentFilter)
    }
}
