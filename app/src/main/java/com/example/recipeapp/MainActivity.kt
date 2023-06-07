package com.example.recipeapp


import android.provider.ContactsContract


import android.content.ContentValues
import android.content.Context
import android.content.Intent

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity


import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.File

import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

//    private var modalList = ArrayList<Modal>()
    private lateinit var toggle: ActionBarDrawerToggle
    private val arraylistName = ArrayList<String>()
    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
//    var images = intArrayOf(
//        R.drawable.user_profile,
//        R.drawable.settime,
//        R.drawable.upload_image,
//        R.drawable.doctor_profile,
//        R.drawable.booking,
//        R.drawable.summary_report,
//        R.drawable.recognize,
//        R.drawable.record,
//    )
//
//    var userImages = intArrayOf(
//        R.drawable.user_profile,
//        R.drawable.settime,
//        R.drawable.booking,
//        R.drawable.recognize,
//        R.drawable.record,
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent=Intent(this,LogInAccount::class.java)
        startActivity(intent)
//
//        userOrAdmin()
//
//        getDocInfo()

//        getImg()
    }
//
//    private fun naviImg(photoUrl: Uri?, loginUser: String) {
//
//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//        val headerView = navigationView.getHeaderView(0)
//        val headerImage = headerView.findViewById<ImageView>(R.id.nav_header_image)
//        val headerTxtView = headerView.findViewById<TextView>(R.id.nav_header_textView)
//        Picasso.get().load(photoUrl).into(headerImage);
//        headerTxtView.text=loginUser
//
//
//
//
//    }
//
//
//    private fun userOrAdmin() {
//        var names: Array<String>
//        modalList.clear()
//        names = arrayOf(" ")
//        var loginUser = " "
//        val userGoogle = Firebase.auth.currentUser
//        userGoogle.let {
//            // Name, email address, and profile photo Url
////                    val name = user.displayName
//            if (userGoogle != null) {
//                loginUser = userGoogle.email.toString()
//                naviImg(userGoogle.photoUrl,loginUser)
//            } else {
//
//                loginUser = " NOne"
//            }
////
//        }
//
//
//
//        if (loginUser.contains("@student.tarc",true)) {
//            names = arrayOf(
//
//                "Account Detail",
//                "View Doctors",
//                "Upload Image for Doctor",
//                "Doctor Profile",
//                "Doctor View Appointment",
//                "Doctor Summarize Report",
//                "Medicine Recognition",
//                "Medicine Record"
//            )
//            for (i in names.indices) {
//
//                modalList.add(Modal(names[i], images[i]))
//            }
//            var customAdapter = HomeCustomAdapter(modalList, this)
//            val grid = findViewById<GridView>(R.id.gridView)
//
////        val arraylist = arrayOf("Book Appointment", "Medicine Record", "Enter Medicine","View Doctor Appointment","User Registration","Login","Set time for Doctors","Upload Images for doctor")
//
////        id.adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, arraylist)
//            grid.adapter = customAdapter
//            grid.setOnItemClickListener { adapterView, view, i, l ->
//
////            if (i == 0) {
////                val intent = Intent(this, DoctorFilterMedicine::class.java)
//////            intent.putExtra("DoctorName", tempListViewClickedValue)
////                startActivity(intent)
////            }
//                when (i) {
//                    0 -> {
//                        val intent = Intent(this, UserLogin::class.java)
//                        //            intent.putExtra("DoctorName", tempListViewClickedValue)
//                        startActivity(intent)
//
//
//                    }
//                    1 -> {
//                        val intent = Intent(this, MainPage::class.java)
//                        //            intent.putExtra("DoctorName", tempListViewClickedValue)
//                        startActivity(intent)
//
//
//                    }
//                    2 -> {
//                        val intent = Intent(this, UploadImg::class.java)
//                        //            intent.putExtra("DoctorName", tempListViewClickedValue)
//                        startActivity(intent)
//
//
//                    }
//
//                    //            else if (i == 4) {
//                    //                val intent = Intent(this, UserRegister::class.java)
//                    ////            intent.putExtra("DoctorName", tempListViewClickedValue)
//                    //                startActivity(intent)
//                    //
//                    //
//                    //            }
//                    3 -> {
//                        val intent = Intent(this, EditDoctorProfile::class.java)
//                        startActivity(intent)
//
//
//                    }
//
//                    //            else if (i == 5) {
//                    //                val intent = Intent(this, DoctorViewAppointment::class.java)
//                    ////            intent.putExtra("DoctorName", tempListViewClickedValue)
//                    //                startActivity(intent)
//                    //
//                    //            }
//
//                    //            else if (i == 6) {
//                    //                val intent = Intent(this, PrescriptionDisplay::class.java)
//                    ////            intent.putExtra("DoctorName", tempListViewClickedValue)
//                    //                startActivity(intent)
//                    //
//                    //            }
//                    4 -> {
//                        val intent = Intent(this, DoctorViewAppointment::class.java)
//                        //            intent.putExtra("DoctorName", tempListViewClickedValue)
//                        startActivity(intent)
//
//
//                    }
////                    5 -> {
////                        val intent = Intent(this,MainPage::class.java )
////                        //            intent.putExtra("DoctorName", tempListViewClickedValue)
////                        startActivity(intent)
////
////                    }
////                    6 -> {
////                        val intent = Intent(this, DoctorAppointment::class.java)
////                        //            intent.putExtra("DoctorName", tempListViewClickedValue)
////                        startActivity(intent)
////
////                    }
//
//                    5-> {
//                        val intent = Intent(this, DoctorSummarizeReport::class.java)
//                        startActivity(intent)
//
//                    }
//                    6 -> {
//
//                        val intent = Intent(this, UserMedicine::class.java)
//                        //            intent.putExtra("DoctorName", tempListViewClickedValue)
//                        startActivity(intent)
//                    }
//                    7 -> {
//
//                        val intent = Intent(this, MedicineRecord::class.java)
//                        //            intent.putExtra("DoctorName", tempListViewClickedValue)
//                        startActivity(intent)
//                    }
//
//                }
//
//
//            }
//
//        }
//
//
//        else{
//
//
//            val intent=Intent(this,UserLogin::class.java)
//            startActivity(intent)
//        }
//
//
//
//
//    }
//
//
//    class HomeCustomAdapter(var itemModel: ArrayList<Modal>, var context: Context) : BaseAdapter() {
//        override fun getCount(): Int {
//
//            return itemModel.size
//        }
//
//        override fun getItem(p0: Int): Any {
//            return itemModel[p0]
//        }
//
//        override fun getItemId(p0: Int): Long {
//
//            return p0.toLong()
//        }
//
//        private var layoutInflater =
//            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
//
//            var view = view
//            if (view == null) {
//                view = layoutInflater.inflate(R.layout.welcomepage, viewGroup, false)
//            }
//            var tvImageName = view?.findViewById<TextView>(R.id.imageName)
//            var imageView = view?.findViewById<ImageView>(R.id.imageView)
//
//            tvImageName?.text = itemModel[position].name
//            imageView?.setImageResource(itemModel[position].image!!)
//
//            return view!!
//        }
//
//
//    }
//
//
//
//
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (toggle.onOptionsItemSelected(item)) {
//            return true
//
//
//        }
//
//
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun getDocInfo() {
//        val cache = MyCache()
//
//
//
//        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()
//
//
//        val docRef = mFirebaseDatabaseInstance?.collection("doctor")
//        docRef?.get()?.addOnSuccessListener {
//
//            arraylistName.clear()
//
//
////            modalList.clear()
//            for (document in it) {
//                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
//
//                val name = document.get("name").toString()
//                if (name.contains("Dr")) {
//                    arraylistName.add(name)
//                }
//
//            }
//
//
//
//            var check=0
//            for(i in arraylistName.indices){
//
//                if(cache.retrieveBitmapFromCache(arraylistName[i])==null){
//
//
//                    check++
//
//
//
//                }
//
//            }
//
//
//
//
//            if(check>0){
//
//
//                getImg()
//
//
//            }
//
//
//
//
//
//
//
//
//
//
////            getImg()
//        }
//
//
//            ?.addOnFailureListener {
//                Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show()
//            }
//
//
//    }
//    private fun getImg() {
//
//        val arrBitMap = ArrayList<Bitmap>()
//        Toast.makeText(this,"Loaded Successfully from Cloud Storage",Toast.LENGTH_SHORT).show()
//
//        val extractName = ArrayList<String>()
//        val cache = MyCache()
//
//        var times = 0
//
//
//        for (i in arraylistName.indices) {
//
//            val fireb = Firebase.storage.reference.child("Img/${arraylistName.get(i)}.jpg")
//
//            val localfile = File.createTempFile("tempImage", "jpg")
//
//
//
//
//
//            fireb.getFile(localfile).addOnCompleteListener {
//
//                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
//
//
//
//
//
//
//                cache.saveBitmapToCahche(arraylistName[i], bitmap)
////                arrBitMap.add(bitmap)
////
////
////
////
////                times++
//
//
//            }.addOnFailureListener {
//
//
//                Toast.makeText(
//                    this,
//                    "${arraylistName}failed to retrieve iamge$extractName",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//
//        }
//
//
//
//
//
//    }



}