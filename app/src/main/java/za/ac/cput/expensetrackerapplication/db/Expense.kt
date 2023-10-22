package za.ac.cput.expensetrackerapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey


 // Entity data class represents a single row in the database.

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)
