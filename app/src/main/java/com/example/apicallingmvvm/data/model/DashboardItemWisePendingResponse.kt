package com.example.apicallingmvvm.data.model

class DashboardItemWisePendingResponse : ArrayList<DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem>() {
    data class DashboardItemWisePendingResponseItem(
        var `data`: List<Data>,
        var message: String,
        var result: Boolean,
        var servertime: String
    ) {
        data class Data(
            var ismore: Boolean,
            var pendingdata: List<Pendingdata>
        )

        data class Pendingdata(
            var amount: String,
            var colornm: String,
            var itemCode: String,
            var itemName: String,
            var pendingQty: String,
            var poDt: String,
            var poNum: String
        )
    }
}
