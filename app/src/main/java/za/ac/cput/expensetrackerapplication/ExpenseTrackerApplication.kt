package za.ac.cput.expensetrackerapplication

import android.app.Application
import za.ac.cput.expensetrackerapplication.db.AppContainer

class ExpenseTrackerApplication : Application() {

    //AppContainer instance used by the rest of classes to obtain dependencies

    lateinit var container: AppContainer

}
