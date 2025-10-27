package br.edu.up.buscadefilmes.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.edu.up.buscadefilmes.presentation.components.FilmeItem
import br.edu.up.buscadefilmes.presentation.viewModel.FilmeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarFilmesScreen(
    navController: NavController, // 1. Adicione o NavController
    filmeViewModel: FilmeViewModel
) {

    val filmes by filmeViewModel.getAllFilmes().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Filmes") },
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            // 2. Adicione a validação de lista vazia
            if (filmes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Nenhum filme cadastrado.")
                }
            } else {
                LazyColumn {
                    items(filmes) { filme ->
                        FilmeItem(
                            filme = filme,
                            // 3. Adicione os eventos de clique
                            onEditClick = {
                                // Navega para a nova tela de edição
                                navController.navigate("editar/${filme.id}")
                            },
                            onDeleteClick = {
                                // Chama a função de excluir no ViewModel
                                filmeViewModel.excluirFilme(filme.id)
                            }
                        )
                    }
                }
            }
        }
    }
}