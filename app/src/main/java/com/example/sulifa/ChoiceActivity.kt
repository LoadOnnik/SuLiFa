package com.example.sulifa

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent

class ChoiceActivity : AppCompatActivity() {

    private lateinit var tvTurn: TextView
    private lateinit var btnRock: Button
    private lateinit var btnScissors: Button
    private lateinit var btnPaper: Button
    private lateinit var btnNext: Button
    private lateinit var btnShowResult: Button

    private var mode = "bot"
    private var player1 = ""
    private var player2 = ""

    private var currentPlayer = 1
    private var choice1 = ""
    private var choice2 = ""
    private var roundReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_choice)

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

        mode = intent.getStringExtra("EXTRA_MODE") ?: "bot"

        player1 = intent.getStringExtra("EXTRA_PLAYER1")
            ?: getString(R.string.choice_player_default)

        player2 = intent.getStringExtra("EXTRA_PLAYER2")
            ?: getString(R.string.choice_bot_name)

        currentPlayer = savedInstanceState?.getInt("currentPlayer") ?: 1
        choice1 = savedInstanceState?.getString("choice1") ?: ""
        choice2 = savedInstanceState?.getString("choice2") ?: ""
        roundReady = savedInstanceState?.getBoolean("roundReady") ?: false

        tvTurn = findViewById(R.id.tvTurn)
        btnRock = findViewById(R.id.btnRock)
        btnScissors = findViewById(R.id.btnScissors)
        btnPaper = findViewById(R.id.btnPaper)
        btnNext = findViewById(R.id.btnNext)
        btnShowResult = findViewById(R.id.btnShowResult)

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            Log.i("ChoiceActivity", "Возврат в главное меню")
            finish()
        }

        btnRock.setOnClickListener {
            selectChoice("rock")
        }

        btnScissors.setOnClickListener {
            selectChoice("scissors")
        }

        btnPaper.setOnClickListener {
            selectChoice("paper")
        }

        btnNext.setOnClickListener {
            if (mode == "bot") {
                choice2 = listOf("rock", "scissors", "paper").random()
                roundReady = true

                Log.i("ChoiceActivity", "Бот сделал ход")
            } else if (currentPlayer == 1) {
                currentPlayer = 2

                Log.i("ChoiceActivity", "Ход передан второму игроку")
            } else {
                roundReady = true

                Log.i("ChoiceActivity", "Оба игрока завершили ход")
            }

            updateScreen()
        }

        btnShowResult.setOnClickListener {
            if (!roundReady) {
                return@setOnClickListener
            }

            Log.i("ChoiceActivity", "Открытие результата")

            val resultIntent = Intent(this, ResultActivity::class.java)

            resultIntent.putExtra("EXTRA_MODE", mode)
            resultIntent.putExtra("EXTRA_PLAYER1", player1)
            resultIntent.putExtra("EXTRA_PLAYER2", player2)
            resultIntent.putExtra("EXTRA_CHOICE1", choice1)
            resultIntent.putExtra("EXTRA_CHOICE2", choice2)

            startActivity(resultIntent)
            finish()
        }

        updateScreen()
        Log.d("ChoiceActivity", "onCreate")
    }

    private fun selectChoice(choice: String) {
        if (currentPlayer == 1) {
            choice1 = choice
        } else {
            choice2 = choice
        }

        Log.i("ChoiceActivity", "Игрок $currentPlayer выбрал вариант")
        updateScreen()
    }

    private fun updateScreen() {
        if (roundReady) {
            tvTurn.setText(R.string.choice_ready)

            btnRock.visibility = View.GONE
            btnScissors.visibility = View.GONE
            btnPaper.visibility = View.GONE
            btnNext.visibility = View.GONE

            btnShowResult.visibility = View.VISIBLE
            return
        }

        val currentName = if (currentPlayer == 1) player1 else player2
        tvTurn.text = getString(R.string.choice_turn, currentName)

        btnRock.visibility = View.VISIBLE
        btnScissors.visibility = View.VISIBLE
        btnPaper.visibility = View.VISIBLE
        btnShowResult.visibility = View.GONE

        val selectedChoice = if (currentPlayer == 1) choice1 else choice2

        btnNext.visibility =
            if (selectedChoice.isEmpty()) View.GONE else View.VISIBLE

        btnRock.alpha = if (selectedChoice == "rock") 0.6f else 1f
        btnScissors.alpha = if (selectedChoice == "scissors") 0.6f else 1f
        btnPaper.alpha = if (selectedChoice == "paper") 0.6f else 1f
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("currentPlayer", currentPlayer)
        outState.putString("choice1", choice1)
        outState.putString("choice2", choice2)
        outState.putBoolean("roundReady", roundReady)

        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        Log.d("ChoiceActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ChoiceActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ChoiceActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ChoiceActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ChoiceActivity", "onDestroy")
    }
}