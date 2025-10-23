package br.edu.up.buscadefilmes.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import br.edu.up.buscadefilmes.domain.Validacao
import br.edu.up.buscadefilmes.domain.model.Filme

class FilmeViewModel: ViewModel() {
    var listaFilmes = mutableStateOf(listOf<Filme>())
        private set

    fun salvarFilme(titulo: String, diretor: String): String{
        if (Validacao.hasCamposEmBranco(titulo,diretor)){
            return "Preencha todos os campos!"
        }

        val filme = Filme(
            Validacao.getId(),
            titulo,
            diretor
        )

        listaFilmes.value += filme

        return "Filme salvo com sucesso!"
    }

    fun atualizarFilme(id: Int, titulo: String, diretor: String): String {
        if(Validacao.hasCamposEmBranco(titulo, diretor)){
            return  "Ao editar, preencha todos os dados"
        }

        val filmesAtualizados = listaFilmes.value.map { filme ->
            if(filme.id == id){
                filme.copy(titulo = titulo, diretor = diretor)
            } else {
                filme
            }
        }
        listaFilmes.value = filmesAtualizados

        return "Filme atualizado"
    }

    fun excluirFilme(id: Int): String{
        if(Validacao.hasNullId(id)){
            return "Id inválido"
        }

        listaFilmes.value = listaFilmes.value.filter { it.id != id }

        return "Filme excluido com sucesso"
    }
}