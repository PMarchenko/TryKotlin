package com.pmarchenko.itdroid.pocketkotlin

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorBridge
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorHost
import com.pmarchenko.itdroid.pocketkotlin.utils.logd

class MainActivity : AppCompatActivity(), EditorHost {

    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var appBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initUI()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun registerEditor(editorBridge: EditorBridge) {
        logd { "registerEditor $editorBridge" }
    }

    override fun unregisterEditor() {
        logd { "unregisterEditor" }
    }

    private fun initUI() {
        appBarLayout = findViewById(R.id.app_bar)
        navigationView = findViewById(R.id.nav_view)

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))

        drawerLayout = findViewById(R.id.drawer_layout)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.menu_item_my_projects), drawerLayout)

        // Wire navigation components together
        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)

        // As nav drawer menu grouped by categories, menu highlighted logic implemented in setupActionBarWithNavController is not working
        navController.addOnDestinationChangedListener { _, navDestination: NavDestination, _ -> navigationView.setCheckedItem(navDestination.id) }
    }
}
