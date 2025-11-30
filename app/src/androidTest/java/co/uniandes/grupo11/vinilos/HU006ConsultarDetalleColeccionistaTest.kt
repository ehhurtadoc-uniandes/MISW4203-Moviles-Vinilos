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
class HU006ConsultarDetalleColeccionistaTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testConsultarDetalleColeccionista_NavegacionDesdeListaAlDetalle() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collector_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleColeccionista_VerificaTituloEnBarraSuperior() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
            .check(matches(withText("Detalle del Coleccionista")))
    }

    @Test
    fun testConsultarDetalleColeccionista_VerificaInformacionBasicaDelColeccionista() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collector_image))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.collector_name))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.collector_email))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.collector_phone))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleColeccionista_VerificaSeccionDeComentarios() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.comments_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("Comentarios Realizados")))
        
        onView(withId(R.id.comments_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleColeccionista_VerificaSeccionDeArtistasFavoritos() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.favorite_artists_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("Artistas Favoritos")))
        
        onView(withId(R.id.favorite_artists_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleColeccionista_VerificaScrollEnDetalle() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.favorite_artists_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleColeccionista_VerificaMenuNavegacionInferiorVisible() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleColeccionista_RegresarALaLista() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collector_name))
            .check(matches(isDisplayed()))
        
        pressBack()
        
        Thread.sleep(500)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.toolbar))
            .check(matches(withText("Coleccionistas")))
    }

    @Test
    fun testConsultarDetalleColeccionista_NavegacionEntreVariosColeccionistas() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collector_name))
            .check(matches(isDisplayed()))
        
        pressBack()
        
        Thread.sleep(500)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collector_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleColeccionista_VerificaDatosDeComentarios() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.comments_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleColeccionista_VerificaDatosDeArtistasFavoritos() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.favorite_artists_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleColeccionista_VerificaSeccionDeAlbumesEnColeccion() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collector_albums_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("Álbumes en Colección")))
        
        onView(withId(R.id.collector_albums_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarDetalleColeccionista_VerificaDatosDeAlbumesEnColeccion() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collector_albums_recycler_view))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }
}


