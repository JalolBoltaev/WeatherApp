package me.jeybi.weatherapplication.network

import io.reactivex.Observable
import me.jeybi.weatherapplication.model.CitiesDataResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiList {
    @GET("data/2.5/group")
    fun getCitiesData(@Query("id") id : String, @Query("units") units : String, @Query("appid") token : String) : Observable<CitiesDataResponse>

    @GET("data/2.5/forecast")
    fun getCityWeeklyData(@Query("id") id : String,@Query("appid") token : String,@Query("cnt") cnt : Int) : Observable<CitiesDataResponse>

}
