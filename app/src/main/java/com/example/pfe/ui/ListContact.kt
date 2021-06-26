package com.example.pfe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pfe.R
import com.example.pfe.RecyclerAdapter
import com.example.pfe.adapter.PostsAdapter

class ListContact : AppCompatActivity() {

 lateinit var contactViewModel: PostsAdapter
    lateinit var lvContact: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ecran_d_accueil)

      /*  val context = this
        lvContact = findViewById(R.id.RecyclerView_Acc)
        lvContact.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        contactViewModel = ViewModelProviders.of(this,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return contactViewModel as T
            }
        }).get(contactViewModel::class.java)
        val observer = Observer<List<Habits>> {
            recyclerAdapter = context?.let { it1 -> RecyclerAdapter(it1, it!!) }!!
            lvContact.adapter = recyclerAdapter
            recyclerAdapter.notifyDataSetChanged()

        }*/
        contactViewModel.getItem(5)


    }
}