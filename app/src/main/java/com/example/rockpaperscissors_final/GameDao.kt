package com.example.rockpaperscissors_final

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM gameHistory")
    suspend fun getAllGames() : List<Game>

    @Query("DELETE FROM gameHistory")
    suspend fun deleteAllGames()

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

}