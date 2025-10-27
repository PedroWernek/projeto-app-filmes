package br.edu.up.buscadefilmes.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.edu.up.buscadefilmes.presentation.components.StarRatingBar
import br.edu.up.buscadefilmes.presentation.viewModel.FilmeViewModel
import br.edu.up.buscadefilmes.R
import br.edu.up.buscadefilmes.domain.Validacao

@Composable
fun CadastroFilmeScreen(filmeViewModel: FilmeViewModel) {
    var titulo by remember { mutableStateOf("") }
    var diretor by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf("") }
    var nota by remember { mutableFloatStateOf(1.0F) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.tela_inicial),
            contentDescription = "Logo do App de Filmes",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
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
            modifier = Modifier
                .fillMaxWidth()
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
                    filmeViewModel.salvarFilme(titulo, diretor, comentario, nota)
                    titulo = ""
                    diretor = ""
                    comentario = ""
                    nota = 0f
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !Validacao.hasCamposEmBranco(titulo,diretor,comentario)

        ) {
            Text("Cadastrar")
        }
    }
}