package br.edu.up.buscadefilmes.domain

abstract class Validacao{

    companion object {
        private var id = 0

        fun hasNullId(id: Int): Boolean{
            return id <= 0
        }

        fun hasCamposEmBranco(titulo: String, diretor: String, comentario: String): Boolean {
            return titulo.isBlank() || diretor.isBlank() || comentario.isBlank()
        }

    }
}
