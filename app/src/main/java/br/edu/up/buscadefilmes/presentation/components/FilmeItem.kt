package br.edu.up.buscadefilmes.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.edu.up.buscadefilmes.domain.model.Filme

@Composable
fun FilmeItem(
    filme: Filme,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onEditClick() }, // Torna o card clicável para edição
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Coluna para Título e Diretor
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = filme.titulo,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Diretor: ${filme.diretor}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Mostra a nota e o comentário
                Text(
                    text = "Nota: ${filme.nota}",
                    style = MaterialTheme.typography.bodySmall
                )
                if (filme.comentario.isNotBlank()) {
                    Text(
                        text = "Comentário: ${filme.comentario}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2
                    )
                }
            }

            // Botão de Excluir
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Excluir Filme",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}