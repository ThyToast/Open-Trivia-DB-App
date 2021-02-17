package com.example.opentriviadbdemoapp.utils

enum class State {
    DONE, LOADING, ERROR
}

class NetworkState(val state: State, val message: String? = null) {

    companion object {
        val LOADED = NetworkState(State.DONE)
        val LOADING = NetworkState(State.LOADING)
        fun error(message: String?) = NetworkState(State.ERROR, message)
    }
}