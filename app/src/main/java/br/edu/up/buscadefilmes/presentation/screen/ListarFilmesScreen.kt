package br.edu.up.buscadefilmes.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.edu.up.buscadefilmes.presentation.components.FilmeItem
import br.edu.up.buscadefilmes.presentation.viewModel.FilmeViewModel

@Composable
fun ListarFilmesScreen(
    navController: NavController,
    filmeViewModel: FilmeViewModel,
    modoDeletar: Boolean,
) {

    val filmes by filmeViewModel.getAllFilmes().collectAsState(initial = emptyList())
    Column(modifier = Modifier.fillMaxSize()) {
        if (filmes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Nenhum filme cadastrado.")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filmes) { filme ->
                    FilmeItem(
                        filme = filme,
                        onEditClick = {
                            navController.navigate("editar/${filme.id}")
                        },
                        modoDeletar = modoDeletar,
                        onDeleteClick = {
                            filmeViewModel.excluirFilme(filme.id)
                        }
                    )
                }
            }
        }
    }
}