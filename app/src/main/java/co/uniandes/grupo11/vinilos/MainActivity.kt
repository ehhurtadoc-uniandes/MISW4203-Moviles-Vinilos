package co.uniandes.grupo11.vinilos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import co.uniandes.grupo11.vinilos.ui.albums.AlbumsFragment
import co.uniandes.grupo11.vinilos.ui.albums.AlbumDetailFragment
import co.uniandes.grupo11.vinilos.ui.artists.ArtistsFragment
import co.uniandes.grupo11.vinilos.ui.collectors.CollectorsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbarTitle = findViewById(R.id.toolbar_title)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            clearBackStack()
            
            when (item.itemId) {
                R.id.navigation_albums -> {
                    loadFragment(AlbumsFragment(), clearBackStack = false)
                    toolbarTitle.text = getString(R.string.app_name)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    true
                }
                R.id.navigation_artists -> {
                    loadFragment(ArtistsFragment(), clearBackStack = false)
                    toolbarTitle.text = getString(R.string.nav_artists)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    true
                }
                R.id.navigation_collectors -> {
                    loadFragment(CollectorsFragment(), clearBackStack = false)
                    toolbarTitle.text = getString(R.string.nav_collectors)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.navigation_albums
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    
                    val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                    when (currentFragment) {
                        is AlbumsFragment -> {
                            toolbarTitle.text = getString(R.string.app_name)
                            supportActionBar?.setDisplayHomeAsUpEnabled(false)
                            bottomNavigation.selectedItemId = R.id.navigation_albums
                        }
                        is ArtistsFragment -> {
                            toolbarTitle.text = getString(R.string.nav_artists)
                            supportActionBar?.setDisplayHomeAsUpEnabled(false)
                            bottomNavigation.selectedItemId = R.id.navigation_artists
                        }
                        is CollectorsFragment -> {
                            toolbarTitle.text = getString(R.string.nav_collectors)
                            supportActionBar?.setDisplayHomeAsUpEnabled(false)
                            bottomNavigation.selectedItemId = R.id.navigation_collectors
                        }
                    }
                } else {
                    finish()
                }
            }
        })

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            when (currentFragment) {
                is AlbumDetailFragment -> {
                    toolbarTitle.text = "Detalle del Álbum"
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                is AlbumsFragment -> {
                    toolbarTitle.text = getString(R.string.app_name)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                is ArtistsFragment -> {
                    toolbarTitle.text = getString(R.string.nav_artists)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                is CollectorsFragment -> {
                    toolbarTitle.text = getString(R.string.nav_collectors)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment, clearBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun clearBackStack() {
        while (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        }
    }
}
