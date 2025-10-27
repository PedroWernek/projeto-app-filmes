package br.edu.up.buscadefilmes.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.edu.up.buscadefilmes.presentation.components.FilmeItem
import br.edu.up.buscadefilmes.presentation.viewModel.FilmeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarFilmesScreen(navController: NavController, filmeViewModel: FilmeViewModel) {

    val filmes by filmeViewModel.getAllFilmes().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Filmes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(filmes) { filme ->
                    FilmeItem(filme)
                }
            }
        }
    }
}