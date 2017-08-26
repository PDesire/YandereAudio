package com.meli.pdesire.yandereservice

/**
 * Created by PDesire on 20.05.2017.
 */


import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import android.widget.Toast
import com.meli.pdesire.yandereservice.framework.YanderePackageManager
import org.jetbrains.annotations.Nullable
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
class SettingsActivity : AppCompatPreferenceActivity(), NavigationView.OnNavigationItemSelectedListener {

    private fun closedReleaseTest () {
        if (YanderePackageManager.closedReleaseTest(this)) {
            Toast.makeText(this, getString(R.string.security_error),
                    Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setupActionBar() {
        var bar = actionBar
        if (bar != null)
            bar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        closedReleaseTest()
    }


    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        loadHeadersFromResource(R.xml.pref_headers, target)
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean {
        return PreferenceFragment::class.java.name == fragmentName
                || AudioSettingsFragment::class.java.name == fragmentName
                || EngineFragment::class.java.name == fragmentName
                || CreditsFragment::class.java.name == fragmentName
    }

    public override fun onStart() {
        super.onStart()
        closedReleaseTest()
    }

    public override fun onStop() {
        super.onStop()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_service) {
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.nav_pdesireaudio) {
            val intent = Intent(applicationContext, PDesireAudioActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.light_theme) {
            this.setTheme(R.style.AppTheme)
            recreate()
        } else if (id == R.id.dark_theme) {
            this.setTheme(R.style.AppThemeDark)
            recreate()
        } else if (id == R.id.nav_contact_xda) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/crossdevice-dev/sony/soundmod-project-desire-feel-dream-sound-t3130504"))
            startActivity(intent)
        } else if (id == R.id.nav_contact_pdesire) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/member.php?u=6126659"))
            startActivity(intent)
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}