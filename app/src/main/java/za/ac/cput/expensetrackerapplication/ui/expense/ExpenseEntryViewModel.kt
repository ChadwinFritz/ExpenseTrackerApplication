package za.ac.cput.expensetrackerapplication.ui.expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import za.ac.cput.expensetrackerapplication.db.Expense
import za.ac.cput.expensetrackerapplication.repository.ExpensesRepository
import java.text.NumberFormat

//ViewModel to validate and insert Expenses in the Room database.
class ExpenseEntryViewModel(private val expensesRepository: ExpensesRepository) : ViewModel() {

    //Holds current Expense ui state

    var expenseUiState by mutableStateOf(ExpenseUiState())
        private set

    //Updates the [expenseUiState] with the value provided in the argument. This method also triggers
      //a validation for input values.

    fun updateUiState(expenseDetails: ExpenseDetails) {
        expenseUiState =
            ExpenseUiState(expenseDetails = expenseDetails, isEntryValid = validateInput(expenseDetails))
    }

    suspend fun saveExpense() {
        if (validateInput()) {
            expensesRepository.insertExpense(expenseUiState.expenseDetails.toExpense())
        }
    }

    private fun validateInput(uiState: ExpenseDetails = expenseUiState.expenseDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
}

//Represents Ui State for an Expense.

data class ExpenseUiState(
    val expenseDetails: ExpenseDetails = ExpenseDetails(),
    val isEntryValid: Boolean = false
)

data class ExpenseDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

/*Extension function to convert [ExpenseDetails] to [Expense]. If the value of [ExpenseDetails.price] is
 not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 [ExpenseDetails.quantity] is not a valid [Int], then the quantity will be set to 0*/

fun ExpenseDetails.toExpense(): Expense = Expense(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

fun Expense.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

//Extension function to convert [Expense] to [ExpenseUiState]

fun Expense.toExpenseUiState(isEntryValid: Boolean = false): ExpenseUiState = ExpenseUiState(
    expenseDetails = this.toExpenseDetails(),
    isEntryValid = isEntryValid
)

//Extension function to convert [Expense] to [ExpenseDetails]

fun Expense.toExpenseDetails(): ExpenseDetails = ExpenseDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)
