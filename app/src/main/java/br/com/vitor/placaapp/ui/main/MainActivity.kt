package br.com.vitor.placaapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.vitor.placaapp.R
import br.com.vitor.placaapp.ui.game.GameActivity
import br.com.vitor.placaapp.ui.game.awayteam.AwayTeamFragment
import br.com.vitor.placaapp.ui.game.awayteam.AwayTeamFragment.AwayTeamSelectedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btNewGame.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
        btExit.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
