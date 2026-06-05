package com.example.apicallingmvvm.model

class UserResponse : ArrayList<UserResponse.UserResponseItem>(){
    data class UserResponseItem(
        val data: List<Data>,
        val message: String,
        val result: Boolean,
        val servertime: String
    ) {
        data class Data(
            val schemetype: String,
            val slno: Int
        )
    }
}
