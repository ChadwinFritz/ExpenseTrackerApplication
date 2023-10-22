package za.ac.cput.expensetrackerapplication.db

import za.ac.cput.expensetrackerapplication.repository.ExpensesRepository


//App container for Dependency injection.

interface AppContainer {
    val expensesRepository: ExpensesRepository
}

