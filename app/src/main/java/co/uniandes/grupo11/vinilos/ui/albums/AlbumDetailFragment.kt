package co.uniandes.grupo11.vinilos.ui.albums

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.adapters.PerformersAdapter
import co.uniandes.grupo11.vinilos.adapters.TracksAdapter
import co.uniandes.grupo11.vinilos.viewmodels.AlbumDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumDetailFragment : Fragment() {
    private lateinit var albumCoverImage: ImageView
    private lateinit var albumTitleText: TextView
    private lateinit var albumGenreText: TextView
    private lateinit var albumDescriptionText: TextView
    private lateinit var recordLabelText: TextView
    private lateinit var releaseDateText: TextView
    private lateinit var performersContainer: LinearLayout
    private lateinit var tracksRecyclerView: RecyclerView
    private lateinit var performersRecyclerView: RecyclerView
    private lateinit var tracksAdapter: TracksAdapter
    private lateinit var performersAdapter: PerformersAdapter

    private lateinit var viewModel: AlbumDetailViewModel
    private var albumId: Int = -1

    companion object {
        private const val ARG_ALBUM_ID = "albumId"

        fun newInstance(albumId: Int): AlbumDetailFragment {
            val fragment = AlbumDetailFragment()
            val args = Bundle()
            args.putInt(ARG_ALBUM_ID, albumId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt(ARG_ALBUM_ID, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumCoverImage = view.findViewById(R.id.album_cover_image)
        albumTitleText = view.findViewById(R.id.album_title)
        albumGenreText = view.findViewById(R.id.album_genre)
        albumDescriptionText = view.findViewById(R.id.album_description)
        recordLabelText = view.findViewById(R.id.record_label)
        releaseDateText = view.findViewById(R.id.release_date)
        performersContainer = view.findViewById(R.id.performers_container)
        tracksRecyclerView = view.findViewById(R.id.tracks_recycler_view)
        performersRecyclerView = view.findViewById(R.id.performers_recycler_view)

        tracksAdapter = TracksAdapter()
        performersAdapter = PerformersAdapter(requireContext())

        tracksRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tracksAdapter
        }

        performersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = performersAdapter
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(AlbumDetailViewModel::class.java)

        if (albumId != -1) {
            viewModel.loadAlbum(albumId)

            viewModel.album.observe(viewLifecycleOwner) { album ->
                album?.let {
                    albumTitleText.text = it.name
                    albumGenreText.text = it.genre
                    albumDescriptionText.text = it.description
                    recordLabelText.text = it.recordLabel

                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
                    try {
                        val date = inputFormat.parse(it.releaseDate)
                        val formattedDate = date?.let { d -> outputFormat.format(d) }
                        releaseDateText.text = "Fecha de lanzamiento: $formattedDate"
                    } catch (e: Exception) {
                        releaseDateText.text = "Fecha de lanzamiento: ${it.releaseDate}"
                    }

                    Log.d("AlbumDetailFragment", "Cargando imagen: ${it.cover}")
                    Glide.with(this)
                        .load(it.cover)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(albumCoverImage)

                    tracksAdapter.updateTracks(it.tracks)
                    performersAdapter.updatePerformers(it.performers)

                    performersContainer.removeAllViews()
                    it.performers.forEach { performer ->
                        val performerView = TextView(requireContext()).apply {
                            text = performer.name
                            textSize = 14f
                            setPadding(0, 0, 16, 0)
                            setTextColor(resources.getColor(android.R.color.secondary_text_light, null))
                        }
                        performersContainer.addView(performerView)
                    }
                }
            }

            viewModel.error.observe(viewLifecycleOwner) { errMsg ->
                errMsg?.let { msg ->
                    Toast.makeText(requireContext(), "Error: $msg", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Error: ID del álbum no proporcionado", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }
}

