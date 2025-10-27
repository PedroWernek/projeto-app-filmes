package br.edu.up.buscadefilmes.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import br.edu.up.buscadefilmes.presentation.components.bottom_nav.BottomNavItem
import br.edu.up.buscadefilmes.presentation.components.bottom_nav.BottomNavigationBar
import br.edu.up.buscadefilmes.presentation.screen.CadastroFilmeScreen
import br.edu.up.buscadefilmes.presentation.screen.EditarFilmeScreen // 1. Importar nova tela
import br.edu.up.buscadefilmes.presentation.screen.HomeScreen
import br.edu.up.buscadefilmes.presentation.screen.ListarFilmesScreen
import br.edu.up.buscadefilmes.presentation.viewModel.FilmeViewModel

@Composable
fun AppNavGraph(modifier: Modifier, navController: NavHostController) {
    val filmeViewModel: FilmeViewModel = viewModel()
    val items = remember {
        listOf(
            BottomNavItem.Cadastrar,
            BottomNavItem.Home,
            BottomNavItem.Listar,
        )
    }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: items[1].route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = items,
                selectedRoute = currentRoute,
                onItemSelected = { item ->
                    navController.navigate(item.route) {
                        // PopUp to the start destination to avoid building a large stack
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Cadastrar.route) {
                LaunchedEffect(key1 = Unit) {
                    filmeViewModel.fetchFilmesFromApi()
                }
                CadastroFilmeScreen(
                    filmeViewModel = filmeViewModel,
                )
            }
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                )
            }
            composable(BottomNavItem.Listar.route) {
                ListarFilmesScreen(
                    navController = navController, // 2. Passar o NavController
                    filmeViewModel = filmeViewModel
                )
            }

            // 3. Adicionar a nova rota de Edição
            composable(
                route = "editar/{filmeId}",
                arguments = listOf(navArgument("filmeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val filmeId = backStackEntry.arguments?.getInt("filmeId")
                if (filmeId != null) {
                    EditarFilmeScreen(
                        navController = navController,
                        filmeViewModel = filmeViewModel,
                        filmeId = filmeId
                    )
                }
            }
        }
    }
}