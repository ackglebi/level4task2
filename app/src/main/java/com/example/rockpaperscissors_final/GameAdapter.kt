package com.example.rockpaperscissors_final

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_main.view.*
import kotlinx.android.synthetic.main.item_history_view.view.*

class GameAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder> () {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(game : Game) {

            /**
             * TODO
             * Deze switch statements global maken?
             */
            when(game.userPick) {
                0 -> game.userPick = R.drawable.rock
                1 -> game.userPick = R.drawable.paper
                2 -> game.userPick = R.drawable.scissors
            }

            when(game.computerPick) {
                0 -> game.computerPick = R.drawable.rock
                1 -> game.computerPick = R.drawable.paper
                2 -> game.computerPick = R.drawable.scissors
            }

            itemView.tvHistoryWinLose.text = game.winner
            itemView.tvHistoryDate.text = game.date.toString()
            itemView.ivHistoryComputer.setImageResource(game.computerPick)
            itemView.ivHistoryYou.setImageResource(game.userPick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }
}