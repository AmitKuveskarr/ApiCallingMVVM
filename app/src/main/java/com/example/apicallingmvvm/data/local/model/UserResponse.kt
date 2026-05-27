package com.example.apicallingmvvm.data.local.model

class UserResponse : ArrayList<UserResponse.UserResponseItem>(){
    data class UserResponseItem(
        var `data`: List<Data>,
        var message: String,
        var result: Boolean,
        var servertime: String
    ) {
        data class Data(
            var schemetype: String,
            var slno: Int
        )
    }
}