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
class HU003ConsultarListadoArtistasTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testConsultarListadoArtistas_NavegaASeccionArtistas() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
            .check(matches(withText("Artistas")))
    }

    @Test
    fun testConsultarListadoArtistas_VerificaTituloEnBarraSuperior() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
            .check(matches(withText("Artistas")))
    }

    @Test
    fun testConsultarListadoArtistas_VerificaQueSeCargueLaLista() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarListadoArtistas_VerificaQueTengaAlMenosUnArtista() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testConsultarListadoArtistas_VerificaDatosDelArtista() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(hasDescendant(withId(R.id.artist_name))))
            .check(matches(hasDescendant(withId(R.id.artist_description))))
            .check(matches(hasDescendant(withId(R.id.artist_image))))
    }

    @Test
    fun testConsultarListadoArtistas_ScrollEnLaLista() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
            )
    }

    @Test
    fun testConsultarListadoArtistas_VerificaPullToRefresh() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.swipe_refresh_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarListadoArtistas_VerificaMenuNavegacionInferior() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        onView(withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.navigation_artists))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarListadoArtistas_NavegacionEntreSeccionesDesdeArtistas() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        onView(withId(R.id.toolbar))
            .check(matches(withText("Artistas")))
        
        onView(withId(R.id.navigation_albums))
            .perform(click())
        
        onView(withId(R.id.toolbar))
            .check(matches(withText("Vinilos")))
        
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        onView(withId(R.id.toolbar))
            .check(matches(withText("Artistas")))
    }

    @Test
    fun testConsultarListadoArtistas_VerificaLayoutLineal() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testConsultarListadoArtistas_VerificaNombreArtistaNoVacio() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(hasDescendant(withId(R.id.artist_name))))
    }

    @Test
    fun testConsultarListadoArtistas_VerificaDescripcionArtista() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
        
        Thread.sleep(500)
        
        onView(withId(R.id.artists_recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(hasDescendant(withId(R.id.artist_description))))
    }

    @Test
    fun testConsultarListadoArtistas_VerificaImagenArtista() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(hasDescendant(withId(R.id.artist_image))))
    }
}
