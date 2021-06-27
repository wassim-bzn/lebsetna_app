package com.example.pfe.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pfe.Ecran_d_accueil
import com.example.pfe.R
import com.example.pfe.model.Habit
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_adding_post.*
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/* c'est une classe pour ajouter un article (habit)*/
class adding_post : AppCompatActivity() {
    lateinit var imageView: ImageView
    private val pickImage = 100
    var imageUri: Uri? = null
    var storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    var userPhone:String?=""
    var userEmail:String?=""
    var userVille:String?=""
    var USER_ROLE:String?=""

    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_post)
        setSupportActionBar(toolbar)
        imageView = findViewById(R.id.imported_image)
        var bundle :Bundle ?=intent.extras
        userPhone= bundle!!.getString("userPhone")
        userEmail= bundle!!.getString("Useremail")
        userVille= bundle!!.getString("userVille")
        USER_ROLE= bundle!!.getString("userRole")

    }
    /*fonction pour importer l'image de l'habit de user gallery =*/
    fun import_image(view: View?) {

        val myToast = Toast.makeText(applicationContext,"import_image clicked", Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.LEFT,200,200)
        myToast.show()
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)


    }
    /*fonction listener pour metter l'image importé dans l'image view =*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    /*fonction pour revenir à la liste d'accueil =*/
    fun back_toMenu(view: View?) {

        val intent = Intent(this, Ecran_d_accueil::class.java)
        intent.putExtra("Useremail", userEmail)
        intent.putExtra("userPhone", userPhone)
        intent.putExtra("userVille", userVille)
        intent.putExtra("userRole", USER_ROLE)
        startActivity(intent)
    }
    /**
     * fonction pour ajouter un habit à la base de donnée*/
    fun add_habit(view: View?) {
        val myToast = Toast.makeText(applicationContext, "add_habit clicked", Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.LEFT, 200, 200)
        myToast.show()
        var uuid:String=UUID.randomUUID().toString()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("habits")
        var editText_Nom = findViewById(R.id.editText_Nom) as EditText
        var editText_description = findViewById(R.id.editText_description) as EditText
        var name = editText_Nom.text.toString()
        var description = editText_description.text.toString()
        /* prendre de date et temps exact avec les seconde */
        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        /* convertir le date pour une chaine qui fait une concatination pour les chiffres de date
        * pour justement l'utiliser en tant que iD pour un post (habit)
        */
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
        val formatted = current.format(formatter)
        /** le date year month day hour minut second c'est un id incrimenter par défaut  */
        var userId=formatted
        /*créer une nouvelle iunstence d'un habit avec les donner de user qui a importé (érit)*/
        val Habit = Habit(name,description,userId,"images/" + userId,userPhone)
        /**
         * myRef.child(userId).setValue(Habit).addOnSuccessListene
         * c'est la fonction listener qui ajoute les données dans la base de données real time*/
        myRef.child(userId).setValue(Habit).addOnSuccessListener {
            editText_Nom.text.clear()
            editText_description.text.clear()

            Toast.makeText(this,"Successfully Saved habit ",Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{

            Toast.makeText(this,"Failed To save Habit! ",Toast.LENGTH_SHORT).show()
        }
        /* c'est la fonction  qui ajoute l'Image dans la base de données des images de firebase Cloud storqge */
        upload_image(userId)

    }
    /* c'est la fonction  qui ajoute l'Image dans la base de données des images de firebase Cloud storqge */
    fun upload_image(uuid:String){
        /*progress dialog pour afficher le pourcentage de l'upload de l'image*/
        val pd = ProgressDialog(this)
        pd.setTitle("Uploading Image..")
        pd.show()
        /*
        *Instentation de la class StorageReference de Firebase pour les appels du service Cloud Storage de Firebase
        * le uuid de l'image il va etre le meme que le l'ID d'un habit pour justement faire le relation entre eu*/
        val habitImagesRef: StorageReference = storageRef.child("images/" + uuid)
        habitImagesRef.putFile(imageUri!!)
            /* c'est la fonction listener qui ajoute l'Image dans la base de données des images de firebase Cloud storqge */
            .addOnSuccessListener {
                /* s'il n'y a pas un problème lors de l'upload */

                taskSnapshot: UploadTask.TaskSnapshot? -> var url = taskSnapshot!!.uploadSessionUri
                /*pd.dismiss() : stop le ProgressDialog lorsque l'upload est fini*/
            pd.dismiss()
                /*affichage d'un  Tost et un snackbar pour informer que l'upload à été fait correctement */

                Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()
            Snackbar.make(findViewById(R.id.result_upload),"Image Uploaded",Snackbar.LENGTH_SHORT).show()
        }
            .addOnFailureListener{
                /* s'il y a un problème lors de l'upload*/
                val myToast = Toast.makeText(applicationContext, "Fail to Upload! ", Toast.LENGTH_SHORT)
                myToast.show()
                pd.dismiss()
            }
            .addOnProgressListener {
                /*calcul de pourcentage des bits de l'image uploadé pour bien affiché la résultat correct dans le progress bar */
                taskSnapshot: UploadTask.TaskSnapshot?-> var snap = taskSnapshot!!
                val progressPercent: Double = 100.0 * snap.getBytesTransferred() / snap.getTotalByteCount()
                pd.setMessage("progress : "+ progressPercent.toInt() + "%")
            }


    }
}
