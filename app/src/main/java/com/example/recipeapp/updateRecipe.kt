package com.example.recipeapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.recipeapp.databinding.ActivityUpdateRecipeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class updateRecipe : AppCompatActivity() {
    private lateinit var ImageUri: Uri
    private var mFirebaseDatabaseInstance: FirebaseFirestore?=null
    private lateinit var binding: ActivityUpdateRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)


//            showBottomNavi()
        binding.btnSelect.setOnClickListener {
            selectImage()

        }

        binding.RecipeImage.setOnClickListener {
            binding.RecipeImage.setOnClickListener {
                selectImage()

            }
        }
//        binding.uploadImageBtn.setOnClickListener {
//
//            try{
//                uploadImage()
//            }
//
//
//            catch (e:UninitializedPropertyAccessException){
//                Toast.makeText(this,"You did not attach any photo", Toast.LENGTH_SHORT).show()
//
//            }
//
//
//        }

            displaySpinner()
            getRecipeData()

            showBottomNavi()


    }

        private fun selectImage() {

            val intent= Intent()
            intent.type="image/*"
            intent.action= Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,100)


        }


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            val cache=MyCache()
            val firebaseImg= binding.RecipeImage
            val receivedRecipeName = intent.getStringExtra("recipeName").toString()
            firebaseImg.setImageBitmap(cache.retrieveBitmapFromCache(receivedRecipeName))
            if(requestCode==100&& resultCode== RESULT_OK)
            {
                ImageUri=data?.data!!

                firebaseImg.setImageURI(ImageUri)

            }







        }

        private fun uploadImage() {

            val firebaseImg= binding.RecipeImage

            val userGoogle = Firebase.auth.currentUser
            var userName=""
            userGoogle.let {
                // Name, email address, and profile photo Url
//                    val name = user.displayName
                if (userGoogle != null) {
                    userName = userGoogle.displayName.toString()

                }

            }

            var recipeName=binding.recipeName.text.toString()
//        docName.replace(" ","")

            val letter:Boolean=isLetters(recipeName)

            if(recipeName!=" "&&recipeName!="")
            {
                val cache=MyCache()

                val progressDialog= ProgressDialog(this)
                progressDialog.setMessage("Uploading File....")
                progressDialog.setCancelable(false)
                progressDialog.show()
                if(progressDialog.isShowing)progressDialog.dismiss()
                val storageReference= FirebaseStorage.getInstance().getReference("RecipeImg/$recipeName.jpg")
                storageReference.putFile(ImageUri).addOnCompleteListener {
//                    firebaseImg.setImageURI(null)
                    if(progressDialog.isShowing)progressDialog.dismiss()
                    val imgBitmap= MediaStore.Images.Media.getBitmap(contentResolver, ImageUri)
                    cache.saveBitmapToCahche(recipeName,imgBitmap)
                    Toast.makeText(this,"Uploaded",Toast.LENGTH_SHORT).show()



                }.addOnFailureListener{

                    if(progressDialog.isShowing)progressDialog.dismiss()
                }


            }

            else{

                Toast.makeText(this,"Recipe Name is empty",Toast.LENGTH_SHORT).show()


            }



        }


        private fun getRecipeData() {

            val cache=MyCache()
            val receivedRecipeName = intent.getStringExtra("recipeName").toString()
            val receivedRecipeIngredients = intent.getStringExtra("recipeIngredients").toString()
            val receivedRecipeSteps = intent.getStringExtra("recipeSteps").toString()

//            Toast.makeText(this,receivedRecipeName,Toast.LENGTH_LONG).show()

            binding.recipeName.setText(receivedRecipeName)
            binding.RecipeImage.setImageBitmap(cache.retrieveBitmapFromCache(receivedRecipeName))
            binding.recipeSteps.setText(receivedRecipeSteps)
            binding.recipeIngredients.setText(receivedRecipeIngredients)
            var recipeType=""
            binding.spinnerRecipeTypes.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (parent != null) {
                        recipeType=parent.getItemAtPosition(position).toString()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }


            }

            binding.recipeName.setOnFocusChangeListener { v, hasFocus ->

                if (!hasFocus) {
                    val input = binding.recipeName.text.toString()
                    if (input.isEmpty()) {



                        binding.errRecipeName.text = "recipe name is empty"
                    } else {
                        // set error message if input is invalid
                        binding.errRecipeName.text=" "
                    }
                }
            }


            binding.recipeIngredients.setOnFocusChangeListener { v, hasFocus ->

                if (!hasFocus) {
                    val input = binding.recipeIngredients.text.toString()
                    if (input.isEmpty()) {



                        binding.errRecipeIngredients.text = "recipe ingredients is empty"
                    } else {
                        // set error message if input is invalid
                        binding.errRecipeIngredients.text=" "
                    }
                }
            }

            binding.recipeSteps.setOnFocusChangeListener { v, hasFocus ->

                if (!hasFocus) {
                    val input = binding.recipeSteps.text.toString()
                    if (input.isEmpty()) {



                        binding.errRecipeSteps.text = "recipe steps is empty"
                    } else {
                        // set error message if input is invalid
                        binding.errRecipeSteps.text=" "
                    }
                }
            }
            val btn=binding.updateRecipe
            btn.setOnClickListener {



                val ingredients=binding.recipeIngredients.text.toString()







                mFirebaseDatabaseInstance= FirebaseFirestore.getInstance()
                val recipeName=binding.recipeName.text.toString()
                val letter=isLetters(recipeName)
                val steps=binding.recipeSteps.text.toString()

                if(recipeName.isEmpty()){

                    binding.errRecipeName.text = "recipe name is empty"
                }
                else{
                    binding.errRecipeName.text=" "
                }

                if(steps.isEmpty()){

                    binding.errRecipeSteps.text = "recipe steps is empty"
                }
                else{

                    binding.errRecipeSteps.text=" "
                }
                if(ingredients.isEmpty()){

                    binding.errRecipeIngredients.text = "recipe ingredients is empty"
                }
                else{

                    binding.errRecipeSteps.text=" "
                }


                if(!letter){

                    Toast.makeText(this,"Profession contain non alphabet or empty",Toast.LENGTH_LONG).show()
                    binding.errRecipeName.text = "Profession contain non alphabet or empty"

                }

                else{

                    binding.errRecipeName.text=" "
                }
//                val cache=MyCache()
//                val firebaseImg= binding.RecipeImage
//                val receivedRecipeName = intent.getStringExtra("recipeName").toString()
//                firebaseImg.setImageBitmap(cache.retrieveBitmapFromCache(receivedRecipeName))

                try{
                    if(ingredients!=""&&ingredients!=" "&&steps!=""&&steps!=" "&&recipeName!=""&&recipeName!=" "
                        &&recipeType!=""&&recipeType!=" "){
                        uploadImage()

                    }
                    else{
                        Toast.makeText(this,"Please fill up all data",Toast.LENGTH_LONG).show()
                        val cache=MyCache()
                        val firebaseImg= binding.RecipeImage
                        val receivedRecipeName = intent.getStringExtra("recipeName").toString()
                        firebaseImg.setImageBitmap(cache.retrieveBitmapFromCache(receivedRecipeName))
                    }

                }


                catch (e:UninitializedPropertyAccessException){
                    Toast.makeText(this,"You do not attach any photo", Toast.LENGTH_SHORT).show()

                }

                addRecipe(ingredients,steps,recipeName,recipeType,receivedRecipeName)

            }


        }


        private fun addRecipe(
            ingredients: String,
            steps: String,
            recipeName: String,
            recipeType: String,
            receivedRecipeName: String
        ){
            val letter=isLetters(recipeName)
            if(ingredients!=""&&ingredients!=" "&&steps!=""&&steps!=" "&&recipeName!=""&&recipeName!=" "
                &&recipeType!=""&&recipeType!=" ")
            {
                val recipe= hashMapOf(

                    "recipeName" to recipeName,
                    "ingredients" to ingredients,
                    "steps" to steps,
                    "recipeTypes" to recipeType




                )
//        val  doc =doctor?.uid

//


                mFirebaseDatabaseInstance?.collection("recipe")?.document(receivedRecipeName)?.update(
                    recipe as Map<String, Any>
                )?.addOnSuccessListener {


                    Toast.makeText(this,"Successfully added recipe",Toast.LENGTH_SHORT).show()


                }
                    ?.addOnFailureListener {

                        Toast.makeText(this,"Failed to add recipe", Toast.LENGTH_SHORT).show()
                    }




            }

            else{

                Toast.makeText(this,"Inputs are empty",Toast.LENGTH_SHORT).show()

            }



        }


        private fun isLetters(string: String): Boolean {
            return string.matches("^[a-zA-Z0123456789 ]*$".toRegex())

//        return string.none { it !in 'A'..'Z' && it !in 'a'..'z' }
        }



        private fun displaySpinner(){

            val spinner: Spinner = binding.spinnerRecipeTypes

            // Retrieve the array of values from the XML file
            val values: Array<String> = resources.getStringArray(R.array.recipeTypes_values_array)

            // Create an ArrayAdapter to populate the Spinner with the values
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, values)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Set the adapter to the Spinner
            spinner.adapter = adapter



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
                        val intent= Intent(this,viewRecipe::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

        }


    }









