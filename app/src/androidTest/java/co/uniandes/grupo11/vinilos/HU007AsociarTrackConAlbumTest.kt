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
class HU007AsociarTrackConAlbumTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAsociarTrack_NavegacionDesdeDetalleAlbumAFormulario() {
        Thread.sleep(3000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(3000)
        
        onView(withId(R.id.btn_add_track))
            .perform(click())
        
        Thread.sleep(3000)
        
        onView(withId(R.id.toolbar_title))
            .check(matches(isDisplayed()))
            .check(matches(withText("Asociar Pista")))
    }

    @Test
    fun testAsociarTrack_VerificaInformacionAlbumSeleccionado() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_track))
            .perform(click())
        
        Thread.sleep(500)
        
        onView(withId(R.id.selected_album_card))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.selected_album_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAsociarTrack_VerificaCamposDelFormulario() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_track))
            .perform(click())
        
        Thread.sleep(500)
        
        onView(withId(R.id.track_name_input))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.track_duration_input))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.btn_add_track))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAsociarTrack_IngresarNombrePista() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_track))
            .perform(click())
        
        Thread.sleep(500)
        
        onView(withId(R.id.track_name_input))
            .perform(click(), replaceText("Decisiones"), closeSoftKeyboard())
        
        onView(withId(R.id.track_name_input))
            .check(matches(withText("Decisiones")))
    }

    @Test
    fun testAsociarTrack_IngresarDuracion() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_track))
            .perform(click())
        
        Thread.sleep(500)
        
        onView(withId(R.id.track_duration_input))
            .perform(click())
        
        onView(withId(R.id.track_duration_input))
            .perform(clearText(), replaceText("505"))
        
        Thread.sleep(500)
        
        onView(withId(R.id.track_duration_input))
            .perform(closeSoftKeyboard())
        
        Thread.sleep(300)
        
        // El campo tiene TextWatcher que formatea "505" a "5:05"
        onView(withId(R.id.track_duration_input))
            .check(matches(withText("5:05")))
    }

    @Test
    fun testAsociarTrack_VerificaFormatoIndicador() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_track))
            .perform(click())
        
        Thread.sleep(500)
        
        onView(withId(R.id.duration_format_text))
            .check(matches(isDisplayed()))
            .check(matches(withText("Formato: MM:SS")))
    }

    @Test
    fun testAsociarTrack_FormularioCompleto() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_track))
            .perform(click())
        
        Thread.sleep(500)
        
        onView(withId(R.id.track_name_input))
            .perform(click(), replaceText("Decisiones"), closeSoftKeyboard())
        
        Thread.sleep(300)
        
        onView(withId(R.id.track_duration_input))
            .perform(click(), replaceText("505"), closeSoftKeyboard())
        
        Thread.sleep(300)
        
        onView(withId(R.id.btn_add_track))
            .check(matches(isEnabled()))
    }

    @Test
    fun testAsociarTrack_BotonAsociarVisible() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_track))
            .perform(click())
        
        Thread.sleep(500)
        
        onView(withId(R.id.btn_add_track))
            .check(matches(isDisplayed()))
            .check(matches(withText("Asociar Pista")))
    }

    @Test
    fun testAsociarTrack_RegresarAlDetalle() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_track))
            .perform(click())
        
        Thread.sleep(500)
        
        pressBack()
        
        Thread.sleep(500)
        
        onView(withId(R.id.album_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAsociarTrack_VerificaMenuNavegacionInferior() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_track))
            .perform(click())
        
        Thread.sleep(500)
        
        onView(withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()))
    }
}

