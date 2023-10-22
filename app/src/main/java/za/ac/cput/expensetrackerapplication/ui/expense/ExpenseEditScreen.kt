package za.ac.cput.expensetrackerapplication.ui.expense

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import za.ac.cput.expensetrackerapplication.ExpensesTopAppBar
import za.ac.cput.expensetrackerapplication.ui.AppViewModelProvider
import za.ac.cput.expensetrackerapplication.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import za.ac.cput.expensetrackerapplication.R

object ExpenseEditDestination : NavigationDestination {
    override val route = "expense_edit"
    override val titleRes = R.string.edit_expense_title
    const val expenseIdArg = "expenseId"
    val routeWithArgs = "$route/{$expenseIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExpenseEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ExpensesTopAppBar(
                title = stringResource(ExpenseEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ExpenseEntryBody(
            expenseUiState = viewModel.expenseUiState,
            onExpenseValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateExpense()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}


