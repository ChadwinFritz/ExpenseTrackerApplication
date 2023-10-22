package za.ac.cput.expensetrackerapplication.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import za.ac.cput.expensetrackerapplication.ExpenseTrackerApplication
import za.ac.cput.expensetrackerapplication.ui.home.HomeViewModel
import za.ac.cput.expensetrackerapplication.ui.expense.ExpenseDetailsViewModel
import za.ac.cput.expensetrackerapplication.ui.expense.ExpenseEditViewModel
import za.ac.cput.expensetrackerapplication.ui.expense.ExpenseEntryViewModel

//Provides Factory to create instance of ViewModel for the entire ExpenseTracker app

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ExpenseEditViewModel
        initializer {
            ExpenseEditViewModel(
                this.createSavedStateHandle(),
                expensesApplication().container.expensesRepository
            )
        }
        // Initializer for ExpenseEntryViewModel
        initializer {
            ExpenseEntryViewModel(expensesApplication().container.expensesRepository)
        }

        // Initializer for ExpenseDetailsViewModel
        initializer {
            ExpenseDetailsViewModel(
                this.createSavedStateHandle(),
                expensesApplication().container.expensesRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(expensesApplication().container.expensesRepository)
        }
    }
}

    //Extension function to queries for [Application] object and returns an instance of
    //[ExpenseTrackerApplication].

fun CreationExtras.expensesApplication(): ExpenseTrackerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ExpenseTrackerApplication)
