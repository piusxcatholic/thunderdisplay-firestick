package net.piusx.thunderdisplay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class SettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val prefs = getSharedPreferences("thunderdisplay", MODE_PRIVATE)
        val currentUrl = prefs.getString("display_url", "") ?: ""

        // Build UI programmatically — no XML layout needed
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(80, 80, 80, 80)
        }

        val title = TextView(this).apply {
            text = "ThunderDisplay Settings"
            textSize = 24f
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 40)
        }

        val label = TextView(this).apply {
            text = "Display URL for this screen:"
            textSize = 16f
            setPadding(0, 0, 0, 16)
        }

        val urlInput = EditText(this).apply {
            setText(currentUrl)
            hint = "https://piusx.net/display/your-screen-slug/"
            textSize = 16f
            setPadding(16, 16, 16, 16)
        }

        val saveButton = Button(this).apply {
            text = "Save & Launch Display"
            textSize = 18f
            setPadding(40, 20, 40, 20)
            setOnClickListener {
                val newUrl = urlInput.text.toString().trim()
                if (newUrl.isNotEmpty()) {
                    prefs.edit().putString("display_url", newUrl).apply()
                    startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
                    finish()
                }
            }
        }

        layout.addView(title)
        layout.addView(label)
        layout.addView(urlInput)
        layout.addView(saveButton)

        setContentView(layout)
    }
}
