package br.edu.up.buscadefilmes.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.edu.up.buscadefilmes.domain.model.Filme
import br.edu.up.buscadefilmes.presentation.components.StarRatingBar
import br.edu.up.buscadefilmes.presentation.viewModel.FilmeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarFilmeScreen(
    navController: NavController,
    filmeViewModel: FilmeViewModel,
    filmeId: Int
) {
    // Observa o filme específico do banco de dados
    val filmeState by filmeViewModel.getFilmeByIdFlow(filmeId).collectAsState(initial = null)

    var titulo by remember { mutableStateOf("") }
    var diretor by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf("") }
    var nota by remember { mutableFloatStateOf(1.0F) }

    // Efeito para carregar os dados do filme nos campos de texto
    // O 'key1 = filmeState' garante que isso rode sempre que o filmeState mudar
    LaunchedEffect(filmeState) {
        filmeState?.let { filme ->
            titulo = filme.titulo
            diretor = filme.diretor
            comentario = filme.comentario
            nota = filme.nota
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Filme") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        // Mostra um indicador de loading enquanto o filmeState for nulo
        if (filmeState == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            // Quando o filme for carregado, mostra os campos
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Titulo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = diretor,
                    onValueChange = { diretor = it },
                    label = { Text("Diretor") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = comentario,
                    onValueChange = { comentario = it },
                    label = { Text("Comentario") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Nota")
                    StarRatingBar(maxStars = 5, rating = nota, onRatingChanged = { nota = it })
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (titulo.isNotBlank() && diretor.isNotBlank()) {
                            // Chama a função ATUALIZAR
                            filmeViewModel.atualizarFilme(filmeId, titulo, diretor, comentario, nota)
                            // Volta para a tela anterior (Lista)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = titulo.isNotBlank() && diretor.isNotBlank()
                ) {
                    Text("Salvar Alterações")
                }
            }
        }
    }
}