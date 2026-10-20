package com.example.sulifa

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.util.Log

class MainActivity : AppCompatActivity() {
    private var selectedMode = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "onCreate: главное меню создано")

        val mainView = findViewById<View>(R.id.main)

        val initialLeft = mainView.paddingLeft
        val initialTop = mainView.paddingTop
        val initialRight = mainView.paddingRight
        val initialBottom = mainView.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                initialLeft + systemBars.left,
                initialTop + systemBars.top,
                initialRight + systemBars.right,
                initialBottom + systemBars.bottom
            )

            insets
        }

        val btnExit = findViewById<Button>(R.id.btnExit)
        val btnBotMode = findViewById<Button>(R.id.btnBotMode)
        val btnPlayersMode = findViewById<Button>(R.id.btnPlayersMode)

        val etPlayer1 = findViewById<EditText>(R.id.etPlayer1)
        val etPlayer2 = findViewById<EditText>(R.id.etPlayer2)

        val btnStart = findViewById<Button>(R.id.btnStart)

        btnExit.setOnClickListener {
            finish()
        }

        btnBotMode.setOnClickListener {
            Log.i("MainActivity", "Выбран режим: игрок против бота")
            selectedMode = "bot"

            etPlayer1.visibility = View.VISIBLE
            etPlayer2.visibility = View.GONE
            btnStart.visibility = View.VISIBLE
        }

        btnPlayersMode.setOnClickListener {
            Log.i("MainActivity", "Выбран режим: два игрока")
            selectedMode = "players"

            etPlayer1.visibility = View.VISIBLE
            etPlayer2.visibility = View.VISIBLE
            btnStart.visibility = View.VISIBLE
        }
        btnStart.setOnClickListener {
            Log.i("MainActivity", "Нажата кнопка начала игры")
            val player1 = etPlayer1.text.toString().trim()
            val player2 = etPlayer2.text.toString().trim()

            if (player1.isEmpty()) {
                Log.w("MainActivity", "Начало игры отменено: пустой никнейм игрока 1")
                etPlayer1.error = getString(R.string.main_name_required)
                etPlayer1.requestFocus()
                return@setOnClickListener
            }

            if (selectedMode == "players" && player2.isEmpty()) {
                Log.w("MainActivity", "Начало игры отменено: пустой никнейм игрока 2")
                etPlayer2.error = getString(R.string.main_name_required)
                etPlayer2.requestFocus()
                return@setOnClickListener
            }

            val gameIntent = Intent(this, ChoiceActivity::class.java)

            gameIntent.putExtra("EXTRA_MODE", selectedMode)
            gameIntent.putExtra("EXTRA_PLAYER1", player1)
            gameIntent.putExtra(
                "EXTRA_PLAYER2",
                if (selectedMode == "bot") getString(R.string.choice_bot_name) else player2
            )

            Log.i("MainActivity", "Переход в ChoiceActivity: режим=$selectedMode")

            startActivity(gameIntent)
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }
}