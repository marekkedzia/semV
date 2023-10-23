package com.unitscalculator

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var currentlyChecked = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configSwitches()
        configSearchButton()
    }

    private fun configSwitches() {
        val categorySwitches: Array<Switch> = arrayOf(
            findViewById(R.id.switchBar),
            findViewById(R.id.switchGeographic),
            findViewById(R.id.switchHistorical),
            findViewById(R.id.switchSport),
        )

        categorySwitches.map { switch ->
            switch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    currentlyChecked++
                    changeBatteryImage(currentlyChecked)
                } else {
                    currentlyChecked--
                    changeBatteryImage(currentlyChecked)
                }
            }
        }
    }

    private fun configSearchButton() {
        val searchButton: Button = findViewById(R.id.buttonSearch)
        searchButton.setOnClickListener {
            search()
        }
    }

    private fun changeBatteryImage(batteryLevel: Int) {
        val batteryImage: ImageView = findViewById(R.id.imageBattery)
        when (batteryLevel) {
            1 -> batteryImage.setImageResource(R.drawable.battery_quarter_solid_11_26_38)
            2 -> batteryImage.setImageResource(R.drawable.battery_half_solid_11_26_38)
            3 -> batteryImage.setImageResource(R.drawable.battery_three_quarters_solid)
            4 -> batteryImage.setImageResource(R.drawable.battery_full_solid)
            else -> batteryImage.setImageResource(R.drawable.battery_empty_solid)
        }
    }

    private fun search() {
        val checkbox: CheckBox = findViewById(R.id.checkBox)
        val amount:Int = findViewById<SeekBar?>(R.id.seekBarAmount).progress

        if (checkbox.isChecked && currentlyChecked > 0) {
            showTrivias(amount)
        } else {
            AlertDialog.Builder(this)
                .setMessage("Proszę wyrazić zgodę oraz wybrać przynajmniej jedną kategorię przed rozpoczęciem wyszukiwania!")
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun showTrivias(amount:Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.scrollable_text_dialog)
        val dialogTextView: TextView = dialog.findViewById(R.id.dialogTextView)
        dialogTextView.text = findTrivias(findCategories(), amount);

        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.show()
    }

    private fun findCategories(): ArrayList<Category> {
        val categories = ArrayList<Category>()

        if (findViewById<Switch>(R.id.switchBar).isChecked) {
            categories.add(Category.BAR)
        }
        if (findViewById<Switch>(R.id.switchGeographic).isChecked) {
            categories.add(Category.GEOGRAPHY)
        }
        if (findViewById<Switch>(R.id.switchHistorical).isChecked) {
            categories.add(Category.HISTORY)
        }
        if (findViewById<Switch>(R.id.switchSport).isChecked) {
            categories.add(Category.SPORT)
        }
        return categories
    }


    private fun findTrivias(categories: ArrayList<Category>, amount: Int): CharSequence {
        val triviasAmount = if (amount != 0) amount else 1

        val categoryAmount = triviasAmount / categories.size
        var trivias = ""
        categories.forEach { c ->
            trivias += "${getTrivia(c, categoryAmount)} \n"
        }

        return trivias
    }

    private fun getTrivia(category: Category, amount: Int): String {
        var trivias =  Trivias();
        val triviasList = mutableListOf<String>()
        when(category) {
            Category.BAR -> {
                val bar = trivias.bar
                for (i in 1..amount) {
                    val randomTrivia = bar[Random.nextInt(0, bar.size)]
                    triviasList.add(randomTrivia)
                }
            }

            Category.GEOGRAPHY -> {
                val geography = trivias.geography
                for (i in 1..amount) {
                    val randomTrivia = geography[Random.nextInt(0, geography.size)]
                    triviasList.add(randomTrivia)
                }
            }

            Category.HISTORY -> {
                val historical = trivias.history
                for (i in 1..amount) {
                    val randomTrivia = historical[Random.nextInt(0, historical.size)]
                    triviasList.add(randomTrivia)
                }
            }

            Category.SPORT -> {
                val sport = trivias.sport
                for (i in 1..amount) {
                    val randomTrivia = sport[Random.nextInt(0, sport.size)]
                    triviasList.add(randomTrivia)
                }
            }
        }
            return triviasList.joinToString("\n\n")
        }
    }
