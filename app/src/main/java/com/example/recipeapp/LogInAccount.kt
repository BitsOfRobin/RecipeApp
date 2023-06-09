package com.example.recipeapp



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class LogInAccount: AppCompatActivity() {
    private var mFirebaseDatabaseInstance: FirebaseFirestore?=null
    private lateinit var googleSignInClient:GoogleSignInClient
    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var firebaseAuth:FirebaseAuth
//    private lateinit var binding: com.example.hospitalbooking.databinding.ActivityUserLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_account)
//        checkUser()

        googleLog()
    }
    private companion object{

        private const val  RC_SIGN_IN=100
        private const val TAG="GOOGLE_SING_IN_TAG"
    }



    private fun googleLog(){

        val googleSignInoptions=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("385146186710-cvq262o56jch8hh3d51u6skh5f4qehqt.apps.googleusercontent.com")
            .requestEmail().build()

        googleSignInClient=GoogleSignIn.getClient(this,googleSignInoptions)

        firebaseAuth= FirebaseAuth.getInstance()



        val btn=findViewById<SignInButton>(R.id.googleSignInBtn)
        btn.setOnClickListener {
            val intent=googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)

        }

        checkUser()

    }


    private fun checkUser()
    {
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null)
        {
//            startActivity(Intent(this,Profile::class.java))

            val intent = Intent(this, ContactsContract.Profile::class.java)
//            intent.putExtra("DoctorName", tempListViewClickedValue)
            startActivity(intent)
            finish()
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(this, "Activity Result II ", Toast.LENGTH_SHORT).show()
        if(requestCode== RC_SIGN_IN)
        {

            Toast.makeText(this, "Activity Result ", Toast.LENGTH_SHORT).show()
            val accountTask=GoogleSignIn.getSignedInAccountFromIntent(data)

//            try{


            val account=accountTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogleAccount(account)
//            }

//            catch(e:Exception)
//            {
//               sage}") Log.d(TAG,"ONActivityResult:${e.mes
//                Toast.makeText(this, "Activity Result ", Toast.LENGTH_SHORT).show()
//            }


        }



    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG,"BEGIN firebase with google account")
        val credential=GoogleAuthProvider.getCredential(account!!.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener {
            Toast.makeText(this, "LoggedIn ", Toast.LENGTH_SHORT).show()
            val firebaseUser=firebaseAuth.currentUser
            val uid=firebaseUser!!.uid
            val email= firebaseUser!!.email
            Toast.makeText(this, "id;$uid ", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "email:$email ", Toast.LENGTH_SHORT).show()

            if(it.additionalUserInfo!!.isNewUser)
            {

                Toast.makeText(this, "new acc is created$email ", Toast.LENGTH_SHORT).show()
            }
            else{

                Toast.makeText(this, "existing user$email ", Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this, GoogleProfile::class.java)
//            intent.putExtra("DoctorName", tempListViewClickedValue)
            startActivity(intent)

        }


            .addOnFailureListener {
                Toast.makeText(this, "LoggedIn Failed${it.message} ", Toast.LENGTH_SHORT).show()
            }



    }


}