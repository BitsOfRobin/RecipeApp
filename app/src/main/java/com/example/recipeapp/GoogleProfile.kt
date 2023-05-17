package com.example.recipeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout


import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class GoogleProfile : AppCompatActivity() {
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var toggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_profile)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Profile")
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        val btn=findViewById<Button>(R.id.logoutBtn)
        btn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()

        }

        var user=" "
        val userGoogle = Firebase.auth.currentUser
        userGoogle.let {
            // Name, email address, and profile photo Url
//                    val name = user.displayName
            if (userGoogle != null) {
                user = userGoogle.displayName.toString()

            } else {

                user = " NOne"
            }

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun checkUser() {
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser==null)
        {
            startActivity(Intent(this, LogInAccount::class.java))
            finish()
        }
        else{

            val email=firebaseUser.email
            val data=firebaseUser.displayName
            val image=firebaseUser.photoUrl
            val phone=firebaseUser.phoneNumber

            val etxt=findViewById<TextView>(R.id.emailTv)
            val name=findViewById<TextView>(R.id.DisplayName)
            val userImg=findViewById<ImageView>(R.id.userImg)

            etxt.text="Email:$email"
            name.text="Name:$data\n Phone Number:$phone"
            Picasso.get().load(image).into(userImg);


        }


    }




}