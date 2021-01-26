package com.example.examtrackerandmore.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.example.examtrackerandmore.data.ExamDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module // give dagger instruction how to create dependencies
@InstallIn(ApplicationComponent::class) //object that contains dependencies
object AppModule {

    // create database
    @Provides
    @Singleton // only create one instance of examDatabase
    fun provideDatabase(
        app: Application,
        callback: ExamDatabase.Callback
        // construct database ROOM Documentation
        // ) = Kotlin short return
    ) = Room.databaseBuilder(app, ExamDatabase::class.java, "exam_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    // todo what does this function do?
    // tell dagger to get a examDao, for examDao a examDatabase is necessary
    @Provides
    fun provideExamDao(db: ExamDatabase) = db.examDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob()) // SupervisorJob: if child fails, let other childs running -> Coroutine DOC

}

// todo why? if you want to switch to another DI library?
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class  ApplicationScope