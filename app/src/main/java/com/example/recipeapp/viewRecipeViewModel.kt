package com.example.recipeapp

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class ViewRecipeViewModel:ViewModel() {

    val _recipeList= MutableLiveData<List<recipe>>()
    val recipeListLive: LiveData<List<recipe>>
        get() = _recipeList
    val _recipeListSearch= MutableLiveData<List<recipe>>()
    val recipeListLiveSearch: LiveData<List<recipe>>
        get() = _recipeListSearch
    val _arrRecipeName= MutableLiveData<List<String>>()
    val arrRecipeName: LiveData<List<String>>
        get() = _arrRecipeName
    val _searchQueryLiveData = MutableLiveData<String>()
    val searchQueryLiveData : LiveData<String>
        get()=_searchQueryLiveData

    var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    var docDetail: String? = null
    val recipeListSearch = ArrayList<recipe>()

    //    private lateinit var binding: ActivityMainBinding
    var recipeList = ArrayList<recipe>()
    var arraylistRecipeName = ArrayList<String>()
    val arraylistData = ArrayList<String>()
    val arraylistIngredients = ArrayList<String>()

    val arraylistRecipeType = ArrayList<String>()
    val arraylistSteps = ArrayList<String>()
    val arraylistEmpty = ArrayList<recipe>()
    init {

        getDataDoc()
//        getImg()
//        retrieveCache()


//        if(p0.isNotEmpty()){
//            dataChanged(p0,"")
//
//        }







        Log.i("ScoreViewModel", "Final score is $recipeList")

    }

    fun getSearchQuery(str:String){

        _searchQueryLiveData.value=str

    }


    fun searchDoctor(p0:String){

        val temp = ArrayList<String>()

        temp.clear()

        _recipeListSearch.value= listOf()
        recipeListSearch.clear()
        for (i in arraylistRecipeType.indices) {
            if (arraylistRecipeType[i].contains(p0, true)) {
                temp.add(arraylistRecipeName[i])

            }



        }


//        for (k in arraylistHospital.indices) {
//            if (arraylistHospital[k].contains(p0, true)) {
//
//                tempHos.add(arraylistName[k])
//
//            }
//        }
//        for (l in arraylistName.indices) {
//            if (arraylistName[l].contains(p0, true)) {
//
//                tempName.add(arraylistName[l])
//
//            }
//        }

        if (temp.isNotEmpty()) {

            dataChanged(temp,"")
//                            paramForSearching()
        }

    }



    fun getDataDoc() {


        val doctor = FirebaseAuth.getInstance().currentUser

        docDetail = doctor?.uid
        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()


        val docRef = mFirebaseDatabaseInstance?.collection("recipe")
        docRef?.get()?.addOnSuccessListener {


//                }
            arraylistData.clear()
            arraylistRecipeName.clear()
            arraylistRecipeType.clear()
            arraylistIngredients.clear()
            arraylistSteps.clear()
            recipeList.clear()
            for (document in it) {
                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                val steps = document.get("steps").toString()

                val recipename = document.get("recipeName").toString()
                val recipeType = document.get("recipeTypes").toString()

                val ingredients = document.get("ingredients").toString()


                arraylistRecipeType.add(recipeType)
//                Toast.makeText(this,"hos$arraylistHospital",Toast.LENGTH_LONG).show()
//                Toast.makeText(this,"hosp$hospital",Toast.LENGTH_LONG).show()

                arraylistIngredients.add(ingredients)
                arraylistRecipeName.add(recipename)


                arraylistSteps.add(steps)


//                arraylistData.add("Name: $name \nProfessional:\n $pro \nAvailable Date:\n$date \n")
            }


//                Toast.makeText(this, "Enter the first read ${arraylistTime.toString()} ", Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "Enter the firebase id ${document.id.toString()} ", Toast.LENGTH_SHORT).show()


        _arrRecipeName.value = arraylistRecipeName
//                val doc= listOf(arraylist)
        var imageArr = ArrayList<Bitmap>()
//            var images= intArrayOf()
//            var dt=1
        var arrBitMap = ArrayList<Bitmap>()
        val file = File.createTempFile("img", "jpg")


//
        val cache = MyCache()
        var check = 0

        for (i in arraylistRecipeName.indices) {

            if (cache.retrieveBitmapFromCache(arraylistRecipeName[i]) == null) {


                check++


            }

        }




        if (check > 0) {


            getImg()
            retrieveCache()
//                    refreshMain()
//                    retrieveCache()


        } else {

            retrieveCache()
        }

        if (arraylistRecipeName.size > recipeList.size) {


            retrieveCache()

        }
    }

            ?.addOnFailureListener {
//                Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show()
            }


    }






    fun getImg() {
//        arraylistName.ensureCapacity(arraylistData.size)
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







//        }

//        val customAdapter = CustomAdapter(modalList, this)
//
//
//
////        docView.adapter = customAdapter
//
//        searchDoc(customAdapter)
//
//        swipe.isRefreshing=false
//        if(call!=0){

    }

    fun retrieveCache(){


        val cache= MyCache()
        for (i in arraylistRecipeName.indices) {
            val bitmap: Bitmap? =cache.retrieveBitmapFromCache(arraylistRecipeName[i])


            bitmap?.let {
                recipe(
                    arraylistIngredients[i],
                    it,
                    arraylistRecipeName[i],
                    arraylistSteps[i],
                    arraylistRecipeType[i]

                )
            }?.let {
                recipeList.add(
                    it
                )
            }

//            Toast.makeText(this,"b$bitmap", Toast.LENGTH_SHORT ).show()

        }

        _recipeList.value=recipeList

        _recipeListSearch.value=recipeList







    }









    fun deletionDoctor(i:Int) {

        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()








        if(i>-1) {



            val deleteDoc = recipeList.get(i).recipeName


                val docRef = mFirebaseDatabaseInstance!!.collection("recipe")
                    .document("${recipeList.get(i).recipeName}")

                docRef
                    .delete()
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener {

                    }


                val fireb =
                    Firebase.storage.reference.child("Img/${recipeList.get(i).recipeName}.jpg")
                fireb.delete().addOnSuccessListener {

                }.addOnFailureListener {

                }

                recipeList.removeAt(i)

                _recipeList.value=recipeList
//                                val arr = CustomAdapter(modalList, this)
//                                arr.notifyDataSetChanged()
//                                docView.adapter = arr

            }


        }




    fun deletionDocAfterSearch(dtname: String,i:Int) {

        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()








        if(i>-1) {



            val deleteDoc =recipeListSearch.get(i).recipeName

            if (deleteDoc =="Dr $dtname") {
                val docRef = mFirebaseDatabaseInstance!!.collection("recipe")
                    .document("${recipeListSearch.get(i).recipeName}")

                docRef
                    .delete()
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener {

                    }


                val fireb =
                    Firebase.storage.reference.child("Img/${recipeListSearch.get(i).recipeName}.jpg")
                fireb.delete().addOnSuccessListener {

                }.addOnFailureListener {

                }

                recipeListSearch.removeAt(i)

                _recipeList.value=recipeListSearch
//                                val arr = CustomAdapter(modalList, this)
//                                arr.notifyDataSetChanged()
//                                docView.adapter = arr

            }


        }

    }
















    fun callForSearching(temp:ArrayList<String>,searchQuery:String){


        if (temp.isNotEmpty()) {
            dataChanged(temp, searchQuery)
        }
    }









    fun dataChanged(tempName: ArrayList<String>, searchQuery: String) {



//        var j = 0
        val size=tempName.size
        for (i in arraylistRecipeName.indices) {
            for(j in tempName.indices) {
                if (arraylistRecipeName[i] == tempName[j]) {

                    val cache = MyCache()
                    val bitmap: Bitmap? = cache.retrieveBitmapFromCache(tempName[j])

                    bitmap?.let {
                        recipe(
                            arraylistIngredients[i],
                            it,
                            arraylistRecipeName[i],
                            arraylistSteps[i],
                            arraylistRecipeType[i]
                        )
                    }?.let {
                        recipeListSearch.add(
                            it
                        )
                    }



                }


            }


        }
//
//         _modalListSearch.value=modalListSearch

        Thread {
            _recipeListSearch.postValue(recipeListSearch)
        }.start()

    }





}