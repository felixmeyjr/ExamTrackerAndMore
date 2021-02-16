package com.example.examtrackerandmore.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao // tell room this is a DAO, trigger code generation
interface ExamDataAccessObject {
    // declare methods for database operations

    // this query returns all exams in exams_table AND SORTED ASCENDING! todo confirm
    @Query("SELECT * FROM exam_table") //  ORDER BY daysLeft ASC") todo add back in
    fun getExams(): Flow<List<Exam>> // flow represents a async stream of data (exams), automatically receives values

    // suspend: switch to different thread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exam: Exam)

    @Update
    suspend fun update(exam: Exam)

    @Delete
    suspend fun delete(exam: Exam)

    @Query("DELETE FROM exam_table")
    suspend fun deleteExams()



}