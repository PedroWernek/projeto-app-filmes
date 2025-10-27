package br.edu.up.buscadefilmes.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import br.edu.up.buscadefilmes.presentation.screen.EditarFilmeScreen
import br.edu.up.buscadefilmes.presentation.screen.HomeScreen
import br.edu.up.buscadefilmes.presentation.screen.ListarFilmesScreen
import br.edu.up.buscadefilmes.presentation.viewModel.FilmeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(navController: NavHostController) {
    val filmeViewModel: FilmeViewModel = viewModel()
    val items = remember {
        listOf(
            BottomNavItem.Cadastrar,
            BottomNavItem.Home,
            BottomNavItem.Listar,
        )
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val rotasComBottomBar = items.map { it.route }

    var modoDeletar by remember { mutableStateOf(false) }

    LaunchedEffect(currentRoute) {
        if (currentRoute != BottomNavItem.Listar.route) {
            modoDeletar = false
        }
    }

    Scaffold(
        topBar = {
            DynamicTopBar(currentRoute = currentRoute, navController = navController)
        },
        bottomBar = {
            if (currentRoute in rotasComBottomBar) {
                BottomNavigationBar(
                    items = items,
                    selectedRoute = currentRoute,
                    onItemSelected = { item ->
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (currentRoute == BottomNavItem.Listar.route) {
                FloatingActionButton(
                    onClick = { modoDeletar = !modoDeletar }
                ) {
                    val icon = if (modoDeletar) Icons.Filled.Close else Icons.Filled.Delete
                    val contentDescription = if (modoDeletar) "Sair do modo deletar" else "Ativar modo deletar"
                    Icon(icon, contentDescription = contentDescription)
                }
            }
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
                HomeScreen()
            }
            composable(BottomNavItem.Listar.route) {
                ListarFilmesScreen(
                    navController = navController,
                    filmeViewModel = filmeViewModel,
                    modoDeletar = modoDeletar
                )
            }
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

