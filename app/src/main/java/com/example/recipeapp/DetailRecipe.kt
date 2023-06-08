package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.recipeapp.databinding.ActivityDetailRecipeBinding
import com.example.recipeapp.databinding.ActivityRecipeAddBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailRecipe : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        receiveRecipe()
        showBottomNavi()
    }

    private fun receiveRecipe(){

        val cache=MyCache()
        val receivedRecipeName = intent.getStringExtra("recipeName").toString()
        val receivedRecipeIngredients = intent.getStringExtra("recipeIngredients").toString()
        val receivedRecipeSteps = intent.getStringExtra("recipeSteps").toString()
        val receivedRecipeType = intent.getStringExtra("recipeTypes").toString()

//            Toast.makeText(this,receivedRecipeName,Toast.LENGTH_LONG).show()

        binding.imageName.text= receivedRecipeName
        binding.imageView.setImageBitmap(cache.retrieveBitmapFromCache(receivedRecipeName))
        binding.stepsItems.text=receivedRecipeSteps
        binding.ingredientsItems.text=receivedRecipeIngredients
        binding.recipeTypesitems.text=receivedRecipeType

    }


    private fun showBottomNavi(){

        val bottomNavigation: BottomNavigationView = binding.bottomNavigationView

        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_Profile-> {
                    val intent= Intent(this,GoogleProfile::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_AddRecipe -> {
                    val intent= Intent(this,RecipeAdd::class.java)
                    startActivity(intent)
                    // Handle Search menu item click
                    true
                }
                R.id.navigation_ViewRecipe -> {
                    // Handle Profile menu item click
                    true
                }
                else -> false
            }
        }

    }


}