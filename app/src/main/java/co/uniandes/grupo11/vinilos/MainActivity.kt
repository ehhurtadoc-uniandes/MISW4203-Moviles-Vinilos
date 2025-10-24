package co.uniandes.grupo11.vinilos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewDetailButton: Button = findViewById(R.id.view_detail_button)
        viewDetailButton.setOnClickListener {
            val intent = Intent(this, AlbumDetailActivity::class.java)
            startActivity(intent)
        }
    }
}