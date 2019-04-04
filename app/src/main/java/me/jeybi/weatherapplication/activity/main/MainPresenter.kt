package me.jeybi.weatherapplication.activity.main

import android.util.Log
import me.jeybi.weatherapplication.model.City
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class MainPresenter(val view: MainMvp.view) : MainMvp.presenter {


    //// Loading all information about 11 cities from local .json file in assets folder

    override fun loadJsonDataFromAssets(inputStream: InputStream) {
        var json: String? = null
        try {

            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            json = ""
        }
        parseCities(json!!)
    }

    //// Parsing JSON into array of objects

    fun parseCities(json: String) {

        val citiesList = ArrayList<City>()
        //// IDs of all cities together as 1 string in order to request GROUP FORECAST from openweathermap.com
        var longIdString = ""

        try {
            val arrayOfCities = JSONArray(json)

            for (i in 0 until arrayOfCities.length()) {
                val cityObject: JSONObject = arrayOfCities[i] as JSONObject
                citiesList.add(City(
                        cityObject.getString("id"),
                        cityObject.getString("name"),
                        cityObject.getString("color"),
                        cityObject.getString("image")
                ))
                if (i != 0)
                    longIdString += ","
                longIdString += cityObject.getString("id")
            }


            view.onCitiesListReady(citiesList,longIdString)
        } catch (e: JSONException) {
            Log.d("LOLLO", "${e}")
        }
    }


}