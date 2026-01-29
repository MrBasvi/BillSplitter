package com.example.billsplitter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.billsplitter.ui.screens.InputScreen
import com.example.billsplitter.ui.screens.ResultScreen
import com.example.billsplitter.ui.screens.WelcomeScreen
import com.example.billsplitter.ui.screens.HistoryScreen
import com.example.billsplitter.ui.viewmodel.SplitterViewModel

@Composable
fun SplitterNavHost(
    navController: NavHostController,
    viewModel: SplitterViewModel,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Welcome.route,
        modifier = modifier
    ) {
        composable(NavigationRoutes.Welcome.route) {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate(NavigationRoutes.Input.route)
                },
                onHistoryClick = {
                    navController.navigate(NavigationRoutes.History.route)
                }
            )
        }

        composable(NavigationRoutes.Input.route) {
            InputScreen(
                billAmount = uiState.billAmount?.toString() ?: "",
                numberOfPeople = uiState.numberOfPeople?.toString() ?: "",
                tipPercentage = uiState.tipPercentage,
                onBillAmountChange = viewModel::updateBillAmount,
                onNumberOfPeopleChange = viewModel::updateNumberOfPeople,
                onTipPercentageChange = viewModel::updateTipPercentage,
                onCalculateClick = {
                    val calcId = viewModel.calculateSplit()
                    if (calcId != null) {
                        navController.navigate(NavigationRoutes.Result.createRoute(calcId))
                    }
                },
                isCalculateEnabled = uiState.isInputValid,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = NavigationRoutes.Result.route,
            arguments = listOf(
                navArgument("calcId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val calcId = backStackEntry.arguments?.getString("calcId")
            val calculation = calcId?.let { viewModel.getCalculationById(it) }

            if (calculation != null) {
                ResultScreen(
                    calculation = calculation,
                    onBackToEdit = {
                        navController.popBackStack()
                    },
                    onNewCalculation = {
                        viewModel.resetCalculation()
                        navController.popBackStack(NavigationRoutes.Input.route, inclusive = true)
                        navController.navigate(NavigationRoutes.Input.route)
                    }
                )
            }
        }

        composable(NavigationRoutes.History.route) {
            HistoryScreen(
                calculations = uiState.calculations,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
