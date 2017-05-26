package teamdevelite.yandereaudio20.pdesire

/**
 * Created by PDesire on 20.05.2017.
 */

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private fun checkLuckyPatcher(): Boolean {
        if (packageExists("com.dimonvideo.luckypatcher")) {
            return true
        }

        if (packageExists("com.chelpus.lackypatch")) {
            return true
        }

        if (packageExists("com.android.vending.billing.InAppBillingService.LACK")) {
            return true
        }

        if (packageExists("com.android.vending.billing.InAppBillingService.LOCK")) {
            return true
        }

        return false
    }

    private fun packageExists(packageName: String): Boolean {
        try {
            val info = this.packageManager.getApplicationInfo(packageName, 0) ?: // No need really to test for null, if the package does not
                    // exist it will really rise an exception. but in case Google
                    // changes the API in the future lets be safe and test it
                    return false

            return true
        } catch (ex: Exception) {
            // If we get here only means the Package does not exist
        }

        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        if (checkLuckyPatcher()) {
            val pirate = AlertDialog.Builder(this)
            pirate.setTitle("PIRATE DETECTED")
            pirate.setMessage("If I would be you, I would uninstall Lucky Patcher and never use it anymore... \n \n I am sure you want to use Project Meli... \n \n Just saying: I know how to make Project Meli unusable, \n so you will not get any sound enhancements")
            pirate.create()
            pirate.show()
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_service) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.component = ComponentName("com.meli.pdesire.projectmeliaudioeffects", "com.meli.pdesire.projectmeliaudioeffects.SettingsActivity")
            startActivity(intent)
        } else if (id == R.id.nav_pdesireaudio) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.component = ComponentName("com.meli.pdesire.projectmeliaudioeffects", "com.meli.pdesire.projectmeliaudioeffects.PDesireAudioActivity")
            startActivity(intent)
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
