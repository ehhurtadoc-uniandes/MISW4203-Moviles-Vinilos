package co.uniandes.grupo11.vinilos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewDetailButton: Button = findViewById(R.id.view_detail_button)
        viewDetailButton.setOnClickListener {
            val networkService = NetworkServiceAdapter.getInstance(this)

            networkService.getAlbum(100,
                onComplete = { album ->
                    Toast.makeText(this, "Album cargado: ${album.name}", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, AlbumDetailActivity::class.java)
                    intent.putExtra("albumId", album.id)
                    startActivity(intent)
                },
                onError = { error ->
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}