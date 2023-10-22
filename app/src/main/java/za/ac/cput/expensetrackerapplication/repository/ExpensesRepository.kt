package za.ac.cput.expensetrackerapplication.repository

import za.ac.cput.expensetrackerapplication.db.Expense
import kotlinx.coroutines.flow.Flow

//Repository that provides insert, update, delete, and retrieve of [Expense] from a given data source.

interface ExpensesRepository {
    //Retrieve all the items from the the given data source.

    fun getAllExpensesStream(): Flow<List<Expense>>

    //Retrieve an item from the given data source that matches with the [id].

    fun getExpenseStream(id: Int): Flow<Expense?>

    //Insert item in the data source

    suspend fun insertExpense(expense: Expense)

    //Delete item from the data source

    suspend fun deleteExpense(expense: Expense)

    //Update item in the data source

    suspend fun updateExpense(expense: Expense)
}

