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

    lateinit var spinner: Spinner
    lateinit var list: Array
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView
    lateinit var btnLogin: Button
    private lateinit var nestedScrollView: NestedScrollView
    private val activity = this@MainActivity
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout

    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText

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


        fun emptyInputEditText() {
            TODO("Not yet implemented")
        }

        /**
         * This method is to initialize objects to be used
         */


        /*  recyclerView = findViewById(R1.id.RecyclerView_Acc)


        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager=linearLayoutManager
        spinner=findViewById(R1.id.spinner)
        val adapter =ArrayAdapter.createFromResource(
            this,
            list.R1.country, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter=adapter
        spinner.onItemSelectedListener=this
        setupHyperlink()
    }*/


    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

