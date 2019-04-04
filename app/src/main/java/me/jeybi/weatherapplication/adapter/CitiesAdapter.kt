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

class CitiesAdapter(val context : Context,val cities : ArrayList<City>,val listOfData : ArrayList<WholeData>?) : RecyclerView.Adapter<CitiesAdapter.CitiesHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CitiesHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cities,p0,false)
        return CitiesHolder(view)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CitiesHolder, p1: Int) {
        val city = cities[p1]

        holder.textViewName.text = city.name

        if (listOfData!=null){
            holder.textViewDegree.text = "${listOfData[p1].main.temp_min}°C / ${listOfData[p1].main.temp_max}°C"

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

        holder.itemView.setOnClickListener {
            val intent = Intent(context,WeeklyForecastActivty::class.java)
            intent.putExtra("id",city.id)
            intent.putExtra("color",city.color)
            intent.putExtra("name",city.name)
            intent.putExtra("image",city.image)
            if (listOfData!=null) {
                intent.putExtra("weather_id", listOfData[p1].weather[0].id)
                intent.putExtra("lon",listOfData[p1].coord.lon)
                intent.putExtra("lon",listOfData[p1].coord.lat)
                intent.putExtra("temp",listOfData[p1].main.temp)
                intent.putExtra("title",listOfData[p1].weather[0].main)
            }
            context.startActivity(intent)
        }
    }

    class CitiesHolder(view : View) : RecyclerView.ViewHolder(view){
        val textViewName = view.findViewById<TextView>(R.id.textViewCityTitle)
        val textViewDegree = view.findViewById<TextView>(R.id.textViewDegree)
        val lottieWeather = view.findViewById<LottieAnimationView>(R.id.lottieWeather)
    }
}