package br.edu.up.buscadefilmes.domain.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmeDao {

    @Insert
    suspend fun insert(filme: Filme)
    @Update
    suspend fun update(filme: Filme)
    @Delete
    suspend fun delete(filme: Filme)
    @Query("SELECT * FROM filmes ORDER BY titulo ASC")
    fun getAllFilmes(): Flow<List<Filme>>
    @Query("SELECT * FROM filmes WHERE id = :idFilme")
    fun getFilmeById(idFilme: Int): Filme
    @Query("SELECT * FROM filmes WHERE titulo LIKE :nomeDiretor")
    fun getFilmesByDiretor(nomeDiretor: String): Flow<List<Filme>>
    @Query("SELECT * FROM filmes WHERE titulo LIKE :tituloFilme")
    fun getFilmesByTitulo(tituloFilme: String): Flow<List<Filme>>

}