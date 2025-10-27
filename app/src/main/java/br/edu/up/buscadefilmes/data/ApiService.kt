package br.edu.up.buscadefilmes.data

import br.edu.up.buscadefilmes.domain.model.Filme
import retrofit2.http.GET
import retrofit2.Response


interface ApiService {
    @GET("filmes")
    suspend fun getFilmes(): Response<List<Filme>>
}