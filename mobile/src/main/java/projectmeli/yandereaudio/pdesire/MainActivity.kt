/*
 * Copyright (C) 2017-2018 Tristan Marsell, All rights reserved.
 *
 * This code is licensed under the BSD-3-Clause License
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package projectmeli.yandereaudio.pdesire

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.github.javiersantos.appupdater.AppUpdater
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.meli.pdesire.yanderecore.*
import com.meli.pdesire.yanderecore.framework.YandereFileManager
import com.meli.pdesire.yanderecore.framework.YandereOutputWrapper
import com.meli.pdesire.yanderecore.framework.YanderePackageManager
import com.meli.pdesire.yanderecore.framework.YandereRootUtility
import com.pdesire.projectcluster.core.AssetCopyManager
import io.fabric.sdk.android.Fabric


/**
 * Created by PDesire on 20.05.2017.
 */


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val PREFS_NAME = "prefs"
    private val PREF_NEW_THEME = "new_theme"
    private val PREF_YANDERE = "yandere"
    private val PREF_ANALYTICS = "analytics"
    private val PREF_ANALYTICS_FABRIC = "analytics_fabric"
    private val PREF_ADS = "ads"

    private val PREFS_CLUSTER_NAME = "cluster_prefs"
    private val PREF_CLUSTER_SUCCESS = "cluster_success"

    private val mOutputWrapper : YandereOutputWrapper? = YandereOutputWrapper(this)
    private lateinit var mInterstitialAd: InterstitialAd

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mFirebaseRemoteConfig: FirebaseRemoteConfig? = null

    fun showAds() {
        val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val useAds = preferences.getBoolean(PREF_ADS, true)
        if (mInterstitialAd.isLoaded && useAds) {
            mInterstitialAd.show()
        }
    }


    private fun toggleThemeNew(newTheme: Boolean) {
        val editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putBoolean(PREF_NEW_THEME, newTheme)
        editor.apply()

        val intent = intent
        finish()

        startActivity(intent)
    }

    private fun closedReleaseTest() {
        if (YanderePackageManager.closedReleaseTest(this)) {
            Toast.makeText(this, getString(R.string.security_error),
                    Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun meliInstalledCheck(): Boolean {
        if (!YandereFileManager.fileCheck("/system/meli.prop")) {
            val messageOutput = AlertDialog.Builder(this)
            messageOutput.setTitle(getString(R.string.meli_not_installed))
                    .setMessage(getString(R.string.no_project_meli_installed))
                    .setPositiveButton(getString(R.string.go_to_thread)) { _, _ ->
                        if (System.getProperty("ro.service.xfrm.supported")!!.equals("true")) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/crossdevice-dev/sony/soundmod-project-desire-feel-dream-sound-t3130504"))
                            startActivity(intent)
                        } else {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/android/software/soundmod-project-desire-hear-perfection-t3183119"))
                            startActivity(intent)
                        }

                    }
                    .setNegativeButton(getString(R.string.ignore)) { _, _ ->
                        // do nothing
                    }
                    .setCancelable(false)
                    .setIcon(R.mipmap.ic_launcher)
                    .create()
                    .show()
            return false
        }
        return true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val useYandere = preferences.getBoolean(PREF_YANDERE, false)

        val cluster = getSharedPreferences(PREFS_CLUSTER_NAME, Context.MODE_PRIVATE)
        val cluster_success = cluster.getBoolean(PREF_CLUSTER_SUCCESS, false)

        closedReleaseTest()

        YandereRootUtility.obtainSURights()

        if (meliInstalledCheck() && !useYandere) {
            val messageOutput = AlertDialog.Builder(this)
            messageOutput.setTitle(R.string.hello_yandere)
                    .setMessage(R.string.hello_yandere_description)
                    .setPositiveButton(R.string.ignore) { _, _ ->

                    }
                    .setNegativeButton(R.string.never_show_again) { _, _ ->
                        editor.putBoolean(PREF_YANDERE, true)
                        editor.apply()
                    }
                    .setCancelable(false)
                    .setIcon(R.mipmap.ic_launcher)
                    .create()
                    .show()
        }

        try {
            if (System.getProperty("ro.projectcluster.state")!!.equals("true") && !cluster_success) {
                val intent = Intent(applicationContext, AssetCopyManager::class.java)
                startActivity(intent)
            }
        } catch (n0 : NullPointerException) {
            n0.printStackTrace()
        }

        val appUpdater = AppUpdater(this)
        appUpdater.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Use the chosen theme
        val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val useNewTheme = preferences.getBoolean(PREF_NEW_THEME, false)
        val useGoogleAnalytics = preferences.getBoolean(PREF_ANALYTICS, false)
        val useFabricAnalytics = preferences.getBoolean(PREF_ANALYTICS_FABRIC, false)
        val useAds = preferences.getBoolean(PREF_ADS, true)

        if (useNewTheme) {
            setTheme(R.style.AppTheme_New_NoActionBar)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val animate = AnimationUtils.loadAnimation(this, R.anim.fade)

        MobileAds.initialize(this, "ca-app-pub-6207390033733991~9531735666")

        if (useAds) {
            mInterstitialAd = InterstitialAd(this)
            mInterstitialAd.adUnitId = "ca-app-pub-6207390033733991/6525356424"
            mInterstitialAd.loadAd(AdRequest.Builder().build())
        }

        mOutputWrapper!!.addNotification(getString(R.string.welcome_back), getString(R.string.welcome_back_yandereaudio))

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        mFirebaseRemoteConfig!!.setDefaults(R.xml.firebase_values)
        mFirebaseRemoteConfig!!.fetch(50)

        if (!useGoogleAnalytics) {
            // Google Firebase Analytics
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

            val params = Bundle()

            params.putString(FirebaseAnalytics.Param.ITEM_ID, "id_open")
            params.putString(FirebaseAnalytics.Param.ITEM_NAME, "open")
            params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "App opened")
            mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)


        } else {
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false)
        }

        if (!useFabricAnalytics) {
            // Fabric.io Analytics
            Fabric.with(this, Crashlytics())
            Answers.getInstance().logContentView(ContentViewEvent())
        }

        val cardview_intro = findViewById<CardView>(R.id.intro)
        val cardview_secd = findViewById<CardView>(R.id.secd)
        val cardview_thrd = findViewById<CardView>(R.id.thrd)
        val cardview_fth = findViewById<CardView>(R.id.fth)
        val cardview_fifth = findViewById<CardView>(R.id.fifth)

        cardview_intro.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/crossdevice-dev/sony/soundmod-project-desire-feel-dream-sound-t3130504"))

            cardview_intro.startAnimation(animate)
            cardview_secd.startAnimation(animate)
            cardview_thrd.startAnimation(animate)
            cardview_fth.startAnimation(animate)
            cardview_fifth.startAnimation(animate)
            cardview_fth.postDelayed({
                startActivity(intent)
                this.recreate()
                showAds()
            }, animate.duration)
        }

        cardview_secd.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/showpost.php?p=75476501&postcount=3308"))

            cardview_intro.startAnimation(animate)
            cardview_secd.startAnimation(animate)
            cardview_thrd.startAnimation(animate)
            cardview_fth.startAnimation(animate)
            cardview_fth.postDelayed({
                startActivity(intent)
                this.recreate()
                showAds()
            }, animate.duration)
        }

        cardview_thrd.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/android/software/soundmod-project-desire-hear-perfection-t3183119"))

            cardview_intro.startAnimation(animate)
            cardview_secd.startAnimation(animate)
            cardview_thrd.startAnimation(animate)
            cardview_fth.startAnimation(animate)
            cardview_fifth.startAnimation(animate)
            cardview_fth.postDelayed({
                startActivity(intent)
                this.recreate()
                showAds()
            }, animate.duration)
        }

        cardview_fth.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/PDesire/YandereSatsukiKernel/commit/f438e0a32cc8e45cd5a6d7b3b80b6e9058099d92"))

            cardview_intro.startAnimation(animate)
            cardview_secd.startAnimation(animate)
            cardview_thrd.startAnimation(animate)
            cardview_fth.startAnimation(animate)
            cardview_fifth.startAnimation(animate)
            cardview_fth.postDelayed({
                startActivity(intent)
                this.recreate()
                showAds()
            }, animate.duration)
        }

        cardview_fifth.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/ProjectShinkaPD"))

            cardview_intro.startAnimation(animate)
            cardview_secd.startAnimation(animate)
            cardview_thrd.startAnimation(animate)
            cardview_fth.startAnimation(animate)
            cardview_fifth.startAnimation(animate)
            cardview_fth.postDelayed({
                startActivity(intent)
                this.recreate()
                showAds()
            }, animate.duration)
        }
    }


    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
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
            val intent = Intent(applicationContext, YandereAudioActivity::class.java)
            startActivity(intent)
            showAds()
        } else if (id == R.id.nav_pdesireaudio) {
            val intent = Intent(applicationContext, KernelSettingsActivity::class.java)
            startActivity(intent)
            showAds()
        } else if (id == R.id.universal_management) {
            val intent = Intent(applicationContext, UniversalManagementActivity::class.java)
            startActivity(intent)
            showAds()
        } else if (id == R.id.sony_management) {
            val intent = Intent(applicationContext, SonyManagementActivity::class.java)
            startActivity(intent)
            showAds()
        } else if (id == R.id.basic_theme) {
            toggleThemeNew(false)
            showAds()
        } else if (id == R.id.new_theme) {
            toggleThemeNew(true)
            showAds()
        } else if (id == R.id.settings) {
            val intent = Intent(applicationContext, YandereSettingsActivity::class.java)
            startActivity(intent)
            showAds()
        } else if (id == R.id.donate) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://paypal.me/PDesireChan"))
            startActivity(intent)
        } else if (id == R.id.nav_contact_xda) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/crossdevice-dev/sony/soundmod-project-desire-feel-dream-sound-t3130504"))
            startActivity(intent)
            showAds()
        } else if (id == R.id.nav_contact_pdesire) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/member.php?u=6126659"))
            startActivity(intent)
            showAds()
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
