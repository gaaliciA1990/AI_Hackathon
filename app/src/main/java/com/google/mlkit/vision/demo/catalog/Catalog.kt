package com.google.mlkit.vision.demo.catalog
import android.content.Context
import java.io.FileOutputStream
import java.io.IOException

class Catalog {

    private var currentList = mutableMapOf<String, Array<String>>()

    fun updateList(listName: String = "Generic", item: String): Boolean{
        //default value for listName for proof of concept purposes

        //Check if the list not existed, create if true
        if (!currentList.containsKey(listName)){
            println("List not existed $listName")
            currentList[listName] = arrayOf()
        }

        //Then Append
        //println("List already existed $listName")
        currentList[listName]?.plus(item)
        return true;
    }

    fun getAllItems(listName:String = "Generic"): Array<String>? {
        return currentList[listName]
    }

    //fun readFromFile

    fun writeToFile(context: Context, filename: String, data: String) {
        try {
            val outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            outputStream.write(data.toByteArray())
            outputStream.close()
            println("Data written to file: $filename")
        } catch (ex: IOException) {
            println("Error writing to file: $filename")
        }
    }
}