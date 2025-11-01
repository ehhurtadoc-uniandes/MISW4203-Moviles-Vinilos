package co.uniandes.grupo11.vinilos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import co.uniandes.grupo11.vinilos.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewDetailButton: Button = findViewById(R.id.view_detail_button)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)

        // Observadores del ViewModel
        viewModel.album.observe(this) { album ->
            album?.let {
                Toast.makeText(this, "Album cargado: ${it.name}", Toast.LENGTH_LONG).show()
                val intent = Intent(this, AlbumDetailActivity::class.java)
                intent.putExtra("albumId", it.id)
                startActivity(intent)
            }
        }

        viewModel.error.observe(this) { errMsg ->
            errMsg?.let { msg ->
                Toast.makeText(this, "Error: $msg", Toast.LENGTH_LONG).show()
            }
        }

        viewDetailButton.setOnClickListener {
            // Delegar la carga al ViewModel
            viewModel.loadAlbum(100)
        }
    }
}