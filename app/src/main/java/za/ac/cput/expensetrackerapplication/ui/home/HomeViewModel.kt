package za.ac.cput.expensetrackerapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import za.ac.cput.expensetrackerapplication.db.Expense
import za.ac.cput.expensetrackerapplication.repository.ExpensesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

//ViewModel to retrieve all Expenses in the Room database.

class HomeViewModel(expensesRepository: ExpensesRepository) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        expensesRepository.getAllExpensesStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

//Ui State for HomeScreen

data class HomeUiState(val expenseList: List<Expense> = listOf())
