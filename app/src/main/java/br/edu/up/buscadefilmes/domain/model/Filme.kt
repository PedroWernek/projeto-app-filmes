package br.edu.up.buscadefilmes.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filmes")
data class Filme(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("titulo") val titulo: String,
    @ColumnInfo("diretor") val diretor: String,
    @ColumnInfo("comentário") val comentario: String,
    @ColumnInfo("nota") val nota: Float
)