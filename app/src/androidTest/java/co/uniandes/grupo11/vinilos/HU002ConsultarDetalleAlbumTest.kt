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
class HU002ConsultarDetalleAlbumTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testConsultarDetalleAlbum_NavegacionDesdeListaAlDetalle() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.album_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleAlbum_VerificaTituloEnBarraSuperior() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.toolbar_title))
            .check(matches(isDisplayed()))
            .check(matches(withText("Detalle del Álbum")))
    }

    @Test
    fun testConsultarDetalleAlbum_VerificaInformacionBasicaDelAlbum() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.album_cover_image))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.album_title))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.album_genre))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.record_label))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleAlbum_VerificaDescripcionYFechaLanzamiento() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.album_description))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.release_date))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleAlbum_VerificaSeccionDePistas() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.tracks_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("Pistas")))
        
        onView(withId(R.id.tracks_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleAlbum_VerificaSeccionDeInterpretes() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.performers_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("Intérpretes")))
        
        onView(withId(R.id.performers_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleAlbum_VerificaScrollEnDetalle() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.performers_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleAlbum_VerificaMenuNavegacionInferiorVisible() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleAlbum_RegresarALaLista() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.album_title))
            .check(matches(isDisplayed()))
        
        pressBack()
        
        Thread.sleep(500)
        
        onView(withId(R.id.albums_recycler_view))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.toolbar_title))
            .check(matches(withText("Vinilos")))
    }

    @Test
    fun testConsultarDetalleAlbum_NavegacionEntreVariosAlbumes() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.album_title))
            .check(matches(isDisplayed()))
        
        pressBack()
        
        Thread.sleep(500)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        
        Thread.sleep(1000)
        
        onView(withId(R.id.album_title))
            .check(matches(isDisplayed()))
    }
}

