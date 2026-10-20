package com.example.sulifa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        val mainView = findViewById<View>(R.id.main)

        val initialLeft = mainView.paddingLeft
        val initialTop = mainView.paddingTop
        val initialRight = mainView.paddingRight
        val initialBottom = mainView.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                initialLeft + bars.left,
                initialTop + bars.top,
                initialRight + bars.right,
                initialBottom + bars.bottom
            )

            insets
        }

        val mode = intent.getStringExtra("EXTRA_MODE") ?: "bot"

        val player1 = intent.getStringExtra("EXTRA_PLAYER1")
            ?: getString(R.string.choice_player_default)

        val player2 = intent.getStringExtra("EXTRA_PLAYER2")
            ?: getString(R.string.choice_bot_name)

        val choice1 = intent.getStringExtra("EXTRA_CHOICE1") ?: ""
        val choice2 = intent.getStringExtra("EXTRA_CHOICE2") ?: ""

        val validChoices = setOf("rock", "scissors", "paper")

        if (choice1 !in validChoices || choice2 !in validChoices) {
            Log.e("ResultActivity", "Получены некорректные данные раунда")
            finish()
            return
        }

        findViewById<TextView>(R.id.tvPlayer1Choice).text =
            getString(
                R.string.result_player_choice,
                player1,
                choiceName(choice1)
            )

        findViewById<TextView>(R.id.tvPlayer2Choice).text =
            getString(
                R.string.result_player_choice,
                player2,
                choiceName(choice2)
            )

        val player1Wins =
            (choice1 == "rock" && choice2 == "scissors") ||
                    (choice1 == "scissors" && choice2 == "paper") ||
                    (choice1 == "paper" && choice2 == "rock")

        val resultText = when {
            choice1 == choice2 -> getString(R.string.result_draw)

            player1Wins -> getString(
                R.string.result_winner,
                player1
            )

            else -> getString(
                R.string.result_winner,
                player2
            )
        }

        findViewById<TextView>(R.id.tvWinner).text = resultText

        findViewById<Button>(R.id.btnMainMenu).setOnClickListener {
            Log.i("ResultActivity", "Возврат в главное меню")

            val menuIntent = Intent(this, MainActivity::class.java)

            menuIntent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
            )

            startActivity(menuIntent)
            finish()
        }

        findViewById<Button>(R.id.btnNewRound).setOnClickListener {
            Log.i("ResultActivity", "Начат новый раунд")

            val roundIntent = Intent(this, ChoiceActivity::class.java)

            roundIntent.putExtra("EXTRA_MODE", mode)
            roundIntent.putExtra("EXTRA_PLAYER1", player1)
            roundIntent.putExtra("EXTRA_PLAYER2", player2)

            startActivity(roundIntent)
            finish()
        }

        Log.d("ResultActivity", "onCreate")
        Log.i("ResultActivity", "Результат раунда: $resultText")
    }

    private fun choiceName(choice: String): String {
        return when (choice) {
            "rock" -> getString(R.string.choice_rock)
            "scissors" -> getString(R.string.choice_scissors)
            "paper" -> getString(R.string.choice_paper)
            else -> error("Неизвестный вариант: $choice")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ResultActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ResultActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ResultActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ResultActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ResultActivity", "onDestroy")
    }
}