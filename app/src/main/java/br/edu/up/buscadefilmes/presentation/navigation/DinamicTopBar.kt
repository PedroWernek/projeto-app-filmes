package br.edu.up.buscadefilmes.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.edu.up.buscadefilmes.presentation.components.bottom_nav.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicTopBar(currentRoute: String?, navController: NavHostController) {
    when (currentRoute) {
        BottomNavItem.Home.route -> TopAppBar(
            title = { Text("Bem-vindo ao App de Filmes") }
        )
        BottomNavItem.Cadastrar.route -> TopAppBar(
            title = { Text("Cadastrar Filme") }
        )
        BottomNavItem.Listar.route -> TopAppBar(
            title = { Text("Lista de Filmes") }
        )
        "editar/{filmeId}" -> TopAppBar(
            title = { Text("Editar Filme") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                }
            }
        )
    }
}