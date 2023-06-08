package com.example.recipeapp

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity

import androidx.drawerlayout.widget.DrawerLayout


import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityGoogleProfileBinding
import com.example.recipeapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.File


class GoogleProfile : AppCompatActivity() {
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var binding: ActivityGoogleProfileBinding
    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    private val arraylistRecipeName = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityGoogleProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_google_profile)

        showBottomNavi()

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


        getDocInfo()
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


    private fun showBottomNavi(){

        val bottomNavigation: BottomNavigationView = binding.bottomNavigationView

        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_Profile-> {
                    val intent=Intent(this,GoogleProfile::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_AddRecipe -> {
                    val intent=Intent(this,RecipeAdd::class.java)
                    startActivity(intent)
                    // Handle Search menu item click
                    true
                }
                R.id.navigation_ViewRecipe -> {
                    val intent=Intent(this,viewRecipe::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    private fun getDocInfo() {
        val cache = MyCache()



        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()


        val docRef = mFirebaseDatabaseInstance?.collection("recipe")
        docRef?.get()?.addOnSuccessListener {

            arraylistRecipeName.clear()


//            modalList.clear()
            for (document in it) {
                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                val name = document.get("recipeName").toString()

                arraylistRecipeName.add(name)


            }



            var check=0
            for(i in arraylistRecipeName.indices){

                if(cache.retrieveBitmapFromCache(arraylistRecipeName[i])==null){


                    check++



                }

            }




            if(check>0){


                getImg()


            }










//            getImg()
        }


            ?.addOnFailureListener {
                Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show()
            }


    }
    fun getImg() {
//        arraylistName.ensureCapacity(arraylistData.size)
        Toast.makeText(this,"Loaded Successfully from Cloud Storage",Toast.LENGTH_SHORT).show()
        val arrBitMap = ArrayList<Bitmap>()
//        count=0
//        val docView = findViewById<GridView>(R.id.gridView)
        var detect = 0
        val extractName = ArrayList<String>()
        val cache= MyCache()
//        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        var times=0

//        count = arraylistName.size
//             Toast.makeText(this, "${arraylistName}",Toast.LENGTH_SHORT).show()
        for (i in arraylistRecipeName.indices) {

            val fireb = Firebase.storage.reference.child("RecipeImg/${arraylistRecipeName.get(i)}.jpg")
//            val fireb=FirebaseStorage.getInstance().getReference("/Img")
            val localfile = File.createTempFile("tempImage", "jpg")




            fireb.getFile(localfile).addOnCompleteListener {

                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)





                cache.saveBitmapToCahche(arraylistRecipeName[i],bitmap)
                arrBitMap.add(bitmap)






                times++


            }.addOnFailureListener {



//                Toast.makeText(this, "${arraylistName }failed to retrieve iamge$extractName", Toast.LENGTH_SHORT).show()
            }

//                dt++
        }








    }





}