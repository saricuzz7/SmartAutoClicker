package com.smartclicker.pro

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*
import android.content.Context

class MainActivity : Activity() {

    private val green = Color.rgb(0, 255, 120)
    private val bg = Color.rgb(15, 18, 20)
    private val prefs by lazy {
        getSharedPreferences("scenarios", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHome()
    }

    private fun button(text: String, action: () -> Unit): Button {
        return Button(this).apply {
            this.text = text
            setTextColor(Color.BLACK)
            setBackgroundColor(green)
            textSize = 16f
            setOnClickListener { action() }
        }
    }

    private fun showHome() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 24, 24, 24)
            setBackgroundColor(bg)
        }

        layout.addView(TextView(this).apply {
            text = "SMART AUTO CLICKER PRO"
            textSize = 24f
            setTextColor(green)
            gravity = Gravity.CENTER
        })

        layout.addView(TextView(this).apply {
            text = "\nScenario yarat • Saqla • Qayta ishlat"
            textSize = 16f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        })

        layout.addView(button("＋ CREATE SCENARIO") {
            createScenario()
        })

        layout.addView(button("📂 MY SCENARIOS") {
            showScenarios()
        })

        layout.addView(button("⚙ SETTINGS") {
            showSettings()
        })

        setContentView(layout)
    }

    private fun createScenario() {
        val input = EditText(this).apply {
            hint = "Scenario nomi"
            setSingleLine(true)
        }

        AlertDialog.Builder(this)
            .setTitle("Yangi scenario")
            .setView(input)
            .setPositiveButton("SAVE") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isNotEmpty()) {
                    prefs.edit().putString(name, "").apply()
                    editScenario(name)
                }
            }
            .setNegativeButton("CANCEL", null)
            .show()
    }

    private fun showScenarios() {
        val names = prefs.all.keys.toList()

        if (names.isEmpty()) {
            Toast.makeText(this, "Hali scenario yo‘q", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(this)
            .setTitle("MY SCENARIOS")
            .setItems(names.toTypedArray()) { _, index ->
                editScenario(names[index])
            }
            .setNegativeButton("BACK", null)
            .show()
    }

    private fun editScenario(name: String) {
        val input = EditText(this).apply {
            hint = "Masalan: CLICK 500, WAIT 1000"
            setText(prefs.getString(name, ""))
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            minLines = 4
            gravity = Gravity.TOP
        }

        AlertDialog.Builder(this)
            .setTitle(name)
            .setMessage("Amallarni yoz: CLICK 500 yoki WAIT 1000")
            .setView(input)
            .setPositiveButton("SAVE") { _, _ ->
                prefs.edit().putString(name, input.text.toString()).apply()
                Toast.makeText(this, "Scenario saqlandi!", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("RUN") { _, _ ->
                Toast.makeText(
                    this,
                    "Saqlangan amallar: ${input.text}",
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("BACK", null)
            .show()
    }

    private fun showSettings() {
        AlertDialog.Builder(this)
            .setTitle("Settings")
            .setMessage("Smart Auto Clicker PRO 1.0\nScenario saqlash faol.")
            .setPositiveButton("OK", null)
            .show()
    }
}
