package co.uniandes.grupo11.vinilos

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.recyclerview.widget.RecyclerView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HU001ConsultarCatalogoAlbumesTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testConsultarCatalogoAlbums_VerificaQueSeCargueLaListaDeAlbums() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarCatalogoAlbums_VerificaQueTengaAlMenosUnAlbum() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testConsultarCatalogoAlbums_VerificaTituloEnBarraSuperior() {
        onView(withId(R.id.toolbar_title))
            .check(matches(isDisplayed()))
            .check(matches(withText("Vinilos")))
    }

    @Test
    fun testConsultarCatalogoAlbums_VerificaMenuNavegacionInferior() {
        onView(withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.navigation_albums))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarCatalogoAlbums_VerificaQueSeVisualizenDatosDelAlbum() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        
        onView(withId(R.id.albums_recycler_view))
            .check(matches(hasDescendant(withId(R.id.album_name))))
            .check(matches(hasDescendant(withId(R.id.album_artists))))
            .check(matches(hasDescendant(withId(R.id.album_genre))))
    }

    @Test
    fun testConsultarCatalogoAlbums_ScrollEnLaLista() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
            )
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarCatalogoAlbums_VerificaLayoutDeGrid() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .check(matches(isDisplayed()))
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testConsultarCatalogoAlbums_RefreshPullToRefresh() {
        Thread.sleep(2000)
        
        onView(withId(R.id.swipe_refresh_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarCatalogoAlbums_NavegacionEntreSecciones() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        onView(withId(R.id.toolbar_title))
            .check(matches(withText("Artistas")))
        
        onView(withId(R.id.navigation_albums))
            .perform(click())
        
        onView(withId(R.id.toolbar_title))
            .check(matches(withText("Vinilos")))
    }
}

