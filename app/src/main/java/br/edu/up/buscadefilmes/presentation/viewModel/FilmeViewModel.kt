package br.edu.up.buscadefilmes.presentation.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.buscadefilmes.data.AppDatabase
import br.edu.up.buscadefilmes.data.RetrofitInstance
import br.edu.up.buscadefilmes.domain.Validacao
import br.edu.up.buscadefilmes.domain.model.Filme
import br.edu.up.buscadefilmes.domain.model.FilmeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmeViewModel(application: Application): AndroidViewModel(application) {
    private val filmeDao: FilmeDao
    private var tag = "FilmeViewModel";

    init {
        val db = AppDatabase.getDatabase(application)
        filmeDao = db.filmeDao()
    }

    fun getAllFilmes(): Flow<List<Filme>> {
        return filmeDao.getAllFilmes()
    }

    fun getFilmesByTitulo(titulo: String): Flow<List<Filme>> {
        return filmeDao.getFilmesByTitulo(titulo)
    }

    fun getFilmesByDiretor(diretor:String): Flow<List<Filme>> {
        return filmeDao.getFilmesByDiretor(diretor)
    }

    fun salvarFilme(titulo: String, diretor: String){
        viewModelScope.launch {
            if (Validacao.hasCamposEmBranco(titulo, diretor)) {
                Log.w("FilmeViewModel", "Preencha todos os campos!")
                return@launch
            }

            val filme = Filme(
                Validacao.getId(),
                titulo,
                diretor
            )

            filmeDao.insert(filme)
            Log.d(tag, "Filme cadastrado com sucesso!")
        }
    }

    fun atualizarFilme(id: Int, titulo: String, diretor: String){
        viewModelScope.launch {
            if (Validacao.hasCamposEmBranco(titulo, diretor)) {
                Log.w(tag,"Ao editar, preencha todos os dados")
                return@launch
            }

            // Correção A: Mover para thread de IO
            // Correção B: Armazenar o resultado do .copy()
            val filmeOriginal = withContext(Dispatchers.IO) {
                filmeDao.getFilmeById(id)
            }
            val filmeAtualizado = filmeOriginal.copy(titulo = titulo, diretor = diretor)
            filmeDao.update(filmeAtualizado)

            Log.d(tag, "Filme atualizado!")
        }
    }

    fun excluirFilme(id: Int){
        viewModelScope.launch {
            if (Validacao.hasNullId(id)) {
                Log.w(tag,"Id inválido")
                return@launch
            }

            // Correção A: Mover para thread de IO
            val filmeParaExcluir = withContext(Dispatchers.IO) {
                filmeDao.getFilmeById(id)
            }
            filmeDao.delete(filmeParaExcluir)

            Log.d(tag, "Filme excluido com sucesso!")
        }
    }

    fun fetchFilmesFromApi() {
        // Garante que a verificação e a chamada de rede ocorram em segundo plano
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // CORREÇÃO: A verificação do banco de dados está DENTRO da corrotina
                val userCount = filmeDao.getAllFilmes().firstOrNull()?.size ?: 0
                if (userCount > 0) {
                    Log.d("FilmeViewModel", "Banco de dados já populado. Não buscando da API.")
                    return@launch
                }

                Log.d("FilmeViewModel", "Banco de dados vazio. Buscando usuários da API.")
                val response = RetrofitInstance.api.getFilmes()

                if (response.isSuccessful && response.body() != null) {
                    val filmesDaApi = response.body()!!
                    filmesDaApi.forEach { filme ->
                        filmeDao.insert(filme)
                    }
                    Log.d("FilmeViewModel", "Usuários da API inseridos no banco com sucesso.")
                } else {
                    Log.e("FilmeViewModel", "Erro na resposta da API: ${response.message()}")
                }
            } catch (e: Exception) {
                // Log genérico para qualquer tipo de erro
                Log.e("FilmeViewModel", "Ocorreu um erro ao buscar dados da API.", e)
            }
        }
    }
}