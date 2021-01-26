package com.example.examtrackerandmore.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.examtrackerandmore.dependencyinjection.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Exam::class], version = 1)
abstract class ExamDatabase : RoomDatabase() {

    // abstract: no function body
    abstract fun examDao(): ExamDataAccessObject

    // Dummy data for recycler view
    // @Inject tells dagger how to create an instance
    class Callback @Inject constructor(
        private val database: Provider<ExamDatabase>, // avoid circular dependency
        @ApplicationScope private val applicationScope: CoroutineScope //make code more clear
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // db operations
            // create object
            val dao = database.get().examDao()

            // due to using suspend function, we need to use coroutines (lightweight threads, code
            // that knows how to append execution and let the program to continue to execute
            // something else)

            // create own coroutine scope and add example items
            applicationScope.launch {
                dao.insert(Exam("Elektrotechnik 1", "2021-03-31", 12, 10))
                dao.insert(Exam("BWL 4", "2021-04-15", 8, 1))
            }


        }
    }
}