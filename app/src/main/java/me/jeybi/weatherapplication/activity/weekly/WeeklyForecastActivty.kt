package me.jeybi.weatherapplication.activity.weekly

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_weekly_forecast_activty.*
import me.jeybi.weatherapplication.R
import me.jeybi.weatherapplication.activity.BaseActivity
import me.jeybi.weatherapplication.adapter.CitiesAdapter
import me.jeybi.weatherapplication.adapter.DailyForecastAdapter
import me.jeybi.weatherapplication.network.ApiList
import me.jeybi.weatherapplication.network.RetrofitClient
import me.jeybi.weatherapplication.utils.Constants

class WeeklyForecastActivty : BaseActivity() {

    var bundle: Bundle?=null
    override fun setLayoutId(): Int {
        return R.layout.activity_weekly_forecast_activty
    }

    private val disposable = CompositeDisposable()

    override fun onViewDidCreate(savedInstanceState: Bundle?) {
        bundle = intent.extras
        recyclerViewCities.layoutManager = LinearLayoutManager(this)


        /// Getting data from previous activity

        if (bundle!=null){
            val id = bundle!!.getString("id")
            val color = bundle!!.getString("color")
            val image = bundle!!.getString("image")
            val name = bundle!!.getString("name")
            val temp = bundle!!.getFloat("temp")
            val desc = bundle!!.getString("title")
            val weather_id = bundle!!.getInt("weather_id")

            rvBackground.setBackgroundColor(Color.parseColor(color))
            Glide.with(this).load(image).into(imageViewCity)
            textViewCityTitle.text = name
            textViewTemperature.text ="${temp}°"
            textViewDesc.text = desc

            // Based on the ID of the Weather, setting up the icons

            when(weather_id){
                in 200 until 233 -> lottieWeather.setAnimation("thunder.json")
                in 300 until 322 -> lottieWeather.setAnimation("drizzle.json")
                in 500 until 532 -> lottieWeather.setAnimation("rain.json")
                in 600 until 623 -> lottieWeather.setAnimation("snow.json")
                in 700 until 781 -> lottieWeather.setAnimation("mist.json")
                800 -> lottieWeather.setAnimation("clean.json")
                in 801 until 805 -> lottieWeather.setAnimation("cloudy.json")
            }


            val apiList = RetrofitClient.instance.create(ApiList::class.java)

            /// Creating disposable in order to prevent crash when subscribsion gets succeed when already activity is not alive

            disposable.add(
                    apiList.getCityWeeklyData(id!!, Constants.api_key ,7)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                recyclerViewCities.adapter = DailyForecastAdapter(this,it.list)
                            },{
                                Log.d("LOLLO",it.message)
                            })
            )

        }

    }

    override fun onStop() {
        super.onStop()

        //Unsubscribing the existing observable
        disposable.dispose()
    }

}
