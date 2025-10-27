package br.edu.up.buscadefilmes.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.edu.up.buscadefilmes.presentation.viewModel.FilmeViewModel
import kotlin.text.isNotBlank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroFilmeScreen(navController: NavController, filmeViewModel: FilmeViewModel) {
    var titulo by remember { mutableStateOf("") }
    var diretor by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastrar Usuário") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Titulo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = diretor,
                onValueChange = { diretor = it },
                label = { Text("Diretor") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (titulo.isNotBlank() && diretor.isNotBlank()) {
                        filmeViewModel.salvarFilme(titulo, diretor)
                        titulo = ""
                        diretor = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = titulo.isNotBlank() && diretor.isNotBlank()

            ) {
                Text("Cadastrar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("listar")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lista de usuários")
            }
        }
    }
}