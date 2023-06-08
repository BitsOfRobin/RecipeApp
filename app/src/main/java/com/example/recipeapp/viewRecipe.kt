package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recipeapp.databinding.ActivityGoogleProfileBinding
import com.example.recipeapp.databinding.ActivityViewRecipeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class viewRecipe : AppCompatActivity() {
    private lateinit var binding: ActivityViewRecipeBinding
    private lateinit var viewRecipeViewModel: ViewRecipeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityViewRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showBottomNavi()


//        setContentView(R.layout.activity_view_recipe)


        viewRecipeViewModel= ViewModelProvider(this).get(ViewRecipeViewModel::class.java)

        viewRecipeViewModel.getDataDoc()
//        var getCache=mainPageViewModel.retrieveCache()
//        displayAdapter()
        viewRecipeViewModel.retrieveCache()
        displayAdapter()



        doctorSearching()



        refresh()




    }

        private fun displayAdapter(){
            val recipeView=binding.recipeRecycleView
            viewRecipeViewModel= ViewModelProvider(this).get(ViewRecipeViewModel::class.java)

           recipeView.layoutManager = LinearLayoutManager(this)


    //        var customAdapter:CustomAdapterRecycleView
            viewRecipeViewModel.recipeListLive.observe(this, androidx.lifecycle.Observer {





                val customAdapter=CustomAdapterRecycleView(

                    it as ArrayList<recipe>,this,object :CustomAdapterRecycleView.OnItemClickListener{
                        override fun onUpdateClick(position: Int) {
                            toUpdateRecipe(position,it as ArrayList<recipe>)
                        }

                        override fun onDeleteClick(position: Int) {
                            longClickForDocDel(position)
                        }


                    }
                )


                recipeView.adapter=customAdapter
            })

        }


    private fun longClickForDocDel(i:Int){


        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Doctor Alert")
        builder.setMessage("Are you sure to delete Doctor?")


        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(
                applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT
            ).show()


            viewRecipeViewModel=ViewModelProvider(this).get(ViewRecipeViewModel::class.java)

            viewRecipeViewModel.deletionDoctor(i)
//            Toast.makeText(this, "Dr $dtname ,$i", Toast.LENGTH_LONG).show()


        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }




        builder.show()

    }


    private fun doctorSearching(){



        val docView=binding.recipeRecycleView


        val temp = ArrayList<String>()

        val searchView =binding.searchRecipe
        searchView.queryHint = " recipe type"

        viewRecipeViewModel=ViewModelProvider(this).get( ViewRecipeViewModel::class.java)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                temp.clear()

                var searchQuery=""

//                val first= p0?.let { p0.toString().indexOf(it[0]) }
                if (p0 != null) {


                    searchQuery.replace("",p0,true)
                    viewRecipeViewModel.getSearchQuery(p0)
//                        showMsg(p0)

                    viewRecipeViewModel.searchDoctor(p0)
                    paramForSearching(p0)
//                        for (i in arraylistPro.indices) {
//                            if (arraylistPro[i].contains(p0, true)) {




                } else {



//                        mainPageViewModel.retrieveCache()

//                        getAdapter()
//                        paramForSearching()

                    displayAdapter()
//                        paramForSearching()

                }




                return false

            }

            override fun onQueryTextChange(p0: String?): Boolean {

                temp.clear()

                var searchQuery=""
                if (p0 != null) {
                    searchQuery.replace("",p0,true)
                    viewRecipeViewModel.getSearchQuery(p0)
//                        showMsg(p0)

                    viewRecipeViewModel.searchDoctor(p0)
                    paramForSearching(p0)
                } else {
//                        getAdapter()
//                        paramForSearching()
//                        paramForSearching()

                    viewRecipeViewModel.getDataDoc()

                    displayAdapter()
                }
//                if (p0 != null) {
//                    showSuggestion(p0,adapter)
//                }






                return true
            }


        })


    }


    private fun refresh() {
        val swipe = binding.swipeRefresh

        viewRecipeViewModel=ViewModelProvider(this).
        get(ViewRecipeViewModel::class.java)
        swipe.setOnRefreshListener {

//            getDataDoc()
           viewRecipeViewModel.getDataDoc()

            displayAdapter()

            swipe.isRefreshing = false

        }



    }
    private fun paramForSearching(searchResult:String){
        val recipeView=binding.recipeRecycleView

       viewRecipeViewModel.recipeListLiveSearch.observe(this, androidx.lifecycle.Observer {

            if(it.isNotEmpty()) {
                val adapter =
                    CustomAdapterRecycleView(it as ArrayList<recipe>, this, object :
                        CustomAdapterRecycleView.OnItemClickListener {
                        override fun onUpdateClick(position: Int) {

                            toUpdateRecipe(position,it as ArrayList<recipe>)
                        }

                        override fun onDeleteClick(position: Int) {
                          longClickForDocDel(position)
                        }


                    })




                adapter.notifyDataSetChanged()
                recipeView.adapter = adapter
            }
        })

    }


    private fun toUpdateRecipe(position:Int,arrRecipe: ArrayList<recipe>){

        val intent = Intent(this, updateRecipe::class.java)
        intent.putExtra("recipeName", arrRecipe[position].recipeName.toString())
//        intent.putExtra("position", position)
        startActivity(intent)
//        Toast.makeText(this,arrRecipe[position].recipeName.toString(),Toast.LENGTH_LONG).show()

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