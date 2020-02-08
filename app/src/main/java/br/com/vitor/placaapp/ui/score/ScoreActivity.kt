package br.com.vitor.placaapp.ui.score

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.vitor.placaapp.R
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {
    private lateinit var scoreViewModel: ScoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        initViewModel()
        setExtras()
        registerListener()
        registerObserver()

    }

    private fun registerObserver() {
        scoreViewModel.goalAway.observe(this, Observer { tvAwayGoal.text = it.toString() })
        scoreViewModel.goalHome.observe(this, Observer { tvGoalHome.text = it.toString() })
    }

    private fun registerListener() {
        btGoalHome.setOnClickListener { scoreViewModel.goalHome() }
        btAwayGoal.setOnClickListener { scoreViewModel.goalAway() }
        btRestart.setOnClickListener {
            scoreViewModel.restartGame()
            resetChron()
        }
        btEndGame.setOnClickListener { finish() }
        btCron.setOnClickListener {
            if (chronometer.isRunning) {
                startChron()
            } else {
                stopChron()
            }
        }
    }

    private fun resetChron() {
        chronometer.reset()
        btCron.text = "Iniciar Partida"
    }

    private fun stopChron() {
        btCron.text = "Parar"
        chronometer.start()
    }

    private fun startChron() {
        btCron.text = "Iniciar Partida"
        chronometer.stop()
    }

    private fun initViewModel() {
        scoreViewModel = ViewModelProviders.of(this).get(ScoreViewModel::class.java)
    }

    private fun setExtras() {
        tvEventName.text = intent.extras?.getString("eventName")
        tvHomeName.text = intent.extras?.getString("homeTeam")
        tvAwayName.text = intent.extras?.getString("awayTeam")
    }
}