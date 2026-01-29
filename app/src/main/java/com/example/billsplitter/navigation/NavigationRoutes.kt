package com.example.billsplitter.navigation

sealed class NavigationRoutes(val route: String) {
    data object Welcome : NavigationRoutes("welcome")
    data object Input : NavigationRoutes("input")
    data object Result : NavigationRoutes("result/{calcId}") {
        fun createRoute(calcId: String) = "result/$calcId"
    }
    data object History : NavigationRoutes("history")
}
