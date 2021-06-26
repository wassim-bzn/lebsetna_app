 package com.example.pfe.common
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.provider.Settings.Global.getString
import android.text.TextUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.EditText
import com.example.pfe.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
class InputControl {
    companion object {
        fun isValidLength(input_ed: EditText,context:Context): Boolean {
            if (input_ed.getText().toString().trim().isEmpty()) {
                input_ed.setError(context.getString(R.string.emptyField))
                return false
            }
            return true
        }
        fun isValidLengthPassword(input_ed: EditText,context:Context): Boolean {
            if (input_ed.getText().toString().trim().isEmpty()||input_ed.getText().toString().trim().length<6) {
                input_ed.setError(context.getString(R.string.invalidPassword))
                return false
            }
            return true
        }
        fun isValidEmail(target: EditText,context:Context): Boolean {
            if (!TextUtils.isEmpty(target.text.toString()) && Patterns.EMAIL_ADDRESS.matcher(target.text.toString())
                            .matches()
            ) {
                return true
            }
            target.setError(context.getString(R.string.invalidEmail));
            return false
        }

    }
}