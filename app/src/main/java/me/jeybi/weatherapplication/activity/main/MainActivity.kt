package me.jeybi.weatherapplication.activity.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jeybi.weatherapplication.R
import me.jeybi.weatherapplication.activity.BaseActivity
import me.jeybi.weatherapplication.model.City
import me.jeybi.weatherapplication.network.ApiList
import me.jeybi.weatherapplication.network.RetrofitClient
import me.jeybi.weatherapplication.utils.Constants
import java.io.IOException
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import me.jeybi.weatherapplication.adapter.CitiesAdapter


class MainActivity : BaseActivity(),MainMvp.view {
    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }


    lateinit var presenter : MainMvp.presenter

    private val disposable = CompositeDisposable()


    override fun onViewDidCreate(savedInstanceState: Bundle?) {
        presenter = MainPresenter(this)
        presenter.loadJsonDataFromAssets(assets.open("cities.json"))
    }

    override fun onCitiesListReady(cities: ArrayList<City>,longIdString : String) {

        recyclerViewCities.layoutManager = LinearLayoutManager(this)
        recyclerViewCities.adapter = CitiesAdapter(this,cities,null)


        val apiList = RetrofitClient.instance.create(ApiList::class.java)


      disposable.add(
              apiList.getCitiesData(longIdString,Constants.UNIT_METRIC,Constants.api_key)
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribeOn(Schedulers.io())
                      .subscribe({
                          recyclerViewCities.adapter = CitiesAdapter(this,cities,it.list)
                      },{
                          Log.d("LOLLO",it.message)
                      })
      )

    }

}
