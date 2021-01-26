package com.example.examtrackerandmore.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao // tell room this is a DAO, trigger code generation
interface ExamDataAccessObject {
    // declare methods for database operations

    // this query returns all exams in exams_table
    @Query("SELECT * FROM exam_table")
    fun getExams(): Flow<List<Exam>> // flow represents a async stream of data (exams), automatically receives values




    // suspend: switch to different thread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exam: Exam)

    @Update
    suspend fun update(exam: Exam)

    @Delete
    suspend fun delete(exam: Exam)




}