package co.uniandes.grupo11.vinilos

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
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
class HU004ConsultarDetalleArtistaTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testConsultarDetalleArtista_NavegacionDesdeListaAlDetalle() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artist_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleArtista_VerificaTituloEnBarraSuperior() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.toolbar_title))
            .check(matches(isDisplayed()))
            .check(matches(withText("Detalle del Artista")))
    }

    @Test
    fun testConsultarDetalleArtista_VerificaInformacionBasicaDelArtista() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artist_image))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.artist_name))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.artist_description))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleArtista_VerificaFechaNacimientoYDescripcion() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artist_description))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.artist_birth_date))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleArtista_VerificaSeccionDeAlbumes() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("Álbumes")))
        
        onView(withId(R.id.albums_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleArtista_VerificaSeccionDePremios() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.awards_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("Premios")))
        
        onView(withId(R.id.awards_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleArtista_VerificaScrollEnDetalle() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.awards_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleArtista_VerificaMenuNavegacionInferiorVisible() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleArtista_RegresarALaLista() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artist_name))
            .check(matches(isDisplayed()))
        
        pressBack()
        
        Thread.sleep(500)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.toolbar_title))
            .check(matches(withText("Artistas")))
    }

    @Test
    fun testConsultarDetalleArtista_NavegacionDesdeDetalleAlbum() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.album_title))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.performers_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artist_name))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.toolbar_title))
            .check(matches(withText("Detalle del Artista")))
    }

    @Test
    fun testConsultarDetalleArtista_NavegacionDesdeAlbumEnDetalleArtista() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artist_name))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.albums_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.album_title))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.toolbar_title))
            .check(matches(withText("Detalle del Álbum")))
    }

    @Test
    fun testConsultarDetalleArtista_NavegacionEntreVariosArtistas() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artist_name))
            .check(matches(isDisplayed()))
        
        pressBack()
        
        Thread.sleep(500)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artist_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleArtista_VerificaDatosDeAlbumes() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        
        onView(withId(R.id.albums_recycler_view))
            .check(matches(hasDescendant(withId(R.id.album_name))))
    }

    @Test
    fun testConsultarDetalleArtista_VerificaDatosDePremios() {
        onView(withId(R.id.navigation_artists))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.artists_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.awards_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        
        onView(withId(R.id.awards_recycler_view))
            .check(matches(hasDescendant(withId(R.id.award_date))))
    }
}

