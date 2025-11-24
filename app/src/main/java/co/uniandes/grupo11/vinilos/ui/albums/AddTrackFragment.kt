package co.uniandes.grupo11.vinilos.ui.albums

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.viewmodels.AddTrackViewModel

class AddTrackFragment : Fragment() {
    private lateinit var selectedAlbumName: TextView
    private lateinit var selectedAlbumArtist: TextView
    private lateinit var trackNameInput: TextInputEditText
    private lateinit var trackDurationInput: TextInputEditText
    private lateinit var btnAddTrack: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: AddTrackViewModel
    private var albumId: Int = -1
    private var albumName: String = ""
    private var albumArtist: String = ""

    companion object {
        private const val ARG_ALBUM_ID = "albumId"
        private const val ARG_ALBUM_NAME = "albumName"
        private const val ARG_ALBUM_ARTIST = "albumArtist"

        fun newInstance(albumId: Int, albumName: String, albumArtist: String): AddTrackFragment {
            val fragment = AddTrackFragment()
            val args = Bundle()
            args.putInt(ARG_ALBUM_ID, albumId)
            args.putString(ARG_ALBUM_NAME, albumName)
            args.putString(ARG_ALBUM_ARTIST, albumArtist)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt(ARG_ALBUM_ID, -1)
            albumName = it.getString(ARG_ALBUM_NAME, "")
            albumArtist = it.getString(ARG_ALBUM_ARTIST, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_track, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedAlbumName = view.findViewById(R.id.selected_album_name)
        selectedAlbumArtist = view.findViewById(R.id.selected_album_artist)
        trackNameInput = view.findViewById(R.id.track_name_input)
        trackDurationInput = view.findViewById(R.id.track_duration_input)
        btnAddTrack = view.findViewById(R.id.btn_add_track)
        progressBar = view.findViewById(R.id.progress_bar)

        selectedAlbumName.text = albumName
        selectedAlbumArtist.text = albumArtist

        // Setup duration input formatter
        setupDurationInputFormatter()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(AddTrackViewModel::class.java)

        btnAddTrack.setOnClickListener {
            val trackName = trackNameInput.text.toString()
            val trackDuration = trackDurationInput.text.toString()
            viewModel.addTrack(albumId, trackName, trackDuration)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnAddTrack.isEnabled = !isLoading
        }

        viewModel.success.observe(viewLifecycleOwner) { track ->
            track?.let {
                Toast.makeText(requireContext(), "Pista agregada exitosamente", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errMsg ->
            errMsg?.let { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupDurationInputFormatter() {
        trackDurationInput.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private var isDeleting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Detect if user is deleting
                isDeleting = count > after
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if (isUpdating) return

                isUpdating = true

                val input = editable.toString().replace(":", "").filter { it.isDigit() }
                
                // If user is deleting and we have a colon at the end, remove it
                val currentText = editable.toString()
                if (isDeleting && currentText.endsWith(":") && input.length <= 2) {
                    editable?.replace(0, editable.length, input)
                    trackDurationInput.setSelection(input.length)
                    isUpdating = false
                    return
                }

                val formatted = when {
                    input.isEmpty() -> ""
                    input.length == 1 -> input
                    input.length == 2 -> {
                        // Don't add colon automatically when we have exactly 2 digits
                        // unless user is adding more characters
                        if (isDeleting || currentText.length < 3) input else "$input:"
                    }
                    input.length == 3 -> {
                        // 3 dígitos: formato M:SS (ej: "505" -> "5:05")
                        val minutes = input.substring(0, 1)
                        val seconds = input.substring(1, 3)
                        "$minutes:$seconds"
                    }
                    input.length == 4 -> {
                        // 4 dígitos: formato MM:SS (ej: "0505" -> "05:05")
                        val minutes = input.substring(0, 2)
                        val seconds = input.substring(2, 4)
                        "$minutes:$seconds"
                    }
                    else -> {
                        // Más de 4 dígitos: tomar solo los primeros 4 en formato MM:SS
                        val minutes = input.substring(0, 2)
                        val seconds = input.substring(2, 4)
                        "$minutes:$seconds"
                    }
                }

                if (editable.toString() != formatted) {
                    val cursorPosition = trackDurationInput.selectionStart
                    editable?.replace(0, editable.length, formatted)
                    
                    // Maintain cursor position intelligently
                    val newPosition = when {
                        isDeleting -> minOf(cursorPosition, formatted.length)
                        formatted.length >= 3 && cursorPosition == 2 -> 3 // Skip over colon when typing
                        else -> formatted.length
                    }
                    trackDurationInput.setSelection(minOf(newPosition, formatted.length))
                }

                isUpdating = false
            }
        })
    }
}

