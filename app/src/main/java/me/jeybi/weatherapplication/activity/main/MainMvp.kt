package me.jeybi.weatherapplication.activity.main

import me.jeybi.weatherapplication.model.City
import java.io.InputStream

interface MainMvp {
    interface view{
        fun onCitiesListReady(cities : ArrayList<City>,longIdString : String)
    }
    interface presenter{
        fun loadJsonDataFromAssets(inputStream : InputStream)
    }
}