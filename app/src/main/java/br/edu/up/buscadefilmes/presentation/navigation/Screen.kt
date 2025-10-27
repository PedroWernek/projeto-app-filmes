package br.edu.up.buscadefilmes.presentation.navigation


sealed class Screen(val route: String) {
    object Cadastro : Screen("cadastrar")
    object Listar : Screen("listar")

}
