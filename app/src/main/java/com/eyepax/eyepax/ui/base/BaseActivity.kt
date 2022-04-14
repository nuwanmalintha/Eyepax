package com.eyepax.eyepax.ui.base

import android.app.Application
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eyepax.eyepax.data.local.AppDatabase
import com.eyepax.eyepax.data.repository.RoomDatabaseRepository

open class BaseActivity : AppCompatActivity() {

    lateinit var roomDatabase: AppDatabase
    lateinit var roomDatabaseRepository: RoomDatabaseRepository
    lateinit var roomDatabaseViewModelFactory: RoomDatabaseViewModelFactory
    lateinit var roomViewModel: RoomDatabaseViewModel

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    protected fun initValue(application: Application) {

        roomDatabase = AppDatabase.getDatabase(application)
        roomDatabaseRepository = RoomDatabaseRepository(roomDatabase)
        roomDatabaseViewModelFactory = RoomDatabaseViewModelFactory(roomDatabaseRepository)
        roomViewModel = ViewModelProvider(
            this.viewModelStore,
            roomDatabaseViewModelFactory
        )[RoomDatabaseViewModel::class.java]
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}