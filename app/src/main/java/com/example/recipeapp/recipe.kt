package com.example.recipeapp

import android.graphics.Bitmap

class recipe {






    var ingredients:String?=null

    var image: Bitmap?=null
    var recipeName:String?=null
    var step:String?=null
    var recipeType:String?=null

    constructor()
    constructor(ingredients:String, image: Bitmap, recipeName:String, step:String, recipeType:String){
        this. ingredients=ingredients

        this.image=image

        this.recipeName=recipeName
        this.step=step
        this. recipeType= recipeType






    }
}