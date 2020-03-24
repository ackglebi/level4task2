package com.example.rockpaperscissors_final

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        gameRepository = GameRepository(this)

        initViews()
    }

    private fun initViews() {
        val rock = findViewById<ImageView>(R.id.ivRock) as ImageView
        val paper = findViewById<ImageView>(R.id.ivPaper) as ImageView
        val scissors = findViewById<ImageView>(R.id.ivScissor) as ImageView

        rock.setOnClickListener {
            checkWinner(0)
        }

        paper.setOnClickListener {
            checkWinner(1)
        }

        scissors.setOnClickListener {
            checkWinner(2)
        }
    }


    /**
     * 0: rock
     * 1: paper
     * 2: scissors
     */
    @SuppressLint("SetTextI18n")
    private fun checkWinner(userPick: Int) {
        var userImagePick = 0
        var computerImagePick = 0
        val computerPick = Random.nextInt(0, 3)

        var testDate = Date()

        when(userPick) {
            0 -> userImagePick = R.drawable.rock
            1 -> userImagePick = R.drawable.paper
            2 -> userImagePick = R.drawable.scissors
        }

        when(computerPick) {
            0 -> computerImagePick = R.drawable.rock
            1 -> computerImagePick = R.drawable.paper
            2 -> computerImagePick = R.drawable.scissors
        }

        if (computerPick == userPick) { // gelijk
            tvWinLose.text = "Draw!"
            ivComputer.setImageResource(computerImagePick)
            ivYou.setImageResource(userImagePick)

            // save game Calendar.getInstance().time
            saveGame(tvWinLose.text.toString(), Calendar.getInstance().time,
                computerPick, userPick)
        } else if ((userPick+1) % 3 == computerPick ) { // computer wint (computerPick - userPick) == 1
            tvWinLose.text = "Computer Wins!"
            ivComputer.setImageResource(computerImagePick)
            ivYou.setImageResource(userImagePick)

            // save game
            saveGame(tvWinLose.text.toString(), Calendar.getInstance().time,
                computerPick, userPick)
        } else { // gebruiker wint
            tvWinLose.text = "You win!"
            ivComputer.setImageResource(computerImagePick)
            ivYou.setImageResource(userImagePick)

            // save game
            saveGame(tvWinLose.text.toString(), Calendar.getInstance().time,
                computerPick, userPick)
        }
    }

    /**
     * Save game to database
     */
    private fun saveGame(winner: String, date: Date, computerPick: Int, userPick: Int) {

        Log.i("Information", "$winner $date $computerPick $userPick")

        CoroutineScope(Dispatchers.Main).launch {

            val game = Game(
                winner = winner,
                date = date,
                computerPick = computerPick,
                userPick = userPick
            )
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.check_history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
