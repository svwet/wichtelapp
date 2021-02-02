package ch.appbrew.wichtelapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.navigation.NavigationView



import androidx.navigation.ui.NavigationUI



class MainActivity : AppCompatActivity() {
    //Navigation Components:
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var navView:NavigationView
    private lateinit var navController: NavController


    //AppBarConfiguration:
    private val idSets = setOf(R.id.FirstFragment,R.id.fragment_gruppen,R.id.fragment_meineWunschliste, R.id.fragment_wichtelwunschliste, R.id.fragment_benutzereinstellung)
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Initialise Navigation Components
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        //Workaround for: navController = findNavController(R.id.nav_host)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfig = AppBarConfiguration(idSets,drawerLayout)

        setupActionBarWithNavController(navController,appBarConfig)
        navView.setupWithNavController(navController)

        override fun onSupportNavigateUp(): Boolean {
            val navController : NavController = navHostFragment.navController
            return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
        }

    }
}