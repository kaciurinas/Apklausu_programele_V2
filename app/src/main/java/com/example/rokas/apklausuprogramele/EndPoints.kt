package com.example.rokas.apklausuprogramele

object EndPoints {
    private val URL_ROOT = "http://kaciurinas.lt/WebApi/v1/?op="
    val URL_ADD_ARTIST = URL_ROOT + "addartist"
    val URL_GET_ARTIST = URL_ROOT + "getartists"
    val URL_GET_SURVEYS = URL_ROOT + "getsurveys"
    val URL_GET_QUESTIONS = URL_ROOT + "getquestions"
}