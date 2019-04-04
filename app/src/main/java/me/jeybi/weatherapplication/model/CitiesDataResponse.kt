package me.jeybi.weatherapplication.model

data class CitiesDataResponse(
        val cnt : Int,
        val list : ArrayList<WholeData>

)

data class WholeData(
        val coord : Coordination,
        val weather : ArrayList<Weather>,
        val main : MainData,
        val visibility : Long,
        val wind : WindData
)

data class WindData(
        val speed : Float,
        val deg : Float
)

data class MainData(
        val temp : Float,
        val pressure : Float,
        val humidity : Float,
        val temp_min : Float,
        val temp_max : Float
)

data class Coordination(
        val lon : Double,
        val lat : Double
)

data class Weather(
        val id : Int,
        val main : String,
        val description : String,
        val icon : String
)