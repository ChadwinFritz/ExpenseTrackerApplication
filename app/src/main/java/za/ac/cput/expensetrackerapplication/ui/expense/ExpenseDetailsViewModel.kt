package za.ac.cput.expensetrackerapplication.ui.expense

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import za.ac.cput.expensetrackerapplication.repository.ExpensesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//ViewModel to retrieve, update and delete an item from the [ExpensesRepository]'s data source.

class ExpenseDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val expensesRepository: ExpensesRepository,
) : ViewModel() {

    private val expenseId: Int = checkNotNull(savedStateHandle[ExpenseDetailsDestination.expenseIdArg])
    val uiState: StateFlow<ExpenseDetailsUiState> =
        expensesRepository.getExpenseStream(expenseId)
            .filterNotNull()
            .map {
                ExpenseDetailsUiState(outOfStock = it.quantity <= 0, expenseDetails = it.toExpenseDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ExpenseDetailsUiState()
            )

    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentExpense = uiState.value.expenseDetails.toExpense()
            if (currentExpense.quantity > 0) {
                expensesRepository.updateExpense(currentExpense.copy(quantity = currentExpense.quantity - 1))
            }
        }
    }

    //Deletes the item from the [ExpensesRepository]'s data source.

    suspend fun deleteExpense() {
        expensesRepository.deleteExpense(uiState.value.expenseDetails.toExpense())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for ItemDetailsScreen
 */
data class ExpenseDetailsUiState(
    val outOfStock: Boolean = true,
    val expenseDetails: ExpenseDetails = ExpenseDetails()
)
