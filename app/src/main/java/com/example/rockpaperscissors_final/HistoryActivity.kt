package com.example.rockpaperscissors_final

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {

    private lateinit var gameRepository: GameRepository
    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar_history)

        gameRepository = GameRepository(this)

        initViews()
    }

    private fun initViews() {
        rvGames.layoutManager = LinearLayoutManager(this@HistoryActivity,
            RecyclerView.VERTICAL, false)
        rvGames.adapter = gameAdapter

        rvGames.addItemDecoration(
            DividerItemDecoration(this@HistoryActivity,
            RecyclerView.VERTICAL)
        )

        getGamesFromDatabase()
    }

    private fun getGamesFromDatabase() {

        CoroutineScope(Dispatchers.Main).launch {

            val gameList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }

            this@HistoryActivity.games.clear()
            this@HistoryActivity.games.addAll(gameList)
            this@HistoryActivity.gameAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.go_back -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.delete_games -> {
                deleteGames()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteGames() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGamesFromDatabase()
        }
    }
}