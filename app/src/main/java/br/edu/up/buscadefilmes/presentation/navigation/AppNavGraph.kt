package br.edu.up.buscadefilmes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.edu.up.buscadefilmes.presentation.screen.CadastroFilmeScreen
import br.edu.up.buscadefilmes.presentation.screen.ListarFilmesScreen
import br.edu.up.buscadefilmes.presentation.viewModel.FilmeViewModel

@Composable
fun AppNavGraph(modifier: Modifier, navController: NavHostController) {
    val filmeViewModel: FilmeViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Cadastro.route
    ) {
        composable(Screen.Cadastro.route) {
            LaunchedEffect(key1 = Unit) {
                filmeViewModel.fetchFilmesFromApi()
            }
            CadastroFilmeScreen(
                filmeViewModel = filmeViewModel,
                navController = navController,
            )
        }
        composable(Screen.Listar.route) {
            ListarFilmesScreen(
                navController = navController,
                filmeViewModel = filmeViewModel
            )
        }
    }
}