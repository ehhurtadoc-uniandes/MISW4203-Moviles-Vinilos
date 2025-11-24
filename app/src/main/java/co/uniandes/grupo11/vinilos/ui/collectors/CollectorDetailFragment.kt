package co.uniandes.grupo11.vinilos.ui.collectors

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
import androidx.recyclerview.widget.GridLayoutManager
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.ui.adapters.CommentsAdapter
import co.uniandes.grupo11.vinilos.ui.adapters.CollectorAlbumsAdapter
import co.uniandes.grupo11.vinilos.ui.adapters.FavoritePerformersAdapter
import co.uniandes.grupo11.vinilos.ui.albums.AlbumDetailFragment
import co.uniandes.grupo11.vinilos.ui.artists.ArtistDetailFragment
import co.uniandes.grupo11.vinilos.ui.artists.BandDetailFragment
import co.uniandes.grupo11.vinilos.viewmodels.CollectorDetailViewModel

class CollectorDetailFragment : Fragment() {
    private lateinit var collectorImage: ImageView
    private lateinit var collectorNameText: TextView
    private lateinit var collectorEmailText: TextView
    private lateinit var collectorPhoneText: TextView
    private lateinit var commentsRecyclerView: RecyclerView
    private lateinit var favoriteArtistsRecyclerView: RecyclerView
    private lateinit var collectorAlbumsRecyclerView: RecyclerView
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var favoritePerformersAdapter: FavoritePerformersAdapter
    private lateinit var collectorAlbumsAdapter: CollectorAlbumsAdapter
    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: CollectorDetailViewModel
    private var collectorId: Int = -1

    companion object {
        private const val ARG_COLLECTOR_ID = "collectorId"

        fun newInstance(collectorId: Int): CollectorDetailFragment {
            val fragment = CollectorDetailFragment()
            val args = Bundle()
            args.putInt(ARG_COLLECTOR_ID, collectorId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectorId = it.getInt(ARG_COLLECTOR_ID, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collector_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectorImage = view.findViewById(R.id.collector_image)
        collectorNameText = view.findViewById(R.id.collector_name)
        collectorEmailText = view.findViewById(R.id.collector_email)
        collectorPhoneText = view.findViewById(R.id.collector_phone)
        commentsRecyclerView = view.findViewById(R.id.comments_recycler_view)
        favoriteArtistsRecyclerView = view.findViewById(R.id.favorite_artists_recycler_view)
        collectorAlbumsRecyclerView = view.findViewById(R.id.collector_albums_recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)

        commentsAdapter = CommentsAdapter()
        favoritePerformersAdapter = FavoritePerformersAdapter(requireContext()) { performer ->
            if (performer.birthDate != null) {
                val detailFragment = ArtistDetailFragment.newInstance(performer.id)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            } else if (performer.creationDate != null) {
                val detailFragment = BandDetailFragment.newInstance(performer.id)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "No se puede determinar el tipo de artista", Toast.LENGTH_SHORT).show()
            }
        }

        collectorAlbumsAdapter = CollectorAlbumsAdapter(requireContext()) { collectorAlbum ->
            collectorAlbum.album?.let { album ->
                val detailFragment = AlbumDetailFragment.newInstance(album.id)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commentsAdapter
        }

        favoriteArtistsRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = favoritePerformersAdapter
        }

        collectorAlbumsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = collectorAlbumsAdapter
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CollectorDetailViewModel::class.java)

        if (collectorId != -1) {
            viewModel.loadCollector(collectorId)

            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            viewModel.collector.observe(viewLifecycleOwner) { collector ->
                collector?.let {
                    collectorNameText.text = it.name
                    collectorEmailText.text = it.email
                    collectorPhoneText.text = it.telephone

                    commentsAdapter.updateComments(it.comments)
                    favoritePerformersAdapter.updatePerformers(it.favoritePerformers)
                    collectorAlbumsAdapter.updateCollectorAlbums(it.collectorAlbums)
                }
            }

            viewModel.error.observe(viewLifecycleOwner) { errMsg ->
                errMsg?.let { msg ->
                    Toast.makeText(requireContext(), "Error: $msg", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Error: ID del coleccionista no proporcionado", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }
}

