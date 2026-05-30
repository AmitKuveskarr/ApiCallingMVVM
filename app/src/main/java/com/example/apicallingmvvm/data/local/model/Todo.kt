package com.example.apicallingmvvm.data.local.model

data class Todo(
    val userId: Int? = null,
    val id: Int? = null,
    val title: String,
    val completed: Boolean
)
