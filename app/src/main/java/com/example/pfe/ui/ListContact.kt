package com.example.pfe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pfe.R
import com.example.pfe.RecyclerAdapter
import com.example.pfe.adapter.PostsAdapter

class ListContact : AppCompatActivity() {

 lateinit var contactViewModel: PostsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ecran_d_accueil)
        contactViewModel.getItem(5)
    }
}