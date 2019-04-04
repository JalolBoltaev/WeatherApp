package me.jeybi.weatherapplication.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        onViewDidCreate(savedInstanceState)
    }

    abstract fun onViewDidCreate(savedInstanceState: Bundle?)
    abstract @LayoutRes fun setLayoutId() : Int
}