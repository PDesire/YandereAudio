package com.meli.pdesire.yandereservice

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceFragment
import com.meli.pdesire.yandereservice.fragments.*
import com.meli.pdesire.yandereservice.framework.YandereOutputWrapper
import com.meli.pdesire.yandereservice.framework.YanderePackageManager
import projectmeli.yandereaudio.pdesire.R

/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 *
 * See [
   * Android Design: Settings](http://developer.android.com/design/patterns/settings.html) for design guidelines and the [Settings
   * API Guide](http://developer.android.com/guide/topics/ui/settings.html) for more information on developing a Settings UI.
 */
class UniversalManagementActivity : AppCompatPreferenceActivity() {

    private val PREFS_NAME = "prefs"
    private val PREF_NEW_THEME = "new_theme"

    private fun closedReleaseTest () {
        if (YanderePackageManager.closedReleaseTest(this)) {
            YandereOutputWrapper.outputToast(R.string.security_error, this)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Use the chosen theme
        val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val useNewTheme = preferences.getBoolean(PREF_NEW_THEME, false)

        if (useNewTheme) {
            setTheme(R.style.AppTheme_New)
        }

        super.onCreate(savedInstanceState)

        closedReleaseTest()
    }


    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onBuildHeaders(target: List<Header>) {
        loadHeadersFromResource(R.xml.pref_headers_universal, target)
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean {
        return PreferenceFragment::class.java.name == fragmentName
                || HTCCallibrationFragment::class.java.name == fragmentName
                || SupportedEqualizerFragment::class.java.name == fragmentName
    }

    public override fun onStart() {
        super.onStart()
        closedReleaseTest()
    }

    public override fun onStop() {
        super.onStop()
    }
}