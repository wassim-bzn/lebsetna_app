package com.example.pfe.adapter

import android.R.id.message
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.pfe.R
import com.example.pfe.model.Favourits
import com.example.pfe.model.Habit
import com.example.pfe.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class PostsAdapter(var mCtx:Context, var resource:Int, var items: MutableList<Habit>,var ConnecteduserPhoneNum:String,var action:String):ArrayAdapter<Habit>(mCtx,resource,items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var TAG="Firebase"
        var database = FirebaseDatabase.getInstance()
        val layoutInflater:LayoutInflater= LayoutInflater.from(mCtx)
        val view:View=layoutInflater.inflate(resource,null)
        val imageView:ImageView=view.findViewById(R.id.post_img)
        val titreTextView: TextView =view.findViewById(R.id.post_title)
        val adresseTextView:TextView=view.findViewById(R.id.post_address)
        val nomTextView:TextView=view.findViewById(R.id.post_nom)
        val prenomTextView:TextView=view.findViewById(R.id.post_prenom)
        val imageViewtele:ImageButton=view.findViewById(R.id.call_ic)
        val imageViewEmail:ImageButton=view.findViewById(R.id.email_ic)
        val imageViewCategory:ImageButton=view.findViewById(R.id.favouritebtn)
        val imageViewfavouritebtn_checked:ImageButton=view.findViewById(R.id.favouritebtn_checked)
        val imageButtotrash:ImageButton=view.findViewById(R.id.deleteItembtn)
        var list_myPosts_favourits = mutableListOf<Favourits>()
        var userPhoneNum=ConnecteduserPhoneNum
        var favoutits = Favourits( "", "")
        var UserData= UserModel("","","","","","","")
        var habit:Habit =items[position]
        val myRefUser = database.getReference("Users")
        if (action=="viewMyPosts"){
            imageViewfavouritebtn_checked.visibility = View.INVISIBLE;
            imageViewCategory.visibility = View.INVISIBLE;
            imageButtotrash.visibility=View.VISIBLE
        }
        myRefUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val UserDatasnap = dataSnapshot.child(habit.userPhone.toString())
                UserData.id = UserDatasnap.child("id").getValue().toString()
                UserData.email = UserDatasnap.child("email").getValue().toString()
                UserData.firstName = UserDatasnap.child("firstName").getValue().toString()
                UserData.lastName = UserDatasnap.child("lastName").getValue().toString()
                UserData.phone = UserDatasnap.child("phone").getValue().toString()
                UserData.ville = UserDatasnap.child("ville").getValue().toString()
                UserData.role = UserDatasnap.child("role").getValue().toString()

                //imageView.setImageDrawable(mCtx.resources.getDrawable(habits.photo))
                var storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                var imageref = storageRef.child(habit.ImageUrl.toString())
                imageref.downloadUrl.addOnSuccessListener {Uri->

                    val imageURL = Uri.toString()
                    Glide.with(view)
                        .load(imageURL)
                        .into(imageView)

                }
                titreTextView.text=habit.description
                adresseTextView.text=UserData.ville
                nomTextView.text=UserData.firstName
                prenomTextView.text=UserData.lastName
                imageViewtele.contentDescription=UserData.phone
                imageViewEmail.contentDescription=UserData.email


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read User Data.", error.toException())
            }
        })
        val myRefFavorits = database.getReference("Favourits")

        myRefFavorits.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list_myPosts_favourits = mutableListOf<Favourits>()
                val UserDatasnap = dataSnapshot.getValue()
                //UserData=(UserDatasnap["id"],UserDatasnap["email"],UserDatasnap["firstName"],UserDatasnap["lastName"],UserDatasnap["phone"],UserDatasnap["ville"],UserDatasnap["role"])
                for (snapshot in dataSnapshot.children) {
                  var habitIdfav = snapshot.child("habitId").getValue().toString()
                  var userPhonefav = snapshot.child("userPhone").getValue().toString()
                  if (habitIdfav==habit.userId && userPhoneNum==userPhonefav){
                      if (action=="viewMyPosts"){
                          imageViewfavouritebtn_checked.visibility = View.INVISIBLE;
                          imageViewCategory.visibility = View.INVISIBLE;
                          imageButtotrash.visibility=View.VISIBLE
                      }else{
                      imageViewfavouritebtn_checked.visibility = View.VISIBLE;
                      imageViewCategory.visibility = View.INVISIBLE;
                      imageButtotrash.visibility=View.INVISIBLE
                      }
                  }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read User Data.", error.toException())
            }
        })

        imageViewEmail.setOnClickListener(View.OnClickListener {
            Toast.makeText(context,"envoie d'un imageViewEmail!",Toast.LENGTH_LONG).show()
            var email_description=imageViewEmail.contentDescription
            var subject="Selling product"
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", email_description.toString(), null
                )
            )
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, message)
            context.startActivity(Intent.createChooser(intent, "Choose an Email client :"))

        })
        imageViewtele.setOnClickListener(View.OnClickListener {
            Toast.makeText(context,"envoie d'un imageViewtele!",Toast.LENGTH_LONG).show()
            var phone_description=imageViewtele.contentDescription
            val intent = Intent(Intent.ACTION_DIAL)
            val temp = "tel:"+phone_description.toString()
            intent.data = Uri.parse(temp)
            context.startActivity(intent)
        })
        imageViewCategory.setOnClickListener(View.OnClickListener {
            val myRef = database.getReference("Favourits")
            var favourits= Favourits(userPhoneNum,habit.userId)
            myRef.child(habit.userId.toString()+userPhoneNum).setValue(favourits).addOnSuccessListener {
                Toast.makeText(context,"Successfully Saved favoutits ",Toast.LENGTH_SHORT).show()
                if (action=="viewMyPosts"){
                    imageViewfavouritebtn_checked.visibility = View.INVISIBLE;
                    imageViewCategory.visibility = View.INVISIBLE;
                    imageButtotrash.visibility=View.VISIBLE
                }else {
                    imageViewfavouritebtn_checked.visibility = View.VISIBLE;
                    imageViewCategory.visibility = View.INVISIBLE;
                    imageButtotrash.visibility = View.INVISIBLE
                }
            }.addOnFailureListener{

                Toast.makeText(context,"Failed To save favoutits! ",Toast.LENGTH_SHORT).show()
            }
        })
        imageViewfavouritebtn_checked.setOnClickListener(View.OnClickListener {
            val myRef = database.getReference("Favourits")
            myRef.child(habit.userId.toString()+userPhoneNum).removeValue().addOnSuccessListener {
                Toast.makeText(context,"Successfully removed favorites ",Toast.LENGTH_SHORT).show()
                if (action=="viewMyPosts"){
                    imageViewfavouritebtn_checked.visibility = View.INVISIBLE;
                    imageViewCategory.visibility = View.INVISIBLE;
                    imageButtotrash.visibility=View.VISIBLE
                }else {
                    imageViewfavouritebtn_checked.visibility = View.INVISIBLE;
                    imageViewCategory.visibility = View.VISIBLE;
                    imageButtotrash.visibility = View.INVISIBLE
                }
            }.addOnFailureListener{

                Toast.makeText(context,"Failed To remove favoutits! ",Toast.LENGTH_SHORT).show()
            }
        })
        imageButtotrash.setOnClickListener(View.OnClickListener {
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            val myRefhabit = database.getReference("habits")
                            myRefhabit.child(habit.userId.toString()).removeValue().addOnSuccessListener {
                                Toast.makeText(context,"Successfully removed habit ",Toast.LENGTH_SHORT).show()
                                imageViewfavouritebtn_checked.visibility = View.INVISIBLE;
                                imageViewCategory.visibility = View.INVISIBLE;
                                imageButtotrash.visibility=View.INVISIBLE
                            }.addOnFailureListener{

                                Toast.makeText(context,"Failed To remove favoutits! ",Toast.LENGTH_SHORT).show()
                            }
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                        }
                    }
                }

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to delete this item?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()

        })

        return view
    }

}




