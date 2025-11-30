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
class HU008ComentarAlbumTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testComentarAlbum_NavegacionDesdeDetalleAlbumAFormulario() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
            .check(matches(withText("Agregar Comentario")))
    }

    @Test
    fun testComentarAlbum_VerificaTituloEnBarraSuperior() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
            .check(matches(withText("Agregar Comentario")))
    }

    @Test
    fun testComentarAlbum_VerificaInformacionAlbumSeleccionado() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.album_info_card))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.album_name))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.album_artist))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testComentarAlbum_VerificaCamposDelFormulario() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.comment_input))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.rating_bar))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.collector_spinner))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.btn_submit_comment))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testComentarAlbum_IngresarDescripcion() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.comment_input))
            .perform(replaceText("Un álbum increíble con excelente música"), closeSoftKeyboard())
        
        onView(withId(R.id.comment_input))
            .check(matches(withText("Un álbum increíble con excelente música")))
    }

    @Test
    fun testComentarAlbum_SeleccionarCalificacion() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.rating_bar))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testComentarAlbum_VerificaSpinnerColeccionistas() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.collector_spinner))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testComentarAlbum_FormularioCompleto() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(2000)
        
        onView(withId(R.id.comment_input))
            .perform(replaceText("Excelente álbum"), closeSoftKeyboard())
        
        Thread.sleep(300)
        
        onView(withId(R.id.rating_bar))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        
        Thread.sleep(300)
        
        onView(withId(R.id.collector_spinner))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        
        Thread.sleep(300)
        
        onView(withId(R.id.btn_submit_comment))
            .perform(scrollTo())
            .check(matches(isEnabled()))
    }

    @Test
    fun testComentarAlbum_BotonAgregarVisible() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.btn_submit_comment))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("Enviar Comentario")))
    }

    @Test
    fun testComentarAlbum_RegresarAlDetalle() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(1000)
        
        pressBack()
        
        Thread.sleep(500)
        
        onView(withId(R.id.album_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testComentarAlbum_VerificaMenuNavegacionInferior() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testComentarAlbum_VerificaEtiquetasDelFormulario() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo(), click())
        
        Thread.sleep(1000)
        
        onView(withId(R.id.album_label))
            .check(matches(isDisplayed()))
            .check(matches(withText("Álbum")))
        
        onView(withId(R.id.comment_label))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("Comentario")))
        
        onView(withId(R.id.rating_label))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("Calificación")))
        
        onView(withId(R.id.collector_label))
            .check(matches(isDisplayed()))
            .check(matches(withText("Coleccionista")))
    }

    @Test
    fun testComentarAlbum_VerificaBotonAgregarEnDetalleAlbum() {
        Thread.sleep(2000)
        
        onView(withId(R.id.albums_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(2000)
        
        onView(withId(R.id.btn_add_comment))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText("+ Agregar")))
    }
}

