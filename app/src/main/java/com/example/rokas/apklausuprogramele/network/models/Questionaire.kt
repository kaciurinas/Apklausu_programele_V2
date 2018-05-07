package com.example.rokas.apklausuprogramele.network.models

import java.io.Serializable

data class Questionaire (
        val id: Int,
        val name: String,
        val nof_questions: Int
) : Serializable {

    override fun toString(): String = name
}