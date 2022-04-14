package com.eyepax.eyepax.utils

import android.graphics.Color
import android.view.View
import com.eyepax.eyepax.R
import com.google.android.material.snackbar.Snackbar

class SnackBars {
    companion object {
        fun success(view: View) {
            val snackBar = Snackbar.make(
                view, R.string.success,
                Snackbar.LENGTH_SHORT
            )
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(Color.GRAY)
            snackBar.show()
        }

        fun error(view: View) {
            val snackBar = Snackbar.make(
                view, R.string.error_please_try_again,
                Snackbar.LENGTH_SHORT
            )
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(Color.RED)
            snackBar.show()
        }

        fun success(text: String?, view: View) {
            val snackBar = Snackbar.make(
                view, text.toString(),
                Snackbar.LENGTH_SHORT
            )
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(Color.GRAY)
            snackBar.show()
        }

        fun error(text: String?, view: View) {
            val snackBar = Snackbar.make(
                view, text.toString(),
                Snackbar.LENGTH_SHORT
            )
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(Color.RED)
            snackBar.show()
        }
    }
}