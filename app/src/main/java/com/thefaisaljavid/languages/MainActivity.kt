package com.thefaisaljavid.languages

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("mylang", "")
        Log.e("language", "$language onCreate()")


        var tvLang = findViewById<Button>(R.id.tvLang)
        var eng = findViewById<Button>(R.id.eng)
        var urdu = findViewById<Button>(R.id.urdu)
        tvLang.setOnClickListener {
            changeLanguageDialog()
        }

        eng.setOnClickListener {
            english()
        }
        urdu.setOnClickListener {
            urdu()
        }
    }

    private fun english(){
        setLocale("en")
        recreate()
    }
    private fun urdu(){
        setLocale("ur")
        recreate()
    }
    private fun changeLanguageDialog() {
        val array = arrayOf("English", "Urdu")

        val mBuilder = AlertDialog.Builder(this)
        //set title for alert dialog
        mBuilder.setTitle(R.string.choose_languages)

        mBuilder.setSingleChoiceItems(array, -1) { dialog, which ->
            if (which == 0) {
                setLocale("en")
                recreate()
            } else if (which == 1) {
                setLocale("ur")
                recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
        editor.putString("mylang", language)
        editor.commit()
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("mylang", "")
        Log.e("", "$language loadLocate()")
        setLocale(language.toString())
    }

    override fun onResume() {
        loadLocate()
        super.onResume()
    }

    override fun onStart() {
        loadLocate()
        super.onStart()
    }
}