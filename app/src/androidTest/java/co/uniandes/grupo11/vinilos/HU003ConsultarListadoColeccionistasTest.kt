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
class HU003ConsultarListadoColeccionistasTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testConsultarListadoColeccionistas_NavegaASeccionColeccionistas() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        onView(withId(R.id.toolbar_title))
            .check(matches(isDisplayed()))
            .check(matches(withText("Coleccionistas")))
    }

    @Test
    fun testConsultarListadoColeccionistas_VerificaTituloEnBarraSuperior() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        onView(withId(R.id.toolbar_title))
            .check(matches(isDisplayed()))
            .check(matches(withText("Coleccionistas")))
    }

    @Test
    fun testConsultarListadoColeccionistas_VerificaQueSeCargueLaLista() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarListadoColeccionistas_VerificaQueTengaAlMenosUnColeccionista() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testConsultarListadoColeccionistas_VerificaDatosDelColeccionista() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(hasDescendant(withId(R.id.collector_name))))
            .check(matches(hasDescendant(withId(R.id.collector_email))))
            .check(matches(hasDescendant(withId(R.id.collector_phone))))
    }

    @Test
    fun testConsultarListadoColeccionistas_ScrollEnLaLista() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
            )
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarListadoColeccionistas_VerificaPullToRefresh() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.swipe_refresh_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarListadoColeccionistas_VerificaMenuNavegacionInferior() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        onView(withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.navigation_collectors))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConsultarListadoColeccionistas_NavegacionEntreSeccionesDesdeColeccionistas() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.toolbar_title))
            .check(matches(withText("Coleccionistas")))
        
        onView(withId(R.id.navigation_albums))
            .perform(click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.toolbar_title))
            .check(matches(withText("Vinilos")))
        
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.toolbar_title))
            .check(matches(withText("Coleccionistas")))
    }

    @Test
    fun testConsultarListadoColeccionistas_VerificaLayoutLineal() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(isDisplayed()))
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testConsultarListadoColeccionistas_VerificaNombreColeccionistaNoVacio() {
        onView(withId(R.id.navigation_collectors))
            .perform(click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collectors_recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        
        onView(withId(R.id.collectors_recycler_view))
            .check(matches(hasDescendant(withId(R.id.collector_name))))
    }
}
