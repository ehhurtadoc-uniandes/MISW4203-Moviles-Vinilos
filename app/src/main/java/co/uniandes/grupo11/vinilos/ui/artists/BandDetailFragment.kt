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
import co.uniandes.grupo11.vinilos.viewmodels.BandDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class BandDetailFragment : Fragment() {
    private lateinit var artistImage: ImageView
    private lateinit var artistNameText: TextView
    private lateinit var artistCreationDateText: TextView
    private lateinit var artistDescriptionText: TextView
    private lateinit var albumsRecyclerView: RecyclerView
    private lateinit var awardsRecyclerView: RecyclerView
    private lateinit var albumsAdapter: ArtistAlbumsAdapter
    private lateinit var awardsAdapter: AwardsAdapter
    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: BandDetailViewModel
    private var bandId: Int = -1

    companion object {
        private const val ARG_BAND_ID = "bandId"

        fun newInstance(bandId: Int): BandDetailFragment {
            val fragment = BandDetailFragment()
            val args = Bundle()
            args.putInt(ARG_BAND_ID, bandId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bandId = it.getInt(ARG_BAND_ID, -1)
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
        artistCreationDateText = view.findViewById(R.id.artist_birth_date)
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
        ).get(BandDetailViewModel::class.java)

        if (bandId != -1) {
            viewModel.loadBand(bandId)

            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            viewModel.band.observe(viewLifecycleOwner) { band ->
                band?.let {
                    artistNameText.text = it.name
                    artistDescriptionText.text = it.description

                    // Formatear fecha de creación
                    it.creationDate?.let { creationDateStr ->
                        try {
                            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                            val outputFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
                            val date = inputFormat.parse(creationDateStr)
                            val formattedDate = date?.let { d -> outputFormat.format(d) }
                            artistCreationDateText.text = getString(R.string.creation_date_prefix) + " " + (formattedDate ?: creationDateStr)
                        } catch (e: Exception) {
                            artistCreationDateText.text = getString(R.string.creation_date_prefix) + " " + creationDateStr
                        }
                    } ?: run {
                        artistCreationDateText.visibility = View.GONE
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
            Toast.makeText(requireContext(), "Error: ID de la banda no proporcionado", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }
}

