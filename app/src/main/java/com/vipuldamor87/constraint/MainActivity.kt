package com.vipuldamor87.constraint

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"
    lateinit var spinner: Spinner
    lateinit var locale: Locale
    private var currentLanguage = "en"
    private var currentLang: String? = null
    lateinit var  sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar!!.hide()
        sharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        setlanguage()
        setContentView(R.layout.activity_main)
        title = "KotlinApp"
        spinner = findViewById(R.id.spinner)

        val list = ArrayList<String>()
        list.add("Current language is ${getcurrentLanguage()}")
        list.add("English")
        list.add("Gujarati")
        list.add("Hindi")
        list.add("Arabic")


        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                    }
                    1 -> setLocale("en")
                    2 -> setLocale("gu")
                    3 -> setLocale("hi")
                    4 -> setLocale("ar")

                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun getcurrentLanguage(): String {
        if (currentLanguage == "gu"){return "Gujarati"}
        else if (currentLanguage == "hi"){return "Hindi"}
        else if (currentLanguage == "ar"){return "Arabic"}
        else if (currentLanguage == "en"){ return "English"}
        else{ return "none"}
    }

    private fun setlanguage() {
        val localeName = sharedPreferences.getString("localname","en")
        Log.d("local","$localeName")
        locale = Locale(localeName)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        currentLanguage = localeName.toString()
        conf.locale = locale
        res.updateConfiguration(conf, dm)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("localname",localeName)
        editor.apply()
        editor.commit()
        if(currentLanguage == "ar") {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
            Log.d("Mytag","RTL selected")
        }
    }

    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("localname",localeName)
            editor.apply()
            editor.commit()
            val refresh = Intent(
                    this,
                    MainActivity::class.java
            )
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)
        } else {
            Toast.makeText(
                    this@MainActivity, "Language, , already, , selected)!", Toast.LENGTH_SHORT).show();
        }
    }
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
        exitProcess(0)
    }
}



