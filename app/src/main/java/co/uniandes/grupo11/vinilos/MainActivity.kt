package co.uniandes.grupo11.vinilos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import co.uniandes.grupo11.vinilos.ui.albums.AddCommentFragment
import co.uniandes.grupo11.vinilos.ui.albums.AddTrackFragment
import co.uniandes.grupo11.vinilos.ui.albums.AlbumsFragment
import co.uniandes.grupo11.vinilos.ui.albums.AlbumDetailFragment
import co.uniandes.grupo11.vinilos.ui.artists.ArtistDetailFragment
import co.uniandes.grupo11.vinilos.ui.artists.BandDetailFragment
import co.uniandes.grupo11.vinilos.ui.artists.ArtistsFragment
import co.uniandes.grupo11.vinilos.ui.collectors.CollectorDetailFragment
import co.uniandes.grupo11.vinilos.ui.collectors.CollectorsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            clearBackStack()

            when (item.itemId) {
                R.id.navigation_albums -> {
                    loadFragment(AlbumsFragment(), clearBackStack = false)
                    toolbar.title = getString(R.string.app_name)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    true
                }
                R.id.navigation_artists -> {
                    loadFragment(ArtistsFragment(), clearBackStack = false)
                    toolbar.title = getString(R.string.nav_artists)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    true
                }
                R.id.navigation_collectors -> {
                    loadFragment(CollectorsFragment(), clearBackStack = false)
                    toolbar.title = getString(R.string.nav_collectors)
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
                        is AlbumDetailFragment -> {
                            toolbar.title = "Detalle del Álbum"
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        }
                        is AddTrackFragment -> {
                            toolbar.title = "Asociar Pista"
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        }
                        is AddCommentFragment -> {
                            toolbar.title = "Agregar Comentario"
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        }
                        is ArtistDetailFragment -> {
                            toolbar.title = getString(R.string.artist_detail_title)
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        }
                        is BandDetailFragment -> {
                            toolbar.title = getString(R.string.artist_detail_title)
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        }
                        is CollectorDetailFragment -> {
                            toolbar.title = "Detalle del Coleccionista"
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        }
                        is AlbumsFragment -> {
                            toolbar.title = getString(R.string.app_name)
                            supportActionBar?.setDisplayHomeAsUpEnabled(false)
                            bottomNavigation.selectedItemId = R.id.navigation_albums
                        }
                        is ArtistsFragment -> {
                            toolbar.title = getString(R.string.nav_artists)
                            supportActionBar?.setDisplayHomeAsUpEnabled(false)
                            bottomNavigation.selectedItemId = R.id.navigation_artists
                        }
                        is CollectorsFragment -> {
                            toolbar.title = getString(R.string.nav_collectors)
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
                    toolbar.title = "Detalle del Álbum"
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                is AddTrackFragment -> {
                    toolbar.title = "Asociar Pista"
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                is AddCommentFragment -> {
                    toolbar.title = "Agregar Comentario"
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                is ArtistDetailFragment -> {
                    toolbar.title = getString(R.string.artist_detail_title)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                is BandDetailFragment -> {
                    toolbar.title = getString(R.string.artist_detail_title)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                is CollectorDetailFragment -> {
                    toolbar.title = "Detalle del Coleccionista"
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                is AlbumsFragment -> {
                    toolbar.title = getString(R.string.app_name)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                is ArtistsFragment -> {
                    toolbar.title = getString(R.string.nav_artists)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                is CollectorsFragment -> {
                    toolbar.title = getString(R.string.nav_collectors)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment, clearBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }

    private fun clearBackStack() {
        while (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        }
    }
}
