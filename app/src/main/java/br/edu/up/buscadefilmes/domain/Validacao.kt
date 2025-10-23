package br.edu.up.buscadefilmes.domain

abstract class Validacao{

    companion object {
        private var id = 0

        fun getId(): Int {
            return id++
        }

        fun hasNullId(id: Int): Boolean{
            return id <= 0
        }

        fun hasCamposEmBranco(titulo: String, diretor: String): Boolean {
            return titulo.isBlank() || diretor.isBlank()
        }

    }
}
