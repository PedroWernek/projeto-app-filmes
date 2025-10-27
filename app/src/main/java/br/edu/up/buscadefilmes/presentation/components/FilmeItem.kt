package br.edu.up.buscadefilmes.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.edu.up.buscadefilmes.domain.model.Filme

@Composable
fun FilmeItem(filme: Filme) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "Titulo: ${filme.titulo}")
        Text(text = "Diretor: ${filme.diretor}")
        Spacer(modifier = Modifier.height(8.dp))
    }
}
