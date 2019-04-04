package me.jeybi.weatherapplication.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import me.jeybi.weatherapplication.R
import me.jeybi.weatherapplication.activity.weekly.WeeklyForecastActivty
import me.jeybi.weatherapplication.model.City
import me.jeybi.weatherapplication.model.WholeData

class DailyForecastAdapter(val context : Context, val listOfData : ArrayList<WholeData>) : RecyclerView.Adapter<DailyForecastAdapter.DailyForecastHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DailyForecastHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_days,p0,false)
        return DailyForecastHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfData!!.size
    }

    override fun onBindViewHolder(holder:  DailyForecastHolder, p1: Int) {
        val data = listOfData[p1]

        holder.textViewName.text = getDayName(p1)

        if (listOfData!=null){
            holder.textViewDegree.text = "${listOfData[p1].main.temp.toInt()-273}°C"

            when(listOfData[p1].weather[0].id){
                in 200 until 233 -> holder.lottieWeather.setAnimation("thunder.json")
                in 300 until 322 -> holder.lottieWeather.setAnimation("drizzle.json")
                in 500 until 532 -> holder.lottieWeather.setAnimation("rain.json")
                in 600 until 623 -> holder.lottieWeather.setAnimation("snow.json")
                in 700 until 781 -> holder.lottieWeather.setAnimation("mist.json")
                800 -> holder.lottieWeather.setAnimation("clean.json")
                in 801 until 805 -> holder.lottieWeather.setAnimation("cloudy.json")

            }
        }


    }

    private fun getDayName(position: Int): String {
        return when(position){
            0-> "Monday"
            1-> "Tuesday"
            2-> "Wednesday"
            3-> "Thursday"
            4-> "Friday"
            5-> "Saturday"
            6-> "Sunday"
            else -> "Monday"
        }
    }

    class  DailyForecastHolder(view : View) : RecyclerView.ViewHolder(view){
        val textViewName = view.findViewById<TextView>(R.id.textViewCityTitle)
        val textViewDegree = view.findViewById<TextView>(R.id.textViewDegree)
        val lottieWeather = view.findViewById<LottieAnimationView>(R.id.lottieWeather)
    }
}