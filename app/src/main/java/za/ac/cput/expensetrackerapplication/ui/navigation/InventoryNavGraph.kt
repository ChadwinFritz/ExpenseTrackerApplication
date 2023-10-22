package za.ac.cput.expensetrackerapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import za.ac.cput.expensetrackerapplication.ui.home.HomeDestination
import za.ac.cput.expensetrackerapplication.ui.home.HomeScreen
import za.ac.cput.expensetrackerapplication.ui.expense.ExpenseDetailsDestination
import za.ac.cput.expensetrackerapplication.ui.expense.ExpenseDetailsScreen
import za.ac.cput.expensetrackerapplication.ui.expense.ExpenseEditDestination
import za.ac.cput.expensetrackerapplication.ui.expense.ExpenseEditScreen
import za.ac.cput.expensetrackerapplication.ui.expense.ExpenseEntryDestination
import za.ac.cput.expensetrackerapplication.ui.expense.ExpenseEntryScreen

//Provides Navigation graph for the application.

@Composable
fun ExpensesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = HomeDestination.route, modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(navigateToExpenseEntry = { navController.navigate(ExpenseEntryDestination.route) },
                navigateToExpenseUpdate = {
                    navController.navigate("${ExpenseDetailsDestination.route}/${it}")
                })
        }
        composable(route = ExpenseEntryDestination.route) {
            ExpenseEntryScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            route = ExpenseDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ExpenseDetailsDestination.expenseIdArg) {
                type = NavType.IntType
            })
        ) {
            ExpenseDetailsScreen(
                navigateToEditExpense =
                {
                    navController.navigate("${ExpenseEditDestination.route}/$it")
                },
                navigateBack = { navController.navigateUp() })
        }
        composable(
            route = ExpenseEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ExpenseEditDestination.expenseIdArg) {
                type = NavType.IntType
            })
        ) {
            ExpenseEditScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}
