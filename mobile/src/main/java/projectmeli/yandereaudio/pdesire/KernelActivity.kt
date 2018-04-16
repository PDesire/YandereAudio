package projectmeli.yandereaudio.pdesire

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle

class KernelActivity : Activity() {

    private val PREFS_NAME = "prefs"
    private val PREF_NEW_THEME = "new_theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        val useNewTheme = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(PREF_NEW_THEME, false)

        if (useNewTheme) {
            setTheme(R.style.AppTheme_New)
        }

        super.onCreate(savedInstanceState)

        setContentView(R.layout.kernel_main)
        window.decorView.setBackgroundColor(Color.WHITE)
    }
}