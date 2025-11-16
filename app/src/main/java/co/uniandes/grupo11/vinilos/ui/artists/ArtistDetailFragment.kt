package co.uniandes.grupo11.vinilos.ui.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.ui.adapters.ArtistAlbumsAdapter
import co.uniandes.grupo11.vinilos.ui.adapters.AwardsAdapter
import co.uniandes.grupo11.vinilos.ui.albums.AlbumDetailFragment
import co.uniandes.grupo11.vinilos.viewmodels.ArtistDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class ArtistDetailFragment : Fragment() {
    private lateinit var artistImage: ImageView
    private lateinit var artistNameText: TextView
    private lateinit var artistBirthDateText: TextView
    private lateinit var artistDescriptionText: TextView
    private lateinit var albumsRecyclerView: RecyclerView
    private lateinit var awardsRecyclerView: RecyclerView
    private lateinit var albumsAdapter: ArtistAlbumsAdapter
    private lateinit var awardsAdapter: AwardsAdapter
    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: ArtistDetailViewModel
    private var artistId: Int = -1

    companion object {
        private const val ARG_ARTIST_ID = "artistId"

        fun newInstance(artistId: Int): ArtistDetailFragment {
            val fragment = ArtistDetailFragment()
            val args = Bundle()
            args.putInt(ARG_ARTIST_ID, artistId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artistId = it.getInt(ARG_ARTIST_ID, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artist_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artistImage = view.findViewById(R.id.artist_image)
        artistNameText = view.findViewById(R.id.artist_name)
        artistBirthDateText = view.findViewById(R.id.artist_birth_date)
        artistDescriptionText = view.findViewById(R.id.artist_description)
        albumsRecyclerView = view.findViewById(R.id.albums_recycler_view)
        awardsRecyclerView = view.findViewById(R.id.awards_recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)

        albumsAdapter = ArtistAlbumsAdapter { album ->
            val detailFragment = AlbumDetailFragment.newInstance(album.id)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        awardsAdapter = AwardsAdapter()

        albumsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = albumsAdapter
        }

        awardsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = awardsAdapter
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(ArtistDetailViewModel::class.java)

        if (artistId != -1) {
            viewModel.loadArtist(artistId)

            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            viewModel.artist.observe(viewLifecycleOwner) { artist ->
                artist?.let {
                    artistNameText.text = it.name
                    artistDescriptionText.text = it.description

                    // Formatear fecha de nacimiento
                    it.birthDate?.let { birthDateStr ->
                        try {
                            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                            val outputFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
                            val date = inputFormat.parse(birthDateStr)
                            val formattedDate = date?.let { d -> outputFormat.format(d) }
                            artistBirthDateText.text = getString(R.string.birth_date_prefix) + " " + (formattedDate ?: birthDateStr)
                        } catch (e: Exception) {
                            artistBirthDateText.text = getString(R.string.birth_date_prefix) + " " + birthDateStr
                        }
                    } ?: run {
                        artistBirthDateText.visibility = View.GONE
                    }

                    Glide.with(requireContext())
                        .load(it.image)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .circleCrop()
                        .into(artistImage)

                    albumsAdapter.updateAlbums(it.albums)
                    awardsAdapter.updateAwards(it.performerPrizes ?: emptyList())
                }
            }

            viewModel.error.observe(viewLifecycleOwner) { errMsg ->
                errMsg?.let { msg ->
                    Toast.makeText(requireContext(), "Error: $msg", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Error: ID del artista no proporcionado", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }
}

