package co.uniandes.grupo11.vinilos.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.models.Collector
import co.uniandes.grupo11.vinilos.viewmodels.AddCommentViewModel

class AddCommentFragment : Fragment() {
    private lateinit var albumNameTextView: TextView
    private lateinit var albumArtistTextView: TextView
    private lateinit var collectorSpinner: AutoCompleteTextView
    private lateinit var commentInput: TextInputEditText
    private lateinit var ratingBar: RatingBar
    private lateinit var ratingText: TextView
    private lateinit var btnSubmitComment: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: AddCommentViewModel
    private var albumId: Int = -1
    private var albumName: String = ""
    private var albumArtist: String = ""
    private var collectors: List<Collector> = emptyList()
    private var selectedCollectorId: Int = -1

    companion object {
        private const val ARG_ALBUM_ID = "albumId"
        private const val ARG_ALBUM_NAME = "albumName"
        private const val ARG_ALBUM_ARTIST = "albumArtist"

        fun newInstance(albumId: Int, albumName: String, albumArtist: String): AddCommentFragment {
            val fragment = AddCommentFragment()
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
        return inflater.inflate(R.layout.fragment_add_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumNameTextView = view.findViewById(R.id.album_name)
        albumArtistTextView = view.findViewById(R.id.album_artist)
        collectorSpinner = view.findViewById(R.id.collector_spinner)
        commentInput = view.findViewById(R.id.comment_input)
        ratingBar = view.findViewById(R.id.rating_bar)
        ratingText = view.findViewById(R.id.rating_text)
        btnSubmitComment = view.findViewById(R.id.btn_submit_comment)
        progressBar = view.findViewById(R.id.progress_bar)

        albumNameTextView.text = albumName
        albumArtistTextView.text = albumArtist

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(AddCommentViewModel::class.java)

        setupRatingBar()
        setupObservers()
        setupSubmitButton()
    }

    private fun setupRatingBar() {
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            val ratingInt = rating.toInt()
            ratingText.text = "$ratingInt de 5 estrellas"
        }
    }

    private fun setupObservers() {
        viewModel.collectors.observe(viewLifecycleOwner) { collectorsList ->
            collectors = collectorsList
            setupCollectorSpinner(collectorsList)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnSubmitComment.isEnabled = !isLoading
            collectorSpinner.isEnabled = !isLoading
            commentInput.isEnabled = !isLoading
            ratingBar.isEnabled = !isLoading
        }

        viewModel.success.observe(viewLifecycleOwner) { success ->
            if (success == true) {
                Toast.makeText(requireContext(), "Comentario agregado exitosamente", Toast.LENGTH_SHORT).show()
                viewModel.clearSuccess()
                parentFragmentManager.popBackStack()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    private fun setupCollectorSpinner(collectorsList: List<Collector>) {
        val collectorNames = collectorsList.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, collectorNames)
        collectorSpinner.setAdapter(adapter)

        collectorSpinner.setOnItemClickListener { _, _, position, _ ->
            selectedCollectorId = collectorsList[position].id
        }
    }

    private fun setupSubmitButton() {
        btnSubmitComment.setOnClickListener {
            val description = commentInput.text.toString()
            val rating = ratingBar.rating.toInt()

            if (selectedCollectorId == -1) {
                Toast.makeText(requireContext(), "Por favor selecciona un coleccionista", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (description.isBlank()) {
                Toast.makeText(requireContext(), "Por favor escribe un comentario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addComment(albumId, description, rating, selectedCollectorId)
        }
    }
}

