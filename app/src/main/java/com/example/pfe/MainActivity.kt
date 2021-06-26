package com.example.pfe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pfe.common.InputControl
import com.example.pfe.ui.activities.RegistrationActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.reflect.Array

class MainActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private lateinit var appCompatButtonLogin: AppCompatButton


    private lateinit var textViewLinkRegister: AppCompatTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()

        appCompatButtonLogin.setOnClickListener {
            if  (InputControl.isValidLength(ed_email,this)and (InputControl.isValidLength(ed_password,this))) {
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                this.startActivity(intent)
            }
        }
        textViewLinkRegister.setOnClickListener {
            if(InputControl.isValidEmail(ed_email,this)and(InputControl.isValidLengthPassword(ed_password,this))) {
                val intentRegister = Intent(this@MainActivity, RegistrationActivity::class.java)
                this.startActivity(intentRegister)
            }
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

